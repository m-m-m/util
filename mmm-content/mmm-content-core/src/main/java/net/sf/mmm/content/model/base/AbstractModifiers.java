/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.model.api.Modifiers;

/**
 * This is the base implementation of the {@link Modifiers} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractModifiers implements Modifiers {

  /** @see #isSystem() */
  private final boolean systemFlag;

  /** @see #isFinal() */
  private final boolean finalFlag;

  /**
   * The constructor.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   */
  public AbstractModifiers(boolean isSystem, boolean isFinal) {

    super();
    this.systemFlag = isSystem;
    this.finalFlag = isFinal;
  }

  /**
   * The constructor.
   * 
   * @param modifiers is the modifiers object to copy.
   */
  public AbstractModifiers(Modifiers modifiers) {

    this(modifiers.isSystem(), modifiers.isFinal());
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSystem() {

    return this.systemFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isFinal() {

    return this.finalFlag;
  }

}
