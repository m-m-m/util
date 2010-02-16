/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.Visitor;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsSubFormatter;
import net.sf.mmm.util.nls.base.NlsFormatterMap;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * that formats {@link Type}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class NlsFormatterType extends AbstractNlsSubFormatter<Object> {

  /** The package <code>java.lang</code>. */
  private static final Package PACKAGE_JAVA_LANG = Package.class.getPackage();

  /** @see #getStyle() */
  private final String style;

  /** @see #getReflectionUtil() */
  private final ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   * 
   * @param style is the {@link #getStyle() style}.
   * @param reflectionUtil is the {@link #getReflectionUtil() ReflectionUtil}.
   */
  public NlsFormatterType(String style, ReflectionUtil reflectionUtil) {

    super();
    this.style = style;
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getType() {

    return NlsFormatterManager.TYPE_TYPE;
  }

  /**
   * @return the reflectionUtil
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(NlsFormatterMap formatterMap) {

    super.register(formatterMap);
    if (NlsFormatterManager.STYLE_MEDIUM.equals(this.style)) {
      formatterMap.registerFormatter(this, getType(), null);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments,
      NlsTemplateResolver resolver, Appendable buffer) throws IOException {

    if (object == null) {
      buffer.append("null");
    } else if (object instanceof Type) {
      Type type = (Type) object;
      if (NlsFormatterManager.STYLE_SHORT.equals(this.style)) {
        Class<?> rawType = this.reflectionUtil.createGenericType(type).getAssignmentClass();
        buffer.append(rawType.getSimpleName());
      } else if (NlsFormatterManager.STYLE_MEDIUM.equals(this.style)) {
        Class<?> rawType = this.reflectionUtil.createGenericType(type).getAssignmentClass();
        if (PACKAGE_JAVA_LANG.equals(rawType.getPackage())) {
          buffer.append(rawType.getSimpleName());
        } else {
          buffer.append(rawType.getCanonicalName());
        }
      } else {
        ClassFormatter formatter = new ClassFormatter(buffer);
        this.reflectionUtil.toString(type, buffer, formatter);
      }
    } else {
      buffer.append(object.toString());
    }
  }

  /**
   * This inner class is used to format {@link Class}es.
   */
  private class ClassFormatter implements Visitor<Class<?>> {

    /** @see #visit(Class) */
    private final Appendable buffer;

    /**
     * The constructor.
     * 
     * @param buffer is the {@link Appendable}.
     */
    public ClassFormatter(Appendable buffer) {

      super();
      this.buffer = buffer;
    }

    /**
     * {@inheritDoc}
     */
    public void visit(Class<?> value) {

      try {
        if (NlsFormatterManager.STYLE_LONG.equals(NlsFormatterType.this.style)) {
          if (PACKAGE_JAVA_LANG.equals(value.getPackage())) {
            this.buffer.append(value.getSimpleName());
          } else {
            this.buffer.append(value.getCanonicalName());
          }
        } else {
          this.buffer.append(value.getCanonicalName());
        }
      } catch (IOException e) {
        throw new RuntimeIoException(e);
      }
    }
  }
}
