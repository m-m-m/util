/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.BasicHelper;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.StringSyntax;
import net.sf.mmm.util.lang.api.StringUtil;
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
public class StringUtilImpl implements StringUtil {

  private static final Character CHAR_TAB = Character.valueOf('\t');

  private static final Character CHAR_BACKSLASH = Character.valueOf('\\');

  private static final Character CHAR_CR = Character.valueOf('\r');

  private static final Character CHAR_LF = Character.valueOf('\n');

  private static final Character CHAR_SINGLE_QUOTE = Character.valueOf('\'');

  private static final Character CHAR_DOUBLE_QUOTE = Character.valueOf('\"');

  private static final Character CHAR_FF = Character.valueOf('\f');

  private static final Character CHAR_BS = Character.valueOf('\b');

  private static final Character CHAR_NUL = Character.valueOf('\0');

  private static final Character CHAR_SOH = Character.valueOf('\1');

  private static final Character CHAR_STX = Character.valueOf('\2');

  private static final Character CHAR_ETX = Character.valueOf('\3');

  private static final Character CHAR_EOT = Character.valueOf('\4');

  private static final Character CHAR_ENQ = Character.valueOf('\5');

  private static final Character CHAR_ACK = Character.valueOf('\6');

  private static final Character CHAR_BEL = Character.valueOf('\7');

  private static StringUtil instance;

  private static final char[] SEPARATORS = new char[] { ' ', '-', '_', '.' };

  private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /**
   * The constructor.
   */
  public StringUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StringUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
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

  @Override
  public String getLineSeparator() {

    return BasicHelper.getSystemProperty(SYSTEM_PROPERTY_LINE_SEPARATOR);
  }

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

  @Override
  public void replace(char[] string, char oldChar, char newChar) {

    for (int i = 0; i < string.length; i++) {
      if (string[i] == oldChar) {
        string[i] = newChar;
      }
    }
  }

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

  @Override
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase) {

    return replaceSuffixWithCase(string, suffixLength, newSuffixLowerCase, Locale.ENGLISH);
  }

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
      // is lower case?
      if (c == Character.toLowerCase(c)) {
        result.append(newSuffixLowerCase);
      } else {
        // first replaced char is upper case!
        if (suffixLength > 1) {
          c = string.charAt(replaceIndex + 1);
          // is lower case?
          if (c == Character.toLowerCase(c)) {
            // capitalize
            String first = newSuffixLowerCase.substring(0, 1);
            String capital = BasicHelper.toUpperCase(first, locale);
            if (capital.length() == 1) {
              result.append(newSuffixLowerCase);
              result.setCharAt(replaceIndex, capital.charAt(0));
            } else {
              result.append(capital);
              result.append(newSuffixLowerCase.substring(1));
            }
          } else {
            // all upper case
            result.append(BasicHelper.toUpperCase(newSuffixLowerCase, locale));
          }
        }
      }
    }
    return result.toString();
  }

  @Override
  public boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

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

  @Override
  public boolean isAllLowerCase(String string) {

    if (string != null) {
      for (char c : string.toCharArray()) {
        if (Character.toLowerCase(c) != c) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isAllUpperCase(String string) {

    if (string != null) {
      for (char c : string.toCharArray()) {
        if (Character.toUpperCase(c) != c) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String padNumber(long number, int digits) {

    return padNumber(number, digits, 10);
  }

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

  @Override
  public String toCamlCase(String string) {

    return toCamlCase(string, SEPARATORS);
  }

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

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public String toSeparatedString(Collection<?> collection, String separator, StringSyntax syntax) {

    Formatter formatter = FormatterToString.getInstance();
    return toSeparatedString(collection, separator, syntax, formatter);
  }

  @Override
  public <E> String toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax, Formatter<E> formatter) {

    StringBuilder buffer = new StringBuilder();
    toSeparatedString(collection, separator, syntax, formatter, buffer);
    return buffer.toString();
  }

  @Override
  public <E> void toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax, Formatter<E> formatter, Appendable buffer) {

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

  @Override
  public List<String> fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax) {

    List<String> result = new ArrayList<>();
    fromSeparatedString(separatedString, separator, syntax, result);
    return result;
  }

  @Override
  public void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax, Collection<String> collection) {

    ValueConverterIdentity<String> identityConverter = new ValueConverterIdentity<>(String.class);
    fromSeparatedString(separatedString, separator, syntax, collection, identityConverter);
  }

  @Override
  public <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax, Collection<E> collection,
      ValueConverter<String, E> converter) {

    fromSeparatedString(separatedString, separator, syntax, collection, converter, converter.getTargetType());
  }

  @Override
  public <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax, Collection<E> collection,
      ValueConverter<? super String, ? super E> converter, Class<E> type) {

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

  @Override
  public String toHex(byte[] data) {

    char[] buffer = new char[data.length * 2];
    int i = 0;
    for (byte b : data) {
      buffer[i++] = HEX[(b & 0xF0) >> 4];
      buffer[i++] = HEX[b & 0x0F];
    }
    return new String(buffer);
  }

  @Override
  public Character resolveEscape(char c) {

    switch (c) {
      case '0':
        return CHAR_NUL;
      case 't':
        return CHAR_TAB;
      case '\\':
        return CHAR_BACKSLASH;
      case 'r':
        return CHAR_CR;
      case 'n':
        return CHAR_LF;
      case '\'':
        return CHAR_SINGLE_QUOTE;
      case '\"':
        return CHAR_DOUBLE_QUOTE;
      case 'f':
        return CHAR_FF;
      case 'b':
        return CHAR_BS;
      case '1':
        return CHAR_SOH;
      case '2':
        return CHAR_STX;
      case '3':
        return CHAR_ETX;
      case '4':
        return CHAR_EOT;
      case '5':
        return CHAR_ENQ;
      case '6':
        return CHAR_ACK;
      case '7':
        return CHAR_BEL;
    }
    return null;
  }

  @Override
  public Character resolveEscape(String sequence) {

    if (sequence == null) {
      return null;
    }
    int length = sequence.length();
    if (length == 0) {
      return null;
    } else if (length == 1) {
      return resolveEscape(sequence.charAt(0));
    } else if (length < 4) { // octal C compatibility legacy stuff
      for (int i = 0; i < length; i++) {
        char c = sequence.charAt(i);
        if ((c < '0') || (c > '7')) {
          return null;
        }
      }
      int value = Integer.parseInt(sequence, 8);
      if (value <= 0377) {
        return Character.valueOf((char) value);
      }
    } else if (length >= 5) {
      int start = length - 4;
      for (int i = start - 1; i >= 0; i--) {
        if (sequence.charAt(i) != 'u') {
          return null;
        }
      }
      int value = Integer.parseInt(sequence.substring(start), 16);
      return Character.valueOf((char) value);
    }
    return null;
  }
}
