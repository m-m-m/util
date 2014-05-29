/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.persistence.api.PersistenceManager;
import net.sf.mmm.persistence.api.RevisionMetadata;
import net.sf.mmm.persistence.api.RevisionedDao;
import net.sf.mmm.persistence.base.jpa.AbstractJpaGenericDao;
import net.sf.mmm.util.entity.api.MutableRevisionedEntity;
import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.persistence.api.RevisionedDao} using
 * {@link org.hibernate.envers Hibernate-Envers} to manage the revision-control.
 *
 * @param <ID> is the type of the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() primary key} of the
 *        managed {@link net.sf.mmm.util.entity.api.GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the managed entity.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedDaoEnvers<ID, ENTITY extends MutableRevisionedEntity<ID>> extends
    AbstractJpaGenericDao<ID, ENTITY> implements RevisionedDao<ID, ENTITY> {

  /** @see #getPersistenceManager() */
  private PersistenceManager persistenceManager;

  /**
   * The constructor.
   */
  public AbstractRevisionedDaoEnvers() {

    super();
  }

  /**
   * @return the {@link PersistenceManager} instance.
   */
  protected PersistenceManager getPersistenceManager() {

    return this.persistenceManager;
  }

  /**
   * @param persistenceManager is the {@link PersistenceManager} to {@link Inject}.
   */
  @Inject
  public void setPersistenceManager(PersistenceManager persistenceManager) {

    getInitializationState().requireNotInitilized();
    this.persistenceManager = persistenceManager;
  }

  /**
   * @return the auditReader
   */
  protected AuditReader getAuditReader() {

    return AuditReaderFactory.get(getEntityManager());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedEntity.LATEST_REVISION) {
      return find(id);
    } else {
      return loadRevision(id, revision);
    }
  }

  /**
   * This method gets a historic revision of the {@link net.sf.mmm.util.entity.api.GenericEntity} with the
   * given <code>id</code>.
   *
   * @param id is the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() ID} of the requested
   *        {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   * @param revision is the {@link RevisionedEntity#getRevision() revision}
   * @return the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link net.sf.mmm.util.entity.api.GenericEntity entity}
   *         could NOT be found.
   */
  protected ENTITY loadRevision(Object id, Number revision) throws ObjectNotFoundException {

    Class<? extends ENTITY> entityClassImplementation = getEntityClassImplementation();
    ENTITY entity = getAuditReader().find(entityClassImplementation, id, revision);
    if (entity != null) {
      entity.setRevision(revision);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number createRevision(ENTITY entity) {

    // TODO:
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Number> getRevisionHistory(ENTITY entity) {

    return getAuditReader().getRevisions(getEntityClassImplementation(), entity.getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<RevisionMetadata> getRevisionHistoryMetadata(Object id) {

    AuditReader auditReader = getAuditReader();
    List<Number> revisionList = auditReader.getRevisions(getEntityClassImplementation(), id);
    List<RevisionMetadata> result = new ArrayList<RevisionMetadata>();
    for (Number revision : revisionList) {
      Long revisionLong = Long.valueOf(revision.longValue());
      result.add(new LazyRevisionMetadata(this.persistenceManager, revisionLong));
    }
    return result;
  }
}
