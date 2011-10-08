/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

/**
 * This is the interface for a {@link PersistenceEntity} that is (potentially)
 * revision-controlled.
 * 
 * @see RevisionedPersistenceEntityManager
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedPersistenceEntity<ID> extends PersistenceEntity<ID> {

  /**
   * The {@link PersistenceEntityManager#load(Object) latest}
   * {@link #getRevision() revision} of a {@link PersistenceEntity}. It can also
   * be used for generic
   * {@link RevisionedPersistenceEntityManager#load(Object, Number) requests} of
   * the {@link PersistenceEntityManager#load(Object) latest} revision.<br>
   * Value is: {@value}
   */
  Number LATEST_REVISION = null;

  /**
   * This method gets the revision of this entity. The
   * {@link RevisionedPersistenceEntity#LATEST_REVISION latest revision} of an
   * entity will always return <code>null</code>. Otherwise this object is a
   * <em>historic entity</em> and it will be read-only so modifications are NOT
   * permitted.
   * 
   * @return the revision or <code>null</code> if this is the latest revision.
   */
  Number getRevision();

}
