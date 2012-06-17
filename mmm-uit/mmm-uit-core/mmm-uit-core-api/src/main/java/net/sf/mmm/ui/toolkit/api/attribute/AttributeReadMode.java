/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.UiMode;

/**
 * This interface gives read access to the {@link #getMode() mode} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadMode {

  /**
   * This method gets the current {@link UiMode} of this object.
   * 
   * @return the {@link UiMode}.
   */
  UiMode getMode();

}
