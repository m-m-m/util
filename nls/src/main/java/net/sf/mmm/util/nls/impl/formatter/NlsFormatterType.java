/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsFormatterPlugin;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} that formats {@link Type}s.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsFormatterType extends AbstractNlsFormatterPlugin<Object> {

  /** The package {@code java.lang}. */
  private static final Package PACKAGE_JAVA_LANG = Package.class.getPackage();

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

  @Override
  public String getStyle() {

    return this.style;
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_TYPE;
  }

  @Override
  public void format(Object object, Locale locale, Map<String, Object> arguments, NlsTemplateResolver resolver, Appendable buffer) throws IOException {

    if (object == null) {
      buffer.append("null");
    } else if (object instanceof Type) {
      toString((Type) object, buffer);
    } else {
      buffer.append(object.toString());
    }
  }

  private void toString(Type type, Appendable appendable) {

    try {
      if (type instanceof Class<?>) {
        Class<?> clazz = (Class<?>) type;
        if (this.style.equals(NlsFormatterManager.STYLE_SHORT)) {
          appendable.append(clazz.getSimpleName());
        } else if (this.style.equals(NlsFormatterManager.STYLE_LONG) || this.style.equals(NlsFormatterManager.STYLE_MEDIUM)) {
          if (PACKAGE_JAVA_LANG.equals(clazz.getPackage())) {
            appendable.append(clazz.getSimpleName());
          } else {
            appendable.append(clazz.getCanonicalName());
          }
        } else {
          appendable.append(clazz.getCanonicalName());
        }
      } else if (type instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        boolean longOrFullStyle = this.style.equals(NlsFormatterManager.STYLE_LONG) || this.style.equals(NlsFormatterManager.STYLE_FULL);
        if (longOrFullStyle) {
          Type ownerType = parameterizedType.getOwnerType();
          if (ownerType != null) {
            toString(ownerType, appendable);
            appendable.append('.');
          }
        }
        toString(parameterizedType.getRawType(), appendable);
        if (longOrFullStyle) {
          appendable.append('<');
          boolean separator = false;
          for (Type arg : parameterizedType.getActualTypeArguments()) {
            if (separator) {
              appendable.append(", ");
            }
            toString(arg, appendable);
            separator = true;
          }
          appendable.append('>');
        }
      } else if (type instanceof TypeVariable<?>) {
        TypeVariable<?> typeVariable = (TypeVariable<?>) type;
        appendable.append(typeVariable.getName());
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0) {
          // is this supported after all?
          Type firstBound = bounds[0];
          if (!Object.class.equals(firstBound)) {
            appendable.append(" extends ");
            toString(firstBound, appendable);
          }
        }
      } else if (type instanceof WildcardType) {
        WildcardType wildcardType = (WildcardType) type;
        Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length > 0) {
          appendable.append("? super ");
          toString(lowerBounds[0], appendable);
        } else {
          Type[] upperBounds = wildcardType.getUpperBounds();
          if (upperBounds.length > 0) {
            appendable.append("? extends ");
            toString(upperBounds[0], appendable);
          } else {
            appendable.append("?");
          }
        }
      } else if (type instanceof GenericArrayType) {
        toString(((GenericArrayType) type).getGenericComponentType(), appendable);
        appendable.append("[]");
      } else {
        appendable.append(type.toString());
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error writing type to Appendable.", e);
    }
  }

}
