/* $Id$ */
package net.sf.mmm.ui.toolkit.api.window;

import net.sf.mmm.ui.toolkit.api.state.UIReadModalIF;

/**
 * This is the interface for a dialog. A dialog is a window that is not visible
 * in the taskbar. It usually has the top focus of the application and may be
 * modal what means that all other windows of the application are blocked until
 * the dialog is closed. A dialog is created from a frame that will be his 
 * parent. 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIDialogIF extends UIWindowIF, UIReadModalIF {

    /** the type of this object */
    String TYPE = "Dialog";
    
}
