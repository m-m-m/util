/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.widgets;

import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class WidgetsDialogController extends AbstractMainDialogController<WidgetsViewWidget> {

  /**
   * The constructor.
   */
  public WidgetsDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Widgets";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return ShowcaseDialogConstants.DIALOG_ID_WIDGETS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected WidgetsViewWidget createView() {

    return new WidgetsViewWidget(getUiContext());
  }

}
