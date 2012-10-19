/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.Accessibility;

/**
 * This interface gives read access to the {@link #getAltText() alternative text} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAltText extends Accessibility {

  /**
   * This method gets the <em>alternative text</em> of this object. This text is used to provide the essential
   * information of an object in textual form. E.g. if an image can NOT be displayed because it is missing,
   * the alternative text is displayed instead. Further, accessibility tools such as screen-readers can use
   * this information to help the user in order to better understand the meaning of an object.
   * 
   * @return the alternative text or <code>null</code> if NOT set.
   */
  String getAltText();

}
