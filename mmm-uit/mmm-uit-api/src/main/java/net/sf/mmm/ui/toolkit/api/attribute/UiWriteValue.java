/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValue() value}
 * of an {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteValue<V> extends UiReadValue<V> {

  /**
   * This method sets the text of this object.
   * 
   * @param text is the new text for this object.
   */
  void setValue(V text);

}
