/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import net.sf.mmm.data.api.reflection.DataClassGroupVersion;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.version.api.VersionIdentifier;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of {@link DataClassGroupVersion}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = "DataClassGroupVersion")
public class DataClassGroupVersionImpl implements DataClassGroupVersion, GenericEntity<String> {

  /** @see #getId() */
  private String id;

  /** @see #getGroupVersion() */
  private VersionIdentifier groupVersion;

  /**
   * The constructor.
   */
  public DataClassGroupVersionImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param groupId - see {@link #getGroupId()}.
   * @param groupVersion - see {@link #getGroupVersion()}.
   */
  public DataClassGroupVersionImpl(String groupId, VersionIdentifier groupVersion) {

    super();
    setId(groupId);
    this.groupVersion = groupVersion;
  }

  /**
   * {@inheritDoc}
   */
  @Id
  public String getId() {

    return this.id;
  }

  /**
   * @param id is the id to set
   */
  public void setId(String id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public String getGroupId() {

    return getId();
  }

  /**
   * @param groupId is the groupId to set
   */
  protected void setGroupId(String groupId) {

    setId(groupId);
  }

  /**
   * {@inheritDoc}
   */
  @Type(type = "net.sf.mmm.data.impl.datatype.usertype.VersionIdentifierUserType")
  public VersionIdentifier getGroupVersion() {

    return this.groupVersion;
  }

  /**
   * @param groupVersion is the groupVersion to set
   */
  protected void setGroupVersion(VersionIdentifier groupVersion) {

    this.groupVersion = groupVersion;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public int getModificationCounter() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public Date getModificationTimestamp() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public boolean isPersistent() {

    // how to implement this?
    return false;
  }

}
