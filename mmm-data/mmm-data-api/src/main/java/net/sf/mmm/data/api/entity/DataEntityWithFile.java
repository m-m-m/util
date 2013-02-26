/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity;

import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;

/**
 * This is the view interface for a {@link DataEntity} that {@link #getFile() has} a {@link DataFile file}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityWithFile.CLASS_ID, title = DataEntityWithFile.CLASS_TITLE)
public interface DataEntityWithFile extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_ENTITYWITHFILE;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataEntityWithFile";

  /**
   * This method gets the associated file.
   * 
   * @return the {@link DataFile}.
   */
  DataFile getFile();

}
