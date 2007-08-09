/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.Collection;
import java.util.List;

import net.sf.mmm.util.reflect.ClassResolver;

/**
 * This is the call-back interface used by the content-model to resolve the
 * field-types. Using the fully qualified name (e.g. <code>java.util.Date</code>)
 * for the type causes unnecessary typing and is especially bad for maintainance
 * (e.g. if the name or package of such type will be changed on refactoring, you
 * do NOT want to change this in various configuration files). Therefore a
 * logical name (e.g. <code>Date</code>) is used that can be
 * {@link #resolveClass(String) resolved} uniquely by this service.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueTypeService extends ClassResolver {

  /**
   * This method gets the type with the given logical <code>name</code>.<br>
   * 
   * @param name is the logical name of the requested type.
   * @return the class reflecting the type with the given <code>name</code> or
   *         <code>null</code> if no such type is registered.
   */
  Class getType(String name);

  /**
   * This method gets the names of the registered value-types.
   * 
   * @return the registered value types.
   */
  Collection<String> getTypeNames();

}
