/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.text.api.Justification;

/**
 * This class represents an argument of an
 * {@link net.sf.mmm.util.nls.api.NlsMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class NlsArgument {

  /** @see #getKey() */
  private final String key;

  /** @see #getFormatter() */
  private final NlsFormatter<?> formatter;

  /** @see #getJustification() */
  private final Justification justification;

  /**
   * The constructor.
   * 
   * @param key is the {@link #getKey() key}.
   * @param formatter is the {@link #getFormatter() formatter}.
   * @param justification is the {@link #getJustification() justification}.
   */
  public NlsArgument(String key, NlsFormatter<?> formatter, Justification justification) {

    super();
    this.key = key;
    this.formatter = formatter;
    this.justification = justification;
  }

  /**
   * This method gets the key of the argument to format.
   * 
   * @return the key
   */
  public String getKey() {

    return this.key;
  }

  /**
   * Is the formatter used to format the {@link #getKey() argument}.
   * 
   * @return the formatter
   */
  public NlsFormatter<?> getFormatter() {

    return this.formatter;
  }

  /**
   * This method gets the optional {@link Justification}.
   * 
   * @return the justification or <code>null</code> for none.
   */
  public Justification getJustification() {

    return this.justification;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(NlsArgumentParser.START_EXPRESSION);
    sb.append(this.key);
    sb.append(this.formatter);
    if (this.justification != null) {
      sb.append(NlsArgumentParser.START_EXPRESSION);
      sb.append(this.justification);
      sb.append(NlsArgumentParser.END_EXPRESSION);
    }
    sb.append(NlsArgumentParser.END_EXPRESSION);
    return sb.toString();
  }

}
