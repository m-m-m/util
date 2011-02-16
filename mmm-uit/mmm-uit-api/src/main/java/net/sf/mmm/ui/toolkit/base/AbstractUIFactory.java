/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.ui.toolkit.api.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;
import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIDecoratedComponent;
import net.sf.mmm.ui.toolkit.api.composite.UISlicePanel;
import net.sf.mmm.ui.toolkit.api.composite.UIScrollPanel;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
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
import net.sf.mmm.ui.toolkit.api.widget.UITextField;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIFactoryRenamed} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIFactory implements UIFactoryRenamed {

  /** the disposed flag */
  private boolean disposed;

  /** @see #getLocale() */
  private Locale locale;

  /** @see #getScriptOrientation() */
  private ScriptOrientation scriptOrientation;

  /** @see #getScriptOrientation() */
  private ScriptOrientation designOrientation;

  /** The list of all windows that have been created by this factory */
  private List<AbstractUIWindow> windows;

  /**
   * The constructor.
   */
  public AbstractUIFactory() {

    super();
    // TODO: do we need a thread-safe implementation here?
    this.windows = new ArrayList<AbstractUIWindow>();
    this.disposed = false;
    this.locale = Locale.getDefault();
    // TODO: set from default locale!
    this.scriptOrientation = ScriptOrientation.LEFT_TO_RIGHT;
    this.designOrientation = ScriptOrientation.LEFT_TO_RIGHT;
  }

  /**
   * {@inheritDoc}
   */
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * {@inheritDoc}
   */
  public void setLocale(Locale locale) {

    this.locale = locale;
    // TODO: update script-orientation from resource-bundle
  }

  /**
   * {@inheritDoc}
   */
  public ScriptOrientation getScriptOrientation() {

    return this.scriptOrientation;
  }

  /**
   * {@inheritDoc}
   */
  public void setScriptOrientation(ScriptOrientation scriptOrientation) {

    if (this.scriptOrientation != scriptOrientation) {
      this.scriptOrientation = scriptOrientation;
      refresh(UIRefreshEvent.ORIENTATION_MODIFIED);
    }
  }

  /**
   * {@inheritDoc}
   */
  public ScriptOrientation getDesignOrientation() {

    return this.designOrientation;
  }

  /**
   * {@inheritDoc}
   */
  public void setDesignOrientation(ScriptOrientation orientation) {

    this.designOrientation = orientation;
  }

  /**
   * This method determines if the vertical orientation of the GUI should be
   * inverted (mirrored).
   * 
   * @see #getScriptOrientation()
   * @see #getDesignOrientation()
   * 
   * @return <code>true</code> for inverse orientation, <code>false</code>
   *         for designed orientation.
   */
  public boolean isFlipVertical() {

    return (getScriptOrientation().isHorizontal() != getDesignOrientation().isHorizontal());
  }

  /**
   * This method determines if the horizontal orientation of the GUI should be
   * inverted (mirrored).
   * 
   * @see #getScriptOrientation()
   * @see #getDesignOrientation()
   * 
   * @return <code>true</code> for inverse orientation, <code>false</code>
   *         for designed orientation.
   */
  public boolean isFlipHorizontal() {

    return (getScriptOrientation().isLeftToRight() != getDesignOrientation().isLeftToRight());
  }

  /**
   * This method refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.window.UIWindow windows} created by this
   * factory. The refresh of a window recursively refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.UINodeRenamed nodes} contained in the window.
   * This way all visible GUI elements are refreshed.
   * 
   * @param event is the event with details about the refresh.
   */
  public void refresh(UIRefreshEvent event) {

    AbstractUIWindow[] currentWindows;
    synchronized (this.windows) {
      currentWindows = this.windows.toArray(new AbstractUIWindow[this.windows.size()]);
    }
    for (AbstractUIWindow window : currentWindows) {
      window.refresh(event);
    }
  }

  /**
   * This method adds (registers) the given <code>window</code> to this
   * factory.
   * 
   * @param window is the window to add.
   */
  public void addWindow(AbstractUIWindow window) {

    synchronized (this.windows) {
      this.windows.add(window);
    }
  }

  /**
   * This method removes (de-registers) the given <code>window</code> from
   * this factory.
   * 
   * @param window is the window to remove.
   */
  public void removeWindow(AbstractUIWindow window) {

    synchronized (this.windows) {
      this.windows.remove(window);
    }
  }

  /**
   * {@inheritDoc}
   */
  public UIFrame createFrame(String title) {

    return createFrame(title, true);
  }

  /**
   * {@inheritDoc}
   */
  public UILabel createLabel() {

    return createLabel("");
  }

  /**
   * {@inheritDoc}
   */
  public UITextField createTextField() {

    return createTextField(true);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(String text, ButtonStyle style) {

    return createButton(text, null, style);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(UiImage icon, ButtonStyle style) {

    return createButton(null, icon, style);
  }

  /**
   * {@inheritDoc}
   */
  public UIButton createButton(Action action) {

    UIButton button = createButton(action.getName(), action.getButtonStyle());
    button.addActionListener(action.getActionListener());
    UiImage icon = action.getIcon();
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
   * {@inheritDoc}
   */
  public UISlicePanel createPanel(Orientation orientation) {

    return createPanel(orientation, null);
  }

  /**
   * {@inheritDoc}
   */
  public <C extends UiElement> UIDecoratedComponent<UILabel, C> createLabeledComponent(
      String label, C component) {

    return createDecoratedComponent(createLabel(label), component);
  }

  /**
   * {@inheritDoc}
   */
  public UIDecoratedComponent<UILabel, UISlicePanel> createLabeledComponents(String label,
      UiElement... components) {

    UISlicePanel panel = createPanel(Orientation.HORIZONTAL);
    for (UiElement component : components) {
      panel.addComponent(component);
    }
    return createLabeledComponent(label, panel);
  }

  /**
   * {@inheritDoc}
   */
  public UiImage createPicture(File imageFile) throws IOException {

    try {
      return createPicture(imageFile.toURI().toURL());
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <E> UISlideBar<E> createSlideBar(UIListModel<E> model) {

    return createSlideBar(model, Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIList<E> createList(UIListModel<E> model) {

    return createList(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UIComboBox<E> createComboBox(UIListModel<E> model) {

    return createComboBox(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public UIProgressBar createProgressBar() {

    return createProgressBar(Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public UIScrollPanel createScrollPanel() {

    return createScrollPanel(null);
  }

  /**
   * {@inheritDoc}
   */
  public UITable<?> createTable() {

    return createTable(false);
  }

  /**
   * {@inheritDoc}
   */
  public UITable<?> createTable(boolean multiSelection) {

    return createTable(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public UITree<?> createTree() {

    return createTree(false);
  }

  /**
   * {@inheritDoc}
   */
  public UITree<?> createTree(boolean multiSelection) {

    return createTree(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UiElement component) {

    // TODO: i18n
    return createPrintAction(component, "Print");
  }

  /**
   * {@inheritDoc}
   */
  public Action createPrintAction(UiElement component, String actionName) {

    if (component == null) {
      throw new IllegalArgumentException("Component must NOT be null!");
    }
    return createPrintAction(component, actionName, actionName + " " + component.getId());
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    this.disposed = true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    return this.disposed;
  }

}
