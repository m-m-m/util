/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.io.IOException;

import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.util.lang.api.Formatter} for a {@link String}
 * -based part of the {@link VersionIdentifier}.
 * 
 * @see ComposedVersionIdentifierFormatter
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractVersionIdentifierFormatterString extends AbstractFormatter<VersionIdentifier> {

  /** The static prefix. */
  private final String prefix;

  /** The maximum number of letters for the {@link String} to format. */
  private final int maximumLength;

  /**
   * The constructor.
   * 
   * @param prefix is the static prefix to append before the number. Will be omitted if the number is
   *        <code>null</code>.
   * @param maximumLength is the maximum number of letters for the {@link String} to format. The default is
   *        {@link Integer#MAX_VALUE}.
   */
  public AbstractVersionIdentifierFormatterString(String prefix, int maximumLength) {

    super();
    this.prefix = prefix;
    this.maximumLength = maximumLength;
  }

  /**
   * This method gets the {@link String} to format.
   * 
   * @param value is the {@link VersionIdentifier}.
   * @return the {@link String} to format.
   */
  protected abstract String getString(VersionIdentifier value);

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFormat(VersionIdentifier value, Appendable buffer) throws IOException {

    String string = getString(value);
    if (string != null) {
      if (this.prefix != null) {
        buffer.append(this.prefix);
      }
      if (string.length() > this.maximumLength) {
        string = string.substring(0, this.maximumLength);
      }
      buffer.append(string);
    }
  }

}
