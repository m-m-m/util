/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * A {@link DataClassGroupVersion} groups a set of {@link DataClass}es like a
 * package in java. This is used for version controlling the schema (DDL) of
 * classes within the same namespace. If a newer version is detected an
 * automatic migration can be performed.<br/>
 * Additionally a plugin can check if the version is greater or equal to an
 * expected version.
 * 
 * @see DataClass#getGroupVersion()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassGroupVersion {

  /**
   * This method gets the ID that uniquely identifies the group. It is supposed
   * to follow the conventions for java packages. However, it is also possible
   * to use the conventions for an XML namespace.
   * 
   * @return the group ID.
   */
  String getGroupId();

  /**
   * This method gets the {@link VersionIdentifier} of this group.
   * 
   * @return the {@link VersionIdentifier}.
   */
  VersionIdentifier getGroupVersion();

}
