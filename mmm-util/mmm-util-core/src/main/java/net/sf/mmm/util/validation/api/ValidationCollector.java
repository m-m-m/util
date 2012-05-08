/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the interface for a listener that gets notified if a {@link ValidationFailure}
 * {@link #onFailure(ValidationFailure) occurred}.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public interface ValidationCollector {

  /**
   * This method gets called if a {@link ValidationFailure} occurred.
   * 
   * @param failure is the new {@link ValidationFailure}.
   */
  void onFailure(ValidationFailure failure);

}
