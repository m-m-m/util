/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.dialog;

import net.sf.mmm.client.ui.base.dialog.DialogSlot;

/**
 * This interface is (mis)used to define the constants for common {@link Dialog#getId() dialog IDs},
 * {@link Dialog#getType() dialog types}, {@link DialogSlot}s and {@link DialogPlace}s. <br>
 * Technically mandatory for the core implementation are only {@link #TYPE_ROOT} and {@link #TYPE_MAIN}.
 * However, you find the entire common sense here and there are (base) implementations for
 * {@link net.sf.mmm.client.ui.base.dialog.DialogController} that depend on these predefined constants and
 * will make your life a lot easier. <br>
 * We recommend that you extend this interface with your own <code>MyProjectDialogConstants</code> to define
 * the constants for your dialogs. In most cases you should avoid defining constants for parameterized
 * {@link DialogPlace}s.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DialogConstants {

  /**
   * The {@link Dialog#getType() type} of the <em>root</em> dialog. This is the top-level dialog of the
   * application and represents the {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow}.
   *
   * @see net.sf.mmm.client.ui.base.dialog.root.RootDialogController
   */
  String TYPE_ROOT = "Root";

  /**
   * The {@link Dialog#getType() type} of the <em>page</em> dialog. This is the first custom dialog that
   * defines the structure of the application page (main window). It is supposed to embed itself into the
   * {@link #TYPE_ROOT root} page and provide {@link net.sf.mmm.client.ui.base.dialog.DialogSlot slots} for
   * child dialogs, at least a {@link #TYPE_MAIN main} slot. For an example see
   * {@link net.sf.mmm.client.ui.base.dialog.DialogController}.
   *
   * @see net.sf.mmm.client.ui.base.dialog.page.AbstractPageDialogController
   */
  String TYPE_PAGE = "Page";

  /**
   * The {@link Dialog#getType() type} of a <em>main</em> dialog. This is a regular dialog displayed in the
   * main content area of the client application window.
   */
  String TYPE_MAIN = "Main";

  /**
   * The {@link Dialog#getType() type} of a <em>header</em> dialog. This is a dialog displayed at the top of
   * the main content area of the client application window above a {@link #TYPE_MAIN main dialog}.
   */
  String TYPE_HEADER = "Header";

  /**
   * The {@link Dialog#getType() type} of a <em>footer</em> dialog. This is a dialog displayed at the bottom
   * of the main content area of the client application window below a {@link #TYPE_MAIN main dialog}.
   */
  String TYPE_FOOTER = "Footer";

  /**
   * The {@link Dialog#getType() type} of a <em>navigation</em> dialog. The navigation offers a structured way
   * for the user to navigate through the application. This can happen in form of a menu-bar that is typically
   * placed below the {@link #TYPE_HEADER header} and above the {@link #TYPE_MAIN main dialog}. However it is
   * often realized as a list or tree of links that is displayed beside the {@link #TYPE_MAIN main dialog}
   * (typically on the left).
   */
  String TYPE_NAVIGATION = "Navigation";

  /**
   * The {@link Dialog#getType() type} of a <em>side</em> dialog. This is a dialog typically displayed at on
   * the right of the main content area of the client application window beside a {@link #TYPE_MAIN main
   * dialog}. It typically shows additional information or a summary of the current state (e.g. shopping
   * cart). However, it may also be used to display advertisements.
   */
  String TYPE_SIDE = "Side";

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_ROOT root} dialog. */
  String DIALOG_ID_ROOT = "root";

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_PAGE page} dialog. */
  String DIALOG_ID_PAGE = "page";

  /** The {@link Dialog#getId() dialog-id} of the (default) {@link #TYPE_NAVIGATION navigation} dialog. */
  String DIALOG_ID_NAVIGATION = "nav";

  /**
   * The {@link DialogSlot} of the {@link #DIALOG_ID_ROOT root dialog} where the {@link #TYPE_PAGE page}
   * dialog is {@link net.sf.mmm.client.ui.base.dialog.DialogController#embed} embedded.
   */
  DialogSlot SLOT_ROOT_PAGE = new DialogSlot(DIALOG_ID_ROOT, TYPE_PAGE);

  /** The {@link DialogSlot} for the main dialog. */
  DialogSlot SLOT_PAGE_MAIN = new DialogSlot(DIALOG_ID_PAGE, TYPE_MAIN);

  /** The {@link DialogSlot} for the navigation dialog. */
  DialogSlot SLOT_PAGE_NAVIGATION = new DialogSlot(DIALOG_ID_NAVIGATION, TYPE_NAVIGATION);

  /**
   * The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog that defines the home screen
   * or start page. It is just a recommendation to make your life easier. You can also define your own ID and
   * place and just ignore these constant if you have a reason to do so.
   *
   * @see DialogManager#initialize(DialogPlace)
   */
  String DIALOG_ID_HOME = "home";

  /**
   * The {@link DialogPlace} for the {@link #DIALOG_ID_HOME home dialog}.
   *
   * @see DialogManager#initialize(DialogPlace)
   */
  DialogPlace PLACE_HOME = new DialogPlace(DIALOG_ID_HOME);

}
