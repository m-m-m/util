/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.base.PojoDescriptorBuilderImpl;

/**
 * This is the implementation of the {@link PojoDescriptorBuilder} interface
 * that only considers public methods to find property-accessors (getters,
 * setters, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PublicMethodPojoDescriptorBuilder extends PojoDescriptorBuilderImpl {

  /**
   * The constructor. 
   */
  public PublicMethodPojoDescriptorBuilder() {

    super(new MethodPropertyIntrospector(true));
  }

}
