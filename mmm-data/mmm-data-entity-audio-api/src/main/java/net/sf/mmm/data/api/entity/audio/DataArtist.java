/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.pim.contact.DataPerson;
import net.sf.mmm.data.api.link.MutableLinkList;

/**
 * This is the interface for an {@link net.sf.mmm.data.api.entity.DataEntity} that represents the
 * {@link DataAudio#getArtist() artist} of an {@link DataAudio audio track}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataArtist extends DataEntity {

  /**
   * This method gets the members of this {@link DataArtist}. This can be an entire list of persons if this
   * {@link DataArtist} represents a group (typically called a band). Each member can be
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classified} with his role (e.g. "drums", "piano",
   * "keyboard", "guitar", "trumpet") if desired. If this is NOT a group, the
   * {@link net.sf.mmm.data.api.link.LinkList} should contain a single {@link net.sf.mmm.data.api.link.Link}
   * with no {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @return the members.
   */
  MutableLinkList<DataPerson> getMembers();

  /**
   * This method gets the default {@link DataAudioGenre genre} of this artist. This is used to initialize or
   * suggest the {@link DataAudio#getGenre() genre} of individual {@link DataArtist audio tracks} of this
   * artist.
   * 
   * @return the default genre, or <code>null</code> if undefined.
   */
  DataAudioGenre getDefaultGenre();

}
