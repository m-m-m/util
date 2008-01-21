/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains scanners to parse character sequences efficient and easily.
 * <h2>Character Scanners</h2>
 * For efficient parsers of complex grammars it is best practice to use a parser
 * generator like <code>javacc</code> or <code>antlr</code>.<br>
 * However in some situations it is more suitable to write a handwritten parser.
 * The tradeoff is that this may result in ugly monolithic code that is hard to 
 * maintain.<br>
 * The {@link net.sf.mmm.util.scanner.CharStreamScanner} is an interface 
 * that covers typical tasks when paring strings or streams and therefore makes 
 * your life a lot easier. You can concentrate on the syntax you want to parse 
 * and do NOT need to repeat checks if the end is already reached all the time.
 * For parsing strings there is the implementation 
 * {@link net.sf.mmm.util.scanner.CharSequenceScanner} that bundles the 
 * string together with the state (parsing position) so you can easily delegate 
 * a step to another method or class. Otherwise you would need to pass the current 
 * position to that method and return the new one from there. This is tricky if 
 * the method should already return something else.<br>
 * <pre>
 * TODO: example without and with scanner.
 * </pre>
 */
package net.sf.mmm.util.scanner;

