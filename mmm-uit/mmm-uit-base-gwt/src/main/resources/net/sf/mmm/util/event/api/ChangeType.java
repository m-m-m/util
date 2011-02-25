/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

/**
 * This enum contains the available {@link ChangeEvent#getType() types} of a
 * {@link ChangeEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (renamed, 1.0.1)
 */
public enum ChangeType {

  /**
   * A change of this type indicates, that something new has been added. E.g.
   * one or multiple item(s) have been inserted into a list.
   */
  ADD,

  /**
   * A change of this type indicates, that something that already exists has
   * been updated. E.g. an existing list item has changed its value or been
   * replaced by a new item.
   */
  UPDATE,

  /**
   * A change of this type indicates, that something that has been removed. E.g.
   * one or multiple existing item(s) have been removed from a list.
   */
  REMOVE
}
