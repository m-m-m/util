/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.test;

import net.sf.mmm.app.client.dialog.MmmDialogConstants;
import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;

/**
 * This is the {@link AbstractMainDialogController controller} for the {@link TestViewWidget test view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TestDialogController extends AbstractMainDialogController<TestViewWidget> {

  /**
   * The constructor.
   */
  public TestDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Test";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return MmmDialogConstants.DIALOG_ID_TEST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TestViewWidget createView() {

    return new TestViewWidget(getUiContext(), getDialogManager());
  }

}
