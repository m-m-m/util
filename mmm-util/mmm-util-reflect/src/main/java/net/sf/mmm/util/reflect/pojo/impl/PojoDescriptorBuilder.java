/* $Id$ */
package net.sf.mmm.util.reflect.pojo.impl;

import java.util.Hashtable;
import java.util.Map;
// import java.util.WeakHashMap;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilderIF;
import net.sf.mmm.util.reflect.pojo.api.PojoDescriptorIF;

/**
 * This is the implementation of the {@link PojoDescriptorBuilderIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorBuilder implements PojoDescriptorBuilderIF {

  /** @see #getDescriptor(Class) */
  private final Map<Class, PojoDescriptorIF> pojoMap;

  /**
   * The constructor.
   */
  public PojoDescriptorBuilder() {

    super();
    this.pojoMap = new Hashtable<Class, PojoDescriptorIF>();
  }

  /**
   * @see net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilderIF#getDescriptor(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public <P> PojoDescriptorIF<P> getDescriptor(Class<P> pojoType) {

    PojoDescriptorIF<P> descriptor = this.pojoMap.get(pojoType);
    if (descriptor == null) {
      descriptor = new PojoDescriptor<P>(pojoType);
      this.pojoMap.put(pojoType, descriptor);
    }
    return descriptor;
  }

}
