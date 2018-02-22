(ns qbits.thorn.test.core-test
  (:refer-clojure :exclude [alias])
  (:require
   [clojure.test :refer :all]
   [clojure.spec.test.alpha :as t]
   [qbits.thorn :refer :all]))

(deftest aliases-cat
  (is (= (alias+category \τ)
         ["GREEK", "L"]))
  (is (= (alias+category \A)
         ["LATIN", "L"]))
  (is (= (alias+category \-)
         ["COMMON", "Pd"])))

(deftest mixed-test
  (is (-> (confusables "paρa" #{"LATIN"})
          first
          :character
          (= \ρ)))
  (is (-> (confusables "paρa" #{"GREEK"})
          first
          :character
          (= \p)))

  (is (not (confusables "Abç" #{"LATIN"})))
  (is (not (confusables "AlloΓ" #{"LATIN"})))
  (is (not (confusables "ρττ" #{"GREEK"})))
  (is (not (confusables "ρτ.τ" #{"GREEK" "COMMON"})))
  (is (= (first (confusables "ρττp"))
         {:character \ρ
          :alias "GREEK"
          :homoglyphs [{"c" "p", "n" "LATIN SMALL LETTER P"}]})))

(deftest dangerous-test
  (is (not (dangerous? "Allo")))
  (is (not (dangerous? "AlloΓ" #{"LATIN"})))
  (is (dangerous? "Alloρ" ))
  (is (not (dangerous? "AlaskaJazz")))
  (is (dangerous? "ΑlaskaJazz")))

(let [syms (t/enumerate-namespace 'qbits.thorn)]
  (prn "Instrumented symbols" (t/instrument syms))
  (prn (t/summarize-results
        (t/check syms  {:clojure.spec.test.check/opts {:num-tests 200}}))))
