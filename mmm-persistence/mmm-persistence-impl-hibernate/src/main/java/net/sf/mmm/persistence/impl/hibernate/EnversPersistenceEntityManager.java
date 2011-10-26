/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.persistence.api.RevisionMetadata;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntityManager;
import net.sf.mmm.persistence.base.RevisionedPersistenceEntityWithoutRevisionSetterException;
import net.sf.mmm.persistence.impl.jpa.JpaPersistenceEntityManager;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.Signature;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntityManager} using
 * {@link org.hibernate.envers Hibernate-Envers} to manage the revision-control.
 * 
 * @param <ID> is the type of the
 *        {@link net.sf.mmm.persistence.api.PersistenceEntity#getId() primary
 *        key} of the managed
 *        {@link net.sf.mmm.persistence.api.PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class EnversPersistenceEntityManager<ID, ENTITY extends RevisionedPersistenceEntity<ID>>
    extends JpaPersistenceEntityManager<ID, ENTITY> implements
    RevisionedPersistenceEntityManager<ID, ENTITY> {

  /** The name of the method setRevision(Number). */
  private static final String SET_REVISION_METHOD_NAME = "setRevision";

  /** The arguments of the method setRevision(Number). */
  private static final Signature SET_REVISION_METHOD_SIGNATURE = new Signature(Number.class);

  /** @see #getAuditReader() */
  private AuditReader auditReader;

  /** The method to set the revision in the entity. */
  private Method setRevisionMethod;

  /**
   * The constructor.
   */
  public EnversPersistenceEntityManager() {

    super();
  }

  /**
   * @return the auditReader
   */
  protected AuditReader getAuditReader() {

    return this.auditReader;
  }

  /**
   * @param auditReader is the auditReader to set
   */
  public void setAuditReader(AuditReader auditReader) {

    getInitializationState().requireNotInitilized();
    this.auditReader = auditReader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.auditReader == null) {
      this.auditReader = AuditReaderFactory.get(getEntityManager());
    }
    Method fallback = null;
    if (this.setRevisionMethod == null) {
      Method[] methods = getEntityClassImplementation().getMethods();
      for (Method method : methods) {
        if (SET_REVISION_METHOD_NAME.equals(method.getName())) {
          Class<?>[] parameterTypes = method.getParameterTypes();
          if (parameterTypes.length == 1) {
            Signature signature = new Signature(parameterTypes);
            if (SET_REVISION_METHOD_SIGNATURE.isApplicable(signature)) {
              this.setRevisionMethod = method;
              break;
            } else {
              fallback = method;
            }
          }
        }
      }
    }
    if (this.setRevisionMethod == null) {
      this.setRevisionMethod = fallback;
    }
    if (this.setRevisionMethod == null) {
      throw new RevisionedPersistenceEntityWithoutRevisionSetterException(
          getEntityClassImplementation());
    } else {
      if (!Modifier.isPublic(this.setRevisionMethod.getModifiers())) {
        this.setRevisionMethod.setAccessible(true);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedPersistenceEntity.LATEST_REVISION) {
      return load(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the
   * {@link net.sf.mmm.persistence.api.PersistenceEntity} with the given
   * <code>id</code>.
   * 
   * @param id is the
   *        {@link net.sf.mmm.persistence.api.PersistenceEntity#getId() ID} of
   *        the requested {@link net.sf.mmm.persistence.api.PersistenceEntity
   *        entity}.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision()
   *        revision}
   * @return the requested {@link net.sf.mmm.persistence.api.PersistenceEntity
   *         entity}.
   * @throws ObjectNotFoundException if the requested
   *         {@link net.sf.mmm.persistence.api.PersistenceEntity entity} could
   *         NOT be found.
   */
  protected ENTITY loadRevision(Object id, Number revision) throws ObjectNotFoundException {

    Class<? extends ENTITY> entityClassImplementation = getEntityClassImplementation();
    ENTITY entity = getAuditReader().find(entityClassImplementation, id, revision);
    try {
      this.setRevisionMethod.invoke(entity, revision);
      return entity;
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, this.setRevisionMethod);
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, this.setRevisionMethod, entity);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Number createRevision(ENTITY entity) {

    // TODO:
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public List<Number> getRevisionHistory(ENTITY entity) {

    return getAuditReader().getRevisions(getEntityClassImplementation(), entity.getId());
  }

  /**
   * {@inheritDoc}
   */
  public List<RevisionMetadata> getRevisionHistoryMetadata(Object id) {

    List<Number> revisionList = getAuditReader().getRevisions(getEntityClassImplementation(), id);
    List<RevisionMetadata> result = new ArrayList<RevisionMetadata>();
    for (Number revision : revisionList) {
      result.add(new LazyRevisionMetadata(getAuditReader(), revision));
    }
    return result;
  }

}
