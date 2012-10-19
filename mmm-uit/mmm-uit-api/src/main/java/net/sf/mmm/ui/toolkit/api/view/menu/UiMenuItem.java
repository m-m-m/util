/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.menu;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelected;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadButtonStyle;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadValue;
import net.sf.mmm.ui.toolkit.api.view.UiNode;

/**
 * This is the interface for a menu-item.<br>
 * Use
 * {@link UiMenu#addItem(String, net.sf.mmm.ui.toolkit.api.event.UiEventListener, net.sf.mmm.ui.toolkit.api.common.ButtonStyle)}
 * to create a menu-item.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiMenuItem extends UiNode, AttributeWriteSelected, UiReadButtonStyle, UiReadValue<String> {

  /** the type of this object */
  String TYPE = "MenuItem";

}
