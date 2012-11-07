/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.persistence.api.RevisionMetadata;
import net.sf.mmm.persistence.api.RevisionedDao;
import net.sf.mmm.persistence.base.RevisionedPersistenceEntityWithoutRevisionSetterException;
import net.sf.mmm.persistence.impl.jpa.AbstractJpaGenericDao;
import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.Signature;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.RevisionedDao} using {@link org.hibernate.envers
 * Hibernate-Envers} to manage the revision-control.
 * 
 * @param <ID> is the type of the {@link net.sf.mmm.util.entity.api.GenericEntity#getId() primary key} of
 *        the managed {@link net.sf.mmm.util.entity.api.GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractRevisionedDaoEnvers<ID, ENTITY extends RevisionedEntity<ID>> extends
    AbstractJpaGenericDao<ID, ENTITY> implements RevisionedDao<ID, ENTITY> {

  /** The name of the method setRevision(Number). */
  private static final String SET_REVISION_METHOD_NAME = "setRevision";

  /** The arguments of the method setRevision(Number). */
  private static final Signature SET_REVISION_METHOD_SIGNATURE = new Signature(Number.class);

  /** The method to set the revision in the entity. */
  private Method setRevisionMethod;

  /**
   * The constructor.
   */
  public AbstractRevisionedDaoEnvers() {

    super();
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
  protected void doInitialize() {

    super.doInitialize();
    Method fallback = null;
    Class<?> entityClass = getEntityClassImplementation();
    while ((this.setRevisionMethod == null) && (entityClass != Object.class)) {
      Method[] methods = entityClass.getDeclaredMethods();
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
      entityClass = entityClass.getSuperclass();
    }
    if (this.setRevisionMethod == null) {
      this.setRevisionMethod = fallback;
    }
    if (this.setRevisionMethod == null) {
      throw new RevisionedPersistenceEntityWithoutRevisionSetterException(entityClass);
    } else {
      if (!Modifier.isPublic(this.setRevisionMethod.getModifiers())) {
        this.setRevisionMethod.setAccessible(true);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY load(ID id, Number revision) throws ObjectNotFoundException {

    if (revision == RevisionedEntity.LATEST_REVISION) {
      return load(id);
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
   * @throws ObjectNotFoundException if the requested {@link net.sf.mmm.util.entity.api.GenericEntity
   *         entity} could NOT be found.
   */
  protected ENTITY loadRevision(Object id, Number revision) throws ObjectNotFoundException {

    Class<? extends ENTITY> entityClassImplementation = getEntityClassImplementation();
    ENTITY entity = getAuditReader().find(entityClassImplementation, id, revision);
    if (entity != null) {
      try {
        this.setRevisionMethod.invoke(entity, revision);
      } catch (IllegalAccessException e) {
        throw new AccessFailedException(e, this.setRevisionMethod);
      } catch (InvocationTargetException e) {
        throw new InvocationFailedException(e, this.setRevisionMethod, entity);
      }
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
      result.add(new LazyRevisionMetadata(auditReader, revision));
    }
    return result;
  }

}
