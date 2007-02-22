/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the
 * {@link #getBorderTitle() border-title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteBorderTitle extends UIReadBorderTitle {

  /**
   * This method set the border-title of this object.
   * 
   * @param borderTitle -
   *        the title of this object's border.
   */
  void setBorderTitle(String borderTitle);

}
