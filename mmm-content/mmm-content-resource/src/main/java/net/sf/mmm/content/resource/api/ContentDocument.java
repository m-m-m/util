/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

/**
 * This is the interface for a document of the content-repository. A document is
 * an entity of the content-model that is neither a {@link ContentFolder folder}
 * nor a {@link ContentFile file}. Such entity contains structured metadata and
 * is part of the custom model.<br>
 * Therefore the user can add and remove entities below this type to customize
 * the content-model. Please note that specific plugins (e.g. the multimedia
 * stuff) require that the according default entities are present. If you remove
 * default entities (declared in <code>mmm-content-entities</code>) you can
 * still use the content-repository, GUI-editor, etc. but additional plugins
 * will NOT work. Adding custom entities has no impact on the plain
 * functionality of the system.
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
