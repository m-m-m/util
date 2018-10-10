/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the interface for a listener that gets notified if a {@link ValidationFailure}
 * {@link #onFailure(ValidationFailure) occurred}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @deprecated see {@link ValidationState}
 */
@Deprecated
public interface ValidationCollector {

  /**
   * This method gets called if a {@link ValidationFailure} occurred.
   *
   * @param failure is the new {@link ValidationFailure}.
   */
  void onFailure(ValidationFailure failure);

  /**
   * @return the number of {@link ValidationFailure}s that have been {@link #onFailure(ValidationFailure) collected}.
   * @since 3.1.0
   */
  int getFailureCount();

}
