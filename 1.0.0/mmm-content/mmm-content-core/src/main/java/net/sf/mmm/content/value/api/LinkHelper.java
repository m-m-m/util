/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.content.api.ContentObject;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class LinkHelper {
  
  /**
   * The constructor.
   */
  private LinkHelper() {

  }

  
  
  public <E extends ContentObject> List<E> resolve(List<Link<E>> linklist) {

    List<E> result = new ArrayList<E>();
    return result;
  }

  public <E extends ContentObject> List<E> resolve(List<Link<E>> linklist, LinkFilter filter) {

    return null;
  }

}
