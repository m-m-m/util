/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.base.DatatypeDetectorImpl;
import net.sf.mmm.util.pojo.api.PojoUtil;
import net.sf.mmm.util.pojo.base.PojoUtilImpl;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.api.EntityTo.PersistentEntityAccess;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;
import net.sf.mmm.util.value.impl.ValueConverterEntityToEto;
import net.sf.mmm.util.value.impl.ValueConverterEtoToEntity;

/**
 * This is the implementation of {@link TransferObjectUtil}.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class TransferObjectUtilImpl extends TransferObjectUtilLimitedImpl implements TransferObjectUtil {

  /** The singleton instance of {@link EtoHelper}. */
  private static final EtoHelper HELPER = new EtoHelper();

  /** @see #getInstance() */
  private static TransferObjectUtil instance;

  /** @see #getComposedValueConverter() */
  private ComposedValueConverter composedValueConverter;

  /** @see #getPojoUtil() */
  private PojoUtil pojoUtil;

  /** @see #getDatatypeDetector() */
  private DatatypeDetector datatypeDetector;

  /**
   * The constructor.
   */
  public TransferObjectUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link TransferObjectUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static TransferObjectUtil getInstance() {

    if (instance == null) {
      synchronized (TransferObjectUtilImpl.class) {
        if (instance == null) {
          TransferObjectUtilImpl impl = new TransferObjectUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.composedValueConverter == null) {
      DefaultComposedValueConverter impl = new DefaultComposedValueConverter();
      impl.addConverterComponent(new ValueConverterEtoToEntity());
      impl.addConverterComponent(new ValueConverterEntityToEto());
      impl.initialize();
      this.composedValueConverter = impl;
    }
    if (this.datatypeDetector == null) {
      DatatypeDetectorImpl impl = new DatatypeDetectorImpl();
      impl.initialize();
      this.datatypeDetector = impl;
    }
    if (this.pojoUtil == null) {
      this.pojoUtil = PojoUtilImpl.getInstance();
    }
  }

  /**
   * @return the instance of {@link ComposedValueConverter}.
   */
  protected ComposedValueConverter getComposedValueConverter() {

    return this.composedValueConverter;
  }

  /**
   * @param composedValueConverter is the instance of {@link ComposedValueConverter} to {@link Inject}.
   */
  @Inject
  public void setComposedValueConverter(ComposedValueConverter composedValueConverter) {

    getInitializationState().requireNotInitilized();
    this.composedValueConverter = composedValueConverter;
  }

  /**
   * @return the {@link DatatypeDetector} instance.
   */
  protected DatatypeDetector getDatatypeDetector() {

    return this.datatypeDetector;
  }

  /**
   * @param datatypeDetector is the {@link DatatypeDetector} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDetector(DatatypeDetector datatypeDetector) {

    getInitializationState().requireNotInitilized();
    this.datatypeDetector = datatypeDetector;
  }

  /**
   * @return the {@link ReflectionUtil} instance.
   */
  protected PojoUtil getPojoUtil() {

    return this.pojoUtil;
  }

  /**
   * @param pojoUtil is the {@link PojoUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(PojoUtil pojoUtil) {

    getInitializationState().requireNotInitilized();
    this.pojoUtil = pojoUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> TO convertFromEntity(ENTITY entity,
      Class<TO> toType) {

    return this.composedValueConverter.convert(entity, null, toType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> List<TO> convertFromEntityList(
      List<ENTITY> entityList, Class<TO> toType) {

    if (entityList == null) {
      return null;
    }
    List<TO> result = new ArrayList<>(entityList.size());
    for (ENTITY entity : entityList) {
      result.add(convertFromEntity(entity, toType));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> Set<TO> convertFromEntitySet(
      Set<ENTITY> entitySet, Class<TO> toType) {

    if (entitySet == null) {
      return null;
    }
    Set<TO> result = new HashSet<>(entitySet.size());
    for (ENTITY entity : entitySet) {
      result.add(convertFromEntity(entity, toType));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(
      TO transferObject, Class<ENTITY> entityType) {

    return this.composedValueConverter.convert(transferObject, null, entityType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> List<ENTITY> convertToEntityList(
      List<TO> transferObjects, Class<ENTITY> entityType) {

    if (transferObjects == null) {
      return null;
    }
    List<ENTITY> result = new ArrayList<>(transferObjects.size());
    for (TO to : transferObjects) {
      result.add(convertToEntity(to, entityType));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> Set<ENTITY> convertToEntitySet(
      Set<TO> transferObjects, Class<ENTITY> entityType) {

    if (transferObjects == null) {
      return null;
    }
    Set<ENTITY> result = new HashSet<>(transferObjects.size());
    for (TO to : transferObjects) {
      result.add(convertToEntity(to, entityType));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModificationCounter(Object container, final boolean remove) {

    Filter<Object> visitor = new Filter<Object>() {

      public boolean accept(Object value) {

        if (TransferObjectUtilImpl.this.datatypeDetector.isDatatype(value.getClass())) {
          return false;
        }
        if (value instanceof EntityTo) {
          EntityTo<?> eto = (EntityTo<?>) value;
          eto.getModificationCounter();
          if (remove) {
            HELPER.setPersistentEntity(eto, null);
          }
        }
        return true;
      }
    };
    this.pojoUtil.visitObjectRecursive(container, visitor);
  }

  /**
   * Make {@link PersistentEntityAccess} accessible.
   */
  private static class EtoHelper extends PersistentEntityAccess {

    /**
     * {@inheritDoc}
     */
    @Override
    protected <ID> void setPersistentEntity(EntityTo<ID> eto, PersistenceEntity<ID> persistentEntity) {

      super.setPersistentEntity(eto, persistentEntity);
    }
  }
}
