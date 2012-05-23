/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This interface gives read and write access to the {@link #getTitle() title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadStringTitle extends AttributeReadTitle<String> {

  /**
   * This method gets the title of this object. The detailed meaning of the title depends on the type of
   * object. E.g. a window will have the title in the top-level bar.
   * 
   * @return the title.
   */
  String getTitle();

}
