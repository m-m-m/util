/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base;

import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;

/**
 * This is the abstract base implementation for a dynamic {@link BooleanBinding}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class AbstractBooleanBinding extends BooleanBinding {

  private final Observable[] dependencies;

  /**
   * The constructor.
   *
   * @param dependencies are the {@link #getDependencies() dependencies}.
   */
  public AbstractBooleanBinding(Observable... dependencies) {
    super();
    this.dependencies = dependencies;
    bind(dependencies);
  }

  @Override
  public void dispose() {

    super.dispose();
    unbind(this.dependencies);
  }

  @Override
  public ObservableList<?> getDependencies() {

    return new ImmutableObservableList<>(this.dependencies);
  }

}
