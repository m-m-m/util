/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.list;

import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ListDialogController extends AbstractMainDialogController<ListViewWidget> {

  /**
   * The constructor.
   */
  public ListDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "List";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return ShowcaseDialogConstants.DIALOG_ID_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ListViewWidget createView() {

    ListViewWidget listViewWidget = new ListViewWidget(getUiContext());
    listViewWidget.getContactList().initTestdata();
    return listViewWidget;
  }

}
