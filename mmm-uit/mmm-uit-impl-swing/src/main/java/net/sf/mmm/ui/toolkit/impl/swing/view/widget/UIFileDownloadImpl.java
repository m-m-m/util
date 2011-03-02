/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIFileDownloadImpl extends AbstractUiWidget implements UiFileDownload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {

      UIFileDownloadImpl.this.fileChooser.setDialogTitle(getValue());
      int selection = UIFileDownloadImpl.this.fileChooser
          .showSaveDialog(UIFileDownloadImpl.this.button);
      if (selection == JFileChooser.APPROVE_OPTION) {
        // TODO

        // FileAccessUtil.save(UIFileDownloadImpl.this.access,
        // UIFileDownloadImpl.this.fileChooser.getSelectedFile(),
        // getParentWindow());
      }
    }

  }

  /** the access to the downloadable data */
  private final UiFileAccess access;

  /** the widget used to present the download */
  private final JButton button;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param uiFileAccess gives access to the data that is offered for download.
   */
  public UIFileDownloadImpl(UIFactorySwing uiFactory, UiFileAccess uiFileAccess) {

    super(uiFactory);
    this.access = uiFileAccess;
    this.fileChooser = new JFileChooser();
    this.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    // this.fileChooser.setMultiSelectionEnabled(false);
    this.fileChooser.setSelectedFile(new File(this.access.getUrl()));
    // TODO: i18n
    this.button = new JButton("Save");
    // TODO: should only be filename
    this.button.setToolTipText("Save " + this.access.getUrl());
    this.button.addActionListener(new Listener());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.button.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.button.getText();
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

}
