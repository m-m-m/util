/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.link;

import net.sf.mmm.data.api.link.Link;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;

/**
 * This class contains helper methods for
 * {@link net.sf.mmm.data.api.link.LinkList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class LinkListHelper {

  /**
   * Construction prohibited.
   */
  private LinkListHelper() {

  }

  /**
   * This method adds the given <code>link</code> to the given
   * <code>linkList</code>
   * 
   * @param linkList
   * @param link
   */
  public static <TARGET extends AbstractDataEntity> void add(MutableLinkListImpl<TARGET> linkList,
      Link<TARGET> link) {

    linkList.getLinkList().add(link);
  }

}
