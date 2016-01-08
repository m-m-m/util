/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.List;
import java.util.function.Function;

import com.sun.javafx.binding.ListExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ListProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.collection.AbstractCollectionValidatorBuilder;
import net.sf.mmm.util.validation.base.collection.ValidatorBuilderCollection;

/**
 * This is the implementation of {@link ListProperty}.
 *
 * @param <E> the generic type of the {@link List#get(int) elements} of the {@link List}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("restriction")
public class ListPropertyImpl<E> extends GenericPropertyImpl<ObservableList<E>> implements ListProperty<E> {

  @SuppressWarnings("rawtypes")
  private static final GenericType TYPE = new SimpleGenericTypeImpl<>(ObservableList.class);

  private ListExpressionHelper<E> helper;

  /**
   * The constructor.
   */
  public ListPropertyImpl() {
    super(null, TYPE, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public ListPropertyImpl(String name, GenericType<ObservableList<E>> type, Bean bean) {
    super(name, type, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public ListPropertyImpl(String name, GenericType<ObservableList<E>> type, Bean bean,
      AbstractValidator<? super ObservableList<E>> validator) {
    super(name, type, bean, validator);
  }

  @Override
  public ListPropertyImpl<E> copy(String newName, Bean newBean,
      AbstractValidator<? super ObservableList<E>> newValidator) {

    return new ListPropertyImpl<>(newName, getType(), newBean, newValidator);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public AbstractCollectionValidatorBuilder<E, ObservableList<E>, PropertyBuilder<ListPropertyImpl<E>>, ?> withValdidator() {

    Function factory = new Function<PropertyBuilder<ListPropertyImpl<E>>, ValidatorBuilderCollection<E, PropertyBuilder<ListPropertyImpl<E>>>>() {

      @Override
      public ValidatorBuilderCollection<E, PropertyBuilder<ListPropertyImpl<E>>> apply(
          PropertyBuilder<ListPropertyImpl<E>> t) {

        return new ValidatorBuilderCollection<>(t);
      }
    };
    return (ValidatorBuilderCollection) withValdidator(factory);
  }

  @Override
  protected boolean useEqualsInternal() {

    return true;
  }

  @Override
  public void addListener(ChangeListener<? super ObservableList<E>> listener) {

    this.helper = ListExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super ObservableList<E>> listener) {

    this.helper = ListExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(InvalidationListener listener) {

    this.helper = ListExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = ListExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(ListChangeListener<? super E> listener) {

    this.helper = ListExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ListChangeListener<? super E> listener) {

    this.helper = ListExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  protected void fireValueChangedEvent() {

    ListExpressionHelper.fireValueChangedEvent(this.helper);
  }

}
