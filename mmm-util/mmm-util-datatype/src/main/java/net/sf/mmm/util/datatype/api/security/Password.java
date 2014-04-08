/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.security;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.lang.api.concern.Security;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} used to store a simple password as {@link String}.
 * Its {@link #toString() string representation} is a fixed {@link String} (********) in order to prevent
 * accidental logging or other spreading of passwords. The actually get the password as {@link String} use
 * {@link #getValue()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Password extends AbstractSimpleDatatype<String> implements Security {

  /** UID for serialization. */
  private static final long serialVersionUID = 2487698739503641538L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected Password() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the actual password.
   */
  public Password(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "********";
  }

}
