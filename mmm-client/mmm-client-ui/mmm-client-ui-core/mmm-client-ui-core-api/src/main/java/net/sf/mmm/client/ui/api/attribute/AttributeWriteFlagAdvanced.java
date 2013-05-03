/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.FlagModifier;

/**
 * This interface gives {@link #setFlag(boolean, FlagModifier) advanced} read and write access to a generic
 * {@link #getFlag() flag}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteFlagAdvanced extends AttributeReadFlagAdvanced {

  /**
   * This method sets the {@link #getFlag(FlagModifier) flag} for the given {@link FlagModifier}.
   * 
   * @param flag is the new value of {@link #getFlag() flag}.
   * @param modifier is the {@link FlagModifier}. May be <code>null</code> for the default modifier.
   */
  void setFlag(boolean flag, FlagModifier modifier);

}
