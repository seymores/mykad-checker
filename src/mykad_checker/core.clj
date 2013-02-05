(ns mykad-checker.core
  (:use [net.cgrand.enlive-html :as html]))

(def SITE-URL "http://daftarj.spr.gov.my/NEWDAFTARJ/DaftarjBM.aspx")

(defn fetch-url [url]
  "Grab the page"
 (html/html-resource (java.net.URL. url)))

(defn get-params 
  "Extract necessary params variables from page"
  [site ic-number]
  (let [inputs (html/select site #{[:input#__VIEWSTATE] [:input#__EVENTVALIDATION]})
        viewstate (first inputs)
        event-vali (second inputs)]
    {"__VIEWSTATE" viewstate "__EVENTVALIDATION" event-vali "__EVENTTARGET" "" "__EVENTARGUMENT" "" "Semak" "CHECK" "txtIC" ic-number}))

