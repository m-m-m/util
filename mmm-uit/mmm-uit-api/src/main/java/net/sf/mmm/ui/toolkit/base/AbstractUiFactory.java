/* $Id: AbstractUIFactory.java 956 2011-02-20 17:40:50Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.common.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.base.window.AbstractUiWindow;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFactory implements UiFactory {

  /** the disposed flag */
  private boolean disposed;

  /** @see #getLocale() */
  // private Locale locale;

  /** @see #getScriptOrientation() */
  private ScriptOrientation scriptOrientation;

  /** @see #getScriptOrientation() */
  private ScriptOrientation designOrientation;

  /** The list of all windows that have been created by this factory */
  private List<AbstractUiWindow> windows;

  /**
   * The constructor.
   */
  public AbstractUiFactory() {

    super();
    // TODO: do we need a thread-safe implementation here?
    this.windows = new ArrayList<AbstractUiWindow>();
    this.disposed = false;
    // this.locale = Locale.getDefault();
    // TODO: set from default locale!
    this.scriptOrientation = ScriptOrientation.LEFT_TO_RIGHT;
    this.designOrientation = ScriptOrientation.LEFT_TO_RIGHT;
  }

  /**
   * {@inheritDoc}
   */
  // public Locale getLocale() {
  //
  // return this.locale;
  // }

  /**
   * {@inheritDoc}
   */
  // public void setLocale(Locale locale) {
  //
  // this.locale = locale;
  // // TODO: update script-orientation from resource-bundle
  // }

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
   * @return <code>true</code> for inverse orientation, <code>false</code> for
   *         designed orientation.
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
   * @return <code>true</code> for inverse orientation, <code>false</code> for
   *         designed orientation.
   */
  public boolean isFlipHorizontal() {

    return (getScriptOrientation().isLeftToRight() != getDesignOrientation().isLeftToRight());
  }

  /**
   * This method refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWindow windows} created by
   * this factory. The refresh of a window recursively refreshes all
   * {@link net.sf.mmm.ui.toolkit.api.UiNode nodes} contained in the window.
   * This way all visible GUI elements are refreshed.
   * 
   * @param event is the event with details about the refresh.
   */
  public void refresh(UIRefreshEvent event) {

    AbstractUiWindow[] currentWindows;
    synchronized (this.windows) {
      currentWindows = this.windows.toArray(new AbstractUiWindow[this.windows.size()]);
    }
    for (AbstractUiWindow window : currentWindows) {
      window.refresh(event);
    }
  }

  /**
   * This method adds (registers) the given <code>window</code> to this factory.
   * 
   * @param window is the window to add.
   */
  public void addWindow(AbstractUiWindow window) {

    synchronized (this.windows) {
      this.windows.add(window);
    }
  }

  /**
   * This method removes (de-registers) the given <code>window</code> from this
   * factory.
   * 
   * @param window is the window to remove.
   */
  public void removeWindow(AbstractUiWindow window) {

    synchronized (this.windows) {
      this.windows.remove(window);
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiFrame createFrame(String title) {

    return createFrame(title, true);
  }

  /**
   * {@inheritDoc}
   */
  public UiLabel createLabel() {

    return createLabel("");
  }

  /**
   * {@inheritDoc}
   */
  public UiTextField createTextField() {

    return createTextField(true);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(String text, ButtonStyle style) {

    return createButton(text, null, style);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(UiImage icon, ButtonStyle style) {

    return createButton(null, icon, style);
  }

  /**
   * {@inheritDoc}
   */
  public UiButton createButton(UiAction uiAction) {

    UiButton button = createButton(uiAction.getName(), uiAction.getButtonStyle());
    button.addActionListener(uiAction.getActionListener());
    UiImage icon = uiAction.getIcon();
    if (icon != null) {
      button.setImage(icon);
    }
    String id = uiAction.getId();
    if (id != null) {
      button.setId(id);
    }
    return button;
  }

  /**
   * {@inheritDoc}
   */
  public UiSlicePanel<UiElement> createPanel(Orientation orientation) {

    return createPanel(orientation, null);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiSlideBar<E> createSlideBar(UiListMvcModel<E> model) {

    return createSlideBar(model, Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiList<E> createList(UiListMvcModel<E> model) {

    return createList(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public <E> UiComboBox<E> createComboBox(UiListMvcModel<E> model) {

    return createComboBox(model, false);
  }

  /**
   * {@inheritDoc}
   */
  public UiProgressBar createProgressBar() {

    return createProgressBar(Orientation.HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  public UiScrollPanel<UiElement> createScrollPanel() {

    return createScrollPanel(null);
  }

  /**
   * {@inheritDoc}
   */
  public UiTable<?> createTable() {

    return createTable(false);
  }

  /**
   * {@inheritDoc}
   */
  public UiTable<?> createTable(boolean multiSelection) {

    return createTable(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public UiTree<?> createTree() {

    return createTree(false);
  }

  /**
   * {@inheritDoc}
   */
  public UiTree<?> createTree(boolean multiSelection) {

    return createTree(multiSelection, null);
  }

  /**
   * {@inheritDoc}
   */
  public UiAction createPrintUiAction(UiElement component) {

    // TODO: i18n
    return createPrintUiAction(component, "Print");
  }

  /**
   * {@inheritDoc}
   */
  public UiAction createPrintUiAction(UiElement component, String actionName) {

    if (component == null) {
      throw new IllegalArgumentException("Component must NOT be null!");
    }
    return createPrintUiAction(component, actionName, actionName + " " + component.getId());
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
