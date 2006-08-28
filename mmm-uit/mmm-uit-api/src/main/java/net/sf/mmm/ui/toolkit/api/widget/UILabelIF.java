/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteIconIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF;


/**
 * This is the interface for a label.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UILabelIF extends UIWidgetIF, UIWriteTextIF, UIWriteIconIF {

    /** the type of this object */
    String TYPE = "Label";
    
}
