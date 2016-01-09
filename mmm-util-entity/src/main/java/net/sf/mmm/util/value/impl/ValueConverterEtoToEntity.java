/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.transferobject.api.EntityTo;

/**
 * This class extends {@link AbstractValueConverterToCompatiblePojo} for conversion from {@link EntityTo ETO}
 * to {@link PersistenceEntity}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class ValueConverterEtoToEntity extends AbstractValueConverterToSimilarPojo<EntityTo, PersistenceEntity> {

  /**
   * The constructor.
   */
  public ValueConverterEtoToEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<EntityTo> getSourceType() {

    return EntityTo.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<PersistenceEntity> getTargetType() {

    return PersistenceEntity.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleNoGetterForSetter(PojoPropertyAccessorOneArg setter, Class<?> targetClass, Object sourceObject,
      Class<?> sourceClass) {

    Class<?> propertyClass = setter.getPropertyClass();
    if (GenericEntity.class.isAssignableFrom(propertyClass)) {
      return;
    }
    GenericType<?> componentType = setter.getPropertyType().getComponentType();
    if (componentType != null) {
      Class<?> componentClass = componentType.getAssignmentClass();
      if (GenericEntity.class.isAssignableFrom(componentClass)) {
        return;
      }
    }
    super.handleNoGetterForSetter(setter, targetClass, sourceObject, sourceClass);
  }
}
