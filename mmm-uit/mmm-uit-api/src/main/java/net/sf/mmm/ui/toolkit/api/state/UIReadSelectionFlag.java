/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the selection flag of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSelectionFlag {

  /**
   * This method determines if the object is selected.
   * 
   * @return <code>true</code> if this object is selected.
   */
  boolean isSelected();

}
