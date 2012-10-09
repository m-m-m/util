/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.SelectionMode;

/**
 * This interface gives read access to the {@link #getSelectionMode() selection mode} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSelectionMode {

  /**
   * This method gets the {@link SelectionMode}.
   * 
   * @return the {@link SelectionMode}. Must not be <code>null</code>.
   */
  SelectionMode getSelectionMode();

}
