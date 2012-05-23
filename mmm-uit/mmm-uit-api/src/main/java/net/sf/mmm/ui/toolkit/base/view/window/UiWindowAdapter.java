/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWritePosition;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTitle;
import net.sf.mmm.ui.toolkit.base.view.UiNodeAdapter;

/**
 * This is the interface that adapts to the native UI object of the underlying
 * toolkit implementation for a
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public interface UiWindowAdapter<DELEGATE> extends UiNodeAdapter<DELEGATE>, AttributeWriteSize, AttributeWriteTitle, AttributeWritePosition {

  /**
   * @see net.sf.mmm.ui.toolkit.api.view.window.UiWindow#pack()
   */
  void pack();

}
