/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import java.util.Date;

/**
 * This is the interface for the metadata associated with a
 * {@link net.sf.mmm.util.entity.api.RevisionedEntity#getRevision() historic revision} of an
 * {@link net.sf.mmm.util.entity.api.RevisionedEntity entity}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionMetadata {

  /**
   * This method gets the {@link net.sf.mmm.util.entity.api.RevisionedEntity#getRevision() revision number}.
   *
   * @return the revision number.
   */
  Number getRevision();

  /**
   * This method gets the date when this revision was created (closed).
   *
   * @return the date of completion or <code>null</code> if the according entity is the latest revision.
   */
  Date getDate();

  /**
   * This method gets the creator of this revision. This can be a string (such as the
   * {@link java.security.Principal#getName() principal-name}) or an
   * {@link net.sf.mmm.util.entity.api.RevisionedEntity entity} representing the according user.
   *
   * @return the creator. May be <code>null</code> (if committed outside user scope or if NOT supported at
   *         all).
   */
  Object getCreator();

}
