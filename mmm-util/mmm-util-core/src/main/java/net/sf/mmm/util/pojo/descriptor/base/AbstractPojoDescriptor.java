/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.util.Collection;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoPropertyDescriptorImpl;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is the abstract base implementation of the {@link PojoDescriptor} interface.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoClass() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoDescriptor<POJO> implements PojoDescriptor<POJO> {

  /** @see #getPojoDescriptorBuilder() */
  private final PojoDescriptorBuilder pojoDescriptorBuilder;

  /** @see #getPojoType() */
  private final GenericType<POJO> pojoType;

  /**
   * The constructor.
   * 
   * @param pojoType is the {@link #getPojoType() pojo-type}.
   * @param pojoDescriptorBuilder is the {@link PojoDescriptorBuilder}.
   */
  public AbstractPojoDescriptor(GenericType<POJO> pojoType, PojoDescriptorBuilder pojoDescriptorBuilder) {

    super();
    this.pojoType = pojoType;
    this.pojoDescriptorBuilder = pojoDescriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<POJO> getPojoClass() {

    return this.pojoType.getRetrievalClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<POJO> getPojoType() {

    return this.pojoType;
  }

  /**
   * @return the {@link PojoDescriptorBuilder} instance.
   */
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    return this.pojoDescriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Collection<? extends AbstractPojoPropertyDescriptor> getPropertyDescriptors();

  /**
   * This method gets the property-descriptor for the given <code>propertyName</code>.
   * 
   * @param propertyName is the name of the requested property-descriptor.
   * @return the requested property-descriptor or <code>null</code> if NO property exists with the given
   *         <code>propertyName</code>.
   */
  public abstract PojoPropertyDescriptorImpl getOrCreatePropertyDescriptor(String propertyName);

  /**
   * {@inheritDoc}
   */
  @Override
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode) {

    return getAccessor(property, mode, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getProperty(POJO pojo, String property) {

    PojoProperty pojoProperty = new PojoProperty(property);
    if (pojoProperty.getIndex() != null) {
      return getAccessor(pojoProperty.getName(), PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED, true).invoke(pojo,
          pojoProperty.getIndex().intValue());
    } else if (pojoProperty.getKey() != null) {
      return getAccessor(pojoProperty.getName(), PojoPropertyAccessorOneArgMode.GET_MAPPED, true).invoke(pojo,
          pojoProperty.getKey());
    } else {
      return getAccessor(property, PojoPropertyAccessorNonArgMode.GET, true).invoke(pojo);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <V> V getProperty(POJO pojo, TypedProperty<V> property) throws PojoPropertyNotFoundException,
      ReflectionException {

    if (property.getParentPath() != null) {
      return (V) getProperty(pojo, property.getPojoPath(), false);
    } else {
      return (V) getProperty(pojo, property.getSegment());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object setProperty(POJO pojo, String propertyName, Object value) {

    PojoProperty property = new PojoProperty(propertyName);
    if (property.getIndex() != null) {
      return getAccessor(property.getName(), PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED, true).invoke(pojo,
          property.getIndex().intValue(), value);
    } else if (property.getKey() != null) {
      return getAccessor(property.getName(), PojoPropertyAccessorTwoArgMode.SET_MAPPED, true).invoke(pojo,
          property.getKey(), value);
    } else {
      return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.SET, true).invoke(pojo, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <V> void setProperty(POJO pojo, TypedProperty<V> property, V value) throws PojoPropertyNotFoundException,
      ReflectionException {

    NlsNullPointerException.checkNotNull("pojo", pojo);
    NlsNullPointerException.checkNotNull(TypedProperty.class, property);
    String parentPath = property.getParentPath();
    if (parentPath != null) {
      Object currentPojo = getProperty(pojo, parentPath, true);
      PojoDescriptor descriptor = this.pojoDescriptorBuilder.getDescriptor(currentPojo.getClass());
      descriptor.setProperty(currentPojo, property.getSegment(), value);
    } else {
      setProperty(pojo, property.getSegment(), value);
    }
  }

  /**
   * 
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param propertyPath is the {@link net.sf.mmm.util.pojo.path.api.PojoPropertyPath#getPojoPath() POJO
   *        property path}.
   * @param required - <code>true</code> if the result is required, <code>false</code> if any intermediate or
   *        the end result may be <code>null</code> resulting in <code>null</code> being returned.
   * @return the requested property value.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private Object getProperty(POJO pojo, String propertyPath, boolean required) {

    String[] segments = propertyPath.split("\\.");
    Object currentPojo = getProperty(pojo, segments[0]);
    if (required && (currentPojo == null)) {
      throw new NlsNullPointerException(pojo.getClass().getSimpleName() + "." + segments[0]);
    }
    for (int i = 1; i < segments.length; i++) {
      if (currentPojo == null) {
        return null;
      }
      PojoDescriptor descriptor = this.pojoDescriptorBuilder.getDescriptor(currentPojo.getClass());
      Object nextPojo = descriptor.getProperty(currentPojo, segments[i]);
      if (required && (nextPojo == null)) {
        StringBuilder currentPath = new StringBuilder(segments[0]);
        for (int j = 1; j < i; j++) {
          currentPath.append('.');
          currentPath.append(segments[j]);
        }
        throw new NlsNullPointerException(pojo.getClass().getSimpleName() + "." + currentPath.toString());
      }
      currentPojo = nextPojo;
    }
    return currentPojo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPropertySize(POJO pojo, String propertyName) {

    Object result = getAccessor(propertyName, PojoPropertyAccessorNonArgMode.GET_SIZE, true).invoke(pojo);
    try {
      Number size = (Number) result;
      return size.intValue();
    } catch (ClassCastException e) {
      throw new IllegalStateException("Size of property '" + propertyName + "' in pojo '" + pojo + "' is no number: "
          + result);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object addPropertyItem(POJO pojo, String propertyName, Object item) {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.ADD, true).invoke(pojo, item);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean removePropertyItem(POJO pojo, String propertyName, Object item) {

    Object result = getAccessor(propertyName, PojoPropertyAccessorOneArgMode.REMOVE, true).invoke(pojo, item);
    if (result instanceof Boolean) {
      return (Boolean) result;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getPropertyItem(POJO pojo, String propertyName, int index) {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED).invoke(pojo, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object setPropertyItem(POJO pojo, String propertyName, int index, Object item) {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED).invoke(pojo, index, item);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyDescriptor getPropertyDescriptor(TypedProperty<?> property) {

    NlsNullPointerException.checkNotNull(TypedProperty.class, property);
    PojoDescriptor<?> descriptor = this;
    String parentPath = property.getParentPath();
    if (parentPath != null) {
      String[] segments = parentPath.split("\\.");
      for (int i = 0; i < segments.length; i++) {
        PojoPropertyDescriptor propertyDescriptor = descriptor.getPropertyDescriptor(segments[i]);
        PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
        if (getter == null) {
          StringBuilder currentPath = new StringBuilder(segments[0]);
          for (int j = 1; j <= i; j++) {
            currentPath.append('.');
            currentPath.append(segments[j]);
          }
          throw new PojoPropertyNotFoundException(getPojoClass(), currentPath.toString());
        }
        descriptor = this.pojoDescriptorBuilder.getDescriptor(getter.getPropertyType());
      }
    }
    PojoPropertyDescriptor propertyDescriptor = descriptor.getPropertyDescriptor(property.getSegment());
    if (propertyDescriptor == null) {
      throw new PojoPropertyNotFoundException(getPojoClass(), property.getPojoPath());
    }
    return propertyDescriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for POJO " + this.pojoType;
  }

}
