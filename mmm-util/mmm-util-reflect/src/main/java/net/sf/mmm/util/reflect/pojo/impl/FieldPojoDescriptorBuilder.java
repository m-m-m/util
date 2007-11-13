/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import net.sf.mmm.util.reflect.pojo.base.PojoDescriptorBuilderImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder} interface that only
 * considers public methods to find property-accessors (getters, setters, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldPojoDescriptorBuilder extends PojoDescriptorBuilderImpl {

  /**
   * The constructor.
   */
  public FieldPojoDescriptorBuilder() {

    super(new FieldPropertyIntrospector());
  }

}
