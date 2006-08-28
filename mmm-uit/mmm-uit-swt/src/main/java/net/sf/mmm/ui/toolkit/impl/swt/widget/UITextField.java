/* $Id: UITextField.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.widget.UITextFieldIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTextAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITextFieldIF} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITextField extends UIWidget implements UITextFieldIF {

    /** the synchron access to the text-field */
    private final SyncTextAccess syncAccess;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UITextField(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
        int style = SWT.DEFAULT;
        this.syncAccess = new SyncTextAccess(uiFactory, style);
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public AbstractSyncControlAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.syncAccess.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
    }

}
