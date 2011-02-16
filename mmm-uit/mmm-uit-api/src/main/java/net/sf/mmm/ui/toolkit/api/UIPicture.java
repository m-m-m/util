/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize;

/**
 * This is the interface for a picture in the UI toolkit. Think of it as a
 * regular picture-file (jpeg, png, gif).<br>
 * The
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize preferred-size} is
 * the physical size of the image. The regular
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadSize size} is initialized with
 * the preferred size but may be changed to scale the image.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIPicture extends UIObject, UiWriteSize, UiReadPreferredSize {

  /** the type of this object */
  String TYPE = "Picture";

}
