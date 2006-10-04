/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIDialogIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the UIWindowIF interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWindow extends net.sf.mmm.ui.toolkit.impl.awt.UIWindow {

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param parentObject
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UINodeIF#getParent() parent} that
     *        created this object. It may be <code>null</code>.
     */
    public UIWindow(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#showMessage(java.lang.String,
     *      java.lang.String, net.sf.mmm.ui.toolkit.api.window.MessageType)
     * 
     */
    public void showMessage(String message, String title, MessageType messageType) {

        int type = JOptionPane.PLAIN_MESSAGE;
        if (messageType == MessageType.ERROR) {
            type = JOptionPane.ERROR_MESSAGE;
        } else if (messageType == MessageType.WARNING) {
            type = JOptionPane.WARNING_MESSAGE;
        } else if (messageType == MessageType.INFO) {
            type = JOptionPane.INFORMATION_MESSAGE;
        }
        JOptionPane.showMessageDialog(getAwtWindow(), message, title, type);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#showQuestion(java.lang.String,
     *      java.lang.String)
     * 
     */
    public boolean showQuestion(String question, String title) {

        int result = JOptionPane.showConfirmDialog(getAwtWindow(), question, title,
                JOptionPane.YES_NO_OPTION);
        return (result == JOptionPane.YES_OPTION);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#createDialog(java.lang.String,
     *      boolean, boolean)
     * 
     */
    public UIDialogIF createDialog(String title, boolean modal, boolean resizeable) {

        JDialog jDialog = null;
        if (getType() == UIFrameIF.TYPE) {
            jDialog = new JDialog((Frame) getAwtWindow());
        } else {
            jDialog = new JDialog((Dialog) getAwtWindow());
        }
        jDialog.setModal(modal);
        jDialog.setResizable(resizeable);
        UIDialog dialog = new UIDialog((UIFactory) getFactory(), this, jDialog);
        dialog.setTitle(title);
        return dialog;
    }

}