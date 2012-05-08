/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionIdentifierFormatter;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.version.api.VersionIdentifierFormatter}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class DefaultVersionIdentifierFormatter extends AbstractFormatter<VersionIdentifier> implements
    VersionIdentifierFormatter {

  /** The {@link Iso8601Util} instance. */
  private final Iso8601Util iso8601Util;

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance.
   */
  public DefaultVersionIdentifierFormatter(Iso8601Util iso8601Util) {

    super();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(VersionIdentifier value, Appendable buffer) throws IOException {

    // hard coded fallback variant
    int segmentCount = value.getVersionSegmentCount();
    for (int i = 0; i < segmentCount; i++) {
      if (i > 0) {
        buffer.append('.');
      }
      buffer.append(Integer.toString(value.getVersionSegment(i)));
    }
    if (value.getPhase() != null) {
      buffer.append('-');
      String alias = value.getPhaseAlias();
      if (alias == null) {
        alias = value.getPhase().getTitle();
      }
      buffer.append(alias);
      Integer phaseNumber = value.getPhaseNumber();
      if (phaseNumber != null) {
        buffer.append(phaseNumber.toString());
      }
    }
    if (value.isSnapshot()) {
      buffer.append('-');
      buffer.append(VersionIdentifier.SNAPSHOT);
    }
    String label = value.getLabel();
    if (label != null) {
      buffer.append('-');
      buffer.append(label);
    }
    Long revision = value.getRevision();
    if (revision != null) {
      buffer.append('-');
      buffer.append(revision.toString());
    }
    Date date = value.getTimestamp();
    if (date != null) {
      buffer.append('-');
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      this.iso8601Util.formatDateTime(calendar, false, false, false, buffer);
    }

  }
}
