/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains a back-port of the according package from java 1.8+ for older java versions.
 * <a name="documentation"/><h2>Back-port of java.util.function</h2>
 * This package contains java 1.5 compatible code to back-port the <code>java.util.function</code>
 * package for older java versions. This allows to make use of functional interfaces in your code without strictly
 * requiring the users of your code to use java 1.8+. Of course this only works when using the functional interfaces
 * in classical java style without using closures / lambda expressions. However, then your code opens the gate for the
 * java 1.8 world and other code can use advanced java 1.8 features.<br/>
 * <b>ATTENTION:</b><br/>
 * Regular {@link java.lang.ClassLoader}s in java prevent loading classes in package-namespace <code>java.*</code> for
 * reasonable purpose. Therefore, you can not simply add this back-port as JAR to your classpath. This would result in
 * the following error:
 * <pre>
 * java.lang.SecurityException: Prohibited package name: java.util.function
 * </pre>
 * To solve this problem there are the following options:
 * <ul>
 *   <li>Use the JVM option <code>-Xbootclasspath/p:/path/to/mmm-util-backport-java.util.function-1.0.0.jar</code> (recommended)</li>
 *   <li>Use the JVM option <code>-Djava.endorsed.dirs=/path/to/directory</code> with a directory containing <code>mmm-util-backport-java.util.function-1.0.0.jar</code>.</li>
 *   <li>Add <code>mmm-util-backport-java.util.function-1.0.0.jar</code> to the <code>$JAVA_HOME/lib/endorsed</code> (not recommended).</li>
 * </ul>
 */
package java.util.function;

