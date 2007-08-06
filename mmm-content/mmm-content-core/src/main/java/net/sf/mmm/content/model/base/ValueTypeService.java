/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

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
   * This method gets the names of the value-types 
   * 
   * @return
   */
  List<String> getTypeNames();
  
}
