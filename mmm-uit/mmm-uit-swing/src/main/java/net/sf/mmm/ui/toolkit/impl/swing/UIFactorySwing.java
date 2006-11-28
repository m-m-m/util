/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UIDisplay;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
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
import net.sf.mmm.ui.toolkit.impl.awt.UIDeviceImpl;
import net.sf.mmm.ui.toolkit.impl.awt.UIDisplayImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIDecoratedComponentImpl;
import net.sf.mmm.ui.toolkit.impl.swing.composite.UIPanelImpl;
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
 */
public class UIFactorySwing extends AbstractUIFactory {

  /** the default display */
  private UIDisplayImpl display;

  /**
   * The dummy constructor.
   * 
   * This constructor may be used for testing if an instance is required for the
   * default display without using the
   * {@link net.sf.mmm.ui.toolkit.api.UIService UIService}.
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
   * @param uiDevice
   *        is the device the display of this factory belongs to.
   * @param graphicConfiguration
   *        is the graphics configuration for the diplay to represent.
   */
  public UIFactorySwing(UIDeviceImpl uiDevice, GraphicsConfiguration graphicConfiguration) {

    super();
    this.display = new UIDisplayImpl(this, uiDevice, graphicConfiguration);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#getDisplay()
   */
  public UIDisplay getDisplay() {

    return this.display;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#getDisplay()
   * 
   * @return the AWT display.
   */
  public GraphicsConfiguration getAwtGc() {

    return this.display.getGraphicsConfiguration();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createFrame(java.lang.String,
   *      boolean)
   */
  public UIFrame createFrame(String title, boolean resizeable) {

    return new UIFrameImpl(this, null, title, resizeable);
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
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation,
   *      java.lang.String)
   */
  public UIPanel createPanel(Orientation orientation, String text) {

    UIPanelImpl panel = new UIPanelImpl(this, null, orientation);
    panel.setBorderTitle(text);
    return panel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createDecoratedComponent(net.sf.mmm.ui.toolkit.api.UIComponent, net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public <D extends UIComponent, C extends UIComponent> UIDecoratedComponent<D, C> createDecoratedComponent(
      D decorator, C component) {
  
    UIDecoratedComponent<D, C> decoratedComponent = new UIDecoratedComponentImpl<D, C>(this, null);
    decoratedComponent.setDecorator(decorator);
    decoratedComponent.setComponent(component);
    return decoratedComponent;
  }
  
  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createScrollPanel(net.sf.mmm.ui.toolkit.api.composite.UIComposite)
   */
  public UIScrollPanel createScrollPanel(UIComposite child) {

    UIScrollPanel scrollPanel = new UIScrollPanelImpl(this, null);
    scrollPanel.setComponent(child);
    return scrollPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createList(net.sf.mmm.ui.toolkit.api.model.UIListModel,
   *      boolean)
   */
  public <E> UIList<E> createList(UIListModel<E> model, boolean multiSelection) {

    UIListImpl<E> list = new UIListImpl<E>(this, null);
    list.setMultiSelection(multiSelection);
    if (model != null) {
      list.setModel(model);
    }
    return list;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createComboBox(net.sf.mmm.ui.toolkit.api.model.UIListModel,
   *      boolean)
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model, boolean editableFlag) {

    UIComboBox<E> comboBox = new UIComboBoxImpl<E>(this, null);
    comboBox.setEditable(editableFlag);
    if (model != null) {
      comboBox.setModel(model);
    }
    return comboBox;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTree(boolean,
   *      net.sf.mmm.ui.toolkit.api.model.UITreeModel)
   */
  public UITree createTree(boolean multiSelection, UITreeModel model) {

    UITreeImpl tree = new UITreeImpl(this, null);
    tree.setMultiSelection(multiSelection);
    if (model != null) {
      tree.setModel(model);
    }
    return tree;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTable(boolean,
   *      net.sf.mmm.ui.toolkit.api.model.UITableModel)
   */
  public UITable createTable(boolean multiSelection, UITableModel model) {

    UITableImpl table = new UITableImpl(this, null);
    // table.setMultiSelection(multiSelection);
    if (model != null) {
      table.setModel(model);
    }
    return table;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createLabel(java.lang.String)
   */
  public UILabel createLabel(String text) {

    UILabel label = new UILabelImpl(this, null);
    label.setText(text);
    return label;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTextField()
   */
  public UITextField createTextField() {

    return new UITextFieldImpl(this, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createSpinBox(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public <E> UISpinBox<E> createSpinBox(UIListModel<E> model) {

    return new UISpinBoxImpl<E>(this, null, model);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createFileDownload(net.sf.mmm.ui.toolkit.api.feature.FileAccess)
   */
  public UIFileDownload createFileDownload(FileAccess access) {

    return new UIFileDownloadImpl(this, null, access);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createFileUpload()
   */
  public UIFileUpload createFileUpload() {

    return new UIFileUploadImpl(this, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPicture(java.net.URL)
   */
  public UIPicture createPicture(URL imageUrl) {

    return new UIPictureImpl(this, imageUrl);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTabbedPanel()
   */
  public UITabbedPanel createTabbedPanel() {

    return new UITabbedPanelImpl(this, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createSplitPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation)
   */
  public UISplitPanel createSplitPanel(Orientation orientation) {

    return new UISplitPanelImpl(this, null, orientation);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createSlideBar(net.sf.mmm.ui.toolkit.api.model.UIListModel,
   *      net.sf.mmm.ui.toolkit.api.composite.Orientation)
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model, Orientation orientation) {

    return new UISlideBarImpl<E>(this, null, orientation, model);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createProgressBar(net.sf.mmm.ui.toolkit.api.composite.Orientation)
   */
  public UIProgressBar createProgressBar(Orientation orientation) {

    return new UIProgressBarImpl(this, null, orientation);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createWorkbench(java.lang.String)
   */
  public UIWorkbench createWorkbench(String title) {

    return new UIWorkbenchImpl(this, title, true);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String, java.lang.String)
   */
  public Action createPrintAction(UIComponent component, String actionName, String jobName) {

    return new PrintAction((AbstractUIComponent) component, actionName, jobName);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createDateEditor()
   */
  public UIDateEditor createDateEditor() {

    return new UIDateEditorImpl(this, null);
  }

}
