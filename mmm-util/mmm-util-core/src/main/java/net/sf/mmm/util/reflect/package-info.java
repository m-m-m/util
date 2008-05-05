/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides utilities for dealing with reflection.
 * <h2>Reflection Utilities</h2>
 * Java reflection is a powerful mechanism that allows flexible and generic 
 * programming. With Java5 a generic type-system was introduced. However this
 * is quite a wild animal and therefore hard to deal with via the reflection 
 * API. Have a look at the following example:
 * <verbatim>
 * public class Foo<A, B> {
 *   A getA() { ... }
 *   B getB() { ... }
 * }
 * public class Bar<X> extends Foo<X, String> {
 *   ...
 * }
 * public class Some extends Bar<Long> {
 *   ...
 * } 
 * </verbatim>
 * If you want to determine the type of <code>Some.getA()</code> reflectively, 
 * you will have to dive into the deepest and trickiest part of the reflection
 * API and might step into one of the many pitfalls on this way. Or you simply
 * use this util.<br>
 * This project provides utilities that make reflection a lot easier. It also
 * offers features that you may not even know they where possible such as 
 * finding all classes located in the classpath or a specific package.<br>
 * Therefore {@link net.sf.mmm.util.reflect.ReflectionUtil} will help you to 
 * deal with reflection. For advanced support when reading annotations have a 
 * look at {@link net.sf.mmm.util.reflect.AnnotationUtil}.
 */
package net.sf.mmm.util.reflect;

