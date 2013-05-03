/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.attribute;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteFlagAdvanced;
import net.sf.mmm.client.ui.api.common.FlagModifier;

/**
 * This is the abstract base implementation of {@link AttributeWriteFlagAdvanced}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractFlagAdvanced implements AttributeWriteFlagAdvanced {

  /** @see #setFlag(boolean, FlagModifier) */
  private Set<FlagModifier> falseModifiersSet;

  /**
   * The constructor.
   */
  public AbstractFlagAdvanced() {

    super();
  }

  /**
   * The constructor for a concurrent (thread-safe) implementation. Typically clients have less requirements
   * for concurrency and ideally use a single UI thread.
   * 
   * @param falseModifiersSet is the underlying {@link Set} used to store the {@link FlagModifier} for which
   *        the flag has been set to <code>false</code>.
   */
  protected AbstractFlagAdvanced(Set<FlagModifier> falseModifiersSet) {

    super();
    this.falseModifiersSet = new HashSet<FlagModifier>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getFlag() {

    if (this.falseModifiersSet == null) {
      return true;
    }
    return this.falseModifiersSet.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getFlag(FlagModifier modifier) {

    if (this.falseModifiersSet == null) {
      return true;
    }
    return this.falseModifiersSet.contains(modifier);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFlag(boolean flag, FlagModifier modifier) {

    if (flag) {
      if (this.falseModifiersSet != null) {
        this.falseModifiersSet.remove(modifier);
      }
    } else {
      if (this.falseModifiersSet == null) {
        this.falseModifiersSet = new HashSet<FlagModifier>();
      }
      this.falseModifiersSet.add(modifier);
    }
  }

}
