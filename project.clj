(defproject cc.qbits/thorn "0.1.0"
  :description "Ηоⅿоɡⅼурһ ԁеτеϲτіоɴ аɴԁ аѕѕогτеԁ υτіⅼѕ"
  :url "https://github.com/mpenet/thorn"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [cheshire "5.8.0"]
                 [org.clojure/test.check "0.9.0"]]
  :source-paths ["src/clj"]
  :profiles {:dev
             {:plugins [[codox "0.10.3"]]}}
  :codox {:source-uri "https://github.com/mpenet/thorn/blob/master/{filepath}#L{line}"
          :output-path "docs"
          :metadata {:doc/format :markdown}}
  :global-vars {*warn-on-reflection* true})
