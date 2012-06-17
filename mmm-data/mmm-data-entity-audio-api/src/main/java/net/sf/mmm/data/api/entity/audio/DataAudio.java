/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataAudio extends DataAudioView {

  /**
   * {@inheritDoc}
   */
  DataArtist getArtist();

  /**
   * This method sets the {@link #getArtist() artist}.
   * 
   * @param artist is the {@link DataArtist} to set.
   */
  void setArtist(DataArtist artist);

  /**
   * {@inheritDoc}
   */
  DataAudioGenre getGenre();

  /**
   * This method sets the {@link #getGenre() genre}.
   * 
   * @param genre is the {@link DataAudioGenre} to set.
   */
  void setGenre(DataAudioGenre genre);
}
