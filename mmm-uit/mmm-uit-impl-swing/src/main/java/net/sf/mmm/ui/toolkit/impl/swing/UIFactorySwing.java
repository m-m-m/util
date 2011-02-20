/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UIDisplay;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.types.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.composite.UiDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabbedPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
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
import net.sf.mmm.ui.toolkit.api.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUIFactory;
import net.sf.mmm.ui.toolkit.impl.awt.UIDeviceImpl;
import net.sf.mmm.ui.toolkit.impl.awt.UIDisplayImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIDecoratedComponentImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UISlicePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UISplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UITabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UILabelImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIListImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UIProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UISpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITableImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.UITreeImpl;
import net.sf.mmm.ui.toolkit.impl.swing.widget.editor.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swing.window.UIWorkbenchImpl;

/**
 * This class is the implementation of the UIFactory interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIFactorySwing extends AbstractUIFactory {

  /** the default display */
  private UIDisplayImpl display;

  /**
   * The dummy constructor.
   * 
   * This constructor may be used for testing if an instance is required for the
   * default display without using the
   * {@link net.sf.mmm.ui.toolkit.api.UiService UIService}.
   */
  public UIFactorySwing() {

    super();
    GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
    UIDeviceImpl uiDevice = new UIDeviceImpl(defaultDevice);
    this.display = new UIDisplayImpl(this, uiDevice, defaultDevice.getDefaultConfiguration());
  }

  /**
   * The constructor.
   * 
   * @param uiDevice is the device the display of this factory belongs to.
   * @param graphicConfiguration is the graphics configuration for the display
   *        to represent.
   */
  public UIFactorySwing(UIDeviceImpl uiDevice, GraphicsConfiguration graphicConfiguration) {

    super();
    this.display = new UIDisplayImpl(this, uiDevice, graphicConfiguration);
  }

  /**
   * {@inheritDoc}
   */
  public UIDisplay getDisplay() {

    return this.display;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactoryRenamed#getDisplay()
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
  public <D extends UiElement, C extends UiElement> UiDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component) {

    UiDecoratedComponent<D, C> decoratedComponent = new UIDecoratedComponentImpl<D, C>(this, null);
    decoratedComponent.setDecorator(decorator);
    decoratedComponent.setComponent(component);
    return decoratedComponent;
  }

  /**
   * {@inheritDoc}
   */
  public UiScrollPanel createScrollPanel(UiComposite child) {

    UiScrollPanel scrollPanel = new UIScrollPanelImpl(this, null);
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
  public UiImage createPicture(URL imageUrl) {

    return new UIPictureImpl(this, imageUrl);
  }

  /**
   * {@inheritDoc}
   */
  public UiTabbedPanel createTabbedPanel() {

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
  public UiWorkbench createWorkbench(String title) {

    UIWorkbenchImpl workbench = new UIWorkbenchImpl(this, title, true);
    addWindow(workbench);
    return workbench;
  }

  /**
   * {@inheritDoc}
   */
  public UiAction createPrintUiAction(UiElement component, String actionName, String jobName) {

    return new PrintAction((AbstractUIComponent) component, actionName, jobName);
  }

  /**
   * {@inheritDoc}
   */
  public UiDateBox createDateEditor() {

    return new UIDateEditorImpl(this, null);
  }

}
