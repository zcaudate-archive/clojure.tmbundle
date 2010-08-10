# textmate-clojure

A TextMate bundle providing syntax highlighting for Clojure.

Based on a previous Clojure bundle by [`stephenroller`](http://github.com/stephenroller/clojure-tmbundle).

Install with:

    $ cd ~/Library/Application\ Support/TextMate/Bundles
    $ git clone git://github.com/mmcgrana/textmate-clojure.git Clojure.tmbundle
    $ osascript -e 'tell app "TextMate" to reload bundles'

## Cake Integration

This fork adds quite a few commands via [`cake`](http://github.com/ninjudd/cake.git) which make the TextMate
experience considerably more "Lispy".

First you need to install cake.

    sudo gem install cake

Once installed you can create new a file and select the Clojure Textmate mode. The very first time you run
a command it will probably take a couple of seconds. This is the JVM warming up. After that things should be
pleasantly fast thanks to cake's persistent VMs.
