/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadValue;

/**
 * This is the interface for a listener that gets
 * {@link #onValueChange(UiReadValue, boolean) notified} if the value of a
 * {@link net.sf.mmm.ui.toolkit.api.UiObject} (e.g. a checkbox, text-field,
 * etc.) changes. It will be triggered both via user-interaction and on
 * programatical changes.
 * 
 * @param <V> is the generic type of the value.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiValueChangeListener<V> {

  /**
   * This method is called when the value of the
   * {@link net.sf.mmm.ui.toolkit.api.view.UiNode} changed where this
   * {@link UiValueChangeListener} was registered.
   * 
   * @param uiNode is the according
   *        {@link net.sf.mmm.ui.toolkit.api.view.UiNode} and can be cased to
   *        such.
   * @param programatic - <code>true</code> if the change was caused
   *        programatical and <code>false</code> if caused by the user.
   */
  void onValueChange(UiReadValue<V> uiNode, boolean programatic);

}
