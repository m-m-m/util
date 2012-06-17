/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * This interface is a collection of constants defining keys for
 * metadata-properties of multimedia content.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyMultimedia extends MetakeyCore {

  /**
   * The genre of the audio/video content. E.g. "Blues" or "Thriller".
   */
  String GENRE = "genre";

  /**
   * The name of the producer(s) of the audio/video content.
   */
  String PRODUCER = "producer";

  /**
   * The name of the publisher, label or company that published the content.
   */
  String PUBLISHER = "publisher";

  /**
   * The number of the media if the content belongs to a whole that is divided
   * into several media (e.g. several CDs, DVDs or BRs). It is the number of the
   * media that contains the content and may be followed by a slash and the
   * maximum number of medias (e.g. "1/2").
   */
  String MEDIA = "media";

  /**
   * The track-number of the content (e.g. index on a CD).
   */
  String TRACK = "track";

}
