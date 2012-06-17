/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEditable;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface of a text-field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiTextField extends UiWidget, UiWriteValue<String>, AttributeWriteEditable {

  /** the type of this object */
  String TYPE = "Textfield";

}
