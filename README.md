# mykad-checker

MyKad Checker and utils.
An API wrapper for [SPR's Semakan Daftar Pemilih](http://daftarj.spr.gov.my/NEWDAFTARJ/DaftarjBM.aspx)
A clone of [Sinar's mykadspr](https://github.com/Sinar/mykadspr) in Clojure.

## Usage

```clojure
(require '[mykad-checker.core :as daftar])

(def rs (daftar/check-mykad "830222075563"))
(keys rs)

; Will either return nil if IC given is not in SPR database or the result as map 

(:jantina :status :loc :daerah :parli :dob :ic-no :nama :negeri :dun)

```



## License

Copyright © 2013 

Distributed under the Eclipse Public License, the same as Clojure.
=======


