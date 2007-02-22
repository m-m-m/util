/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteEditable;
import net.sf.mmm.ui.toolkit.api.state.UIWriteText;

/**
 * This is the interface of a text-field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITextField extends UIWidget, UIWriteText, UIWriteEditable {

  /** the type of this object */
  String TYPE = "Textfield";

}
