# textmate-clojure

A TextMate bundle providing syntax highlighting for Clojure.

Based on a previous Clojure bundle by [`stephenroller`](http://github.com/stephenroller/clojure-tmbundle).

Install with:

    $ cd ~/Library/Application\ Support/TextMate/Bundles
    $ git clone git://github.com/swannodette/textmate-clojure.git Clojure.tmbundle
    $ osascript -e 'tell app "TextMate" to reload bundles'

## Cake Integration

This fork adds quite a few commands via [`cake`](http://github.com/ninjudd/cake.git) which make the TextMate experience considerably more "Lispy".

First you need to install cake.

    sudo gem install cake
    
If you already have cake installed make sure you have at least version 0.3.8.

Once installed you can create a file and select the Clojure mode. You should first run <code>cake repl</code> at the command line before running any of the Clojure mode commands in order to avoid any weirdness. In general this is the recommended way of working. A quick tutorial follows.

The following commands are available:

* Eval : will evaluate the selected code in the current REPL for the project directory.
* Eval pprint : like Eval but pretty-pprints the result.
* Load File : load the entire file into the current REPL.
* Show Source : shows the source of the selected function in a new window.
* Macroexpand : macroexpands the selected s-expression.
* Macroexpand all : fully macroexpands the selected s-expression.

Note that if you didn't start a REPL first at the command line the very first time you run any cake command it will probably take a couple of seconds. This is the JVM warming up. After that things should be very, very fast thanks to cake's persistent VMs.

## REPL Style Development

Coding in Lisp is a very interactive experience. Even if you're used to other languages that have good REPLs (Python, Ruby, Haskell), none are quite as interactive as a competent Lisp REPL.

First launch Terminal.app and run the following in a new window:

<pre class="console">
cake repl
</pre>

After a couple of seconds you should get a REPL. If you already ran this command once before you should be dropped into a REPL immediately.

Create a new file and select the Clojure mode. Type in the expression <code>(4 + 5)</code>. Select this and run the Eval command (Shift-Command-X). You should get a new window with the number 9 in it.