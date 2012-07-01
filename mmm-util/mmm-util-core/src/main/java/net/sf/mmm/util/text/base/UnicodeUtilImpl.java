/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.text.api.DiacriticalMark;
import net.sf.mmm.util.text.api.UnicodeUtil;

/**
 * This is the implementation of the {@link UnicodeUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@SuppressWarnings("boxing")
public class UnicodeUtilImpl implements UnicodeUtil {

  /** @see #getInstance() */
  private static UnicodeUtil instance;

  /** @see #normalize2Ascii(char) */
  private static final Map<Character, String> CHARACTER_TO_ASCII_MAP;

  static {
    CHARACTER_TO_ASCII_MAP = new HashMap<Character, String>();
    CHARACTER_TO_ASCII_MAP.put(NO_BREAK_SPACE, " ");
    CHARACTER_TO_ASCII_MAP.put(SOFT_HYPHEN, "-");
    CHARACTER_TO_ASCII_MAP.put(MINUS_SIGN, "-");
    CHARACTER_TO_ASCII_MAP.put(EN_DASH, "-");

    CHARACTER_TO_ASCII_MAP.put(EM_DASH, "-");
    CHARACTER_TO_ASCII_MAP.put(FIGURE_DASH, "-");
    CHARACTER_TO_ASCII_MAP.put(SWUNG_DASH, "~");
    CHARACTER_TO_ASCII_MAP.put(HORIZONTAL_BAR, "-");

    // Greek letters

    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_ALPHA, "A");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_BETA, "B");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_GAMMA, "G");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_DELTA, "D");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_EPSILON, "E");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_ZETA, "Z");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_ETA, "H");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_THETA, "Th");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_IOTA, "I");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_KAPPA, "K");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_LAMDA, "L");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_MU, "M");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_NU, "N");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_XI, "Ks");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_OMICRON, "O");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_PI, "Pa");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_RHO, "P");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_SIGMA, "S");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_TAU, "T");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_UPSILON, "Y");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_PHI, "Fi");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_CHI, "X");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_PSI, "Ps");
    CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_OMEGA, "W");
    // greek specials
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_DIGAMMA, "F");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_HETA, "H");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_QOPPA, "Q");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_STIGMA, "S");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_SAN, "M");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_CAPITAL_LETTER_SAMPI, "T");

    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_ALPHA, "a");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_BETA, "b");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_GAMMA, "g");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_DELTA, "d");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_EPSILON, "e");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_ZETA, "z");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_ETA, "h");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_THETA, "th");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_IOTA, "i");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_KAPPA, "k");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_LAMDA, "l");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_MU, "m");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_NU, "n");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_XI, "ks");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_OMICRON, "o");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_PI, "pa");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_RHO, "p");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_SIGMA, "s");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_TAU, "t");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_UPSILON, "y");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_PHI, "fi");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_CHI, "x");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_PSI, "ps");
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_OMEGA, "w");
    // greek specials
    CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_DIGAMMA, "f");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_HETA, "h");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_QOPPA, "q");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_STIGMA, "s");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_SAN, "M");
    // CHARACTER_TO_ASCII_MAP.put(GREEK_SMALL_LETTER_SAMPI, "t");

    // musical symbols
    CHARACTER_TO_ASCII_MAP.put(MUSIC_FLAT_SIGN, "b");
    CHARACTER_TO_ASCII_MAP.put(MUSIC_SHARP_SIGN, "#");

    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_SHARP_S, "ss");
    for (DiacriticalMark mark : DiacriticalMark.values()) {
      for (char composed : mark.getComposedCharacters()) {
        CHARACTER_TO_ASCII_MAP.put(composed, mark.normalizeToAscii(composed));
      }
    }
  }

  /**
   * The constructor.
   */
  public UnicodeUtilImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String normalize2Ascii(char character) {

    return CHARACTER_TO_ASCII_MAP.get(Character.valueOf(character));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String normalize2Ascii(CharSequence text, char nonNormalizableCharaterReplacement) {

    if (text == null) {
      return null;
    }
    int length = text.length();
    StringBuilder buffer = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if (c <= 127) {
        buffer.append(c);
      } else {
        String ascii = normalize2Ascii(c);
        if (ascii != null) {
          buffer.append(ascii);
        } else if (nonNormalizableCharaterReplacement != NULL) {
          buffer.append(nonNormalizableCharaterReplacement);
        }
      }
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDash(char character) {

    if (character == HYPHEN_MINUS) {
      return true;
    } else if (character == EN_DASH) {
      return true;
    } else if (character == EM_DASH) {
      return true;
    } else if (character == FIGURE_DASH) {
      return true;
    } else if (character == SWUNG_DASH) {
      return true;
    } else if (character == HORIZONTAL_BAR) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isHyphen(char character) {

    if (character == HYPHEN_MINUS) {
      return true;
    } else if (character == HYPHEN) {
      return true;
    } else if (character == HYPHEN_BULLET) {
      return true;
    } else if (character == HYPHENATION_POINT) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMinus(char character) {

    if (character == HYPHEN_MINUS) {
      return true;
    } else if (character == MINUS_SIGN) {
      return true;
    }
    return false;
  }

  /**
   * This method gets the singleton instance of this {@link UnicodeUtilImpl}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
   * 
   * @return the singleton instance.
   */
  public static UnicodeUtil getInstance() {

    if (instance == null) {
      synchronized (UnicodeUtilImpl.class) {
        if (instance == null) {
          instance = new UnicodeUtilImpl();
        }
      }
    }
    return instance;
  }

}
