/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * This interface is a collection of constants defining keys for
 * metadata-properties of audio/video content.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyAudioVideo extends MetakeyMultimedia {

  /**
   * The duration of the audio/video content.<br>
   * <b>ATTENTION:</b><br>
   * The value of this property is of the type {@link Long} and represents the
   * duration in milliseconds.
   */
  String DURATION = "duration";

  /**
   * The number of Beats Per Minute in the main-part of the audio.<br>
   * <b>ATTENTION:</b><br>
   * The value of this property is of the type {@link Integer}.
   */
  String BPM = "bpm";

  /**
   * The musical key the audio starts with. The ground key is represented with
   * the according capital letter ("A"-"G") and a half key is indicated by "b"
   * or "#". A minor key is indicated by a lower case "m". For instance
   * Cis-Minor is represented as "C#m".
   */
  String KEY = "key";

  /**
   * The genre of the audio/video content. E.g. "Blues" or "Thriller".
   */
  String GENRE = "genre";

  /**
   * The name of the producer(s) of the audio/video content.
   */
  String PRODUCER = "producer";

}
