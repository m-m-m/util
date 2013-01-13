/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

/**
 * This interface gives read and write access to the {@link #getDay() day} of an object related to date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeWriteDay extends AttributeReadDay {

  /**
   * This method sets the {@link #getDay() day}.
   * 
   * @param day is the new value of {@link #getDay()}.
   */
  void setDay(int day);

}
