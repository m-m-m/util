/* $Id: SyncTableAccess.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Table}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTableAccess extends AbstractSyncControlAccess {

    /**
     * operation to get the
     * {@link org.eclipse.swt.widgets.Table#getSelection() selection} of the
     * table.
     */
    private static final String OPERATION_GET_SELECTION = "getSelection";

    /**
     * operation to
     * {@link org.eclipse.swt.widgets.Table#removeAll() "remove all"} items of
     * the table.
     */
    private static final String OPERATION_REMOVE_ALL = "removeAll";

    /** the table to access */
    private Table table;

    /** the selection value */
    private int selection;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the table.
     */
    public SyncTableAccess(UIFactory uiFactory, int swtStyle) {

        super(uiFactory, swtStyle);
        this.table = null;
        this.selection = 0;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#getSwtObject()
     * {@inheritDoc}
     */
    @Override
    public Table getSwtObject() {

        return this.table;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#performSynchron(String)
     * {@inheritDoc}
     */
    @Override
    protected void performSynchron(String operation) {

        if (operation == OPERATION_GET_SELECTION) {
            this.selection = this.table.getSelectionIndex();
        } else if (operation == OPERATION_REMOVE_ALL) {
            this.table.removeAll();
        } else {
            super.performSynchron(operation);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncWidgetAccess#createSynchron()
     * {@inheritDoc}
     */
    @Override
    protected void createSynchron() {

        this.table = new Table(getParent(), getStyle());
        this.table.setHeaderVisible(true);
        this.table.setLinesVisible(true);
        super.createSynchron();
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.Table#getSelection() selection} of the
     * table.
     * 
     * @return the selection.
     */
    public int getSelection() {

        assert (checkReady());
        invoke(OPERATION_GET_SELECTION);
        return this.selection;
    }

    /**
     * This method
     * {@link org.eclipse.swt.widgets.Table#removeAll() "removes all"} items
     * from the table.
     */
    public void removeAll() {

        assert (checkReady());
        invoke(OPERATION_REMOVE_ALL);
    }

}