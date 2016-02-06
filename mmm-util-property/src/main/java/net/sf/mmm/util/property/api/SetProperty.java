/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.function.Function;

import com.sun.javafx.binding.SetExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.collection.AbstractCollectionValidatorBuilder;
import net.sf.mmm.util.validation.base.collection.ValidatorBuilderCollection;

/**
 * This is the implementation of {@link WritableSetProperty}.
 *
 * @param <E> the generic type of the {@link ObservableSet#contains(Object) elements} of the {@link ObservableSet}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@SuppressWarnings("restriction")
public class SetProperty<E> extends AbstractContainerProperty<ObservableSet<E>> implements WritableSetProperty<E> {

  @SuppressWarnings("rawtypes")
  private static final GenericType TYPE = new SimpleGenericTypeImpl<>(ObservableSet.class);

  private final SetChangeListener<E> changeListener = change -> {
    invalidateProperties();
    invalidated();
    fireValueChangedEvent(change);
  };

  private SetExpressionHelper<E> helper;

  private ObservableSet<E> value;

  /**
   * The constructor.
   */
  public SetProperty() {
    super(null, TYPE, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public SetProperty(String name, GenericType<ObservableSet<E>> type, Bean bean) {
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
  public SetProperty(String name, GenericType<ObservableSet<E>> type, Bean bean,
      AbstractValidator<? super ObservableSet<E>> validator) {
    super(name, type, bean, validator);
  }

  @Override
  protected ObservableSet<E> doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(ObservableSet<E> newValue) {

    if (this.value == newValue) {
      return false;
    }
    if (this.value != null) {
      this.value.removeListener(this.changeListener);
    }
    this.value = newValue;
    if (this.value != null) {
      this.value.addListener(this.changeListener);
    }
    return true;
  }

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener InvalidationListeners},
   * {@link javafx.beans.value.ChangeListener ChangeListeners}, and {@link javafx.collections.SetChangeListener}.
   *
   * This method is called when the content of the list changes.
   *
   * @param change the change that needs to be propagated
   */
  protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {

    SetExpressionHelper.fireValueChangedEvent(this.helper, change);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public AbstractCollectionValidatorBuilder<E, ObservableSet<E>, PropertyBuilder<SetProperty<E>>, ?> withValdidator() {

    Function factory = new Function<PropertyBuilder<SetProperty<E>>, ValidatorBuilderCollection<E, PropertyBuilder<SetProperty<E>>>>() {

      @Override
      public ValidatorBuilderCollection<E, PropertyBuilder<SetProperty<E>>> apply(
          PropertyBuilder<SetProperty<E>> t) {

        return new ValidatorBuilderCollection<>(t);
      }
    };
    return (ValidatorBuilderCollection) withValdidator(factory);
  }

  @Override
  public void addListener(ChangeListener<? super ObservableSet<E>> listener) {

    this.helper = SetExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {

    this.helper = SetExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(InvalidationListener listener) {

    this.helper = SetExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = SetExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public void addListener(SetChangeListener<? super E> listener) {

    this.helper = SetExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(SetChangeListener<? super E> listener) {

    this.helper = SetExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  protected void fireValueChangedEvent() {

    SetExpressionHelper.fireValueChangedEvent(this.helper);
  }

}
