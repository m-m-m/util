/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;


/**
 * This is the abstract base implementation of the {@link UiSingleWidgetFactoryDatatype} interface.
 * 
 * @param <VALUE> is the generic type of the {@link #getDatatype() datatype}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiSingleWidgetFactoryDatatype<VALUE> implements UiSingleWidgetFactoryDatatype<VALUE> {

  /** @see #getDatatype() */
  private final Class<VALUE> datatype;

  /**
   * The constructor.
   * 
   * @param datatype - see {@link #getDatatype()}.
   */
  public AbstractUiSingleWidgetFactoryDatatype(Class<VALUE> datatype) {

    super();
    this.datatype = datatype;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<VALUE> getDatatype() {

    return this.datatype;
  }

}
