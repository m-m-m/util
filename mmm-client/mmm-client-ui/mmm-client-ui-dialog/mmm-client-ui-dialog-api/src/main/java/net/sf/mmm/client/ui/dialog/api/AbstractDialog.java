/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.api;

import net.sf.mmm.client.ui.api.attribute.AttributeReadVisible;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the abstract base interface for a dialog or screen of the client application. This should NOT be
 * mixed with a popup.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractDialog extends AttributeReadId<String>, AttributeReadTitle<String>,
    AttributeReadVisible {

  /** The regex pattern {@link #getId() dialog IDs} have to match. */
  String PATTERN_DIALOG_ID = "[^:;=?&\\s/\\\\]+";

  /**
   * {@inheritDoc}
   */
  @Override
  String getTitle();

  /**
   * {@inheritDoc}
   * 
   * The ID of a dialog should only contain ASCII letters, Latin digits, hyphens or underscores. It must NOT
   * contain ':', ';', '&', '?', '=', '/', '\\' or ' '.
   */
  @Override
  String getId();

  /**
   * This method determines if this dialog is currently visible (opened).
   * 
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  @Override
  boolean isVisible();

}
