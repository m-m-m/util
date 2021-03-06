/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import javax.inject.Inject;

import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterPlugin;

/**
 * This is the implementation of the {@link net.sf.mmm.util.nls.api.NlsFormatterManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class MappedNlsFormatterManager extends AbstractNlsFormatterManager {

  /** @see #getFormatter(String, String) */
  private NlsFormatterMap formatterMap;

  /**
   * The constructor.
   */
  public MappedNlsFormatterManager() {

    super();
  }

  /**
   * The constructor.
   *
   * @param formatterMap is the map with the registered formatters.
   */
  public MappedNlsFormatterManager(NlsFormatterMap formatterMap) {

    super();
    this.formatterMap = formatterMap;
  }

  /**
   * This method creates the {@link NlsFormatter} for the given {@code formatType} and the custom {@code subformat}. It
   * is called if no formatter is {@link NlsFormatterMap#registerFormatter(NlsFormatterPlugin, String, String)
   * registered} for the given arguments. <br>
   *
   * @param formatType is the type to be formatted.
   * @param subformat is the custom formatStyle for which no static formatter is registered.
   * @return the according custom formatter or {@code null} if no such formatter is could be created.
   */
  protected NlsFormatterPlugin<Object> getSubFormatter(String formatType, String subformat) {

    return null;
  }

  @Override
  public NlsFormatterPlugin<?> getFormatter(String formatType, String formatStyle) {

    NlsFormatterPlugin<?> result = null;
    result = this.formatterMap.getFormatter(formatType, formatStyle);
    if (result == null) {
      if (formatStyle != null) {
        result = getSubFormatter(formatType, formatStyle);
        if (result == null) {
          result = this.formatterMap.getFormatter(formatType, null);
        }
      }
    }
    if (result == null) {
      if (formatType == null) {
        throw new IllegalStateException();
      }
    }
    return result;
  }

  /**
   * @return the formatterMap
   */
  protected NlsFormatterMap getFormatterMap() {

    return this.formatterMap;
  }

  /**
   * @param formatterMap is the formatterMap to set
   */
  @Inject
  public void setFormatterMap(NlsFormatterMap formatterMap) {

    getInitializationState().requireNotInitilized();
    this.formatterMap = formatterMap;
  }

}
