/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Locale;

import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the implementation of the {@link StringUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
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
   * This method gets the singleton instance of this {@link StringUtilImpl}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
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
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase) {

    return replaceSuffixWithCase(string, suffixLength, newSuffixLowerCase, Locale.ENGLISH);
  }

  /**
   * {@inheritDoc}
   */
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase,
      Locale locale) {

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
            result.append(newSuffixLowerCase.toUpperCase(locale));
          } else {
            // capitalize
            String capital = newSuffixLowerCase.substring(0, 1).toUpperCase(locale);
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
  public boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

  /**
   * {@inheritDoc}
   */
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
  public String padNumber(long number, int digits) {

    return padNumber(number, digits, 10);
  }

  /**
   * {@inheritDoc}
   */
  public String padNumber(long number, int digits, int radix) {

    String result = Long.toString(number, radix);
    int leadingZeros = digits - result.length();
    if (leadingZeros > 0) {
      int capacity = result.length() + leadingZeros;
      StringBuilder buffer = new StringBuilder(capacity);
      buffer.append(result);
      while (leadingZeros > 0) {
        buffer.append('0');
        leadingZeros--;
      }
      result = buffer.toString();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public String toCamlCase(String string) {

    return toCamlCase(string, SEPARATORS);
  }

  /**
   * {@inheritDoc}
   */
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
}
