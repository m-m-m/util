/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.util.Hashtable;
import java.util.Map;
// import java.util.WeakHashMap;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;

/**
 * This is the implementation of the {@link PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilder implements PojoDescriptorBuilder {

  /** @see #getDescriptor(Class) */
  private final Map<Class, PojoDescriptorImpl> pojoMap;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilder() {

    super();
    this.pojoMap = new Hashtable<Class, PojoDescriptorImpl>();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <P> PojoDescriptorImpl<P> getDescriptor(Class<P> pojoType) {

    PojoDescriptorImpl<P> descriptor = this.pojoMap.get(pojoType);
    if (descriptor == null) {
      descriptor = createDescriptor(pojoType);
      this.pojoMap.put(pojoType, descriptor);
    }
    return descriptor;
  }

  /**
   * This method creates the {@link PojoDescriptor pojo descriptor} for the
   * given <code>pojoType</code>.
   * 
   * @see net.sf.mmm.util.pojo.api.PojoDescriptorBuilder#getDescriptor(java.lang.Class)
   * 
   * @param
   * <P>
   * is the templated type of the <code>pojoType</code>.
   * @param pojoType is the type reflecting the POJO.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoType</code>.
   */
  protected abstract <P> PojoDescriptorImpl<P> createDescriptor(Class<P> pojoType);

}
