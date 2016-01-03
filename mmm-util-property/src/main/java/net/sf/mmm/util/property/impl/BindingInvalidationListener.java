/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.lang.ref.WeakReference;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * This is an implementation of {@link InvalidationListener} for
 * {@link GenericPropertyImpl#bind(javafx.beans.value.ObservableValue) binding}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BindingInvalidationListener implements InvalidationListener {

  private final WeakReference<GenericPropertyImpl<?>> weakReference;

  /**
   * The constructor.
   *
   * @param property the property to {@link GenericPropertyImpl#bind(javafx.beans.value.ObservableValue) bind}.
   */
  public BindingInvalidationListener(GenericPropertyImpl<?> property) {
    this.weakReference = new WeakReference<>(property);
  }

  @Override
  public void invalidated(Observable observable) {

    GenericPropertyImpl<?> property = this.weakReference.get();
    if (property == null) {
      observable.removeListener(this);
    } else {
      property.markInvalid();
    }
  }

}
