/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.DataSelectionTree;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;

/**
 * This is the interface for a <em>resource</em>. A resource is a
 * {@link net.sf.mmm.data.api.entity.DataEntity} that represents a node in a
 * repository resource tree (like a filesystem). <br>
 * A {@link DataEntityResource} is either a {@link DataFolder} or a
 * {@link DataFile}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityResource.CLASS_ID)
public interface DataEntityResource extends DataEntity, DataSelectionTree<DataEntityResource> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
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
  DataFolder getParent();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getTitle() title} of this resource.
   * 
   * @return the path of the resource.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_RESOURCE_PATH, isTransient = true, isFinal = true)
  String getPath();

}
