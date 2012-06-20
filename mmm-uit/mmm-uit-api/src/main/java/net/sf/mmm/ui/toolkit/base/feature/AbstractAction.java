/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.view.UiImage;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.ui.toolkit.api.feature.UiAction}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractAction implements UiAction {

  /** the {@link #getName() name} */
  private String name;

  /** the {@link #getId() id} */
  private String id;

  /** the {@link #getButtonStyle() style} */
  private ButtonStyle buttonStyle;

  /** the {@link #getIcon() icon} */
  private UiImage icon;

  /**
   * The constructor.
   */
  public AbstractAction() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param displayName is the {@link #getName() name}.
   */
  public AbstractAction(String displayName) {

    super();
    this.buttonStyle = ButtonStyle.DEFAULT;
    this.icon = null;
    this.name = displayName;
    this.id = displayName;
  }

  /**
   * This method sets the {@link #getName() name}.
   * 
   * @param newName is the new name.
   */
  public void setName(String newName) {

    this.name = newName;
    if (this.id == null) {
      this.name = newName;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * This method sets the {@link #getId() id}.
   * 
   * @param newId is the new id to set.
   */
  public void setId(String newId) {

    this.id = newId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getButtonStyle() style}.
   * 
   * @param newStyle is the style to set.
   */
  public void setButtonStyle(ButtonStyle newStyle) {

    this.buttonStyle = newStyle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ButtonStyle getButtonStyle() {

    return this.buttonStyle;
  }

  /**
   * This method set the {@link #getIcon() icon}.
   * 
   * @param newIcon is the new icon to set.
   */
  public void setIcon(UiImage newIcon) {

    this.icon = newIcon;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiImage getIcon() {

    return this.icon;
  }

}
