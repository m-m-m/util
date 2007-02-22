/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.base.feature.FileAccessUtil;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncFileDialogAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileDownload} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileDownloadImpl extends AbstractUIWidget implements UIFileDownload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class SelectionListener implements Listener {

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {

      UIFileDownloadImpl.this.syncDialgAccess.setParentAccess(UIFileDownloadImpl.this.syncAccess);
      UIFileDownloadImpl.this.syncDialgAccess.setText(getText());
      String file = UIFileDownloadImpl.this.syncDialgAccess.open();
      if (file != null) {
        FileAccessUtil.save(UIFileDownloadImpl.this.fileAccess, new File(file), getParentWindow());
      }
    }

  }

  /** sync access to the widget used to present the download */
  private final SyncButtonAccess syncAccess;

  /** sync access to the file dialog used to choose the saving destiaiton */
  private final SyncFileDialogAccess syncDialgAccess;

  /** the access to the downloadable data */
  private final FileAccess fileAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param access
   *        gives access to the data that is offered for download.
   */
  public UIFileDownloadImpl(UIFactorySwt uiFactory, UISwtNode parentObject, FileAccess access) {

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
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.syncAccess.getText();
  }

}
