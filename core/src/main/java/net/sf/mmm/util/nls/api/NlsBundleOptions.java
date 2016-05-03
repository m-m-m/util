/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This optional annotation is used to define a different name for an {@link NlsBundle} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NlsBundleOptions {

  /**
   * Flag indicating if the {@link NlsBundleMessage} annotation has to be present for all methods of the annotated
   * {@link NlsBundle}. The default is {@code true} to ensure the message is always present.
   */
  boolean requireMessages() default false;

  /**
   * Flag indicating if the ResourceBundle is productive. Set to {@code false} for bundles that are used for
   * testing only and should be ignored e.g. by ResourceBundleSynchronizer.
   *
   * @since 7.0.0
   */
  boolean productive() default true;

}
