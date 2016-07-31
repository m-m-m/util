/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsFormatterPlugin;

/**
 * This class is like a {@link Map} to {@link #registerFormatter(NlsFormatterPlugin, String, String) register} and
 * {@link #getFormatter(String, String) retrieve} {@link NlsFormatter}s. <br>
 * <b>ATTENTION:</b><br>
 * The {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter() default formatter} is NOT stored in this map.
 *
 * @see net.sf.mmm.util.nls.impl.ConfiguredNlsFormatterMap
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class NlsFormatterMap extends AbstractComponent {

  /** @see #getFormatter(String, String) */
  private final Map<String, Map<String, NlsFormatterPlugin<?>>> builders;

  /**
   * The constructor.
   */
  public NlsFormatterMap() {

    super();
    this.builders = new HashMap<>();
  }

  /**
   * This method registers the given {@code formatBuilder}.
   *
   * @param formatter is the {@link NlsFormatterPlugin} to register.
   * @return the {@link NlsFormatter} that was registered for the given {@link NlsFormatterPlugin#getType() type} and
   *         {@link NlsFormatterPlugin#getStyle() style} and is now replaced by the given {@code formatter} or
   *         {@code null} if no {@link NlsFormatter} was replaced.
   */
  public NlsFormatter<?> registerFormatter(NlsFormatterPlugin<?> formatter) {

    return registerFormatter(formatter, formatter.getType(), formatter.getStyle());
  }

  /**
   * This method registers the given {@code formatBuilder}.
   *
   * @param formatter is the formatter to register.
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting. May be {@code null} for default formatter.
   * @return the {@link NlsFormatter} that was registered for the given {@code formatType} and {@code formatStyle} and
   *         is now replaced by the given {@code formatter} or {@code null} if no {@link NlsFormatter} was replaced.
   */
  public NlsFormatter<?> registerFormatter(NlsFormatterPlugin<?> formatter, String formatType,
      String formatStyle) {

    NlsNullPointerException.checkNotNull(NlsFormatter.class, formatter);
    if ((formatType == null) && (formatStyle != null)) {
      throw new NullPointerException("formatType");
    }
    Map<String, NlsFormatterPlugin<?>> style2builderMap = this.builders.get(formatType);
    if (style2builderMap == null) {
      style2builderMap = new HashMap<>();
      this.builders.put(formatType, style2builderMap);
    }
    return style2builderMap.put(formatStyle, formatter);
  }

  /**
   * @see net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String, String)
   *
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting. May be {@code null} for default formatter of the
   *        given {@code formatType}.
   * @return the according {@link NlsFormatter} instance or {@code null} if NO such {@link NlsFormatter} is
   *         {@link #registerFormatter(NlsFormatterPlugin, String, String) registered} .
   */
  public NlsFormatterPlugin<?> getFormatter(String formatType, String formatStyle) {

    NlsFormatterPlugin<?> result = null;
    Map<String, NlsFormatterPlugin<?>> style2builderMap = this.builders.get(formatType);
    if (style2builderMap != null) {
      result = style2builderMap.get(formatStyle);
      if ((result == null) && (formatStyle == null)) {
        result = style2builderMap.get(NlsFormatterManager.STYLE_MEDIUM);
      }
    }
    return result;
  }

}
