/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.DataNodeView;
import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a <em>resource</em>. A resource is a
 * {@link net.sf.mmm.data.api.entity.DataEntityView} that represents a node in a
 * repository resource tree (like a filesystem). <br/>
 * A {@link DataEntityResourceView resource} is either a {@link DataFolderView
 * folder} or a {@link DataFileView file}.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityResourceView.CLASS_ID, title = DataEntityResourceView.CLASS_TITLE, isExtendable = BooleanEnum.FALSE)
public abstract interface DataEntityResourceView extends DataEntityView,
    DataNodeView<DataFolderView> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_ENTITYRESOURCE;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataEntityResource";

  /**
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * This method gets the parent folder of this resource.
   * 
   * {@inheritDoc}
   */
  DataFolderView getParent();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getTitle() title} of this resource.
   * 
   * @return the path of the resource.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_RESOURCE_PATH, isTransient = BooleanEnum.TRUE, isFinal = BooleanEnum.TRUE)
  String getPath();

  /**
   * This method gets the name of this file or folder.<br/>
   * <b>ATTENTION:</b><br/>
   * The title of a resource has to unique for all resources with the same
   * {@link #getParent() parent}. In other words it is prohibited that a
   * {@link DataFolderView} has two {@link DataFolderView#getChildren()
   * children} with the same {@link #getTitle() title}.<br/>
   * 
   * {@inheritDoc}
   */
  String getTitle();

}
