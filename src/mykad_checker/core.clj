(ns mykad-checker.core
  (:use [net.cgrand.enlive-html :as html])
  (:require [clj-http.client :as client]))

(import 'java.io.ByteArrayInputStream)

(def site-url "http://daftarj.spr.gov.my/NEWDAFTARJ/DaftarjBM.aspx")
(def headers {"Content-type" "application/x-www-form-urlencoded"
              "Accept" "text/plain"})

(defn string-to-stream 
  "Convert string to inputstream"
  [string]
  (ByteArrayInputStream. (.getBytes (.trim string))))

(defn fetch-url 
  [url]
  "Grab the page"
  (html/html-resource (java.net.URL. url)))

(defn get-params 
  "Extract necessary params variables from page"
  [site ic-number]
  (let [inputs      (html/select site #{[:input#__VIEWSTATE] [:input#__EVENTVALIDATION]})
        viewstate   (:value (:attrs (first inputs)))
        event-vali  (:value (:attrs (second inputs)))]
    {"__VIEWSTATE" viewstate "__EVENTVALIDATION" event-vali "__EVENTTARGET" "" 
     "__EVENTARGUMENT" "" "Semak" "Semak" "txtIC" ic-number}))

(defn prepare-params
  [icno]
  (get-params (fetch-url site-url) icno))

(defn submit-check
  "Form submit params to check start check"
  [params]
  (let [output (client/post site-url {:form-params params})
        html (html/html-resource (string-to-stream (:body output)))] html))

(defn only-val [elem]
  (.trim (first (:content (first elem)))))

(defn extract-result 
  "Extract the result from the form submit"
  [page]
  (when-not (empty? (html/select page [:td.kerolclass]))
    {:nama    (only-val (html/select page [:span#Labelnama]))
     :ic-no   (only-val (html/select page [:span#LabelIC]))
     :dob     (only-val (html/select page [:span#LabelTlahir]))
     :jantina (only-val (html/select page [:span#Labeljantina]))
     :loc     (only-val (html/select page [:span#Labellokaliti]))
     :daerah  (only-val (html/select page [:span#Labeldm]))
     :dun     (only-val (html/select page [:span#Labeldun]))
     :negeri  (only-val (html/select page [:span#Labelnegeri]))
     :parli   (only-val (html/select page [:span#Labelpar]))
     :status  (only-val (html/select page [:span#LABELSTATUSDPI]))}))

(defn check-mykad
  "Check the ic with the given ic number"
  [icno]
  (-> (prepare-params icno)
      (submit-check)
      (extract-result)))
