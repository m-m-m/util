/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.StringSyntax;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.scanner.base.SimpleCharScannerSyntax;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.ValueConverterIdentity;

/**
 * This is the implementation of {@link StringUtil}.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(StringUtil.CDI_NAME)
public class StringUtilImpl implements StringUtil {

  /** @see #getInstance() */
  private static StringUtil instance;

  /** @see #toCamlCase(String) */
  private static final char[] SEPARATORS = new char[] { ' ', '-', '_', '.' };

  /**
   * The constructor.
   */
  public StringUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StringUtilImpl}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static StringUtil getInstance() {

    if (instance == null) {
      synchronized (StringUtilImpl.class) {
        if (instance == null) {
          instance = new StringUtilImpl();
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLineSeparator() {

    return GwtHelper.getSystemProperty(SYSTEM_PROPERTY_LINE_SEPARATOR);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean parseBoolean(String booleanValue) {

    if (TRUE.equalsIgnoreCase(booleanValue)) {
      return Boolean.TRUE;
    } else if (FALSE.equalsIgnoreCase(booleanValue)) {
      return Boolean.FALSE;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void replace(char[] string, char oldChar, char newChar) {

    for (int i = 0; i < string.length; i++) {
      if (string[i] == oldChar) {
        string[i] = newChar;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String replace(String string, String match, String replace) {

    int matchLen = match.length();
    int replaceLen = replace.length();
    int delta = replaceLen - matchLen;
    if (delta < 0) {
      // assume no replacement...
      delta = 0;
    } else {
      // assume two replacements...
      delta = delta + delta;
    }
    StringBuilder result = new StringBuilder(string.length() + delta);
    int oldPos = 0;
    int pos = string.indexOf(match);
    while (pos >= 0) {
      if (pos > oldPos) {
        result.append(string.substring(oldPos, pos));
      }
      result.append(replace);
      oldPos = pos + matchLen;
      pos = string.indexOf(match, oldPos);
    }
    if (oldPos < string.length()) {
      result.append(string.substring(oldPos));
    }
    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSubstring(String string, String substring, int offset) {

    int stringLength = string.length();
    int substringLength = substring.length();
    int end = substringLength + offset;
    if (end <= stringLength) {
      // TODO: more efficient?
      return string.substring(offset, end).equals(substring);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSubstring(char[] string, String substring, int offset) {

    int substringLength = substring.length();
    if (substringLength + offset <= string.length) {
      for (int i = 0; i < substringLength; i++) {
        if (string[i + offset] != substring.charAt(i)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase) {

    return replaceSuffixWithCase(string, suffixLength, newSuffixLowerCase, Locale.ENGLISH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase, Locale locale) {

    int stringLength = string.length();
    int newSuffixLength = newSuffixLowerCase.length();
    int replaceIndex = stringLength - suffixLength;
    if (replaceIndex < 0) {
      replaceIndex = 0;
    }
    StringBuilder result = new StringBuilder(replaceIndex + newSuffixLength);
    if (replaceIndex > 0) {
      result.append(string.substring(0, replaceIndex));
    }
    if (suffixLength > 0) {
      char c = string.charAt(replaceIndex);
      // all lower case?
      if (c == Character.toLowerCase(c)) {
        result.append(newSuffixLowerCase);
      } else {
        // first replaced char is upper case!
        if (suffixLength > 1) {
          c = string.charAt(replaceIndex + 1);
          // Capitalized or upper case?
          if (c != Character.toLowerCase(c)) {
            // all upper case
            result.append(GwtHelper.toUpperCase(newSuffixLowerCase, locale));
          } else {
            // capitalize
            String first = newSuffixLowerCase.substring(0, 1);
            String capital = GwtHelper.toUpperCase(first, locale);
            if (capital.length() == 1) {
              result.append(newSuffixLowerCase);
              result.setCharAt(replaceIndex, capital.charAt(0));
            } else {
              result.append(capital);
              result.append(newSuffixLowerCase.substring(1));
            }
          }
        }
      }
    }
    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty(String string, boolean trim) {

    if (string == null) {
      return true;
    }
    String s;
    if (trim) {
      s = string.trim();
    } else {
      s = string;
    }
    return (s.length() == 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String padNumber(long number, int digits) {

    return padNumber(number, digits, 10);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String padNumber(long number, int digits, int radix) {

    String result = Long.toString(number, radix);
    int leadingZeros = digits - result.length();
    if (leadingZeros > 0) {
      int capacity = result.length() + leadingZeros;
      StringBuilder buffer = new StringBuilder(capacity);
      while (leadingZeros > 0) {
        buffer.append('0');
        leadingZeros--;
      }
      buffer.append(result);
      result = buffer.toString();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toCamlCase(String string) {

    return toCamlCase(string, SEPARATORS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toCamlCase(String string, char... separators) {

    char[] chars = string.toCharArray();
    StringBuilder buffer = new StringBuilder(chars.length);
    int pos = 0;
    boolean lastSeparator = false;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      boolean isSeparator = false;
      for (char s : separators) {
        if (c == s) {
          isSeparator = true;
          break;
        }
      }
      if (isSeparator) {
        lastSeparator = true;
        if (pos < i) {
          buffer.append(chars, pos, i - pos);
        }
        pos = i + 1;
      } else if (lastSeparator) {
        buffer.append(Character.toUpperCase(c));
        pos++;
        lastSeparator = false;
      }
    }
    if (!lastSeparator) {
      buffer.append(chars, pos, chars.length - pos);
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String fromCamlCase(String string, char separator) {

    char[] chars = string.toCharArray();
    StringBuilder buffer = new StringBuilder(chars.length + 4);
    boolean lastCharWasLowerCase = false;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      char lower = Character.toLowerCase(c);
      if (c == lower) {
        lastCharWasLowerCase = Character.isLowerCase(c);
      } else {
        if (lastCharWasLowerCase) {
          buffer.append(separator);
        }
        lastCharWasLowerCase = false;
      }
      buffer.append(lower);
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public String toSeparatedString(Collection<?> collection, String separator, StringSyntax syntax) {

    Formatter formatter = FormatterToString.getInstance();
    return toSeparatedString(collection, separator, syntax, formatter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> String toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax,
      Formatter<E> formatter) {

    StringBuilder buffer = new StringBuilder();
    toSeparatedString(collection, separator, syntax, formatter, buffer);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax,
      Formatter<E> formatter, Appendable buffer) {

    NlsNullPointerException.checkNotNull("separator", separator);
    if (separator.length() == 0) {
      throw new NlsIllegalArgumentException(separator, "separator");
    }
    try {
      char escape = syntax.getEscape();
      char start = syntax.getQuoteStart();
      char end = syntax.getQuoteEnd();
      char separatorChar = separator.charAt(0);
      String occurrence = null;
      String replacement = null;
      if (escape != '\0') {
        if (end != '\0') {
          occurrence = Character.toString(end);
        } else {
          occurrence = Character.toString(separatorChar);
        }
        replacement = escape + occurrence;
      }

      boolean notStart = false;
      for (E element : collection) {
        if (notStart) {
          buffer.append(separator);
        }
        notStart = true;
        if (start != '\0') {
          buffer.append(start);
        }
        if (escape == '\0') {
          formatter.format(element, buffer);
        } else {
          String elementString = formatter.format(element);
          elementString = elementString.replace(occurrence, replacement);
          buffer.append(elementString);
        }
        if (end != '\0') {
          buffer.append(end);
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax) {

    List<String> result = new ArrayList<String>();
    fromSeparatedString(separatedString, separator, syntax, result);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<String> collection) {

    ValueConverterIdentity<String> identityConverter = new ValueConverterIdentity<String>(String.class);
    fromSeparatedString(separatedString, separator, syntax, collection, identityConverter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<E> collection, ValueConverter<String, E> converter) {

    fromSeparatedString(separatedString, separator, syntax, collection, converter, converter.getTargetType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<E> collection, ValueConverter<? super String, ? super E> converter, Class<E> type) {

    NlsNullPointerException.checkNotNull("separator", separator);
    if (separator.length() == 0) {
      throw new NlsIllegalArgumentException(separator, "separator");
    }
    if (separatedString == null) {
      return;
    }
    CharSequenceScanner scanner = new CharSequenceScanner(separatedString);
    char escape = syntax.getEscape();
    char start = syntax.getQuoteStart();
    char end = syntax.getQuoteEnd();
    char separatorChar = separator.charAt(0);
    SimpleCharScannerSyntax scannerSyntax = new SimpleCharScannerSyntax();
    scannerSyntax.setEscape(escape);
    while (scanner.hasNext()) {
      if (start != '\0') {
        scanner.require(start);
      }
      String elementString;
      if (end == '\0') {
        elementString = scanner.readUntil(separatorChar, true, scannerSyntax);
        scanner.stepBack();
        if (scanner.peek() == separatorChar) {
          scanner.require(separator, false);
        } else {
          scanner.next();
          assert !scanner.hasNext();
        }
      } else {
        elementString = scanner.readUntil(end, false, scannerSyntax);
        if (scanner.hasNext()) {
          scanner.require(separator, false);
        }
      }
      if (elementString == null) {
        StringBuilder format = new StringBuilder();
        if (start != '\0') {
          format.append('\'');
          format.append(start);
          format.append('\'');
        }
        format.append(" <value> ");
        if (end != '\0') {
          format.append('\'');
          format.append(end);
          format.append('\'');
        }
        String value = format.toString();
        format.append(" ['");
        format.append(separator);
        format.append("' ");
        format.append(value);
        format.append("]*");
        throw new NlsParseException(separatedString, format, collection.getClass());
      }
      E element = converter.convert(elementString, separatedString, type);
      collection.add(element);
    }
  }
}
