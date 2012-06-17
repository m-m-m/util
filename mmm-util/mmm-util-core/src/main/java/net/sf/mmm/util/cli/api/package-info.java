/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to build
 * command-line-interfaces (CLI).
 * <a name="documentation"/><h2>CLI API</h2>
 * Typical java-applications start with a main-class - a class with a method 
 * with this magic signature:
 * <pre>public static void main(String[] args)</pre>
 * However you quickly notice that this was NOT the end of wisdom when designing
 * an object-oriented language as java. A main-program often starts easy and 
 * then over the time options and arguments are added. When you want to write 
 * a maintainable main-program you want to have more infrastructure than just 
 * having a string-array lost in a static method.<br>
 * I used <a href="http://jakarta.apache.org/commons/cli">commons-cli</a> and 
 * gnu-getopts and then found <a href="http://args4j.dev.java.net/">args4j</a>.
 * While I loved the idea of args4j from the first moment, I was missing some 
 * features and as I found no way to integrate the wonderful 
 * {@link net.sf.mmm.util.nls.api.NlsMessage native-language-support} of mmm, I 
 * decided to rip the idea and simply re-implement it with all the features I 
 * was missing.<br>
 * Now if you want to create a main-program, the easiest way is to extend
 * {@link net.sf.mmm.util.cli.api.AbstractMain}.<br>
 * <br>
 * In the context of CLI we use the term <em>parameter</em> for a string given
 * on the commandline (out of <code>String[] args</code>). Such parameter is 
 * either an <em>{@link net.sf.mmm.util.cli.api.CliOption option}</em> or a 
 * <em>value</em>. A value can belong to an {@link net.sf.mmm.util.cli.api.CliOption option}
 * or an <em>{@link net.sf.mmm.util.cli.api.CliArgument argument}</em>.
 */
package net.sf.mmm.util.cli.api;

