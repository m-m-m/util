/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

/**
 * This is the interface for an operation. Such object represents an action that
 * can be performed on a {@link net.sf.mmm.data.api.DataObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSecurityOperation extends DataSecurityObject {

  /**
   * The {@link #getTitle() name} of the action required to read a
   * {@link net.sf.mmm.data.api.DataObject} or more precise a
   * {@link net.sf.mmm.data.api.reflection.DataField field} of it.
   */
  String ACTION_READ = "read";

  /**
   * The {@link #getTitle() name} of the action required to write a
   * {@link net.sf.mmm.data.api.DataObject} or more precise a
   * {@link net.sf.mmm.data.api.reflection.DataField field} of it.
   */
  String ACTION_WRITE = "write";

  /**
   * The {@link #getTitle() name} of the action required to create a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_CREATE = "create";

  /**
   * The {@link #getTitle() name} of the action required to delete a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_DELETE = "delete";

  /**
   * The {@link #getTitle() name} of the action required to lock a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_LOCK = "lock";

  /**
   * The {@link #getTitle() name} of the action required to rename a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_RENAME = "rename";

  /**
   * The {@link #getTitle() name} of the action required to copy a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_COPY = "copy";

  /**
   * The {@link #getTitle() name} of the action required to move a
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  String ACTION_MOVE = "move";

}
