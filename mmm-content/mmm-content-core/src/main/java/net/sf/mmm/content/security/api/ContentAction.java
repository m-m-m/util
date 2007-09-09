/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.security.api;

import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface for an action. Such object represents an operation that
 * can be performed on a {@link ContentObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentAction extends ContentSecurityObject {

  /**
   * The {@link #getName() name} of the action required to read a
   * {@link ContentObject} or more precise a
   * {@link net.sf.mmm.content.model.api.ContentField field} of it.
   */
  String ACTION_READ = "read";

  /**
   * The {@link #getName() name} of the action required to write a
   * {@link ContentObject} or more precise a
   * {@link net.sf.mmm.content.model.api.ContentField field} of it.
   */
  String ACTION_WRITE = "write";

  /**
   * The {@link #getName() name} of the action required to create a
   * {@link ContentObject}.
   */
  String ACTION_CREATE = "create";

  /**
   * The {@link #getName() name} of the action required to
   * {@link #isDeleted() delete} a {@link ContentObject}.
   */
  String ACTION_DELETE = "delete";

  /**
   * The {@link #getName() name} of the action required to
   * {@link #getLock() lock} a {@link ContentObject}.
   */
  String ACTION_LOCK = "lock";

  /**
   * The {@link #getName() name} of the action required to rename a
   * {@link ContentObject}.
   */
  String ACTION_RENAME = "rename";

  /**
   * The {@link #getName() name} of the action required to copy a
   * {@link ContentObject}.
   */
  String ACTION_COPY = "copy";

  /**
   * The {@link #getName() name} of the action required to move a
   * {@link ContentObject}.
   */
  String ACTION_MOVE = "move";

}
