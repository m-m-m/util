/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This interface is used as collection of constants with the names of central image icons.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface IconConstants {

  /**
   * The name of the icon for a validation error. The icon is typically showing a red cross but smaller than
   * {@link #ICON_MESSAGE_ERROR}.
   * 
   * @see net.sf.mmm.ui.toolkit.api.attribute.AttributeReadValidationFailure#getValidationFailure()
   */
  String ICON_VALIDATION_FAILURE = "validation-failure.png";

  /**
   * The name of the icon for an error message. The icon is typically showing a red cross.
   * 
   * @see MessageSeverity#ERROR
   */
  String ICON_MESSAGE_ERROR = "message-error.png";

  /**
   * The name of the icon for an warning message. The icon is typically showing a yellow triangle with an
   * exclamation mark.
   * 
   * @see MessageSeverity#WARNING
   */
  String ICON_MESSAGE_WARNING = "message-warning.png";

  /**
   * The name of the icon for an error message. The icon is typically showing a balloon.
   * 
   * @see MessageSeverity#INFORMATION
   */
  String ICON_MESSAGE_INFORMATION = "message-information.png";

  /**
   * The name of the icon for a question message. The icon is typically showing a question mark.
   * 
   * @see MessageSeverity#QUESTION
   */
  String ICON_MESSAGE_QUESTION = "message-question.png";

}
