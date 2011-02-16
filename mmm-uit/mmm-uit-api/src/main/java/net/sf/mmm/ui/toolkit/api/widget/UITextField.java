/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteEditable;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteText;

/**
 * This is the interface of a text-field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITextField extends UIWidget, UiWriteText, UiWriteEditable {

  /** the type of this object */
  String TYPE = "Textfield";

}
