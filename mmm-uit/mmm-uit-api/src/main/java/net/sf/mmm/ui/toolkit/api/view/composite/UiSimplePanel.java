/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadOrientation;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a simple panel. Such panel is a {@link UiComposite} that can dynamically
 * aggregate multiple other {@link UiElement}s. According to the {@link #getOrientation() orientation} the
 * {@link #getChild(int) children} are placed {@link net.sf.mmm.util.lang.api.Orientation#HORIZONTAL} or
 * {@link net.sf.mmm.util.lang.api.Orientation#VERTICAL}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public interface UiSimplePanel<CHILD extends UiElement> extends UiExtendableComposite<CHILD>, AttributeReadOrientation {

  /** @see #getType() */
  String TYPE = "SimplePanel";

}
