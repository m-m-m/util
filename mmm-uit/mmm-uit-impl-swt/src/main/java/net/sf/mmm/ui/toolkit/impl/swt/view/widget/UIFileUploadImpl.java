/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.base.feature.UiFileAccessSimple;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncButtonAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncFileDialogAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileUploadImpl extends AbstractUIWidget implements UiFileUpload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class SelectionListener implements Listener {

    /**
     * {@inheritDoc}
     */
    public void handleEvent(Event event) {

      UIFileUploadImpl.this.syncDialgAccess.setParentAccess(UIFileUploadImpl.this.syncAccess);
      UIFileUploadImpl.this.syncDialgAccess.setText(getValue());
      String file = UIFileUploadImpl.this.syncDialgAccess.open();
      if (file != null) {
        UIFileUploadImpl.this.access = new UiFileAccessSimple(file);
        fireEvent(UiEventType.CLICK);
      }
    }

  }

  /** sync access to the widget used to present the download */
  private final SyncButtonAccess syncAccess;

  /** the file-chooser used to select where to save the download to */
  private final SyncFileDialogAccess syncDialgAccess;

  /** the access to the downloadable data */
  private UiFileAccess access;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UIFileUploadImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    this.access = null;
    this.syncAccess = new SyncButtonAccess(uiFactory, SWT.DEFAULT);
    // TODO i18n
    this.syncAccess.setText("Upload");
    this.syncAccess.addListener(SWT.Selection, new SelectionListener());
    this.syncDialgAccess = new SyncFileDialogAccess(uiFactory, SWT.OPEN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public UiFileAccess getSelection() {

    return this.access;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

}
