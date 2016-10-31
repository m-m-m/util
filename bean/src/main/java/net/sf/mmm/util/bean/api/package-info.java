/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for generic java beans defined as simple interface. <a name="documentation"></a>
 * <h2>Bean API</h2><br>
 * Writing regular Java Beans is painful. You have to write a lot of boilerplate code and implement getters, setters,
 * equals, hashCode, and toString.<br>
 * The {@link net.sf.mmm.util.bean.api.Bean} API provided here saves you from all this pain and makes your life a lot
 * easier. All you need to do is to write an interface for your beans that derive from
 * {@link net.sf.mmm.util.bean.api.Bean} with {@link net.sf.mmm.util.property.api.WritableProperty property} access
 * methods. You can still define getters and setters if you like but there is no need to implement any method. For a
 * complete list of features or an code example see {@link net.sf.mmm.util.bean.api.Bean}. In order to create instances
 * of a custom {@link net.sf.mmm.util.bean.api.Bean} interface, you can simply use
 * {@link net.sf.mmm.util.bean.api.BeanFactory#create(Class)}. You may also add a static {@code create()} method in your
 * {@link net.sf.mmm.util.bean.api.Bean} interface to make the usage as simple as possible.
 */
package net.sf.mmm.util.bean.api;
