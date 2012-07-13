/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.dialog;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.api.dialog.SimplePopupManager;
import net.sf.mmm.ui.toolkit.api.common.MessageSeverity;
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
   * This method gets the title for the given {@link MessageSeverity}.
   * 
   * @param severity is the {@link MessageSeverity}.
   * @return the according title.
   */
  protected String getTitle(MessageSeverity severity) {

    NlsMessage message;
    if (severity == MessageSeverity.INFORMATION) {
      message = this.nlsBundle.infoInformation();
    } else if (severity == MessageSeverity.WARNING) {
      message = this.nlsBundle.infoWarning();
    } else if (severity == MessageSeverity.ERROR) {
      message = this.nlsBundle.infoError();
    } else if (severity == MessageSeverity.QUESTION) {
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
  public void showPopup(String message, MessageSeverity severity, String title) {

    showPopup(message, severity, title, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity) {

    showPopup(message, severity, getTitle(severity));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity, Callback<String> callback) {

    showPopup(message, severity, getTitle(severity), callback);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopupYesNo(String message, String title, Callback<String> callback) {

    Map<String, String> id2buttonLabelMap = new HashMap<String, String>();
    id2buttonLabelMap.put(BUTTON_ID_OK, this.nlsBundle.infoYes().getLocalizedMessage());
    id2buttonLabelMap.put(BUTTON_ID_CANCEL, this.nlsBundle.infoNo().getLocalizedMessage());
    showPopup(message, MessageSeverity.QUESTION, title, callback, id2buttonLabelMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity, String title, Callback<String> callback) {

    Map<String, String> id2buttonLabelMap = new HashMap<String, String>();
    id2buttonLabelMap.put(BUTTON_ID_OK, this.nlsBundle.infoOk().getLocalizedMessage());
    showPopup(message, severity, title, callback, id2buttonLabelMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showPopup(String message, MessageSeverity severity, String title, Callback<String> callback,
      String labelOk, String labelCancel) {

    Map<String, String> id2buttonLabelMap = new HashMap<String, String>();
    id2buttonLabelMap.put(BUTTON_ID_OK, labelOk);
    id2buttonLabelMap.put(BUTTON_ID_CANCEL, labelCancel);
    showPopup(message, severity, title, callback, id2buttonLabelMap);
  }

}
