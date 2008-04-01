/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains dummy implementations of the Java generic 
 * {@link java.lang.reflect.Type} interfaces.
 * <h2>Reflection Types</h2>
 * The Java generics introduced a fine grained type-system. It makes generic 
 * types available via the interface {@link java.lang.reflect.Type} and its 
 * sub-interfaces. Unlike {@link java.lang.Class#forName(String)} there is no 
 * build in way to create a generic {@link java.lang.reflect.Type} from a given 
 * string. This lack is covered by 
 * {@link net.sf.mmm.util.reflect.ReflectionUtil#toType(String)} that uses the 
 * dummy type-implementations of this package.
 */
package net.sf.mmm.util.reflect.type;

