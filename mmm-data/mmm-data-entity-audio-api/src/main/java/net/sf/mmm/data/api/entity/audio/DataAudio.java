/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.audio;

import java.util.Locale;
import java.util.Set;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.music.MusicalKey;

/**
 * This interface is a {@link DataEntity} that represents an audio track.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAudio.CLASS_ID, title = DataAudio.CLASS_TITLE)
public interface DataAudio extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_AUDIO;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataAudio";

  /**
   * This method gets the (main) {@link MusicalKey} of this audio track.
   * 
   * @return the {@link MusicalKey} or <code>null</code> if undefined (unknown, no music at all, not diatonic,
   *         etc.)
   */
  MusicalKey getKey();

  /**
   * This method gets the {@link Locale} representing the primary language of this audio track (for the
   * lyrics).
   * 
   * @return the {@link Locale} representing the primary language or <code>null</code> for an audio track
   *         without human language (e.g. an instrumental). The default is {@link Locale#ROOT} indicating that
   *         the language is unknown or artificial.
   */
  Locale getPrimaryLanguage();

  /**
   * This method gets the {@link Locale} representing the secondary language of this audio track. E.g. for the
   * song "Time to say goodbye" from "Andrea Bocelli" the {@link #getPrimaryLanguage() primary language} would
   * be Italian ("it"), and the {@link #getSecondaryLanguage() secondary language} English ("en").
   * 
   * @return the {@link Locale} representing the secondary language or <code>null</code> if there is no
   *         secondary language involved (default).
   */
  Locale getSecondaryLanguage();

  /**
   * This method gets the tags associated with this audio track. A <em>tag</em> is an arbitrary {@link String}
   * representing some attribute of the track. It is recommended to use English and well established terms as
   * tags. However, you can use whatever fits for your personal interest. Examples for tags are "religious",
   * "fun", "sad", etc.<br/>
   * <b>NOTE:</b><br/>
   * Please avoid adding tags that are redundant to explicit fields of an {@link DataAudio audio track} like
   * {@link #getGenre() genre}, {@link #getPrimaryLanguage() language}, instrumental, etc.
   * 
   * @return the {@link Set} of tags. Empty {@link Set} by default.
   */
  Set<String> getTags();

  /**
   * This method gets the {@link DataArtist artist} of this audio track.
   * 
   * @return the {@link DataArtist} or <code>null</code> if undefined (unknown).
   */
  DataArtist getArtist();

  /**
   * This method sets the {@link #getArtist() artist}.
   * 
   * @param artist is the {@link DataArtist} to set.
   */
  void setArtist(DataArtist artist);

  /**
   * This method gets the {@link DataAudioGenre genre} of this audio track.
   * 
   * @return the {@link DataAudioGenre} or <code>null</code> if undefined (unknown).
   */
  DataAudioGenre getGenre();

  /**
   * This method sets the {@link #getGenre() genre}.
   * 
   * @param genre is the {@link DataAudioGenre} to set.
   */
  void setGenre(DataAudioGenre genre);
}
