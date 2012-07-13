/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog;

import java.util.Map;

import net.sf.mmm.client.base.dialog.AbstractSimplePopupManager;
import net.sf.mmm.ui.toolkit.api.common.MessageSeverity;
import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.lang.api.Callback;

import com.google.gwt.user.client.Window;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SimplePopupManagerDummy extends AbstractSimplePopupManager {

  /**
   * The constructor.
   */
  public SimplePopupManagerDummy() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param nlsBundle
   */
  public SimplePopupManagerDummy(NlsBundleUtilCoreRoot nlsBundle) {

    super(nlsBundle);
    // TODO Auto-generated constructor stub
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity, String title, Callback<String> callback,
      Map<String, String> id2buttonLabelMap) {

    Window.alert(message);
    if (callback != null) {
      callback.apply(BUTTON_ID_OK);
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
