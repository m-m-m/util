/* $Id$ */
package net.sf.mmm.util.reflect.pojo.impl;

import java.util.Hashtable;
import java.util.Map;
// import java.util.WeakHashMap;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;

/**
 * This is the implementation of the {@link PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorBuilderImpl implements PojoDescriptorBuilder {

  /** @see #getDescriptor(Class) */
  private final Map<Class, PojoDescriptor> pojoMap;

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderImpl() {

    super();
    this.pojoMap = new Hashtable<Class, PojoDescriptor>();
  }

  /**
   * @see net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder#getDescriptor(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public <P> PojoDescriptor<P> getDescriptor(Class<P> pojoType) {

    PojoDescriptor<P> descriptor = this.pojoMap.get(pojoType);
    if (descriptor == null) {
      descriptor = new PojoDescriptorImpl<P>(pojoType);
      this.pojoMap.put(pojoType, descriptor);
    }
    return descriptor;
  }

}
