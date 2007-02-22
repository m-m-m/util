/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the text of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteText extends UIReadText {

  /**
   * This method sets the text of this object.
   * 
   * @param text
   *        is the new text for this object.
   */
  void setText(String text);

}
