/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.listener.UiActionListener;

/**
 * This interface allows to {@link #addActionListener(UiActionListener) add} and
 * {@link #removeActionListener(UiActionListener) remove} {@link UiActionListener}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHasActionListeners {

  /**
   * This method adds the given {@link UiActionListener} to this object.
   * 
   * @param listener is the {@link UiActionListener} to add.
   */
  void addActionListener(UiActionListener listener);

  /**
   * This method removes the given {@link UiActionListener} from this object.
   * 
   * @param listener is the {@link UiActionListener} to remove.
   * @return <code>true</code> if the <code>listener</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addActionListener(UiActionListener) registered} and nothing has changed.
   */
  boolean removeActionListener(UiActionListener listener);

}
