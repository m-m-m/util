/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to build
 * command-line-interfaces (CLI).
 * <a name="documentation"/><h2>CLI API</h2>
 * This package provides the API to create a command-line interface (CLI) for a main program
 * easily.
 * <h3>The Problem</h3>
 * Typical java-applications start with a main-class - a class with a method
 * with this magic signature:
 * <pre>public static void main(String[] args)</pre>
 * However you quickly notice that this was NOT the end of wisdom when designing
 * an object-oriented language as java. A main-program often starts easy and
 * then over the time options and arguments are added. When you want to write
 * a maintainable main-program you want to have more infrastructure than just
 * having a string-array lost in a static method.<br>
 * <h3>The Solution</h3>
 * As a solution we provide {@link net.sf.mmm.util.cli.api.AbstractMain} and
 * {@link net.sf.mmm.util.cli.api.AbstractVersionedMain} that you can extend to build your main program.
 * For each {@link net.sf.mmm.util.cli.api.CliOption option} and
 * {@link net.sf.mmm.util.cli.api.CliArgument argument} there are {@link java.lang.annotation.Annotation}s to annotate
 * your regular {@link java.lang.reflect.Field}s. Via {@link net.sf.mmm.util.cli.api.CliMode} multiple modes can be
 * defined to realize complex scenarios. Everything integrates with <code>javax.validation</code> (BV) and our excellent
 * {@link net.sf.mmm.util.nls.api.NlsMessage native-language-support} including
 * {@link net.sf.mmm.util.text.api.LineWrapper text wrapping} and
 * {@link net.sf.mmm.util.text.api.Hyphenator hyphenation}. So you do not have to maintain any usage or help output that
 * is all done for you.
 * <h3>Example</h3>
 * Here you can see a simple example to get started:
 * <pre>
 * {@literal @}{@link net.sf.mmm.util.cli.api.CliMode}(id = {@link net.sf.mmm.util.cli.api.CliMode#ID_DEFAULT}, usage = NlsBundleMyExampleRoot.MSG_MAIN_MODE_USAGE_DEFAULT)
 * public class MyCoolMain extends {@link net.sf.mmm.util.cli.api.AbstractVersionedMain} {
 *
 *   {@literal @}{@link net.sf.mmm.util.cli.api.CliOption}(name = "--flag", aliases = "-f", usage = NlsBundleMyExampleRoot.MSG_MAIN_OPTION_FLAG)
 *   private boolean flag;
 *
 *   {@literal @}{@link java.lang.Override}
 *   public int {@link net.sf.mmm.util.cli.api.AbstractVersionedMain#runDefaultMode() runDefaultMode()} {
 *     if (this.flag) {
 *       // ... do this ...
 *     } else {
 *       // ... do that ...
 *     }
 *   }
 *
 *   public static void main(String[] args) {
 *     new MyCoolMain().{@link #runAndExit(String...) runAndExit}(args);
 *   }
 * }
 * </pre>
 *
 *
 * <h3>History</h3>
 * After using <a href="http://jakarta.apache.org/commons/cli">commons-cli</a>,
 * gnu-getopts and finally <a href="http://args4j.dev.java.net/">args4j</a>,
 * we created this library that brings the best ideas together (with most from args4j).
 * <br>
 * In the context of CLI we use the term <em>parameter</em> for a string given
 * on the commandline (out of <code>String[] args</code>). Such parameter is
 * either an <em>{@link net.sf.mmm.util.cli.api.CliOption option}</em> or a
 * <em>value</em>. A value can belong to an {@link net.sf.mmm.util.cli.api.CliOption option}
 * or an <em>{@link net.sf.mmm.util.cli.api.CliArgument argument}</em>.
 * E.g. the parameters <code>--port 8080 start</code> consist of the option <code>--port</code>
 * with its value <code>8080</code> and the argument <code>start</code>.
 */
package net.sf.mmm.util.cli.api;

