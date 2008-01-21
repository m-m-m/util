/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the {@link #isModal() modal} state of an
 * object.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIDialog
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadModal {

  /**
   * This method determines if this object (dialog) is modal. If a modal dialog
   * is opened all previous windows of the application are blocked until the
   * window is closed.
   * 
   * @return <code>true</code> if modal, <code>false</code> otherwise.
   */
  boolean isModal();

}
