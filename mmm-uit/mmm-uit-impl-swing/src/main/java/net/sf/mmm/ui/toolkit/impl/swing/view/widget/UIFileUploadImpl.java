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

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.base.feature.UiFileAccessSimple;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIFileUploadImpl extends AbstractUiWidget implements UiFileUpload {

  /**
   * This inner class implements the listener that handles the button selection.
   */
  private class Listener implements ActionListener {

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {

      UIFileUploadImpl.this.fileChooser.setDialogTitle(getValue());
      int selection = UIFileUploadImpl.this.fileChooser
          .showOpenDialog(UIFileUploadImpl.this.button);
      if (selection == JFileChooser.APPROVE_OPTION) {
        File uploadFile = UIFileUploadImpl.this.fileChooser.getSelectedFile();
        UIFileUploadImpl.this.access = new UiFileAccessSimple(uploadFile.getAbsolutePath());
        fireEvent(UiEventType.CLICK);
      }
    }

  }

  /** the access to the uploaded data */
  private UiFileAccess access;

  /** the widget used to present the download */
  private final JButton button;

  /** the file-chooser used to select where to save the download to */
  private final JFileChooser fileChooser;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UIFileUploadImpl(UIFactorySwing uiFactory, UiComposite<? extends UiElement> parentObject) {

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
   * {@inheritDoc}
   */
  public @Override
  JComponent getSwingComponent() {

    return this.button;
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

    return this.button.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.button.setText(text);
  }

}
