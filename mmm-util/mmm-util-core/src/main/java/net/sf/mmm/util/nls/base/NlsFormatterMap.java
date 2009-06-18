/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsFormatter;

/**
 * This class is like a {@link Map} to
 * {@link #registerFormatter(NlsFormatter, String, String) register} and
 * {@link #getFormatter(String, String) retrieve} {@link NlsFormatter}s.<br>
 * <b>ATTENTION:</b><br>
 * The {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter() default
 * formatter} is NOT stored in this map.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class NlsFormatterMap {

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
   *         <code>formatType</code> and <code>formatStyle</code> and is now
   *         replaced by the given <code>formatter</code> or <code>null</code>
   *         if no {@link NlsFormatter} was replaced.
   */
  public NlsFormatter<Object> registerFormatter(NlsFormatter<Object> formatter, String formatType,
      String formatStyle) {

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
   * @return the according {@link NlsFormatter} instance or <code>null</code> if
   *         NO such {@link NlsFormatter} is
   *         {@link #registerFormatter(NlsFormatter, String, String) registered}
   *         .
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
