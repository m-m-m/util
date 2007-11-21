/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.attribute;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoAttributeName {

  /**
   * This method gets the programmatic (technical) name of the according
   * property.<br>
   * E.g. for the {@link PojoPropertyAccessorMode#READ read} accessor
   * <code>public String getFooBar()</code> the property name would be
   * <code>fooBar</code>.
   * 
   * @see java.beans.PropertyDescriptor#getName()
   * 
   * @return the property name.
   */
  String getName();

}
