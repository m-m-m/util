/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.entity.pim.contact.DataPerson;
import net.sf.mmm.data.api.link.MutableLinkList;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataArtistGroup extends DataArtistGroupView {

  /**
   * {@inheritDoc}
   */
  MutableLinkList<DataPerson> getArtists();

}
