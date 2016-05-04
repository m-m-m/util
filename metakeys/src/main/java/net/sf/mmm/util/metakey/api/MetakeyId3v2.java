/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * This interface is a collection of constants defining keys for metadata-properties according to
 * <a href="id3.com">ID3v2</a>. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyId3v2 extends MetakeyAudioVideo {

  /**
   * TODO
   */
  String TIT1 = "Content-Group-Description";

  /**
   *
   */
  String TIT2 = TITLE;

  /**
   *
   */
  String TIT3 = SUBTITLE;

  /**
   * Contains the size in bytes of the file representing the audio, excluding the ID3v2 tag, represented as a numeric
   * string.
   */
  String TSIZ = "TSIZ";

  /**
   * The language(s) of the text or lyrics of the audio. According to ID3 the language is represented with three
   * characters according to ISO-639-2 while {@link #LANGUAGE} is defined as single language in ISO-639-1. If more than
   * one language is used in the text their language codes should follow according to their usage.
   */
  String TLAN = LANGUAGE;

  /**
   * TODO
   */
  String TLEN = DURATION;

  /**
   *
   */
  String TALB = "ALBUM";

  /**
   *
   */
  String TCOM = "Composer";

  /**
   *
   */
  String TCON = "Content-Type";

  /**
   *
   */
  String TBPM = BPM;

  String TPE1 = "ARTIST";

  String TPE2 = "Additional-Info-About-Artist";

  /**
   * Contributor/Director
   */
  String TPE3 = "CONTRIBUTOR";

  /**
   * Contains more information about the people behind a remix and similar interpretations of another existing piece.
   */
  String TPE4 = "remixer";

  /**
   * The 'Part Of a Set' frame.
   *
   * @see #MEDIA
   */
  String TPOS = MEDIA;

  /**
   * The {@link #PUBLISHER} (e.g. label).
   */
  String TPUB = PUBLISHER;

  /**
   * Contains a numeric string with a year of the recording. This frames is typically four characters long (e.g. 1999 or
   * 2011).
   */
  String TYER = "year";

}
