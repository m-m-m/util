/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.exception.api.ValueException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.api.EntityTo.PersistentEntityAccess;

/**
 * This class extends {@link AbstractValueConverterToCompatiblePojo} for conversion from {@link PersistenceEntity} to
 * {@link EntityTo ETO}. See {@link EntityTo#getModificationCounter()} for further details.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class ValueConverterEntityToEto extends AbstractValueConverterToSimilarPojo<PersistenceEntity, EntityTo> {

  /** The singleton instance of {@link EtoHelper}. */
  private static final EtoHelper HELPER = new EtoHelper();

  /**
   * The constructor.
   */
  public ValueConverterEntityToEto() {

    super();
  }

  @Override
  public Class<PersistenceEntity> getSourceType() {

    return PersistenceEntity.class;
  }

  @Override
  public Class<EntityTo> getTargetType() {

    return EntityTo.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends EntityTo> T convert(PersistenceEntity value, Object valueSource, GenericType<T> targetType) throws ValueException {

    T eto = super.convert(value, valueSource, targetType);
    HELPER.setPersistentEntity(eto, value);
    return eto;
  }

  /**
   * Make {@link PersistentEntityAccess} accessible.
   */
  private static class EtoHelper extends PersistentEntityAccess {

    @Override
    protected <ID> void setPersistentEntity(EntityTo<ID> eto, PersistenceEntity<ID> persistentEntity) {

      super.setPersistentEntity(eto, persistentEntity);
    }

  }

}
