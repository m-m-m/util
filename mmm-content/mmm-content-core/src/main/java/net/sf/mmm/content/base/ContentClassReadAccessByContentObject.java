/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassReadAccessByContentObject {

  /**
   * This method determines the
   * {@link ContentObject#getContentClass() content-class} of the given
   * <code>contentObject</code>.<br>
   * <b>ATTENTION:</b><br>
   * It is used to realize the method {@link ContentObject#getContentClass()}
   * itself so that method must NOT be used by the implementation.
   * 
   * @param contentObject is the object for which the content-class is
   *        requested.
   * @return the content-class of the given <code>contentObject</code>.
   */
  ContentClass getContentClass(AbstractContentObject contentObject);

}
