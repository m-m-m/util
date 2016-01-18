/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.base.AbstractGenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link GenericProperty}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("restriction")
public abstract class AbstractRegularPropertyImpl<VALUE> extends AbstractGenericProperty<VALUE> {

  private ExpressionHelper<VALUE> helper;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractRegularPropertyImpl(String name, GenericType<VALUE> type, Bean bean) {
    this(name, type, bean, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractRegularPropertyImpl(String name, GenericType<VALUE> type, Bean bean,
      AbstractValidator<? super VALUE> validator) {
    super(name, type, bean, validator);
  }

  @Override
  public void addListener(ChangeListener<? super VALUE> listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super VALUE> listener) {

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

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener}s and
   * {@link javafx.beans.value.ChangeListener}s.
   *
   * This method is called when the value is changed, either manually by calling {@link #setValue(Object)} or in case of
   * a bound property, if the binding becomes invalid.
   */
  @Override
  protected void fireValueChangedEvent() {

    ExpressionHelper.fireValueChangedEvent(this.helper);
  }

}
