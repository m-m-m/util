/* $Id: UIFileUploadImpl.java 976 2011-03-02 21:36:59Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.base.feature.UiFileAccessSimple;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiFileUploadImpl extends AbstractUiWidgetSwing<JButton> implements UiFileUpload {

  /** the access to the uploaded data */
  private UiFileAccess access;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiFileUploadImpl(UiFactorySwing uiFactory) {

    // TODO: i18n
    super(uiFactory, new JButton("Upload"));
    this.access = null;
    this.fileChooser = new JFileChooser();
    this.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    // this.button.setToolTipText("Upload " + this.access.getFilename());
    getAdapter().getDelegate().addActionListener(new Listener());
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
  public String getValue() {

    return getAdapter().getDelegate().getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getAdapter().getDelegate().setText(text);
  }

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {

      UiFileUploadImpl.this.fileChooser.setDialogTitle(getValue());
      int selection = UiFileUploadImpl.this.fileChooser.showOpenDialog(getAdapter().getDelegate());
      if (selection == JFileChooser.APPROVE_OPTION) {
        File uploadFile = UiFileUploadImpl.this.fileChooser.getSelectedFile();
        UiFileUploadImpl.this.access = new UiFileAccessSimple(uploadFile.getAbsolutePath());
        sendEvent(UiEventType.CLICK);
      }
    }

  }

}
