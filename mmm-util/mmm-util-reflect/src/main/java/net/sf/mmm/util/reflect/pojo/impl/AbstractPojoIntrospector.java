/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import net.sf.mmm.util.component.AbstractInitializableComponent;
import net.sf.mmm.util.reflect.VisibilityModifier;

/**
 * This is the abstract base class for the default implementation of
 * {@link net.sf.mmm.util.reflect.pojo.base.PojoMethodIntrospector} or
 * {@link net.sf.mmm.util.reflect.pojo.base.PojoFieldIntrospector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoIntrospector extends AbstractInitializableComponent {

  /** @see #getVisibility() */
  private VisibilityModifier visibility;

  /** @see #isAcceptStatic() */
  private boolean acceptStatic;

  /**
   * The constructor.
   */
  public AbstractPojoIntrospector() {

    super();
  }

  /**
   * The constructor. Configures and {@link #initialize() initializes} the
   * component.
   * 
   * @param visibility is the {@link #getVisibility() visibility}.
   * @param acceptStatic is the {@link #isAcceptStatic() accept-static} flag.
   */
  public AbstractPojoIntrospector(VisibilityModifier visibility, boolean acceptStatic) {

    super();
    this.visibility = visibility;
    this.acceptStatic = acceptStatic;
    initialize();
  }

  /**
   * This method gets the {@link VisibilityModifier#getOrder() lowest}
   * {@link VisibilityModifier visibility} to be considered when ) searching for
   * methods or fields.
   * 
   * @return the visibility
   */
  public VisibilityModifier getVisibility() {

    return this.visibility;
  }

  /**
   * This method sets the {@link #getVisibility() visibility}.
   * 
   * @param visibility is the {@link VisibilityModifier#getOrder() lowest}
   *        {@link VisibilityModifier visibility} to be considered when
   *        searching for accessors-fields.
   */
  public void setVisibility(VisibilityModifier visibility) {

    requireNotInitilized();
    this.visibility = visibility;
  }

  /**
   * @return the acceptStatic
   */
  public boolean isAcceptStatic() {

    return this.acceptStatic;
  }

  /**
   * @param acceptStatic the acceptStatic to set
   */
  public void setAcceptStatic(boolean acceptStatic) {

    requireNotInitilized();
    this.acceptStatic = acceptStatic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (this.visibility == null) {
      this.visibility = VisibilityModifier.PUBLIC;
    }
  }

}
