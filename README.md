# mykad-checker

MyKad Checker and utils.
A clone of [Sinar's mykadspr](https://github.com/Sinar/mykadspr) in Clojure.

## Usage

```clojure
(require '[mykad-checker.core :as kad])

(def rs (kad/check-mykad "830222075563"))
(keys rs)

; Will either return nil is IC given is not in SPR database map of 
; (:jantina :status :loc :daerah :parli :dob :ic-no :nama :negeri :dun)

(:jantina :status :loc :daerah :parli :dob :ic-no :nama :negeri :dun)

```



## License

Copyright © 2013 

Distributed under the Eclipse Public License, the same as Clojure.
=======


