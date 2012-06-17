/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This optional annotation is used to define a different location for the {@link java.util.ResourceBundle}
 * -properties of an {@link NlsBundle} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NlsBundleLocation {

  /**
   * The package of the {@link NlsBundle}. The default (empty string) will be replaced with the
   * {@link Class#getPackage() package} of the annotated interface.
   */
  String bundlePackage() default "";

  /**
   * The name of the {@link NlsBundle}. The default (empty string) will be replaced with the
   * {@link Class#getSimpleName() name} of the annotated interface.
   */
  String bundleName() default "";

}
