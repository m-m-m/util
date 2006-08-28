/* $Id: UIWriteEditableIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object} of the UI framework that
 * can be editable or uneditable. Editable means that the end-user can edit the
 * data of the object (e.g. the text of a text-input field). 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteEditableIF {

    /**
     * This method gets the editable status.
     * 
     * @return <code>true</code> if this object is editable.
     */
    boolean isEditable();

    /**
     * This method set the editable status.
     * 
     * @param editableFlag
     *        if <code>true</code> the object will become editable, if
     *        <code>false</code> the object will become uneditable.
     */
    void setEditable(boolean editableFlag);

}
