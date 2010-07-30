/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.DuplicateObjectException;

/**
 * This enum contains the most important diacritical marks.<br>
 * If you are NOT familiar with unicode and languages that use non-ASCII
 * characters, you should know that each {@link DiacriticalMark} represents a
 * specific shape like e.g. '~', '^', etc. that is added at a specific position
 * (on top, at bottom, etc.) to a letter. For instance if you add
 * {@link #DIAERESIS two dots} to the letter 'a' you get '&auml;'.<br>
 * To make things really complicated, unicode added
 * {@link #getCombiningCharacter() combining characters} representing the mark
 * itself in addition to the precomposed characters (combination of a specific
 * character with the mark[s]).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum DiacriticalMark implements Datatype<Character> {

  /**
   * A mark that can be placed on top of some Latin, Cyrillic or Greek
   * characters. It looks like a stroke directing to the upper right corner. If
   * your environment supports unicode, you can see it here: &#180
   */
  ACUTE(UnicodeUtil.ACUTE_ACCENT, UnicodeUtil.COMBINING_ACUTE_ACCENT, "acute accent") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_ACUTE);
      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_ACUTE);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_ACUTE);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_ACUTE);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_ACUTE);
      addComposition('l', UnicodeUtil.LATIN_SMALL_LETTER_L_WITH_ACUTE);
      addComposition('n', UnicodeUtil.LATIN_SMALL_LETTER_N_WITH_ACUTE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_ACUTE);
      addComposition('r', UnicodeUtil.LATIN_SMALL_LETTER_R_WITH_ACUTE);
      addComposition('s', UnicodeUtil.LATIN_SMALL_LETTER_S_WITH_ACUTE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_ACUTE);
      addComposition('y', UnicodeUtil.LATIN_SMALL_LETTER_Y_WITH_ACUTE);
      addComposition('z', UnicodeUtil.LATIN_SMALL_LETTER_Z_WITH_ACUTE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_ACUTE);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_ACUTE);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_ACUTE);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_ACUTE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_ACUTE);
      addComposition('L', UnicodeUtil.LATIN_CAPITAL_LETTER_L_WITH_ACUTE);
      addComposition('N', UnicodeUtil.LATIN_CAPITAL_LETTER_N_WITH_ACUTE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_ACUTE);
      addComposition('R', UnicodeUtil.LATIN_CAPITAL_LETTER_R_WITH_ACUTE);
      addComposition('S', UnicodeUtil.LATIN_CAPITAL_LETTER_S_WITH_ACUTE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_ACUTE);
      addComposition('Y', UnicodeUtil.LATIN_CAPITAL_LETTER_Y_WITH_ACUTE);
      addComposition('Z', UnicodeUtil.LATIN_CAPITAL_LETTER_Z_WITH_ACUTE);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_RING_ABOVE,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_STROKE,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_STROKE_AND_ACUTE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_STROKE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_STROKE_AND_ACUTE);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_ACUTE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_ACUTE);
    }

  },

  /**
   * A mark that can be placed on top of some Latin, ... characters. It looks
   * like an arc as the lower third of a circle. the If your environment
   * supports unicode, you can see it here: &#728;
   */
  BREVE(UnicodeUtil.BREVE, UnicodeUtil.COMBINING_BREVE, "breve") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_BREVE);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_BREVE);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_BREVE);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_BREVE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_BREVE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_BREVE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_BREVE);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_BREVE);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_BREVE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_BREVE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_BREVE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_BREVE);
    }

  },

  /**
   * A mark that can be placed on top of some Latin, ... characters. It looks
   * like a little 'v'. If your environment supports unicode, you can see it
   * here: &#711;
   */
  CARON(UnicodeUtil.CARON, UnicodeUtil.COMBINING_CARON, "caron") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_CARON);
      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_CARON);
      addComposition('d', UnicodeUtil.LATIN_SMALL_LETTER_D_WITH_CARON);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_CARON);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_CARON);
      addComposition('h', UnicodeUtil.LATIN_SMALL_LETTER_H_WITH_CARON);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_CARON);
      addComposition('j', UnicodeUtil.LATIN_SMALL_LETTER_J_WITH_CARON);
      addComposition('k', UnicodeUtil.LATIN_SMALL_LETTER_K_WITH_CARON);
      addComposition('l', UnicodeUtil.LATIN_SMALL_LETTER_L_WITH_CARON);
      addComposition('n', UnicodeUtil.LATIN_SMALL_LETTER_N_WITH_CARON);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_CARON);
      addComposition('r', UnicodeUtil.LATIN_SMALL_LETTER_R_WITH_CARON);
      addComposition('s', UnicodeUtil.LATIN_SMALL_LETTER_S_WITH_CARON);
      addComposition('t', UnicodeUtil.LATIN_SMALL_LETTER_T_WITH_CARON);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_CARON);
      addComposition('z', UnicodeUtil.LATIN_SMALL_LETTER_Z_WITH_CARON);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_CARON);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_CARON);
      addComposition('D', UnicodeUtil.LATIN_CAPITAL_LETTER_D_WITH_CARON);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_CARON);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_CARON);
      addComposition('H', UnicodeUtil.LATIN_CAPITAL_LETTER_H_WITH_CARON);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_CARON);
      addComposition('K', UnicodeUtil.LATIN_CAPITAL_LETTER_K_WITH_CARON);
      addComposition('L', UnicodeUtil.LATIN_CAPITAL_LETTER_L_WITH_CARON);
      addComposition('N', UnicodeUtil.LATIN_CAPITAL_LETTER_N_WITH_CARON);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_CARON);
      addComposition('R', UnicodeUtil.LATIN_CAPITAL_LETTER_R_WITH_CARON);
      addComposition('S', UnicodeUtil.LATIN_CAPITAL_LETTER_S_WITH_CARON);
      addComposition('T', UnicodeUtil.LATIN_CAPITAL_LETTER_T_WITH_CARON);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_CARON);
      addComposition('Z', UnicodeUtil.LATIN_CAPITAL_LETTER_Z_WITH_CARON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_CARON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_CARON);
    }

  },

  /**
   * TODO
   * 
   * @param warning
   */
  CEDILLA(UnicodeUtil.CEDILLA, UnicodeUtil.COMBINING_CEDILLA, "cedilla") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_CEDILLA);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_CEDILLA);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_CEDILLA);
      addComposition('k', UnicodeUtil.LATIN_SMALL_LETTER_K_WITH_CEDILLA);
      addComposition('l', UnicodeUtil.LATIN_SMALL_LETTER_L_WITH_CEDILLA);
      addComposition('n', UnicodeUtil.LATIN_SMALL_LETTER_N_WITH_CEDILLA);
      addComposition('r', UnicodeUtil.LATIN_SMALL_LETTER_R_WITH_CEDILLA);
      addComposition('s', UnicodeUtil.LATIN_SMALL_LETTER_S_WITH_CEDILLA);
      addComposition('t', UnicodeUtil.LATIN_SMALL_LETTER_T_WITH_CEDILLA);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_CEDILLA);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_CEDILLA);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_CEDILLA);
      addComposition('K', UnicodeUtil.LATIN_CAPITAL_LETTER_K_WITH_CEDILLA);
      addComposition('L', UnicodeUtil.LATIN_CAPITAL_LETTER_L_WITH_CEDILLA);
      addComposition('N', UnicodeUtil.LATIN_CAPITAL_LETTER_N_WITH_CEDILLA);
      addComposition('R', UnicodeUtil.LATIN_CAPITAL_LETTER_R_WITH_CEDILLA);
      addComposition('S', UnicodeUtil.LATIN_CAPITAL_LETTER_S_WITH_CEDILLA);
      addComposition('T', UnicodeUtil.LATIN_CAPITAL_LETTER_T_WITH_CEDILLA);
    }
  },

  /**
   * A mark that can be placed on top of some Latin characters (e.g. in French).
   * It looks like a small '^'. If your environment supports unicode, you can
   * see it here: &#770;
   */
  CIRCUMFLEX(UnicodeUtil.CIRCUMFLEX_ACCENT, UnicodeUtil.COMBINING_CIRCUMFLEX_ACCENT,
      "circumflex accent") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_CIRCUMFLEX);
      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_CIRCUMFLEX);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_CIRCUMFLEX);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_CIRCUMFLEX);
      addComposition('h', UnicodeUtil.LATIN_SMALL_LETTER_H_WITH_CIRCUMFLEX);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_CIRCUMFLEX);
      addComposition('j', UnicodeUtil.LATIN_SMALL_LETTER_J_WITH_CIRCUMFLEX);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_CIRCUMFLEX);
      addComposition('s', UnicodeUtil.LATIN_SMALL_LETTER_S_WITH_CIRCUMFLEX);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_CIRCUMFLEX);
      addComposition('w', UnicodeUtil.LATIN_SMALL_LETTER_W_WITH_CIRCUMFLEX);
      addComposition('y', UnicodeUtil.LATIN_SMALL_LETTER_Y_WITH_CIRCUMFLEX);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_CIRCUMFLEX);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_CIRCUMFLEX);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_CIRCUMFLEX);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_CIRCUMFLEX);
      addComposition('H', UnicodeUtil.LATIN_CAPITAL_LETTER_H_WITH_CIRCUMFLEX);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_CIRCUMFLEX);
      addComposition('J', UnicodeUtil.LATIN_CAPITAL_LETTER_J_WITH_CIRCUMFLEX);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_CIRCUMFLEX);
      addComposition('S', UnicodeUtil.LATIN_CAPITAL_LETTER_S_WITH_CIRCUMFLEX);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_CIRCUMFLEX);
      addComposition('W', UnicodeUtil.LATIN_CAPITAL_LETTER_W_WITH_CIRCUMFLEX);
      addComposition('Y', UnicodeUtil.LATIN_CAPITAL_LETTER_Y_WITH_CIRCUMFLEX);
    }

  },

  /** Two dots on top (trema, diaeresis, or umlaut). E.g. in '&auml;'. */
  DIAERESIS(UnicodeUtil.DIAERESIS, UnicodeUtil.COMBINING_DIAERESIS, "diaeresis") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DIAERESIS);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_DIAERESIS);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_DIAERESIS);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DIAERESIS);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS);
      addComposition('y', UnicodeUtil.LATIN_SMALL_LETTER_Y_WITH_DIAERESIS);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_DIAERESIS);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_DIAERESIS);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS);
      addComposition('Y', UnicodeUtil.LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_GRAVE,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_GRAVE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_GRAVE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_GRAVE);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_CARON,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_CARON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_CARON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_CARON);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_MACRON);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_ACUTE,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_ACUTE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_ACUTE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_ACUTE);
    }

  },

  /**
   * A mark attached at the top right of the letters o and u in the Vietnamese
   * alphabet (overdot).
   */
  DOT_ABOVE(UnicodeUtil.DOT_ABOVE, UnicodeUtil.COMBINING_DOT_ABOVE, "dot above") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE);
      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_DOT_ABOVE);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_DOT_ABOVE);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_DOT_ABOVE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE);
      addComposition('z', UnicodeUtil.LATIN_SMALL_LETTER_Z_WITH_DOT_ABOVE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_DOT_ABOVE);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_DOT_ABOVE);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_DOT_ABOVE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE);
      addComposition('Z', UnicodeUtil.LATIN_CAPITAL_LETTER_Z_WITH_DOT_ABOVE);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON);
    }

  },

  /**
   * TODO
   * 
   * @param warning
   */
  DOT_BELOW(UnicodeUtil.DOT_BELOW, UnicodeUtil.COMBINING_DOT_BELOW, "dot below") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      // empty... whatever this is good for...
    }

  },

  /**
   * Like {@link #ACUTE} but doubled. If your environment supports unicode, you
   * can see it here: TODO
   * 
   * @param warning
   */
  DOUBLE_ACUTE(UnicodeUtil.DOUBLE_ACUTE_ACCENT, UnicodeUtil.COMBINING_DOUBLE_ACUTE_ACCENT,
      "double acute accent") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOUBLE_ACUTE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DOUBLE_ACUTE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_ACUTE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE);
    }

  },

  /**
   * Like {@link #GRAVE} but doubled. If your environment supports unicode, you
   * can see it here: TODO
   * 
   * @param warning
   */
  DOUBLE_GRAVE(UnicodeUtil.DOUBLE_GRAVE_ACCENT, UnicodeUtil.COMBINING_DOUBLE_GRAVE_ACCENT,
      "double grave accent") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DOUBLE_GRAVE);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_DOUBLE_GRAVE);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_DOUBLE_GRAVE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOUBLE_GRAVE);
      addComposition('r', UnicodeUtil.LATIN_SMALL_LETTER_R_WITH_DOUBLE_GRAVE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DOUBLE_GRAVE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DOUBLE_GRAVE);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_DOUBLE_GRAVE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_DOUBLE_GRAVE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_GRAVE);
      addComposition('R', UnicodeUtil.LATIN_CAPITAL_LETTER_R_WITH_DOUBLE_GRAVE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_GRAVE);
    }

  },

  /**
   * TODO
   * 
   * @param warning
   */
  GRAVE(UnicodeUtil.GRAVE_ACCENT, UnicodeUtil.COMBINING_GRAVE_ACCENT, "grave accent") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_GRAVE);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_GRAVE);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_GRAVE);
      addComposition('n', UnicodeUtil.LATIN_SMALL_LETTER_N_WITH_GRAVE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_GRAVE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_GRAVE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_GRAVE);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_GRAVE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_GRAVE);
      addComposition('N', UnicodeUtil.LATIN_CAPITAL_LETTER_N_WITH_GRAVE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_GRAVE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_GRAVE);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_GRAVE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_GRAVE);
    }

  },

  /**
   * A little question mark without the dot, that is placed on top of Vietnamese
   * letters.
   */
  HOOK_ABOVE(UnicodeUtil.HOOK_ABOVE, UnicodeUtil.COMBINING_HOOK_ABOVE, "hook above") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('b', UnicodeUtil.LATIN_SMALL_LETTER_B_WITH_HOOK);
      addComposition('c', UnicodeUtil.LATIN_SMALL_LETTER_C_WITH_HOOK);
      addComposition('d', UnicodeUtil.LATIN_SMALL_LETTER_D_WITH_HOOK);
      addComposition('f', UnicodeUtil.LATIN_SMALL_LETTER_F_WITH_HOOK);
      addComposition('g', UnicodeUtil.LATIN_SMALL_LETTER_G_WITH_HOOK);
      addComposition('h', UnicodeUtil.LATIN_SMALL_LETTER_H_WITH_HOOK);
      addComposition('k', UnicodeUtil.LATIN_SMALL_LETTER_K_WITH_HOOK);
      addComposition('m', UnicodeUtil.LATIN_SMALL_LETTER_M_WITH_HOOK);
      addComposition('p', UnicodeUtil.LATIN_SMALL_LETTER_P_WITH_HOOK);
      addComposition('q', UnicodeUtil.LATIN_SMALL_LETTER_Q_WITH_HOOK);
      addComposition('s', UnicodeUtil.LATIN_SMALL_LETTER_S_WITH_HOOK);
      addComposition('t', UnicodeUtil.LATIN_SMALL_LETTER_T_WITH_HOOK);
      addComposition('v', UnicodeUtil.LATIN_SMALL_LETTER_V_WITH_HOOK);
      addComposition('y', UnicodeUtil.LATIN_SMALL_LETTER_Y_WITH_HOOK);
      addComposition('z', UnicodeUtil.LATIN_SMALL_LETTER_Z_WITH_HOOK);
      addComposition('B', UnicodeUtil.LATIN_CAPITAL_LETTER_B_WITH_HOOK);
      addComposition('C', UnicodeUtil.LATIN_CAPITAL_LETTER_C_WITH_HOOK);
      addComposition('D', UnicodeUtil.LATIN_CAPITAL_LETTER_D_WITH_HOOK);
      addComposition('F', UnicodeUtil.LATIN_CAPITAL_LETTER_F_WITH_HOOK);
      addComposition('G', UnicodeUtil.LATIN_CAPITAL_LETTER_G_WITH_HOOK);
      addComposition('K', UnicodeUtil.LATIN_CAPITAL_LETTER_K_WITH_HOOK);
      addComposition('P', UnicodeUtil.LATIN_CAPITAL_LETTER_P_WITH_HOOK);
      addComposition('T', UnicodeUtil.LATIN_CAPITAL_LETTER_T_WITH_HOOK);
      addComposition('V', UnicodeUtil.LATIN_CAPITAL_LETTER_V_WITH_HOOK);
      addComposition('Y', UnicodeUtil.LATIN_CAPITAL_LETTER_Y_WITH_HOOK);
      addComposition('Z', UnicodeUtil.LATIN_CAPITAL_LETTER_Z_WITH_HOOK);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE,
          UnicodeUtil.LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE_AND_HOOK);
    }

  },

  /**
   * A ... that is placed on top of Vietnamese vowels.
   */
  HORN_ABOVE(UnicodeUtil.HORN, UnicodeUtil.COMBINING_HORN, "horn") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_HORN);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_HORN);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_HORN);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_HORN);
    }

  },

  MACRON(UnicodeUtil.MACRON, UnicodeUtil.COMBINING_MACRON, "macron") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_MACRON);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_MACRON);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_MACRON);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_MACRON);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_MACRON);
      addComposition('y', UnicodeUtil.LATIN_SMALL_LETTER_Y_WITH_MACRON);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_MACRON);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_MACRON);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_MACRON);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_MACRON);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_MACRON);
      addComposition('Y', UnicodeUtil.LATIN_CAPITAL_LETTER_Y_WITH_MACRON);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS,
          UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_MACRON);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_OGONEK,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_OGONEK_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_OGONEK,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_OGONEK_AND_MACRON);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_TILDE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_TILDE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_TILDE,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_TILDE_AND_MACRON);

    }
  },

  OGONEK(UnicodeUtil.OGONEK, UnicodeUtil.COMBINING_OGONEK, "ogonek") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_OGONEK);
      addComposition('e', UnicodeUtil.LATIN_SMALL_LETTER_E_WITH_OGONEK);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_OGONEK);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_OGONEK);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_OGONEK);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_OGONEK);
      addComposition('E', UnicodeUtil.LATIN_CAPITAL_LETTER_E_WITH_OGONEK);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_OGONEK);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_OGONEK);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_OGONEK);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_OGONEK_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_OGONEK_AND_MACRON);
    }
  },

  RING_ABOVE(UnicodeUtil.RING_ABOVE, UnicodeUtil.COMBINING_RING_ABOVE, "ring above") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_RING_ABOVE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_RING_ABOVE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_RING_ABOVE);

      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_ACUTE,
          UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE);
      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_ACUTE,
          UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE);
    }

  },

  // STROKE(UnicodeUtil.COMBINING_STROKE) {
  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected void initialize() {
  //
  // addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_ACUTE,
  // UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_STROKE_AND_ACUTE);
  // addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_ACUTE,
  // UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_STROKE_AND_ACUTE);
  // }
  // },

  /** ~ on top. */
  TILDE('~', UnicodeUtil.COMBINING_TILDE, "tilde") {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {

      addComposition('a', UnicodeUtil.LATIN_SMALL_LETTER_A_WITH_TILDE);
      addComposition('i', UnicodeUtil.LATIN_SMALL_LETTER_I_WITH_TILDE);
      addComposition('n', UnicodeUtil.LATIN_SMALL_LETTER_N_WITH_TILDE);
      addComposition('o', UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_TILDE);
      addComposition('u', UnicodeUtil.LATIN_SMALL_LETTER_U_WITH_TILDE);
      addComposition('A', UnicodeUtil.LATIN_CAPITAL_LETTER_A_WITH_TILDE);
      addComposition('I', UnicodeUtil.LATIN_CAPITAL_LETTER_I_WITH_TILDE);
      addComposition('N', UnicodeUtil.LATIN_CAPITAL_LETTER_N_WITH_TILDE);
      addComposition('O', UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_TILDE);
      addComposition('U', UnicodeUtil.LATIN_CAPITAL_LETTER_U_WITH_TILDE);

      addComposition(UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_CAPITAL_LETTER_O_WITH_TILDE_AND_MACRON);
      addComposition(UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_MACRON,
          UnicodeUtil.LATIN_SMALL_LETTER_O_WITH_TILDE_AND_MACRON);
    }

  };

  /* -------------------------------------- */

  /** @see #getSeparateCharacter() */
  private final char separateCharacter;

  /** @see #getCombiningCharacter() */
  private final char combiningCharacter;

  /** @see #getTitle() */
  private final String title;

  /** @see #compose(char) */
  private final Map<Character, Character> composeMap;

  /** @see #decompose(char) */
  private final Map<Character, Character> decomposeMap;

  /** @see #getComposedCharacters() */
  private final Collection<Character> composedCharacters;

  /**
   * The constructor.
   * 
   * @param combiningChar is the {@link #getCombiningCharacter()}.
   */
  private DiacriticalMark(char separateCharacter, char combiningChar, String title) {

    this.separateCharacter = separateCharacter;
    this.combiningCharacter = combiningChar;
    this.title = title;
    this.composeMap = new HashMap<Character, Character>();
    this.decomposeMap = new HashMap<Character, Character>();
    initialize();
    this.composedCharacters = Collections.unmodifiableCollection(this.composeMap.values());
  }

  /**
   * This method is called at construction.
   */
  protected abstract void initialize();

  /**
   * This method adds the given {@link #compose(char) composition} pair.
   * 
   * @param uncomposed is the uncomposed character.
   * @param composed is the composed character.
   */
  protected void addComposition(char uncomposed, char composed) {

    Character u = Character.valueOf(uncomposed);
    Character c = Character.valueOf(composed);
    Character old = this.composeMap.put(u, c);
    if (old != null) {
      throw new DuplicateObjectException(old, u);
    }
    old = this.decomposeMap.put(c, u);
    if (old != null) {
      throw new DuplicateObjectException(old, u);
    }
  }

  /**
   * @return the separateCharacter
   */
  public char getSeparateCharacter() {

    return this.separateCharacter;
  }

  /**
   * This method gets the combining character for this {@link DiacriticalMark}.
   * It represents the mark itself but is TODO. Therefore unicode allows to
   * express '&auml;' as two TODO.
   * 
   * @return the combining character.
   */
  public char getCombiningCharacter() {

    return this.combiningCharacter;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public Character getValue() {

    return Character.valueOf(this.separateCharacter);
  }

  /**
   * This method composes the given <code>character</code> with this
   * {@link DiacriticalMark}.
   * 
   * @param character is the character to compose (e.g. 'a').
   * @return the composed character (e.g. '&auml;' or '&aacute;') or
   *         <code>null</code> if no such composition exists in unicode.
   */
  public Character compose(char character) {

    return this.composeMap.get(Character.valueOf(character));
  }

  /**
   * 
   * @param character
   * @return
   */
  public Character decompose(char character) {

    return this.decomposeMap.get(Character.valueOf(character));
  }

  /**
   * This method gets a {@link Collection} with all precomposed
   * {@link Character characters} containing this mark.
   * 
   * @return the composed characters.
   */
  public Collection<Character> getComposedCharacters() {

    return this.composedCharacters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
