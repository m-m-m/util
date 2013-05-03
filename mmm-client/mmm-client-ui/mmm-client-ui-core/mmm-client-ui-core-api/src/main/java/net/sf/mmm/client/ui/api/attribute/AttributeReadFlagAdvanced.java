/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.FlagModifier;

/**
 * This interface gives {@link #getFlag(FlagModifier) advanced} read access to a generic {@link #getFlag()
 * flag}.
 * 
 * @see AttributeWriteVisible
 * @see AttributeWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadFlagAdvanced extends AttributeReadFlag {

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>NOTE:</b><br/>
   * This method determines if the flag is <code>true</code> for all {@link FlagModifier}s.
   * 
   * @return <code>true</code> if all {@link #getFlag(FlagModifier) aggregated flags} are <code>true</code>
   *         and all parents have the same flag set to <code>true</code>.
   */
  @Override
  boolean getFlag();

  /**
   * This method gets the value of the flag for the given {@link FlagModifier}.<br/>
   * 
   * @param modifier is the {@link FlagModifier}. May be <code>null</code> for the default modifier.
   * @return the flag for the given {@link FlagModifier}. Will be <code>true</code> if it has never been set
   *         for the given {@link FlagModifier}.
   */
  boolean getFlag(FlagModifier modifier);

}
