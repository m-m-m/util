/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import net.sf.mmm.util.reflect.pojo.NlsBundlePojo;

/**
 * A {@link PojoPathSegmentIsNullException} is thrown by the
 * {@link PojoPathNavigator} if the supplied {@link PojoPathMode mode} is
 * {@link PojoPathMode#FAIL_IF_NULL} and an intermediate
 * {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathSegmentIsNullException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = 7635900006037144705L;

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link PojoPath} that could NOT be evaluated.
   * @param initialPojo is the initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo}
   *        supplied to the {@link PojoPathNavigator}.
   * @param pathToNullSegment is the part of the <code>pojoPath</code> that
   *        evaluated to <code>null</code>.
   */
  public PojoPathSegmentIsNullException(String pojoPath, Object initialPojo,
      String pathToNullSegment) {

    super(NlsBundlePojo.ERR_PATH_SEGMENT_IS_NULL, pojoPath, initialPojo, pathToNullSegment);
  }

}
