/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF;

/**
 * This is the interface of a text-field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITextFieldIF extends UIWidgetIF, UIWriteTextIF {

    /** the type of this object */
    String TYPE = "Textfield";

}