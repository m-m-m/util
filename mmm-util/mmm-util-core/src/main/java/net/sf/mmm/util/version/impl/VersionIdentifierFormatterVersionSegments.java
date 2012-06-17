/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.io.IOException;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the plain
 * {@link VersionIdentifier#getVersionSegment(int) version number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterVersionSegments extends AbstractFormatter<VersionIdentifier> {

  /** The {@link StringUtil} instance. */
  private final StringUtil stringUtil;

  /** The static prefix. */
  private final String prefix;

  /** The separator for {@link VersionIdentifier#getVersionSegment(int) segments}. */
  private final String segmentSeparator;

  /** The minimum number of segments to format. */
  private final int minimumSegmentCount;

  /** The maximum number of segments to format. */
  private final int maximumSegmentCount;

  /** The padding for {@link VersionIdentifier#getVersionSegment(int) segments}. */
  private final int segmentPadding;

  /**
   * The constructor.
   * 
   * @param stringUtil is the {@link StringUtil} instance.
   * @param prefix is the static prefix to append before the number. Will be omitted if the number is
   *        <code>null</code>.
   * @param segmentSeparator is the separator for {@link VersionIdentifier#getVersionSegment(int) segments}.
   *        The typical value is the dot sign (".").
   * @param minimumSegmentCount is the minimum number of segments to format. The default is <code>0</code>.
   * @param maximumSegmentCount is maximum number of segments to format. The default is
   *        <code>{@link Integer#MAX_VALUE}</code>.
   * @param segmentPadding is the padding (minimum number of digits) for each
   *        {@link VersionIdentifier#getVersionSegment(int) segment}. The default is <code>0</code>.
   */
  public VersionIdentifierFormatterVersionSegments(StringUtil stringUtil, String prefix, String segmentSeparator,
      int minimumSegmentCount, int maximumSegmentCount, int segmentPadding) {

    super();
    this.stringUtil = stringUtil;
    this.prefix = prefix;
    this.segmentSeparator = segmentSeparator;
    this.minimumSegmentCount = minimumSegmentCount;
    this.maximumSegmentCount = maximumSegmentCount;
    this.segmentPadding = segmentPadding;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(VersionIdentifier value, Appendable buffer) throws IOException {

    int segmentCount = value.getVersionSegmentCount();
    if (segmentCount < this.minimumSegmentCount) {
      segmentCount = this.minimumSegmentCount;
    }
    if (segmentCount > this.maximumSegmentCount) {
      segmentCount = this.maximumSegmentCount;
    }
    if ((segmentCount > 0) && (this.prefix != null)) {
      buffer.append(this.prefix);
    }
    for (int i = 0; i < segmentCount; i++) {
      if (i > 0) {
        buffer.append(this.segmentSeparator);
      }
      buffer.append(doFormatSegment(value, i));
    }
  }

  /**
   * This method formats the {@link VersionIdentifier#getVersionSegment(int) segment} at the given
   * <code>index</code>.
   * 
   * @param value is the {@link VersionIdentifier}.
   * @param index is the index of the {@link VersionIdentifier#getVersionSegment(int) segment} to format.
   * @return the formatted segment.
   */
  protected CharSequence doFormatSegment(VersionIdentifier value, int index) {

    int segment = value.getVersionSegment(index);
    return this.stringUtil.padNumber(segment, this.segmentPadding);
  }

}
