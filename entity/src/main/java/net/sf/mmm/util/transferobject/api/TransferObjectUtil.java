/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import java.util.List;
import java.util.Set;

import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the interface for a collection of utility functions to deal with {@link AbstractTransferObject}s.
 *
 * @see net.sf.mmm.util.transferobject.base.TransferObjectUtilImpl#getInstance()
 *
 * @deprecated use devon bean-mapping instead
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Deprecated
public interface TransferObjectUtil extends TransferObjectUtilLimited {

  /**
   * This method converts the given {@link PersistenceEntity} to the corresponding {@link EntityTo
   * transfer-object} identified by the given {@link Class}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to convert.
   * @param <TO> is the generic type of the {@link EntityTo transfer-object} to create.
   * @param entity is the {@link PersistenceEntity} to convert.
   * @param toType is the {@link Class} reflecting the {@link EntityTo ETO} to create.
   * @return an instance of {@code toType} with the values of the given {@code entity}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> TO convertFromEntity(ENTITY entity, Class<TO> toType);

  /**
   * Variant of {@link #convertFromEntity(PersistenceEntity, Class)} for a {@link List}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to convert.
   * @param <TO> is the generic type of the {@link EntityTo transfer-object} to create.
   * @param entityList is the {@link List} of {@link PersistenceEntity entities} to convert.
   * @param toType is the {@link Class} reflecting the {@link EntityTo ETO} to create.
   * @return a {@link List} with all {@link PersistenceEntity entities} from {@code entityList}
   *         {@link #convertFromEntity(PersistenceEntity, Class) converted} to the given {@code toType}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> List<TO> convertFromEntityList(List<ENTITY> entityList, Class<TO> toType);

  /**
   * Variant of {@link #convertFromEntity(PersistenceEntity, Class)} for a {@link Set}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to convert.
   * @param <TO> is the generic type of the {@link EntityTo transfer-object} to create.
   * @param entitySet is the {@link Set} of {@link PersistenceEntity entities} to convert.
   * @param toType is the {@link Class} reflecting the {@link EntityTo ETO} to create.
   * @return a {@link Set} with all {@link PersistenceEntity entities} from {@code entitySet}
   *         {@link #convertFromEntity(PersistenceEntity, Class) converted} to the given {@code toType}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> Set<TO> convertFromEntitySet(Set<ENTITY> entitySet, Class<TO> toType);

  /**
   * This method converts the given {@link EntityTo ETO} to the corresponding {@link PersistenceEntity}
   * identified by the given {@link Class}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to create.
   * @param <TO> is the generic type of the {@link EntityTo ETO} to convert.
   * @param transferObject is the {@link EntityTo ETO} to convert.
   * @param entityType is the {@link Class} reflecting the {@link PersistenceEntity} to create.
   * @return an instance of {@code entityType} with the values of the given {@code transferObject}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(TO transferObject, Class<ENTITY> entityType);

  /**
   * Variant of {@link #convertToEntity(EntityTo, Class)} for a {@link List}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to create.
   * @param <TO> is the generic type of the {@link EntityTo ETO} to convert.
   * @param transferObjects is the {@link List} of {@link EntityTo ETO}s to convert.
   * @param entityType is the {@link Class} reflecting the {@link PersistenceEntity} to create.
   * @return a {@link List} with all {@link EntityTo ETO}s from {@code transferObjects}
   *         {@link #convertToEntity(EntityTo, Class) converted} to the given {@code entityType}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> List<ENTITY> convertToEntityList(List<TO> transferObjects, Class<ENTITY> entityType);

  /**
   * Variant of {@link #convertToEntity(EntityTo, Class)} for a {@link Set}.
   *
   * @param <ID> is the generic type of the {@link PersistenceEntity#getId() ID}.
   * @param <ENTITY> is the generic type of the {@link PersistenceEntity entity} to create.
   * @param <TO> is the generic type of the {@link EntityTo ETO} to convert.
   * @param transferObjects is the {@link Set} of {@link EntityTo ETO}s to convert.
   * @param entityType is the {@link Class} reflecting the {@link PersistenceEntity} to create.
   * @return a {@link Set} with all {@link EntityTo ETO}s from {@code transferObjects}
   *         {@link #convertToEntity(EntityTo, Class) converted} to the given {@code entityType}.
   */
  <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> Set<ENTITY> convertToEntitySet(Set<TO> transferObjects, Class<ENTITY> entityType);

  /**
   * This method recursively updates all {@link EntityTo#getModificationCounter() modification counters} of
   * the {@link EntityTo ETO}s contained in the given {@code container}. <br>
   * The method {@link #convertFromEntity(PersistenceEntity, Class)} is typically used before the transaction
   * has been closed (by the logic layer of the application). However, JPA implementations like hibernate only
   * update the {@link PersistenceEntity#getModificationCounter() modification counter} after the transaction
   * has been closed and the new modification counters are available from the DB. {@link EntityTo} and this
   * method offer the perfect solution to this problem. All you have to do is call this method once for the
   * transfer object you want to send to the client after the transaction has been committed and before the
   * object gets serialized. <br>
   * <b>ATTENTION:</b><br>
   * This method (or better its current default implementation) assumes that you do not have cyclic
   * dependencies in your {@link TransferObjectUtil}. Otherwise this can lead to an infinity loop. However,
   * having cycles in {@link TransferObject}s is typically a design flaw. If you think different you can try
   * to convince us so we will add support for this.
   *
   * @see EntityTo#getModificationCounter()
   *
   * @param container is the {@link EntityTo} to update or any other {@link TransferObject}, bean,
   *        {@link java.util.Collection}, or {@link java.util.Map} potentially containing an {@link EntityTo}.
   * @param remove - {@code true} to remove any internal references from {@link EntityTo ETO}s so they get
   *        dereferences and garbage-collected, {@code false} otherwise.
   */
  void updateModificationCounter(Object container, boolean remove);

}
