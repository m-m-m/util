/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

/**
 * This is the call-back interface used to
 * {@link #recognize(Object, PojoPath) recognize}
 * {@link net.sf.mmm.util.pojo.api.Pojo}s traversed by a
 * {@link PojoPathNavigator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathRecognizer {

  /**
   * This method is called to recognize the <code>actualPojo</code>. It is
   * called by the {@link PojoPathNavigator} (an {@link PojoPathFunction}s) for
   * each {@link net.sf.mmm.util.pojo.api.Pojo} that has been traversed.
   * 
   * @param actualPojo is the actual
   *        {@link net.sf.mmm.util.pojo.api.Pojo} to recognize.
   * @param currentPath is the {@link PojoPath} that lead to the
   *        <code>actualPojo</code>.
   */
  void recognize(Object actualPojo, PojoPath currentPath);

}
