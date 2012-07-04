/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.dialog;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.api.dialog.SeverityIcon;
import net.sf.mmm.client.api.dialog.SimplePopupManager;
import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.lang.api.Callback;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the abstract base implementation of {@link SimplePopupManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSimplePopupManager implements SimplePopupManager {

  /** The instance of {@link NlsBundleUtilCoreRoot}. */
  private final NlsBundleUtilCoreRoot nlsBundle;

  /**
   * The constructor.
   */
  public AbstractSimplePopupManager() {

    this(NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class));
  }

  /**
   * The constructor.
   * 
   * @param nlsBundle is the instance of {@link NlsBundleUtilCoreRoot}.
   */
  public AbstractSimplePopupManager(NlsBundleUtilCoreRoot nlsBundle) {

    super();
    this.nlsBundle = nlsBundle;
  }

  /**
   * This method gets the title for the given {@link SeverityIcon}.
   * 
   * @param severityIcon is the {@link SeverityIcon}.
   * @return the according title.
   */
  protected String getTitle(SeverityIcon severityIcon) {

    NlsMessage message;
    if (severityIcon == SeverityIcon.INFORMATION) {
      message = this.nlsBundle.infoInformation();
    } else if (severityIcon == SeverityIcon.WARNING) {
      message = this.nlsBundle.infoWarning();
    } else if (severityIcon == SeverityIcon.ERROR) {
      message = this.nlsBundle.infoError();
    } else if (severityIcon == SeverityIcon.QUESTION) {
      message = this.nlsBundle.infoConfirmation();
    } else {
      message = null;
    }
    if (message == null) {
      return null;
    } else {
      return message.getLocalizedMessage();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, SeverityIcon severityIcon, String title) {

    showPopup(message, severityIcon, title, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, SeverityIcon severityIcon) {

    showPopup(message, severityIcon, getTitle(severityIcon));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopupYesNo(String message, String title, Callback<String> callback) {

    Map<String, String> id2buttonLabelMap = new HashMap<String, String>();
    id2buttonLabelMap.put(BUTTON_ID_OK, this.nlsBundle.infoYes().getLocalizedMessage());
    id2buttonLabelMap.put(BUTTON_ID_CANCEL, this.nlsBundle.infoNo().getLocalizedMessage());
    showPopup(message, SeverityIcon.QUESTION, title, null, id2buttonLabelMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, SeverityIcon severityIcon, String title, Callback<String> callback,
      String labelOk, String labelCancel) {

    Map<String, String> id2buttonLabelMap = new HashMap<String, String>();
    id2buttonLabelMap.put(BUTTON_ID_OK, labelOk);
    id2buttonLabelMap.put(BUTTON_ID_CANCEL, labelCancel);
    showPopup(message, severityIcon, title, callback, id2buttonLabelMap);
  }

}
