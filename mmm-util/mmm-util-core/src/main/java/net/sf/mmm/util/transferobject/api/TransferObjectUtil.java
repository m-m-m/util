/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the interface for a collection of utility functions to deal with {@link AbstractTransferObject}s.
 * 
 * @see net.sf.mmm.util.transferobject.base.TransferObjectUtilImpl#getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@ComponentSpecification
public interface TransferObjectUtil extends TransferObjectUtilLimited {

  /**
   * This method converts the given {@link PersistenceEntity} to the corresponding {@link EntityTo
   * transfer-object} identified by the given {@link Class}.
   * 
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to convert.
   * @param <TO> is the generic type of the {@link EntityTo transfer-object} to create.
   * @param entity is the {@link PersistenceEntity} to convert.
   * @param toType is the {@link Class} reflecting the {@link AbstractTransferObject} to create.
   * @return an instance of <code>toType</code> with the values of the given <code>entity</code>.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> TO convertFromEntity(ENTITY entity,
      Class<TO> toType);

  /**
   * This method converts the given {@link EntityTo transfer-object} to the corresponding
   * {@link PersistenceEntity} identified by the given {@link Class}.
   * 
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to create.
   * @param <TO> is the generic type of the {@link EntityTo transfer-object} to convert.
   * @param transferObject is the {@link EntityTo transfer-object} to convert.
   * @param entityType is the {@link Class} reflecting the {@link PersistenceEntity} to create.
   * @return an instance of <code>entityType</code> with the values of the given <code>transferObject</code>.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(TO transferObject,
      Class<ENTITY> entityType);

  /**
   * This method recursively updates all {@link EntityTo#getModificationCounter() modification counters} of
   * the {@link EntityTo}s contained in the given {@link TransferObject}.<br/>
   * The method {@link #convertFromEntity(PersistenceEntity, Class)} is typically used before the transaction
   * has been closed (by the logic layer of the application). However, JPA implementations like hibernate only
   * update the {@link PersistenceEntity#getModificationCounter() modification counter} after the transaction
   * has been closed and the new modification counters are available from the DB. {@link EntityTo} and this
   * method offer the perfect solution to this problem. All you have to do is call this method once for the
   * transfer object you want to send to the client after the transaction has been committed and before the
   * object gets serialized.<br/>
   * If you want to see how it works have a look at the implementation of
   * {@link EntityTo#getModificationCounter()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method (or better its current default implementation) assumes that you do not have cyclic
   * dependencies in your {@link TransferObjectUtil}. Otherwise this can lead to an infinity loop. However,
   * having cycles in {@link TransferObject}s is typically a design flaw. If you think different you can try
   * to convince us so we will add support for this.
   * 
   * @param transferObject
   */
  void updateModificationCounter(TransferObject transferObject);

}
