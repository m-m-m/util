/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF;
import net.sf.mmm.ui.toolkit.base.feature.FileAccessUtil;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncFileDialogAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileDownloadIF} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileDownload extends UIWidget implements UIFileDownloadIF {

    /**
     * This inner class implements the listener that handles the button
     * selection.
     */
    private class SelectionListener implements Listener {

        /**
         * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
         * {@inheritDoc}
     */
        public void handleEvent(Event event) {

            UIFileDownload.this.syncDialgAccess.setParentAccess(UIFileDownload.this.syncAccess);
            UIFileDownload.this.syncDialgAccess.setText(getText());
            String file = UIFileDownload.this.syncDialgAccess.open();
            if (file != null) {
                FileAccessUtil.save(UIFileDownload.this.fileAccess, new File(file), getParentWindow());
            }
        }

    }

    /** sync access to the widget used to present the download */
    private final SyncButtonAccess syncAccess;

    /** sync access to the file dialog used to choose the saving destiaiton */
    private final SyncFileDialogAccess syncDialgAccess;

    /** the access to the downloadable data */
    private final FileAccessIF fileAccess;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param access
     *        gives access to the data that is offered for download.
     */
    public UIFileDownload(UIFactory uiFactory, UISwtNode parentObject, FileAccessIF access) {

        super(uiFactory, parentObject);
        this.fileAccess = access;
        this.syncAccess = new SyncButtonAccess(uiFactory, SWT.DEFAULT);
        this.syncAccess.setText("Save");
        this.syncAccess.addListener(SWT.Selection, new SelectionListener());
        this.syncDialgAccess = new SyncFileDialogAccess(uiFactory, SWT.SAVE);
        this.syncDialgAccess.setFilename(this.fileAccess.getFilename());
        // this.fileChooser = new FileDialog(getSwtParentShell(), SWT.SAVE);
        // this.fileChooser.setFileName(this.access.getFilename());
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
