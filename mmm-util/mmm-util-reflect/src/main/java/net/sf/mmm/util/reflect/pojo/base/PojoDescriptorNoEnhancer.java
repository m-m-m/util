/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

/**
 * This is the simplest implementation of the {@link PojoDescriptorEnhancer}.
 * It does nothing!
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class PojoDescriptorNoEnhancer implements PojoDescriptorEnhancer {

  /** The singleton instance. */
  public static final PojoDescriptorNoEnhancer INSTANCE = new PojoDescriptorNoEnhancer();

  /**
   * The constructor.
   */
  private PojoDescriptorNoEnhancer() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void enhanceDescriptor(AbstractPojoDescriptor<?> descriptor) {

  }

}
