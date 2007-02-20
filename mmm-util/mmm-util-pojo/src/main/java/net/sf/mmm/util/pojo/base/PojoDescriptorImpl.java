/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
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
      getOrCreateProperty(accessor.getName()).setAccessor(accessor);
    }
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getPojoType()
   */
  public Class<P> getPojoType() {

    return this.pojoType;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getPropertyDescriptor(java.lang.String)
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
   * This method gets the {@link AbstractPojoPropertyAccessor accessor} used to
   * read the property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property.
   * @return the accessor used to read the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no public
   *         read-{@link AbstractPojoPropertyAccessor#getAccessibleObject() method}
   *         exists for the property in the according
   *         {@link #getPojoType() POJO}.
   */
  public AbstractPojoPropertyAccessor getReadAccess(String propertyName) {

    AbstractPojoPropertyAccessor accessor = null;
    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor != null) {
      accessor = descriptor.getReadAccess();
    }
    return accessor;
  }

  /**
   * This method gets the {@link AbstractPojoPropertyAccessor accessor} used to
   * write the property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property.
   * @return the accessor used to write the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no public
   *         write-{@link AbstractPojoPropertyAccessor#getAccessibleObject() method}
   *         exists for the property in the according
   *         {@link #getPojoType() POJO}.
   */
  public AbstractPojoPropertyAccessor getWriteAccess(String propertyName) {

    AbstractPojoPropertyAccessor accessor = null;
    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor != null) {
      accessor = descriptor.getWriteAccess();
    }
    return accessor;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#getProperty(java.lang.Object,
   *      java.lang.String)
   */
  public Object getProperty(P pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    AbstractPojoPropertyAccessor readAccess = getReadAccess(propertyName);
    if (readAccess == null) {
      throw new PojoPropertyNotFoundException(getPojoType(), propertyName);
    }
    return readAccess.get(pojoInstance);
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoDescriptor#setProperty(java.lang.Object,
   *      java.lang.String, java.lang.Object)
   */
  public void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    AbstractPojoPropertyAccessor writeAccess = getWriteAccess(propertyName);
    if (writeAccess == null) {
      throw new PojoPropertyNotFoundException(getPojoType(), propertyName);
    }
    // TODO: maybe convert the type
    writeAccess.set(pojoInstance, value);
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
