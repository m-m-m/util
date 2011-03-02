/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getTitle() title}
 * of an {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadTitle {

  /**
   * This method gets the title of this object. The detailed meaning of the
   * title depends on the type of object. E.g. a
   * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWindow} will have the title
   * in the top-level bar, while a
   * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel} will have
   * the title on the top of the panel within the borderline.
   * 
   * @return the title.
   */
  String getTitle();

}
