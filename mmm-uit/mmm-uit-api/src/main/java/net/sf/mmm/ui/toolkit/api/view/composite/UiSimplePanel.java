/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadOrientation;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a simple panel. Such panel is a {@link UiComposite}
 * that can dynamically aggregate multiple other {@link UiElement}s. According
 * to the {@link #getOrientation() orientation} the {@link #getChild(int)
 * children} are placed
 * {@link net.sf.mmm.ui.toolkit.api.common.Orientation#HORIZONTAL} or
 * {@link net.sf.mmm.ui.toolkit.api.common.Orientation#VERTICAL}.<br>
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 *        This should actually be bound to {@link UiElement}. However as we
 *        finally need an implementation of {@link UiElement} java forces us to
 *        leave the generic type variable here. This is solved by casting in the
 *        {@link net.sf.mmm.ui.toolkit.api.UiFactory}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiSimplePanel<E extends UiElement> extends UiExtendableComposite<E>,
    UiReadOrientation {

  /** @see #getType() */
  String TYPE = "SimplePanel";

}
