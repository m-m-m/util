/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaActiveDescendant;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaChecked;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaLevel;
import net.sf.mmm.client.ui.api.aria.attribute.AttributeWriteAriaOrientation;
import net.sf.mmm.client.ui.api.aria.datatype.AriaTristate;
import net.sf.mmm.util.lang.api.Orientation;

/**
 * This class extends {@link AbstractRoleWithAttributeExpanded} with various common ARIA attributes to avoid
 * java multi-inheritance problems. As {@link net.sf.mmm.client.ui.api.aria.role.Role}s shall always be
 * accessed via their interface we consider this as implementation secret and expect nobody to bypass the API
 * and access invalid attributes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRoleWithCommonAttributes extends AbstractRoleWithBaseAttributes implements
    AttributeWriteAriaChecked, AttributeWriteAriaLevel, AttributeWriteAriaActiveDescendant,
    AttributeWriteAriaOrientation {

  /** @see #getChecked() */
  private AriaTristate checked;

  /** @see #getLevel() */
  private Integer level;

  /** @see #getActiveDescendant() */
  private String activeDescendant;

  /** @see #getOrientation() */
  private Orientation orientation;

  /**
   * The constructor.
   */
  public AbstractRoleWithCommonAttributes() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChecked(AriaTristate checked) {

    this.checked = checked;
    setAttribute(HTML_ATTRIBUTE_ARIA_CHECKED, checked);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AriaTristate getChecked() {

    return this.checked;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getLevel() {

    return this.level;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLevel(Integer level) {

    this.level = level;
    setAttribute(HTML_ATTRIBUTE_ARIA_LEVEL, level);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setActiveDescendant(String id) {

    this.activeDescendant = id;
    setAttribute(HTML_ATTRIBUTE_ARIA_ACTIVE_DESCENDANT, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getActiveDescendant() {

    return this.activeDescendant;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Orientation getOrientation() {

    return this.orientation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOrientation(Orientation orientation) {

    this.orientation = orientation;
    setAttribute(HTML_ATTRIBUTE_ARIA_ORIENTATION, orientation);
  }

}
