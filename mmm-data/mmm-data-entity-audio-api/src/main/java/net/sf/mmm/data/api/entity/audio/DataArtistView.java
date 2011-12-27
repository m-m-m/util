/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.entity.pim.contact.DataPersonView;
import net.sf.mmm.data.api.link.LinkList;

/**
 * This is the view interface for an
 * {@link net.sf.mmm.data.api.entity.DataEntityView entity} that represents the
 * {@link DataAudioView#getArtist() artist} of an {@link DataAudioView audio
 * track}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public abstract interface DataArtistView {

  /**
   * This method gets the members of this {@link DataArtistView}. This can be an
   * entire list of persons if this {@link DataArtistView} represents a group
   * (typically called a band). Each member can be
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classified} with his
   * role (e.g. "drums", "piano", "keyboard", "guitar", "trumpet") if desired.
   * If this is NOT a group, the {@link LinkList} should contain a single
   * {@link net.sf.mmm.data.api.link.Link} with no
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @return the members.
   */
  LinkList<? extends DataPersonView> getMembers();

  /**
   * This method gets the default {@link DataAudioGenreView genre} of this
   * artist. This is used to initialize or suggest the
   * {@link DataAudioView#getGenre() genre} of individual {@link DataArtistView
   * audio tracks} of this artist.
   * 
   * @return the default genre, or <code>null</code> if undefined.
   */
  DataAudioGenreView getDefaultGenre();

}
