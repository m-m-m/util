/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.util.Locale;

import net.sf.mmm.util.exception.api.ValueOutOfRangeException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This class represents the settings for the {@link CliParser#printHelp(Appendable, CliOutputSettings) help output}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOutputSettings {

  /** The minimum value for {@link #getWidth() width}. */
  private static final int MIN_WIDTH = 5;

  private int width;

  private String lineSeparator;

  private Locale locale;

  private NlsTemplateResolver templateResolver;

  /**
   * The constructor.
   */
  public CliOutputSettings() {

    super();
    this.lineSeparator = System.lineSeparator();
    this.width = 80;
    this.locale = Locale.getDefault();
    this.templateResolver = null;
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
   * This method gets the width of the {@link CliParser#printHelp(Appendable, CliOutputSettings) help-output} in
   * characters per line.
   *
   * @return the width.
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * @param width is the {@link #getWidth() width} to set.
   */
  public void setWidth(int width) {

    if (width < MIN_WIDTH) {
      throw new ValueOutOfRangeException(Integer.valueOf(width), (Number) Integer.valueOf(MIN_WIDTH), Integer.valueOf(Integer.MAX_VALUE), "width");
    }
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

  /**
   * This method gets the {@link Locale} used to nationalize the output. By default {@link Locale#getDefault()} ist
   * used.
   *
   * @return the {@link Locale}.
   */
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * This method sets the {@link #getLocale() locale} to use.
   *
   * @param locale is the locale to set
   */
  public void setLocale(Locale locale) {

    this.locale = locale;
  }

  /**
   * @return the templateResolver
   */
  public NlsTemplateResolver getTemplateResolver() {

    return this.templateResolver;
  }

  /**
   * @param templateResolver is the templateResolver to set
   */
  public void setTemplateResolver(NlsTemplateResolver templateResolver) {

    this.templateResolver = templateResolver;
  }

}
