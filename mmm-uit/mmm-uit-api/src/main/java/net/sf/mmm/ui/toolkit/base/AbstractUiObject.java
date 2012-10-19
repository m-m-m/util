/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.UiObject;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.ui.toolkit.api.UiObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiObject implements UiObject, AttributeWriteHtmlId {

  /** @see #getFactory() */
  private AbstractUiFactory factory;

  /** @see #getId() */
  private String id;

  /** @see #getId() */
  private static int idCounter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiObject(AbstractUiFactory uiFactory) {

    super();
    this.factory = uiFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiFactory getFactory() {

    return this.factory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    if (this.id == null) {
      this.id = "id" + idCounter++;
    }
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    this.id = newId;
  }

}
