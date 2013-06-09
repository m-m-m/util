/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link UiDataBindingFactory}. It currently does nothing and its
 * only purpose is to prevent problems with potential
 * {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extensions} of {@link UiDataBindingFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiDataBindingFactory extends AbstractLoggableComponent implements UiDataBindingFactory {

  /**
   * The constructor.
   */
  public AbstractUiDataBindingFactory() {

    super();
  }

}
