/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import net.sf.mmm.util.pojo.base.PojoDescriptorBuilderImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.api.PojoDescriptorBuilder} interface that
 * considers all methods to find property-accessors (getters, setters, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodPojoDescriptorBuilder extends PojoDescriptorBuilderImpl {

  /**
   * The constructor.
   */
  public MethodPojoDescriptorBuilder() {

    super(new MethodPropertyIntrospector(false));
  }

}
