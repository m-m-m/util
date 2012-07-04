/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api.dialog;

import java.util.Map;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.lang.api.Callback;

/**
 * This is the interface for a component that allows to open very simple popup dialogs.<br/>
 * <b>ATTENTION:</b><br/>
 * Be aware of {@link net.sf.mmm.util.nls.api.NlsMessage internationalization} whenever you supply messages,
 * labels or titles of a popup. Please also note that this component is designed for a client that has to deal
 * with a single {@link java.util.Locale}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SimplePopupManager {

  /** The ID for the "OK" button. */
  String BUTTON_ID_OK = "#ok";

  /** The ID for the "Cancel" button. */
  String BUTTON_ID_CANCEL = "#cancel";

  /**
   * This method opens a popup dialog with the given <code>message</code>.
   * 
   * @param message is the message to display.
   * @param severityIcon is the {@link SeverityIcon}. Should NOT be {@link SeverityIcon#QUESTION}.
   * @param title is the title that will be displayed in the title-bar of the popup.
   */
  void showPopup(String message, SeverityIcon severityIcon, String title);

  /**
   * This method opens a popup dialog with the given <code>message</code>. The title of the popup is
   * automatically derived from the given {@link SeverityIcon}.
   * 
   * @see #showPopup(String, SeverityIcon, String)
   * 
   * @param message is the message to display.
   * @param severityIcon is the {@link SeverityIcon}. Should NOT be {@link SeverityIcon#QUESTION}.
   */
  void showPopup(String message, SeverityIcon severityIcon);

  /**
   * This method opens a confirmation popup dialog with the given <code>message</code> and an "OK" button.
   * 
   * @param message is the message to display.
   * @param severityIcon is the {@link SeverityIcon}. Should NOT be {@link SeverityIcon#QUESTION}.
   * @param callback is the {@link Callback} invoked after the popup has been confirmed (the user has clicked
   *        the "OK" button). The given {@link String} value will always be {@link #BUTTON_ID_OK}.
   */
  void showPopup(String message, SeverityIcon severityIcon, Callback<String> callback);

  /**
   * This method opens a confirmation popup dialog with the given <code>message</code> and an "OK" button.
   * 
   * @param message is the message to display.
   * @param title is the title that will be displayed in the title-bar of the popup.
   * @param severityIcon is the {@link SeverityIcon}. Should NOT be {@link SeverityIcon#QUESTION}.
   * @param callback is the {@link Callback} invoked after the popup has been confirmed (the user has clicked
   *        the "OK" button). The given {@link String} value will always be {@link #BUTTON_ID_OK}.
   */
  void showPopup(String message, SeverityIcon severityIcon, String title, Callback<String> callback);

  /**
   * This method opens a confirmation popup dialog with the given <code>message</code> and two buttons for the
   * options "Yes" and "No". It will use {@link SeverityIcon#QUESTION}.<br/>
   * <b>ATTENTION:</b><br/>
   * This is a very common also also very often misused feature of UI toolkits or frameworks. A suitable
   * example is e.g. to use this with the message "Do you really want to delete this object?". However, it
   * would still be more explicit to have the buttons labeled with "Delete" and "Cancel" using
   * {@link #showPopup(String, SeverityIcon, String, Callback, String, String)}. An example for a misuse of
   * this method would be the message
   * "Do you want to delete this occurrence or the series of the appointment? Press "
   * Yes" to delete the series and "No" for this occurrence.". From this bad example that confuses the user,
   * you should learn that it is always better to use more explicit labels.
   * 
   * @param message is the message to display.
   * @param title is the title that will be displayed in the title-bar of the popup.
   * @param callback is the {@link Callback} invoked after the popup has been confirmed (the user has clicked
   *        on one of the buttons). The given {@link String} value will be the ID of the button that has been
   *        clicked. Here, either {@link #BUTTON_ID_OK} for "Yes" or {@link #BUTTON_ID_CANCEL} for "No".
   */
  void showPopupYesNo(String message, String title, Callback<String> callback);

  /**
   * This method opens a confirmation popup dialog with the given <code>message</code> and two buttons for the
   * options "Yes" and "No". It will use {@link SeverityIcon#QUESTION}.
   * 
   * @param message is the message to display.
   * @param severityIcon is the {@link SeverityIcon} - typically {@link SeverityIcon#QUESTION}.
   * @param title is the title that will be displayed in the title-bar of the popup.
   * @param callback is the {@link Callback} invoked after the popup has been confirmed (the user has clicked
   *        on one of the buttons). The given {@link String} value will be the ID of the button that has been
   *        clicked. Here, either {@link #BUTTON_ID_OK} or {@link #BUTTON_ID_CANCEL}.
   * @param labelOk is the label for the OK-button (may also be something like "Save", "Approve", etc.).
   * @param labelCancel is the label for the cancel button.
   */
  void showPopup(String message, SeverityIcon severityIcon, String title, Callback<String> callback, String labelOk,
      String labelCancel);

  /**
   * This method opens a confirmation popup dialog with the given <code>message</code>. It is the most generic
   * and flexible but also the most inconvenient method variant.
   * 
   * @param message is the message to display.
   * @param severityIcon is the {@link SeverityIcon}. Should NOT be {@link SeverityIcon#QUESTION}.
   * @param title is the title that will be displayed in the title-bar of the popup.
   * @param callback is the {@link Callback} invoked after the popup has been confirmed (the user has clicked
   *        on one of the buttons). The given {@link String} value will be the ID of the button that has been
   *        clicked. The ID is the {@link Map#keySet() key} of the given <code>id2buttonLabelMap</code>.
   * @param id2buttonLabelMap is a {@link Map} defining the buttons for the popup. The {@link Map#values()
   *        values} are the labels of the buttons while the {@link Map#keySet() keys} are their IDs. The ID of
   *        the button that has been clicked is passed to the given <code>callback</code>.
   */
  void showPopup(String message, SeverityIcon severityIcon, String title, Callback<String> callback,
      Map<String, String> id2buttonLabelMap);

  /**
   * This method opens a popup dialog showing the given <code>error</code>. It will use
   * {@link SeverityIcon#ERROR}.
   * 
   * @param error is the {@link Throwable} that has occurred.
   */
  void showPopup(Throwable error);

}
