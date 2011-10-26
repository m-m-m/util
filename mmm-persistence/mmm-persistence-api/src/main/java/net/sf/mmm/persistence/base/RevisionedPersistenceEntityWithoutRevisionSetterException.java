/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base;

import java.lang.reflect.Type;

import net.sf.mmm.persistence.NlsBundlePersistence;
import net.sf.mmm.persistence.api.PersistenceException;

/**
 * This exception is thrown if an implementation of
 * {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntity} shall be
 * managed by a
 * {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntityManager} but has
 * no setter method for the
 * {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntity#getRevision()
 * revision}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RevisionedPersistenceEntityWithoutRevisionSetterException extends PersistenceException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7391286548262808236L;

  /**
   * The constructor.
   * 
   * @param entityClass is the {@link Type} reflecting the
   *        {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntity} that
   *        is missing the setter for the revision.
   */
  public RevisionedPersistenceEntityWithoutRevisionSetterException(Type entityClass) {

    super(NlsBundlePersistence.ERR_REVISIONED_ENTITY_WITHOUT_REVISION_SETTER, toMap(KEY_TYPE,
        entityClass));
  }

}
