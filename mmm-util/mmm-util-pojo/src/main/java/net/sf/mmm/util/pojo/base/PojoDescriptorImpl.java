/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyNotFoundException;

/**
 * This is the abstract base implementation of the {@link PojoDescriptor}
 * interface.
 * 
 * @param
 * <P>
 * is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorImpl<P> implements PojoDescriptor<P> {

  /** @see #getPojoType() */
  private final Class<P> pojoType;

  /** @see #getPropertyDescriptor(String) */
  private final Map<String, PojoPropertyDescriptorImpl> propertyMap;

  /** @see #getPropertyDescriptor(String) */
  private final Collection<PojoPropertyDescriptorImpl> propertyList;

  /**
   * The constructor
   * 
   * @param pojoClass
   *        is the {@link #getPojoType() pojo-class}.
   * @param introspector
   *        is used to find the accessors for the given <code>pojoClass</code>.
   */
  public PojoDescriptorImpl(Class<P> pojoClass, PojoPropertyIntrospector introspector) {

    this.pojoType = pojoClass;
    this.propertyMap = new HashMap<String, PojoPropertyDescriptorImpl>();
    this.propertyList = Collections.unmodifiableCollection(this.propertyMap.values());
    Iterator<AbstractPojoPropertyAccessor> accessorIterator = introspector.findAccessors(pojoClass);
    while (accessorIterator.hasNext()) {
      AbstractPojoPropertyAccessor accessor = accessorIterator.next();
      PojoPropertyDescriptorImpl descriptor = getOrCreateProperty(accessor.getName());
      if (descriptor.getAccessor(accessor.getAccessMode()) == null) {
        descriptor.setAccessor(accessor);        
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Class<P> getPojoType() {

    return this.pojoType;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyDescriptorImpl getPropertyDescriptor(String propertyName) {

    return this.propertyMap.get(propertyName);
  }

  /**
   * This method gets the property-descriptor for the given
   * <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property-descriptor.
   * @return the requested property-descriptor or <code>null</code> if NO
   *         property exists with the given <code>propertyName</code>.
   */
  protected PojoPropertyDescriptorImpl getOrCreateProperty(String propertyName) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      descriptor = new PojoPropertyDescriptorImpl(propertyName);
      this.propertyMap.put(propertyName, descriptor);
    }
    return descriptor;
  }

  /**
   * This method gets the {@link AbstractPojoPropertyAccessor accessor} for the
   * property identified by the given <code>propertyName</code> and the given
   * access <code>mode</code>.
   * 
   * @param propertyName
   *        is the name of the property.
   * @param mode
   *        identifies the way how to access the specified property.
   * @return the accessor for the property identified by the given
   *         <code>propertyName</code> using the given <code>mode</code>.
   * @throws PojoPropertyNotFoundException
   *         if the requested property-accessor does NOT exist.
   */
  public AbstractPojoPropertyAccessor getAccessor(String propertyName, PojoPropertyAccessMode mode)
      throws PojoPropertyNotFoundException {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      throw new PojoPropertyNotFoundException(this.pojoType, propertyName);
    }
    AbstractPojoPropertyAccessor accessor = descriptor.getAccessor(mode);
    if (accessor == null) {
      throw new PojoPropertyNotFoundException(this.pojoType, propertyName, mode);
    }
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  public Object getProperty(P pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessMode.READ).get(pojoInstance);
  }

  /**
   * {@inheritDoc}
   */
  public void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    // TODO: maybe convert the type
    getAccessor(propertyName, PojoPropertyAccessMode.WRITE).set(pojoInstance, value);
  }

  /**
   * {@inheritDoc}
   */
  public void addPropertyItem(P pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    getAccessor(propertyName, PojoPropertyAccessMode.ADD).set(pojoInstance, item);
  }

  /**
   * This method gets the {@link PojoPropertyDescriptorImpl descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptorImpl property descriptor}s
   */
  public Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors() {

    return this.propertyList;
  }
}
