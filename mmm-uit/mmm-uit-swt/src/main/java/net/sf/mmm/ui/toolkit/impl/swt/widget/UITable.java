/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UITableModelIF;
import net.sf.mmm.ui.toolkit.api.widget.UITableIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.TableModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTableAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITableIF} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITable extends UIWidget implements UITableIF {

    /** the sync access to the native SWT table */
    private final SyncTableAccess syncAccess;
    
    /** the model adapter */
    private final TableModelAdapter modelAdapter;
    
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
    public UITable(UIFactory uiFactory, UISwtNode parentObject, boolean multiSelection) {

        super(uiFactory, parentObject);
        int style;
        if (multiSelection) {
            style = SWT.MULTI | SWT.FULL_SELECTION;
        } else {
            style = SWT.SINGLE;
        }
        style |= SWT.VIRTUAL;
        this.syncAccess = new SyncTableAccess(uiFactory, style);
        this.modelAdapter = new TableModelAdapter(this.syncAccess);
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncTableAccess getSyncAccess() {
    
        return this.syncAccess;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#create()
     * {@inheritDoc}
     */
    @Override
    public void create() {
    
        super.create();
        this.modelAdapter.initialize();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITableIF#getModel()
     * {@inheritDoc}
     */
    public UITableModelIF getModel() {

        return this.modelAdapter.getModel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UITableIF#setModel(net.sf.mmm.ui.toolkit.api.model.UITableModelIF)
     * {@inheritDoc}
     */
    public void setModel(UITableModelIF newModel) {

        this.modelAdapter.setModel(newModel);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

}
