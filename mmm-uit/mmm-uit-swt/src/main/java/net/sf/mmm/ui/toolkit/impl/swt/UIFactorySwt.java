/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.feature.FileAccess;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UIFileDownload;
import net.sf.mmm.ui.toolkit.api.widget.UIFileUpload;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWorkbench;
import net.sf.mmm.ui.toolkit.base.AbstractUIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIDecoratedComponentImpl;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UISlicePanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIScrollPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UISplitPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UITabbedPanelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.feature.PrintAction;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SwtWorkerThread;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIButtonImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIComboBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIFileDownloadImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIFileUploadImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UILabelImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIListImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UIProgressBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UISlideBarImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UISpinBoxImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITableImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITextFieldImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.UITreeImpl;
import net.sf.mmm.ui.toolkit.impl.swt.widget.editor.UIDateEditorImpl;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIFrameImpl;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIWorkbenchImpl;

/**
 * This class is the implementation of the UIFactory interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFactorySwt extends AbstractUIFactory {

  /** the display */
  private UIDisplayImpl display;

  /** the actual SWT display */
  private Display swtDisplay;

  /** the worker thread */
  private SwtWorkerThread worker;

  /**
   * The dummy constructor.
   * 
   * This constructor may be used for testing if an instance is required for the
   * default display without using the
   * {@link net.sf.mmm.ui.toolkit.api.UIService UIService}.
   */
  public UIFactorySwt() {

    super();
    this.worker = new SwtWorkerThread();
    this.worker.start();
    this.swtDisplay = this.worker.getDisplay();
    Monitor m = this.worker.getMonitor();
    this.display = new UIDisplayImpl(this, new UIDeviceImpl(m), this.swtDisplay);
  }

  /**
   * The constructor.
   * 
   * @param SWTDisplay
   *        is the display to use.
   */
  public UIFactorySwt(Display SWTDisplay) {

    this(SWTDisplay, new UIDeviceImpl(SWTDisplay.getPrimaryMonitor()));
  }

  /**
   * The constructor.
   * 
   * @param SWTDisplay
   *        is the display to use.
   * @param uiDevice
   *        is the graphics device the display of this factory belongs to.
   */
  public UIFactorySwt(Display SWTDisplay, UIDeviceImpl uiDevice) {

    super();
    this.swtDisplay = SWTDisplay;
    this.display = new UIDisplayImpl(this, uiDevice, this.swtDisplay);
  }

  /**
   * This method gets the dummy parent used for UI objects that should have no
   * parent (<code>null</code>).
   * 
   * @return the SWT dummy parent.
   * @deprecated this hack should be removed!
   */
  @Deprecated
  public Shell getDummyParent() {

    return this.worker.getDummyParent();
  }

  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    UIFrameImpl frame = new UIFrameImpl(UIFactorySwt.this, null, resizeable);
    frame.setTitle(title);
    return frame;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createButton(java.lang.String,
   *      net.sf.mmm.ui.toolkit.api.UIPicture,
   *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
   */
  public UIButton createButton(String text, UIPicture icon, ButtonStyle style) {

    UIButton button = new UIButtonImpl(this, null, style);
    button.setText(text);
    if (icon != null) {
      button.setIcon(icon);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public UISlicePanel createPanel(Orientation orientation, String borderTitle) {

    UISlicePanel panel = new UISlicePanelImpl(this, null, borderTitle, orientation);
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public <D extends UIComponent, C extends UIComponent> UIDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component) {

    UIDecoratedComponentImpl<D, C> decoratedComponent = new UIDecoratedComponentImpl<D, C>(this,
        null, null);
    decoratedComponent.setDecorator(decorator);
    decoratedComponent.setComponent(component);
    return decoratedComponent;
  }

  /**
   * {@inheritDoc}
   */
  public UIScrollPanel createScrollPanel(UIComposite child) {

    UIScrollPanel panel = new UIScrollPanelImpl(this, null);
    if (child != null) {
      panel.setComponent(child);
    }
    return panel;
  }

  /**
   * {@inheritDoc}
   */
  public UISplitPanel createSplitPanel(Orientation orientation) {

    UISplitPanel splitPanel = new UISplitPanelImpl(this, null, null, orientation);
    return splitPanel;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIList<E> createList(UIListModel<E> model, boolean multiSelection) {

    UIList<E> list = new UIListImpl<E>(this, null, multiSelection, model);
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public <N> UITree<N> createTree(boolean multiSelection, UITreeModel<N> model) {

    UITreeImpl<N> tree = new UITreeImpl<N>(this, null, multiSelection);
    if (model != null) {
      tree.setModel(model);
    }
    return tree;
  }

  /**
   * {@inheritDoc}
   */
  public <C> UITable<C> createTable(boolean multiSelection, UITableModel<C> model) {

    UITableImpl<C> table = new UITableImpl<C>(this, null, multiSelection);
    if (model != null) {
      table.setModel(model);
    }
    return table;
  }

  /**
   * {@inheritDoc}
   */
  public UIDisplayImpl getDisplay() {

    return this.display;
  }

  /**
   * {@inheritDoc}
   */
  public UILabel createLabel(String text) {

    UILabel label = new UILabelImpl(this, null);
    label.setText(text);
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public UITextField createTextField(boolean editable) {

    UITextFieldImpl textField = new UITextFieldImpl(this, null);
    textField.setEditable(editable);
    return textField;
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISpinBox<E> createSpinBox(UIListModel<E> model) {

    return new UISpinBoxImpl<E>(this, null, model);
  }

  /**
   * {@inheritDoc}
   */
  public UIFileDownload createFileDownload(FileAccess access) {

    return new UIFileDownloadImpl(this, null, access);
  }

  /**
   * {@inheritDoc}
   */
  public UIFileUpload createFileUpload() {

    return new UIFileUploadImpl(this, null);
  }

  /**
   * {@inheritDoc}
   */
  public UIPicture createPicture(URL imageUrl) throws IOException {

    return new UIPictureImpl(this, imageUrl);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model, boolean editableFlag) {

    return new UIComboBoxImpl<E>(this, null, editableFlag, model);
  }

  /**
   * {@inheritDoc}
   */
  public UITabbedPanel createTabbedPanel() {

    return new UITabbedPanelImpl(this, null);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model, Orientation orientation) {

    // TODO
    return new UISlideBarImpl<E>(this, null, orientation, model);
  }

  /**
   * {@inheritDoc}
   */
  public UIProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, null, orientation, false);
  }

  /**
   * {@inheritDoc}
   */
  public UIWorkbench createWorkbench(String title) {

    UIWorkbenchImpl workbench = new UIWorkbenchImpl(this);
    workbench.setTitle(title);
    return workbench;
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UIComponent component, String actionName, String jobName) {

    return new PrintAction((AbstractUIComponent) component, actionName, jobName);
  }

  /**
   * {@inheritDoc}
   */
  public UIDateEditor createDateEditor() {

    return new UIDateEditorImpl(this, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    this.worker.dispose();
    super.dispose();
  }

  /**
   * This method converts the given <code>buttonStyle</code> to the according
   * {@link org.eclipse.swt.SWT} constant for a {@link MenuItem}.
   * 
   * @param buttonStyle
   *        is the button-style to convert.
   * @return the according SWT button style.
   */
  public static int convertButtonStyleForMenuItem(ButtonStyle buttonStyle) {

    return convertButtonStyle(buttonStyle);
    /*
     * if (buttonStyle == ButtonStyle.DEFAULT) { return SWT.CASCADE; } else {
     * return convertButtonStyle(buttonStyle); }
     */
  }

  /**
   * This method converts the given <code>buttonStyle</code> to the according
   * {@link org.eclipse.swt.SWT} constant for a
   * {@link org.eclipse.swt.widgets.Button}.
   * 
   * @param buttonStyle
   *        is the button-style to convert.
   * @return the according SWT button style.
   */
  public static int convertButtonStyle(ButtonStyle buttonStyle) {

    switch (buttonStyle) {
      case DEFAULT:
        return SWT.PUSH;
      case CHECK:
        return SWT.CHECK;
      case RADIO:
        return SWT.RADIO;
      case TOGGLE:
        return SWT.TOGGLE;
      default :
        throw new IllegalArgumentException("Unknown style " + buttonStyle);
    }
  }

  /**
   * This method converts the given <code>orientation</code> to the according
   * {@link org.eclipse.swt.SWT} constant.
   * 
   * @param orientation
   *        is the orientation to convert.
   * @return the according SWT style.
   */
  public static int convertOrientation(Orientation orientation) {

    switch (orientation) {
      case HORIZONTAL:
        return SWT.HORIZONTAL;
      case VERTICAL:
        return SWT.VERTICAL;
      default :
        throw new IllegalArgumentException("Unknown orientation " + orientation);
    }
  }

}
