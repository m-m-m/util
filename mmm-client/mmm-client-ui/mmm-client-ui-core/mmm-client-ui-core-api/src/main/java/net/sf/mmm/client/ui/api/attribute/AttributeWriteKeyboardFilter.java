/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This interface gives read and write access to the {@link #getKeyboardFilter() keyboard-filter} attribute of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeWriteKeyboardFilter extends AttributeReadKeyboardFilter {

  /**
   * This method sets the {@link #getKeyboardFilter() keyboard-filter}. Typically such filter is only set once
   * (e.g. to build custom datatype fields based on generic text input widgets) and then never changed.<br/>
   * <b>ATTENTION:</b><br/>
   * A keyboard filter is no guarantee that it is impossible for the user to enter invalid characters. E.g. if
   * you are building a web-client some browser quirks allow to bypass the keyboard filter using copy and
   * paste. Be sure to also validate the result in any case.
   * 
   * @param keyboardFilter is the keyboard filter to set. An existing filter will be replaced. May be
   *        <code>null</code> to remove an existing filter.
   */
  void setKeyboardFilter(CharFilter keyboardFilter);

}
