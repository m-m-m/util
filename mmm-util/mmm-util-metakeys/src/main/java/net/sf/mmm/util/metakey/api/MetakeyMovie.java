/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyMovie extends MetakeyAudioVideo {

  String COMPANY = "company";

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
