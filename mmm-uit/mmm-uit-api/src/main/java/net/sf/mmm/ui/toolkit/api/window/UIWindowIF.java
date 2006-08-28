/* $Id: UIWindowIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.window;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF;
import net.sf.mmm.ui.toolkit.api.state.UIWritePositionIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteTitleIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteVisibleIF;

/**
 * This is the abstract interface for a window of the UIFactory.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWindowIF extends UINodeIF, UIWriteDisposedIF, UIWriteSizeIF, UIWriteTitleIF,
        UIWritePositionIF, UIWriteVisibleIF {

    /**
     * This method creates a dialog as child of this window.
     * 
     * @param title
     *        is the title of the dialog.
     * @param modal -
     *        if <code>true</code> all windows of the application are blocked
     *        until the dialog is closed.
     * @param resizeable -
     *        if <code>true</code> the dialog can be resized by the user.
     * @return the created dialog.
     */
    UIDialogIF createDialog(String title, boolean modal, boolean resizeable);

    /**
     * This method shows a message to the user. This is done by opening a dialog
     * containing the message with an "ok" button.
     * 
     * @param message
     *        is the complete and detailed message to show.
     * @param title
     *        is a short title.
     * @param messageType
     *        classifies the type of the message to show (according icon).
     */
    void showMessage(String message, String title, MessageType messageType);

    /**
     * This method shows a message to the user. This is done by opening a dialog
     * containing the message and information about the given throwable with an
     * "ok" button.
     * 
     * @param message
     *        is the complete and detailed message to show.
     * @param title
     *        is a short title.
     * @param messageType
     *        classifies the type of the message to show (according icon).
     * @param throwable
     *        is the cause of the actual message.
     */
    void showMessage(String message, String title, MessageType messageType, Throwable throwable);

    /**
     * This method asks a yes/no question to the user. This is done by opening a
     * dialog with the question with "yes" and "no" buttons.
     * 
     * @param question
     *        is the complete question to aks including all details the user
     *        should know to be able to answer the question easily.
     * @param title
     *        is a short title.
     * @return <code>true</code> if the question is answered with yes,
     *         <code>false</code> otherwise.
     */
    boolean showQuestion(String question, String title);

    /**
     * This method sizes the window according to its preferences.
     */
    void pack();

    /**
     * This method sets the position of the window, so that it is in the center
     * of the screen
     */
    void centerWindow();

    /**
     * This method gets the menu bar of this window.
     * 
     * @return the menu bar. This method will never return <code>null</code>
     *         but create an empty menubar on the first call.
     */
    UIMenuBarIF getMenuBar();

    /**
     * This method gets the composite that is used to display the content of
     * this winodw.
     * 
     * @return the composite content of this window or <code>null</code> if
     *         the composite has not been set.
     */
    UICompositeIF getComposite();

    /**
     * This method sets the composite that is used to display the content of
     * this window. The previous composite of this window will be removed.
     * 
     * @param newComposite
     *        is the new composite for this window.
     */
    void setComposite(UICompositeIF newComposite);

}
