/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

/**
 * This interface contains arbitrary string for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TestValues {

  /**
   * Thai sentence: "The language barrier is a major problem for today's global communication."
   */
  String THAI_SENTENCE = "เด็กที่มีปัญหาทางการเรียนรู้่บางคนสามารถเรียนร่วมกับเด็กปกติได้";

  /** The date 2000-01-01 in milliseconds. */
  long DATE_2000_01_01 = 946681200000L;

  /** The time 23:59:59 in milliseconds. */
  long TIME_23_59_59 = ((23 * 60) + 59) * 60 + 59 * 1000L;

}
