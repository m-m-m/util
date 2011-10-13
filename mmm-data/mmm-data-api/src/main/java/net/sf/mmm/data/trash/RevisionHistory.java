/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.trash;

import java.util.Date;

import net.sf.mmm.data.security.api.ContentUser;

/**
 * This is the interface for the history of a specific ContentResource.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RevisionHistory {

  /**
   * The name of this value type.
   */
  String VALUE_NAME = RevisionHistory.class.getSimpleName();

  /**
   * This method gets the date when the given <code>action</code> was performed
   * on the associated revision.
   * 
   * @see net.sf.mmm.data.security.api.ContentAction
   * 
   * @param action is the action for that the date of performing is requested.
   *        This should be the name of an
   *        {@link net.sf.mmm.data.security.api.ContentAction action} - e.g.
   *        {@link net.sf.mmm.data.security.api.ContentAction#ACTION_CREATE}.
   * @return the date when the given action was performed on this revision or
   *         <code>null</code> if the actions was not (yet) performed on this
   *         revision.
   */
  Date getDate(String action);

  /**
   * This method gets the user that performed the given <code>action</code> on
   * the associated revision.
   * 
   * @see net.sf.mmm.data.security.api.ContentAction
   * 
   * @param action is the action for that the performing user is requested. This
   *        should be the name of an
   *        {@link net.sf.mmm.data.security.api.ContentAction action} - e.g.
   *        {@link net.sf.mmm.data.security.api.ContentAction#ACTION_CREATE}.
   * @return the user that performed the given action on this revision or
   *         <code>null</code> if the actions was not (yet) performed on this
   *         revision.
   */
  ContentUser getSubject(String action);
}
