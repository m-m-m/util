/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the interface for the component used to manage {@link Dialog}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface DialogManager {

  /**
   * This method gets the {@link Dialog} with the given {@link Dialog#getId() ID}.
   * 
   * @param dialogId the {@link Dialog#getId() ID} of the requested {@link Dialog}.
   * @return the requested {@link Dialog}.
   * @throws ObjectNotFoundException if no {@link Dialog} with the given <code>dialogId</code>.
   */
  Dialog getDialog(String dialogId) throws ObjectNotFoundException;

  /**
   * This method gets the current {@link Dialog} of the given <code>type</code>. If multiple {@link Dialog}s
   * of the same type are open at a time, this method will return the one in the front.
   * 
   * @param type is the {@link Dialog#getType() type} of the requested {@link Dialog}.
   * @return the current {@link Dialog} with the given <code>type</code> or <code>null</code> if no such
   *         {@link Dialog} is visible.
   */
  Dialog getCurrentDialog(String type);

  /**
   * This method gets the current {@link PopupDialog}. If multiple {@link PopupDialog}s are open, it will
   * return the top-level one. <br>
   * <b>ATTENTION:</b><br>
   * Beside a {@link PopupDialog} there can also be {@link net.sf.mmm.client.ui.api.UiPopupHelper simple
   * popups} that are NOT considered by this method.
   * 
   * @return the current {@link PopupDialog} or <code>null</code> if no {@link PopupDialog} is currently open.
   */
  PopupDialog getCurrentPopupDialog();

  /**
   * This method gets the current {@link Dialog} of the {@link Dialog#getType() type}
   * {@link DialogConstants#TYPE_MAIN main}.
   * 
   * @return the current {@link Dialog}.
   */
  Dialog getCurrentMainDialog();

  /**
   * This method initializes this {@link DialogManager}. It opens the first dialog according to the initial
   * {@link DialogPlace} that may be provided by the user e.g. from a bookmark. If no {@link DialogPlace} is
   * given by the user, the <code>defaultPlace</code> is opened.
   * 
   * @param defaultPlace is the {@link DialogPlace} of the start {@link Dialog} to open as default.
   */
  void initialize(DialogPlace defaultPlace);

  /**
   * This method gets the current {@link DialogPlace}.
   * 
   * @return the current {@link DialogPlace}. Will be <code>null</code> until {@link #initialize(DialogPlace)}
   *         has been called.
   */
  DialogPlace getCurrentPlace();

  /**
   * This method navigates back in the history to the {@link DialogPlace} that has been visited before. In a
   * web-application this can also be triggered by pressing the back button.
   */
  void navigateBack();

  /**
   * This method navigates forward in the history. After {@link #navigateBack()} was invoked, this method will
   * go to the {@link DialogPlace} that has been visited before the invocation of {@link #navigateBack()}. In
   * a web-application this can also be triggered by pressing the forward button.
   */
  void navigateForward();

  /**
   * This is a convenience method for
   * <code>{@link #navigateTo(DialogPlace) navigateTo}(new {@link DialogPlace}(dialogId))</code>. <br>
   * <b>ATTENTION:</b><br>
   * It is best practice NOT to use this method. See {@link DialogPlace} for further details.
   * 
   * @param dialogId is the {@link Dialog#getId() ID} of the dialog to open.
   */
  void navigateTo(String dialogId);

  /**
   * This method opens the given {@link DialogPlace} and its according {@link Dialog} in the current
   * {@link ApplicationWindow}. The {@link #getCurrentPlace() current place} is added to the navigation
   * history.
   * 
   * @param place is the {@link DialogPlace} identifying the {@link Dialog} to open.
   */
  void navigateTo(DialogPlace place);

  /**
   * This method opens the given {@link DialogPlace} and its according {@link Dialog} in a new
   * {@link ApplicationWindow}. The operation does not affect the current navigation history. In case of a
   * web-application a new browser window (or tab) is opened that starts another instance of the client at the
   * given <code>place</code>. In a native client a new window is opened with its own {@link DialogManager}
   * instance and new instances of all {@link Dialog}s.
   * 
   * @param place is the {@link DialogPlace} to open in the new window.
   * @return the handle for the new window.
   */
  ApplicationWindow openInNewWindow(DialogPlace place);

}
