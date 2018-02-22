(ns qbits.thorn
  (:refer-clojure :exclude [alias])
  (:require
   [clojure.spec.alpha :as s]
   [cheshire.core :as json]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defonce categories-data (json/parse-string (slurp (io/resource "categories.json"))))

(eval `(s/def ::alias ~(set (get categories-data "iso_15924_aliases"))))
(eval `(s/def ::category ~(set (get categories-data "categories"))))

(s/fdef alias+category
        :args (s/cat :chr char?)
        :ret (s/tuple ::alias ::category))
(defn alias+category
  "Retrieves the script block alias and unicode category for a unicode
  character."
  [chr]
  (let [code-points-ranges (get categories-data "code_points_ranges")
        c (int chr)]
  (loop [l 0
         r (dec (count code-points-ranges))]
    (if (>= r l)
      (let [m (+ l r)
            cp (get code-points-ranges m)]
        (cond
          (< c (first cp))
          (recur l (dec m))

          (> c (second cp))
          (recur (inc m) m)
          :else
          [(get-in categories-data ["iso_15924_aliases" (nth cp 2)])
           (get-in categories-data ["categories" (nth cp 3)])]))))))

(s/fdef alias
        :args (s/cat :chr char?)
        :ret ::alias)
(defn alias
  "Retrieves the script block alias for a unicode character."
  [chr]
  (first (alias+category chr)))

(s/fdef category
        :args (s/cat :chr char?)
        :ret ::category)
(defn category
  "Retrieves the unicode category for a unicode character."
  [chr]
  (second (alias+category chr)))

(s/fdef unique-category
        :args (s/cat :s string?)
        :ret (s/coll-of ::alias :kind set?))
(defn unique-aliases
  "Retrieves all unique script block aliases used in a unicode string."
  [s]
  (into #{} (map alias) s))


(s/fdef mixed-script?
        :args (s/cat :s string?
                     :allowed-aliases (s/? (s/coll-of ::alias :kind set?)))
        :ret boolean?)
(defn mixed-script?
  "Checks if ``s`` contains mixed-scripts content, excluding script
   blocks aliases in ``allowed_aliases``.
   E.g. ``B. C`` is not considered mixed-scripts by default: it contains characters
   from **Latin** and **Common**, but **Common** is excluded by default."
  ([s allowed-aliases]
   (->> s
        unique-aliases
        (remove allowed-aliases)
        count
        (< 1)))
  ([s]
   (mixed-script? s #{"COMMON"})))

;; confusables

(defonce confusables-data (json/parse-string (slurp (io/resource "confusables.json"))))

(s/fdef confusables
        :args (s/cat :s string?
                     :preferred-aliases (s/? (s/coll-of ::alias :kind set?)))
        :ret (s/nilable (s/coll-of ::confusable)))
(s/def ::confusable (s/keys :req-un [::character ::alias (or ::homoglyph
                                                             ::homoglyphs)]))
(s/def ::character char?)
(s/def ::homoglyph char?)
(s/def ::homoglyphs (s/coll-of (s/map-of #{"c" "n"} string?)))

(defn confusables
  ([s preferred-aliases]
   (-> (for [chr (distinct s)
             :let [a (alias chr)]
             :when (not (contains? preferred-aliases a))
             :let [found (get confusables-data (str chr))]
             d found
             glyph (get d "c")
             :let [a (alias glyph)]
             :when (contains? preferred-aliases a)]
         {:character chr
          :alias a
          :homoglyph glyph})
       not-empty))
  ([s]
   (-> (for [chr (distinct s)
             :let [a (alias chr)
                   found (get confusables-data (str chr))]]
         {:character chr
          :alias a
          :homoglyphs found})
       not-empty)))

(s/fdef dangerous?
        :args (s/cat :s string?
                     :preferred-aliases (s/? (s/coll-of ::alias :kind set?))))
(defn dangerous?
  "Checks if ``string`` can be dangerous, i.e. is it not only
   mixed-scripts but also contains characters from other scripts than
   the ones in ``preferred-aliases`` that might be confusable with
   characters from scripts in ``preferred-aliases``"
  ([s preferred-aliases]
   (and (mixed-script? s)
        (confusables s preferred-aliases)))
  ([s]
   (and (mixed-script? s)
        (confusables s))))
