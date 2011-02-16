/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteEditable;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSelectionIndex;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSelectionValue;

/**
 * This is the interface for a spin-box. It is an iteractive widget used to
 * specify an value out of an ordered list. The widget is visualized like a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITextField text-field} for the
 * integer value but with additional buttons to increment and decrement the
 * current value within the allowed range. The decrement button should have an
 * arrow-down icon or the text "-". The increment button should have an arrow-up
 * icon or the text "+". The buttons should also be associated with the
 * arrow-up/down keys on the keyboard.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UISpinBox<E> extends UIListWidget<E>, UiWriteSelectionIndex,
    UiWriteSelectionValue<E>, UiWriteEditable {

  /** the type of this object */
  String TYPE = "SpinBox";

}
