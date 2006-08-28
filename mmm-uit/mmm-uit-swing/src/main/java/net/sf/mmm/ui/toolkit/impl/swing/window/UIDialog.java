/* $Id: UIDialog.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.window.UIDialogIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBar;

/**
 * This class is the implementation of the UIDialogIF interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDialog extends UIWindow implements UIDialogIF {

    /** the swing dialog */
    private final JDialog dialog;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param jDialog
     *        is the swing dialog to wrap.
     */
    public UIDialog(UIFactory uiFactory, UINodeIF parentObject, JDialog jDialog) {

        super(uiFactory, parentObject);
        this.dialog = jDialog;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.awt.UIWindow#getAwtWindow()
     * {@inheritDoc}
     */
    protected Window getAwtWindow() {

        return this.dialog;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitleIF#getTitle()
     * {@inheritDoc}
     */
    public String getTitle() {

        return this.dialog.getTitle();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitleIF#setTitle(java.lang.String)
     * {@inheritDoc}
     */
    public void setTitle(String newTitle) {

        this.dialog.setTitle(newTitle);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadModalIF#isModal()
     * {@inheritDoc}
     */
    public boolean isModal() {

        return this.dialog.isModal();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow#createMenuBar()
     * {@inheritDoc}
     */
    protected UIMenuBarIF createMenuBar() {

        JMenuBar menuBar = this.dialog.getJMenuBar();
        if (menuBar == null) {
            menuBar = new JMenuBar();
            this.dialog.setJMenuBar(menuBar);
        }
        return new UIMenuBar((UIFactory) getFactory(), this, menuBar);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#setComposite(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * {@inheritDoc}
     */
    public void setComposite(UICompositeIF newComposite) {

        JComponent jComponent = ((UIComponent) newComposite).getSwingComponent();
        this.dialog.setContentPane(jComponent);
        registerComposite(newComposite);
    }

}