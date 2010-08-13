# textmate-clojure

A TextMate bundle providing syntax highlighting for Clojure.

Based on a previous Clojure bundle by [`stephenroller`](http://github.com/stephenroller/clojure-tmbundle).

Install with:

    $ cd ~/Library/Application\ Support/TextMate/Bundles
    $ git clone git://github.com/swannodette/textmate-clojure.git Clojure.tmbundle
    $ osascript -e 'tell app "TextMate" to reload bundles'

## Cake Integration

This fork adds quite a few commands via [`cake`](http://github.com/ninjudd/cake) which makes the TextMate experience considerably more "Lispy".

First you need to install cake.

    sudo gem install cake
    
If you already have cake installed make sure you have at least version 0.3.8.

Once installed you can create a file and select the Clojure mode. You should first run <code>cake repl</code> at the command line before running any of the Clojure mode commands in order to avoid any weirdness. In general this is the recommended way of working. A quick tutorial follows in the next section.

The commands:

* Eval : will evaluate the selected code in the current REPL for the project directory.
* Eval pprint : like Eval but pretty-pprints the result.
* Load File : load the entire file into the current REPL.
* Show Source : shows the source of the selected function in a new window.
* Macroexpand : macroexpands the selected s-expression.
* Macroexpand all : fully macroexpands the selected s-expression.

## REPL Style Development

Coding in Lisp is a very interactive experience. Even if you are used to other languages that have good REPLs (Python, Ruby, Haskell), none are quite as interactive as a competent Lisp REPL.

First launch Terminal.app and run the following in a new window:

<pre class="console">
cake repl
</pre>

After a couple (or several) seconds you should get a REPL. If you've already run this command once before you should be dropped into a REPL immediately. Only the first time will be slow.

Create a new file and select the Clojure mode in TextMate. Type in the expression <code>(+ 4 5)</code>. Select this and run the Eval command (Command-Shift-X). You should get a new window with the number 9 in it.

## Tips

In order to get proper word movement in Clojure you might want to set your Word Characters to <code>_/-.:</code> in the Text Editing tab of the TextMate Preferences window.