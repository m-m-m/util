/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyMusic extends MetakeyAudioVideo {

  /**
   * TODO: Name (Company)... Brand of the producing studio.
   */
  String LABEL = PUBLISHER;

  /**
   * The artist(s) which performed this musical content.
   */
  String ARTIST = "artist";

  /**
   * The composer of the musical content if it differs from the {@link #ARTIST}.
   */
  String COMPOSER = "composer";

  /**
   * The album (CD) containing this musical content.<br>
   * <b>ATTENTION:</b><br>
   * This property does NOT actually belong to the metadata of the content
   * itself. The exact same song may be present on different CDs. Therefore this
   * is an N:M relation from the album to the song.
   */
  String ALBUM = "album";

}
