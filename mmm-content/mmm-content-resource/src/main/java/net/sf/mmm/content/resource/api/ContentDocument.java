/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

/**
 * This is the interface for a document of the content-repository. A document is
 * an entity of the content-model that is neither a {@link ContentFolder folder}
 * nor a {@link ContentFile file}. Such entity contains metadata and is 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentDocument extends ContentResource {

  /**
   * the name of the class reflecting {@link ContentFolder}.
   */
  String CLASS_NAME = "Document";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 23;

}
