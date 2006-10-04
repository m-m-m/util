/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.model.UITreeModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UITreeIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.TreeModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTreeAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITreeIF} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITree extends UIWidget implements UITreeIF {

    /** the unwrapped swt tree */
    private final SyncTreeAccess syncAccess;

    /** the model adapter */
    private final TreeModelAdapter modelAdapter;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param multiSelection -
     *        if <code>true</code> the user can select multiple, else ony one.
     */
    public UITree(UIFactory uiFactory, UISwtNode parentObject, boolean multiSelection) {

        super(uiFactory, parentObject);
        int style = SWT.BORDER;
        if (multiSelection) {
            style |= SWT.MULTI;
        } else {
            style |= SWT.SINGLE;
        }
        this.syncAccess = new SyncTreeAccess(uiFactory, style);
        this.modelAdapter = new TreeModelAdapter(this.syncAccess);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * 
     */
    @Override
    public SyncTreeAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#create()
     * 
     */
    @Override
    public void create() {
    
        super.create();
        this.modelAdapter.initialize();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getModel()
     * 
     */
    @SuppressWarnings("unchecked")
    public UITreeModelIF getModel() {

        return this.modelAdapter.getModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#setModel(net.sf.mmm.ui.toolkit.api.model.UITreeModelIF)
     * 
     */
    @SuppressWarnings("unchecked")
    public void setModel(UITreeModelIF newModel) {

        this.modelAdapter.setModel(newModel);        
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF#isMultiSelection()
     * 
     */
    public boolean isMultiSelection() {

        return this.syncAccess.hasStyle(SWT.MULTI);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getSelection()
     * 
     */
    public Object getSelection() {

        Object[] selection = this.syncAccess.getSelection();
        if (selection.length > 0) {
            return selection[0];
        }
        return null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITreeIF#getSelections()
     * 
     */
    public Object[] getSelections() {

        return this.syncAccess.getSelection();
    }

}