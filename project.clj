(defproject mykad-checker "0.1.0-SNAPSHOT"
  :description "MyKAD checker in clj -- clone of https://github.com/Sinar/mykadspr/blob/master/py/mykadspr.py"
  :url "https://github.com/seymores/mykad-checker"
  :license {:name "GPL v3" :url "http://www.gnu.org/licenses"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [enlive "1.0.0"]
                 [clj-http "0.6.4"]
                 [com.taoensso/carmine "1.4.0"]
                 [org.clojure/data.csv "0.1.2"]])

; Usage: (check-mykad "790222075569")
