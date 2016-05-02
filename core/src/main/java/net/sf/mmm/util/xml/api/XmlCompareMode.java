/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

/**
 * This class is a simple bean that represents the mode when comparing XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class XmlCompareMode {

  /** @see #isCheckComments() */
  private boolean checkComments;

  /** @see #isNormalizeSpaces() */
  private boolean normalizeSpaces;

  /** @see #isJoinText() */
  private boolean joinText;

  /** @see #isJoinCData() */
  private boolean joinCData;

  /** @see #isJoinComment() */
  private boolean joinComment;

  /**
   * The constructor.
   */
  public XmlCompareMode() {

    super();
    this.joinText = true;
    this.joinCData = true;
  }

  /**
   * This method determines how {@link org.w3c.dom.Comment}s are compared.
   * 
   * @return {@code true} if comments will be compared, {@code false} to ignore comments.
   */
  public boolean isCheckComments() {

    return this.checkComments;
  }

  /**
   * @param checkComments is the checkComments to set
   */
  public void setCheckComments(boolean checkComments) {

    this.checkComments = checkComments;
  }

  /**
   * This method determines how to deal with <em>whitespace-characters</em> {@code ' '} ,
   * {@code '\t'},{@code '\r'}, and {@code '\n'} when comparing content of
   * {@link org.w3c.dom.Text} (or {@link org.w3c.dom.CDATASection}).
   * 
   * @return {@code true} if leading and trailing whitespace-characters will be ignored around
   *         line-breaks (breaking and indentation does NOT matter) and {@code false} if all
   *         whitespace-characters have to be equal.
   */
  public boolean isNormalizeSpaces() {

    return this.normalizeSpaces;
  }

  /**
   * @param normalizeSpaces is the normalizeSpaces to set
   */
  public void setNormalizeSpaces(boolean normalizeSpaces) {

    this.normalizeSpaces = normalizeSpaces;
  }

  /**
   * This method determines if subsequent {@link org.w3c.dom.Text}-nodes should be treated as one single
   * {@link org.w3c.dom.Text}-node when comparing XML. The {@link #XmlCompareMode() default} is
   * {@code true}. If both {@link #isJoinText() joinText} {@link #isJoinCData() joinCData} are set,
   * {@link org.w3c.dom.Text} and {@link org.w3c.dom.CDATASection} are treated as equal, what means that all
   * text between nodes is treated as single text no matter what node-structure is used to represent it.
   * 
   * @return {@code true} if the content of {@link org.w3c.dom.Text}-nodes should joined before
   *         comparison, {@code false} if each {@link org.w3c.dom.Text}-node is compared separately.
   */
  public boolean isJoinText() {

    return this.joinText;
  }

  /**
   * @param joinText is the joinText to set
   */
  public void setJoinText(boolean joinText) {

    this.joinText = joinText;
  }

  /**
   * This method determines if subsequent {@link org.w3c.dom.CDATASection}-nodes should be treated as one
   * single {@link org.w3c.dom.CDATASection}-node when comparing XML. The {@link #XmlCompareMode() default} is
   * {@code true}. If both {@link #isJoinText() joinText} {@link #isJoinCData() joinCData} are set,
   * {@link org.w3c.dom.Text} and {@link org.w3c.dom.CDATASection} are treated as equal, what means that all
   * text between nodes is treated as single text no matter what node-structure is used to represent it.
   * 
   * @return {@code true} if the content of {@link org.w3c.dom.CDATASection}-nodes should joined before
   *         comparison, {@code false} if each {@link org.w3c.dom.CDATASection}-node is compared
   *         separately.
   */
  public boolean isJoinCData() {

    return this.joinCData;
  }

  /**
   * @param joinCData is the joinCData to set
   */
  public void setJoinCData(boolean joinCData) {

    this.joinCData = joinCData;
  }

  /**
   * This method determines if subsequent {@link org.w3c.dom.Comment}-nodes should be treated as one single
   * {@link org.w3c.dom.Comment}-node when comparing XML.
   * 
   * @return {@code true} if the content of {@link org.w3c.dom.Comment} -nodes should joined before
   *         comparison, {@code false} if each {@link org.w3c.dom.Comment}-node is compared separately.
   */
  public boolean isJoinComment() {

    return this.joinComment;
  }

  /**
   * @param joinComments is the joinComments to set
   */
  public void setJoinComment(boolean joinComments) {

    this.joinComment = joinComments;
  }

}
