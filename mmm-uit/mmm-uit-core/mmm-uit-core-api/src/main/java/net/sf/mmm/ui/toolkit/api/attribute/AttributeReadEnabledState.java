/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.EnabledState;

/**
 * This interface gives read access to the {@link #getEnabledState() enabled-state} of an object.
 * 
 * @see AttributeReadEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadEnabledState {

  /**
   * This method gets the {@link EnabledState} of this object.
   * 
   * @see AttributeReadEnabled#isEnabled()
   * 
   * @return the {@link EnabledState}.
   */
  EnabledState getEnabledState();

}
