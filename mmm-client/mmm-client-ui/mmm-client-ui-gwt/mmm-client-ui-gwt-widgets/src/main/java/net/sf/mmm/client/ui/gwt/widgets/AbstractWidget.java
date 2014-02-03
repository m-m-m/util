/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base class for custom widgets that do not have to derive classes below {@link Widget}.
 * It adds some missing default convenience features such as {@link #setId(String)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractWidget extends Widget implements AttributeWriteHtmlId {

  /**
   * The constructor.
   * 
   */
  public AbstractWidget() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return getElement().getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    getElement().setId(id);
  }

}
