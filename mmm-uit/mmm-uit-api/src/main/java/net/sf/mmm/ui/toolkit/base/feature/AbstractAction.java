/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.feature.Action;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.Action} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractAction implements Action {

  /** the {@link #getName() name} */
  private String name;

  /** the {@link #getId() id} */
  private String id;

  /** the {@link #getStyle() style} */
  private ButtonStyle style;

  /** the {@link #getIcon() icon} */
  private UIPicture icon;

  /**
   * The constructor.
   */
  public AbstractAction() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param displayName
   *        is the {@link #getName() name}.
   */
  public AbstractAction(String displayName) {

    super();
    this.style = ButtonStyle.DEFAULT;
    this.icon = null;
    this.name = displayName;
    this.id = displayName;
  }

  /**
   * This method sets the {@link #getName() name}.
   * 
   * @param newName
   *        is the new name.
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
  public String getName() {

    return this.name;
  }

  /**
   * This method sets the {@link #getId() id}.
   * 
   * @param newId
   *        is the new id to set.
   */
  public void setId(String newId) {

    this.id = newId;
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getStyle() style}.
   * 
   * @param newStyle
   *        is the style to set.
   */
  public void setStyle(ButtonStyle newStyle) {

    this.style = newStyle;
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getStyle() {

    return this.style;
  }

  /**
   * This method set the {@link #getIcon() icon}.
   * 
   * @param newIcon
   *        is the new icon to set.
   */
  public void setIcon(UIPicture newIcon) {

    this.icon = newIcon;
  }

  /**
   * {@inheritDoc}
   */
  public UIPicture getIcon() {

    return this.icon;
  }

}
