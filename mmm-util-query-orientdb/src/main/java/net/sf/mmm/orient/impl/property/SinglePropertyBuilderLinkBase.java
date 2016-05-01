/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Inject;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.orient.api.mapping.OrientBeanMapper;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#LINK}.
 *
 * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public abstract class SinglePropertyBuilderLinkBase<V> extends AbstractLoggableComponent
    implements SinglePropertyBuilder<V> {

  private OrientBeanMapper beanMapper;

  /**
   * The constructor.
   */
  public SinglePropertyBuilderLinkBase() {
    super();
  }

  /**
   * @return the {@link OrientBeanMapper}.
   */
  protected OrientBeanMapper getBeanMapper() {

    return this.beanMapper;
  }

  /**
   * @param beanMapper is the OrientBeanMapper to {@link Inject}.
   */
  @Inject
  public void setBeanMapper(OrientBeanMapper beanMapper) {

    this.beanMapper = beanMapper;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.beanMapper == null) {
      throw new ResourceMissingException("beanMapper");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public GenericType<V> getValueType(OProperty oProperty) {

    Class<? extends OrientBean> beanClass = null;
    OClass linkedClass = oProperty.getLinkedClass();
    if (linkedClass != null) {
      OrientBean prototype = this.beanMapper.getBeanPrototype(linkedClass);
      if (prototype != null) {
        beanClass = (Class<? extends OrientBean>) prototype.access().getBeanClass();
      }
    }
    GenericType genericType = getValueType(beanClass);
    return genericType;
  }

  /**
   * @param beanClass the linkend {@link OrientBean}-{@link Class}.
   * @return the {@link GenericType}.
   */
  protected abstract GenericType getValueType(Class<? extends OrientBean> beanClass);

  @Override
  public OProperty build(WritableProperty<V> property, OClass oClass) {

    OClass linkedClass = getLinkedClass(property);
    if (linkedClass == null) {
      return oClass.createProperty(property.getName(), getType());
    } else {
      return oClass.createProperty(property.getName(), getType(), linkedClass);
    }
  }

  /**
   * @param property the link(list) {@link WritableProperty property}.
   * @return the linked {@link OClass} corresponding to the given {@link WritableProperty property}.
   */
  protected abstract OClass getLinkedClass(WritableProperty<V> property);

}
