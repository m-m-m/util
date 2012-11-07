/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.api;

/**
 * This is the interface for a {@link GenericEntity} that is (potentially) revision-controlled. An instance of
 * this interface represents the {@link #getRevision() revision} of a {@link GenericEntity}. There are two
 * cases to distinguish:
 * <ul>
 * <li><b>{@link #LATEST_REVISION latest revision}</b><br/>
 * A {@link RevisionedEntity} pointing to {@link #LATEST_REVISION} represents the latest state of the entity
 * and can be modified.</li>
 * <li><b>historic {@link #getRevision() revision}</b><br/>
 * If the object is {@link #getRevision() revision controlled}, it has a history of modifications. A
 * {@link RevisionedEntity} can represent a historic {@link #getRevision() revision} out of this history. It
 * therefore is immutable so operations to modify the {@link RevisionedEntity} will typically fail.</li>
 * </ul>
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedEntity<ID> extends GenericEntity<ID> {

  /**
   * The latest {@link #getRevision() revision} of an {@link GenericEntity entity}.<br>
   * Value is: {@value}
   */
  Number LATEST_REVISION = null;

  /**
   * This method gets the revision of this entity. The {@link RevisionedEntity#LATEST_REVISION latest
   * revision} of an entity will always return <code>null</code>. Otherwise this object is a
   * <em>historic entity</em> and it will be read-only so modifications are NOT permitted.
   * 
   * @return the revision or {@link #LATEST_REVISION} (<code>null</code>) if this is the latest revision.
   */
  Number getRevision();

}
