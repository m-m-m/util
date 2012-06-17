/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This optional annotation is used to define a different name for an {@link NlsBundle} interface.
 * 
 * @author hohwille
 * @since 3.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NlsBundleOptions {

  /**
   * Flag indicating if the {@link NlsBundleMessage} annotation has to be present for all methods of the
   * annotated {@link NlsBundle}. The default is <code>true</code> to ensure the message is always present.
   */
  boolean requireMessages() default false;

}
