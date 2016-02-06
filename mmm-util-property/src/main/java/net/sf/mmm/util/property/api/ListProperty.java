/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.List;
import java.util.function.Function;

import com.sun.javafx.binding.ListExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.collection.AbstractCollectionValidatorBuilder;
import net.sf.mmm.util.validation.base.collection.ValidatorBuilderCollection;

/**
 * This is the implementation of {@link WritableListProperty}.
 *
 * @param <E> the generic type of the {@link List#get(int) elements} of the {@link List}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@SuppressWarnings("restriction")
public class ListProperty<E> extends AbstractContainerProperty<ObservableList<E>>
    implements WritableListProperty<E> {

  @SuppressWarnings("rawtypes")
  private static final GenericType TYPE = new SimpleGenericTypeImpl<>(ObservableList.class);

  private final ListChangeListener<E> listChangeListener = change -> {
    invalidateProperties();
    invalidated();
    fireValueChangedEvent(change);
  };

  private ListExpressionHelper<E> helper;

  private ObservableList<E> value;

  /**
   * The constructor.
   */
  public ListProperty() {
    super(null, TYPE, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public ListProperty(String name, GenericType<ObservableList<E>> type, Bean bean) {
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
  public ListProperty(String name, GenericType<ObservableList<E>> type, Bean bean,
      AbstractValidator<? super ObservableList<E>> validator) {
    super(name, type, bean, validator);
  }

  @Override
  protected ObservableList<E> doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(ObservableList<E> newValue) {

    if (this.value == newValue) {
      return false;
    }
    if (this.value != null) {
      this.value.removeListener(this.listChangeListener);
    }
    this.value = newValue;
    if (this.value != null) {
      this.value.addListener(this.listChangeListener);
    }
    return true;
  }

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener InvalidationListeners},
   * {@link javafx.beans.value.ChangeListener ChangeListeners}, and {@link javafx.collections.ListChangeListener}.
   *
   * This method is called when the content of the list changes.
   *
   * @param change the change that needs to be propagated
   */
  protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> change) {

    ListExpressionHelper.fireValueChangedEvent(this.helper, change);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public AbstractCollectionValidatorBuilder<E, ObservableList<E>, PropertyBuilder<ListProperty<E>>, ?> withValdidator() {

    Function factory = new Function<PropertyBuilder<ListProperty<E>>, ValidatorBuilderCollection<E, PropertyBuilder<ListProperty<E>>>>() {

      @Override
      public ValidatorBuilderCollection<E, PropertyBuilder<ListProperty<E>>> apply(
          PropertyBuilder<ListProperty<E>> t) {

        return new ValidatorBuilderCollection<>(t);
      }
    };
    return (ValidatorBuilderCollection) withValdidator(factory);
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
