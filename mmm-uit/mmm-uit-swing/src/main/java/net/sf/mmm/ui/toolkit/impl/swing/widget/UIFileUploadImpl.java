/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.base.feature.SimpleFileAccess;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIFileUpload} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFileUploadImpl extends AbstractUIWidget implements UIFileUpload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

      UIFileUploadImpl.this.fileChooser.setDialogTitle(getText());
      int selection = UIFileUploadImpl.this.fileChooser.showOpenDialog(UIFileUploadImpl.this.button);
      if (selection == JFileChooser.APPROVE_OPTION) {
        File uploadFile = UIFileUploadImpl.this.fileChooser.getSelectedFile();
        UIFileUploadImpl.this.access = new SimpleFileAccess(uploadFile);
        invoke(ActionType.SELECT);
      }
    }

  }

  /** the access to the uploaded data */
  private FileAccess access;

  /** the widget used to present the download */
  private final JButton button;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UIFileUploadImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.access = null;
    this.fileChooser = new JFileChooser();
    this.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    // TODO: i18n
    this.button = new JButton("Upload");
    // this.button.setToolTipText("Upload " + this.access.getFilename());
    this.button.addActionListener(new Listener());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.button;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIFileUpload#getSelection()
   */
  public FileAccess getSelection() {

    return this.access;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.button.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.button.setText(text);
  }

}
