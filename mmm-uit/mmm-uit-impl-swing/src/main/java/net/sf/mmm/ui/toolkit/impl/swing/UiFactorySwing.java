/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.mmm.ui.toolkit.api.UiDisplay;
import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiGridRow;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
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
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiBorderPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiGridPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiSimplePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiSplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UiTabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiLabelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiListImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiSlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiSpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiTableImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiTextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UiTreeImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.AbstractUiWindowSwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UiDialogImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UiFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UiInternalFrame;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UiWorkbenchImpl;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This class is the implementation of the UIFactory interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named("swing")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UiFactorySwing extends AbstractUiFactory {

  /** the default display */
  private UiDisplayImpl display;

  /**
   * The default constructor. Intended for direct usage without
   * {@link net.sf.mmm.ui.toolkit.api.UiService UIService}.
   * 
   * @param title is the title of this factory. Should be the name of the actual
   *        application creating the UI.
   */
  public UiFactorySwing(String title) {

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
  public UiFactorySwing(String title, UiDeviceImpl uiDevice,
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

    UiFrameImpl frame = new UiFrameImpl(this, null, title, resizeable);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFrame createFrame(UiFrame parent, String title, boolean resizable) {

    UiFrameImpl frame = new UiFrameImpl(this, (UiFrameImpl) parent, title, resizable);
    addWindow(frame);
    return frame;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showQuestion(UiWindow parent, String question, String title) {

    int result = JOptionPane.showConfirmDialog(getNativeWindow(parent), question, title,
        JOptionPane.YES_NO_OPTION);
    return (result == JOptionPane.YES_OPTION);
  }

  /**
   * This method gets the native {@link Window} for the given {@link UiWindow}.
   * 
   * @param window is the {@link UiWindow}.
   * @return the native {@link Window}.
   */
  protected static Window getNativeWindow(UiWindow window) {

    if (window instanceof UiInternalFrame) {
      UiWindow parentWindow = window.getParentWindow();
      if (parentWindow == null) {
        return null;
      }
      return getNativeWindow(parentWindow);
    }
    return ((AbstractUiWindowSwing<Window>) window).getAdapter().getDelegate();
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
    JOptionPane.showMessageDialog(getNativeWindow(parent), msg, title, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDialog createDialog(UiWindow parent, String title, boolean modal, boolean resizeable) {

    JDialog jDialog = new JDialog(getNativeWindow(parent));
    jDialog.setModal(modal);
    jDialog.setResizable(resizeable);
    UiDialogImpl dialog = new UiDialogImpl(this, parent, jDialog);
    dialog.setTitle(title);
    addWindow(dialog);
    return dialog;
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text, ButtonStyle style) {

    UiButton button = new UiButtonImpl(this, style);
    button.setValue(text);
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiBorderPanel<CHILD> createBorderPanel(String title) {

    return new UiBorderPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiSimplePanel<CHILD> createSimplePanel(Orientation orientation) {

    return new UiSimplePanelImpl(this, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public UiGridPanel<? extends UiGridRow<? extends UiElement>> createGridPanel() {

    return new UiGridPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiScrollPanel<CHILD> createScrollPanel(CHILD child) {

    UiScrollPanel<CHILD> scrollPanel = new UiScrollPanelImpl(this);
    scrollPanel.setChild(child);
    return scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiList<E> createList(UiListMvcModel<E> model, boolean multiSelection) {

    UiListImpl<E> list = new UiListImpl<E>(this, null);
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

    UiComboBox<E> comboBox = new UiComboBoxImpl<E>(this);
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

    UiTreeImpl<N> tree = new UiTreeImpl<N>(this);
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

    UiTableImpl<C> table = new UiTableImpl<C>(this);
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

    UiLabel label = new UiLabelImpl(this);
    label.setValue(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField() {

    UiTextFieldImpl textField = new UiTextFieldImpl(this);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSpinBox<E> createSpinBox(UiListMvcModel<E> model) {

    return new UiSpinBoxImpl<E>(this, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileDownload createFileDownload(UiFileAccess access) {

    return new UiFileDownloadImpl(this, access);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileUpload createFileUpload() {

    return new UiFileUploadImpl(this);
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
  public <CHILD extends UiElement> UiTabPanel<CHILD> createTabbedPanel() {

    return new UiTabbedPanelImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public <CHILD extends UiElement> UiSplitPanel<CHILD> createSplitPanel(Orientation orientation) {

    return new UiSplitPanelImpl(this, orientation);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model, Orientation orientation) {

    return new UiSlideBarImpl<E>(this, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar(Orientation orientation) {

    return new UiProgressBarImpl(this, orientation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWorkbench createWorkbench(String title) {

    UiWorkbenchImpl workbench = new UiWorkbenchImpl(this, title, true);
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

    return new UiDateEditorImpl(this);
  }

}
