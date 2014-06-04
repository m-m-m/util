/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.base.BasicUtilImpl;

/**
 * This class represents the attributes of a file as defined in a Unix/Posix Filesystem. They consist of a
 * {@link #getUser() user}, a {@link #getGroup() group} and a {@link #getPermissions() mode mask} (modifiers).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class FileAttributes implements Cloneable {

  /** @see #getUser() */
  private String user;

  /** @see #getGroup() */
  private String group;

  /** @see #getPermissions() */
  private FileAccessPermissions permissions;

  /** the {@link BasicUtilImpl} instance to use. */
  private BasicUtil basicUtil;

  /**
   * The constructor.
   */
  public FileAttributes() {

    super();
    this.basicUtil = BasicUtilImpl.getInstance();
  }

  /**
   * The constructor.
   * 
   * @param basicUtil the instance of {@link BasicUtilImpl} to use.
   */
  public FileAttributes(BasicUtil basicUtil) {

    super();
    this.basicUtil = basicUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FileAttributes clone() {

    try {
      FileAttributes clone = (FileAttributes) super.clone();
      if (clone.permissions != null) {
        clone.setPermissions(clone.permissions.clone());
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @return the user
   */
  public String getUser() {

    return this.user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(String user) {

    this.user = user;
  }

  /**
   * @return the group
   */
  public String getGroup() {

    return this.group;
  }

  /**
   * @param group the group to set
   */
  public void setGroup(String group) {

    this.group = group;
  }

  /**
   * @return the userPermissions
   */
  public FileAccessPermissions getPermissions() {

    return this.permissions;
  }

  /**
   * @param mask the userPermissions to set
   */
  public void setPermissions(FileAccessPermissions mask) {

    this.permissions = mask;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    FileAttributes otherAttributes = (FileAttributes) obj;
    if (!this.basicUtil.isEqual(this.user, otherAttributes.user)) {
      return false;
    }
    if (!this.basicUtil.isEqual(this.group, otherAttributes.group)) {
      return false;
    }
    if (!this.basicUtil.isEqual(this.permissions, otherAttributes.permissions)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    int hash = 0;
    if (this.user != null) {
      hash = this.user.hashCode();
    }
    if (this.group != null) {
      hash = hash * 31 + this.group.hashCode();
    }
    if (this.permissions != null) {
      hash = hash * 31 + this.permissions.hashCode();
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (this.user != null) {
      sb.append(this.user);
    }
    sb.append(':');
    if (this.group != null) {
      sb.append(this.group);
    }
    sb.append(' ');
    if (this.permissions != null) {
      sb.append(this.permissions);
    }
    return super.toString();
  }

}
