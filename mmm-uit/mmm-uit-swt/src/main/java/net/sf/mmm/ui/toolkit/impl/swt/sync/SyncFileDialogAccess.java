/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.FileDialog}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncFileDialogAccess extends AbstractSyncDialogAccess {

    /**
     * operation to {@link org.eclipse.swt.widgets.FileDialog#open() open} the
     * dialog.
     */
    private static final String OPERATION_OPEN = "open";

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.FileDialog#setFileName(java.lang.String) filename}
     * of the dialog.
     */
    private static final String OPERATION_SET_FILENAME = "setFileName";

    /**
     * operation to get the
     * {@link org.eclipse.swt.widgets.FileDialog#getFileName() filename} of the
     * dialog.
     */
    private static final String OPERATION_GET_FILENAME = "getFileName";

    /** the actual SWT object to access */
    private FileDialog fileDialog;

    /** the name of the selected file */
    private String filename;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the widget.
     */
    public SyncFileDialogAccess(UIFactory uiFactory, int swtStyle) {

        super(uiFactory, swtStyle);
        this.fileDialog = null;
        this.filename = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#createSynchron()
     * {@inheritDoc}
     */
    @Override
    protected void createSynchron() {

        this.fileDialog = new FileDialog(getParent(), getStyle());
        super.createSynchron();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#performSynchron(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void performSynchron(String operation) {

        if (operation == OPERATION_OPEN) {
            this.filename = this.fileDialog.open();
        } else if (operation == OPERATION_SET_FILENAME) {
            this.fileDialog.setFileName(this.filename);
        } else if (operation == OPERATION_SET_FILENAME) {
            this.filename = this.fileDialog.getFileName();
        } else {
            super.performSynchron(operation);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncDialogAccess#getSwtObject()
     * {@inheritDoc}
     */
    @Override
    public FileDialog getSwtObject() {

        return this.fileDialog;
    }

    /**
     * This method opens the dialog.
     * 
     * @return the name of the selected file or <code>null</code> if no file
     *         was choosen (e.g. dialog was canceled).
     */
    public String open() {

        assert (checkReady());
        invoke(OPERATION_OPEN);
        return this.filename;
    }

    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.FileDialog#setFileName(java.lang.String) filename}
     * of the dialog.
     * 
     * @param name
     *        is the name of the selected file.
     */
    public void setFilename(String name) {

        assert (checkReady());
        this.filename = name;
        invoke(OPERATION_SET_FILENAME);
    }

    /**
     * This method gets the
     * {@link org.eclipse.swt.widgets.FileDialog#getFileName() filename} of the
     * dialog.
     * 
     * @return the name of the selected file.
     */
    public String getFilename() {

        assert (checkReady());
        invoke(OPERATION_GET_FILENAME);
        return this.filename;
    }

}
