/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

/**
 * This Class contains the state of an HTML
 * {@link XmlUtil#extractPlainText(String, StringBuilder, ParserState) parsing} process.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ParserState {

  /** @see #getTagStartIndex() */
  private int tagStartIndex;

  /** @see #getInAttribute() */
  private char inAttribute;

  /** @see #getCdataCloseCount() */
  private int cdataCloseCount;

  /**
   * The constructor.
   */
  public ParserState() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param other is the state to continue with.
   */
  public ParserState(ParserState other) {

    super();
    if (other != null) {
      if (other.tagStartIndex != 0) {
        this.tagStartIndex = -1;
      } else {
        this.tagStartIndex = 0;
      }
      this.inAttribute = other.inAttribute;
      this.cdataCloseCount = other.cdataCloseCount;
    }
  }

/**
   * @return index of first char after '<' in string or 0 if not in tag.
   */
  public int getTagStartIndex() {

    return this.tagStartIndex;
  }

  /**
   * @param tagStartIndex is the tagStartIndex to set
   */
  public void setTagStartIndex(int tagStartIndex) {

    this.tagStartIndex = tagStartIndex;
  }

  /**
   * @return the opening quotation char if inside attribute, else 0.
   */
  public char getInAttribute() {

    return this.inAttribute;
  }

  /**
   * @param inAttribute is the inAttribute to set
   */
  public void setInAttribute(char inAttribute) {

    this.inAttribute = inAttribute;
  }

  /**
   * @return the CDATA close character count. This value is set to 3 at start of CDATA section, count down for
   *         closing "]]>" but reset to 3 on mismatch.
   */
  public int getCdataCloseCount() {

    return this.cdataCloseCount;
  }

  /**
   * @param cdataCloseCount is the cdataCloseCount to set
   */
  public void setCdataCloseCount(int cdataCloseCount) {

    this.cdataCloseCount = cdataCloseCount;
  }

}
