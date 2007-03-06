/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.base;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingService;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector;
import net.sf.mmm.nls.impl.ResourceMissingException;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.api.PojoPropertyNotFoundException;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.base.AbstractGenericValue;

/**
 * This is the abstract base implementation of the
 * {@link ConfigurationBindingService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationBindingService implements ConfigurationBindingService {

  /** @see #setDescriptorBuilder(PojoDescriptorBuilder) */
  private PojoDescriptorBuilder descriptorBuilder;

  /**
   * The constructor
   */
  public AbstractConfigurationBindingService() {

    super();
    this.descriptorBuilder = null;
  }

  /**
   * This method sets the descriptor-builder used to find reflective properties
   * of POJOs.
   * 
   * @param pojoDescriptorBuilder
   *        the descriptorBuilder to set.
   */
  @Resource
  public void setDescriptorBuilder(PojoDescriptorBuilder pojoDescriptorBuilder) {

    this.descriptorBuilder = pojoDescriptorBuilder;
  }

  /**
   * This method initializes this object.
   * 
   * @throws ResourceMissingException
   */
  @PostConstruct
  public void initialize() throws ResourceMissingException {

    if (this.descriptorBuilder == null) {
      throw new ResourceMissingException("descriptorBuilder");
    }
  }

  /**
   * This method finds an according accessor for injection.
   * 
   * @param propertyDescriptor
   *        is the property-descriptor or <code>null</code>.
   * @return <code>null</code> if the property-descriptor is <code>null</code>
   *         or it does NOT have an injectable accessor.
   */
  private PojoPropertyAccessor findAccessor(PojoPropertyDescriptor propertyDescriptor) {

    PojoPropertyAccessor accessor = null;
    if (propertyDescriptor != null) {
      accessor = propertyDescriptor.getAccessor(PojoPropertyAccessMode.WRITE);
      if (accessor == null) {
        accessor = propertyDescriptor.getAccessor(PojoPropertyAccessMode.ADD);
      }
    }
    return accessor;
  }

  /**
   * This method tries to find the appropriate accessor to inject the property
   * with the given <code>propertyName</code>.
   * 
   * TODO extract this stuff to utility class in configuration-core...
   * 
   * @param descriptor
   *        is the POJO descriptor.
   * @param propertyName
   *        is the name of the property.
   * @return the accessor or <code>null</code> if none could be found.
   */
  protected PojoPropertyAccessor findAccessor(PojoDescriptor<?> descriptor, String propertyName) {

    // TODO: extract first locating and rename to "findSingularAccessor"
    PojoPropertyAccessor accessor = findAccessor(descriptor.getPropertyDescriptor(propertyName));
    if (accessor != null) {
      return accessor;
    }
    // <root><services><coolService/><smartService></services></root>
    // --> addService(new CoolService());
    // --> addService(new SmartService());
    // ...
    // serviceS
    // bunchES
    // childREN
    // utility --> utilities
    // woman --> women (excuses NOT supported)
    if (propertyName.endsWith("hildren")) {
      // children --> child (remove last 3 characters)
      String singular = propertyName.substring(0, propertyName.length() - 3);
      accessor = findAccessor(descriptor.getPropertyDescriptor(singular));
      if (accessor != null) {
        return accessor;
      }
    }
    if (propertyName.endsWith("s")) {
      String singular = propertyName.substring(0, propertyName.length() - 1);
      accessor = findAccessor(descriptor.getPropertyDescriptor(singular));
      if (accessor != null) {
        return accessor;
      }
    }
    if (propertyName.endsWith("es")) {
      String singular = propertyName.substring(0, propertyName.length() - 2);
      accessor = findAccessor(descriptor.getPropertyDescriptor(singular));
      if (accessor != null) {
        return accessor;
      }
    }
    if (propertyName.endsWith("ies")) {
      String singular = propertyName.substring(0, propertyName.length() - 3) + 'y';
      accessor = findAccessor(descriptor.getPropertyDescriptor(singular));
      if (accessor != null) {
        return accessor;
      }
    }
    return null;
  }

  /**
   * This method determines if the given <code>type</code> is a simple type.
   * This means that it is directly supported by
   * {@link GenericValue#getValue(Class)}.
   * 
   * @param type
   *        is the type to check.
   * @return <code>true</code> if the given type is simple, <code>false</code>
   *         otherwise.
   */
  public boolean isSimpleType(Class<?> type) {

    return AbstractGenericValue.isSupported(type);
  }

  /**
   * @see net.sf.mmm.configuration.binding.api.ConfigurationBindingService#configure(net.sf.mmm.configuration.api.Configuration,
   *      java.lang.Object,
   *      net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector)
   */
  public void configure(Configuration configuration, Object pojo,
      ConfigurationBindingInjector builder) throws ConfigurationException {

    Class<?> pojoType = pojo.getClass();
    PojoDescriptor<?> descriptor = this.descriptorBuilder.getDescriptor(pojoType);
    // iterate children
    // TODO: maybe attributes will only be used as constructor arguments???
    for (Configuration child : configuration.getDescendants("*|@*")) {
      // TODO: collect children with same name in collection?
      // NO: collection types are detected on the level above:
      // <foos><foo/><foo/></foos>
      String name = child.getName();
      String propertyName;
      if (child.getType() == Configuration.Type.ATTRIBUTE) {
        propertyName = name.substring(1);
      } else {
        propertyName = name;
      }
      propertyName = StringUtil.toCamlCase(propertyName);
      PojoPropertyAccessor accessor = findAccessor(descriptor, propertyName);
      if (accessor == null) {
        throw new PojoPropertyNotFoundException(pojoType, propertyName + "[" + child.getPath()
            + "]");
      }
      // <foos><foo>A</foo><foo>B</foo>...</foos> --> addFoo(A); addFoo(B);
      // <foos><foo>A</foo><foo>B</foo>...</foos> --> pojo.foos = new Foo[]
      // {A,B}
      // 
      builder.inject(child, accessor, pojo, this);
    }
  }
}
