/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to deal with reflection.
 * <a name="documentation"/><h2>Reflection-Util API</h2>
 * Java reflection is a powerful mechanism that allows flexible and generic 
 * programming. With Java5 a generic type-system was introduced. However this
 * is quite a wild animal and this way hard to deal with via the reflection 
 * API. Therefore this package provides the interface 
 * {@link net.sf.mmm.util.reflect.api.GenericType} that allows simple access 
 * to the complex generic type-system.<br>
 * You will also find utilities that make reflection a lot easier and may
 * offer features that you may not even know they where possible such as 
 * finding all classes located in the classpath or a specific package.<br>
 * Therefore {@link net.sf.mmm.util.reflect.api.ReflectionUtil} will help you to 
 * deal with reflection. For advanced support when reading annotations have a 
 * look at {@link net.sf.mmm.util.reflect.api.AnnotationUtil}. Additionally
 * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil} offers 
 * reflective and generic operations on {@link java.util.Collection}s.<br>
 * Further the interface {@link net.sf.mmm.util.reflect.api.ClassResolver} allows
 * to retrieve {@link java.lang.Class}es by name in order to abstract from 
 * {@link java.lang.ClassLoader}s as well as to do name mappings (e.g. 
 * <code>Object</code> to <code>java.lang.Object</code>).
 */
package net.sf.mmm.util.reflect.api;

