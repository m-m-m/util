/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.pojo.NlsBundleUtilPojoRoot;

/**
 * A {@link PojoPathUnsafeException} is thrown by the {@link PojoPathNavigator} if the supplied {@link PojoPathMode
 * mode} was {@link PojoPathMode#FAIL_IF_NULL} and the {@link PojoPath} was <em>unsafe</em>.
 *
 * @see PojoPathNavigator#getType(net.sf.mmm.util.reflect.api.GenericType, String, boolean, PojoPathContext)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathUnsafeException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7485880426331604481L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "PojoPathUnsafe";

  /**
   * The constructor.
   *
   * @param initialPojoType is the initial {@link net.sf.mmm.util.pojo.api.Pojo} -type supplied to the
   *        {@link PojoPathNavigator}.
   * @param pojoPath is the {@link PojoPath} that is unsafe.
   */
  public PojoPathUnsafeException(Object initialPojoType, String pojoPath) {

    super(createBundle(NlsBundleUtilPojoRoot.class).errorPojoPathUnsafe(pojoPath, initialPojoType));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
