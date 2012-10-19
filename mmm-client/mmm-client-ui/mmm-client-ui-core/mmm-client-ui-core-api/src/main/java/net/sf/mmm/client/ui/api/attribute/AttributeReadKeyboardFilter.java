/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This interface gives read access to the {@link #getKeyboardFilter() keyboard-filter} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadKeyboardFilter {

  /**
   * This method gets the <em>keyboard-filter</em>. Only characters {@link CharFilter#accept(char) accepted}
   * by filter can be entered into the according UI field.
   * 
   * @return the current keyboard-filter or <code>null</code> if not set.
   */
  CharFilter getKeyboardFilter();

}
