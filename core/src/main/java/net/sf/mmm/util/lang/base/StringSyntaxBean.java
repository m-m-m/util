/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.StringSyntax;

/**
 * This is the implementation of {@link StringSyntax} as simple Java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class StringSyntaxBean implements StringSyntax {

  /** @see #getEscape() */
  private char escape;

  /** @see #getQuoteStart() */
  private char quoteStart;

  /** @see #getQuoteEnd() */
  private char quoteEnd;

  /**
   * The constructor.
   */
  public StringSyntaxBean() {

    this('\0');
  }

  /**
   * The constructor.
   * 
   * @param escape - see {@link #getEscape()}.
   */
  public StringSyntaxBean(char escape) {

    this(escape, '\0');
  }

  /**
   * The constructor.
   * 
   * @param escape - see {@link #getEscape()}.
   * @param quote - see {@link #setQuote(char)}.
   */
  public StringSyntaxBean(char escape, char quote) {

    this(escape, quote, quote);
  }

  /**
   * The constructor.
   * 
   * @param escape - see {@link #getEscape()}.
   * @param quoteStart - see {@link #getQuoteStart()}.
   * @param quoteEnd - see @{@link #getQuoteEnd()}.
   */
  public StringSyntaxBean(char escape, char quoteStart, char quoteEnd) {

    super();
    this.escape = escape;
    this.quoteStart = quoteStart;
    this.quoteEnd = quoteEnd;
  }

  @Override
  public char getEscape() {

    return this.escape;
  }

  /**
   * @param escape is the {@link #getEscape() escape} to set.
   */
  public void setEscape(char escape) {

    this.escape = escape;
  }

  @Override
  public char getQuoteStart() {

    return this.quoteStart;
  }

  /**
   * @param quoteStart is the {@link #getQuoteStart() quoteStart} to set.
   */
  public void setQuoteStart(char quoteStart) {

    this.quoteStart = quoteStart;
  }

  @Override
  public char getQuoteEnd() {

    return this.quoteEnd;
  }

  /**
   * @param quoteEnd is the {@link #getQuoteEnd() quoteEnd} to set.
   */
  public void setQuoteEnd(char quoteEnd) {

    this.quoteEnd = quoteEnd;
  }

  /**
   * This method sets both the {@link #getQuoteStart() quote-start} and {@link #getQuoteEnd() quote-end}
   * character.
   * 
   * @param quote the quote character to set.
   */
  public void setQuote(char quote) {

    this.quoteStart = quote;
    this.quoteEnd = quote;
  }

}
