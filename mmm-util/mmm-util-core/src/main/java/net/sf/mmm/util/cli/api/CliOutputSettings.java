/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.lang.api.StringUtil;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliOutputSettings {

  /** @see #getWidth() */
  private int width;

  /** @see #getLineSeparator() */
  private String lineSeparator;

  /**
   * The constructor.
   */
  public CliOutputSettings() {

    super();
    this.lineSeparator = StringUtil.LINE_SEPARATOR;
  }

  /**
   * The constructor.
   * 
   * @param width is the {@link #getWidth() width}.
   */
  public CliOutputSettings(int width) {

    super();
    this.width = width;
  }

  /**
   * @return the width
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * @param width is the width to set
   */
  public void setWidth(int width) {

    this.width = width;
  }

  /**
   * @return the lineSeparator
   */
  public String getLineSeparator() {

    return this.lineSeparator;
  }

  /**
   * @param lineSeparator is the lineSeparator to set
   */
  public void setLineSeparator(String lineSeparator) {

    this.lineSeparator = lineSeparator;
  }

}
