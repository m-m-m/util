/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isVisibleRecursive() visible-recursive} property of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadVisibleRecursive extends AttributeReadVisible {

  /**
   * This method determines if this object is actually visible for the end-user. In advance to
   * {@link #isVisible()} it also takes its parent objects into account. So it only returns <code>true</code>
   * if the object is actually attached to the UI and physically in the current dialogue (it may still be
   * clipped from the screen or scrolled out of the visible panel).
   * 
   * @return <code>true</code> if visible to the end-user, <code>false</code> otherwise.
   */
  boolean isVisibleRecursive();

}
