# Τһогɴ

[![Build Status](https://travis-ci.org/mpenet/thorn.svg?branch=master)](https://travis-ci.org/mpenet/thorn)

This is a direct port of the work of [@vhf](https://github.com/vhf) on
[https://github.com/vhf/confusable_homoglyphs](https://github.com/vhf/confusable_homoglyphs)
to clojure .

*a homoglyph is one of two or more graphemes, characters, or glyphs with
shapes that appear identical or very similar*
[wikipedia:Homoglyph](https://en.wikipedia.org/wiki/Homoglyph)

Unicode homoglyphs can be a nuisance on the web. Your most popular
client, AlaskaJazz, might be upset to be impersonated by a trickster
who deliberately chose the username ΑlaskaJazz.

- `AlaskaJazz` is single script: only Latin characters.
- `ΑlaskaJazz` is mixed-script: the first character is a greek letter.

You might also want to avoid people being tricked into entering their
password on `www.micros﻿оft.com` or `www.faϲebook.com` instead of
`www.microsoft.com` or `www.facebook.com`. [Here is a
utility](http://unicode.org/cldr/utility/confusables.jsp) to play with
these **confusable homoglyphs**.

Not all mixed-script strings have to be ruled out though, you could
only exclude mixed-script strings containing characters that might be
confused with a character from some unicode blocks of your choosing.

-  `Allo` and `ρττ` are fine: single script.
-  `AlloΓ` is fine when our preferred script alias is 'latin': mixed script, but `Γ` is not confusable.
-  `Alloρ` is dangerous: mixed script and `ρ` could be confused with
   `p`.

## Documentation

[codox generated documentation](https://mpenet.github.io/thorn/qbits.thorn.html).

The
[tests](https://github.com/mpenet/thorn/blob/master/test/qbits/thorn/test/core_test.clj)
might help you getting started.

## Installation

thorn is [available on Clojars](https://clojars.org/cc.qbits/thorn).

Add this to your dependencies:

[![Clojars Project](https://img.shields.io/clojars/v/cc.qbits/thorn.svg)](https://clojars.org/cc.qbits/thorn)


## License

Distributed under the
[Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html),
the same as Clojure.

Port of https://github.com/vhf/confusable_homoglyphs which is
[MIT-licensed](https://github.com/vhf/confusable_homoglyphs/blob/master/LICENSE)
