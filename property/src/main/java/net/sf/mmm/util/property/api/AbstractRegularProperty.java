/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the abstract base implementation of a regular {@link WritableProperty}. Here regular means that the
 * {@link #getValue() value} type is not special such as a {@link java.util.Collection}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@SuppressWarnings("restriction")
public abstract class AbstractRegularProperty<V> extends AbstractValueProperty<V> {

  private ExpressionHelper<V> helper;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractRegularProperty(String name, Bean bean) {
    this(name, bean, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractRegularProperty(String name, Bean bean, AbstractValidator<? super V> validator) {
    super(name, bean, validator);
  }

  @Override
  public void addListener(ChangeListener<? super V> listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super V> listener) {

    this.helper = ExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  protected void fireValueChangedEvent() {

    ExpressionHelper.fireValueChangedEvent(this.helper);
  }

}
