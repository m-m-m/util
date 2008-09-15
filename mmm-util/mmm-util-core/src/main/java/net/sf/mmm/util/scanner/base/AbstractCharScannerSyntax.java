/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import net.sf.mmm.util.scanner.api.CharScannerSyntax;

/**
 * This is the abstract base implementation of the {@link CharScannerSyntax}
 * interface.<br>
 * The actual <code>char</code>s like {@link #getEscape() escape} are realized
 * as simple bean-properties and initialized with <code>'\0'</code> so they are
 * disabled by default.
 * 
 * @see net.sf.mmm.util.scanner.api.CharStreamScanner#readUntil(char, boolean,
 *      CharScannerSyntax)
 */
public abstract class AbstractCharScannerSyntax implements CharScannerSyntax {

  /** @see #getEscape() */
  private char escape;

  /** @see #getQuoteStart() */
  private char quoteStart;

  /** @see #getQuoteEnd() */
  private char quoteEnd;

  /** @see #getQuoteEscape() */
  private char quoteEscape;

  /** @see #isQuoteEscapeLazy() */
  private boolean quoteEscapeLazy;

  /** @see #getAltQuoteStart() */
  private char altQuoteStart;

  /** @see #getAltQuoteEnd() */
  private char altQuoteEnd;

  /** @see #getAltQuoteEscape() */
  private char altQuoteEscape;

  /** @see #isAltQuoteEscapeLazy() */
  private boolean altQuoteEscapeLazy;

  /** @see #getEntityStart() */
  private char entityStart;

  /** @see #getEntityEnd() */
  private char entityEnd;

  /**
   * The constructor.
   */
  public AbstractCharScannerSyntax() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public char getEscape() {

    return this.escape;
  }

  /**
   * @param escape is the {@link #getEscape() escape} character to set.
   */
  public void setEscape(char escape) {

    this.escape = escape;
  }

  /**
   * {@inheritDoc}
   */
  public char getQuoteStart() {

    return this.quoteStart;
  }

  /**
   * @param quoteStart is the {@link #getQuoteStart() quote-start} character to
   *        set.
   */
  public void setQuoteStart(char quoteStart) {

    this.quoteStart = quoteStart;
  }

  /**
   * {@inheritDoc}
   */
  public char getQuoteEnd() {

    return this.quoteEnd;
  }

  /**
   * @param quoteEnd is the {@link #getQuoteEnd() quote-end} character to set.
   */
  public void setQuoteEnd(char quoteEnd) {

    this.quoteEnd = quoteEnd;
  }

  /**
   * This method sets both the {@link #getQuoteStart() quote-start} and
   * {@link #getQuoteEnd() quote-end} character.
   * 
   * @param quote the quote character to set.
   */
  public void setQuote(char quote) {

    this.quoteStart = quote;
    this.quoteEnd = quote;
  }

  /**
   * {@inheritDoc}
   */
  public char getQuoteEscape() {

    return this.quoteEscape;
  }

  /**
   * @param quoteEscape is the {@link #getQuoteEnd() quote-escape} to set.
   */
  public void setQuoteEscape(char quoteEscape) {

    this.quoteEscape = quoteEscape;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isQuoteEscapeLazy() {

    return this.quoteEscapeLazy;
  }

  /**
   * @param quoteEscapeLazy the {@link #isQuoteEscapeLazy() quote-escape-lazy}
   *        flag to set
   */
  public void setQuoteEscapeLazy(boolean quoteEscapeLazy) {

    this.quoteEscapeLazy = quoteEscapeLazy;
  }

  /**
   * This method gets the alternative character used to start a quotation that
   * should be terminated by a {@link #getAltQuoteEnd() alt-quote-end}
   * character. The text inside the quote is taken as is (without the quote
   * characters).
   * 
   * @see #getQuoteStart()
   * 
   * @return the alternative character used to start a quotation or
   *         <code>0</code> (<code>'\0'</code>) for no quotation.
   */
  public char getAltQuoteStart() {

    return this.altQuoteStart;
  }

  /**
   * @param alternativeQuoteStart is the {@link #getAltQuoteStart()
   *        alt-quote-start} character to set.
   */
  public void setAltQuoteStart(char alternativeQuoteStart) {

    this.altQuoteStart = alternativeQuoteStart;
  }

  /**
   * This method gets the alternative character used to end a quotation.
   * 
   * @see #getAltQuoteStart()
   * 
   * @return the alternative character used to end a quotation.
   */
  public char getAltQuoteEnd() {

    return this.altQuoteEnd;
  }

  /**
   * This method sets the {@link #getAltQuoteEnd() alt-quote-end} character.
   * 
   * @param alternativeQuoteEnd is the {@link #getAltQuoteEnd() alt-quote-end}
   *        character.
   */
  public void setAltQuoteEnd(char alternativeQuoteEnd) {

    this.altQuoteEnd = alternativeQuoteEnd;
  }

  /**
   * This method sets both the {@link #getAltQuoteStart() alt-quote-start} and
   * {@link #getAltQuoteEnd() alt-quote-end} character.
   * 
   * @param altQuote the alt-quote character to set.
   */
  public void setAltQuote(char altQuote) {

    this.altQuoteStart = altQuote;
    this.altQuoteEnd = altQuote;
  }

  /**
   * {@inheritDoc}
   */
  public char getAltQuoteEscape() {

    return this.altQuoteEscape;
  }

  /**
   * @param altQuoteEscape is the {@link #getAltQuoteEscape() alt-quote-escape}
   *        to set.
   */
  public void setAltQuoteEscape(char altQuoteEscape) {

    this.altQuoteEscape = altQuoteEscape;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAltQuoteEscapeLazy() {

    return this.altQuoteEscapeLazy;
  }

  /**
   * @param altQuoteEscapeLazy the {@link #isAltQuoteEscapeLazy()
   *        alt-quote-lazy} flag to set
   */
  public void setAltQuoteEscapeLazy(boolean altQuoteEscapeLazy) {

    this.altQuoteEscapeLazy = altQuoteEscapeLazy;
  }

  /**
   * {@inheritDoc}
   */
  public char getEntityStart() {

    return this.entityStart;
  }

  /**
   * @param entityStart the {@link #getEntityStart() entity-start} to set.
   */
  public void setEntityStart(char entityStart) {

    this.entityStart = entityStart;
  }

  /**
   * {@inheritDoc}
   */
  public char getEntityEnd() {

    return this.entityEnd;
  }

  /**
   * @param entityEnd the {@link #getEntityEnd() entity-end} to set.
   */
  public void setEntityEnd(char entityEnd) {

    this.entityEnd = entityEnd;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * You need to override this method if you want to
   * {@link #setEntityStart(char) use} entities.
   */
  public String resolveEntity(String entity) {

    throw new UnsupportedOperationException();
  }
}
