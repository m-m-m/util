/* $Id: UIFileUpload.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncFileDialogAccess;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF} interface using SWT
 * as the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileUpload extends UIWidget implements UIFileUploadIF {

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

            UIFileUpload.this.syncDialgAccess.setParentAccess(UIFileUpload.this.syncAccess);
            UIFileUpload.this.syncDialgAccess.setText(getText());
            String file = UIFileUpload.this.syncDialgAccess.open();
            if (file != null) {
                File uploadFile = new File(file);
                UIFileUpload.this.access = new SimpleFileAccess(uploadFile);
                invoke(ActionType.SELECT);
            }
        }

    }

    /** sync access to the widget used to present the download */
    private final SyncButtonAccess syncAccess;

    /** the file-chooser used to select where to save the download to */
    private final SyncFileDialogAccess syncDialgAccess;

    /** the access to the downloadable data */
    private FileAccessIF access;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIFileUpload(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
        this.access = null;
        this.syncAccess = new SyncButtonAccess(uiFactory, SWT.DEFAULT);
        //TODO i18n
        this.syncAccess.setText("Upload");
        this.syncAccess.addListener(SWT.Selection, new SelectionListener());
        this.syncDialgAccess = new SyncFileDialogAccess(uiFactory, SWT.OPEN);
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
     * @see net.sf.mmm.ui.toolkit.api.widget.UIFileUploadIF#getSelection()
     * {@inheritDoc}
     */
    public FileAccessIF getSelection() {

        return this.access;
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
