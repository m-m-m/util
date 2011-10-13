/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.entity.api;

import net.sf.mmm.data.api.ContentClassAnnotation;
import net.sf.mmm.data.api.ContentFieldAnnotation;
import net.sf.mmm.data.api.ContentFieldIds;
import net.sf.mmm.data.api.ContentSelectionTree;

/**
 * This is the interface for a folder.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentResource.CLASS_ID)
public interface ContentResource extends ContentEntity, ContentSelectionTree<ContentResource> {

  /**
   * The {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.data.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 11;

  /**
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * This method gets the parent folder of this resource.
   * 
   * {@inheritDoc}
   */
  ContentFolder getParent();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getTitle() title} of this resource.
   * 
   * @return the path of the resource.
   */
  @ContentFieldAnnotation(id = ContentFieldIds.ID_RESOURCE_PATH, isTransient = true, isFinal = true)
  String getPath();

}
