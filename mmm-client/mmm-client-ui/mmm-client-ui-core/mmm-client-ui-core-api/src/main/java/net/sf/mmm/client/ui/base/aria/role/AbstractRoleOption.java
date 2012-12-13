/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleOption;

/**
 * This is the abstract base implementation of {@link RoleOption}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleOption extends AbstractRoleWithCommonAttributes implements RoleOption {

  /** @see #getPosInSet() */
  private Integer posInSet;

  /** @see #getSetSize() */
  private Integer setSize;

  /**
   * The constructor.
   */
  public AbstractRoleOption() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getPosInSet() {

    return this.posInSet;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosInSet(Integer posInSet) {

    this.posInSet = posInSet;
    setAttribute(HTML_ATTRIBUTE_ARIA_POS_IN_SET, posInSet);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSetSize(Integer setSize) {

    this.setSize = setSize;
    setAttribute(HTML_ATTRIBUTE_ARIA_SET_SIZE, setSize);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getSetSize() {

    return this.setSize;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateDelegate() {

    super.updateDelegate();
    if (this.posInSet != null) {
      setPosInSet(this.posInSet);
    }
    if (this.setSize != null) {
      setSetSize(this.setSize);
    }
  }
}
