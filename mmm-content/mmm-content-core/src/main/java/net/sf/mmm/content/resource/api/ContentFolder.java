/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

/**
 * This is the interface for the regular
 * {@link net.sf.mmm.content.api.ContentObject#isFolder() folder}-{@link ContentResource resource}.
 * Like a folder in the local filesystem, this is a folder in the repository.<br>
 * <b>ATTENTION:</b><br>
 * Please note that any other
 * {@link net.sf.mmm.content.api.ContentObject entity} may also be a
 * {@link net.sf.mmm.content.api.ContentObject#isFolder() folder}. So you need
 * to use {@link #isFolder()} when
 * {@link ContentResource#getChildren() browsing} rather than checking if a
 * child is a {@link ContentFolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentFolder extends ContentResource {

  /**
   * the name of the class reflecting {@link ContentFolder}.
   */
  String CLASS_NAME = "ContentFolder";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 21;

  /**
   * The {@link net.sf.mmm.content.api.ContentObject#getName() name} of the
   * root-{@link ContentFolder folder}.
   */
  String NAME_ROOT = "";

  /** The path of the root-{@link ContentFolder folder} */
  String PATH_ROOT = PATH_SEPARATOR;

  /**
   * {@inheritDoc}
   */
  ContentFolder getParent();

}
