/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #isTitleVisible() titleVisible} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadTitleVisible extends Accessibility {

  /**
   * This method determines if the {@link AttributeReadStringTitle#getTitle() title} of this object is
   * {@link AttributeReadVisible#isVisible() visible}. For {@link Accessibility} reasons you should always
   * {@link AttributeWriteStringTitle#setTitle(String) set the title} of a titled object. However in specific
   * cases you want to save space on the screen and may not require the title to be displayed for seeing
   * users. Then you can hide the title while it is still recognized by assistive technology such as screen
   * readers.
   * 
   * @return <code>true</code> if visible, <code>false</code> if hidden.
   */
  boolean isTitleVisible();

}
