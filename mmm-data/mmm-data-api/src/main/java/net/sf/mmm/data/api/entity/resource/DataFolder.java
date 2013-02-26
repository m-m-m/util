/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.DataSelectionGenericTree;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataEntityResource} that represents a <em>folder</em>. A folder is a
 * collection of other {@link DataEntityResource resources}.<br/>
 * See {@link net.sf.mmm.data.api.repository.DataRepository#move(DataEntityResource, DataFolder)} for details
 * how to add or remove {@link #getChildren() children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFolder.CLASS_ID, title = DataFolder.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataFolder extends DataEntityResource, DataSelectionGenericTree<DataEntityResource, DataFolder> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_FOLDER;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataFolder";

  /** The id of the root-folder. */
  long FOLDER_ID_ROOT = 2;

  /**
   * This method gets the {@link #getChildren() child} with the given <code>title</code>.
   * 
   * @param title is the {@link DataEntityResource#getTitle() title} of the requested child.
   * @return the child with the given <code>title</code> or <code>null</code> if no such child exists.
   */
  DataEntityResource getChild(String title);

}
