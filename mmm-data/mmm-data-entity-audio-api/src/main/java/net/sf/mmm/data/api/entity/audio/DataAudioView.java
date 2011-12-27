/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAudioView.CLASS_ID, title = DataAudioView.CLASS_TITLE)
public interface DataAudioView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_AUDIO;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataAudio";

  /**
   * This method gets the {@link DataArtistView artist} of this audio track.
   * 
   * @return the {@link DataArtistView} or <code>null</code> if undefined
   *         (unknown).
   */
  DataArtistView getArtist();

  /**
   * This method gets the {@link DataAudioGenreView genre} of this audio track.
   * 
   * @return the {@link DataAudioGenreView} or <code>null</code> if undefined
   *         (unknown).
   */
  DataAudioGenreView getGenre();

}
