/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for a version-information of a resource.
 * 
 * @see net.sf.mmm.content.value.api.ContentId
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Version {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this value
   * type.
   */
  String VALUE_NAME = "Version";

  /** The {@link #getVersionSuffix() suffix} of an alpha version. */
  String SUFFIX_ALPHA = "alpha";

  /** The {@link #getVersionSuffix() suffix} of a beta version. */
  String SUFFIX_BETA = "beta";

  /** The {@link #getVersionSuffix() suffix} of a gamma version. */
  String SUFFIX_GAMMA = "gamma";

  /** The {@link #getVersionSuffix() suffix} of a release candidate version. */
  String SUFFIX_RELEASE_CANDIDATE = "rc";

  /** The {@link #getVersionSuffix() suffix} of a pre-release version. */
  String SUFFIX_PRE_RELEASE = "pre";

  /**
   * This method gets the comment given by the user who created the version.
   * 
   * @return the versions comment. May be <code>null</code> for no comment.
   */
  String getComment();

  // Branches are realized as new resource with an own version-history but
  // linked with the HEAD (or source branch).
  /**
   * This method gets the name of the branch this version belongs to or
   * <code>null</code> if the version is not in a branch (in the head).
   * 
   * @return the name of the branch (<code>null</code> for head).
   */
  // String getBranchName();
  /**
   * This method gets the major version number. <br>
   * E.g. for version "3.42.1.5-test" the major version is "3".
   * 
   * @return the major version
   */
  int getMajorVersion();

  /**
   * This method gets the minor version number. E.g. for version "3.42.1.5-test"
   * the minor version is "42".
   * 
   * @return the minor version number.
   */
  int getMinorVersion();

  /**
   * This method gets the milli version number. E.g. for version "3.42.1.5-test"
   * the minor version is "1".
   * 
   * @return the minor version number.
   */
  int getMilliVersion();

  /**
   * This method gets the micro version number. E.g. for version "3.42.1.5-test"
   * the micro version is "5".
   * 
   * @return the micro version number.
   */
  int getMicroVersion();

  /**
   * This method gets the version suffix.<br>
   * E.g. for version "3.42.1.5-test" the version suffix is "test".<br>
   * A suffix reduces the rank of a version: e.g. "1.0" is
   * {@link #isHigherThan(Version) higher-than} "1.0-stable".
   * 
   * @return the version suffix.
   */
  String getVersionSuffix();

  /**
   * This method gets the string representation of this object in the form
   * <code>{major}[.{minor}[.{milli}[.{micro}]]][-{suffix}]</code>.
   * 
   * @see java.lang.Object#toString()
   */
  String toString();

  /**
   * This method determines if this version is higher than the given one.<br>
   * E.g. "1.0">"1.0-rc1">"1.0-beta">"1.0-alpha">"0.9.237">"0.9">"0.1">"0"
   * 
   * @param otherVersion is the version to compare with.
   * @return <code>true</code> if this version is higher than the given
   *         version and <code>false</code> otherwise.
   */
  boolean isHigherThan(Version otherVersion);

}
