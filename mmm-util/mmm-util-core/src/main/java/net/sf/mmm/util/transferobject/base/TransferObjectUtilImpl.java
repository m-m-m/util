/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.base.DatatypeDetectorImpl;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.api.EntityTo.PersistentEntityAccess;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * This is the implementation of {@link TransferObjectUtil}.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Named
public class TransferObjectUtilImpl extends TransferObjectUtilLimitedImpl implements TransferObjectUtil {

  /** The singleton instance of {@link EtoHelper}. */
  private static final EtoHelper HELPER = new EtoHelper();

  /** @see #getInstance() */
  private static TransferObjectUtil instance;

  /** @see #getComposedValueConverter() */
  private ComposedValueConverter composedValueConverter;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

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
      this.composedValueConverter = DefaultComposedValueConverter.getInstance();
    }
    if (this.datatypeDetector == null) {
      DatatypeDetectorImpl impl = new DatatypeDetectorImpl();
      impl.initialize();
      this.datatypeDetector = impl;
    }
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
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
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
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
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(TO transferObject,
      Class<ENTITY> entityType) {

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
    this.reflectionUtil.visitObjectRecursive(container, visitor);
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
