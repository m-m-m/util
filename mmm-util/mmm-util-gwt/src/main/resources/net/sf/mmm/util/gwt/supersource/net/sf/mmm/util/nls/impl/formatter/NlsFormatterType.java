/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} that formats
 * {@link java.lang.reflect.Type}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsFormatterType extends AbstractNlsFormatterPlugin<Object> {

  /** The package prefix <code>{@value}</code>. */
  private static final String PREFIX_JAVA_LANG = "java.lang.";

  /** @see #getStyle() */
  private final String style;

  /**
   * The constructor.
   * 
   * @param style is the {@link #getStyle() style}.
   */
  public NlsFormatterType(String style) {

    super();
    this.style = style;
  }

  /**
   * {@inheritDoc}
   */
  public String getStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return NlsFormatterManager.TYPE_TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    if (object == null) {
      buffer.append("null");
    } else if (object instanceof Class) {
      Class<?> type = (Class<?>) object;
      String result = type.getName();
      if (NlsFormatterManager.STYLE_SHORT.equals(this.style)) {
        result = type.getSimpleName();
      } else if (NlsFormatterManager.STYLE_MEDIUM.equals(this.style)) {
        if (result.startsWith(PREFIX_JAVA_LANG)) {
          result = result.substring(PREFIX_JAVA_LANG.length());
        }
      }
      buffer.append(result);
    } else {
      buffer.append(object.toString());
    }
  }

}
