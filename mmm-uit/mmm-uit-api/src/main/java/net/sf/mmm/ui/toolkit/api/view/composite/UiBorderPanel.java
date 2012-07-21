/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} that can
 * hold one child {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} that is surrounded by
 * a {@link #setTitle(String) titled} border.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public interface UiBorderPanel<CHILD extends UiElement> extends UiSingleComposite<CHILD>, AttributeWriteStringTitle {

  /** the type of this object */
  String TYPE = "BorderPanel";

}
