/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for scanners that help to parse character sequences 
 * efficient and easily.
 * <a name="documentation"/><h2>Scanner API</h2>
 * For efficient parsers of complex grammars it is best practice to use a parser
 * generator like <code>javacc</code> or <code>antlr</code>.<br>
 * However in some situations it is more suitable to write a handwritten parser.
 * The tradeoff is that this may result in ugly monolithic code that is hard to 
 * maintain.<br>
 * The {@link net.sf.mmm.util.scanner.api.CharStreamScanner} is an interface 
 * that covers typical tasks when paring strings or streams and therefore makes 
 * your life a lot easier. You can concentrate on the syntax you want to parse 
 * and do NOT need to repeat checks if the end is already reached all the time.
 * For parsing strings there is the implementation 
 * {@link net.sf.mmm.util.scanner.base.CharSequenceScanner} that bundles the 
 * string together with the state (parsing position) so you can easily delegate 
 * a step to another method or class. Otherwise you would need to pass the current 
 * position to that method and return the new one from there. This is tricky if 
 * the method should already return something else.<br>
 * Here is a little example of an entirely handwritten parser:
 * <pre>
 * String input = getInputString();
 * int i = 0;
 * boolean colonFound = false;
 * while (i < input.length()) {
 *   char c = input.charAt(i++);
 *   if (c == ':') {
 *     colonFound = true;
 *     break;
 *   }
 * }
 * if (!colonFound) {
 *   throw new IllegalArgumentException("Expected character ':' not found!");
 * }
 * String key = input.substring(0, i - 1);
 * String value = null;
 * if (i < input.length()) {
 *   while ((i < input.length()) && (input.charAt(i) == ' ')) {
 *     i++;
 *   }
 *   int start = i;
 *   while (i < input.length()) {
 *     char c = input.charAt(i);
 *     if ((c < '0') || (c > '9')) {
 *       break;
 *     }
 *     i++;
 *   }
 *   value = input.substring(start, i);
 * }
 * </pre>
 * Here is the same thing when using {@link net.sf.mmm.util.scanner.base.CharSequenceScanner}:
 * <pre>
 * String input = getInputString();
 * {@link net.sf.mmm.util.scanner.api.CharStreamScanner} scanner = new {@link net.sf.mmm.util.scanner.base.CharSequenceScanner}(input);
 * String key = scanner.{@link net.sf.mmm.util.scanner.api.CharStreamScanner#readUntil(char, boolean) readUntil}(':', false);
 * if (key == null) {
 *   throw new IllegalArgumentException("Expected character ':' not found!");
 * }
 * scanner.{@link net.sf.mmm.util.scanner.api.CharStreamScanner#skipWhile(char) skipWhile}(' ');
 * String value = scanner.{@link net.sf.mmm.util.scanner.api.CharStreamScanner#readWhile(net.sf.mmm.util.filter.api.CharFilter) 
 * readWhile}({@link net.sf.mmm.util.filter.api.CharFilter#LATIN_DIGIT_FILTER});
 * </pre>
 */
package net.sf.mmm.util.scanner.api;

