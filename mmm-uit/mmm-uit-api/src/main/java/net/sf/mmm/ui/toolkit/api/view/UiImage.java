/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view;

import net.sf.mmm.ui.toolkit.api.UiObject;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize;

/**
 * This is the interface for an image in the UI toolkit. Think of it as a
 * regular picture-file (jpg, png, gif, svg, etc.).<br/>
 * A {@link UiImage} is just a representation of the image data and NOT a
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiWidget}. To add it to the UI,
 * use {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel#setImage(UiImage)}.<br/>
 * The {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize
 * preferred-size} is the physical size of the image. The regular
 * {@link net.sf.mmm.ui.toolkit.api.attribute.UiReadSize size} is initialized
 * with the preferred size but may be changed to scale the image.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiImage extends UiObject, UiWriteSize, UiReadPreferredSize {

  /** the type of this object */
  String TYPE = "Picture";

}
