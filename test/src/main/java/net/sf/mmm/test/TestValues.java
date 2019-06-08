/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import java.util.Locale;

/**
 * This interface contains arbitrary values for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TestValues {

  /** Spanish {@link Locale}. */
  Locale SPANISH = new Locale("es");

  /**
   * Thai sentence: "The language barrier is a major problem for today's global communication."
   */
  String THAI_SENTENCE = "เด็กที่มีปัญหาทางการเรียนรู้่บางคนสามารถเรียนร่วมกับเด็กปกติได้";

  /** Exotic letters each from a different block. */
  String UNICODE_LETTERS = "\u0100\u0180\u02B0\u039E\u0400\u0500\u0531\u05D0\u0710\u0750\u0780\u0990\u0A10\u0A90\u0B10\u0B90\u0C10\u0C90\u0D10\u0D90\u0E10\u0E81\u0F00\u1000\u10A0\u1100\u1200\u1380\u13A0\u1410"
      + "\u16A0\u1700\u1720\u1740\u1760\u1780\u1820\u18B0\u1900\u1950\u1980\u1A00\u1A20\u1B10\u1B90\u1BC0\u1C00\u1C60\u1CF0\u1D00\u1D80\u1E00\u1F00";

  /** Various digits each from a different block. */
  String UNICODE_DIGITS = "6\u096C\u0CEC\u1C50"; // + "\u2165\u0BF0\uD835\uDFE8\u2160";

  /** Exotic symbols from different blocks. */
  String UNICODE_NON_ALPHANUMERIC_SYMBOLS = "\u19E0\u1AB0\u1CC0\u1CD0\u1DC0\u2023\u20A0\u20D0\u2100\u2190\u2200\u2300";

  /** The date 2000-01-01 in milliseconds. */
  long DATE_2000_01_01 = 946681200000L;

  /** The time 23:59:59 in milliseconds. */
  long TIME_23_59_59 = ((23 * 60) + 59) * 60 + 59 * 1000L;

}
