/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiFileDownloadImpl extends AbstractUiWidgetSwing<JButton> implements UiFileDownload {

  /** the access to the download-able data */
  private final UiFileAccess access;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param uiFileAccess gives access to the data that is offered for download.
   */
  public UiFileDownloadImpl(UiFactorySwing uiFactory, UiFileAccess uiFileAccess) {

    // TODO: i18n
    super(uiFactory, new JButton("Save"));
    this.access = uiFileAccess;
    this.fileChooser = new JFileChooser();
    this.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    // this.fileChooser.setMultiSelectionEnabled(false);
    this.fileChooser.setSelectedFile(new File(this.access.getUrl()));
    // TODO: should only be filename
    JButton button = getAdapter().getDelegate();
    button.setToolTipText("Save " + this.access.getUrl());
    button.addActionListener(new Listener());
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getAdapter().getDelegate().setText(text);
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
  public String getType() {

    return TYPE;
  }

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {

      UiFileDownloadImpl.this.fileChooser.setDialogTitle(getValue());
      int selection = UiFileDownloadImpl.this.fileChooser
          .showSaveDialog(getAdapter().getDelegate());
      if (selection == JFileChooser.APPROVE_OPTION) {
        // TODO

        // FileAccessUtil.save(UIFileDownloadImpl.this.access,
        // UIFileDownloadImpl.this.fileChooser.getSelectedFile(),
        // getParentWindow());
      }
    }

  }

}
