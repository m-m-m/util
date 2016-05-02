/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This {@link NlsBundleOptions#requireMessages() required} annotation is used to define the
 * {@link NlsMessage#getInternationalizedMessage() internationalized message} for a method of an
 * {@link NlsBundle} interface. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NlsBundleMessage {

  /**
   * The {@link NlsMessage#getInternationalizedMessage() message} for the {@link NlsMessage message} defined
   * by the annotated method.
   */
  String value();
}
