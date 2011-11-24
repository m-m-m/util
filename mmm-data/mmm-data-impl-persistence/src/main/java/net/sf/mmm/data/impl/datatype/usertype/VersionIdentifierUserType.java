/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype.usertype;

import javax.inject.Inject;

import net.sf.mmm.persistence.impl.hibernate.usertype.StringDatatypeUserType;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionUtil;
import net.sf.mmm.util.version.impl.VersionUtilImpl;

/**
 * This is the implementation of the {@link org.hibernate.usertype.UserType} to
 * map the datatype {@link VersionIdentifier}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class VersionIdentifierUserType extends StringDatatypeUserType<VersionIdentifier> {

  /** @see #getVersionUtil() */
  private VersionUtil versionUtil;

  /**
   * The constructor.
   */
  public VersionIdentifierUserType() {

    super(VersionIdentifier.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.versionUtil == null) {
      this.versionUtil = VersionUtilImpl.getInstance();
    }
  }

  /**
   * @return the versionUtil
   */
  protected VersionUtil getVersionUtil() {

    return this.versionUtil;
  }

  /**
   * @param versionUtil is the versionUtil to set
   */
  @Inject
  public void setVersionUtil(VersionUtil versionUtil) {

    this.versionUtil = versionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VersionIdentifier toDatatype(String value) {

    if (!getInitializationState().isInitialized()) {
      initialize();
    }
    return getVersionUtil().createVersionIdentifier(value);
  }

}
