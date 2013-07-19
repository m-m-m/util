/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.base.AbstractUiPopupHelper;
import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * This is a dummy implementation of {@link net.sf.mmm.client.ui.api.UiPopupHelper}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiPopupHelperTest extends AbstractUiPopupHelper {

  /**
   * The constructor.
   */
  public UiPopupHelperTest() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param nlsBundle is the instance of {@link NlsBundleUtilCoreRoot}.
   */
  public UiPopupHelperTest(NlsBundleUtilCoreRoot nlsBundle) {

    super(nlsBundle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity, String title, Consumer<String> callback,
      Map<String, String> id2buttonLabelMap) {

    getLogger().info(message);
    if (callback != null) {
      callback.accept(BUTTON_ID_OK);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(Throwable error) {

    showPopup(error.getLocalizedMessage(), MessageSeverity.ERROR);
  }

}
