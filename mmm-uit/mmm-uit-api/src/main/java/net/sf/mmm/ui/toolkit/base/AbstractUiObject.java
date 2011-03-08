/* $Id: AbstractUIObject.java 957 2011-02-21 22:18:03Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UiObject;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteId;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiObject implements UiObject, UiWriteId {

  /** @see #getFactory() */
  private AbstractUiFactory factory;

  /** @see #getId() */
  private String id;

  /** @see #getStyles() */
  private String style;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiObject(AbstractUiFactory uiFactory) {

    super();
    this.factory = uiFactory;
    this.style = "";
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUiFactory getFactory() {

    return this.factory;
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  public void setId(String newId) {

    this.id = newId;
  }

  /**
   * {@inheritDoc}
   */
  public String getStyles() {

    return this.style;
  }

  /**
   * This method sets the {@link #getStyles() styles} internally.
   * 
   * @param styles are the new styles to set.
   */
  protected void doSetStyles(String styles) {

    this.style = styles;
  }

}
