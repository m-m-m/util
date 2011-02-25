/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import net.sf.mmm.ui.toolkit.api.UiDisplay;
import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileDownload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiFileUpload;
import net.sf.mmm.ui.toolkit.api.view.widget.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.impl.awt.UiDeviceImpl;
import net.sf.mmm.ui.toolkit.impl.awt.UiDisplayImpl;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UIScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UISlicePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UISplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.composite.UITabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.UIComboBoxImpl;
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
import net.sf.mmm.ui.toolkit.impl.swing.view.widget.editor.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UIFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.window.UIWorkbenchImpl;

/**
 * This class is the implementation of the UIFactory interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
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
  public UiButton createButton(String text, UiImage icon, ButtonStyle style) {

    UiButton button = new UIButtonImpl(this, null, style);
    button.setValue(text);
    if (icon != null) {
      button.setImage(icon);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public UiSlicePanel createPanel(Orientation orientation, String text) {

    UISlicePanelImpl panel = new UISlicePanelImpl(this, null, orientation);
    panel.setBorderTitle(text);
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public UiScrollPanel<UiElement> createScrollPanel(UiElement child) {

    UiScrollPanel<UiElement> scrollPanel = new UIScrollPanelImpl(this, null);
    scrollPanel.setComponent(child);
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

    UiComboBox<E> comboBox = new UIComboBoxImpl<E>(this, null);
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

    UITreeImpl<N> tree = new UITreeImpl<N>(this, null);
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

    UITableImpl<C> table = new UITableImpl<C>(this, null);
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

    UiLabel label = new UILabelImpl(this, null);
    label.setValue(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField(boolean editable) {

    UITextFieldImpl textField = new UITextFieldImpl(this, null);
    textField.setEditable(editable);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSpinBox<E> createSpinBox(UiListMvcModel<E> model) {

    return new UISpinBoxImpl<E>(this, null, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileDownload createFileDownload(UiFileAccess access) {

    return new UIFileDownloadImpl(this, null, access);
  }

  /**
   * {@inheritDoc}
   */
  public UiFileUpload createFileUpload() {

    return new UIFileUploadImpl(this, null);
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

    return new UITabbedPanelImpl(this, null);
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

    return new UISlideBarImpl<E>(this, null, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, null, orientation);
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

    return new UIDateEditorImpl(this, null);
  }

}
