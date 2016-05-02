/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the
 * {@link VersionIdentifier#getRevision() revision}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterRevision extends AbstractVersionIdentifierFormatterNumber {

  /**
   * The constructor.
   * 
   * @param stringUtil is the {@link StringUtil} instance.
   * @param prefix is the static prefix to append before the {@link VersionIdentifier#getRevision() revision}.
   *        Will be omitted if {@link VersionIdentifier#getRevision() revision} is {@code null}.
   * @param padding is the padding (minimum number of digits) for the {@link VersionIdentifier#getRevision()
   *        revision}. The default is {@code 0}.
   */
  public VersionIdentifierFormatterRevision(StringUtil stringUtil, String prefix, int padding) {

    super(stringUtil, prefix, padding);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Number getNumber(VersionIdentifier value) {

    return value.getRevision();
  }

}
