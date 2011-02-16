/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UIFactoryRenamed;
import net.sf.mmm.ui.toolkit.api.UiObject;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UiObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUIObject implements UiObject {

  /** @see #getFactory() */
  private AbstractUIFactory factory;

  /** @see #getId() */
  private String id;

  /** @see #getStyle() */
  private String style;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   */
  public AbstractUIObject(AbstractUIFactory uiFactory) {

    super();
    this.factory = uiFactory;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIFactory getFactory() {

    return this.factory;
  }

  /**
   * {@inheritDoc}
   * 
   * Override this method if you implement a window object.
   */
  public boolean isWindow() {

    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * Override this method if you implement a widget object.
   */
  public boolean isWidget() {

    return false;
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
  public String getStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public void setStyle(String style) {

    this.style = style;
  }

}
