/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the
 * {@link #getBorderTitle() border-title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadBorderTitle {

  /**
   * This method gets the title of the objects border.
   * 
   * @return the border title or <code>null</code> if the object has no
   *         titled border.
   */
  String getBorderTitle();

}
