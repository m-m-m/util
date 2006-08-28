/* $Id: UILabelIF.java 191 2006-07-24 21:00:49Z hohwille $ */
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
