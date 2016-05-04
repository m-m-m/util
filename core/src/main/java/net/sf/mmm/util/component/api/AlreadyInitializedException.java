/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.component.NlsBundleUtilComponentRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This is the exception thrown if an operation was invoked after the object was initialized but has to be called before
 * initialization. <br>
 * Typical this exception is thrown by a component if a setter {@link javax.inject.Inject injection} is performed after
 * the {@link javax.annotation.PostConstruct initialization}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AlreadyInitializedException extends NlsRuntimeException {

  private static final long serialVersionUID = -2011255277429741541L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "AlreadyInit";

  /**
   * The constructor.
   */
  public AlreadyInitializedException() {

    super(createBundle(NlsBundleUtilComponentRoot.class).errorAlreadyInitialized());
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
