/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base;

import java.lang.ref.WeakReference;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;

/**
 * This is an implementation of {@link InvalidationListener} for
 * {@link GenericPropertyImpl#bind(javafx.beans.value.ObservableValue) binding}.
 *
 * @author hohwille
 * @since 7.1.0
 */
class BindingInvalidationListener implements InvalidationListener {

  private final WeakReference<AbstractGenericProperty<?>> weakReference;

  /**
   * The constructor.
   *
   * @param property the property to {@link GenericProperty#bind(javafx.beans.value.ObservableValue) bind}.
   */
  public BindingInvalidationListener(AbstractGenericProperty<?> property) {
    this.weakReference = new WeakReference<>(property);
  }

  @Override
  public void invalidated(Observable observable) {

    AbstractGenericProperty<?> property = this.weakReference.get();
    if (property == null) {
      observable.removeListener(this);
    } else {
      property.markInvalid();
    }
  }

}
