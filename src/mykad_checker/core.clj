(ns mykad-checker.core
  (:use [net.cgrand.enlive-html :as html])
  (:require [clj-http.client :as client])
  (:import (java.io.ByteArrayInputStream) (java.net.URL)))

(def site-url "http://daftarj.spr.gov.my/NEWDAFTARJ/DaftarjBM.aspx")

(defn- string-to-stream 
  "Convert string to inputstream"
  [string]
  (ByteArrayInputStream. (.getBytes (.trim string))))

(defn- fetch-url 
  [url]
  "Grab the page with Enlive"
  (html/html-resource (URL. url)))

(defn- only-val [elem]
  (.trim (first (:content (first elem)))))

(defn get-params 
  "Extract necessary params variable from page"
  [site ic-number]
  (let [inputs      (html/select site #{[:input#__VIEWSTATE] [:input#__EVENTVALIDATION]})
        viewstate   (:value (:attrs (first inputs)))
        event-vali  (:value (:attrs (second inputs)))]
    {"__VIEWSTATE" viewstate "__EVENTVALIDATION" event-vali "__EVENTTARGET" "" "__EVENTARGUMENT" "" "Semak" "Semak" "txtIC" ic-number}))

(defn submit-check
  "Form submit to perform the IC check"
  [params]
  (let [output (client/post site-url {:form-params params})
        html (html/html-resource (string-to-stream (:body output)))]
    html))

(defn extract-result 
  "Extract the data from the form submit result page"
  [page]
  (when (seq (html/select page [:td.kerolclass]))
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

(defn prepare-params
  "Simplify the preparation of the vars and params needed"
  [icno]
  (get-params (fetch-url site-url) icno))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn check-mykad
  "Check the ic with the given ic number"
  [icno]
  (-> (prepare-params icno) submit-check extract-result))

