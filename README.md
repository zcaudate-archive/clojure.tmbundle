# textmate-clojure

A TextMate bundle providing syntax highlighting for Clojure.

Based on a previous Clojure bundle by [`stephenroller`](http://github.com/stephenroller/clojure-tmbundle).

Install with:

    $ cd ~/Library/Application\ Support/TextMate/Bundles
    $ git clone git://github.com/mmcgrana/textmate-clojure.git Clojure.tmbundle
    $ osascript -e 'tell app "TextMate" to reload bundles'

## Cake Integration

This fork adds quite a few commands via [`cake`](http://github.com/ninjudd/cake.git) which make the TextMate experience considerably more "Lispy".

First you need to install cake.

    sudo gem install cake

Once installed you can create a file and select the Clojure mode. It's recommended (though not necessary) that you run <code>cake repl</code> at the command line before running the Clojure mode commands. The cake REPL has excellent autocompletion features and it's handy and fun to move back and forth between your source file and interacting at the REPL - welcome to the power of Lisp! :)

The following commands are available:

* Eval : will evaluate the selected code in the current REPL for the project directory.
* Eval pprint : like Eval but pretty-pprints the result.
* Load File : load the entire file into the current REPL.
* Show Source : shows the source of the selected function in a new window.
* Macroexpand : macroexpands the selected s-expression.
* Macroexpand all : fully macroexpands the selected s-expression.
* Jump to definition : jump to the source file where the selected var is defined.

Note that if you didn't start a REPL first at the command line the very first time you run any cake command it will probably take a couple of seconds. This is the JVM warming up. After that things should be very, very fast thanks to cake's persistent VMs.