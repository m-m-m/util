/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.component.NlsBundleUtilComponentRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This is the exception thrown if an operation was invoked before the object was initialized but has to be
 * called after initialization. <br>
 * Typical this exception is thrown by a component if it was used (a method declared in the component API was
 * called) before the {@link javax.annotation.PostConstruct initialization}.
 *
 * @deprecated will be removed in some future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Deprecated
public class NotInitializedException extends NlsRuntimeException {

  private static final long serialVersionUID = -2684272742410884991L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "NotInit";

  /**
   * The constructor.
   */
  public NotInitializedException() {

    super(createBundle(NlsBundleUtilComponentRoot.class).errorNotInitialized());
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
