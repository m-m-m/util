/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.api;

/**
 * This is the interface for a mutable {@link RevisionedEntity}.
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @author hohwille
 * @since 3.1.0
 */
public interface MutableRevisionedEntity<ID> extends RevisionedEntity<ID>, MutableGenericEntity<ID> {

  /**
   * This method sets the {@link #getRevision() revision} of this entity. <br>
   * <b>ATTENTION:</b><br>
   * This operation should only be used in specific cases and if you are aware of what you are doing as this attribute
   * is managed by the persistence. However, for final freedom we decided to add this method to the API (e.g. to copy
   * from transfer-object to persistent-entity and vice-versa).
   *
   * @param revision is the new value of {@link #getRevision()}.
   */
  void setRevision(Number revision);

}
