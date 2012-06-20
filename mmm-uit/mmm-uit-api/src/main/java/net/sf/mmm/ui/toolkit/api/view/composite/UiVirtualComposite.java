/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a virtual {@link UiComposite}. It is not reflected in the user-interface but acts
 * as a logical group of {@link UiElement}s. This allows to
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public interface UiVirtualComposite<CHILD extends UiElement> extends UiExtendableComposite<CHILD> {

}
