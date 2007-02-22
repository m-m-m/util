/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteEditable;
import net.sf.mmm.ui.toolkit.api.state.UIWriteText;

/**
 * This is the interface for a combo-box UI. A combo-box is used to display
 * items in a list as a pull-down-menu.<br>
 * By default only the selected item is displayed together with an arrow-down
 * button. If selected, the complete list pops up as menu ontop of the button.
 * If the number of items is too large to fit in one menu, the menu becomes
 * scrollable or contains a "virtual" item at the end that opens another menu
 * containing more of the elements. <br>
 * If the combo-box is
 * {@link net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable() editable},
 * the user can additionally enter free text instead of selecting one of the
 * given items. Please note that the user can enter text, that does not
 * correspond to an item in the model. Therefore you need to call
 * {@link #getText()} instead of {@link #getSelectedValue()} to retrieve the
 * user selection if the combo is editable.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIComboBox<E> extends UIListWidget<E>, UIWriteEditable, UIWriteText {

  /** the type of this object */
  String TYPE = "ComboBox";

}
