/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.masterdetail;

import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MasterDetailDialogController extends AbstractMainDialogController<MasterDetailViewWidget> {

  /**
   * The constructor.
   */
  public MasterDetailDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Master/Detail";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return ShowcaseDialogConstants.DIALOG_ID_MASTER_DETAIL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MasterDetailViewWidget createView() {

    return new MasterDetailViewWidget(getUiContext());
  }

}
