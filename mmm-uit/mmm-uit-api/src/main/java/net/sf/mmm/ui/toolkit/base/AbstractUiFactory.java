/* $Id: AbstractUIFactory.java 956 2011-02-20 17:40:50Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UiFactory;
import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.common.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.api.view.composite.UiScrollPanel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.api.view.widget.UiProgressBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSlideBar;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.api.view.window.UiWorkbench;
import net.sf.mmm.ui.toolkit.base.view.window.AbstractUiWindow;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFactory implements UiFactory {

  /**
   * The title of the application.
   * 
   * @see #getOrCreateWorkbench()
   */
  private String applicationTitle;

  /** the disposed flag */
  private boolean disposed;

  /** @see #getLocale() */
  // private Locale locale;

  /** @see #getScriptOrientation() */
  private ScriptOrientation scriptOrientation;

  /** @see #getScriptOrientation() */
  private ScriptOrientation designOrientation;

  /** @see #getOrCreateWorkbench() */
  private UiWorkbench workbench;

  /** The list of all windows that have been created by this factory */
  private List<AbstractUiWindow<?>> windows;

  /**
   * The constructor.
   * 
   * @param title is the title of this factory. Should be the name of the actual
   *        application creating the UI.
   */
  public AbstractUiFactory(String title) {

    super();
    this.applicationTitle = title;
    // TODO: do we need a thread-safe implementation here?
    this.windows = new ArrayList<AbstractUiWindow<?>>();
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
   * {@link net.sf.mmm.ui.toolkit.api.view.UiNode nodes} contained in the
   * window. This way all visible GUI elements are refreshed.
   * 
   * @param event is the event with details about the refresh.
   */
  public void refresh(UIRefreshEvent event) {

    // use concurrent list via list factory?
    AbstractUiWindow<?>[] currentWindows;
    synchronized (this.windows) {
      currentWindows = this.windows.toArray(new AbstractUiWindow[this.windows.size()]);
    }
    for (AbstractUiWindow<?> window : currentWindows) {
      window.refresh(event);
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiWorkbench getOrCreateWorkbench() {

    if (this.workbench == null) {
      synchronized (this) {
        if (this.workbench == null) {
          this.workbench = createWorkbench(this.applicationTitle);
        }
      }
    }
    return this.workbench;
  }

  /**
   * This method creates a new {@link UiWorkbench}.
   * 
   * @see #getOrCreateWorkbench()
   * 
   * @param workbenchTitle is the {@link UiWorkbench#getTitle() title}.
   * 
   * @return the {@link UiWorkbench}.
   */
  protected abstract UiWorkbench createWorkbench(String workbenchTitle);

  /**
   * This method adds (registers) the given <code>window</code> to this factory.
   * 
   * @param window is the window to add.
   */
  public void addWindow(AbstractUiWindow<?> window) {

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
  public void removeWindow(AbstractUiWindow<?> window) {

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
  public UiButton createButton(String text) {

    return createButton(text, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public <E extends UiElement> UiBorderPanel<E> createBorderPanel(String title, E child) {

    UiBorderPanel<E> panel = createBorderPanel(title);
    panel.setChild(child);
    return panel;
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
  public <CHILD extends UiElement> UiScrollPanel<CHILD> createScrollPanel() {

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

    String jobName = actionName;
    if (component == null) {
      // throw new IllegalArgumentException("Component must NOT be null!");
    } else {
      jobName = actionName + " " + component.getId();
    }
    return createPrintUiAction(component, actionName, jobName);
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

  /**
   * This method is invoked if an
   * {@link net.sf.mmm.ui.toolkit.api.event.UiEventListener} caused an error.
   * This default implementation does nothing. Override this method to change
   * this (log the exception, throw it or whatever).
   * 
   * @param error is the error to handle.
   */
  public void handleEventError(RuntimeException error) {

    // do nothing ...
  }

  /**
   * @see UiFrame#createFrame(String, boolean)
   * 
   * @param parent is the actual {@link UiFrame} where
   *        {@link UiFrame#createFrame(String, boolean)} was invoked.
   * @param title is the title the new frame will have.
   * @param resizable - if <code>true</code> the frame can be resized by the
   *        user.
   * @return the created frame.
   */
  public abstract UiFrame createFrame(UiFrame parent, String title, boolean resizable);

  /**
   * @see UiWindow#createDialog(String, boolean, boolean)
   * 
   * @param parent is the actual {@link UiWindow} where
   *        {@link UiWindow#createDialog(String, boolean, boolean)} was invoked.
   * @param title is the {@link UiDialog#getTitle() title} of the dialog.
   * @param modal - if <code>true</code> all windows of the application are
   *        blocked while the dialog is visible.
   * @param resizeable - if <code>true</code> the dialog can be resized by the
   *        user.
   * @return the created dialog.
   */
  public abstract UiDialog createDialog(UiWindow parent, String title, boolean modal, boolean resizeable);

  /**
   * @see UiWindow#showMessage(String, String, MessageType, Throwable)
   * 
   * @param parent is the actual {@link UiWindow} where
   *        {@link UiWindow#showMessage(String, String, MessageType, Throwable)}
   *        was invoked.
   * @param message is the complete and detailed message to show.
   * @param title is a short title.
   * @param messageType classifies the type of the message to show (according
   *        icon).
   * @param throwable is the cause of the actual message. Maybe
   *        <code>null</code> here (if invoked via
   *        {@link UiWindow#showMessage(String, String, MessageType)}).
   */
  public abstract void showMessage(UiWindow parent, String message, String title, MessageType messageType,
      Throwable throwable);

  /**
   * @see UiWindow#showQuestion(String, String)
   * 
   * @param parent is the actual {@link UiWindow} where
   *        {@link UiWindow#showQuestion(String, String)} was invoked.
   * @param question is the complete question to ask including all details the
   *        user should know to be able to answer the question easily.
   * @param title is a short title.
   * @return <code>true</code> if the question is answered with yes,
   *         <code>false</code> otherwise.
   */
  public abstract boolean showQuestion(UiWindow parent, String question, String title);
}
