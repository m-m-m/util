/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl;

import javax.annotation.PostConstruct;

import net.sf.mmm.util.component.InitializationState;
import net.sf.mmm.util.reflect.VisibilityModifier;

/**
 * This is the abstract base class for the default implementation of
 * {@link net.sf.mmm.util.reflect.pojo.descriptor.base.PojoMethodIntrospector}
 * or {@link net.sf.mmm.util.reflect.pojo.descriptor.base.PojoFieldIntrospector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoIntrospector {

  /** @see #initialize() */
  private final InitializationState initializationState;

  /** @see #getVisibility() */
  private VisibilityModifier visibility;

  /** @see #isAcceptStatic() */
  private boolean acceptStatic;

  /**
   * The constructor.
   */
  public AbstractPojoIntrospector() {

    super();
    this.initializationState = new InitializationState();
  }

  /**
   * @return the initializationState
   */
  protected InitializationState getInitializationState() {

    return this.initializationState;
  }

  /**
   * The constructor. Configures and {@link #initialize() initializes} the
   * component.
   * 
   * @param visibility is the {@link #getVisibility() visibility}.
   * @param acceptStatic is the {@link #isAcceptStatic() accept-static} flag.
   */
  public AbstractPojoIntrospector(VisibilityModifier visibility, boolean acceptStatic) {

    this();
    this.visibility = visibility;
    this.acceptStatic = acceptStatic;
    initialize();
  }

  /**
   * This method gets the {@link VisibilityModifier#getOrder() lowest}
   * {@link VisibilityModifier visibility} to be considered when searching for
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

    this.initializationState.requireNotInitilized();
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

    this.initializationState.requireNotInitilized();
    this.acceptStatic = acceptStatic;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (this.initializationState.setInitializing()) {
      if (this.visibility == null) {
        this.visibility = VisibilityModifier.PUBLIC;
      }
      this.initializationState.setInitialized();
    }
  }

}
