/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.pojo.NlsBundleUtilPojoRoot;

/**
 * A {@link PojoPathCreationException} is thrown by the {@link PojoPathNavigator} if the supplied {@link PojoPathMode
 * mode} is {@link PojoPathMode#CREATE_IF_NULL} and an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
 * <code>null</code> but could NOT be created. <br>
 * This can happen because the {@link net.sf.mmm.util.pojo.api.PojoFactory#newInstance(Class) instantiation} failed or
 * there is insufficient information about what to create (e.g. the current object is a {@link java.util.Map} was NOT
 * accessed via an generic method signature).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathCreationException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = 2315013832458974625L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "PojoPathCreate";

  /**
   * The constructor.
   *
   * @param pojoPath is the {@link PojoPath} that evaluated to <code>null</code> but could NOT be created.
   * @param initialPojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} supplied to the {@link PojoPathNavigator}.
   */
  public PojoPathCreationException(Object initialPojo, String pojoPath) {

    this(null, initialPojo, pojoPath);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param initialPojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} supplied to the {@link PojoPathNavigator}.
   * @param pojoPath is the {@link PojoPath} that evaluated to <code>null</code> but could NOT be created.
   */
  public PojoPathCreationException(Throwable nested, Object initialPojo, String pojoPath) {

    super(nested, createBundle(NlsBundleUtilPojoRoot.class).errorPojoPathCreation(pojoPath, initialPojo));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
