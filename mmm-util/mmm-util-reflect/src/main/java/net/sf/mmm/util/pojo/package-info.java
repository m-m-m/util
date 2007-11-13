/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides utilities for reflecting on POJOs.
 * <h2>POJO Reflection Utilities</h2>
 * This POJO utility is an alternative to Java-Beans 
 * {@link java.beans.Introspector} or
 * <a href="http://commons.apache.org/commons-beanutils">commons-beanutils</a>.
 * The java beans specification is quite restrictive. For example the getter 
 * and setter of a property needs to have the exact same type (e.g. for 
 * <code>Integer getFoo()</code> and <code>void setFoo(int)</code> the property
 * <code>foo</code> can NOT be accessed via beanutils).<br>
 * As implied by POJO this utility does NOT have such restrictions. A POJO 
 * (plain old java object) in this manner is more or less any java object.
 * In advance boolean getters can also use the prefix <code>has</code>. Further 
 * a list-type property can also define an 
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessMode#ADD add-method} 
 * allowing to add items to the list.<br>
 */
package net.sf.mmm.util.pojo;

