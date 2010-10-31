/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a manager of {@link PojoPathFunction}s.<br>
 * Typically a {@link PojoPathNavigator} instance will have an
 * {@link PojoPathFunctionManager} to {@link #getFunction(String) resolve}
 * {@link PojoPathFunction}s. Further the {@link PojoPathContext} can
 * {@link PojoPathContext#getAdditionalFunctionManager() provide additional
 * functions} that are used prior.
 * 
 * @see #getFunction(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@ComponentSpecification
public interface PojoPathFunctionManager {

  /**
   * This method gets a {@link PojoPathFunction} by its name. Therefore the
   * {@link PojoPathFunction} instance has to be registered somehow in the
   * implementation of {@link PojoPathFunctionManager this} interface.
   * 
   * @param functionName is the name of the requested {@link PojoPathFunction}
   *        excluding the {@link PojoPathFunction#FUNCTION_NAME_PREFIX prefix}.
   * @return the requested {@link PojoPathFunction} or <code>null</code> if no
   *         {@link PojoPathFunction} is registered for the given
   *         <code>functionName</code>.
   */
  @SuppressWarnings("rawtypes")
  PojoPathFunction getFunction(String functionName);

}
