/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;

/**
 * This interface allows to {@link #getSlot(DialogSlot) determine a slot}. It is typically implemented by a
 * view for {@link DialogController#embed(DialogController, DialogSlot) embedding} of sub-dialogs via their
 * {@link DialogController}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadSlot {

  /**
   * Gets the {@link UiWidgetSlot} identified by the given {@link DialogSlot}.
   *
   * @param slot is the {@link DialogSlot}.
   * @return the corresponding {@link UiWidgetSlot}.
   */
  UiWidgetSlot getSlot(DialogSlot slot);

}
