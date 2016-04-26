/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.filter.api.CharFilter;
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
public class UnicodeUtilImpl extends AbstractLoggableComponent implements UnicodeUtil {

  /** @see #getInstance() */
  private static UnicodeUtil instance;

  /** @see #normalize2Ascii(char) */
  private static final Map<Character, String> CHARACTER_TO_ASCII_MAP;

  /** @see #transliterate(char) */
  private static final Map<Character, String> TRANSLITERATION_MAP;

  static {
    CHARACTER_TO_ASCII_MAP = new HashMap<>();
    CHARACTER_TO_ASCII_MAP.put(NO_BREAK_SPACE, " ");
    CHARACTER_TO_ASCII_MAP.put(SOFT_HYPHEN, "-");
    CHARACTER_TO_ASCII_MAP.put(MINUS_SIGN, "-");
    CHARACTER_TO_ASCII_MAP.put(EN_DASH, "-");

    CHARACTER_TO_ASCII_MAP.put(EM_DASH, "-");
    CHARACTER_TO_ASCII_MAP.put(FIGURE_DASH, "-");
    CHARACTER_TO_ASCII_MAP.put(SWUNG_DASH, "~");
    CHARACTER_TO_ASCII_MAP.put(HORIZONTAL_BAR, "-");

    // Latin letters
    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_DOTLESS_J, "j");
    // German umlauts
    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_SHARP_S, "ss");
    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_A_WITH_DIAERESIS, "ae");
    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_O_WITH_DIAERESIS, "oe");
    CHARACTER_TO_ASCII_MAP.put(LATIN_SMALL_LETTER_U_WITH_DIAERESIS, "ue");
    CHARACTER_TO_ASCII_MAP.put(LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS, "Ae");
    CHARACTER_TO_ASCII_MAP.put(LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS, "Oe");
    CHARACTER_TO_ASCII_MAP.put(LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS, "Ue");

    TRANSLITERATION_MAP = new HashMap<>();
    initIso9(TRANSLITERATION_MAP);
    initIso843(TRANSLITERATION_MAP);

    // musical symbols
    CHARACTER_TO_ASCII_MAP.put(MUSIC_FLAT_SIGN, "b");
    CHARACTER_TO_ASCII_MAP.put(MUSIC_SHARP_SIGN, "#");

    for (DiacriticalMark mark : DiacriticalMark.values()) {
      for (char composed : mark.getComposedCharacters()) {
        if (!CHARACTER_TO_ASCII_MAP.containsKey(composed)) {
          Character decomposed = mark.decompose(composed);
          if (decomposed == null) {
            LoggerFactory.getLogger(UnicodeUtilImpl.class)
                .error("Illegal diacritic '" + mark + "' could NOT decomposed '" + composed + "'!");
          } else {
            char normalized = decomposed.charValue();
            String ascii = null;
            while (ascii == null) {
              if (CharFilter.ASCII_LETTER_FILTER.accept(normalized)) {
                ascii = Character.toString(normalized);
              } else {
                ascii = CHARACTER_TO_ASCII_MAP.get(decomposed);
              }
              if (ascii == null) {
                boolean decomposeFailed2Ascii = true;
                for (DiacriticalMark subMark : DiacriticalMark.values()) {
                  decomposed = subMark.decompose(normalized);
                  if (decomposed != null) {
                    normalized = decomposed.charValue();
                    decomposeFailed2Ascii = false;
                    break;
                  }
                }
                if (decomposeFailed2Ascii) {
                  LoggerFactory.getLogger(UnicodeUtilImpl.class)
                      .debug("Decomposed form '" + normalized + "' is not ASCII!");
                  break;
                }
              }
            }
            if (ascii != null) {
              CHARACTER_TO_ASCII_MAP.put(composed, ascii);
            }
          }
        }
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
   * Implementation of <a href="http://de.wikipedia.org/wiki/ISO_9">ISO 9</a> (cyrillic transliteration).
   *
   * @param map is where to add the transliteration mapping.
   */
  private static void initIso9(Map<Character, String> map) {

    // Cyrillic letters (ISO-9)
    map.put(CYRILLIC_CAPITAL_LETTER_A, "A");
    map.put(CYRILLIC_CAPITAL_LETTER_A_WITH_BREVE, Character.toString(LATIN_CAPITAL_LETTER_A_WITH_BREVE));
    map.put(CYRILLIC_CAPITAL_LETTER_A_WITH_DIAERESIS, Character.toString(LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_SCHWA, "A" + COMBINING_DOUBLE_ACUTE_ACCENT);
    map.put(CYRILLIC_CAPITAL_LETTER_BE, "B");
    map.put(CYRILLIC_CAPITAL_LETTER_VE, "V");
    map.put(CYRILLIC_CAPITAL_LETTER_GHE, "G");
    map.put(CYRILLIC_CAPITAL_LETTER_GHE_WITH_UPTURN, "G" + COMBINING_GRAVE_ACCENT);
    map.put(CYRILLIC_CAPITAL_LETTER_GHE_WITH_MIDDLE_HOOK, Character.toString(LATIN_CAPITAL_LETTER_G_WITH_BREVE));
    map.put(CYRILLIC_CAPITAL_LETTER_GHE_WITH_STROKE, Character.toString(LATIN_CAPITAL_LETTER_G_WITH_DOT_ABOVE));
    map.put(CYRILLIC_CAPITAL_LETTER_DE, "D");
    map.put(CYRILLIC_CAPITAL_LETTER_DJE, Character.toString(LATIN_CAPITAL_LETTER_D_WITH_STROKE));
    map.put(CYRILLIC_CAPITAL_LETTER_GJE, Character.toString(LATIN_CAPITAL_LETTER_G_WITH_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_IE, "E");
    map.put(CYRILLIC_CAPITAL_LETTER_IE_WITH_BREVE, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_BREVE));
    map.put(CYRILLIC_CAPITAL_LETTER_IO, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_UKRAINIAN_IE, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_ABKHASIAN_CHE, "C" + COMBINING_BREVE);
    map.put(CYRILLIC_CAPITAL_LETTER_ABKHASIAN_CHE_WITH_DESCENDER,
        Character.toString(LATIN_CAPITAL_LETTER_C_WITH_CEDILLA) + COMBINING_BREVE);
    map.put(CYRILLIC_CAPITAL_LETTER_ZHE, Character.toString(LATIN_CAPITAL_LETTER_Z_WITH_CARON));
    map.put(CYRILLIC_CAPITAL_LETTER_ZHE_WITH_BREVE, "Z" + COMBINING_BREVE);
    map.put(CYRILLIC_CAPITAL_LETTER_ZHE_WITH_DIAERESIS, "Z" + COMBINING_MACRON);
    map.put(CYRILLIC_CAPITAL_LETTER_ZHE_WITH_DESCENDER,
        Character.toString(LATIN_CAPITAL_LETTER_Z_WITH_CARON) + COMBINING_CEDILLA);
    map.put(CYRILLIC_CAPITAL_LETTER_ZE, "Z");
    map.put(CYRILLIC_CAPITAL_LETTER_ZE_WITH_DIAERESIS, "Z" + COMBINING_DIAERESIS);
    map.put(CYRILLIC_CAPITAL_LETTER_DZE, Character.toString(LATIN_CAPITAL_LETTER_Z_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_ABKHASIAN_DZE, Character.toString(LATIN_CAPITAL_LETTER_Z_WITH_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_I, "I");
    map.put(CYRILLIC_CAPITAL_LETTER_I_WITH_DIAERESIS, Character.toString(LATIN_CAPITAL_LETTER_I_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_BYELORUSSIAN_UKRAINIAN_I,
        Character.toString(LATIN_CAPITAL_LETTER_I_WITH_GRAVE));
    map.put(CYRILLIC_CAPITAL_LETTER_YI, Character.toString(LATIN_CAPITAL_LETTER_I_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_SHORT_I, "J");
    map.put(CYRILLIC_CAPITAL_LETTER_JE, "J" + COMBINING_CARON);
    map.put(CYRILLIC_CAPITAL_LETTER_KA, "K");
    map.put(CYRILLIC_CAPITAL_LETTER_KA_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_K_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_KA_WITH_STROKE, "K" + COMBINING_MACRON);
    map.put(CYRILLIC_CAPITAL_LETTER_EL, "L");
    map.put(CYRILLIC_CAPITAL_LETTER_LJE, "L" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_CAPITAL_LETTER_EM, "M");
    map.put(CYRILLIC_CAPITAL_LETTER_EN, "N");
    map.put(CYRILLIC_CAPITAL_LETTER_NJE, "N" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_CAPITAL_LIGATURE_EN_GHE, Character.toString(LATIN_CAPITAL_LETTER_N_WITH_DOT_ABOVE));
    map.put(CYRILLIC_CAPITAL_LETTER_EN_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_N_WITH_DOT_BELOW));
    map.put(CYRILLIC_CAPITAL_LETTER_EN_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_N_WITH_DOT_BELOW));
    map.put(CYRILLIC_CAPITAL_LETTER_O, "O");
    map.put(CYRILLIC_CAPITAL_LETTER_O_WITH_DIAERESIS, Character.toString(LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_BARRED_O, Character.toString(LATIN_CAPITAL_LETTER_O_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_PE, "P");
    map.put(CYRILLIC_CAPITAL_LETTER_PE_WITH_MIDDLE_HOOK, Character.toString(LATIN_CAPITAL_LETTER_P_WITH_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_ER, "R");
    map.put(CYRILLIC_CAPITAL_LETTER_ES, "S");
    map.put(CYRILLIC_CAPITAL_LETTER_ES_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_ES_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_TE, "T");
    map.put(CYRILLIC_CAPITAL_LETTER_TE_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_T_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_TSHE, Character.toString(LATIN_CAPITAL_LETTER_C_WITH_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_KJE, Character.toString(LATIN_CAPITAL_LETTER_K_WITH_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_U, "U");
    map.put(CYRILLIC_CAPITAL_LETTER_SHORT_U, Character.toString(LATIN_CAPITAL_LETTER_U_WITH_BREVE));
    map.put(CYRILLIC_CAPITAL_LETTER_U_WITH_DIAERESIS, Character.toString(LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE,
        Character.toString(LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE));
    map.put(CYRILLIC_CAPITAL_LETTER_STRAIGHT_U, Character.toString(LATIN_CAPITAL_LETTER_U_WITH_GRAVE));
    map.put(CYRILLIC_CAPITAL_LETTER_EF, "F");
    map.put(CYRILLIC_CAPITAL_LETTER_HA, "H");
    map.put(CYRILLIC_CAPITAL_LETTER_HA_WITH_DESCENDER, Character.toString(LATIN_CAPITAL_LETTER_H_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_SHHA, Character.toString(LATIN_CAPITAL_LETTER_H_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_TSE, "C");
    map.put(CYRILLIC_CAPITAL_LIGATURE_TE_TSE, "C" + COMBINING_MACRON);
    map.put(CYRILLIC_CAPITAL_LETTER_CHE, Character.toString(LATIN_CAPITAL_LETTER_C_WITH_CARON));
    map.put(CYRILLIC_CAPITAL_LETTER_CHE_WITH_DIAERESIS, "C" + COMBINING_DIAERESIS);
    map.put(CYRILLIC_CAPITAL_LETTER_KHAKASSIAN_CHE, Character.toString(LATIN_CAPITAL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_CAPITAL_LETTER_DZHE, "D" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_CAPITAL_LETTER_SHA, Character.toString(LATIN_CAPITAL_LETTER_S_WITH_CARON));
    map.put(CYRILLIC_CAPITAL_LETTER_SHCHA, Character.toString(LATIN_CAPITAL_LETTER_S_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_HARD_SIGN, Character.toString(MODIFIER_LETTER_DOUBLE_PRIME));
    map.put(CYRILLIC_CAPITAL_LETTER_YERU, "Y");
    map.put(CYRILLIC_CAPITAL_LETTER_YERU_WITH_DIAERESIS,
        Character.toString(LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_YERU_WITH_DIAERESIS,
        Character.toString(LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS));
    map.put(CYRILLIC_CAPITAL_LETTER_SOFT_SIGN, Character.toString(MODIFIER_LETTER_PRIME));
    map.put(CYRILLIC_CAPITAL_LETTER_E, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_GRAVE));
    map.put(CYRILLIC_CAPITAL_LETTER_YU, Character.toString(LATIN_CAPITAL_LETTER_U_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_YA, Character.toString(LATIN_CAPITAL_LETTER_A_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_CAPITAL_LETTER_SEMISOFT_SIGN, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_CARON));
    map.put(CYRILLIC_CAPITAL_LETTER_BIG_YUS, Character.toString(LATIN_CAPITAL_LETTER_A_WITH_CARON));
    map.put(CYRILLIC_CAPITAL_LETTER_FITA, "F" + COMBINING_GRAVE_ACCENT);
    map.put(CYRILLIC_CAPITAL_LETTER_IZHITSA, Character.toString(LATIN_CAPITAL_LETTER_Y_WITH_GRAVE));
    map.put(CYRILLIC_CAPITAL_LETTER_ABKHASIAN_HA, Character.toString(LATIN_CAPITAL_LETTER_O_WITH_GRAVE));
    // lower case
    map.put(CYRILLIC_SMALL_LETTER_A, "a");
    map.put(CYRILLIC_SMALL_LETTER_A_WITH_BREVE, Character.toString(LATIN_SMALL_LETTER_A_WITH_BREVE));
    map.put(CYRILLIC_SMALL_LETTER_A_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_A_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_SCHWA, "a" + COMBINING_DOUBLE_ACUTE_ACCENT);
    map.put(CYRILLIC_SMALL_LETTER_BE, "b");
    map.put(CYRILLIC_SMALL_LETTER_VE, "v");
    map.put(CYRILLIC_SMALL_LETTER_GHE, "g");
    map.put(CYRILLIC_SMALL_LETTER_GHE_WITH_UPTURN, "g" + COMBINING_GRAVE_ACCENT);
    map.put(CYRILLIC_SMALL_LETTER_GHE_WITH_MIDDLE_HOOK, Character.toString(LATIN_SMALL_LETTER_G_WITH_BREVE));
    map.put(CYRILLIC_SMALL_LETTER_GHE_WITH_STROKE, Character.toString(LATIN_SMALL_LETTER_G_WITH_DOT_ABOVE));
    map.put(CYRILLIC_SMALL_LETTER_DE, "d");
    map.put(CYRILLIC_SMALL_LETTER_DJE, Character.toString(LATIN_SMALL_LETTER_D_WITH_STROKE));
    map.put(CYRILLIC_SMALL_LETTER_GJE, Character.toString(LATIN_SMALL_LETTER_G_WITH_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_IE, "e");
    map.put(CYRILLIC_SMALL_LETTER_IE_WITH_BREVE, Character.toString(LATIN_SMALL_LETTER_E_WITH_BREVE));
    map.put(CYRILLIC_SMALL_LETTER_IO, Character.toString(LATIN_SMALL_LETTER_E_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_UKRAINIAN_IE, Character.toString(LATIN_SMALL_LETTER_E_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_ABKHASIAN_CHE, "c" + COMBINING_BREVE);
    map.put(CYRILLIC_SMALL_LETTER_ABKHASIAN_CHE_WITH_DESCENDER,
        Character.toString(LATIN_SMALL_LETTER_C_WITH_CEDILLA) + COMBINING_BREVE);
    map.put(CYRILLIC_SMALL_LETTER_ZHE, Character.toString(LATIN_SMALL_LETTER_Z_WITH_CARON));
    map.put(CYRILLIC_SMALL_LETTER_ZHE_WITH_BREVE, "z" + COMBINING_BREVE);
    map.put(CYRILLIC_SMALL_LETTER_ZHE_WITH_DIAERESIS, "z" + COMBINING_MACRON);
    map.put(CYRILLIC_SMALL_LETTER_ZHE_WITH_DESCENDER,
        Character.toString(LATIN_SMALL_LETTER_Z_WITH_CARON) + COMBINING_CEDILLA);
    map.put(CYRILLIC_SMALL_LETTER_ZE, "z");
    map.put(CYRILLIC_SMALL_LETTER_ZE_WITH_DIAERESIS, "z" + COMBINING_DIAERESIS);
    map.put(CYRILLIC_SMALL_LETTER_DZE, Character.toString(LATIN_SMALL_LETTER_Z_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_ABKHASIAN_DZE, Character.toString(LATIN_SMALL_LETTER_Z_WITH_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_I, "i");
    map.put(CYRILLIC_SMALL_LETTER_I_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_I_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_BYELORUSSIAN_UKRAINIAN_I, Character.toString(LATIN_SMALL_LETTER_I_WITH_GRAVE));
    map.put(CYRILLIC_SMALL_LETTER_YI, Character.toString(LATIN_SMALL_LETTER_I_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_SHORT_I, "j");
    map.put(CYRILLIC_SMALL_LETTER_JE, "j" + COMBINING_CARON);
    map.put(CYRILLIC_SMALL_LETTER_KA, "k");
    map.put(CYRILLIC_SMALL_LETTER_KA_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_K_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_KA_WITH_STROKE, "k" + COMBINING_MACRON);
    map.put(CYRILLIC_SMALL_LETTER_EL, "l");
    map.put(CYRILLIC_SMALL_LETTER_LJE, "l" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_SMALL_LETTER_EM, "m");
    map.put(CYRILLIC_SMALL_LETTER_EN, "n");
    map.put(CYRILLIC_SMALL_LETTER_NJE, "n" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_SMALL_LIGATURE_EN_GHE, Character.toString(LATIN_SMALL_LETTER_N_WITH_DOT_ABOVE));
    map.put(CYRILLIC_SMALL_LETTER_EN_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_N_WITH_DOT_BELOW));
    map.put(CYRILLIC_SMALL_LETTER_EN_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_N_WITH_DOT_BELOW));
    map.put(CYRILLIC_SMALL_LETTER_O, "o");
    map.put(CYRILLIC_SMALL_LETTER_O_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_O_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_BARRED_O, Character.toString(LATIN_SMALL_LETTER_O_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_PE, "p");
    map.put(CYRILLIC_SMALL_LETTER_PE_WITH_MIDDLE_HOOK, Character.toString(LATIN_SMALL_LETTER_P_WITH_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_ER, "r");
    map.put(CYRILLIC_SMALL_LETTER_ES, "s");
    map.put(CYRILLIC_SMALL_LETTER_ES_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_ES_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_TE, "t");
    map.put(CYRILLIC_SMALL_LETTER_TE_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_T_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_TSHE, Character.toString(LATIN_SMALL_LETTER_C_WITH_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_KJE, Character.toString(LATIN_SMALL_LETTER_K_WITH_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_U, "u");
    map.put(CYRILLIC_SMALL_LETTER_SHORT_U, Character.toString(LATIN_SMALL_LETTER_U_WITH_BREVE));
    map.put(CYRILLIC_SMALL_LETTER_U_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_U_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_U_WITH_DOUBLE_ACUTE, Character.toString(LATIN_SMALL_LETTER_U_WITH_DOUBLE_ACUTE));
    map.put(CYRILLIC_SMALL_LETTER_STRAIGHT_U, Character.toString(LATIN_SMALL_LETTER_U_WITH_GRAVE));
    map.put(CYRILLIC_SMALL_LETTER_EF, "f");
    map.put(CYRILLIC_SMALL_LETTER_HA, "h");
    map.put(CYRILLIC_SMALL_LETTER_HA_WITH_DESCENDER, Character.toString(LATIN_SMALL_LETTER_H_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_SHHA, Character.toString(LATIN_SMALL_LETTER_H_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_TSE, "c");
    map.put(CYRILLIC_SMALL_LIGATURE_TE_TSE, "c" + COMBINING_MACRON);
    map.put(CYRILLIC_SMALL_LETTER_CHE, Character.toString(LATIN_SMALL_LETTER_C_WITH_CARON));
    map.put(CYRILLIC_SMALL_LETTER_CHE_WITH_DIAERESIS, "c" + COMBINING_DIAERESIS);
    map.put(CYRILLIC_SMALL_LETTER_KHAKASSIAN_CHE, Character.toString(LATIN_SMALL_LETTER_C_WITH_CEDILLA));
    map.put(CYRILLIC_SMALL_LETTER_DZHE, "d" + COMBINING_CIRCUMFLEX_ACCENT);
    map.put(CYRILLIC_SMALL_LETTER_SHA, Character.toString(LATIN_SMALL_LETTER_S_WITH_CARON));
    map.put(CYRILLIC_SMALL_LETTER_SHCHA, Character.toString(LATIN_SMALL_LETTER_S_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_HARD_SIGN, Character.toString(MODIFIER_LETTER_DOUBLE_PRIME));
    map.put(CYRILLIC_SMALL_LETTER_YERU, "y");
    map.put(CYRILLIC_SMALL_LETTER_YERU_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_Y_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_YERU_WITH_DIAERESIS, Character.toString(LATIN_SMALL_LETTER_Y_WITH_DIAERESIS));
    map.put(CYRILLIC_SMALL_LETTER_SOFT_SIGN, Character.toString(MODIFIER_LETTER_PRIME));
    map.put(CYRILLIC_SMALL_LETTER_E, Character.toString(LATIN_SMALL_LETTER_E_WITH_GRAVE));
    map.put(CYRILLIC_SMALL_LETTER_YU, Character.toString(LATIN_SMALL_LETTER_U_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_YA, Character.toString(LATIN_SMALL_LETTER_A_WITH_CIRCUMFLEX));
    map.put(CYRILLIC_SMALL_LETTER_SEMISOFT_SIGN, Character.toString(LATIN_SMALL_LETTER_E_WITH_CARON));
    map.put(CYRILLIC_SMALL_LETTER_BIG_YUS, Character.toString(LATIN_SMALL_LETTER_A_WITH_CARON));
    map.put(CYRILLIC_SMALL_LETTER_FITA, "f" + COMBINING_GRAVE_ACCENT);
    map.put(CYRILLIC_SMALL_LETTER_IZHITSA, Character.toString(LATIN_SMALL_LETTER_Y_WITH_GRAVE));
    map.put(CYRILLIC_SMALL_LETTER_ABKHASIAN_HA, Character.toString(LATIN_SMALL_LETTER_O_WITH_GRAVE));
    // no case
    map.put(REVERSED_PRIME, Character.toString(RIGHT_SINGLE_QUOTATION_MARK));
    map.put(CYRILLIC_LETTER_PALOCHKA, Character.toString(DOUBLE_DAGGER));
  }

  /**
   * Implementation of <a href="http://en.wikipedia.org/wiki/ISO_843">ISO 843</a> (Greek transliteration).
   *
   * @param map is where to add the transliteration mapping.
   */
  private static void initIso843(Map<Character, String> map) {

    // Greek letters
    map.put(GREEK_CAPITAL_LETTER_ALPHA, "A");
    map.put(GREEK_CAPITAL_LETTER_BETA, "B");
    map.put(GREEK_CAPITAL_LETTER_GAMMA, "G");
    map.put(GREEK_CAPITAL_LETTER_DELTA, "D");
    map.put(GREEK_CAPITAL_LETTER_EPSILON, "E");
    map.put(GREEK_CAPITAL_LETTER_ZETA, "Z");
    map.put(GREEK_CAPITAL_LETTER_ETA, Character.toString(LATIN_CAPITAL_LETTER_E_WITH_MACRON));
    map.put(GREEK_CAPITAL_LETTER_THETA, "Th");
    map.put(GREEK_CAPITAL_LETTER_IOTA, "I");
    map.put(GREEK_CAPITAL_LETTER_KAPPA, "K");
    map.put(GREEK_CAPITAL_LETTER_LAMDA, "L");
    map.put(GREEK_CAPITAL_LETTER_MU, "M");
    map.put(GREEK_CAPITAL_LETTER_NU, "N");
    map.put(GREEK_CAPITAL_LETTER_XI, "X");
    map.put(GREEK_CAPITAL_LETTER_OMICRON, "O");
    map.put(GREEK_CAPITAL_LETTER_PI, "P");
    map.put(GREEK_CAPITAL_LETTER_RHO, "R");
    map.put(GREEK_CAPITAL_LETTER_SIGMA, "S");
    map.put(GREEK_CAPITAL_LETTER_TAU, "T");
    map.put(GREEK_CAPITAL_LETTER_UPSILON, "Y");
    map.put(GREEK_CAPITAL_LETTER_PHI, "F");
    map.put(GREEK_CAPITAL_LETTER_CHI, "Ch");
    map.put(GREEK_CAPITAL_LETTER_PSI, "Ps");
    map.put(GREEK_CAPITAL_LETTER_OMEGA, Character.toString(LATIN_CAPITAL_LETTER_O_WITH_MACRON));
    // greek specials
    map.put(GREEK_LETTER_DIGAMMA, "F");
    map.put(GREEK_CAPITAL_LETTER_HETA, "H");
    map.put(GREEK_CAPITAL_LETTER_KAPPA, "Q");
    map.put(GREEK_CAPITAL_LETTER_SAN, "S");
    map.put(GREEK_LETTER_SAMPI, "Ss");

    map.put(GREEK_SMALL_LETTER_ALPHA, "a");
    map.put(GREEK_SMALL_LETTER_BETA, "b");
    map.put(GREEK_SMALL_LETTER_GAMMA, "g");
    map.put(GREEK_SMALL_LETTER_DELTA, "d");
    map.put(GREEK_SMALL_LETTER_EPSILON, "e");
    map.put(GREEK_SMALL_LETTER_ZETA, "z");
    map.put(GREEK_SMALL_LETTER_ETA, Character.toString(LATIN_SMALL_LETTER_E_WITH_MACRON));
    map.put(GREEK_SMALL_LETTER_ETA_WITH_TONOS, Character.toString(LATIN_SMALL_LETTER_E_WITH_MACRON_AND_ACUTE));
    map.put(GREEK_SMALL_LETTER_THETA, "th");
    map.put(GREEK_SMALL_LETTER_IOTA, "i");
    map.put(GREEK_SMALL_LETTER_IOTA_WITH_TONOS, Character.toString(LATIN_SMALL_LETTER_I_WITH_ACUTE));
    map.put(GREEK_SMALL_LETTER_KAPPA, "k");
    map.put(GREEK_SMALL_LETTER_LAMDA, "l");
    map.put(GREEK_SMALL_LETTER_MU, "m");
    map.put(GREEK_SMALL_LETTER_NU, "n");
    map.put(GREEK_SMALL_LETTER_XI, "x");
    map.put(GREEK_SMALL_LETTER_OMICRON, "o");
    map.put(GREEK_SMALL_LETTER_PI, "p");
    map.put(GREEK_SMALL_LETTER_RHO, "r");
    map.put(GREEK_SMALL_LETTER_SIGMA, "s");
    map.put(GREEK_SMALL_LETTER_TAU, "t");
    map.put(GREEK_SMALL_LETTER_UPSILON, "y");
    map.put(GREEK_SMALL_LETTER_PHI, "f");
    map.put(GREEK_SMALL_LETTER_CHI, "ch");
    map.put(GREEK_SMALL_LETTER_PSI, "ps");
    map.put(GREEK_SMALL_LETTER_OMEGA, Character.toString(LATIN_SMALL_LETTER_O_WITH_MACRON));
    // greek specials
    map.put(GREEK_SMALL_LETTER_DIGAMMA, "f");
    map.put(GREEK_SMALL_LETTER_HETA, "h");
    map.put(GREEK_SMALL_LETTER_KOPPA, "q");
    map.put(GREEK_SMALL_LETTER_SAN, "s");
    map.put(GREEK_SMALL_LETTER_SAMPI, "ss");
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    instance = this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String normalize2Ascii(char character) {

    return normalize2Ascii(character, '?');
  }

  /**
   * @see #normalize2Ascii(char)
   *
   * @param character is the character to convert.
   * @param nonNormalizableCharaterReplacement is the character used to replace unicode characters that have no
   *        {@link #normalize2Ascii(char) corresponding ASCII representation}. Use {@link #NULL} to remove these
   *        characters. A typical character to use is <code>?</code>.
   * @return a sequence of ASCII-characters that represent the given character or <code>null</code> if the character is
   *         already ASCII or there is no ASCII-representation available.
   */
  public String normalize2Ascii(char character, char nonNormalizableCharaterReplacement) {

    String transliteration = transliterate(character);
    if (transliteration == null) {
      return CHARACTER_TO_ASCII_MAP.get(Character.valueOf(character));
    } else {
      int length = transliteration.length();
      if (length == 1) {
        return CHARACTER_TO_ASCII_MAP.get(Character.valueOf(transliteration.charAt(0)));
      }
      StringBuilder buffer = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
        char c = transliteration.charAt(i);
        if (c <= 127) {
          buffer.append(c);
        } else {
          String ascii = CHARACTER_TO_ASCII_MAP.get(c);
          if (ascii != null) {
            buffer.append(ascii);
          } else if (nonNormalizableCharaterReplacement != NULL) {
            buffer.append(nonNormalizableCharaterReplacement);
          }
        }
      }
      return buffer.toString();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String transliterate(char character) {

    return TRANSLITERATION_MAP.get(character);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String transliterate(String text) {

    if (text == null) {
      return null;
    }
    int length = text.length();
    StringBuilder buffer = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      String translit = transliterate(c);
      if (translit != null) {
        buffer.append(translit);
      } else {
        buffer.append(c);
      }
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String normalize2Ascii(CharSequence text) {

    return normalize2Ascii(text, '?');
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
    StringBuilder buffer = new StringBuilder(length + 4);
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if (c <= 127) {
        buffer.append(c);
      } else {
        String ascii = normalize2Ascii(c, nonNormalizableCharaterReplacement);
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
  @Override
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
  @Override
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
  @Override
  public boolean isMinus(char character) {

    if (character == HYPHEN_MINUS) {
      return true;
    } else if (character == MINUS_SIGN) {
      return true;
    }
    return false;
  }

  /**
   * This method gets the singleton instance of this {@link UnicodeUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static UnicodeUtil getInstance() {

    if (instance == null) {
      synchronized (UnicodeUtilImpl.class) {
        if (instance == null) {
          new UnicodeUtilImpl().initialize();
        }
      }
    }
    return instance;
  }

}
