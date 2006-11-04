/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to an integer
 * {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteIntegerValue extends UIReadIntegerValue {

  /**
   * This method sets the current value to the given one.
   * 
   * @param newValue
   *        is the new value to set.
   */
  void setValue(int newValue);

}
