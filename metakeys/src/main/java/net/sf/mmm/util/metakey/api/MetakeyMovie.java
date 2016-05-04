/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * This interface is a collection of constants defining keys for metadata-properties of a movie (video content). <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyMovie extends MetakeyAudioVideo {

  /**
   * The company "responsible for the movie".
   */
  String COMPANY = "company";

  /**
   * The country where the movie was produced.
   */
  String COUNTRY = "country";

  /**
   * The director of the movie.
   */
  String DIRECTOR = "director";

  /**
   * The name of the actors playing the movie.
   */
  String ACTOR = "actor";

}
