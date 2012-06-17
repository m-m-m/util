/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This optional annotation is used to define a different {@link java.util.ResourceBundle#containsKey(String)
 * key} for a method of an {@link NlsBundle} interface. If NOT present, the
 * {@link java.lang.reflect.Method#getName() name of the method} is used as key.
 * 
 * @author hohwille
 * @since 3.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NlsBundleKey {

  /**
   * The {@link java.util.ResourceBundle#getString(String) key} for the annotated method.
   */
  String value();
}
