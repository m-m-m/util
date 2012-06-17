/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteImage;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface for a label.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiLabel extends UiWidget, UiWriteValue<String>, UiWriteImage {

  /** the type of this object */
  String TYPE = "Label";

}
