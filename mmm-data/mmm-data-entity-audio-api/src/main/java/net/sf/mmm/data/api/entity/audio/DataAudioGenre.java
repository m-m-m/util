/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import net.sf.mmm.data.api.DataSelectionTree;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataAudioGenreView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAudioGenreView.CLASS_ID, title = DataAudioGenreView.CLASS_TITLE)
public interface DataAudioGenre extends DataAudioGenreView,
    DataSelectionTree<DataAudioGenreView, DataAudioGenre> {

  /**
   * This method sets the {@link #getId3Genre() CDDB genre ID} of this genre.
   * 
   * @param cddbGenreId is the {@link #getId3Genre() CDDB genre ID} to set.
   */
  void setId3Genre(Integer cddbGenreId);

}
