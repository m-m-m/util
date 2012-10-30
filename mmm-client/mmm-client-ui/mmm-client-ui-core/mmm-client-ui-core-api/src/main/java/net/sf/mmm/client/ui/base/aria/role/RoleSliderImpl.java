/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.RoleSlider;

/**
 * This is the implementation of {@link RoleSlider}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class RoleSliderImpl extends AbstractRoleRange implements RoleSlider {

  /**
   * The constructor.
   */
  public RoleSliderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return ARIA_ROLE_SLIDER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<RoleSlider> getRoleInterface() {

    return RoleSlider.class;
  }

}
