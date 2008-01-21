/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsFormatter;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class MappedNlsFormatterManager extends AbstractNlsFormatterManager {

  /** @see #getFormatter(String, String) */
  private final NlsFormatterMap formatterMap;

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
   * This method creates the {@link NlsFormatter} for the given
   * <code>formatType</code> and the custom <code>subformat</code>. It is
   * called if no formatter is
   * {@link NlsFormatterMap#registerFormatter(NlsFormatter, String, String) registered}
   * for the given arguments.<br>
   * 
   * @param formatType is the type to be formatted.
   * @param subformat is the custom formatStyle for which no static formatter is
   *        registered.
   * @return the according custom formatter or <code>null</code> if no such
   *         formatter is could be created.
   */
  protected NlsFormatter<Object> getSubFormatter(String formatType, String subformat) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract NlsFormatter<Object> getFormatter();

  /**
   * {@inheritDoc}
   */
  public NlsFormatter<Object> getFormatter(String formatType, String formatStyle) {

    NlsFormatter<Object> result = null;
    if (formatType != null) {
      result = this.formatterMap.getFormatter(formatType, formatStyle);
      if (result == null) {
        if (formatStyle != null) {
          result = getSubFormatter(formatType, formatStyle);
          if (result == null) {
            result = this.formatterMap.getFormatter(formatType, null);
          }
        }
      }
    }
    if (result == null) {
      result = getFormatter();
    }
    return result;
  }

  /**
   * This inner class is like a {@link Map} to
   * {@link #registerFormatter(NlsFormatter, String, String) register} and
   * {@link #getFormatter(String, String) retrieve} {@link NlsFormatter}s.<br>
   * <b>ATTENTION:</b><br>
   * The
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter() default formatter}
   * is NOT stored in this map.
   */
  protected static class NlsFormatterMap {

    /** @see #getFormatter(String, String) */
    private final Map<String, Map<String, NlsFormatter<Object>>> builders;

    /**
     * The constructor.
     */
    public NlsFormatterMap() {

      super();
      this.builders = new HashMap<String, Map<String, NlsFormatter<Object>>>();
    }

    /**
     * This method registers the given <code>formatBuilder</code>.
     * 
     * @param formatter is the formatter to register.
     * @param formatType is the type to be formatted.
     * @param formatStyle is the style defining details of formatting. May be
     *        <code>null</code> for default formatter.
     * @return the {@link NlsFormatter} that was registered for the given
     *         <code>formatType</code> and <code>formatStyle</code> and is
     *         now replaced by the given <code>formatter</code> or
     *         <code>null</code> if no {@link NlsFormatter} was replaced.
     */
    public NlsFormatter<Object> registerFormatter(NlsFormatter<Object> formatter,
        String formatType, String formatStyle) {

      if (formatter == null) {
        throw new NullPointerException();
      }
      if (formatType == null) {
        throw new NullPointerException();
      }
      Map<String, NlsFormatter<Object>> style2builderMap = this.builders.get(formatType);
      if (style2builderMap == null) {
        style2builderMap = new HashMap<String, NlsFormatter<Object>>();
        this.builders.put(formatType, style2builderMap);
      }
      // if (style2builderMap.containsKey(formatStyle)) {
      // // if NLS is broken there is no need for NlsThrowable here...
      // throw new IllegalStateException("Format-builder for type (" +
      // formatType + ") and style ("
      // + formatStyle + ") already registered!");
      // }
      return style2builderMap.put(formatStyle, formatter);
    }

    /**
     * @see net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String,
     *      String)
     * 
     * @param formatType is the type to be formatted.
     * @param formatStyle is the style defining details of formatting. May be
     *        <code>null</code> for default formatter of the given
     *        <code>formatType</code>.
     * @return the according {@link NlsFormatter} instance or <code>null</code>
     *         if NO such {@link NlsFormatter} is
     *         {@link #registerFormatter(NlsFormatter, String, String) registered}.
     */
    public NlsFormatter<Object> getFormatter(String formatType, String formatStyle) {

      NlsFormatter<Object> result = null;
      if (formatType != null) {
        Map<String, NlsFormatter<Object>> style2builderMap = this.builders.get(formatType);
        if (style2builderMap != null) {
          result = style2builderMap.get(formatStyle);
        }
      }
      return result;
    }

  }

}
