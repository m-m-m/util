/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

/**
 * This is the generic implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorBuilderImpl extends AbstractPojoDescriptorBuilder {

  /** the introspector to use */
  private final PojoPropertyIntrospector introspector;

  /**
   * The constructor.
   * 
   * @param propertyIntrospector is the introspector used to find the
   *        property-accessors of a POJO.
   */
  public PojoDescriptorBuilderImpl(PojoPropertyIntrospector propertyIntrospector) {

    super();
    this.introspector = propertyIntrospector;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <P> PojoDescriptorImpl<P> createDescriptor(Class<P> pojoType) {

    return new PojoDescriptorImpl<P>(pojoType, this.introspector);
  }

}
