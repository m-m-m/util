/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the text of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadText {

  /**
   * This method gets the text of this object.
   * 
   * @return the text of this object.
   */
  String getText();

}
