/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.mmm.ui.toolkit.api.UiDisplay;
import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UIScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UISlicePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UISplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UITabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiBorderPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiSimplePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UILabelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIListImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UISlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UISpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UITableImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UITextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UITreeImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.AbstractUiWindowAwt;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UIDialogImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UIFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UIWorkbenchImpl;

/**
 * This class is the implementation of the UIFactory interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UIFactorySwing extends AbstractUiFactory {

  /** the default display */
  private UiDisplayImpl display;

  /**
   * The default constructor. Intended for direct usage without
   * {@link net.sf.mmm.ui.toolkit.api.UiService UIService}.
   * 
   * @param title is the title of this factory. Should be the name of the actual
   *        application creating the UI.
   */
  public UIFactorySwing(String title) {

    super(title);
    GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
    UiDeviceImpl uiDevice = new UiDeviceImpl(defaultDevice);
    this.display = new UiDisplayImpl(this, uiDevice, defaultDevice.getDefaultConfiguration());
  }

  /**
   * The constructor.
   * 
   * @param title is the title of this factory. Should be the name of the actual
   *        application creating the UI.
   * @param uiDevice is the device the display of this factory belongs to.
   * @param graphicConfiguration is the graphics configuration for the display
   *        to represent.
   */
  public UIFactorySwing(String title, UiDeviceImpl uiDevice,
      GraphicsConfiguration graphicConfiguration) {

    super(title);
    this.display = new UiDisplayImpl(this, uiDevice, graphicConfiguration);
  }

  /**
   * {@inheritDoc}
   */
  public UiDisplay getDisplay() {

    return this.display;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UiFactory#getDisplay()
   * 
   * @return the AWT display.
   */
  public GraphicsConfiguration getAwtGc() {

    return this.display.getGraphicsConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame createFrame(String title, boolean resizeable) {

    UIFrameImpl frame = new UIFrameImpl(this, null, title, resizeable);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame createFrame(UiFrame parent, String title, boolean resizable) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showQuestion(UiWindow parent, String question, String title) {

    int result = JOptionPane.showConfirmDialog(((AbstractUiWindowAwt) parent).getNativeWindow(),
        question, title, JOptionPane.YES_NO_OPTION);
    return (result == JOptionPane.YES_OPTION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showMessage(UiWindow parent, String message, String title, MessageType messageType,
      Throwable throwable) {

    int type = JOptionPane.PLAIN_MESSAGE;
    if (messageType == MessageType.ERROR) {
      type = JOptionPane.ERROR_MESSAGE;
    } else if (messageType == MessageType.WARNING) {
      type = JOptionPane.WARNING_MESSAGE;
    } else if (messageType == MessageType.INFO) {
      type = JOptionPane.INFORMATION_MESSAGE;
    }
    String msg = message;
    if (throwable != null) {
      // TODO: temporary hack
      msg = msg + "\n" + throwable.getMessage();
    }
    JOptionPane.showMessageDialog(((AbstractUiWindowAwt) parent).getNativeWindow(), msg, title,
        type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDialog createDialog(UiWindow parent, String title, boolean modal, boolean resizeable) {

    JDialog jDialog = new JDialog(((AbstractUiWindowAwt) parent).getNativeWindow());
    jDialog.setModal(modal);
    jDialog.setResizable(resizeable);
    UIDialogImpl dialog = new UIDialogImpl(this, parent, jDialog);
    dialog.setTitle(title);
    addWindow(dialog);
    return dialog;
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text, ButtonStyle style) {

    UiButton button = new UIButtonImpl(this, style);
    button.setValue(text);
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public <E extends UiElement> UiBorderPanel<E> createBorderPanel(String title) {

    return new UiBorderPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public <E extends UiElement> UiSimplePanel<E> createSimplePanel(Orientation orientation) {

    return new UiSimplePanelImpl(this, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public UiSlicePanel createPanel(Orientation orientation, String text) {

    UISlicePanelImpl panel = new UISlicePanelImpl(this, orientation);
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public UiScrollPanel<UiElement> createScrollPanel(UiElement child) {

    UiScrollPanel<UiElement> scrollPanel = new UIScrollPanelImpl(this);
    scrollPanel.setChild(child);
    return scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiList<E> createList(UiListMvcModel<E> model, boolean multiSelection) {

    UIListImpl<E> list = new UIListImpl<E>(this, null);
    list.setMultiSelection(multiSelection);
    if (model != null) {
      list.setModel(model);
    }
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model, boolean editableFlag) {

    UiComboBox<E> comboBox = new UIComboBoxImpl<E>(this);
    comboBox.setEditable(editableFlag);
    if (model != null) {
      comboBox.setModel(model);
    }
    return comboBox;
  }

  /**
   * {@inheritDoc}
   */
  public <N> UiTree<N> createTree(boolean multiSelection, UiTreeMvcModel<N> model) {

    UITreeImpl<N> tree = new UITreeImpl<N>(this);
    tree.setMultiSelection(multiSelection);
    if (model != null) {
      tree.setModel(model);
    }
    return tree;
  }

  /**
   * {@inheritDoc}
   */
  public <C> UiTable<C> createTable(boolean multiSelection, UiTableMvcModel<C> model) {

    UITableImpl<C> table = new UITableImpl<C>(this);
    // table.setMultiSelection(multiSelection);
    if (model != null) {
      table.setModel(model);
    }
    return table;
  }

  /**
   * {@inheritDoc}
   */
  public UiLabel createLabel(String text) {

    UiLabel label = new UILabelImpl(this);
    label.setValue(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField() {

    UITextFieldImpl textField = new UITextFieldImpl(this);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSpinBox<E> createSpinBox(UiListMvcModel<E> model) {

    return new UISpinBoxImpl<E>(this, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileDownload createFileDownload(UiFileAccess access) {

    return new UIFileDownloadImpl(this, access);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileUpload createFileUpload() {

    return new UIFileUploadImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createImage(UiFileAccess fileAccess) {

    return new UiImageImpl(this, fileAccess);
  }

  /**
   * {@inheritDoc}
   */
  public UiTabPanel createTabbedPanel() {

    return new UITabbedPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public UiSplitPanel createSplitPanel(Orientation orientation) {

    return new UISplitPanelImpl(this, null, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model, Orientation orientation) {

    return new UISlideBarImpl<E>(this, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, orientation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWorkbench createWorkbench(String title) {

    UIWorkbenchImpl workbench = new UIWorkbenchImpl(this, title, true);
    addWindow(workbench);
    return workbench;
  }

  /**
   * {@inheritDoc}
   */
  public UiAction createPrintUiAction(UiElement component, String actionName, String jobName) {

    return new PrintAction((AbstractUiElement) component, actionName, jobName);
  }

  /**
   * {@inheritDoc}
   */
  public UiDateBox createDateEditor() {

    return new UIDateEditorImpl(this);
  }

}
