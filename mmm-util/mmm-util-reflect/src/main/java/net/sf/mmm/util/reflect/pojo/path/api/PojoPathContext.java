/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.reflect.pojo.factory.api.PojoFactory;

/**
 * This is the interface of an object that carries the context for a
 * {@link PojoPathNavigator}. The context gives control and state to the caller
 * of the {@link PojoPathNavigator} allowing to be fast and thread-safe as well
 * as being extendable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathContext {

  /**
   * This method provides a cache that is used by the {@link PojoPathNavigator}
   * to speed up repetitive calls with the same initial
   * {@link net.sf.mmm.util.reflect.pojo.Pojo} and {@link PojoPath}s with a
   * common prefix.<br>
   * <b>ATTENTION:</b><br>
   * Never make assumptions about the content of this cache. It is provided here
   * to allow the {@link PojoPathNavigator} to be thread-safe and more efficient
   * and NOT to expose it for external usage. The internal structure of the
   * cache may change in future releases. Use the
   * {@link #getRecognizer() recognizer} to track visited
   * {@link net.sf.mmm.util.reflect.pojo.Pojo}s.
   * 
   * @see java.util.HashMap
   * @see java.util.WeakHashMap
   * 
   * @return a mutable {@link Map} to use as cache or <code>null</code> to
   *         disable caching.
   */
  Map<Object, Object> getCache();

  /**
   * This method gets the properties of the context. These can be accessed from
   * {@link PojoPathFunction}s for evaluation. Please note that conditional
   * evaluation can conflict with {@link #getCache() caching}. Ensure that such
   * {@link PojoPathFunction}s are declared to be NOT
   * {@link PojoPathFunction#isDeterministic() deterministic}.
   * 
   * @return the properties of this context.
   */
  Properties getProperties();

  /**
   * This method gets an optional {@link PojoPathRecognizer recognizer} that
   * adds support for the visitor-pattern. Therefore all POJOs traversed by the
   * {@link PojoPathNavigator} are
   * {@link PojoPathRecognizer#recognize(Object, PojoPath) recognized}
   * by the returned object.
   * 
   * @return the {@link PojoPathRecognizer} or <code>null</code> to turn this
   *         feature off.
   */
  PojoPathRecognizer getRecognizer();

  /**
   * This method gets an optional {@link PojoPathFunctionManager}.
   * {@link PojoPathFunction}s
   * {@link PojoPathFunctionManager#getFunction(String) provided} by this
   * manager overrules the {@link PojoPathFunction}s that may be provided by
   * the {@link PojoPathNavigator} itself. While {@link PojoPathFunction}s that
   * may be registered globally in the {@link PojoPathNavigator} should be
   * stateless and thread-safe, the {@link PojoPathFunction}s provided here may
   * be stateful depending on the usage of this context.
   * 
   * @return the {@link PojoPathFunctionManager} of this context or
   *         <code>null</code> if no context-specific {@link PojoPathFunction}s
   *         should be provided.
   */
  PojoPathFunctionManager getAdditionalFunctionManager();

  /**
   * This method gets the {@link PojoFactory} instance used to
   * {@link PojoPathMode#CREATE_IF_NULL create} new
   * {@link net.sf.mmm.util.reflect.pojo.Pojo}s.
   * 
   * @return the {@link PojoFactory}.
   */
  PojoFactory getPojoFactory();

}
