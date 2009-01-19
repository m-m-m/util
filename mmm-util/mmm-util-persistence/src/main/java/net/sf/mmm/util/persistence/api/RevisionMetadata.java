/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.api;

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
   * This method gets the date when this revision was created.
   * 
   * @return
   */
  Timestamp getCreationDate();

}
