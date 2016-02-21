/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.pojo.NlsBundleUtilPojoRoot;

/**
 * A {@link PojoPathSegmentIsNullException} is thrown by the {@link PojoPathNavigator} if the supplied
 * {@link PojoPathMode mode} is {@link PojoPathMode#FAIL_IF_NULL} and an intermediate
 * {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathSegmentIsNullException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = 7635900006037144705L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "PojoPathSegmentNull";

  /**
   * The constructor.
   *
   * @param initialPojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} supplied to the {@link PojoPathNavigator}.
   * @param pojoPath is the {@link PojoPath} that evaluated to <code>null</code> .
   */
  public PojoPathSegmentIsNullException(Object initialPojo, String pojoPath) {

    super(createBundle(NlsBundleUtilPojoRoot.class).errorPojoPathSegmentIsNull(pojoPath, initialPojo));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
