/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions that help with unicode characters and texts. It also
 * contains constants for many unicode characters.
 *
 * @see DiacriticalMark
 * @see net.sf.mmm.util.xml.api.XmlUtil#resolveEntity(String)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
@SuppressWarnings("javadoc")
public interface UnicodeUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.text.api.UnicodeUtil";

  // ** C0 controls **

  /** The null character typically used for termination. */
  char NULL = 0x00000;

  /** The tab character used for indendation. */
  char CHARACTER_TABULATION = 0x00009;

  /**
   * The character indicating a move of the cursor to the next line. Originally the cursor was only moved down but
   * remained in the same column (horizontal position). On linux/unix systems this also includes a move of the cursor to
   * the beginning of the next line (see {@link #CARRIAGE_RETURN}). This is the origin of the differences for newlines
   * on different operating systems (windows vs. linux vs. mac).
   */
  char LINE_FEED = 0x0000A;

  /** The character indicating a move to the next page. */
  char FORM_FEED = 0x0000C;

  /**
   * The character indicating a move of the cursor to the beginning of the current line.
   */
  char CARRIAGE_RETURN = 0x0000D;

  // ** Basic Latin / "ASCII" (20-7F) **

  /** The regular whitespace character used to separate words. */
  char SPACE = 0x0020;

  /** The exclamation mark used to terminate an exclamation. Looks like this: {@value} */
  char EXCLAMATION_MARK = 0x00021;

  /** The quotation mark sign used to enclose quotations. Looks like this: {@value} */
  char QUOTATION_MARK = 0x00022;

  /** The hash or number sign. Looks like this: {@value} */
  char NUMBER_SIGN = 0x00023;

  /**
   * The sign for the currency dollar. Looks like this: {@value}<br>
   * This currency is not unique as many countries named their currency dollar (e.g. US-$, CAN-$, AU-$).
   */
  char DOLLAR_SIGN = 0x0024;

  /** The percent sign indicating the 1/100 part of the preceding number. Looks like this: {@value} */
  char PERCENT_SIGN = 0x0025;

  /** The ampersand sign used for the term "and". Looks like this: {@value} */
  char AMPERSAND = 0x0026;

  /** The apostrophe sign. Looks like this: {@value} */
  char APOSTROPHE = 0x0027;

  /** The left/opening parenthesis. Looks like this: {@value} */
  char LEFT_PARENTHESIS = 0x00028;

  /** The right/closing parenthesis. Looks like this: {@value} */
  char RIGHT_PARENTHESIS = 0x00029;

  /** The asterisk sign commonly used for multiplication. Looks like this: {@value} */
  char ASTERISK = 0x002A;

  /** The plus sign. Looks like this: {@value} */
  char PLUS_SIGN = 0x002B;

  /** The comma used as a separator within a sentence. Looks like this: {@value} */
  char COMMA = 0x0002C;

  /**
   * The ASCII hyphen or minus sign ({@value} ).
   *
   * @see #HYPHEN
   * @see #MINUS_SIGN
   * @see #SOFT_HYPHEN
   */
  char HYPHEN_MINUS = 0x002D;

  /** The full stop sign used in most languages to terminate a sentence. Looks like this: {@value} */
  char FULL_STOP = 0x0002E;

  /**
   * The slash sign also commonly used for divisions (where actually {@link #DIVISION_SIGN} shall be used). Looks like
   * this: {@value}
   */
  char SOLIDUS = 0x0002F;

  /** The Arabic digit for zero. Looks like this: {@value} */
  char DIGIT_ZERO = 0x0030;

  /** The Arabic digit for one. Looks like this: {@value} */
  char DIGIT_ONE = 0x0031;

  /** The Arabic digit for two. Looks like this: {@value} */
  char DIGIT_TWO = 0x0032;

  /** The Arabic digit for three. Looks like this: {@value} */
  char DIGIT_THREE = 0x0033;

  /** The Arabic digit for four. Looks like this: {@value} */
  char DIGIT_FOUR = 0x0034;

  /** The Arabic digit for five. Looks like this: {@value} */
  char DIGIT_FIVE = 0x0035;

  /** The Arabic digit for six. Looks like this: {@value} */
  char DIGIT_SIX = 0x0036;

  /** The Arabic digit for seven. Looks like this: {@value} */
  char DIGIT_SEVEN = 0x0037;

  /** The Arabic digit for eight. Looks like this: {@value} */
  char DIGIT_EIGHT = 0x0038;

  /** The Arabic digit for nine. Looks like this: {@value} */
  char DIGIT_NINE = 0x0039;

  /** The colon sign informally called double-dot. Looks like this: {@value} */
  char COLON = 0x0003A;

  /**
   * The semi-colon sign used as separator for various cases (e.g. for connected sentences instead of {@link #FULL_STOP}
   * ). Looks like this: {@value}
   */
  char SEMICOLON = 0x0003B;

  /**
   * The less-than sign for the according mathematical relation. Also used as opening brace in markup languages. Looks
   * like this: {@value}
   */
  char LESS_THAN_SIGN = 0x0003C;

  // char LESS_THAN_SIGN_WITH_VERTICAL_LINE=0x0003C-020D2;

  /** The equals-than sign for the according mathematical relation. Looks like this: {@value} */
  char EQUALS_SIGN = 0x0003D;

  // char EQUALS_SIGN_WITH_REVERSE_SLASH=0x0003D-020E5;
  /**
   * The greater-than sign for the according mathematical relation. Also used as closing brace in markup languages.
   * Looks like this: {@value}
   */
  char GREATER_THAN_SIGN = 0x0003E;

  // char GREATER_THAN_SIGN_WITH_VERTICAL_LINE=0x0003E-020D2;

  /** The question mark sign used to terminate a question. Looks like this: {@value} */
  char QUESTION_MARK = 0x0003F;

  /**
   * The at sign used as separator in email addresses. Looks like a small letter <code>a</code> in a circle: * *
   * {@value}
   */
  char COMMERCIAL_AT = 0x00040;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_A = 0x0041;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_B = 0x0042;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_C = 0x0043;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_D = 0x0044;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_E = 0x0045;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_F = 0x0046;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_G = 0x0047;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_H = 0x0048;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_I = 0x0049;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_J = 0x004A;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_K = 0x004B;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_L = 0x004C;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_M = 0x004D;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_N = 0x004E;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_O = 0x004F;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_P = 0x0050;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_Q = 0x0051;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_R = 0x0052;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_S = 0x0053;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_T = 0x0054;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_U = 0x0055;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_V = 0x0056;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_W = 0x0057;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_X = 0x0058;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_Y = 0x0059;

  /** The Latin letter {@value} . */
  char LATIN_CAPITAL_LETTER_Z = 0x005A;

  /** The left/opening square bracket. Looks like this: {@value} */
  char LEFT_SQUARE_BRACKET = 0x0005B;

  /** The reverse solidus or backslash sign. Looks like this: {@value} */
  char REVERSE_SOLIDUS = 0x0005C;

  // char REVERSE_SOLIDUS_SUBSET_OF=0x0005C-02282;

  /** The right/closing square bracket. Looks like this: {@value} */
  char RIGHT_SQUARE_BRACKET = 0x0005D;

  /**
   * The circumflex accent. Looks like this: {@value}
   *
   * @see #COMBINING_CIRCUMFLEX_ACCENT
   */
  char CIRCUMFLEX_ACCENT = 0x0005E;

  /** The low line or underscore sign. Looks like this: {@value} */
  char LOW_LINE = 0x0005F;

  /**
   * The grave accent. Looks like this: {@value}
   *
   * @see #COMBINING_GRAVE_ACCENT
   */
  char GRAVE_ACCENT = 0x00060;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_A = 0x0061;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_B = 0x0062;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_C = 0x0063;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_D = 0x0064;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_E = 0x0065;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_F = 0x0066;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_G = 0x0067;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_H = 0x0068;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_I = 0x0069;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_J = 0x006A;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_K = 0x006B;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_L = 0x006C;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_M = 0x006D;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_N = 0x006E;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_O = 0x006F;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_P = 0x0070;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_Q = 0x0071;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_R = 0x0072;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_S = 0x0073;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_T = 0x0074;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_U = 0x0075;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_V = 0x0076;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_W = 0x0077;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_X = 0x0078;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_Y = 0x0079;

  /** The small Latin letter {@value} . */
  char LATIN_SMALL_LETTER_Z = 0x007A;

  /** The left/opening curly brace. Looks like this: {@value} */
  char LEFT_CURLY_BRACKET = 0x0007B;

  /** The vertical line/bar symbol. Looks like this: {@value} */
  char VERTICAL_LINE = 0x0007C;

  /** The right/closing curly brace. Looks like this: {@value} */
  char RIGHT_CURLY_BRACKET = 0x0007D;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#TILDE}.
   */
  char TILDE = 0x007E;

  /** The delete character is a control character for the function of the delete/backspace key. */
  char DELETE = 0x0007F;

  // ** C1 controls (80-9F) **

  // ** Latin-1 supplement (A0-FF) **

  /**
   * A {@link #SPACE} that shall not be wrapped.
   */
  char NO_BREAK_SPACE = 0x000A0;

  /**
   * An inverted (upside-down) {@link #EXCLAMATION_MARK exclamation mark} (!) used at the beginning of an exclamation in
   * Spanish. Looks like this: {@value}
   */
  char INVERTED_EXCLAMATION_MARK = 0x000A1;

  /**
   * The sign for the currency unit cent. A cent is the 1/100 part of a {@link #DOLLAR_SIGN dollar}. Looks like this:
   * {@value}
   */
  char CENT_SIGN = 0x00A2;

  /** The sign for the British currency pound. Looks like this: {@value} */
  char POUND_SIGN = 0x00A3;

  /** The universal currency sign. Looks like this: {@value} */
  char CURRENCY_SIGN = 0x00A4;

  /** The sign for the Chinese currency yen. Looks like a dobule striketrough letter Y: {@value} */
  char YEN_SIGN = 0x00A5;

  /** The broken vertical bar symbol. Looks like this: {@value} */
  char BROKEN_BAR = 0x000A6;

  /** The section or paragraph sign. Looks like this: {@value} */
  char SECTION_SIGN = 0x000A7;

  /**
   * Looks like two small upper dots: {@value}
   *
   * @see DiacriticalMark#DIAERESIS
   * @see #COMBINING_DIAERESIS
   */
  char DIAERESIS = 0x000A8;

  /** Copyright sign (c) as correct glyph: {@value} . */
  char COPYRIGHT_SIGN = 0x000A9;

  /** The feminine ordinal indicator. Looks like this: {@value} */
  char FEMININE_ORDINAL_INDICATOR = 0x000AA;

  /** The left/opening double angle quotation mark. Looks like this: {@value} */
  char LEFT_POINTING_DOUBLE_ANGLE_QUOTATION_MARK = 0x000AB;

  /** The sign for a negation in boolean algebra. Looks like this: {@value} */
  char NOT_SIGN = 0x000AC;

  /**
   * The soft hyphen that indicates a word-wrap position (for hyphenation). Similar to ASCII hyphen-minus ('-'). It has
   * no visual glyph as representation.
   */
  char SOFT_HYPHEN = 0x000AD;

  /** The symbol for a registered trademark. Looks like the letter <code>R</code> in a circle: {@value} */
  char REGISTERED_SIGN = 0x000AE;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#MACRON}.
   */
  char MACRON = 0x000AF;

  /**
   * The symbol indicating degrees for angles or temperature. It looks like a small superscript circle as you can see
   * here: {@value}
   */
  char DEGREE_SIGN = 0x000B0;

  /** The symbol indicating plus (+) and/or minus (-). Looks like this: {@value} */
  char PLUS_MINUS_SIGN = 0x000B1;

  /** A small superscript variant of 2 e.g for the square. Looks like this: {@value} */
  char SUPERSCRIPT_TWO = 0x000B2;

  /** A small superscript variant of 3 e.g for cubic. Looks like this: {@value} */
  char SUPERSCRIPT_THREE = 0x000B3;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#ACUTE}.
   */
  char ACUTE_ACCENT = 0x000B4;

  /** The micro sign for 10<sup>-6</sup>. Looks like this: {@value} */
  char MICRO_SIGN = 0x000B5;

  /** The pilcrow or paragraph sign. Looks like this: {@value} */
  char PILCROW_SIGN = 0x000B6;

  /** A small centered dot. Looks like this: {@value} */
  char MIDDLE_DOT = 0x000B7;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#CEDILLA}.
   */
  char CEDILLA = 0x000B8;

  /** A small superscript variant of 1. Looks like this: {@value} */
  char SUPERSCRIPT_ONE = 0x000B9;

  /** The masculine ordinal indicator. Looks like this: {@value} */
  char MASCULINE_ORDINAL_INDICATOR = 0x000BA;

  /** The right/closing double angle quotation mark. Looks like this: {@value} */
  char RIGHT_POINTING_DOUBLE_ANGLE_QUOTATION_MARK = 0x000BB;

  /** The glyph for the fraction 1/4. Looks like this: {@value} */
  char VULGAR_FRACTION_ONE_QUARTER = 0x000BC;

  /** The glyph for the fraction 1/2. Looks like this: {@value} */
  char VULGAR_FRACTION_ONE_HALF = 0x000BD;

  /** The glyph for the fraction 3/4. Looks like this: {@value} */
  char VULGAR_FRACTION_THREE_QUARTERS = 0x000BE;

  /**
   * An inverted (upside-down) {@link #QUESTION_MARK question mark} (?) used at the beginning of a question in Spanish.
   * Looks like this: {@value}
   */
  char INVERTED_QUESTION_MARK = 0x000BF;

  /** The capital Latin letter A with a {@link #GRAVE_ACCENT grave accent}. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_A_WITH_GRAVE = 0x000C0;

  /** The capital Latin letter A with a {@link #ACUTE_ACCENT acute accent}. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_A_WITH_ACUTE = 0x000C1;

  /**
   * The capital Latin letter A with a {@link #CIRCUMFLEX_ACCENT circumflex accent}. Looks like this: {@value}
   */
  char LATIN_CAPITAL_LETTER_A_WITH_CIRCUMFLEX = 0x000C2;

  /** The capital Latin letter A with a {@link #TILDE tilde}. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_A_WITH_TILDE = 0x000C3;

  /** The capital Latin letter A with a {@link #DIAERESIS diaeresis}. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS = 0x000C4;

  /** The capital Latin letter A with a {@link #RING_ABOVE ring above}. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE = 0x000C5;

  /** The combination of the capital Latin letters A and E. Looks like this: {@value} */
  char LATIN_CAPITAL_LETTER_AE = 0x000C6;

  char LATIN_CAPITAL_LETTER_C_WITH_CEDILLA = 0x000C7;

  char LATIN_CAPITAL_LETTER_E_WITH_GRAVE = 0x000C8;

  char LATIN_CAPITAL_LETTER_E_WITH_ACUTE = 0x000C9;

  char LATIN_CAPITAL_LETTER_E_WITH_CIRCUMFLEX = 0x000CA;

  char LATIN_CAPITAL_LETTER_E_WITH_DIAERESIS = 0x000CB;

  char LATIN_CAPITAL_LETTER_I_WITH_GRAVE = 0x000CC;

  char LATIN_CAPITAL_LETTER_I_WITH_ACUTE = 0x000CD;

  char LATIN_CAPITAL_LETTER_I_WITH_CIRCUMFLEX = 0x000CE;

  char LATIN_CAPITAL_LETTER_I_WITH_DIAERESIS = 0x000CF;

  char LATIN_CAPITAL_LETTER_ETH = 0x000D0;

  char LATIN_CAPITAL_LETTER_N_WITH_TILDE = 0x000D1;

  char LATIN_CAPITAL_LETTER_O_WITH_GRAVE = 0x000D2;

  char LATIN_CAPITAL_LETTER_O_WITH_ACUTE = 0x000D3;

  char LATIN_CAPITAL_LETTER_O_WITH_CIRCUMFLEX = 0x000D4;

  char LATIN_CAPITAL_LETTER_O_WITH_TILDE = 0x000D5;

  char LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS = 0x000D6;

  char MULTIPLICATION_SIGN = 0x000D7;

  char LATIN_CAPITAL_LETTER_O_WITH_STROKE = 0x000D8;

  char LATIN_CAPITAL_LETTER_U_WITH_GRAVE = 0x000D9;

  char LATIN_CAPITAL_LETTER_U_WITH_ACUTE = 0x000DA;

  char LATIN_CAPITAL_LETTER_U_WITH_CIRCUMFLEX = 0x000DB;

  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS = 0x000DC;

  char LATIN_CAPITAL_LETTER_Y_WITH_ACUTE = 0x000DD;

  char LATIN_CAPITAL_LETTER_THORN = 0x000DE;

  char LATIN_SMALL_LETTER_SHARP_S = 0x000DF;

  char LATIN_SMALL_LETTER_A_WITH_GRAVE = 0x000E0;

  char LATIN_SMALL_LETTER_A_WITH_ACUTE = 0x000E1;

  char LATIN_SMALL_LETTER_A_WITH_CIRCUMFLEX = 0x000E2;

  char LATIN_SMALL_LETTER_A_WITH_TILDE = 0x000E3;

  char LATIN_SMALL_LETTER_A_WITH_DIAERESIS = 0x000E4;

  char LATIN_SMALL_LETTER_A_WITH_RING_ABOVE = 0x000E5;

  char LATIN_SMALL_LETTER_AE = 0x000E6;

  char LATIN_SMALL_LETTER_C_WITH_CEDILLA = 0x000E7;

  char LATIN_SMALL_LETTER_E_WITH_GRAVE = 0x000E8;

  char LATIN_SMALL_LETTER_E_WITH_ACUTE = 0x000E9;

  char LATIN_SMALL_LETTER_E_WITH_CIRCUMFLEX = 0x000EA;

  char LATIN_SMALL_LETTER_E_WITH_DIAERESIS = 0x000EB;

  char LATIN_SMALL_LETTER_I_WITH_GRAVE = 0x000EC;

  char LATIN_SMALL_LETTER_I_WITH_ACUTE = 0x000ED;

  char LATIN_SMALL_LETTER_I_WITH_CIRCUMFLEX = 0x000EE;

  char LATIN_SMALL_LETTER_I_WITH_DIAERESIS = 0x000EF;

  char LATIN_SMALL_LETTER_ETH = 0x000F0;

  char LATIN_SMALL_LETTER_N_WITH_TILDE = 0x000F1;

  char LATIN_SMALL_LETTER_O_WITH_GRAVE = 0x000F2;

  char LATIN_SMALL_LETTER_O_WITH_ACUTE = 0x000F3;

  char LATIN_SMALL_LETTER_O_WITH_CIRCUMFLEX = 0x000F4;

  char LATIN_SMALL_LETTER_O_WITH_TILDE = 0x000F5;

  char LATIN_SMALL_LETTER_O_WITH_DIAERESIS = 0x000F6;

  char DIVISION_SIGN = 0x000F7;

  char LATIN_SMALL_LETTER_O_WITH_STROKE = 0x000F8;

  char LATIN_SMALL_LETTER_U_WITH_GRAVE = 0x000F9;

  char LATIN_SMALL_LETTER_U_WITH_ACUTE = 0x000FA;

  char LATIN_SMALL_LETTER_U_WITH_CIRCUMFLEX = 0x000FB;

  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS = 0x000FC;

  char LATIN_SMALL_LETTER_Y_WITH_ACUTE = 0x000FD;

  char LATIN_SMALL_LETTER_THORN = 0x000FE;

  char LATIN_SMALL_LETTER_Y_WITH_DIAERESIS = 0x000FF;

  // ** Latin extended-A (100-17F) **

  char LATIN_CAPITAL_LETTER_A_WITH_MACRON = 0x00100;

  char LATIN_SMALL_LETTER_A_WITH_MACRON = 0x00101;

  char LATIN_CAPITAL_LETTER_A_WITH_BREVE = 0x00102;

  char LATIN_SMALL_LETTER_A_WITH_BREVE = 0x00103;

  char LATIN_CAPITAL_LETTER_A_WITH_OGONEK = 0x00104;

  char LATIN_SMALL_LETTER_A_WITH_OGONEK = 0x00105;

  char LATIN_CAPITAL_LETTER_C_WITH_ACUTE = 0x00106;

  char LATIN_SMALL_LETTER_C_WITH_ACUTE = 0x00107;

  char LATIN_CAPITAL_LETTER_C_WITH_CIRCUMFLEX = 0x00108;

  char LATIN_SMALL_LETTER_C_WITH_CIRCUMFLEX = 0x00109;

  char LATIN_CAPITAL_LETTER_C_WITH_DOT_ABOVE = 0x0010A;

  char LATIN_SMALL_LETTER_C_WITH_DOT_ABOVE = 0x0010B;

  char LATIN_CAPITAL_LETTER_C_WITH_CARON = 0x0010C;

  char LATIN_SMALL_LETTER_C_WITH_CARON = 0x0010D;

  char LATIN_CAPITAL_LETTER_D_WITH_CARON = 0x0010E;

  char LATIN_SMALL_LETTER_D_WITH_CARON = 0x0010F;

  char LATIN_CAPITAL_LETTER_D_WITH_STROKE = 0x00110;

  char LATIN_SMALL_LETTER_D_WITH_STROKE = 0x00111;

  char LATIN_CAPITAL_LETTER_E_WITH_MACRON = 0x00112;

  char LATIN_SMALL_LETTER_E_WITH_MACRON = 0x00113;

  char LATIN_CAPITAL_LETTER_E_WITH_BREVE = 0x0114;

  char LATIN_SMALL_LETTER_E_WITH_BREVE = 0x0115;

  char LATIN_CAPITAL_LETTER_E_WITH_DOT_ABOVE = 0x00116;

  char LATIN_SMALL_LETTER_E_WITH_DOT_ABOVE = 0x00117;

  char LATIN_CAPITAL_LETTER_E_WITH_OGONEK = 0x00118;

  char LATIN_SMALL_LETTER_E_WITH_OGONEK = 0x00119;

  char LATIN_CAPITAL_LETTER_E_WITH_CARON = 0x0011A;

  char LATIN_SMALL_LETTER_E_WITH_CARON = 0x0011B;

  char LATIN_CAPITAL_LETTER_G_WITH_CIRCUMFLEX = 0x0011C;

  char LATIN_SMALL_LETTER_G_WITH_CIRCUMFLEX = 0x0011D;

  char LATIN_CAPITAL_LETTER_G_WITH_BREVE = 0x0011E;

  char LATIN_SMALL_LETTER_G_WITH_BREVE = 0x0011F;

  char LATIN_CAPITAL_LETTER_G_WITH_DOT_ABOVE = 0x00120;

  char LATIN_SMALL_LETTER_G_WITH_DOT_ABOVE = 0x00121;

  char LATIN_CAPITAL_LETTER_G_WITH_CEDILLA = 0x00122;

  char LATIN_SMALL_LETTER_G_WITH_CEDILLA = 0x00123;

  char LATIN_CAPITAL_LETTER_H_WITH_CIRCUMFLEX = 0x00124;

  char LATIN_SMALL_LETTER_H_WITH_CIRCUMFLEX = 0x00125;

  char LATIN_CAPITAL_LETTER_H_WITH_STROKE = 0x00126;

  char LATIN_SMALL_LETTER_H_WITH_STROKE = 0x00127;

  char LATIN_CAPITAL_LETTER_I_WITH_TILDE = 0x00128;

  char LATIN_SMALL_LETTER_I_WITH_TILDE = 0x00129;

  char LATIN_CAPITAL_LETTER_I_WITH_MACRON = 0x0012A;

  char LATIN_SMALL_LETTER_I_WITH_MACRON = 0x0012B;

  char LATIN_CAPITAL_LETTER_I_WITH_BREVE = 0x012C;

  char LATIN_SMALL_LETTER_I_WITH_BREVE = 0x012D;

  char LATIN_CAPITAL_LETTER_I_WITH_OGONEK = 0x0012E;

  char LATIN_SMALL_LETTER_I_WITH_OGONEK = 0x0012F;

  char LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE = 0x00130;

  char LATIN_SMALL_LETTER_DOTLESS_I = 0x00131;

  char LATIN_CAPITAL_LIGATURE_IJ = 0x00132;

  char LATIN_SMALL_LIGATURE_IJ = 0x00133;

  char LATIN_CAPITAL_LETTER_J_WITH_CIRCUMFLEX = 0x00134;

  char LATIN_SMALL_LETTER_J_WITH_CIRCUMFLEX = 0x00135;

  char LATIN_CAPITAL_LETTER_K_WITH_CEDILLA = 0x00136;

  char LATIN_SMALL_LETTER_K_WITH_CEDILLA = 0x00137;

  char LATIN_SMALL_LETTER_KRA = 0x00138;

  char LATIN_CAPITAL_LETTER_L_WITH_ACUTE = 0x00139;

  char LATIN_SMALL_LETTER_L_WITH_ACUTE = 0x0013A;

  char LATIN_CAPITAL_LETTER_L_WITH_CEDILLA = 0x0013B;

  char LATIN_SMALL_LETTER_L_WITH_CEDILLA = 0x0013C;

  char LATIN_CAPITAL_LETTER_L_WITH_CARON = 0x0013D;

  char LATIN_SMALL_LETTER_L_WITH_CARON = 0x0013E;

  char LATIN_CAPITAL_LETTER_L_WITH_MIDDLE_DOT = 0x0013F;

  char LATIN_SMALL_LETTER_L_WITH_MIDDLE_DOT = 0x00140;

  char LATIN_CAPITAL_LETTER_L_WITH_STROKE = 0x00141;

  char LATIN_SMALL_LETTER_L_WITH_STROKE = 0x00142;

  char LATIN_CAPITAL_LETTER_N_WITH_ACUTE = 0x00143;

  char LATIN_SMALL_LETTER_N_WITH_ACUTE = 0x00144;

  char LATIN_CAPITAL_LETTER_N_WITH_CEDILLA = 0x00145;

  char LATIN_SMALL_LETTER_N_WITH_CEDILLA = 0x00146;

  char LATIN_CAPITAL_LETTER_N_WITH_CARON = 0x00147;

  char LATIN_SMALL_LETTER_N_WITH_CARON = 0x00148;

  char LATIN_SMALL_LETTER_N_PRECEDED_BY_APOSTROPHE = 0x00149;

  char LATIN_CAPITAL_LETTER_ENG = 0x0014A;

  char LATIN_SMALL_LETTER_ENG = 0x0014B;

  char LATIN_CAPITAL_LETTER_O_WITH_MACRON = 0x0014C;

  char LATIN_SMALL_LETTER_O_WITH_MACRON = 0x0014D;

  char LATIN_CAPITAL_LETTER_O_WITH_BREVE = 0x014E;

  char LATIN_SMALL_LETTER_O_WITH_BREVE = 0x014F;

  char LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_ACUTE = 0x00150;

  char LATIN_SMALL_LETTER_O_WITH_DOUBLE_ACUTE = 0x00151;

  char LATIN_CAPITAL_LIGATURE_OE = 0x00152;

  char LATIN_SMALL_LIGATURE_OE = 0x00153;

  char LATIN_CAPITAL_LETTER_R_WITH_ACUTE = 0x00154;

  char LATIN_SMALL_LETTER_R_WITH_ACUTE = 0x00155;

  char LATIN_CAPITAL_LETTER_R_WITH_CEDILLA = 0x00156;

  char LATIN_SMALL_LETTER_R_WITH_CEDILLA = 0x00157;

  char LATIN_CAPITAL_LETTER_R_WITH_CARON = 0x00158;

  char LATIN_SMALL_LETTER_R_WITH_CARON = 0x00159;

  char LATIN_CAPITAL_LETTER_S_WITH_ACUTE = 0x0015A;

  char LATIN_SMALL_LETTER_S_WITH_ACUTE = 0x0015B;

  char LATIN_CAPITAL_LETTER_S_WITH_CIRCUMFLEX = 0x0015C;

  char LATIN_SMALL_LETTER_S_WITH_CIRCUMFLEX = 0x0015D;

  char LATIN_CAPITAL_LETTER_S_WITH_CEDILLA = 0x0015E;

  char LATIN_SMALL_LETTER_S_WITH_CEDILLA = 0x0015F;

  char LATIN_CAPITAL_LETTER_S_WITH_CARON = 0x00160;

  char LATIN_SMALL_LETTER_S_WITH_CARON = 0x00161;

  char LATIN_CAPITAL_LETTER_T_WITH_CEDILLA = 0x00162;

  char LATIN_SMALL_LETTER_T_WITH_CEDILLA = 0x00163;

  char LATIN_CAPITAL_LETTER_T_WITH_CARON = 0x00164;

  char LATIN_SMALL_LETTER_T_WITH_CARON = 0x00165;

  char LATIN_CAPITAL_LETTER_T_WITH_STROKE = 0x00166;

  char LATIN_SMALL_LETTER_T_WITH_STROKE = 0x00167;

  char LATIN_CAPITAL_LETTER_U_WITH_TILDE = 0x00168;

  char LATIN_SMALL_LETTER_U_WITH_TILDE = 0x00169;

  char LATIN_CAPITAL_LETTER_U_WITH_MACRON = 0x0016A;

  char LATIN_SMALL_LETTER_U_WITH_MACRON = 0x0016B;

  char LATIN_CAPITAL_LETTER_U_WITH_BREVE = 0x0016C;

  char LATIN_SMALL_LETTER_U_WITH_BREVE = 0x0016D;

  char LATIN_CAPITAL_LETTER_U_WITH_RING_ABOVE = 0x0016E;

  char LATIN_SMALL_LETTER_U_WITH_RING_ABOVE = 0x0016F;

  char LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE = 0x00170;

  char LATIN_SMALL_LETTER_U_WITH_DOUBLE_ACUTE = 0x00171;

  char LATIN_CAPITAL_LETTER_U_WITH_OGONEK = 0x00172;

  char LATIN_SMALL_LETTER_U_WITH_OGONEK = 0x00173;

  char LATIN_CAPITAL_LETTER_W_WITH_CIRCUMFLEX = 0x00174;

  char LATIN_SMALL_LETTER_W_WITH_CIRCUMFLEX = 0x00175;

  char LATIN_CAPITAL_LETTER_Y_WITH_CIRCUMFLEX = 0x00176;

  char LATIN_SMALL_LETTER_Y_WITH_CIRCUMFLEX = 0x00177;

  char LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS = 0x00178;

  char LATIN_CAPITAL_LETTER_Z_WITH_ACUTE = 0x00179;

  char LATIN_SMALL_LETTER_Z_WITH_ACUTE = 0x0017A;

  char LATIN_CAPITAL_LETTER_Z_WITH_DOT_ABOVE = 0x0017B;

  char LATIN_SMALL_LETTER_Z_WITH_DOT_ABOVE = 0x0017C;

  char LATIN_CAPITAL_LETTER_Z_WITH_CARON = 0x0017D;

  char LATIN_SMALL_LETTER_Z_WITH_CARON = 0x0017E;

  char LATIN_SMALL_LETTER_LONG_S = 0x017F;

  // ** Latin extended-B (180-24F) **

  char LATIN_SMALL_LETTER_B_WITH_STROKE = 0x0180;

  char LATIN_CAPITAL_LETTER_B_WITH_HOOK = 0x0181;

  char LATIN_CAPITAL_LETTER_B_WITH_TOPBAR = 0x0182;

  char LATIN_SMALL_LETTER_B_WITH_TOPBAR = 0x0183;

  char LATIN_CAPITAL_LETTER_TONE_SIX = 0x0184;

  char LATIN_SMALL_LETTER_TONE_SIX = 0x0185;

  char LATIN_CAPITAL_LETTER_OPEN_O = 0x0186;

  char LATIN_CAPITAL_LETTER_C_WITH_HOOK = 0x0187;

  char LATIN_SMALL_LETTER_C_WITH_HOOK = 0x0188;

  char LATIN_CAPITAL_LETTER_AFRICAN_D = 0x0189;

  char LATIN_CAPITAL_LETTER_D_WITH_HOOK = 0x018A;

  char LATIN_CAPITAL_LETTER_D_WITH_TOPBAR = 0x018B;

  char LATIN_SMALL_LETTER_D_WITH_TOPBAR = 0x018C;

  char LATIN_SMALL_LETTER_TURNED_DELTA = 0x018D;

  char LATIN_CAPITAL_LETTER_REVERSED_E = 0x018E;

  /** The capital form of {@link #LATIN_SMALL_LETTER_SCHWA}. */
  char LATIN_CAPITAL_LETTER_SCHWA = 0x018F;

  char LATIN_CAPITAL_LETTER_OPEN_E = 0x0190;

  char LATIN_CAPITAL_LETTER_F_WITH_HOOK = 0x0191;

  char LATIN_SMALL_LETTER_F_WITH_HOOK = 0x00192;

  char LATIN_CAPITAL_LETTER_G_WITH_HOOK = 0x0193;

  char LATIN_CAPITAL_LETTER_GAMMA = 0x0194;

  char LATIN_SMALL_LETTER_HV = 0x0195;

  char LATIN_CAPITAL_LETTER_IOTA = 0x0196;

  char LATIN_CAPITAL_LETTER_I_WITH_STROKE = 0x0197;

  char LATIN_CAPITAL_LETTER_K_WITH_HOOK = 0x0198;

  char LATIN_SMALL_LETTER_K_WITH_HOOK = 0x0199;

  char LATIN_SMALL_LETTER_L_WITH_BAR = 0x019A;

  char LATIN_SMALL_LETTER_LAMBDA_WITH_STROKE = 0x019B;

  char LATIN_CAPITAL_LETTER_TURNED_M = 0x019C;

  char LATIN_CAPITAL_LETTER_N_WITH_LEFT_HOOK = 0x019D;

  char LATIN_SMALL_LETTER_N_WITH_LONG_RIGHT_LEG = 0x019E;

  char LATIN_CAPITAL_LETTER_O_WITH_MIDDLE_TILDE = 0x019F;

  char LATIN_CAPITAL_LETTER_O_WITH_HORN = 0x01A0;

  char LATIN_SMALL_LETTER_O_WITH_HORN = 0x01A1;

  char LATIN_CAPITAL_LETTER_OI = 0x01A2;

  char LATIN_SMALL_LETTER_OI = 0x01A3;

  char LATIN_CAPITAL_LETTER_P_WITH_HOOK = 0x01A4;

  char LATIN_SMALL_LETTER_P_WITH_HOOK = 0x01A5;

  char LATIN_LETTER_YR = 0x01A6;

  char LATIN_CAPITAL_LETTER_TONE_TWO = 0x01A7;

  char LATIN_SMALL_LETTER_TONE_TWO = 0x01A8;

  char LATIN_CAPITAL_LETTER_ESH = 0x01A9;

  char LATIN_LETTER_REVERSED_ESH_LOOP = 0x01AA;

  char LATIN_SMALL_LETTER_T_WITH_PALATAL_HOOK = 0x01AB;

  char LATIN_CAPITAL_LETTER_T_WITH_HOOK = 0x01AC;

  char LATIN_SMALL_LETTER_T_WITH_HOOK = 0x01AD;

  char LATIN_CAPITAL_LETTER_T_WITH_RETROFLEX_HOOK = 0x01AE;

  char LATIN_CAPITAL_LETTER_U_WITH_HORN = 0x01AF;

  char LATIN_SMALL_LETTER_U_WITH_HORN = 0x01B0;

  char LATIN_CAPITAL_LETTER_UPSILON = 0x01B1;

  char LATIN_CAPITAL_LETTER_V_WITH_HOOK = 0x01B2;

  char LATIN_CAPITAL_LETTER_Y_WITH_HOOK = 0x01B3;

  char LATIN_SMALL_LETTER_Y_WITH_HOOK = 0x01B4;

  char LATIN_CAPITAL_LETTER_Z_WITH_STROKE = 0x001B5;

  char LATIN_SMALL_LETTER_Z_WITH_STROKE = 0x01B6;

  char LATIN_CAPITAL_LETTER_EZH = 0x01B7;

  char LATIN_CAPITAL_LETTER_EZH_REVERSED = 0x01B8;

  char LATIN_SMALL_LETTER_EZH_REVERSED = 0x01B9;

  char LATIN_SMALL_LETTER_EZH_WITH_TAIL = 0x01BA;

  char LATIN_LETTER_TWO_WITH_STROKE = 0x01BB;

  char LATIN_CAPITAL_LETTER_TONE_FIVE = 0x01BC;

  char LATIN_SMALL_LETTER_TONE_FIVE = 0x01BD;

  char LATIN_LETTER_INVERTED_GLOTTAL_STOP_WITH_STROKE = 0x01BE;

  char LATIN_LETTER_WYNN = 0x01BF;

  char LATIN_LETTER_DENTAL_CLICK = 0x01C0;

  char LATIN_LETTER_LATERAL_CLICK = 0x01C1;

  char LATIN_LETTER_ALVEOLAR_CLICK = 0x01C2;

  char LATIN_LETTER_RETROFLEX_CLICK = 0x01C3;

  char LATIN_CAPITAL_LETTER_DZ_WITH_CARON = 0x01C4;

  char LATIN_CAPITAL_LETTER_D_WITH_SMALL_LETTER_Z_WITH_CARON = 0x01C5;

  char LATIN_SMALL_LETTER_DZ_WITH_CARON = 0x01C6;

  char LATIN_CAPITAL_LETTER_LJ = 0x01C7;

  char LATIN_CAPITAL_LETTER_L_WITH_SMALL_LETTER_J = 0x01C8;

  char LATIN_SMALL_LETTER_LJ = 0x01C9;

  char LATIN_CAPITAL_LETTER_NJ = 0x01CA;

  char LATIN_CAPITAL_LETTER_N_WITH_SMALL_LETTER_J = 0x01CB;

  char LATIN_SMALL_LETTER_NJ = 0x01CC;

  char LATIN_CAPITAL_LETTER_A_WITH_CARON = 0x01CD;

  char LATIN_SMALL_LETTER_A_WITH_CARON = 0x01CE;

  char LATIN_CAPITAL_LETTER_I_WITH_CARON = 0x01CF;

  char LATIN_SMALL_LETTER_I_WITH_CARON = 0x01D0;

  char LATIN_CAPITAL_LETTER_O_WITH_CARON = 0x01D1;

  char LATIN_SMALL_LETTER_O_WITH_CARON = 0x01D2;

  char LATIN_CAPITAL_LETTER_U_WITH_CARON = 0x01D3;

  char LATIN_SMALL_LETTER_U_WITH_CARON = 0x01D4;

  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_MACRON = 0x01D5;

  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_MACRON = 0x01D6;

  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_ACUTE = 0x01D7;

  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_ACUTE = 0x01D8;

  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_CARON = 0x01D9;

  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_CARON = 0x01DA;

  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_GRAVE = 0x01DB;

  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_GRAVE = 0x01DC;

  /**
   * Looks like a {@link #LATIN_SMALL_LETTER_E} that is turned (mirrored to the vertical axis).
   */
  char LATIN_SMALL_LETTER_TURNED_E = 0x01DD;

  char LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS_AND_MACRON = 0x01DE;

  char LATIN_SMALL_LETTER_A_WITH_DIAERESIS_AND_MACRON = 0x01DF;

  char LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON = 0x01E0;

  char LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON = 0x01E1;

  char LATIN_CAPITAL_LETTER_AE_WITH_MACRON = 0x01E2;

  char LATIN_SMALL_LETTER_AE_WITH_MACRON = 0x01E3;

  char LATIN_CAPITAL_LETTER_G_WITH_STROKE = 0x01E4;

  char LATIN_SMALL_LETTER_G_WITH_STROKE = 0x01E5;

  char LATIN_CAPITAL_LETTER_G_WITH_CARON = 0x01E6;

  char LATIN_SMALL_LETTER_G_WITH_CARON = 0x01E7;

  char LATIN_CAPITAL_LETTER_K_WITH_CARON = 0x01E8;

  char LATIN_SMALL_LETTER_K_WITH_CARON = 0x01E9;

  char LATIN_CAPITAL_LETTER_O_WITH_OGONEK = 0x01EA;

  char LATIN_SMALL_LETTER_O_WITH_OGONEK = 0x01EB;

  char LATIN_CAPITAL_LETTER_O_WITH_OGONEK_AND_MACRON = 0x01EC;

  char LATIN_SMALL_LETTER_O_WITH_OGONEK_AND_MACRON = 0x01ED;

  char LATIN_CAPITAL_LETTER_EZH_WITH_CARON = 0x01EE;

  char LATIN_SMALL_LETTER_EZH_WITH_CARON = 0x01EF;

  char LATIN_SMALL_LETTER_J_WITH_CARON = 0x01F0;

  char LATIN_CAPITAL_LETTER_DZ = 0x01F1;

  char LATIN_CAPITAL_LETTER_D_WITH_SMALL_LETTER_Z = 0x01F2;

  char LATIN_SMALL_LETTER_DZ = 0x01F3;

  char LATIN_CAPITAL_LETTER_G_WITH_ACUTE = 0x01F4;

  char LATIN_SMALL_LETTER_G_WITH_ACUTE = 0x001F5;

  char LATIN_CAPITAL_LETTER_HWAIR = 0x01F6;

  char LATIN_CAPITAL_LETTER_WYNN = 0x01F7;

  char LATIN_CAPITAL_LETTER_N_WITH_GRAVE = 0x01F8;

  char LATIN_SMALL_LETTER_N_WITH_GRAVE = 0x01F9;

  char LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE = 0x01FA;

  char LATIN_SMALL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE = 0x01FB;

  char LATIN_CAPITAL_LETTER_AE_WITH_ACUTE = 0x01FC;

  char LATIN_SMALL_LETTER_AE_WITH_ACUTE = 0x01FD;

  char LATIN_CAPITAL_LETTER_O_WITH_STROKE_AND_ACUTE = 0x01FE;

  char LATIN_SMALL_LETTER_O_WITH_STROKE_AND_ACUTE = 0x01FF;

  char LATIN_CAPITAL_LETTER_A_WITH_DOUBLE_GRAVE = 0x0200;

  char LATIN_SMALL_LETTER_A_WITH_DOUBLE_GRAVE = 0x0201;

  char LATIN_CAPITAL_LETTER_A_WITH_INVERTED_BREVE = 0x0202;

  char LATIN_SMALL_LETTER_A_WITH_INVERTED_BREVE = 0x0203;

  char LATIN_CAPITAL_LETTER_E_WITH_DOUBLE_GRAVE = 0x0204;

  char LATIN_SMALL_LETTER_E_WITH_DOUBLE_GRAVE = 0x0205;

  char LATIN_CAPITAL_LETTER_E_WITH_INVERTED_BREVE = 0x0206;

  char LATIN_SMALL_LETTER_E_WITH_INVERTED_BREVE = 0x0207;

  char LATIN_CAPITAL_LETTER_I_WITH_DOUBLE_GRAVE = 0x0208;

  char LATIN_SMALL_LETTER_I_WITH_DOUBLE_GRAVE = 0x0209;

  char LATIN_CAPITAL_LETTER_I_WITH_INVERTED_BREVE = 0x020A;

  char LATIN_SMALL_LETTER_I_WITH_INVERTED_BREVE = 0x020B;

  char LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_GRAVE = 0x020C;

  char LATIN_SMALL_LETTER_O_WITH_DOUBLE_GRAVE = 0x020D;

  char LATIN_CAPITAL_LETTER_O_WITH_INVERTED_BREVE = 0x020E;

  char LATIN_SMALL_LETTER_O_WITH_INVERTED_BREVE = 0x020F;

  char LATIN_CAPITAL_LETTER_R_WITH_DOUBLE_GRAVE = 0x0210;

  char LATIN_SMALL_LETTER_R_WITH_DOUBLE_GRAVE = 0x0211;

  char LATIN_CAPITAL_LETTER_R_WITH_INVERTED_BREVE = 0x0212;

  char LATIN_SMALL_LETTER_R_WITH_INVERTED_BREVE = 0x0213;

  char LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_GRAVE = 0x0214;

  char LATIN_SMALL_LETTER_U_WITH_DOUBLE_GRAVE = 0x0215;

  char LATIN_CAPITAL_LETTER_U_WITH_INVERTED_BREVE = 0x0216;

  char LATIN_SMALL_LETTER_U_WITH_INVERTED_BREVE = 0x0217;

  char LATIN_CAPITAL_LETTER_S_WITH_COMMA_BELOW = 0x0218;

  char LATIN_SMALL_LETTER_S_WITH_COMMA_BELOW = 0x0219;

  char LATIN_CAPITAL_LETTER_T_WITH_COMMA_BELOW = 0x021A;

  char LATIN_SMALL_LETTER_T_WITH_COMMA_BELOW = 0x021B;

  char LATIN_CAPITAL_LETTER_YOGH = 0x021C;

  char LATIN_SMALL_LETTER_YOGH = 0x021D;

  char LATIN_CAPITAL_LETTER_H_WITH_CARON = 0x021E;

  char LATIN_SMALL_LETTER_H_WITH_CARON = 0x021F;

  char LATIN_CAPITAL_LETTER_N_WITH_LONG_RIGHT_LEG = 0x0220;

  char LATIN_SMALL_LETTER_D_WITH_CURL = 0x0221;

  char LATIN_CAPITAL_LETTER_OU = 0x0222;

  char LATIN_SMALL_LETTER_OU = 0x0223;

  char LATIN_CAPITAL_LETTER_Z_WITH_HOOK = 0x0224;

  char LATIN_SMALL_LETTER_Z_WITH_HOOK = 0x0225;

  char LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE = 0x0226;

  char LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE = 0x0227;

  char LATIN_CAPITAL_LETTER_E_WITH_CEDILLA = 0x0228;

  char LATIN_SMALL_LETTER_E_WITH_CEDILLA = 0x0229;

  char LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS_AND_MACRON = 0x022A;

  char LATIN_SMALL_LETTER_O_WITH_DIAERESIS_AND_MACRON = 0x022B;

  char LATIN_CAPITAL_LETTER_O_WITH_TILDE_AND_MACRON = 0x022C;

  char LATIN_SMALL_LETTER_O_WITH_TILDE_AND_MACRON = 0x022D;

  char LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE = 0x022E;

  char LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE = 0x022F;

  char LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON = 0x0230;

  char LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON = 0x0231;

  char LATIN_CAPITAL_LETTER_Y_WITH_MACRON = 0x0232;

  char LATIN_SMALL_LETTER_Y_WITH_MACRON = 0x0233;

  char LATIN_SMALL_LETTER_L_WITH_CURL = 0x0234;

  char LATIN_SMALL_LETTER_N_WITH_CURL = 0x0235;

  char LATIN_SMALL_LETTER_T_WITH_CURL = 0x0236;

  char LATIN_SMALL_LETTER_DOTLESS_J = 0x0237;

  char LATIN_SMALL_LETTER_DB_DIGRAPH = 0x0238;

  char LATIN_SMALL_LETTER_QP_DIGRAPH = 0x0239;

  char LATIN_CAPITAL_LETTER_A_WITH_STROKE = 0x023A;

  char LATIN_CAPITAL_LETTER_C_WITH_STROKE = 0x023B;

  char LATIN_SMALL_LETTER_C_WITH_STROKE = 0x023C;

  char LATIN_CAPITAL_LETTER_L_WITH_BAR = 0x023D;

  char LATIN_CAPITAL_LETTER_T_WITH_DIAGONAL_STROKE = 0x023E;

  char LATIN_SMALL_LETTER_S_WITH_SWASH_TAIL = 0x023F;

  char LATIN_SMALL_LETTER_Z_WITH_SWASH_TAIL = 0x0240;

  char LATIN_CAPITAL_LETTER_GLOTTAL_STOP = 0x0241;

  char LATIN_SMALL_LETTER_GLOTTAL_STOP = 0x0242;

  char LATIN_CAPITAL_LETTER_B_WITH_STROKE = 0x0243;

  char LATIN_CAPITAL_LETTER_U_BAR = 0x0244;

  char LATIN_CAPITAL_LETTER_TURNED_V = 0x0245;

  char LATIN_CAPITAL_LETTER_E_WITH_STROKE = 0x0246;

  char LATIN_SMALL_LETTER_E_WITH_STROKE = 0x0247;

  char LATIN_CAPITAL_LETTER_J_WITH_STROKE = 0x0248;

  char LATIN_SMALL_LETTER_J_WITH_STROKE = 0x0249;

  char LATIN_CAPITAL_LETTER_SMALL_Q_WITH_HOOK_TAIL = 0x024A;

  char LATIN_SMALL_LETTER_Q_WITH_HOOK_TAIL = 0x024B;

  char LATIN_CAPITAL_LETTER_R_WITH_STROKE = 0x024C;

  char LATIN_SMALL_LETTER_R_WITH_STROKE = 0x024D;

  char LATIN_CAPITAL_LETTER_Y_WITH_STROKE = 0x024E;

  char LATIN_SMALL_LETTER_Y_WITH_STROKE = 0x024F;

  // *** IPA extensions (250-2AF) ***

  char LATIN_SMALL_LETTER_TURNED_A = 0x0250;

  char LATIN_SMALL_LETTER_ALPHA = 0x0251;

  char LATIN_SMALL_LETTER_TURNED_ALPHA = 0x0252;

  char LATIN_SMALL_LETTER_B_WITH_HOOK = 0x0253;

  char LATIN_SMALL_LETTER_OPEN_O = 0x0254;

  char LATIN_SMALL_LETTER_C_WITH_CURL = 0x0255;

  char LATIN_SMALL_LETTER_D_WITH_TAIL = 0x0256;

  char LATIN_SMALL_LETTER_D_WITH_HOOK = 0x0257;

  char LATIN_SMALL_LETTER_REVERSED_E = 0x0258;

  /**
   * The small <em>schwa</em> letter is used in the International Phonetic Alphabet to represent the
   * "mid central unrounded vowel". It is used for instance in the second syllable of the English word "harmony". This
   * letter uses the same glyph as {@link #LATIN_SMALL_LETTER_TURNED_E}.
   */
  char LATIN_SMALL_LETTER_SCHWA = 0x0259;

  char LATIN_SMALL_LETTER_SCHWA_WITH_HOOK = 0x025A;

  char LATIN_SMALL_LETTER_OPEN_E = 0x025B;

  char LATIN_SMALL_LETTER_REVERSED_OPEN_E = 0x025C;

  char LATIN_SMALL_LETTER_REVERSED_OPEN_E_WITH_HOOK = 0x025D;

  char LATIN_SMALL_LETTER_CLOSED_REVERSED_OPEN_E = 0x025E;

  char LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE = 0x025F;

  char LATIN_SMALL_LETTER_G_WITH_HOOK = 0x0260;

  char LATIN_SMALL_LETTER_SCRIPT_G = 0x0261;

  char LATIN_LETTER_SMALL_CAPITAL_G = 0x0262;

  char LATIN_SMALL_LETTER_GAMMA = 0x0263;

  char LATIN_SMALL_LETTER_RAMS_HORN = 0x0264;

  char LATIN_SMALL_LETTER_TURNED_H = 0x0265;

  char LATIN_SMALL_LETTER_H_WITH_HOOK = 0x0266;

  char LATIN_SMALL_LETTER_HENG_WITH_HOOK = 0x0267;

  char LATIN_SMALL_LETTER_I_WITH_STROKE = 0x0268;

  char LATIN_SMALL_LETTER_IOTA = 0x0269;

  char LATIN_LETTER_SMALL_CAPITAL_I = 0x026A;

  char LATIN_SMALL_LETTER_L_WITH_MIDDLE_TILDE = 0x026B;

  char LATIN_SMALL_LETTER_L_WITH_BELT = 0x026C;

  char LATIN_SMALL_LETTER_L_WITH_RETROFLEX_HOOK = 0x026D;

  char LATIN_SMALL_LETTER_LEZH = 0x026E;

  char LATIN_SMALL_LETTER_TURNED_M = 0x026F;

  char LATIN_SMALL_LETTER_TURNED_M_WITH_LONG_LEG = 0x0270;

  char LATIN_SMALL_LETTER_M_WITH_HOOK = 0x0271;

  char LATIN_SMALL_LETTER_N_WITH_LEFT_HOOK = 0x0272;

  char LATIN_SMALL_LETTER_N_WITH_RETROFLEX_HOOK = 0x0273;

  char LATIN_LETTER_SMALL_CAPITAL_N = 0x0274;

  char LATIN_SMALL_LETTER_BARRED_O = 0x0275;

  char LATIN_LETTER_SMALL_CAPITAL_OE = 0x0276;

  char LATIN_SMALL_LETTER_CLOSED_OMEGA = 0x0277;

  char LATIN_SMALL_LETTER_PHI = 0x0278;

  char LATIN_SMALL_LETTER_TURNED_R = 0x0279;

  char LATIN_SMALL_LETTER_TURNED_R_WITH_LONG_LEG = 0x027A;

  char LATIN_SMALL_LETTER_TURNED_R_WITH_HOOK = 0x027B;

  char LATIN_SMALL_LETTER_R_WITH_LONG_LEG = 0x027C;

  char LATIN_SMALL_LETTER_R_WITH_TAIL = 0x027D;

  char LATIN_SMALL_LETTER_R_WITH_FISHHOOK = 0x027E;

  char LATIN_SMALL_LETTER_REVERSED_R_WITH_FISHHOOK = 0x027F;

  char LATIN_LETTER_SMALL_CAPITAL_R = 0x0280;

  char LATIN_LETTER_SMALL_CAPITAL_INVERTED_R = 0x0281;

  char LATIN_SMALL_LETTER_S_WITH_HOOK = 0x0282;

  char LATIN_SMALL_LETTER_ESH = 0x0283;

  char LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE_AND_HOOK = 0x0284;

  char LATIN_SMALL_LETTER_SQUAT_REVERSED_ESH = 0x0285;

  char LATIN_SMALL_LETTER_ESH_WITH_CURL = 0x0286;

  char LATIN_SMALL_LETTER_TURNED_T = 0x0287;

  char LATIN_SMALL_LETTER_T_WITH_RETROFLEX_HOOK = 0x0288;

  char LATIN_SMALL_LETTER_U_BAR = 0x0289;

  char LATIN_SMALL_LETTER_UPSILON = 0x028A;

  char LATIN_SMALL_LETTER_V_WITH_HOOK = 0x028B;

  char LATIN_SMALL_LETTER_TURNED_V = 0x028C;

  char LATIN_SMALL_LETTER_TURNED_W = 0x028D;

  char LATIN_SMALL_LETTER_TURNED_Y = 0x028E;

  char LATIN_LETTER_SMALL_CAPITAL_Y = 0x028F;

  char LATIN_SMALL_LETTER_Z_WITH_RETROFLEX_HOOK = 0x0290;

  char LATIN_SMALL_LETTER_Z_WITH_CURL = 0x0291;

  char LATIN_SMALL_LETTER_EZH = 0x0292;

  char LATIN_SMALL_LETTER_EZH_WITH_CURL = 0x0293;

  char LATIN_LETTER_GLOTTAL_STOP = 0x0294;

  char LATIN_LETTER_PHARYNGEAL_VOICED_FRICATIVE = 0x0295;

  char LATIN_LETTER_INVERTED_GLOTTAL_STOP = 0x0296;

  char LATIN_LETTER_STRETCHED_C = 0x0297;

  char LATIN_LETTER_BILABIAL_CLICK = 0x0298;

  char LATIN_LETTER_SMALL_CAPITAL_B = 0x0299;

  char LATIN_SMALL_LETTER_CLOSED_OPEN_E = 0x029A;

  char LATIN_LETTER_SMALL_CAPITAL_G_WITH_HOOK = 0x029B;

  char LATIN_LETTER_SMALL_CAPITAL_H = 0x029C;

  char LATIN_SMALL_LETTER_J_WITH_CROSSED_TAIL = 0x029D;

  char LATIN_SMALL_LETTER_TURNED_K = 0x029E;

  char LATIN_LETTER_SMALL_CAPITAL_L = 0x029F;

  char LATIN_SMALL_LETTER_Q_WITH_HOOK = 0x02A0;

  char LATIN_LETTER_GLOTTAL_STOP_WITH_STROKE = 0x02A1;

  char LATIN_LETTER_REVERSED_GLOTTAL_STOP_WITH_STROKE = 0x02A2;

  char LATIN_SMALL_LETTER_DZ_DIGRAPH = 0x02A3;

  char LATIN_SMALL_LETTER_DEZH_DIGRAPH = 0x02A4;

  char LATIN_SMALL_LETTER_DZ_DIGRAPH_WITH_CURL = 0x02A5;

  char LATIN_SMALL_LETTER_TS_DIGRAPH = 0x02A6;

  char LATIN_SMALL_LETTER_TESH_DIGRAPH = 0x02A7;

  char LATIN_SMALL_LETTER_TC_DIGRAPH_WITH_CURL = 0x02A8;

  char LATIN_SMALL_LETTER_FENG_DIGRAPH = 0x02A9;

  char LATIN_SMALL_LETTER_LS_DIGRAPH = 0x02AA;

  char LATIN_SMALL_LETTER_LZ_DIGRAPH = 0x02AB;

  char LATIN_LETTER_BILABIAL_PERCUSSIVE = 0x02AC;

  char LATIN_LETTER_BIDENTAL_PERCUSSIVE = 0x02AD;

  char LATIN_SMALL_LETTER_TURNED_H_WITH_FISHHOOK = 0x02AE;

  char LATIN_SMALL_LETTER_TURNED_H_WITH_FISHHOOK_AND_TAIL = 0x02AF;

  // ** Spacing Modifier Letters (2B0-2FF) **

  char MODIFIER_LETTER_SMALL_H = 0x02B0;

  char MODIFIER_LETTER_SMALL_H_WITH_HOOK = 0x02B1;

  char MODIFIER_LETTER_SMALL_J = 0x02B2;

  char MODIFIER_LETTER_SMALL_R = 0x02B3;

  char MODIFIER_LETTER_SMALL_TURNED_R = 0x02B4;

  char MODIFIER_LETTER_SMALL_TURNED_R_WITH_HOOK = 0x02B5;

  char MODIFIER_LETTER_SMALL_CAPITAL_INVERTED_R = 0x02B6;

  char MODIFIER_LETTER_SMALL_W = 0x02B7;

  char MODIFIER_LETTER_SMALL_Y = 0x02B8;

  char MODIFIER_LETTER_PRIME = 0x02B9;

  char MODIFIER_LETTER_DOUBLE_PRIME = 0x02BA;

  char MODIFIER_LETTER_TURNED_COMMA = 0x02BB;

  char MODIFIER_LETTER_APOSTROPHE = 0x02BC;

  char MODIFIER_LETTER_REVERSED_COMMA = 0x02BD;

  char MODIFIER_LETTER_RIGHT_HALF_RING = 0x02BE;

  char MODIFIER_LETTER_LEFT_HALF_RING = 0x02BF;

  char MODIFIER_LETTER_GLOTTAL_STOP = 0x02C0;

  char MODIFIER_LETTER_REVERSED_GLOTTAL_STOP = 0x02C1;

  char MODIFIER_LETTER_LEFT_ARROWHEAD = 0x02C2;

  char MODIFIER_LETTER_RIGHT_ARROWHEAD = 0x02C3;

  char MODIFIER_LETTER_UP_ARROWHEAD = 0x02C4;

  char MODIFIER_LETTER_DOWN_ARROWHEAD = 0x02C5;

  char MODIFIER_LETTER_CIRCUMFLEX_ACCENT = 0x02C6;

  char CARON = 0x02C7;

  char MODIFIER_LETTER_VERTICAL_LINE = 0x02C8;

  char MODIFIER_LETTER_MACRON = 0x02C9;

  char MODIFIER_LETTER_ACUTE_ACCENT = 0x02CA;

  char MODIFIER_LETTER_GRAVE_ACCENT = 0x02CB;

  char MODIFIER_LETTER_LOW_VERTICAL_LINE = 0x02CC;

  char MODIFIER_LETTER_LOW_MACRON = 0x02CD;

  char MODIFIER_LETTER_LOW_GRAVE_ACCENT = 0x02CE;

  char MODIFIER_LETTER_LOW_ACUTE_ACCENT = 0x02CF;

  char MODIFIER_LETTER_TRIANGULAR_COLON = 0x02D0;

  char MODIFIER_LETTER_HALF_TRIANGULAR_COLON = 0x02D1;

  char MODIFIER_LETTER_CENTRED_RIGHT_HALF_RING = 0x02D2;

  char MODIFIER_LETTER_CENTRED_LEFT_HALF_RING = 0x02D3;

  char MODIFIER_LETTER_UP_TACK = 0x02D4;

  char MODIFIER_LETTER_DOWN_TACK = 0x02D5;

  char MODIFIER_LETTER_PLUS_SIGN = 0x02D6;

  char MODIFIER_LETTER_MINUS_SIGN = 0x02D7;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#BREVE}.
   */
  char BREVE = 0x02D8;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#DOT_ABOVE}.
   */
  char DOT_ABOVE = 0x02D9;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#RING_ABOVE}.
   */
  char RING_ABOVE = 0x02DA;

  /**
   * {@link DiacriticalMark#getSeparateCharacter() Diacritical mark character} for {@link DiacriticalMark#OGONEK}.
   */
  char OGONEK = 0x02DB;

  char SMALL_TILDE = 0x02DC;

  char DOUBLE_ACUTE_ACCENT = 0x02DD;

  char MODIFIER_LETTER_RHOTIC_HOOK = 0x02DE;

  char MODIFIER_LETTER_CROSS_ACCENT = 0x02DF;

  char MODIFIER_LETTER_SMALL_GAMMA = 0x02E0;

  char MODIFIER_LETTER_SMALL_L = 0x02E1;

  char MODIFIER_LETTER_SMALL_S = 0x02E2;

  char MODIFIER_LETTER_SMALL_X = 0x02E3;

  char MODIFIER_LETTER_SMALL_REVERSED_GLOTTAL_STOP = 0x02E4;

  char MODIFIER_LETTER_EXTRA_HIGH_TONE_BAR = 0x02E5;

  char MODIFIER_LETTER_HIGH_TONE_BAR = 0x02E6;

  char MODIFIER_LETTER_MID_TONE_BAR = 0x02E7;

  char MODIFIER_LETTER_LOW_TONE_BAR = 0x02E8;

  char MODIFIER_LETTER_EXTRA_LOW_TONE_BAR = 0x02E9;

  char MODIFIER_LETTER_YIN_DEPARTING_TONE_MARK = 0x02EA;

  char MODIFIER_LETTER_YANG_DEPARTING_TONE_MARK = 0x02EB;

  char MODIFIER_LETTER_VOICING = 0x02EC;

  char MODIFIER_LETTER_UNASPIRATED = 0x02ED;

  char MODIFIER_LETTER_DOUBLE_APOSTROPHE = 0x02EE;

  // ** Combining characters (300-36F) **

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for { link DiacriticalMark#GRAVE}.
   */
  char COMBINING_GRAVE_ACCENT = 0x0300;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for { link {@link DiacriticalMark#ACUTE} .
   */
  char COMBINING_ACUTE_ACCENT = 0x0301;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#CIRCUMFLEX} .
   */
  char COMBINING_CIRCUMFLEX_ACCENT = 0x0302;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#TILDE} .
   */
  char COMBINING_TILDE = 0x0303;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#MACRON}.
   */
  char COMBINING_MACRON = 0x0304;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for an overline (line on top).
   */
  char COMBINING_OVERLINE = 0x0305;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#BREVE}.
   */
  char COMBINING_BREVE = 0x0306;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#DOT_ABOVE}.
   */
  char COMBINING_DOT_ABOVE = 0x0307;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#DIAERESIS}.
   */
  char COMBINING_DIAERESIS = 0x0308;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#HOOK_ABOVE}.
   */
  char COMBINING_HOOK_ABOVE = 0x0309;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#RING_ABOVE}.
   */
  char COMBINING_RING_ABOVE = 0x030A;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#DOUBLE_ACUTE}.
   */
  char COMBINING_DOUBLE_ACUTE_ACCENT = 0x030B;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#CARON}.
   */
  char COMBINING_CARON = 0x030C;

  char COMBINING_VERTICAL_LINE_ABOVE = 0x030D;

  char COMBINING_DOUBLE_VERTICAL_LINE_ABOVE = 0x030E;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#DOUBLE_GRAVE}.
   */
  char COMBINING_DOUBLE_GRAVE_ACCENT = 0x030F;

  char COMBINING_CANDRABINDU = 0x0310;

  char COMBINING_INVERTED_BREVE = 0x0311;

  char COMBINING_TURNED_COMMA_ABOVE = 0x0312;

  char COMBINING_COMMA_ABOVE = 0x0313;

  char COMBINING_REVERSED_COMMA_ABOVE = 0x0314;

  char COMBINING_COMMA_ABOVE_RIGHT = 0x0315;

  char COMBINING_GRAVE_ACCENT_BELOW = 0x0316;

  char COMBINING_ACUTE_ACCENT_BELOW = 0x0317;

  char COMBINING_LEFT_TACK_BELOW = 0x0318;

  char COMBINING_RIGHT_TACK_BELOW = 0x0319;

  char COMBINING_LEFT_ANGLE_ABOVE = 0x031A;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for {@link DiacriticalMark#HORN_ABOVE}.
   */
  char COMBINING_HORN = 0x031B;

  char COMBINING_LEFT_HALF_RING_BELOW = 0x031C;

  char COMBINING_UP_TACK_BELOW = 0x031D;

  char COMBINING_DOWN_TACK_BELOW = 0x031E;

  char COMBINING_PLUS_SIGN_BELOW = 0x031F;

  char COMBINING_MINUS_SIGN_BELOW = 0x0320;

  char COMBINING_PALATALIZED_HOOK_BELOW = 0x0321;

  char COMBINING_RETROFLEX_HOOK_BELOW = 0x0322;

  char COMBINING_DOT_BELOW = 0x0323;

  char COMBINING_DIAERESIS_BELOW = 0x0324;

  char COMBINING_RING_BELOW = 0x0325;

  char COMBINING_COMMA_BELOW = 0x0326;

  char COMBINING_CEDILLA = 0x0327;

  char COMBINING_OGONEK = 0x0328;

  char COMBINING_VERTICAL_LINE_BELOW = 0x0329;

  char COMBINING_BRIDGE_BELOW = 0x032A;

  char COMBINING_INVERTED_DOUBLE_ARCH_BELOW = 0x032B;

  char COMBINING_CARON_BELOW = 0x032C;

  char COMBINING_CIRCUMFLEX_ACCENT_BELOW = 0x032D;

  char COMBINING_BREVE_BELOW = 0x032E;

  char COMBINING_INVERTED_BREVE_BELOW = 0x032F;

  char COMBINING_TILDE_BELOW = 0x0330;

  char COMBINING_MACRON_BELOW = 0x0331;

  char COMBINING_LOW_LINE = 0x0332;

  char COMBINING_DOUBLE_LOW_LINE = 0x0333;

  char COMBINING_TILDE_OVERLAY = 0x0334;

  char COMBINING_SHORT_STROKE_OVERLAY = 0x0335;

  char COMBINING_LONG_STROKE_OVERLAY = 0x0336;

  /**
   * @see #SOLIDUS
   */
  char COMBINING_SHORT_SOLIDUS_OVERLAY = 0x0337;

  char COMBINING_LONG_SOLIDUS_OVERLAY = 0x0338;

  char COMBINING_RIGHT_HALF_RING_BELOW = 0x0339;

  char COMBINING_INVERTED_BRIDGE_BELOW = 0x033A;

  char COMBINING_SQUARE_BELOW = 0x033B;

  char COMBINING_SEAGULL_BELOW = 0x033C;

  char COMBINING_X_ABOVE = 0x033D;

  char COMBINING_VERTICAL_TILDE = 0x033E;

  char COMBINING_DOUBLE_OVERLINE = 0x033F;

  char COMBINING_GRAVE_TONE_MARK = 0x0340;

  char COMBINING_ACUTE_TONE_MARK = 0x0341;

  char COMBINING_GREEK_PERISPOMENI = 0x0342;

  char COMBINING_GREEK_KORONIS = 0x0343;

  char COMBINING_GREEK_DIALYTIKA_TONOS = 0x0344;

  char COMBINING_GREEK_YPOGEGRAMMENI = 0x0345;

  char COMBINING_BRIDGE_ABOVE = 0x0346;

  char COMBINING_EQUALS_SIGN_BELOW = 0x0347;

  char COMBINING_DOUBLE_VERTICAL_LINE_BELOW = 0x0348;

  char COMBINING_LEFT_ANGLE_BELOW = 0x0349;

  char COMBINING_NOT_TILDE_ABOVE = 0x034A;

  char COMBINING_HOMOTHETIC_ABOVE = 0x034B;

  char COMBINING_ALMOST_EQUAL_TO_ABOVE = 0x034C;

  char COMBINING_LEFT_RIGHT_ARROW_BELOW = 0x034D;

  char COMBINING_UPWARDS_ARROW_BELOW = 0x034E;

  char COMBINING_DOUBLE_TILDE = 0x0360;

  char COMBINING_DOUBLE_INVERTED_BREVE = 0x0361;

  char COMBINING_DOUBLE_RIGHTWARDS_ARROW_BELOW = 0x0362;

  // COMBINING_LATIN_SMALL_LETTER_A = 0x0363;
  // ...
  // COMBINING_LATIN_SMALL_LETTER_X = 0x036F

  char GREEK_CAPITAL_LETTER_HETA = 0x000370;

  char GREEK_SMALL_LETTER_HETA = 0x000371;

  char GREEK_CAPITAL_LETTER_ALPHA = 0x000391;

  char GREEK_CAPITAL_LETTER_BETA = 0x000392;

  char GREEK_CAPITAL_LETTER_GAMMA = 0x00393;

  char GREEK_CAPITAL_LETTER_DELTA = 0x00394;

  char GREEK_CAPITAL_LETTER_EPSILON = 0x00395;

  char GREEK_CAPITAL_LETTER_ZETA = 0x00396;

  char GREEK_CAPITAL_LETTER_ETA = 0x00397;

  char GREEK_CAPITAL_LETTER_THETA = 0x00398;

  char GREEK_CAPITAL_LETTER_IOTA = 0x00399;

  char GREEK_CAPITAL_LETTER_KAPPA = 0x0039A;

  char GREEK_CAPITAL_LETTER_LAMDA = 0x0039B;

  char GREEK_CAPITAL_LETTER_MU = 0x0039C;

  char GREEK_CAPITAL_LETTER_NU = 0x0039D;

  char GREEK_CAPITAL_LETTER_XI = 0x0039E;

  char GREEK_CAPITAL_LETTER_OMICRON = 0x0039F;

  char GREEK_CAPITAL_LETTER_PI = 0x003A0;

  char GREEK_CAPITAL_LETTER_RHO = 0x003A1;

  char GREEK_CAPITAL_LETTER_SIGMA = 0x003A3;

  char GREEK_CAPITAL_LETTER_TAU = 0x003A4;

  char GREEK_CAPITAL_LETTER_UPSILON = 0x003A5;

  char GREEK_CAPITAL_LETTER_PHI = 0x003A6;

  char GREEK_CAPITAL_LETTER_CHI = 0x003A7;

  char GREEK_CAPITAL_LETTER_PSI = 0x003A8;

  char GREEK_CAPITAL_LETTER_OMEGA = 0x003A9;

  char GREEK_CAPITAL_LETTER_IOTA_WITH_DIALYTIKA = 0x003AA;

  char GREEK_CAPITAL_LETTER_UPSILON_WITH_DIALYTIKA = 0x003AB;

  char GREEK_SMALL_LETTER_ALPHA_WITH_TONOS = 0x003AC;

  char GREEK_SMALL_LETTER_EPSILON_WITH_TONOS = 0x003AD;

  char GREEK_SMALL_LETTER_ETA_WITH_TONOS = 0x003AE;

  char GREEK_SMALL_LETTER_IOTA_WITH_TONOS = 0x003AF;

  char GREEK_SMALL_LETTER_UPSILON_WITH_DIALYTIKA_AND_TONOS = 0x003B0;

  char GREEK_SMALL_LETTER_ALPHA = 0x003B1;

  char GREEK_SMALL_LETTER_BETA = 0x003B2;

  char GREEK_SMALL_LETTER_GAMMA = 0x003B3;

  char GREEK_SMALL_LETTER_DELTA = 0x003B4;

  char GREEK_SMALL_LETTER_EPSILON = 0x003B5;

  char GREEK_SMALL_LETTER_ZETA = 0x003B6;

  char GREEK_SMALL_LETTER_ETA = 0x003B7;

  char GREEK_SMALL_LETTER_THETA = 0x003B8;

  char GREEK_SMALL_LETTER_IOTA = 0x003B9;

  char GREEK_SMALL_LETTER_KAPPA = 0x003BA;

  char GREEK_SMALL_LETTER_LAMDA = 0x003BB;

  char GREEK_SMALL_LETTER_MU = 0x003BC;

  char GREEK_SMALL_LETTER_NU = 0x003BD;

  char GREEK_SMALL_LETTER_XI = 0x003BE;

  char GREEK_SMALL_LETTER_OMICRON = 0x003BF;

  char GREEK_SMALL_LETTER_PI = 0x003C0;

  char GREEK_SMALL_LETTER_RHO = 0x003C1;

  char GREEK_SMALL_LETTER_FINAL_SIGMA = 0x003C2;

  char GREEK_SMALL_LETTER_SIGMA = 0x003C3;

  char GREEK_SMALL_LETTER_TAU = 0x003C4;

  char GREEK_SMALL_LETTER_UPSILON = 0x003C5;

  char GREEK_SMALL_LETTER_PHI = 0x003C6;

  char GREEK_SMALL_LETTER_CHI = 0x003C7;

  char GREEK_SMALL_LETTER_PSI = 0x003C8;

  char GREEK_SMALL_LETTER_OMEGA = 0x003C9;

  char GREEK_SMALL_LETTER_IOTA_WITH_DIALYTIKA = 0x003CA;

  char GREEK_SMALL_LETTER_UPSILON_WITH_DIALYTIKA = 0x003CB;

  char GREEK_SMALL_LETTER_OMICRON_WITH_TONOS = 0x003CC;

  char GREEK_SMALL_LETTER_UPSILON_WITH_TONOS = 0x003CD;

  char GREEK_SMALL_LETTER_OMEGA_WITH_TONOS = 0x003CE;

  char GREEK_CAPITAL_KAI_SYMBOL = 0x003CF;

  char GREEK_BETA_SYMBOL = 0x003D0;

  char GREEK_THETA_SYMBOL = 0x003D1;

  char GREEK_UPSILON_WITH_HOOK_SYMBOL = 0x003D2;

  char GREEK_UPSILON_WITH_ACUTE_AND_HOOK_SYMBOL = 0x003D3;

  char GREEK_UPSILON_WITH_DIAERESIS_AND_HOOK_SYMBOL = 0x003D4;

  char GREEK_PHI_SYMBOL = 0x003D5;

  char GREEK_PI_SYMBOL = 0x003D6;

  char GREEK_KAI_SYMBOL = 0x003D7;

  char GREEK_LETTER_ARCHAIC_KOPPA = 0x003D8;

  char GREEK_SMALL_LETTER_ARCHAIC_KOPPA = 0x003D9;

  char GREEK_LETTER_STIGMA = 0x003DA;

  char GREEK_SMALL_LETTER_STIGMA = 0x003DB;

  char GREEK_LETTER_DIGAMMA = 0x003DC;

  char GREEK_SMALL_LETTER_DIGAMMA = 0x003DD;

  char GREEK_LETTER_KOPPA = 0x003DE;

  char GREEK_SMALL_LETTER_KOPPA = 0x003DF;

  char GREEK_LETTER_SAMPI = 0x003E0;

  char GREEK_SMALL_LETTER_SAMPI = 0x003E1;

  char COPTIC_CAPITAL_LETTER_SHEI = 0x003E2;

  char COPTIC_SMALL_LETTER_SHEI = 0x003E3;

  char COPTIC_CAPITAL_LETTER_FEI = 0x003E4;

  char COPTIC_SMALL_LETTER_FEI = 0x003E5;

  char COPTIC_CAPITAL_LETTER_KHEI = 0x003E6;

  char COPTIC_SMALL_LETTER_KHEI = 0x003E7;

  char COPTIC_CAPITAL_LETTER_HORI = 0x003E8;

  char COPTIC_SMALL_LETTER_HORI = 0x003E9;

  char COPTIC_CAPITAL_LETTER_GANGIA = 0x003EA;

  char COPTIC_SMALL_LETTER_GANGIA = 0x003EB;

  char COPTIC_CAPITAL_LETTER_SHIMA = 0x003EC;

  char COPTIC_SMALL_LETTER_SHIMA = 0x003ED;

  char COPTIC_CAPITAL_LETTER_DEI = 0x003EE;

  char COPTIC_SMALL_LETTER_DEI = 0x003EF;

  char GREEK_KAPPA_SYMBOL = 0x003F0;

  char GREEK_RHO_SYMBOL = 0x003F1;

  char GREEK_LUNATE_SIGMA_SYMBOL = 0x003F2;

  char GREEK_LETTER_YOT = 0x003F3;

  char GREEK_CAPITAL_THETA_SYMBOL = 0x003F4;

  char GREEK_LUNATE_EPSILON_SYMBOL = 0x003F5;

  char GREEK_REVERSED_LUNATE_EPSILON_SYMBOL = 0x003F6;

  char GREEK_CAPITAL_LETTER_SHO = 0x003F7;

  char GREEK_SMALL_LETTER_SHO = 0x003F8;

  char GREEK_CAPITAL_LUNATE_SIGMA_SYMBOL = 0x003F9;

  char GREEK_CAPITAL_LETTER_SAN = 0x003FA;

  char GREEK_SMALL_LETTER_SAN = 0x003FB;

  char GREEK_RHO_WITH_STROKE_SYMBOL = 0x003FC;

  char GREEK_CAPITAL_REVERSED_LUNATE_SIGMA_SYMBOL = 0x003FD;

  char GREEK_CAPITAL_REVERSED_DOTTED_LUNATE_SIGMA_SYMBOL = 0x003FF;

  // ************************************
  // Cyrillic alphabet (0400-04FF)
  // ************************************

  char CYRILLIC_CAPITAL_LETTER_IE_WITH_GRAVE = 0x00400;

  /** The capital Cyrillic letter <em>Io</em>. Looks like an 'E' with a {@link #DIAERESIS}: {@value} */
  char CYRILLIC_CAPITAL_LETTER_IO = 0x00401;

  /**
   * The capital Cyrillic letter <em>Dje</em>. Looks like an 'b' that is open at the bottom and has a bar on top:
   * {@value}
   */
  char CYRILLIC_CAPITAL_LETTER_DJE = 0x00402;

  /** The capital Cyrillic letter <em>Gje</em>. Looks like this: {@value} */
  char CYRILLIC_CAPITAL_LETTER_GJE = 0x00403;

  /**
   * The capital Cyrillic letter <em>Ie</em> used by Ukrainian. Looks like an 'E' in the shape of an
   * {@link #EURO_CURRENCY_SIGN} : {@value}
   */
  char CYRILLIC_CAPITAL_LETTER_UKRAINIAN_IE = 0x00404;

  /** The capital Cyrillic letter <em>Dze</em>. Looks like an 'S': {@value} */
  char CYRILLIC_CAPITAL_LETTER_DZE = 0x00405;

  /**
   * The capital Cyrillic letter <em>I</em> used by Byelorussian and Ukrainian. Looks like an 'I' : {@value}
   */
  char CYRILLIC_CAPITAL_LETTER_BYELORUSSIAN_UKRAINIAN_I = 0x00406;

  char CYRILLIC_CAPITAL_LETTER_YI = 0x00407;

  char CYRILLIC_CAPITAL_LETTER_JE = 0x00408;

  char CYRILLIC_CAPITAL_LETTER_LJE = 0x00409;

  char CYRILLIC_CAPITAL_LETTER_NJE = 0x0040A;

  char CYRILLIC_CAPITAL_LETTER_TSHE = 0x0040B;

  char CYRILLIC_CAPITAL_LETTER_KJE = 0x0040C;

  char CYRILLIC_CAPITAL_LETTER_SHORT_U = 0x0040E;

  char CYRILLIC_CAPITAL_LETTER_DZHE = 0x0040F;

  char CYRILLIC_CAPITAL_LETTER_A = 0x00410;

  /** The capital Cyrillic letter <em>Be</em>. Looks like the union of an 'E' with a 'b': {@value} */
  char CYRILLIC_CAPITAL_LETTER_BE = 0x00411;

  /** The capital Cyrillic letter <em>Ve</em>. Looks like a 'B': {@value} */
  char CYRILLIC_CAPITAL_LETTER_VE = 0x00412;

  /**
   * The capital Cyrillic letter <em>Ghe</em>. Looks like a vertical and a horizontal bar crossing in the top left edge:
   * {@value}
   */
  char CYRILLIC_CAPITAL_LETTER_GHE = 0x00413;

  /** The capital Cyrillic letter <em>De</em>. Looks like similar to an 'A': {@value} */
  char CYRILLIC_CAPITAL_LETTER_DE = 0x00414;

  /** The capital Cyrillic letter <em>Ie</em>. Looks like an 'E': {@value} */
  char CYRILLIC_CAPITAL_LETTER_IE = 0x00415;

/** The capital Cyrillic letter <em>Zhe</em>. Looks like an 'X' with a vertical bar through the center or the combination of {@literal '>|<'}: {@value} */
  char CYRILLIC_CAPITAL_LETTER_ZHE = 0x00416;

  /** The capital Cyrillic letter <em>Ze</em>. Looks like a '3': {@value} */
  char CYRILLIC_CAPITAL_LETTER_ZE = 0x00417;

  /** The capital Cyrillic letter <em>I</em>. Looks like an inverted 'N': {@value} */
  char CYRILLIC_CAPITAL_LETTER_I = 0x00418;

  /** The capital Cyrillic letter <em>short I</em>. Looks like a 'B': {@value} */
  char CYRILLIC_CAPITAL_LETTER_SHORT_I = 0x00419;

  char CYRILLIC_CAPITAL_LETTER_KA = 0x0041A;

  char CYRILLIC_CAPITAL_LETTER_EL = 0x0041B;

  char CYRILLIC_CAPITAL_LETTER_EM = 0x0041C;

  char CYRILLIC_CAPITAL_LETTER_EN = 0x0041D;

  char CYRILLIC_CAPITAL_LETTER_O = 0x0041E;

  char CYRILLIC_CAPITAL_LETTER_PE = 0x0041F;

  char CYRILLIC_CAPITAL_LETTER_ER = 0x00420;

  char CYRILLIC_CAPITAL_LETTER_ES = 0x00421;

  char CYRILLIC_CAPITAL_LETTER_TE = 0x00422;

  char CYRILLIC_CAPITAL_LETTER_U = 0x00423;

  char CYRILLIC_CAPITAL_LETTER_EF = 0x00424;

  char CYRILLIC_CAPITAL_LETTER_HA = 0x00425;

  char CYRILLIC_CAPITAL_LETTER_TSE = 0x00426;

  char CYRILLIC_CAPITAL_LETTER_CHE = 0x00427;

  char CYRILLIC_CAPITAL_LETTER_SHA = 0x00428;

  char CYRILLIC_CAPITAL_LETTER_SHCHA = 0x00429;

  /** The capital Cyrillic letter TODO. Looks like this: {@value} */
  char CYRILLIC_CAPITAL_LETTER_HARD_SIGN = 0x0042A;

  char CYRILLIC_CAPITAL_LETTER_YERU = 0x0042B;

  char CYRILLIC_CAPITAL_LETTER_SOFT_SIGN = 0x0042C;

  char CYRILLIC_CAPITAL_LETTER_E = 0x0042D;

  char CYRILLIC_CAPITAL_LETTER_YU = 0x0042E;

  char CYRILLIC_CAPITAL_LETTER_YA = 0x0042F;

  char CYRILLIC_SMALL_LETTER_A = 0x00430;

  char CYRILLIC_SMALL_LETTER_BE = 0x00431;

  char CYRILLIC_SMALL_LETTER_VE = 0x00432;

  char CYRILLIC_SMALL_LETTER_GHE = 0x00433;

  char CYRILLIC_SMALL_LETTER_DE = 0x00434;

  char CYRILLIC_SMALL_LETTER_IE = 0x00435;

  char CYRILLIC_SMALL_LETTER_ZHE = 0x00436;

  char CYRILLIC_SMALL_LETTER_ZE = 0x00437;

  char CYRILLIC_SMALL_LETTER_I = 0x00438;

  char CYRILLIC_SMALL_LETTER_SHORT_I = 0x00439;

  char CYRILLIC_SMALL_LETTER_KA = 0x0043A;

  char CYRILLIC_SMALL_LETTER_EL = 0x0043B;

  char CYRILLIC_SMALL_LETTER_EM = 0x0043C;

  char CYRILLIC_SMALL_LETTER_EN = 0x0043D;

  char CYRILLIC_SMALL_LETTER_O = 0x0043E;

  char CYRILLIC_SMALL_LETTER_PE = 0x0043F;

  char CYRILLIC_SMALL_LETTER_ER = 0x00440;

  char CYRILLIC_SMALL_LETTER_ES = 0x00441;

  char CYRILLIC_SMALL_LETTER_TE = 0x00442;

  char CYRILLIC_SMALL_LETTER_U = 0x00443;

  char CYRILLIC_SMALL_LETTER_EF = 0x00444;

  char CYRILLIC_SMALL_LETTER_HA = 0x00445;

  char CYRILLIC_SMALL_LETTER_TSE = 0x00446;

  char CYRILLIC_SMALL_LETTER_CHE = 0x00447;

  char CYRILLIC_SMALL_LETTER_SHA = 0x00448;

  char CYRILLIC_SMALL_LETTER_SHCHA = 0x00449;

  char CYRILLIC_SMALL_LETTER_HARD_SIGN = 0x0044A;

  char CYRILLIC_SMALL_LETTER_YERU = 0x0044B;

  char CYRILLIC_SMALL_LETTER_SOFT_SIGN = 0x0044C;

  char CYRILLIC_SMALL_LETTER_E = 0x0044D;

  char CYRILLIC_SMALL_LETTER_YU = 0x0044E;

  char CYRILLIC_SMALL_LETTER_YA = 0x0044F;

  char CYRILLIC_SMALL_LETTER_IO = 0x00451;

  char CYRILLIC_SMALL_LETTER_DJE = 0x00452;

  char CYRILLIC_SMALL_LETTER_GJE = 0x00453;

  char CYRILLIC_SMALL_LETTER_UKRAINIAN_IE = 0x00454;

  char CYRILLIC_SMALL_LETTER_DZE = 0x00455;

  char CYRILLIC_SMALL_LETTER_BYELORUSSIAN_UKRAINIAN_I = 0x00456;

  char CYRILLIC_SMALL_LETTER_YI = 0x00457;

  char CYRILLIC_SMALL_LETTER_JE = 0x00458;

  char CYRILLIC_SMALL_LETTER_LJE = 0x00459;

  char CYRILLIC_SMALL_LETTER_NJE = 0x0045A;

  char CYRILLIC_SMALL_LETTER_TSHE = 0x0045B;

  char CYRILLIC_SMALL_LETTER_KJE = 0x0045C;

  char CYRILLIC_SMALL_LETTER_SHORT_U = 0x0045E;

  char CYRILLIC_SMALL_LETTER_DZHE = 0x0045F;

  char CYRILLIC_CAPITAL_LETTER_OMEGA = 0x000460;

  char CYRILLIC_SMALL_LETTER_OMEGA = 0x000461;

  char CYRILLIC_CAPITAL_LETTER_YAT = 0x000462;

  char CYRILLIC_SMALL_LETTER_YAT = 0x000463;

  char CYRILLIC_CAPITAL_LETTER_IOTIFIED_E = 0x000464;

  char CYRILLIC_SMALL_LETTER_IOTIFIED_E = 0x000465;

  char CYRILLIC_CAPITAL_LETTER_LITTLE_YUS = 0x000466;

  char CYRILLIC_SMALL_LETTER_LITTLE_YUS = 0x000467;

  char CYRILLIC_CAPITAL_LETTER_IOTIFIED_LITTLE_YUS = 0x000468;

  char CYRILLIC_SMALL_LETTER_IOTIFIED_LITTLE_YUS = 0x000469;

  char CYRILLIC_CAPITAL_LETTER_BIG_YUS = 0x00046A;

  char CYRILLIC_SMALL_LETTER_BIG_YUS = 0x00046B;

  char CYRILLIC_CAPITAL_LETTER_IOTIFIED_BIG_YUS = 0x00046C;

  char CYRILLIC_SMALL_LETTER_IOTIFIED_BIG_YUS = 0x00046D;

  char CYRILLIC_CAPITAL_LETTER_KSI = 0x00046E;

  char CYRILLIC_SMALL_LETTER_KSI = 0x00046F;

  char CYRILLIC_CAPITAL_LETTER_PSI = 0x000470;

  char CYRILLIC_SMALL_LETTER_PSI = 0x000471;

  char CYRILLIC_CAPITAL_LETTER_FITA = 0x000472;

  char CYRILLIC_SMALL_LETTER_FITA = 0x000473;

  char CYRILLIC_CAPITAL_LETTER_IZHITSA = 0x00474;

  char CYRILLIC_SMALL_LETTER_IZHITSA = 0x00475;

  char CYRILLIC_CAPITAL_LETTER_IZHITSA_WITH_DOUBLE_GRAVE_ACCENT = 0x00476;

  char CYRILLIC_SMALL_LETTER_IZHITSA_WITH_DOUBLE_GRAVE_ACCENT = 0x00477;

  char CYRILLIC_CAPITAL_LETTER_UK = 0x00478;

  char CYRILLIC_SMALL_LETTER_UK = 0x00479;

  char CYRILLIC_CAPITAL_LETTER_ROUND_OMEGA = 0x00047A;

  char CYRILLIC_SMALL_LETTER_ROUND_OMEGA = 0x00047B;

  char CYRILLIC_CAPITAL_LETTER_OMEGA_WITH_TITLO = 0x00047C;

  char CYRILLIC_SMALL_LETTER_OMEGA_WITH_TITLO = 0x00047D;

  char CYRILLIC_CAPITAL_LETTER_OT = 0x00047E;

  char CYRILLIC_SMALL_LETTER_OT = 0x00047F;

  char CYRILLIC_CAPITAL_LETTER_KOPPA = 0x00480;

  char CYRILLIC_SMALL_LETTER_KOPPA = 0x00481;

  char CYRILLIC_THOUSANDS_SIGN = 0x00482;

  char COMBINING_CYRILLIC_TITLO = 0x00483;

  char COMBINING_CYRILLIC_PALATALIZATION = 0x00484;

  char COMBINING_CYRILLIC_DASIA_PNEUMATA = 0x00485;

  char COMBINING_CYRILLIC_PSILI_PNEUMATA = 0x00486;

  char COMBINING_CYRILLIC_HUNDRED_THOUSANDS_SIGN = 0x00488;

  char COMBINING_CYRILLIC_MILLIONS_SIGN = 0x00489;

  char CYRILLIC_CAPITAL_LETTER_SEMISOFT_SIGN = 0x00048C;

  char CYRILLIC_SMALL_LETTER_SEMISOFT_SIGN = 0x00048D;

  char CYRILLIC_CAPITAL_LETTER_ER_WITH_TICK = 0x00048E;

  char CYRILLIC_SMALL_LETTER_ER_WITH_TICK = 0x00048F;

  char CYRILLIC_CAPITAL_LETTER_GHE_WITH_UPTURN = 0x000490;

  char CYRILLIC_SMALL_LETTER_GHE_WITH_UPTURN = 0x000491;

  char CYRILLIC_CAPITAL_LETTER_GHE_WITH_STROKE = 0x000492;

  char CYRILLIC_SMALL_LETTER_GHE_WITH_STROKE = 0x000493;

  char CYRILLIC_CAPITAL_LETTER_GHE_WITH_MIDDLE_HOOK = 0x000494;

  char CYRILLIC_SMALL_LETTER_GHE_WITH_MIDDLE_HOOK = 0x000495;

  char CYRILLIC_CAPITAL_LETTER_ZHE_WITH_DESCENDER = 0x000496;

  char CYRILLIC_SMALL_LETTER_ZHE_WITH_DESCENDER = 0x000497;

  char CYRILLIC_CAPITAL_LETTER_ZE_WITH_DESCENDER = 0x00498;

  char CYRILLIC_SMALL_LETTER_ZE_WITH_DESCENDER = 0x00499;

  char CYRILLIC_CAPITAL_LETTER_KA_WITH_DESCENDER = 0x00049A;

  char CYRILLIC_SMALL_LETTER_KA_WITH_DESCENDER = 0x00049B;

  char CYRILLIC_CAPITAL_LETTER_KA_WITH_VERTICAL_STROKE = 0x00049C;

  char CYRILLIC_SMALL_LETTER_KA_WITH_VERTICAL_STROKE = 0x00049D;

  char CYRILLIC_CAPITAL_LETTER_KA_WITH_STROKE = 0x00049E;

  char CYRILLIC_SMALL_LETTER_KA_WITH_STROKE = 0x00049F;

  char CYRILLIC_CAPITAL_LETTER_BASHKIR_KA = 0x0004A0;

  char CYRILLIC_SMALL_LETTER_BASHKIR_KA = 0x0004A1;

  char CYRILLIC_CAPITAL_LETTER_EN_WITH_DESCENDER = 0x0004A2;

  char CYRILLIC_SMALL_LETTER_EN_WITH_DESCENDER = 0x0004A3;

  char CYRILLIC_CAPITAL_LIGATURE_EN_GHE = 0x0004A4;

  char CYRILLIC_SMALL_LIGATURE_EN_GHE = 0x0004A5;

  char CYRILLIC_CAPITAL_LETTER_PE_WITH_MIDDLE_HOOK = 0x0004A6;

  char CYRILLIC_SMALL_LETTER_PE_WITH_MIDDLE_HOOK = 0x0004A7;

  char CYRILLIC_CAPITAL_LETTER_ABKHASIAN_HA = 0x0004A8;

  char CYRILLIC_SMALL_LETTER_ABKHASIAN_HA = 0x0004A9;

  char CYRILLIC_CAPITAL_LETTER_ES_WITH_DESCENDER = 0x0004AA;

  char CYRILLIC_SMALL_LETTER_ES_WITH_DESCENDER = 0x0004AB;

  char CYRILLIC_CAPITAL_LETTER_TE_WITH_DESCENDER = 0x0004AC;

  char CYRILLIC_SMALL_LETTER_TE_WITH_DESCENDER = 0x0004AD;

  char CYRILLIC_CAPITAL_LETTER_STRAIGHT_U = 0x0004AE;

  char CYRILLIC_SMALL_LETTER_STRAIGHT_U = 0x0004AF;

  char CYRILLIC_CAPITAL_LETTER_STRAIGHT_U_WITH_STROKE = 0x0004B0;

  char CYRILLIC_SMALL_LETTER_STRAIGHT_U_WITH_STROKE = 0x0004B1;

  char CYRILLIC_CAPITAL_LETTER_HA_WITH_DESCENDER = 0x0004B2;

  char CYRILLIC_SMALL_LETTER_HA_WITH_DESCENDER = 0x0004B3;

  char CYRILLIC_CAPITAL_LIGATURE_TE_TSE = 0x0004B4;

  char CYRILLIC_SMALL_LIGATURE_TE_TSE = 0x0004B5;

  char CYRILLIC_CAPITAL_LETTER_CHE_WITH_DESCENDER = 0x0004B6;

  char CYRILLIC_SMALL_LETTER_CHE_WITH_DESCENDER = 0x0004B7;

  char CYRILLIC_CAPITAL_LETTER_CHE_WITH_VERTICAL_STROKE = 0x0004B8;

  char CYRILLIC_SMALL_LETTER_CHE_WITH_VERTICAL_STROKE = 0x0004B9;

  char CYRILLIC_CAPITAL_LETTER_SHHA = 0x0004BA;

  char CYRILLIC_SMALL_LETTER_SHHA = 0x0004BB;

  char CYRILLIC_CAPITAL_LETTER_ABKHASIAN_CHE = 0x0004BC;

  char CYRILLIC_SMALL_LETTER_ABKHASIAN_CHE = 0x0004BD;

  char CYRILLIC_CAPITAL_LETTER_ABKHASIAN_CHE_WITH_DESCENDER = 0x00004BE;

  char CYRILLIC_SMALL_LETTER_ABKHASIAN_CHE_WITH_DESCENDER = 0x00004BF;

  char CYRILLIC_LETTER_PALOCHKA = 0x0004C0;

  char CYRILLIC_CAPITAL_LETTER_ZHE_WITH_BREVE = 0x00004C1;

  char CYRILLIC_SMALL_LETTER_ZHE_WITH_BREVE = 0x00004C2;

  char CYRILLIC_CAPITAL_LETTER_KA_WITH_HOOK = 0x0004C3;

  char CYRILLIC_SMALL_LETTER_KA_WITH_HOOK = 0x0004C4;

  char CYRILLIC_CAPITAL_LETTER_EN_WITH_HOOK = 0x0004C7;

  char CYRILLIC_SMALL_LETTER_EN_WITH_HOOK = 0x0004C8;

  char CYRILLIC_CAPITAL_LETTER_KHAKASSIAN_CHE = 0x0004CB;

  char CYRILLIC_SMALL_LETTER_KHAKASSIAN_CHE = 0x0004CC;

  char CYRILLIC_CAPITAL_LETTER_A_WITH_BREVE = 0x0004D0;

  char CYRILLIC_SMALL_LETTER_A_WITH_BREVE = 0x0004D1;

  char CYRILLIC_CAPITAL_LETTER_A_WITH_DIAERESIS = 0x0004D2;

  char CYRILLIC_SMALL_LETTER_A_WITH_DIAERESIS = 0x0004D3;

  char CYRILLIC_CAPITAL_LIGATURE_A_IE = 0x0004D4;

  char CYRILLIC_SMALL_LIGATURE_A_IE = 0x0004D5;

  char CYRILLIC_CAPITAL_LETTER_IE_WITH_BREVE = 0x0004D6;

  char CYRILLIC_SMALL_LETTER_IE_WITH_BREVE = 0x0004D7;

  char CYRILLIC_CAPITAL_LETTER_SCHWA = 0x0004D8;

  char CYRILLIC_SMALL_LETTER_SCHWA = 0x0004D9;

  char CYRILLIC_CAPITAL_LETTER_SCHWA_WITH_DIAERESIS = 0x0004DA;

  char CYRILLIC_SMALL_LETTER_SCHWA_WITH_DIAERESIS = 0x0004DB;

  char CYRILLIC_CAPITAL_LETTER_ZHE_WITH_DIAERESIS = 0x0004DC;

  char CYRILLIC_SMALL_LETTER_ZHE_WITH_DIAERESIS = 0x0004DD;

  char CYRILLIC_CAPITAL_LETTER_ZE_WITH_DIAERESIS = 0x0004DE;

  char CYRILLIC_SMALL_LETTER_ZE_WITH_DIAERESIS = 0x0004DF;

  char CYRILLIC_CAPITAL_LETTER_ABKHASIAN_DZE = 0x0004E0;

  char CYRILLIC_SMALL_LETTER_ABKHASIAN_DZE = 0x0004E1;

  char CYRILLIC_SMALL_LETTER_I_WITH_MACRON = 0x004E3;

  char CYRILLIC_CAPITAL_LETTER_I_WITH_DIAERESIS = 0x00004E4;

  char CYRILLIC_SMALL_LETTER_I_WITH_DIAERESIS = 0x00004E5;

  char CYRILLIC_CAPITAL_LETTER_O_WITH_DIAERESIS = 0x0004E6;

  char CYRILLIC_SMALL_LETTER_O_WITH_DIAERESIS = 0x00004E7;

  char CYRILLIC_CAPITAL_LETTER_BARRED_O = 0x0004E8;

  char CYRILLIC_SMALL_LETTER_BARRED_O = 0x00004E9;

  char CYRILLIC_CAPITAL_LETTER_BARRED_O_WITH_DIAERESIS = 0x0004EA;

  char CYRILLIC_SMALL_LETTER_BARRED_O_WITH_DIAERESIS = 0x0004EB;

  char CYRILLIC_CAPITAL_LETTER_E_WITH_DIAERESIS = 0x0004EC;

  char CYRILLIC_SMALL_LETTER_E_WITH_DIAERESIS = 0x0004ED;

  char CYRILLIC_CAPITAL_LETTER_U_WITH_MACRON = 0x0004EE;

  char CYRILLIC_SMALL_LETTER_U_WITH_MACRON = 0x0004EF;

  char CYRILLIC_CAPITAL_LETTER_U_WITH_DIAERESIS = 0x0004F0;

  char CYRILLIC_SMALL_LETTER_U_WITH_DIAERESIS = 0x00004F1;

  char CYRILLIC_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE = 0x0004F2;

  char CYRILLIC_SMALL_LETTER_U_WITH_DOUBLE_ACUTE = 0x00004F3;

  char CYRILLIC_CAPITAL_LETTER_CHE_WITH_DIAERESIS = 0x0004F4;

  char CYRILLIC_SMALL_LETTER_CHE_WITH_DIAERESIS = 0x0004F5;

  char CYRILLIC_CAPITAL_LETTER_YERU_WITH_DIAERESIS = 0x0004F8;

  char CYRILLIC_SMALL_LETTER_YERU_WITH_DIAERESIS = 0x0004F9;

  // --- ---
  char LATIN_CAPITAL_LETTER_E_WITH_MACRON_AND_ACUTE = 0x001E16;

  char LATIN_SMALL_LETTER_E_WITH_MACRON_AND_ACUTE = 0x001E17;

  char LATIN_CAPITAL_LETTER_H_WITH_DOT_BELOW = 0x001E24;

  char LATIN_CAPITAL_LETTER_H_WITH_CEDILLA = 0x001E28;

  char LATIN_SMALL_LETTER_H_WITH_CEDILLA = 0x001E29;

  char LATIN_CAPITAL_LETTER_K_WITH_ACUTE = 0x001E30;

  char LATIN_SMALL_LETTER_K_WITH_ACUTE = 0x001E31;

  char LATIN_CAPITAL_LETTER_N_WITH_DOT_ABOVE = 0x001E44;

  char LATIN_SMALL_LETTER_N_WITH_DOT_ABOVE = 0x001E45;

  char LATIN_CAPITAL_LETTER_N_WITH_DOT_BELOW = 0x001E46;

  char LATIN_SMALL_LETTER_N_WITH_DOT_BELOW = 0x001E47;

  char LATIN_CAPITAL_LETTER_P_WITH_ACUTE = 0x001E54;

  char LATIN_SMALL_LETTER_P_WITH_ACUTE = 0x001E55;

  char LATIN_CAPITAL_LETTER_Z_WITH_CIRCUMFLEX = 0x001E90;

  char LATIN_SMALL_LETTER_Z_WITH_CIRCUMFLEX = 0x001E91;

  char LATIN_CAPITAL_LETTER_Y_WITH_GRAVE = 0x001EF2;

  char LATIN_SMALL_LETTER_Y_WITH_GRAVE = 0x001EF3;

  // --- chars with more than 2 bytes ---

  char EN_SPACE = 0x02002;

  char EM_SPACE = 0x02003;

  char THREE_PER_EM_SPACE = 0x02004;

  char FOUR_PER_EM_SPACE = 0x02005;

  char FIGURE_SPACE = 0x02007;

  char PUNCTUATION_SPACE = 0x02008;

  char THIN_SPACE = 0x02009;

  // char SPACE_OF_WIDTH_5/18_EM=0x02009-0200A-0200A;
  char HAIR_SPACE = 0x0200A;

  char ZERO_WIDTH_SPACE = 0x0200B;

  /** The hyphen. Similar to ASCII hyphen-minus ('-'). */
  char HYPHEN = 0x02010;

  /** The non-breaking hyphen. */
  char NON_BREAKING_HYPHEN = 0x02011;

  /** The figure dash (for digits). */
  char FIGURE_DASH = 0x02012;

  /** The en dash - a shorter dash. */
  char EN_DASH = 0x02013;

  /** The em dash - a longer dash. */
  char EM_DASH = 0x02014;

  /** A horizontal bar. */
  char HORIZONTAL_BAR = 0x02015;

  char DOUBLE_VERTICAL_LINE = 0x02016;

  char LEFT_SINGLE_QUOTATION_MARK = 0x02018;

  char RIGHT_SINGLE_QUOTATION_MARK = 0x02019;

  char SINGLE_LOW_9_QUOTATION_MARK = 0x0201A;

  char SINGLE_HIGH_REVERSED_9_QUOTATION_MARK = 0x0201B;

  char LEFT_DOUBLE_QUOTATION_MARK = 0x0201C;

  char RIGHT_DOUBLE_QUOTATION_MARK = 0x0201D;

  char DOUBLE_LOW_9_QUOTATION_MARK = 0x0201E;

  char DOUBLE_HIGH_REVERSED_9_QUOTATION_MARK = 0x0201F;

  char DAGGER = 0x02020;

  char DOUBLE_DAGGER = 0x02021;

  char BULLET = 0x02022;

  char TRIANGULAR_BULLET = 0x02023;

  char TWO_DOT_LEADER = 0x02025;

  char HORIZONTAL_ELLIPSIS = 0x02026;

  char HYPHENATION_POINT = 0x02027;

  char PER_MILLE_SIGN = 0x02030;

  char PER_TEN_THOUSAND_SIGN = 0x02031;

  char PRIME = 0x02032;

  char DOUBLE_PRIME = 0x02033;

  char TRIPLE_PRIME = 0x02034;

  char REVERSED_PRIME = 0x02035;

  char SINGLE_LEFT_POINTING_ANGLE_QUOTATION_MARK = 0x02039;

  char SINGLE_RIGHT_POINTING_ANGLE_QUOTATION_MARK = 0x0203A;

  char CARET_INSERTION_POINT = 0x02041;

  char HYPHEN_BULLET = 0x02043;

  char REVERSED_SEMICOLON = 0x0204F;

  char SWUNG_DASH = 0x02053;

  char QUADRUPLE_PRIME = 0x02057;

  char MEDIUM_MATHEMATICAL_SPACE = 0x0205F;

  char WORD_JOINER = 0x02060;

  char FUNCTION_APPLICATION = 0x02061;

  char INVISIBLE_TIMES = 0x02062;

  char INVISIBLE_SEPARATOR = 0x02063;

  char COMBINING_THREE_DOTS_ABOVE = 0x020DB;

  char COMBINING_FOUR_DOTS_ABOVE = 0x020DC;

  /** The Sign for Currency EUR. Looks like this: {@value} */
  char EURO_CURRENCY_SIGN = 0x020AC;

  char DOUBLE_STRUCK_CAPITAL_C = 0x02102;

  /** The "care-of" (c/o) sign. Looks like this: {@value} */
  char CARE_OF = 0x02105;

  char SCRIPT_SMALL_G = 0x0210A;

  char SCRIPT_CAPITAL_H = 0x0210B;

  char BLACK_LETTER_CAPITAL_H = 0x0210C;

  char DOUBLE_STRUCK_CAPITAL_H = 0x0210D;

  char PLANCK_CONSTANT = 0x0210E;

  char PLANCK_CONSTANT_OVER_TWO_PI = 0x0210F;

  char SCRIPT_CAPITAL_I = 0x02110;

  char BLACK_LETTER_CAPITAL_I = 0x02111;

  char SCRIPT_CAPITAL_L = 0x02112;

  char SCRIPT_SMALL_L = 0x02113;

  char DOUBLE_STRUCK_CAPITAL_N = 0x02115;

  char NUMERO_SIGN = 0x02116;

  char SOUND_RECORDING_COPYRIGHT = 0x02117;

  char SCRIPT_CAPITAL_P = 0x02118;

  char DOUBLE_STRUCK_CAPITAL_P = 0x02119;

  char DOUBLE_STRUCK_CAPITAL_Q = 0x0211A;

  char SCRIPT_CAPITAL_R = 0x0211B;

  char BLACK_LETTER_CAPITAL_R = 0x0211C;

  char DOUBLE_STRUCK_CAPITAL_R = 0x0211D;

  char PRESCRIPTION_TAKE = 0x0211E;

  char TRADE_MARK_SIGN = 0x02122;

  char DOUBLE_STRUCK_CAPITAL_Z = 0x02124;

  char OHM_SIGN = 0x02126;

  char INVERTED_OHM_SIGN = 0x02127;

  char BLACK_LETTER_CAPITAL_Z = 0x02128;

  char TURNED_GREEK_SMALL_LETTER_IOTA = 0x02129;

  char ANGSTROM_SIGN = 0x0212B;

  char SCRIPT_CAPITAL_B = 0x0212C;

  char BLACK_LETTER_CAPITAL_C = 0x0212D;

  char SCRIPT_SMALL_E = 0x0212F;

  char SCRIPT_CAPITAL_E = 0x02130;

  char SCRIPT_CAPITAL_F = 0x02131;

  char SCRIPT_CAPITAL_M = 0x02133;

  char SCRIPT_SMALL_O = 0x02134;

  char ALEF_SYMBOL = 0x02135;

  char BET_SYMBOL = 0x02136;

  char GIMEL_SYMBOL = 0x02137;

  char DALET_SYMBOL = 0x02138;

  char DOUBLE_STRUCK_ITALIC_CAPITAL_D = 0x02145;

  char DOUBLE_STRUCK_ITALIC_SMALL_D = 0x02146;

  char DOUBLE_STRUCK_ITALIC_SMALL_E = 0x02147;

  char DOUBLE_STRUCK_ITALIC_SMALL_I = 0x02148;

  char VULGAR_FRACTION_ONE_THIRD = 0x02153;

  char VULGAR_FRACTION_TWO_THIRDS = 0x02154;

  char VULGAR_FRACTION_ONE_FIFTH = 0x02155;

  char VULGAR_FRACTION_TWO_FIFTHS = 0x02156;

  char VULGAR_FRACTION_THREE_FIFTHS = 0x02157;

  char VULGAR_FRACTION_FOUR_FIFTHS = 0x02158;

  char VULGAR_FRACTION_ONE_SIXTH = 0x02159;

  char VULGAR_FRACTION_FIVE_SIXTHS = 0x0215A;

  char VULGAR_FRACTION_ONE_EIGHTH = 0x0215B;

  char VULGAR_FRACTION_THREE_EIGHTHS = 0x0215C;

  char VULGAR_FRACTION_FIVE_EIGHTHS = 0x0215D;

  char VULGAR_FRACTION_SEVEN_EIGHTHS = 0x0215E;

  char LEFTWARDS_ARROW = 0x02190;

  char UPWARDS_ARROW = 0x02191;

  char RIGHTWARDS_ARROW = 0x02192;

  char DOWNWARDS_ARROW = 0x02193;

  char LEFT_RIGHT_ARROW = 0x02194;

  char UP_DOWN_ARROW = 0x02195;

  char NORTH_WEST_ARROW = 0x02196;

  char NORTH_EAST_ARROW = 0x02197;

  char SOUTH_EAST_ARROW = 0x02198;

  char SOUTH_WEST_ARROW = 0x02199;

  char LEFTWARDS_ARROW_WITH_STROKE = 0x0219A;

  char RIGHTWARDS_ARROW_WITH_STROKE = 0x0219B;

  char RIGHTWARDS_WAVE_ARROW = 0x0219D;

  // char RIGHTWARDS_WAVE_ARROW_WITH_SLASH=0x0219D-00338;
  char LEFTWARDS_TWO_HEADED_ARROW = 0x0219E;

  char UPWARDS_TWO_HEADED_ARROW = 0x0219F;

  char RIGHTWARDS_TWO_HEADED_ARROW = 0x021A0;

  char DOWNWARDS_TWO_HEADED_ARROW = 0x021A1;

  char LEFTWARDS_ARROW_WITH_TAIL = 0x021A2;

  char RIGHTWARDS_ARROW_WITH_TAIL = 0x021A3;

  char LEFTWARDS_ARROW_FROM_BAR = 0x021A4;

  char UPWARDS_ARROW_FROM_BAR = 0x021A5;

  char RIGHTWARDS_ARROW_FROM_BAR = 0x021A6;

  char DOWNWARDS_ARROW_FROM_BAR = 0x021A7;

  char LEFTWARDS_ARROW_WITH_HOOK = 0x021A9;

  char RIGHTWARDS_ARROW_WITH_HOOK = 0x021AA;

  char LEFTWARDS_ARROW_WITH_LOOP = 0x021AB;

  char RIGHTWARDS_ARROW_WITH_LOOP = 0x021AC;

  char LEFT_RIGHT_WAVE_ARROW = 0x021AD;

  char LEFT_RIGHT_ARROW_WITH_STROKE = 0x021AE;

  char UPWARDS_ARROW_WITH_TIP_LEFTWARDS = 0x021B0;

  char UPWARDS_ARROW_WITH_TIP_RIGHTWARDS = 0x021B1;

  char DOWNWARDS_ARROW_WITH_TIP_LEFTWARDS = 0x021B2;

  char DOWNWARDS_ARROW_WITH_TIP_RIGHTWARDS = 0x021B3;

  char ANTICLOCKWISE_TOP_SEMICIRCLE_ARROW = 0x021B6;

  char CLOCKWISE_TOP_SEMICIRCLE_ARROW = 0x021B7;

  char ANTICLOCKWISE_OPEN_CIRCLE_ARROW = 0x021BA;

  char CLOCKWISE_OPEN_CIRCLE_ARROW = 0x021BB;

  char LEFTWARDS_HARPOON_WITH_BARB_UPWARDS = 0x021BC;

  char LEFTWARDS_HARPOON_WITH_BARB_DOWNWARDS = 0x021BD;

  char UPWARDS_HARPOON_WITH_BARB_RIGHTWARDS = 0x021BE;

  char UPWARDS_HARPOON_WITH_BARB_LEFTWARDS = 0x021BF;

  char RIGHTWARDS_HARPOON_WITH_BARB_UPWARDS = 0x021C0;

  char RIGHTWARDS_HARPOON_WITH_BARB_DOWNWARDS = 0x021C1;

  char DOWNWARDS_HARPOON_WITH_BARB_RIGHTWARDS = 0x021C2;

  char DOWNWARDS_HARPOON_WITH_BARB_LEFTWARDS = 0x021C3;

  char RIGHTWARDS_ARROW_OVER_LEFTWARDS_ARROW = 0x021C4;

  char UPWARDS_ARROW_LEFTWARDS_OF_DOWNWARDS_ARROW = 0x021C5;

  char LEFTWARDS_ARROW_OVER_RIGHTWARDS_ARROW = 0x021C6;

  char LEFTWARDS_PAIRED_ARROWS = 0x021C7;

  char UPWARDS_PAIRED_ARROWS = 0x021C8;

  char RIGHTWARDS_PAIRED_ARROWS = 0x021C9;

  char DOWNWARDS_PAIRED_ARROWS = 0x021CA;

  char LEFTWARDS_HARPOON_OVER_RIGHTWARDS_HARPOON = 0x021CB;

  char RIGHTWARDS_HARPOON_OVER_LEFTWARDS_HARPOON = 0x021CC;

  char LEFTWARDS_DOUBLE_ARROW_WITH_STROKE = 0x021CD;

  char LEFT_RIGHT_DOUBLE_ARROW_WITH_STROKE = 0x021CE;

  char RIGHTWARDS_DOUBLE_ARROW_WITH_STROKE = 0x021CF;

  char LEFTWARDS_DOUBLE_ARROW = 0x021D0;

  char UPWARDS_DOUBLE_ARROW = 0x021D1;

  char RIGHTWARDS_DOUBLE_ARROW = 0x021D2;

  char DOWNWARDS_DOUBLE_ARROW = 0x021D3;

  char LEFT_RIGHT_DOUBLE_ARROW = 0x021D4;

  char UP_DOWN_DOUBLE_ARROW = 0x021D5;

  char NORTH_WEST_DOUBLE_ARROW = 0x021D6;

  char NORTH_EAST_DOUBLE_ARROW = 0x021D7;

  char SOUTH_EAST_DOUBLE_ARROW = 0x021D8;

  char SOUTH_WEST_DOUBLE_ARROW = 0x021D9;

  char LEFTWARDS_TRIPLE_ARROW = 0x021DA;

  char RIGHTWARDS_TRIPLE_ARROW = 0x021DB;

  char RIGHTWARDS_SQUIGGLE_ARROW = 0x021DD;

  char LEFTWARDS_ARROW_TO_BAR = 0x021E4;

  char RIGHTWARDS_ARROW_TO_BAR = 0x021E5;

  char DOWNWARDS_ARROW_LEFTWARDS_OF_UPWARDS_ARROW = 0x021F5;

  char LEFTWARDS_OPEN_HEADED_ARROW = 0x021FD;

  char RIGHTWARDS_OPEN_HEADED_ARROW = 0x021FE;

  char LEFT_RIGHT_OPEN_HEADED_ARROW = 0x021FF;

  char FOR_ALL = 0x02200;

  char COMPLEMENT = 0x02201;

  char PARTIAL_DIFFERENTIAL = 0x02202;

  // char PARTIAL_DIFFERENTIAL_WITH_SLASH=0x02202-00338;

  char THERE_EXISTS = 0x02203;

  char THERE_DOES_NOT_EXIST = 0x02204;

  char EMPTY_SET = 0x02205;

  char NABLA = 0x02207;

  char ELEMENT_OF = 0x02208;

  char NOT_AN_ELEMENT_OF = 0x02209;

  char CONTAINS_AS_MEMBER = 0x0220B;

  char DOES_NOT_CONTAIN_AS_MEMBER = 0x0220C;

  char N_ARY_PRODUCT = 0x0220F;

  char N_ARY_COPRODUCT = 0x02210;

  char N_ARY_SUMMATION = 0x02211;

  /** The unicode minus-sign. Similar to ASCII hyphen-minus ('-'). */
  char MINUS_SIGN = 0x02212;

  char MINUS_OR_PLUS_SIGN = 0x02213;

  char DOT_PLUS = 0x02214;

  char SET_MINUS = 0x02216;

  char ASTERISK_OPERATOR = 0x02217;

  char RING_OPERATOR = 0x02218;

  char SQUARE_ROOT = 0x0221A;

  char PROPORTIONAL_TO = 0x0221D;

  char INFINITY = 0x0221E;

  char RIGHT_ANGLE = 0x0221F;

  char ANGLE = 0x02220;

  // char ANGLE_WITH_VERTICAL_LINE=0x02220-020D2;
  char MEASURED_ANGLE = 0x02221;

  char SPHERICAL_ANGLE = 0x02222;

  char DIVIDES = 0x02223;

  char DOES_NOT_DIVIDE = 0x02224;

  char PARALLEL_TO = 0x02225;

  char NOT_PARALLEL_TO = 0x02226;

  char LOGICAL_AND = 0x02227;

  char LOGICAL_OR = 0x02228;

  char INTERSECTION = 0x02229;

  // char INTERSECTION_WITH_SERIFS=0x02229-0FE00;
  char UNION = 0x0222A;

  // char UNION_WITH_SERIFS=0x0222A-0FE00;
  char INTEGRAL = 0x0222B;

  char DOUBLE_INTEGRAL = 0x0222C;

  char TRIPLE_INTEGRAL = 0x0222D;

  char CONTOUR_INTEGRAL = 0x0222E;

  char SURFACE_INTEGRAL = 0x0222F;

  char VOLUME_INTEGRAL = 0x02230;

  char CLOCKWISE_INTEGRAL = 0x02231;

  char CLOCKWISE_CONTOUR_INTEGRAL = 0x02232;

  char ANTICLOCKWISE_CONTOUR_INTEGRAL = 0x02233;

  char THEREFORE = 0x02234;

  char BECAUSE = 0x02235;

  char RATIO = 0x02236;

  char PROPORTION = 0x02237;

  char DOT_MINUS = 0x02238;

  char GEOMETRIC_PROPORTION = 0x0223A;

  char HOMOTHETIC = 0x0223B;

  char TILDE_OPERATOR = 0x0223C;

  // char TILDE_OPERATOR_WITH_VERTICAL_LINE=0x0223C-020D2;
  char REVERSED_TILDE = 0x0223D;

  char INVERTED_LAZY_S = 0x0223E;

  char INVERTED_LAZY_S_WITH_DOUBLE_UNDERLINE = 0x0223E - 00333;

  char SINE_WAVE = 0x0223F;

  char WREATH_PRODUCT = 0x02240;

  char NOT_TILDE = 0x02241;

  char MINUS_TILDE = 0x02242;

  // char MINUS_TILDE_WITH_SLASH=0x02242-00338;
  char ASYMPTOTICALLY_EQUAL_TO = 0x02243;

  char NOT_ASYMPTOTICALLY_EQUAL_TO = 0x02244;

  char APPROXIMATELY_EQUAL_TO = 0x02245;

  char APPROXIMATELY_BUT_NOT_ACTUALLY_EQUAL_TO = 0x02246;

  char NEITHER_APPROXIMATELY_NOR_ACTUALLY_EQUAL_TO = 0x02247;

  char ALMOST_EQUAL_TO = 0x02248;

  char NOT_ALMOST_EQUAL_TO = 0x02249;

  char ALMOST_EQUAL_OR_EQUAL_TO = 0x0224A;

  char TRIPLE_TILDE = 0x0224B;

  // char TRIPLE_TILDE_WITH_SLASH=0x0224B-00338;
  char ALL_EQUAL_TO = 0x0224C;

  char EQUIVALENT_TO = 0x0224D;

  // char EQUIVALENT_TO_WITH_VERTICAL_LINE=0x0224D-020D2;
  char GEOMETRICALLY_EQUIVALENT_TO = 0x0224E;

  // char GEOMETRICALLY_EQUIVALENT_TO_WITH_SLASH=0x0224E-00338;
  char DIFFERENCE_BETWEEN = 0x0224F;

  // char DIFFERENCE_BETWEEN_WITH_SLASH=0x0224F-00338;
  char APPROACHES_THE_LIMIT = 0x02250;

  // char APPROACHES_THE_LIMIT_WITH_SLASH=0x02250-00338;
  char GEOMETRICALLY_EQUAL_TO = 0x02251;

  char APPROXIMATELY_EQUAL_TO_OR_THE_IMAGE_OF = 0x02252;

  char IMAGE_OF_OR_APPROXIMATELY_EQUAL_TO = 0x02253;

  char COLON_EQUALS = 0x02254;

  char EQUALS_COLON = 0x02255;

  char RING_IN_EQUAL_TO = 0x02256;

  char RING_EQUAL_TO = 0x02257;

  char ESTIMATES = 0x02259;

  char EQUIANGULAR_TO = 0x0225A;

  char DELTA_EQUAL_TO = 0x0225C;

  char QUESTIONED_EQUAL_TO = 0x0225F;

  char NOT_EQUAL_TO = 0x02260;

  char IDENTICAL_TO = 0x02261;

  // char IDENTICAL_TO_WITH_REVERSE_SLASH=0x02261-020E5;
  char NOT_IDENTICAL_TO = 0x02262;

  char LESS_THAN_OR_EQUAL_TO = 0x02264;

  // char LESS_THAN_OR_EQUAL_TO_WITH_VERTICAL_LINE=0x02264-020D2;
  char GREATER_THAN_OR_EQUAL_TO = 0x02265;

  // char GREATER_THAN_OR_EQUAL_TO_WITH_VERTICAL_LINE=0x02265-020D2;
  char LESS_THAN_OVER_EQUAL_TO = 0x02266;

  // char LESS_THAN_OVER_EQUAL_TO_WITH_SLASH=0x02266-00338;
  char GREATER_THAN_OVER_EQUAL_TO = 0x02267;

  // char GREATER_THAN_OVER_EQUAL_TO_WITH_SLASH=0x02267-00338;
  char LESS_THAN_BUT_NOT_EQUAL_TO = 0x02268;

  // char LESS_THAN_BUT_NOT_EQUAL_TO_WITH_VERTICAL_STROKE=0x02268-0FE00;
  char GREATER_THAN_BUT_NOT_EQUAL_TO = 0x02269;

  // char GREATER_THAN_BUT_NOT_EQUAL_TO_WITH_VERTICAL_STROKE=0x02269-0FE00;
  char MUCH_LESS_THAN = 0x0226A;

  // char MUCH_LESS_THAN_WITH_SLASH=0x0226A-00338;
  // char MUCH_LESS_THAN_WITH_VERTICAL_LINE=0x0226A-020D2;
  char MUCH_GREATER_THAN = 0x0226B;

  // char MUCH_GREATER_THAN_WITH_SLASH=0x0226B-00338;
  // char MUCH_GREATER_THAN_WITH_VERTICAL_LINE=0x0226B-020D2;
  char BETWEEN = 0x0226C;

  char NOT_EQUIVALENT_TO = 0x0226D;

  char NOT_LESS_THAN = 0x0226E;

  char NOT_GREATER_THAN = 0x0226F;

  char NEITHER_LESS_THAN_NOR_EQUAL_TO = 0x02270;

  char NEITHER_GREATER_THAN_NOR_EQUAL_TO = 0x02271;

  char LESS_THAN_OR_EQUIVALENT_TO = 0x02272;

  char GREATER_THAN_OR_EQUIVALENT_TO = 0x02273;

  char NEITHER_LESS_THAN_NOR_EQUIVALENT_TO = 0x02274;

  char NEITHER_GREATER_THAN_NOR_EQUIVALENT_TO = 0x02275;

  char LESS_THAN_OR_GREATER_THAN = 0x02276;

  char GREATER_THAN_OR_LESS_THAN = 0x02277;

  char NEITHER_LESS_THAN_NOR_GREATER_THAN = 0x02278;

  char NEITHER_GREATER_THAN_NOR_LESS_THAN = 0x02279;

  char PRECEDES = 0x0227A;

  char SUCCEEDS = 0x0227B;

  char PRECEDES_OR_EQUAL_TO = 0x0227C;

  char SUCCEEDS_OR_EQUAL_TO = 0x0227D;

  char PRECEDES_OR_EQUIVALENT_TO = 0x0227E;

  char SUCCEEDS_OR_EQUIVALENT_TO = 0x0227F;

  // char SUCCEEDS_OR_EQUIVALENT_TO_WITH_SLASH=0x0227F-00338;
  char DOES_NOT_PRECEDE = 0x02280;

  char DOES_NOT_SUCCEED = 0x02281;

  char SUBSET_OF = 0x02282;

  // char SUBSET_OF_WITH_VERTICAL_LINE=0x02282-020D2;
  char SUPERSET_OF = 0x02283;

  // char SUPERSET_OF_SOLIDUS=0x02283-0002F;
  // char SUPERSET_OF_WITH_VERTICAL_LINE=0x02283-020D2;
  char NOT_A_SUBSET_OF = 0x02284;

  char NOT_A_SUPERSET_OF = 0x02285;

  char SUBSET_OF_OR_EQUAL_TO = 0x02286;

  char SUPERSET_OF_OR_EQUAL_TO = 0x02287;

  char NEITHER_A_SUBSET_OF_NOR_EQUAL_TO = 0x02288;

  char NEITHER_A_SUPERSET_OF_NOR_EQUAL_TO = 0x02289;

  char SUBSET_OF_WITH_NOT_EQUAL_TO = 0x0228A;

  // char
  // SUBSET_OF_WITH_NOT_EQUAL_TO_VARIANT_WITH_THROUGH_BOTTOM_MEMBERS=0x0228A-0FE00;
  char SUPERSET_OF_WITH_NOT_EQUAL_TO = 0x0228B;

  // char
  // SUPERSET_OF_WITH_NOT_EQUAL_TO_VARIANT_WITH_THROUGH_BOTTOM_MEMBERS=0x0228B-0FE00;
  char MULTISET_MULTIPLICATION = 0x0228D;

  char MULTISET_UNION = 0x0228E;

  char SQUARE_IMAGE_OF = 0x0228F;

  // char SQUARE_IMAGE_OF_WITH_SLASH=0x0228F-00338;
  char SQUARE_ORIGINAL_OF = 0x02290;

  // char SQUARE_ORIGINAL_OF_WITH_SLASH=0x02290-00338;
  char SQUARE_IMAGE_OF_OR_EQUAL_TO = 0x02291;

  char SQUARE_ORIGINAL_OF_OR_EQUAL_TO = 0x02292;

  char SQUARE_CAP = 0x02293;

  // char SQUARE_CAP_WITH_SERIFS=0x02293-0FE00;
  char SQUARE_CUP = 0x02294;

  // char SQUARE_CUP_WITH_SERIFS=0x02294-0FE00;
  char CIRCLED_PLUS = 0x02295;

  char CIRCLED_MINUS = 0x02296;

  char CIRCLED_TIMES = 0x02297;

  char CIRCLED_DIVISION_SLASH = 0x02298;

  char CIRCLED_DOT_OPERATOR = 0x02299;

  char CIRCLED_RING_OPERATOR = 0x0229A;

  char CIRCLED_ASTERISK_OPERATOR = 0x0229B;

  char CIRCLED_DASH = 0x0229D;

  char SQUARED_PLUS = 0x0229E;

  char SQUARED_MINUS = 0x0229F;

  char SQUARED_TIMES = 0x022A0;

  char SQUARED_DOT_OPERATOR = 0x022A1;

  char RIGHT_TACK = 0x022A2;

  char LEFT_TACK = 0x022A3;

  char DOWN_TACK = 0x022A4;

  char UP_TACK = 0x022A5;

  char MODELS = 0x022A7;

  char TRUE = 0x022A8;

  char FORCES = 0x022A9;

  char TRIPLE_VERTICAL_BAR_RIGHT_TURNSTILE = 0x022AA;

  char DOUBLE_VERTICAL_BAR_DOUBLE_RIGHT_TURNSTILE = 0x022AB;

  char DOES_NOT_PROVE = 0x022AC;

  char NOT_TRUE = 0x022AD;

  char DOES_NOT_FORCE = 0x022AE;

  char NEGATED_DOUBLE_VERTICAL_BAR_DOUBLE_RIGHT_TURNSTILE = 0x022AF;

  char PRECEDES_UNDER_RELATION = 0x022B0;

  char NORMAL_SUBGROUP_OF = 0x022B2;

  char CONTAINS_AS_NORMAL_SUBGROUP = 0x022B3;

  char NORMAL_SUBGROUP_OF_OR_EQUAL_TO = 0x022B4;

  // char NORMAL_SUBGROUP_OF_OR_EQUAL_TO_WITH_VERTICAL_LINE=0x022B4-020D2;
  char CONTAINS_AS_NORMAL_SUBGROUP_OR_EQUAL_TO = 0x022B5;

  // char
  // CONTAINS_AS_NORMAL_SUBGROUP_OR_EQUAL_TO_WITH_VERTICAL_LINE=0x022B5-020D2;
  char ORIGINAL_OF = 0x022B6;

  char IMAGE_OF = 0x022B7;

  char MULTIMAP = 0x022B8;

  char HERMITIAN_CONJUGATE_MATRIX = 0x022B9;

  char INTERCALATE = 0x022BA;

  char XOR = 0x022BB;

  char NOR = 0x022BD;

  char RIGHT_ANGLE_WITH_ARC = 0x022BE;

  char RIGHT_TRIANGLE = 0x022BF;

  char N_ARY_LOGICAL_AND = 0x022C0;

  char N_ARY_LOGICAL_OR = 0x022C1;

  char N_ARY_INTERSECTION = 0x022C2;

  char N_ARY_UNION = 0x022C3;

  char DIAMOND_OPERATOR = 0x022C4;

  char DOT_OPERATOR = 0x022C5;

  char STAR_OPERATOR = 0x022C6;

  char DIVISION_TIMES = 0x022C7;

  char BOWTIE = 0x022C8;

  char LEFT_NORMAL_FACTOR_SEMIDIRECT_PRODUCT = 0x022C9;

  char RIGHT_NORMAL_FACTOR_SEMIDIRECT_PRODUCT = 0x022CA;

  char LEFT_SEMIDIRECT_PRODUCT = 0x022CB;

  char RIGHT_SEMIDIRECT_PRODUCT = 0x022CC;

  char REVERSED_TILDE_EQUALS = 0x022CD;

  char CURLY_LOGICAL_OR = 0x022CE;

  char CURLY_LOGICAL_AND = 0x022CF;

  char DOUBLE_SUBSET = 0x022D0;

  char DOUBLE_SUPERSET = 0x022D1;

  char DOUBLE_INTERSECTION = 0x022D2;

  char DOUBLE_UNION = 0x022D3;

  char PITCHFORK = 0x022D4;

  char EQUAL_AND_PARALLEL_TO = 0x022D5;

  char LESS_THAN_WITH_DOT = 0x022D6;

  char GREATER_THAN_WITH_DOT = 0x022D7;

  char VERY_MUCH_LESS_THAN = 0x022D8;

  // char VERY_MUCH_LESS_THAN_WITH_SLASH=0x022D8-00338;
  char VERY_MUCH_GREATER_THAN = 0x022D9;

  // char VERY_MUCH_GREATER_THAN_WITH_SLASH=0x022D9-00338;
  char LESS_THAN_EQUAL_TO_OR_GREATER_THAN = 0x022DA;

  // char LESS_THAN_SLANTED_EQUAL_TO_OR_GREATER_THAN=0x022DA-0FE00;
  char GREATER_THAN_EQUAL_TO_OR_LESS_THAN = 0x022DB;

  // char GREATER_THAN_SLANTED_EQUAL_TO_OR_LESS_THAN=0x022DB-0FE00;
  char EQUAL_TO_OR_PRECEDES = 0x022DE;

  char EQUAL_TO_OR_SUCCEEDS = 0x022DF;

  char DOES_NOT_PRECEDE_OR_EQUAL = 0x022E0;

  char DOES_NOT_SUCCEED_OR_EQUAL = 0x022E1;

  char NOT_SQUARE_IMAGE_OF_OR_EQUAL_TO = 0x022E2;

  char NOT_SQUARE_ORIGINAL_OF_OR_EQUAL_TO = 0x022E3;

  char LESS_THAN_BUT_NOT_EQUIVALENT_TO = 0x022E6;

  char GREATER_THAN_BUT_NOT_EQUIVALENT_TO = 0x022E7;

  char PRECEDES_BUT_NOT_EQUIVALENT_TO = 0x022E8;

  char SUCCEEDS_BUT_NOT_EQUIVALENT_TO = 0x022E9;

  char NOT_NORMAL_SUBGROUP_OF = 0x022EA;

  char DOES_NOT_CONTAIN_AS_NORMAL_SUBGROUP = 0x022EB;

  char NOT_NORMAL_SUBGROUP_OF_OR_EQUAL_TO = 0x022EC;

  char DOES_NOT_CONTAIN_AS_NORMAL_SUBGROUP_OR_EQUAL = 0x022ED;

  char VERTICAL_ELLIPSIS = 0x022EE;

  char MIDLINE_HORIZONTAL_ELLIPSIS = 0x022EF;

  char UP_RIGHT_DIAGONAL_ELLIPSIS = 0x022F0;

  char DOWN_RIGHT_DIAGONAL_ELLIPSIS = 0x022F1;

  char ELEMENT_OF_WITH_LONG_HORIZONTAL_STROKE = 0x022F2;

  char ELEMENT_OF_WITH_VERTICAL_BAR_AT_END_OF_HORIZONTAL_STROKE = 0x022F3;

  char SMALL_ELEMENT_OF_WITH_VERTICAL_BAR_AT_END_OF_HORIZONTAL_STROKE = 0x022F4;

  char ELEMENT_OF_WITH_DOT_ABOVE = 0x022F5;

  // char ELEMENT_OF_WITH_DOT_ABOVE_WITH_SLASH=0x022F5-00338;
  char ELEMENT_OF_WITH_OVERBAR = 0x022F6;

  char SMALL_ELEMENT_OF_WITH_OVERBAR = 0x022F7;

  char ELEMENT_OF_WITH_TWO_HORIZONTAL_STROKES = 0x022F9;

  // char ELEMENT_OF_WITH_TWO_HORIZONTAL_STROKES_WITH_SLASH=0x022F9-00338;
  char CONTAINS_WITH_LONG_HORIZONTAL_STROKE = 0x022FA;

  char CONTAINS_WITH_VERTICAL_BAR_AT_END_OF_HORIZONTAL_STROKE = 0x022FB;

  char SMALL_CONTAINS_WITH_VERTICAL_BAR_AT_END_OF_HORIZONTAL_STROKE = 0x022FC;

  char CONTAINS_WITH_OVERBAR = 0x022FD;

  char SMALL_CONTAINS_WITH_OVERBAR = 0x022FE;

  char PROJECTIVE = 0x02305;

  char PERSPECTIVE = 0x02306;

  char LEFT_CEILING = 0x02308;

  char RIGHT_CEILING = 0x02309;

  char LEFT_FLOOR = 0x0230A;

  char RIGHT_FLOOR = 0x0230B;

  char BOTTOM_RIGHT_CROP = 0x0230C;

  char BOTTOM_LEFT_CROP = 0x0230D;

  char TOP_RIGHT_CROP = 0x0230E;

  char TOP_LEFT_CROP = 0x0230F;

  char REVERSED_NOT_SIGN = 0x02310;

  char ARC = 0x02312;

  char SEGMENT = 0x02313;

  char TELEPHONE_RECORDER = 0x02315;

  char POSITION_INDICATOR = 0x02316;

  char TOP_LEFT_CORNER = 0x0231C;

  char TOP_RIGHT_CORNER = 0x0231D;

  char BOTTOM_LEFT_CORNER = 0x0231E;

  char BOTTOM_RIGHT_CORNER = 0x0231F;

  char FROWN = 0x02322;

  char SMILE = 0x02323;

  char LEFT_POINTING_ANGLE_BRACKET = 0x02329;

  char RIGHT_POINTING_ANGLE_BRACKET = 0x0232A;

  char CYLINDRICITY = 0x0232D;

  char ALL_AROUND_PROFILE = 0x0232E;

  char APL_FUNCTIONAL_SYMBOL_I_BEAM = 0x02336;

  char APL_FUNCTIONAL_SYMBOL_CIRCLE_STILE = 0x0233D;

  char APL_FUNCTIONAL_SYMBOL_SLASH_BAR = 0x0233F;

  char RIGHT_ANGLE_WITH_DOWNWARDS_ZIGZAG_ARROW = 0x0237C;

  char UPPER_LEFT_OR_LOWER_RIGHT_CURLY_BRACKET_SECTION = 0x023B0;

  char UPPER_RIGHT_OR_LOWER_LEFT_CURLY_BRACKET_SECTION = 0x023B1;

  char TOP_SQUARE_BRACKET = 0x023B4;

  char BOTTOM_SQUARE_BRACKET = 0x023B5;

  char BOTTOM_SQUARE_BRACKET_OVER_TOP_SQUARE_BRACKET = 0x023B6;

  char OPEN_BOX = 0x02423;

  char CIRCLED_LATIN_CAPITAL_LETTER_S = 0x024C8;

  char BOX_DRAWINGS_LIGHT_HORIZONTAL = 0x02500;

  char BOX_DRAWINGS_LIGHT_VERTICAL = 0x02502;

  char BOX_DRAWINGS_LIGHT_DOWN_AND_RIGHT = 0x0250C;

  char BOX_DRAWINGS_LIGHT_DOWN_AND_LEFT = 0x02510;

  char BOX_DRAWINGS_LIGHT_UP_AND_RIGHT = 0x02514;

  char BOX_DRAWINGS_LIGHT_UP_AND_LEFT = 0x02518;

  char BOX_DRAWINGS_LIGHT_VERTICAL_AND_RIGHT = 0x0251C;

  char BOX_DRAWINGS_LIGHT_VERTICAL_AND_LEFT = 0x02524;

  char BOX_DRAWINGS_LIGHT_DOWN_AND_HORIZONTAL = 0x0252C;

  char BOX_DRAWINGS_LIGHT_UP_AND_HORIZONTAL = 0x02534;

  char BOX_DRAWINGS_LIGHT_VERTICAL_AND_HORIZONTAL = 0x0253C;

  char BOX_DRAWINGS_DOUBLE_HORIZONTAL = 0x02550;

  char BOX_DRAWINGS_DOUBLE_VERTICAL = 0x02551;

  char BOX_DRAWINGS_DOWN_SINGLE_AND_RIGHT_DOUBLE = 0x02552;

  char BOX_DRAWINGS_DOWN_DOUBLE_AND_RIGHT_SINGLE = 0x02553;

  char BOX_DRAWINGS_DOUBLE_DOWN_AND_RIGHT = 0x02554;

  char BOX_DRAWINGS_DOWN_SINGLE_AND_LEFT_DOUBLE = 0x02555;

  char BOX_DRAWINGS_DOWN_DOUBLE_AND_LEFT_SINGLE = 0x02556;

  char BOX_DRAWINGS_DOUBLE_DOWN_AND_LEFT = 0x02557;

  char BOX_DRAWINGS_UP_SINGLE_AND_RIGHT_DOUBLE = 0x02558;

  char BOX_DRAWINGS_UP_DOUBLE_AND_RIGHT_SINGLE = 0x02559;

  char BOX_DRAWINGS_DOUBLE_UP_AND_RIGHT = 0x0255A;

  char BOX_DRAWINGS_UP_SINGLE_AND_LEFT_DOUBLE = 0x0255B;

  char BOX_DRAWINGS_UP_DOUBLE_AND_LEFT_SINGLE = 0x0255C;

  char BOX_DRAWINGS_DOUBLE_UP_AND_LEFT = 0x0255D;

  char BOX_DRAWINGS_VERTICAL_SINGLE_AND_RIGHT_DOUBLE = 0x0255E;

  char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_RIGHT_SINGLE = 0x0255F;

  char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_RIGHT = 0x02560;

  char BOX_DRAWINGS_VERTICAL_SINGLE_AND_LEFT_DOUBLE = 0x02561;

  char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_LEFT_SINGLE = 0x02562;

  char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_LEFT = 0x02563;

  char BOX_DRAWINGS_DOWN_SINGLE_AND_HORIZONTAL_DOUBLE = 0x02564;

  char BOX_DRAWINGS_DOWN_DOUBLE_AND_HORIZONTAL_SINGLE = 0x02565;

  char BOX_DRAWINGS_DOUBLE_DOWN_AND_HORIZONTAL = 0x02566;

  char BOX_DRAWINGS_UP_SINGLE_AND_HORIZONTAL_DOUBLE = 0x02567;

  char BOX_DRAWINGS_UP_DOUBLE_AND_HORIZONTAL_SINGLE = 0x02568;

  char BOX_DRAWINGS_DOUBLE_UP_AND_HORIZONTAL = 0x02569;

  char BOX_DRAWINGS_VERTICAL_SINGLE_AND_HORIZONTAL_DOUBLE = 0x0256A;

  char BOX_DRAWINGS_VERTICAL_DOUBLE_AND_HORIZONTAL_SINGLE = 0x0256B;

  char BOX_DRAWINGS_DOUBLE_VERTICAL_AND_HORIZONTAL = 0x0256C;

  char UPPER_HALF_BLOCK = 0x02580;

  char LOWER_HALF_BLOCK = 0x02584;

  char FULL_BLOCK = 0x02588;

  char LIGHT_SHADE = 0x02591;

  char MEDIUM_SHADE = 0x02592;

  char DARK_SHADE = 0x02593;

  char WHITE_SQUARE = 0x025A1;

  char BLACK_SMALL_SQUARE = 0x025AA;

  char WHITE_SMALL_SQUARE = 0x025AB;

  char WHITE_RECTANGLE = 0x025AD;

  char BLACK_VERTICAL_RECTANGLE = 0x025AE;

  char WHITE_PARALLELOGRAM = 0x025B1;

  char WHITE_UP_POINTING_TRIANGLE = 0x025B3;

  char BLACK_UP_POINTING_SMALL_TRIANGLE = 0x025B4;

  char WHITE_UP_POINTING_SMALL_TRIANGLE = 0x025B5;

  char BLACK_RIGHT_POINTING_SMALL_TRIANGLE = 0x025B8;

  char WHITE_RIGHT_POINTING_SMALL_TRIANGLE = 0x025B9;

  char WHITE_DOWN_POINTING_TRIANGLE = 0x025BD;

  char BLACK_DOWN_POINTING_SMALL_TRIANGLE = 0x025BE;

  char WHITE_DOWN_POINTING_SMALL_TRIANGLE = 0x025BF;

  char BLACK_LEFT_POINTING_SMALL_TRIANGLE = 0x025C2;

  char WHITE_LEFT_POINTING_SMALL_TRIANGLE = 0x025C3;

  char LOZENGE = 0x025CA;

  char WHITE_CIRCLE = 0x025CB;

  char WHITE_UP_POINTING_TRIANGLE_WITH_DOT = 0x025EC;

  char LARGE_CIRCLE = 0x025EF;

  char UPPER_LEFT_TRIANGLE = 0x025F8;

  char UPPER_RIGHT_TRIANGLE = 0x025F9;

  char LOWER_LEFT_TRIANGLE = 0x025FA;

  char WHITE_MEDIUM_SQUARE = 0x025FB;

  char BLACK_MEDIUM_SQUARE = 0x025FC;

  char BLACK_STAR = 0x02605;

  char WHITE_STAR = 0x02606;

  char BLACK_TELEPHONE = 0x0260E;

  char FEMALE_SIGN = 0x02640;

  char MALE_SIGN = 0x02642;

  char BLACK_SPADE_SUIT = 0x02660;

  char BLACK_CLUB_SUIT = 0x02663;

  char BLACK_HEART_SUIT = 0x02665;

  char BLACK_DIAMOND_SUIT = 0x02666;

  char EIGHTH_NOTE = 0x0266A;

  char MUSIC_FLAT_SIGN = 0x0266D;

  char MUSIC_NATURAL_SIGN = 0x0266E;

  char MUSIC_SHARP_SIGN = 0x0266F;

  char CHECK_MARK = 0x02713;

  char BALLOT_X = 0x02717;

  char MALTESE_CROSS = 0x02720;

  char SIX_POINTED_BLACK_STAR = 0x02736;

  char LIGHT_VERTICAL_BAR = 0x02758;

  char LONG_LEFTWARDS_ARROW = 0x027F5;

  char LONG_RIGHTWARDS_ARROW = 0x027F6;

  char LONG_LEFT_RIGHT_ARROW = 0x027F7;

  char LONG_LEFTWARDS_DOUBLE_ARROW = 0x027F8;

  char LONG_RIGHTWARDS_DOUBLE_ARROW = 0x027F9;

  char LONG_LEFT_RIGHT_DOUBLE_ARROW = 0x027FA;

  char LONG_RIGHTWARDS_ARROW_FROM_BAR = 0x027FC;

  char LONG_RIGHTWARDS_SQUIGGLE_ARROW = 0x027FF;

  char LEFTWARDS_DOUBLE_ARROW_WITH_VERTICAL_STROKE = 0x02902;

  char RIGHTWARDS_DOUBLE_ARROW_WITH_VERTICAL_STROKE = 0x02903;

  char LEFT_RIGHT_DOUBLE_ARROW_WITH_VERTICAL_STROKE = 0x02904;

  char RIGHTWARDS_TWO_HEADED_ARROW_FROM_BAR = 0x02905;

  char LEFTWARDS_DOUBLE_DASH_ARROW = 0x0290C;

  char RIGHTWARDS_DOUBLE_DASH_ARROW = 0x0290D;

  char LEFTWARDS_TRIPLE_DASH_ARROW = 0x0290E;

  char RIGHTWARDS_TRIPLE_DASH_ARROW = 0x0290F;

  char RIGHTWARDS_TWO_HEADED_TRIPLE_DASH_ARROW = 0x02910;

  char RIGHTWARDS_ARROW_WITH_DOTTED_STEM = 0x02911;

  char UPWARDS_ARROW_TO_BAR = 0x02912;

  char DOWNWARDS_ARROW_TO_BAR = 0x02913;

  char RIGHTWARDS_TWO_HEADED_ARROW_WITH_TAIL = 0x02916;

  char LEFTWARDS_ARROW_TAIL = 0x02919;

  char RIGHTWARDS_ARROW_TAIL = 0x0291A;

  char LEFTWARDS_DOUBLE_ARROW_TAIL = 0x0291B;

  char RIGHTWARDS_DOUBLE_ARROW_TAIL = 0x0291C;

  char LEFTWARDS_ARROW_TO_BLACK_DIAMOND = 0x0291D;

  char RIGHTWARDS_ARROW_TO_BLACK_DIAMOND = 0x0291E;

  char LEFTWARDS_ARROW_FROM_BAR_TO_BLACK_DIAMOND = 0x0291F;

  char RIGHTWARDS_ARROW_FROM_BAR_TO_BLACK_DIAMOND = 0x02920;

  char NORTH_WEST_ARROW_WITH_HOOK = 0x02923;

  char NORTH_EAST_ARROW_WITH_HOOK = 0x02924;

  char SOUTH_EAST_ARROW_WITH_HOOK = 0x02925;

  char SOUTH_WEST_ARROW_WITH_HOOK = 0x02926;

  char NORTH_WEST_ARROW_AND_NORTH_EAST_ARROW = 0x02927;

  char NORTH_EAST_ARROW_AND_SOUTH_EAST_ARROW = 0x02928;

  char SOUTH_EAST_ARROW_AND_SOUTH_WEST_ARROW = 0x02929;

  char SOUTH_WEST_ARROW_AND_NORTH_WEST_ARROW = 0x0292A;

  char WAVE_ARROW_POINTING_DIRECTLY_RIGHT = 0x02933;

  // char WAVE_ARROW_POINTING_DIRECTLY_RIGHT_WITH_SLASH=0x02933-00338;
  char ARROW_POINTING_RIGHTWARDS_THEN_CURVING_DOWNWARDS = 0x02935;

  char ARROW_POINTING_DOWNWARDS_THEN_CURVING_LEFTWARDS = 0x02936;

  char ARROW_POINTING_DOWNWARDS_THEN_CURVING_RIGHTWARDS = 0x02937;

  char RIGHT_SIDE_ARC_CLOCKWISE_ARROW = 0x02938;

  char LEFT_SIDE_ARC_ANTICLOCKWISE_ARROW = 0x02939;

  char TOP_ARC_CLOCKWISE_ARROW_WITH_MINUS = 0x0293C;

  char TOP_ARC_ANTICLOCKWISE_ARROW_WITH_PLUS = 0x0293D;

  char RIGHTWARDS_ARROW_WITH_PLUS_BELOW = 0x02945;

  char LEFT_RIGHT_ARROW_THROUGH_SMALL_CIRCLE = 0x02948;

  char UPWARDS_TWO_HEADED_ARROW_FROM_SMALL_CIRCLE = 0x02949;

  char LEFT_BARB_UP_RIGHT_BARB_DOWN_HARPOON = 0x0294A;

  char LEFT_BARB_DOWN_RIGHT_BARB_UP_HARPOON = 0x0294B;

  char LEFT_BARB_UP_RIGHT_BARB_UP_HARPOON = 0x0294E;

  char UP_BARB_RIGHT_DOWN_BARB_RIGHT_HARPOON = 0x0294F;

  char LEFT_BARB_DOWN_RIGHT_BARB_DOWN_HARPOON = 0x02950;

  char UP_BARB_LEFT_DOWN_BARB_LEFT_HARPOON = 0x02951;

  char LEFTWARDS_HARPOON_WITH_BARB_UP_TO_BAR = 0x02952;

  char RIGHTWARDS_HARPOON_WITH_BARB_UP_TO_BAR = 0x02953;

  char UPWARDS_HARPOON_WITH_BARB_RIGHT_TO_BAR = 0x02954;

  char DOWNWARDS_HARPOON_WITH_BARB_RIGHT_TO_BAR = 0x02955;

  char LEFTWARDS_HARPOON_WITH_BARB_DOWN_TO_BAR = 0x02956;

  char RIGHTWARDS_HARPOON_WITH_BARB_DOWN_TO_BAR = 0x02957;

  char UPWARDS_HARPOON_WITH_BARB_LEFT_TO_BAR = 0x02958;

  char DOWNWARDS_HARPOON_WITH_BARB_LEFT_TO_BAR = 0x02959;

  char LEFTWARDS_HARPOON_WITH_BARB_UP_FROM_BAR = 0x0295A;

  char RIGHTWARDS_HARPOON_WITH_BARB_UP_FROM_BAR = 0x0295B;

  char UPWARDS_HARPOON_WITH_BARB_RIGHT_FROM_BAR = 0x0295C;

  char DOWNWARDS_HARPOON_WITH_BARB_RIGHT_FROM_BAR = 0x0295D;

  char LEFTWARDS_HARPOON_WITH_BARB_DOWN_FROM_BAR = 0x0295E;

  char RIGHTWARDS_HARPOON_WITH_BARB_DOWN_FROM_BAR = 0x0295F;

  char UPWARDS_HARPOON_WITH_BARB_LEFT_FROM_BAR = 0x02960;

  char DOWNWARDS_HARPOON_WITH_BARB_LEFT_FROM_BAR = 0x02961;

  char LEFTWARDS_HARPOON_WITH_BARB_UP_ABOVE_LEFTWARDS_HARPOON_WITH_BARB_DOWN = 0x02962;

  char UPWARDS_HARPOON_WITH_BARB_LEFT_BESIDE_UPWARDS_HARPOON_WITH_BARB_RIGHT = 0x02963;

  char RIGHTWARDS_HARPOON_WITH_BARB_UP_ABOVE_RIGHTWARDS_HARPOON_WITH_BARB_DOWN = 0x02964;

  char DOWNWARDS_HARPOON_WITH_BARB_LEFT_BESIDE_DOWNWARDS_HARPOON_WITH_BARB_RIGHT = 0x02965;

  char LEFTWARDS_HARPOON_WITH_BARB_UP_ABOVE_RIGHTWARDS_HARPOON_WITH_BARB_UP = 0x02966;

  char LEFTWARDS_HARPOON_WITH_BARB_DOWN_ABOVE_RIGHTWARDS_HARPOON_WITH_BARB_DOWN = 0x02967;

  char RIGHTWARDS_HARPOON_WITH_BARB_UP_ABOVE_LEFTWARDS_HARPOON_WITH_BARB_UP = 0x02968;

  char RIGHTWARDS_HARPOON_WITH_BARB_DOWN_ABOVE_LEFTWARDS_HARPOON_WITH_BARB_DOWN = 0x02969;

  char LEFTWARDS_HARPOON_WITH_BARB_UP_ABOVE_LONG_DASH = 0x0296A;

  char LEFTWARDS_HARPOON_WITH_BARB_DOWN_BELOW_LONG_DASH = 0x0296B;

  char RIGHTWARDS_HARPOON_WITH_BARB_UP_ABOVE_LONG_DASH = 0x0296C;

  char RIGHTWARDS_HARPOON_WITH_BARB_DOWN_BELOW_LONG_DASH = 0x0296D;

  char UPWARDS_HARPOON_WITH_BARB_LEFT_BESIDE_DOWNWARDS_HARPOON_WITH_BARB_RIGHT = 0x0296E;

  char DOWNWARDS_HARPOON_WITH_BARB_LEFT_BESIDE_UPWARDS_HARPOON_WITH_BARB_RIGHT = 0x0296F;

  char RIGHT_DOUBLE_ARROW_WITH_ROUNDED_HEAD = 0x02970;

  char EQUALS_SIGN_ABOVE_RIGHTWARDS_ARROW = 0x02971;

  char TILDE_OPERATOR_ABOVE_RIGHTWARDS_ARROW = 0x02972;

  char LEFTWARDS_ARROW_ABOVE_TILDE_OPERATOR = 0x02973;

  char RIGHTWARDS_ARROW_ABOVE_TILDE_OPERATOR = 0x02974;

  char RIGHTWARDS_ARROW_ABOVE_ALMOST_EQUAL_TO = 0x02975;

  char LESS_THAN_ABOVE_LEFTWARDS_ARROW = 0x02976;

  char GREATER_THAN_ABOVE_RIGHTWARDS_ARROW = 0x02978;

  char SUBSET_ABOVE_RIGHTWARDS_ARROW = 0x02979;

  char SUPERSET_ABOVE_LEFTWARDS_ARROW = 0x0297B;

  char LEFT_FISH_TAIL = 0x0297C;

  char RIGHT_FISH_TAIL = 0x0297D;

  char UP_FISH_TAIL = 0x0297E;

  char DOWN_FISH_TAIL = 0x0297F;

  char LEFT_WHITE_PARENTHESIS = 0x02985;

  char RIGHT_WHITE_PARENTHESIS = 0x02986;

  char LEFT_SQUARE_BRACKET_WITH_UNDERBAR = 0x0298B;

  char RIGHT_SQUARE_BRACKET_WITH_UNDERBAR = 0x0298C;

  char LEFT_SQUARE_BRACKET_WITH_TICK_IN_TOP_CORNER = 0x0298D;

  char RIGHT_SQUARE_BRACKET_WITH_TICK_IN_BOTTOM_CORNER = 0x0298E;

  char LEFT_SQUARE_BRACKET_WITH_TICK_IN_BOTTOM_CORNER = 0x0298F;

  char RIGHT_SQUARE_BRACKET_WITH_TICK_IN_TOP_CORNER = 0x02990;

  char LEFT_ANGLE_BRACKET_WITH_DOT = 0x02991;

  char RIGHT_ANGLE_BRACKET_WITH_DOT = 0x02992;

  char LEFT_ARC_LESS_THAN_BRACKET = 0x02993;

  char RIGHT_ARC_GREATER_THAN_BRACKET = 0x02994;

  char DOUBLE_LEFT_ARC_GREATER_THAN_BRACKET = 0x02995;

  char DOUBLE_RIGHT_ARC_LESS_THAN_BRACKET = 0x02996;

  char VERTICAL_ZIGZAG_LINE = 0x0299A;

  char RIGHT_ANGLE_VARIANT_WITH_SQUARE = 0x0299C;

  char MEASURED_RIGHT_ANGLE_WITH_DOT = 0x0299D;

  char ANGLE_WITH_UNDERBAR = 0x029A4;

  char REVERSED_ANGLE_WITH_UNDERBAR = 0x029A5;

  char OBLIQUE_ANGLE_OPENING_UP = 0x029A6;

  char OBLIQUE_ANGLE_OPENING_DOWN = 0x029A7;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_UP_AND_RIGHT = 0x029A8;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_UP_AND_LEFT = 0x029A9;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_DOWN_AND_RIGHT = 0x029AA;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_DOWN_AND_LEFT = 0x029AB;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_RIGHT_AND_UP = 0x029AC;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_LEFT_AND_UP = 0x029AD;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_RIGHT_AND_DOWN = 0x029AE;

  char MEASURED_ANGLE_WITH_OPEN_ARM_ENDING_IN_ARROW_POINTING_LEFT_AND_DOWN = 0x029AF;

  char REVERSED_EMPTY_SET = 0x029B0;

  char EMPTY_SET_WITH_OVERBAR = 0x029B1;

  char EMPTY_SET_WITH_SMALL_CIRCLE_ABOVE = 0x029B2;

  char EMPTY_SET_WITH_RIGHT_ARROW_ABOVE = 0x029B3;

  char EMPTY_SET_WITH_LEFT_ARROW_ABOVE = 0x029B4;

  char CIRCLE_WITH_HORIZONTAL_BAR = 0x029B5;

  char CIRCLED_VERTICAL_BAR = 0x029B6;

  char CIRCLED_PARALLEL = 0x029B7;

  char CIRCLED_PERPENDICULAR = 0x029B9;

  char CIRCLE_WITH_SUPERIMPOSED_X = 0x029BB;

  char CIRCLED_ANTICLOCKWISE_ROTATED_DIVISION_SIGN = 0x029BC;

  char CIRCLED_WHITE_BULLET = 0x029BE;

  char CIRCLED_BULLET = 0x029BF;

  char CIRCLED_LESS_THAN = 0x029C0;

  char CIRCLED_GREATER_THAN = 0x029C1;

  char CIRCLE_WITH_SMALL_CIRCLE_TO_THE_RIGHT = 0x029C2;

  char CIRCLE_WITH_TWO_HORIZONTAL_STROKES_TO_THE_RIGHT = 0x029C3;

  char SQUARED_RISING_DIAGONAL_SLASH = 0x029C4;

  char SQUARED_FALLING_DIAGONAL_SLASH = 0x029C5;

  char TWO_JOINED_SQUARES = 0x029C9;

  char TRIANGLE_WITH_SERIFS_AT_BOTTOM = 0x029CD;

  char RIGHT_TRIANGLE_ABOVE_LEFT_TRIANGLE = 0x029CE;

  char LEFT_TRIANGLE_BESIDE_VERTICAL_BAR = 0x029CF;

  // char LEFT_TRIANGLE_BESIDE_VERTICAL_BAR_WITH_SLASH=0x029CF-00338;
  char VERTICAL_BAR_BESIDE_RIGHT_TRIANGLE = 0x029D0;

  // char VERTICAL_BAR_BESIDE_RIGHT_TRIANGLE_WITH_SLASH=0x029D0-00338;
  char LEFT_DOUBLE_WIGGLY_FENCE = 0x029DA;

  char INCOMPLETE_INFINITY = 0x029DC;

  char TIE_OVER_INFINITY = 0x029DD;

  char INFINITY_NEGATED_WITH_VERTICAL_BAR = 0x029DE;

  char EQUALS_SIGN_AND_SLANTED_PARALLEL = 0x029E3;

  char EQUALS_SIGN_AND_SLANTED_PARALLEL_WITH_TILDE_ABOVE = 0x029E4;

  char IDENTICAL_TO_AND_SLANTED_PARALLEL = 0x029E5;

  char BLACK_LOZENGE = 0x029EB;

  char RULE_DELAYED = 0x029F4;

  char SOLIDUS_WITH_OVERBAR = 0x029F6;

  char N_ARY_CIRCLED_DOT_OPERATOR = 0x02A00;

  char N_ARY_CIRCLED_PLUS_OPERATOR = 0x02A01;

  char N_ARY_CIRCLED_TIMES_OPERATOR = 0x02A02;

  char N_ARY_UNION_OPERATOR_WITH_PLUS = 0x02A04;

  char N_ARY_SQUARE_UNION_OPERATOR = 0x02A06;

  char QUADRUPLE_INTEGRAL_OPERATOR = 0x02A0C;

  char FINITE_PART_INTEGRAL = 0x02A0D;

  char CIRCULATION_FUNCTION = 0x02A10;

  char ANTICLOCKWISE_INTEGRATION = 0x02A11;

  char LINE_INTEGRATION_WITH_RECTANGULAR_PATH_AROUND_POLE = 0x02A12;

  char LINE_INTEGRATION_WITH_SEMICIRCULAR_PATH_AROUND_POLE = 0x02A13;

  char LINE_INTEGRATION_NOT_INCLUDING_THE_POLE = 0x02A14;

  char INTEGRAL_AROUND_A_POINT_OPERATOR = 0x02A15;

  char QUATERNION_INTEGRAL_OPERATOR = 0x02A16;

  char INTEGRAL_WITH_LEFTWARDS_ARROW_WITH_HOOK = 0x02A17;

  char PLUS_SIGN_WITH_SMALL_CIRCLE_ABOVE = 0x02A22;

  char PLUS_SIGN_WITH_CIRCUMFLEX_ACCENT_ABOVE = 0x02A23;

  char PLUS_SIGN_WITH_TILDE_ABOVE = 0x02A24;

  char PLUS_SIGN_WITH_DOT_BELOW = 0x02A25;

  char PLUS_SIGN_WITH_TILDE_BELOW = 0x02A26;

  char PLUS_SIGN_WITH_SUBSCRIPT_TWO = 0x02A27;

  char MINUS_SIGN_WITH_COMMA_ABOVE = 0x02A29;

  char MINUS_SIGN_WITH_DOT_BELOW = 0x02A2A;

  char PLUS_SIGN_IN_LEFT_HALF_CIRCLE = 0x02A2D;

  char PLUS_SIGN_IN_RIGHT_HALF_CIRCLE = 0x02A2E;

  char VECTOR_OR_CROSS_PRODUCT = 0x02A2F;

  char MULTIPLICATION_SIGN_WITH_DOT_ABOVE = 0x02A30;

  char MULTIPLICATION_SIGN_WITH_UNDERBAR = 0x02A31;

  char SMASH_PRODUCT = 0x02A33;

  char MULTIPLICATION_SIGN_IN_LEFT_HALF_CIRCLE = 0x02A34;

  char MULTIPLICATION_SIGN_IN_RIGHT_HALF_CIRCLE = 0x02A35;

  char CIRCLED_MULTIPLICATION_SIGN_WITH_CIRCUMFLEX_ACCENT = 0x02A36;

  char MULTIPLICATION_SIGN_IN_DOUBLE_CIRCLE = 0x02A37;

  char CIRCLED_DIVISION_SIGN = 0x02A38;

  char PLUS_SIGN_IN_TRIANGLE = 0x02A39;

  char MINUS_SIGN_IN_TRIANGLE = 0x02A3A;

  char MULTIPLICATION_SIGN_IN_TRIANGLE = 0x02A3B;

  char INTERIOR_PRODUCT = 0x02A3C;

  char AMALGAMATION_OR_COPRODUCT = 0x02A3F;

  char INTERSECTION_WITH_DOT = 0x02A40;

  char UNION_WITH_OVERBAR = 0x02A42;

  char INTERSECTION_WITH_OVERBAR = 0x02A43;

  char INTERSECTION_WITH_LOGICAL_AND = 0x02A44;

  char UNION_WITH_LOGICAL_OR = 0x02A45;

  char UNION_ABOVE_INTERSECTION = 0x02A46;

  char INTERSECTION_ABOVE_UNION = 0x02A47;

  char UNION_ABOVE_BAR_ABOVE_INTERSECTION = 0x02A48;

  char INTERSECTION_ABOVE_BAR_ABOVE_UNION = 0x02A49;

  char UNION_BESIDE_AND_JOINED_WITH_UNION = 0x02A4A;

  char INTERSECTION_BESIDE_AND_JOINED_WITH_INTERSECTION = 0x02A4B;

  char CLOSED_UNION_WITH_SERIFS = 0x02A4C;

  char CLOSED_INTERSECTION_WITH_SERIFS = 0x02A4D;

  char CLOSED_UNION_WITH_SERIFS_AND_SMASH_PRODUCT = 0x02A50;

  char DOUBLE_LOGICAL_AND = 0x02A53;

  char DOUBLE_LOGICAL_OR = 0x02A54;

  char TWO_INTERSECTING_LOGICAL_AND = 0x02A55;

  char TWO_INTERSECTING_LOGICAL_OR = 0x02A56;

  char SLOPING_LARGE_OR = 0x02A57;

  char SLOPING_LARGE_AND = 0x02A58;

  char LOGICAL_AND_WITH_MIDDLE_STEM = 0x02A5A;

  char LOGICAL_OR_WITH_MIDDLE_STEM = 0x02A5B;

  char LOGICAL_AND_WITH_HORIZONTAL_DASH = 0x02A5C;

  char LOGICAL_OR_WITH_HORIZONTAL_DASH = 0x02A5D;

  char LOGICAL_AND_WITH_UNDERBAR = 0x02A5F;

  char EQUALS_SIGN_WITH_DOT_BELOW = 0x02A66;

  char TILDE_OPERATOR_WITH_DOT_ABOVE = 0x02A6A;

  char CONGRUENT_WITH_DOT_ABOVE = 0x02A6D;

  // char CONGRUENT_WITH_DOT_ABOVE_WITH_SLASH=0x02A6D-00338;
  char EQUALS_WITH_ASTERISK = 0x02A6E;

  // char ALMOST_EQUAL_TO_WITH_CIRCUMFLEX_ACCENT=0x02A6F;
  char APPROXIMATELY_EQUAL_OR_EQUAL_TO = 0x02A70;

  // char APPROXIMATELY_EQUAL_OR_EQUAL_TO_WITH_SLASH=0x02A70-00338;
  char EQUALS_SIGN_ABOVE_PLUS_SIGN = 0x02A71;

  char PLUS_SIGN_ABOVE_EQUALS_SIGN = 0x02A72;

  char EQUALS_SIGN_ABOVE_TILDE_OPERATOR = 0x02A73;

  char DOUBLE_COLON_EQUAL = 0x02A74;

  char TWO_CONSECUTIVE_EQUALS_SIGNS = 0x02A75;

  char EQUALS_SIGN_WITH_TWO_DOTS_ABOVE_AND_TWO_DOTS_BELOW = 0x02A77;

  char EQUIVALENT_WITH_FOUR_DOTS_ABOVE = 0x02A78;

  char LESS_THAN_WITH_CIRCLE_INSIDE = 0x02A79;

  char GREATER_THAN_WITH_CIRCLE_INSIDE = 0x02A7A;

  char LESS_THAN_WITH_QUESTION_MARK_ABOVE = 0x02A7B;

  char GREATER_THAN_WITH_QUESTION_MARK_ABOVE = 0x02A7C;

  char LESS_THAN_OR_SLANTED_EQUAL_TO = 0x02A7D;

  // char LESS_THAN_OR_SLANTED_EQUAL_TO_WITH_SLASH=0x02A7D-00338;
  char GREATER_THAN_OR_SLANTED_EQUAL_TO = 0x02A7E;

  // char GREATER_THAN_OR_SLANTED_EQUAL_TO_WITH_SLASH=0x02A7E-00338;
  char LESS_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_INSIDE = 0x02A7F;

  char GREATER_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_INSIDE = 0x02A80;

  char LESS_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_ABOVE = 0x02A81;

  char GREATER_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_ABOVE = 0x02A82;

  char LESS_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_ABOVE_RIGHT = 0x02A83;

  char GREATER_THAN_OR_SLANTED_EQUAL_TO_WITH_DOT_ABOVE_LEFT = 0x02A84;

  char LESS_THAN_OR_APPROXIMATE = 0x02A85;

  char GREATER_THAN_OR_APPROXIMATE = 0x02A86;

  char LESS_THAN_AND_SINGLE_LINE_NOT_EQUAL_TO = 0x02A87;

  char GREATER_THAN_AND_SINGLE_LINE_NOT_EQUAL_TO = 0x02A88;

  char LESS_THAN_AND_NOT_APPROXIMATE = 0x02A89;

  char GREATER_THAN_AND_NOT_APPROXIMATE = 0x02A8A;

  char LESS_THAN_ABOVE_DOUBLE_LINE_EQUAL_ABOVE_GREATER_THAN = 0x02A8B;

  char GREATER_THAN_ABOVE_DOUBLE_LINE_EQUAL_ABOVE_LESS_THAN = 0x02A8C;

  char LESS_THAN_ABOVE_SIMILAR_OR_EQUAL = 0x02A8D;

  char GREATER_THAN_ABOVE_SIMILAR_OR_EQUAL = 0x02A8E;

  char LESS_THAN_ABOVE_SIMILAR_ABOVE_GREATER_THAN = 0x02A8F;

  char GREATER_THAN_ABOVE_SIMILAR_ABOVE_LESS_THAN = 0x02A90;

  char LESS_THAN_ABOVE_GREATER_THAN_ABOVE_DOUBLE_LINE_EQUAL = 0x02A91;

  char GREATER_THAN_ABOVE_LESS_THAN_ABOVE_DOUBLE_LINE_EQUAL = 0x02A92;

  char LESS_THAN_ABOVE_SLANTED_EQUAL_ABOVE_GREATER_THAN_ABOVE_SLANTED_EQUAL = 0x02A93;

  char GREATER_THAN_ABOVE_SLANTED_EQUAL_ABOVE_LESS_THAN_ABOVE_SLANTED_EQUAL = 0x02A94;

  char SLANTED_EQUAL_TO_OR_LESS_THAN = 0x02A95;

  char SLANTED_EQUAL_TO_OR_GREATER_THAN = 0x02A96;

  char SLANTED_EQUAL_TO_OR_LESS_THAN_WITH_DOT_INSIDE = 0x02A97;

  char SLANTED_EQUAL_TO_OR_GREATER_THAN_WITH_DOT_INSIDE = 0x02A98;

  char DOUBLE_LINE_EQUAL_TO_OR_LESS_THAN = 0x02A99;

  char DOUBLE_LINE_EQUAL_TO_OR_GREATER_THAN = 0x02A9A;

  char SIMILAR_OR_LESS_THAN = 0x02A9D;

  char SIMILAR_OR_GREATER_THAN = 0x02A9E;

  char SIMILAR_ABOVE_LESS_THAN_ABOVE_EQUALS_SIGN = 0x02A9F;

  char SIMILAR_ABOVE_GREATER_THAN_ABOVE_EQUALS_SIGN = 0x02AA0;

  char DOUBLE_NESTED_LESS_THAN = 0x02AA1;

  // char DOUBLE_NESTED_LESS_THAN_WITH_SLASH=0x02AA1-00338;
  char DOUBLE_NESTED_GREATER_THAN = 0x02AA2;

  // char DOUBLE_NESTED_GREATER_THAN_WITH_SLASH=0x02AA2-00338;
  char GREATER_THAN_OVERLAPPING_LESS_THAN = 0x02AA4;

  char GREATER_THAN_BESIDE_LESS_THAN = 0x02AA5;

  char LESS_THAN_CLOSED_BY_CURVE = 0x02AA6;

  char GREATER_THAN_CLOSED_BY_CURVE = 0x02AA7;

  char LESS_THAN_CLOSED_BY_CURVE_ABOVE_SLANTED_EQUAL = 0x02AA8;

  char GREATER_THAN_CLOSED_BY_CURVE_ABOVE_SLANTED_EQUAL = 0x02AA9;

  char SMALLER_THAN = 0x02AAA;

  char LARGER_THAN = 0x02AAB;

  char SMALLER_THAN_OR_EQUAL_TO = 0x02AAC;

  // char SMALLER_THAN_OR_SLANTED_EQUAL=0x02AAC-0FE00;
  char LARGER_THAN_OR_EQUAL_TO = 0x02AAD;

  // char LARGER_THAN_OR_SLANTED_EQUAL=0x02AAD-0FE00;
  char EQUALS_SIGN_WITH_BUMPY_ABOVE = 0x02AAE;

  char PRECEDES_ABOVE_SINGLE_LINE_EQUALS_SIGN = 0x02AAF;

  // char PRECEDES_ABOVE_SINGLE_LINE_EQUALS_SIGN_WITH_SLASH=0x02AAF-00338;
  char SUCCEEDS_ABOVE_SINGLE_LINE_EQUALS_SIGN = 0x02AB0;

  // char SUCCEEDS_ABOVE_SINGLE_LINE_EQUALS_SIGN_WITH_SLASH=0x02AB0-00338;
  char PRECEDES_ABOVE_EQUALS_SIGN = 0x02AB3;

  char SUCCEEDS_ABOVE_EQUALS_SIGN = 0x02AB4;

  char PRECEDES_ABOVE_NOT_EQUAL_TO = 0x02AB5;

  char SUCCEEDS_ABOVE_NOT_EQUAL_TO = 0x02AB6;

  char PRECEDES_ABOVE_ALMOST_EQUAL_TO = 0x02AB7;

  char SUCCEEDS_ABOVE_ALMOST_EQUAL_TO = 0x02AB8;

  char PRECEDES_ABOVE_NOT_ALMOST_EQUAL_TO = 0x02AB9;

  char SUCCEEDS_ABOVE_NOT_ALMOST_EQUAL_TO = 0x02ABA;

  char DOUBLE_PRECEDES = 0x02ABB;

  char DOUBLE_SUCCEEDS = 0x02ABC;

  char SUBSET_WITH_DOT = 0x02ABD;

  char SUPERSET_WITH_DOT = 0x02ABE;

  char SUBSET_WITH_PLUS_SIGN_BELOW = 0x02ABF;

  char SUPERSET_WITH_PLUS_SIGN_BELOW = 0x02AC0;

  char SUBSET_WITH_MULTIPLICATION_SIGN_BELOW = 0x02AC1;

  char SUPERSET_WITH_MULTIPLICATION_SIGN_BELOW = 0x02AC2;

  char SUBSET_OF_OR_EQUAL_TO_WITH_DOT_ABOVE = 0x02AC3;

  char SUPERSET_OF_OR_EQUAL_TO_WITH_DOT_ABOVE = 0x02AC4;

  char SUBSET_OF_ABOVE_EQUALS_SIGN = 0x02AC5;

  // char SUBSET_OF_ABOVE_EQUALS_SIGN_WITH_SLASH=0x02AC5-00338;
  char SUPERSET_OF_ABOVE_EQUALS_SIGN = 0x02AC6;

  // char SUPERSET_OF_ABOVE_EQUALS_SIGN_WITH_SLASH=0x02AC6-00338;
  char SUBSET_OF_ABOVE_TILDE_OPERATOR = 0x02AC7;

  char SUPERSET_OF_ABOVE_TILDE_OPERATOR = 0x02AC8;

  char SUBSET_OF_ABOVE_NOT_EQUAL_TO = 0x02ACB;

  // char
  // SUBSET_OF_ABOVE_NOT_EQUAL_TO_VARIANT_WITH_THROUGH_BOTTOM_MEMBERS=0x02ACB-0FE00;
  char SUPERSET_OF_ABOVE_NOT_EQUAL_TO = 0x02ACC;

  // char
  // SUPERSET_OF_ABOVE_NOT_EQUAL_TO_VARIANT_WITH_THROUGH_BOTTOM_MEMBERS=0x02ACC-0FE00;
  char CLOSED_SUBSET = 0x02ACF;

  char CLOSED_SUPERSET = 0x02AD0;

  char CLOSED_SUBSET_OR_EQUAL_TO = 0x02AD1;

  char CLOSED_SUPERSET_OR_EQUAL_TO = 0x02AD2;

  char SUBSET_ABOVE_SUPERSET = 0x02AD3;

  char SUPERSET_ABOVE_SUBSET = 0x02AD4;

  char SUBSET_ABOVE_SUBSET = 0x02AD5;

  char SUPERSET_ABOVE_SUPERSET = 0x02AD6;

  char SUPERSET_BESIDE_SUBSET = 0x02AD7;

  char SUPERSET_BESIDE_AND_JOINED_BY_DASH_WITH_SUBSET = 0x02AD8;

  char ELEMENT_OF_OPENING_DOWNWARDS = 0x02AD9;

  char PITCHFORK_WITH_TEE_TOP = 0x02ADA;

  char TRANSVERSAL_INTERSECTION = 0x02ADB;

  char VERTICAL_BAR_DOUBLE_LEFT_TURNSTILE = 0x02AE4;

  char LONG_DASH_FROM_LEFT_MEMBER_OF_DOUBLE_VERTICAL = 0x02AE6;

  char SHORT_DOWN_TACK_WITH_OVERBAR = 0x02AE7;

  char SHORT_UP_TACK_WITH_UNDERBAR = 0x02AE8;

  char SHORT_UP_TACK_ABOVE_SHORT_DOWN_TACK = 0x02AE9;

  char DOUBLE_UP_TACK = 0x02AEB;

  char DOUBLE_STROKE_NOT_SIGN = 0x02AEC;

  char REVERSED_DOUBLE_STROKE_NOT_SIGN = 0x02AED;

  char DOES_NOT_DIVIDE_WITH_REVERSED_NEGATION_SLASH = 0x02AEE;

  char VERTICAL_LINE_WITH_CIRCLE_ABOVE = 0x02AEF;

  char VERTICAL_LINE_WITH_CIRCLE_BELOW = 0x02AF0;

  char DOWN_TACK_WITH_CIRCLE_BELOW = 0x02AF1;

  char PARALLEL_WITH_HORIZONTAL_STROKE = 0x02AF2;

  char PARALLEL_WITH_TILDE_OPERATOR = 0x02AF3;

  char DOUBLE_SOLIDUS_OPERATOR = 0x02AFD;

  // char DOUBLE_SOLIDUS_OPERATOR_WITH_REVERSE_SLASH=0x02AFD-020E5;
  char LEFT_DOUBLE_ANGLE_BRACKET = 0x0300A;

  char RIGHT_DOUBLE_ANGLE_BRACKET = 0x0300B;

  char LEFT_TORTOISE_SHELL_BRACKET = 0x03014;

  char RIGHT_TORTOISE_SHELL_BRACKET = 0x03015;

  char LEFT_WHITE_TORTOISE_SHELL_BRACKET = 0x03018;

  char RIGHT_WHITE_TORTOISE_SHELL_BRACKET = 0x03019;

  char LEFT_WHITE_SQUARE_BRACKET = 0x0301A;

  char RIGHT_WHITE_SQUARE_BRACKET = 0x0301B;

  char LATIN_SMALL_LIGATURE_FF = 0x0FB00;

  char LATIN_SMALL_LIGATURE_FI = 0x0FB01;

  char LATIN_SMALL_LIGATURE_FL = 0x0FB02;

  char LATIN_SMALL_LIGATURE_FFI = 0x0FB03;

  char LATIN_SMALL_LIGATURE_FFL = 0x0FB04;

  char PRESENTATION_FORM_FOR_VERTICAL_LEFT_PARENTHESIS = 0x0FE35;

  char PRESENTATION_FORM_FOR_VERTICAL_RIGHT_PARENTHESIS = 0x0FE36;

  char PRESENTATION_FORM_FOR_VERTICAL_LEFT_CURLY_BRACKET = 0x0FE37;

  char PRESENTATION_FORM_FOR_VERTICAL_RIGHT_CURLY_BRACKET = 0x0FE38;

  char REPLACEMENT_CHARACTER = 0x0FFFD;

  // TODO
  char DOT_BELOW = '\0';

  char HOOK_ABOVE = '\0';

  /**
   * As it seems, unicode only specifies the combining double grave accent.
   *
   * @see DiacriticalMark#DOUBLE_GRAVE
   */
  char DOUBLE_GRAVE_ACCENT = '\0';

  /**
   * As it seems, unicode only specifies the combining horn.
   *
   * @see DiacriticalMark#HORN_ABOVE
   */
  char HORN = '\0';

  /**
   * This method gets the <a href="http://en.wikipedia.org/wiki/Transliteration">transliteration</a> of the given
   * <code>character</code>.
   *
   * @see java.text.Normalizer
   * @see #transliterate(String)
   *
   * @param character is the character to convert.
   * @return a sequence of characters with the transliteration of the given character or <code>null</code> if no such
   *         transliteration is available.
   */
  String transliterate(char character);

  /**
   * This method gets the <a href="http://en.wikipedia.org/wiki/Transliteration">transliteration</a> of the given
   * <code>text</code>. This method will support common transliteration standards such as ISO 843 (greek), ISO 9
   * (cyrillic), etc. However, for some writing systems there are multiple ways of transliteration and some things done
   * by this method may not officially be called transliteration. So please consider it as a pragmatic way to convert
   * text to the Latin alphabet. We are looking for help in supporting additional writing systems but not for scientific
   * discussion about linguistic.
   *
   * @see java.text.Normalizer
   *
   * @param text is the {@link String} to convert.
   * @return the transliteration of the given text. All characters that have no transliteration will remain unmodified.
   */
  String transliterate(String text);

  /**
   * This method determines an ASCII-representation for the given character if available.
   *
   * @see java.text.Normalizer
   *
   * @param character is the character to convert.
   * @return a sequence of ASCII-characters that represent the given character or <code>null</code> if the character is
   *         already ASCII or there is no ASCII-representation available.
   */
  String normalize2Ascii(char character);

  /**
   * This method converts the given <code>text</code> to the best possible ASCII-representation. All ASCII-characters
   * will remain unchanged. All other characters are {@link #normalize2Ascii(char) normalized to ASCII}.
   *
   * @see #normalize2Ascii(CharSequence, char)
   * @see java.text.Normalizer
   *
   * @param text is the text to convert.
   * @return the converted text.
   */
  String normalize2Ascii(CharSequence text);

  /**
   * This method converts the given <code>text</code> to the best possible ASCII-representation. All ASCII-characters
   * will remain unchanged. All other characters are {@link #normalize2Ascii(char) normalized to ASCII} and if not
   * possible replaced by <code>nonNormalizableCharaterReplacement</code>.
   *
   * @see java.text.Normalizer
   *
   * @param text is the text to convert.
   * @param nonNormalizableCharaterReplacement is the character used to replace unicode characters that have no
   *        {@link #normalize2Ascii(char) corresponding ASCII representation}. Use {@link #NULL} to remove these
   *        characters. A typical character to use is <code>?</code>.
   * @return the converted text.
   */
  String normalize2Ascii(CharSequence text, char nonNormalizableCharaterReplacement);

  /**
   * This method determines if the given character is a dash.
   *
   * @see #HYPHEN_MINUS
   * @see #EN_DASH
   * @see #EM_DASH
   * @see #FIGURE_DASH
   * @see #HORIZONTAL_BAR
   * @see #SWUNG_DASH
   *
   * @param character is the character to check.
   * @return <code>true</code> if <code>character</code> is a dash.
   */
  boolean isDash(char character);

  /**
   * This method determines if the given character is a hyphen. A hyphen is a character used to separate syllables in
   * words or to join words.
   *
   * @see #HYPHEN_MINUS
   * @see #HYPHEN
   * @see #HYPHEN_BULLET
   * @see #HYPHENATION_POINT
   *
   * @param character is the character to check.
   * @return <code>true</code> if <code>character</code> is a hyphen.
   */
  boolean isHyphen(char character);

  /**
   * This method determines if the given character is a minus-sign.
   *
   * @see #HYPHEN_MINUS
   * @see #MINUS_SIGN
   *
   * @param character is the character to check.
   * @return <code>true</code> if <code>character</code> is a minus sign.
   */
  boolean isMinus(char character);

}
