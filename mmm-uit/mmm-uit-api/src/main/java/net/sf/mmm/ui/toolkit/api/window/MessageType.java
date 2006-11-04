/* $Id$ */
package net.sf.mmm.ui.toolkit.api.window;

/**
 * This enum contains the available types for message dialogs.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#showMessage(String, String,
 *      MessageType)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum MessageType {

  /** the error message type */
  ERROR,

  /** the warning message type */
  WARNING,

  /** the info message type */
  INFO;

}
