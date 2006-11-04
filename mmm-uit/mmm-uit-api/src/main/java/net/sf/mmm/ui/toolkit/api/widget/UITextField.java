/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteText;

/**
 * This is the interface of a text-field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITextField extends UIWidget, UIWriteText {

  /** the type of this object */
  String TYPE = "Textfield";

}
