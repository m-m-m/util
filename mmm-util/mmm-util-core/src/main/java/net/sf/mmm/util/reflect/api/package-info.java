/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for objects that help to deal with reflection.
 * <h2>Reflection-Util API</h2>
 * This package provides the interface {@link net.sf.mmm.util.reflect.api.GenericType}
 * that allows simple access to the complex generic type-system introduced with 
 * Java5.<br>
 * Further the interface {@link net.sf.mmm.util.reflect.api.ClassResolver} allows
 * to retrieve {@link java.lang.Class}es by name in order to abstract from 
 * {@link java.lang.ClassLoader}s as well as to do name mappings (e.g. 
 * <code>Object</code> to <code>java.lang.Object</code>).
 */
package net.sf.mmm.util.reflect.api;

