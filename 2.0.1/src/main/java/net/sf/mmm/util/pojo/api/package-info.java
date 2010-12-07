/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for dealing with {@link net.sf.mmm.util.pojo.api.Pojo}s.
 * <a name="documentation"/><h2>POJO API</h2>
 * POJO is a shortcut for Plain Old Java Object and more or less means any java 
 * object. While the java beans specification is generally a good idea to 
 * follow, it is sometimes too restrictive. E.g. you might want to name a 
 * boolean getter with the prefix "has" or want to have a primitive type as 
 * setter argument while the getter has the according object type.
 * Using this utilities you are NOT limited by such restrictions.<br>
 * Further the generic type-system introduced with Java 5 is quite complex and
 * NOT properly supported by existing bean-utilities (see 
 * {@link net.sf.mmm.util.reflect.api.GenericType} for further details).
 * <br>
 * Instead of the {@link java.beans.Introspector} for java-beans or
 * <a href="http://commons.apache.org/beanutils">commons-beanutils</a>,
 * you can use the advanced introspection offered by
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}.<br>
 * Further there is support to traverse a complete object-web spanned by an 
 * initial {@link net.sf.mmm.util.pojo.api.Pojo} based on a 
 * {@link net.sf.mmm.util.pojo.path.api.PojoPath} (similar to XPath for 
 * XML) using the 
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator}.<br>
 * A simple way to create new instances of 
 * {@link net.sf.mmm.util.pojo.api.Pojo}s is offered by the 
 * {@link net.sf.mmm.util.pojo.api.PojoFactory}.
 */
package net.sf.mmm.util.pojo.api;

