/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import java.sql.Timestamp;

/**
 * This is the interface for the metadata associated with a
 * {@link RevisionedPersistenceEntity#getRevision() historic revision} of an
 * {@link RevisionedPersistenceEntity entity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionMetadata {

  /**
   * This method gets the date when this revision was created (opened).
   * 
   * @return the date of creation.
   */
  Timestamp getDateOfCreation();

  /**
   * This method gets the date when this revision was completed (closed).
   * 
   * @return the date of completion or <code>null</code> if the according entity
   *         is the latest revision.
   */
  Timestamp getDateOfCompletion();

  /**
   * This method gets the creator of this revision. This can be a string (such
   * as the {@link java.security.Principal#getName() principal-name}) or an
   * {@link PersistenceEntity entity} representing the according user.
   * 
   * @return the creator or <code>null</code> if NOT supported.
   */
  Object getCreator();

}
