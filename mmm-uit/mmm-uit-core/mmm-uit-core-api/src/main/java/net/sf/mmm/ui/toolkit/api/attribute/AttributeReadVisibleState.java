/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.VisibleState;

/**
 * This interface gives read access to the {@link #getVisibleState() visible state} of an object.
 * 
 * @see AttributeReadVisible
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadVisibleState {

  /**
   * This method gets the {@link VisibleState} of this object.
   * 
   * @see VisibleState#isVisible()
   * 
   * @return the {@link VisibleState}.
   */
  VisibleState getVisibleState();

}
