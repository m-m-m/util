/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import net.sf.mmm.util.reflect.pojo.NlsBundlePojo;

/**
 * An {@link IllegalPojoPathException} is thrown by the
 * {@link PojoPathNavigator} if the supplied {@link PojoPath} is illegal.<br>
 * Here are some examples of illegal POJO-paths:
 * <ul>
 * <li><code>null</code></li>
 * <li><code>""</code></li>
 * <li><code>"."</code></li>
 * <li><code>".foo"</code></li>
 * <li><code>"bar."</code></li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalPojoPathException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -221363144035352042L;

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link PojoPathNavigator POJO-path} that is illegal.
   */
  public IllegalPojoPathException(String pojoPath) {

    super(NlsBundlePojo.ERR_ILLEGAL_PATH, pojoPath);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param pojoPath is the {@link PojoPathNavigator POJO-path} that is illegal.
   */
  public IllegalPojoPathException(String pojoPath, Throwable nested) {

    super(nested, NlsBundlePojo.ERR_ILLEGAL_PATH, pojoPath);
  }

}
