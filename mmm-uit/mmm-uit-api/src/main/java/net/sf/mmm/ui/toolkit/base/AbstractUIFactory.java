/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBar;
import net.sf.mmm.ui.toolkit.api.widget.UISlideBar;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIFactory implements UIFactory {

  /** the disposed flag */
  private boolean disposed;

  /**
   * The constructor.
   */
  public AbstractUIFactory() {

    super();
    this.disposed = false;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createFrame(java.lang.String)
   */
  public UIFrame createFrame(String title) {

    return createFrame(title, true);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createLabel()
   */
  public UILabel createLabel() {

    return createLabel("");
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createButton(java.lang.String)
   */
  public UIButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createButton(java.lang.String,
   *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
   */
  public UIButton createButton(String text, ButtonStyle style) {

    return createButton(text, null, style);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createButton(net.sf.mmm.ui.toolkit.api.UIPicture,
   *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
   */
  public UIButton createButton(UIPicture icon, ButtonStyle style) {

    return createButton(null, icon, style);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createButton(net.sf.mmm.ui.toolkit.api.feature.Action)
   */
  public UIButton createButton(Action action) {

    UIButton button = createButton(action.getName(), action.getStyle());
    button.addActionListener(action.getActionListener());
    UIPicture icon = action.getIcon();
    if (icon != null) {
      button.setIcon(icon);
    }
    String id = action.getId();
    if (id != null) {
      button.setId(id);
    }
    return button;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPanel(net.sf.mmm.ui.toolkit.api.composite.Orientation)
   */
  public UIPanel createPanel(Orientation orientation) {

    return createPanel(orientation, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createLabeledComponent(java.lang.String, net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public <C extends UIComponent> UIDecoratedComponent<UILabel, C> createLabeledComponent(
      String label, C component) {
  
    return createDecoratedComponent(createLabel(label), component);
  }
  
  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPicture(java.io.File)
   */
  public UIPicture createPicture(File imageFile) throws IOException {

    try {
      return createPicture(imageFile.toURL());
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createSlideBar(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model) {

    return createSlideBar(model, Orientation.HORIZONTAL);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createList(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public <E> UIList<E> createList(UIListModel<E> model) {

    return createList(model, false);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createComboBox(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model) {

    return createComboBox(model, false);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createProgressBar()
   */
  public UIProgressBar createProgressBar() {

    return createProgressBar(Orientation.HORIZONTAL);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createScrollPanel()
   */
  public UIScrollPanel createScrollPanel() {

    return createScrollPanel(null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTable()
   */
  public UITable<?> createTable() {

    return createTable(false);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTable(boolean)
   */
  public UITable<?> createTable(boolean multiSelection) {

    return createTable(multiSelection, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTree()
   */
  public UITree<?> createTree() {

    return createTree(false);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createTree(boolean)
   */
  public UITree<?> createTree(boolean multiSelection) {

    return createTree(multiSelection, null);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public Action createPrintAction(UIComponent component) {

    // TODO: i18n
    return createPrintAction(component, "Print");
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIFactory#createPrintAction(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String)
   */
  public Action createPrintAction(UIComponent component, String actionName) {

    if (component == null) {
      throw new IllegalArgumentException("Component must NOT be null!");
    }
    return createPrintAction(component, actionName, actionName + " " + component.getId());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#dispose()
   */
  public void dispose() {

    this.disposed = true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed#isDisposed()
   */
  public boolean isDisposed() {

    return this.disposed;
  }

}
