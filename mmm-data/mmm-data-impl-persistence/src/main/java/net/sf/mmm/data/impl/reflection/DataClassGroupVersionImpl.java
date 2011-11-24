/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.reflection;

import javax.persistence.Embeddable;

import net.sf.mmm.data.api.reflection.DataClassGroupVersion;
import net.sf.mmm.util.version.api.VersionIdentifier;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of {@link DataClassGroupVersion}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Embeddable
public class DataClassGroupVersionImpl implements DataClassGroupVersion {

  /** @see #getGroupId() */
  private String groupId;

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
    this.groupId = groupId;
    this.groupVersion = groupVersion;
  }

  /**
   * {@inheritDoc}
   */
  public String getGroupId() {

    return this.groupId;
  }

  /**
   * @param groupId is the groupId to set
   */
  protected void setGroupId(String groupId) {

    this.groupId = groupId;
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

}
