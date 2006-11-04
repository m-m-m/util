/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to an integer {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadIntegerValue {

  /**
   * This method gets the current value.
   * 
   * @return the current value.
   */
  int getValue();

}
