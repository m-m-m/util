/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteIcon;
import net.sf.mmm.ui.toolkit.api.state.UIWriteText;

/**
 * This is the interface for a label.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UILabel extends UIWidget, UIWriteText, UIWriteIcon {

  /** the type of this object */
  String TYPE = "Label";

}
