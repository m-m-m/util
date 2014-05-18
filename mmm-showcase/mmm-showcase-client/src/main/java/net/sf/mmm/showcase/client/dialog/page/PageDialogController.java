/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.page;

import net.sf.mmm.client.ui.base.dialog.page.AbstractPageDialogController;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PageDialogController extends AbstractPageDialogController<PageViewWidget> {

  /**
   * The constructor.
   */
  public PageDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Showcase";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PageViewWidget createView() {

    return new PageViewWidget(getUiContext());
  }

}
