/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for
 * the {@link VersionIdentifier#isSnapshot() snapshot} indicator.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class VersionIdentifierFormatterSnapshot extends AbstractVersionIdentifierFormatterString {

  /**
   * The constructor.
   * 
   * @param snapshotIndicator is the static string to append if the
   *        {@link VersionIdentifier} is a
   *        {@link VersionIdentifier#isSnapshot() snapshot} (e.g. "-SNAPSHOT").
   */
  public VersionIdentifierFormatterSnapshot(String snapshotIndicator) {

    super(snapshotIndicator, snapshotIndicator.length());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getString(VersionIdentifier value) {

    if (value.isSnapshot()) {
      return "";
    } else {
      return null;
    }
  }

}
