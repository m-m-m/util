/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.api.EntityTo;
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

  /** @see #getInstance() */
  private static TransferObjectUtil instance;

  /** @see #getComposedValueConverter() */
  private ComposedValueConverter composedValueConverter;

  /**
   * The constructor.
   */
  public TransferObjectUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link TransferObjectUtilImpl}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
   * 
   * @return the singleton instance.
   */
  public static TransferObjectUtil getInstance() {

    if (instance == null) {
      synchronized (TransferObjectUtilImpl.class) {
        if (instance == null) {
          TransferObjectUtilImpl impl = new TransferObjectUtilImpl();
          impl.setComposedValueConverter(DefaultComposedValueConverter.getInstance());
          instance = impl;
        }
      }
    }
    return instance;
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
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(TO transferObject,
      Class<ENTITY> entityType) {

    return this.composedValueConverter.convert(transferObject, null, entityType);
  }

}
