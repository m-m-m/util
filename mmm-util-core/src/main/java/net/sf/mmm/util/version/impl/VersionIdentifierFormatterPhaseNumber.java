/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the
 * {@link VersionIdentifier#getPhaseNumber() phase number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterPhaseNumber extends AbstractVersionIdentifierFormatterNumber {

  /**
   * The constructor.
   * 
   * @param stringUtil is the {@link StringUtil} instance.
   * @param prefix is the static prefix to append before the {@link VersionIdentifier#getPhaseNumber() phase
   *        number}. Will be omitted if {@link VersionIdentifier#getPhaseNumber() phase number} is
   *        <code>null</code>.
   * @param phaseNumberPadding is the padding (minimum number of digits) for the
   *        {@link VersionIdentifier#getPhaseNumber() phase number}. The default is <code>0</code>.
   */
  public VersionIdentifierFormatterPhaseNumber(StringUtil stringUtil, String prefix, int phaseNumberPadding) {

    super(stringUtil, prefix, phaseNumberPadding);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Number getNumber(VersionIdentifier value) {

    Integer phaseNumber = null;
    if (value.getPhase() != null) {
      phaseNumber = value.getPhaseNumber();
    }
    return phaseNumber;
  }

}
