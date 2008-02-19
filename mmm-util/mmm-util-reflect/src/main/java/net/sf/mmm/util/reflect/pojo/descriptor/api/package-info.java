/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for introspection of POJOs.
 * <h2>POJO Descriptor Utilities</h2>
 * POJO is a shortcut for Plain Old Java Object and more or less means any java 
 * object. While the java beans specification is generally a good idea to 
 * follow, it is sometimes too restrictive. E.g. you might want to name a 
 * boolean getter with the prefix "has" or want to have a primitive type as 
 * setter argument while the getter has the according object type.
 * If you use the 
 * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder}
 * instead of the {@link java.beans.Introspector} for java-beans or
 * <a href="http://commons.apache.org/commons-beanutils">commons-beanutils</a>,
 * you are NOT limited by such restrictions.<br>
 * <br>
 * Further 
 * a list-type property can also define an 
 * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#ADD add-method} 
 * allowing to add items to the list.<br>
 */
package net.sf.mmm.util.reflect.pojo.descriptor.api;

