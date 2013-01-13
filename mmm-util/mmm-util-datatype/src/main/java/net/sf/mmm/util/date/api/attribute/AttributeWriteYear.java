/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

/**
 * This interface gives read and write access to the {@link #getYear() year} of an object related to date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeWriteYear extends AttributeReadYear {

  /**
   * This method sets the {@link #getYear() year}.
   * 
   * @param year is the new value of {@link #getYear()}.
   */
  void setYear(int year);

}
