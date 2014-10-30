/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains a temporary backport for functional interfaces from <code>java.util.function</code>.
 * <a name="documentation"></a><h2>Util Lang API Function</h2>
 * This package contains backports for the interfaces located in the package <code>java.util.function</code> that have
 * been introduced with Java 1.8. Initially it was our plan to use the proper interfaces directly and create a backport
 * with <code>mmm-util-backport-java.util.function</code>. <br>
 * However, it turned out that Java is so picky about its reserved <code>java</code> package that creating a backport for
 * earlier Java versions is technically possible but practically useless. All our users would need to add
 * <code>-Xbootclasspath</code> to all compilations and runtimes involved or need to "hack" their JVM. As this makes it
 * practically impossible to run the code in a predefined JEE environment we decided to go for "copies" in this package.
 * After Java 1.8 is widely established, we will derive these interfaces from the correct ones in
 * <code>java.util.function</code> and mark them as deprecated. In APIs that take such an interface we will replace it
 * with the correct one. Then users can migrate and remove the deprecations. In a later release we can then entirely
 * remove this package. <br>
 */
package net.sf.mmm.util.lang.api.function;

