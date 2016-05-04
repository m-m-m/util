/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This class is a simple container for the {@link #getText() text} of a column together with its
 * {@link TextColumnInfo metadata}.
 * 
 * @see LineWrapper#wrap(Appendable, TextTableInfo, TextColumn[])
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class TextColumn {

  private  final String text;

  private  final TextColumnInfo columnInfo;

  /**
   * The constructor.
   * 
   * @param text is the {@link #getText() text}.
   * @param columnInfo is the {@link #getColumnInfo() column-info}.
   */
  public TextColumn(String text, TextColumnInfo columnInfo) {

    super();
    this.text = text;
    this.columnInfo = columnInfo;
  }

  /**
   * This method gets the text of this column.
   * 
   * @return the column text.
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method gets the {@link TextColumnInfo} holding the metadata of the column.
   * 
   * @return the column-info.
   */
  public TextColumnInfo getColumnInfo() {

    return this.columnInfo;
  }

}
