/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.version.api.DevelopmentPhase;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the
 * {@link VersionIdentifier#getPhaseAlias() phase alias}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterPhaseAlias extends AbstractVersionIdentifierFormatterString {

  /**
   * The constructor.
   *
   * @param prefix is the static prefix to append before the {@link VersionIdentifier#getPhase() phase}. Will
   *        be omitted if {@link VersionIdentifier#getPhase() phase} is <code>null</code>.
   * @param maximumLength is the maximum number of letters for the {@link VersionIdentifier#getPhaseAlias()
   *        phase alias}. The default is {@link Integer#MAX_VALUE}.
   */
  public VersionIdentifierFormatterPhaseAlias(String prefix, int maximumLength) {

    super(prefix, maximumLength);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getString(VersionIdentifier value) {

    String phaseAlias = value.getPhaseAlias();
    if (phaseAlias == null) {
      // fallback for robustness, should not happen...
      DevelopmentPhase phase = value.getPhase();
      if (phase != null) {
        phaseAlias = phase.toString();
      }
    }
    return phaseAlias;
  }

}
