/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ComposedValueConverterFactory;

/**
 * This is the abstract base implementation of {@link ComposedValueConverterFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractComposedValueConverterFactory extends AbstractLoggableComponent implements
    ComposedValueConverterFactory {

  private  ComposedValueConverter defaultConverter;

  /**
   * The constructor.
   */
  public AbstractComposedValueConverterFactory() {

    super();
  }

  @Override
  public ComposedValueConverter getDefaultConverter() {

    return this.defaultConverter;
  }

  /**
   * @param defaultConverter is the default instance of {@link ComposedValueConverter} to {@link Inject}.
   */
  @Inject
  public void setDefaultConverter(ComposedValueConverter defaultConverter) {

    getInitializationState().requireNotInitilized();
    this.defaultConverter = defaultConverter;
  }

}
