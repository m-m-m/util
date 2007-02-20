/* $Id$ */
package net.sf.mmm.util.pojo.base;

import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;

/**
 * This is the implementation of the {@link PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorBuilderImpl extends AbstractPojoDescriptorBuilder {

  /** the introspector to use */
  private final PojoPropertyIntrospector introspector;

  /**
   * The constructor.
   * 
   * @param propertyIntrospector
   *        is the introspector used to find the property-accessors of a POJO.
   */
  public PojoDescriptorBuilderImpl(PojoPropertyIntrospector propertyIntrospector) {

    super();
    this.introspector = propertyIntrospector;
  }

  /**
   * @see net.sf.mmm.util.pojo.base.AbstractPojoDescriptorBuilder#createDescriptor(java.lang.Class)
   */
  @Override
  protected <P> PojoDescriptorImpl<P> createDescriptor(Class<P> pojoType) {

    return new PojoDescriptorImpl<P>(pojoType, this.introspector);
  }

}
