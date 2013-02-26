/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.collection.api.Node;

/**
 * This is the interface for a {@link DataObject} that has a {@link #getParent() parent}.
 * 
 * @param <PARENT> is the generic type representing the {@link #getParent() parent}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataNode.CLASS_ID, title = DataNode.CLASS_TITLE)
public interface DataNode<PARENT extends DataObject> extends DataObject, Node<PARENT> {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_NODE;

  /**
   * The {@link DataObject#getTitle() title} of the {@link net.sf.mmm.data.api.reflection.DataClass}
   * reflecting this type.
   */
  String CLASS_TITLE = "DataNode";

  /**
   * This method gets the parent node of this selection entry. If the parent is NOT set (<code>null</code>),
   * this entry will not be selectable.
   * 
   * @return the parent node. May be <code>null</code>.
   */
  @Override
  @DataFieldAnnotation(id = DataFieldIds.ID_NODE_PARENT)
  PARENT getParent();

  /**
   * This method sets the {@link #getParent() parent}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method should be called with care. If possible use <code>move</code> methods of
   * {@link net.sf.mmm.data.api.repository.DataRepository} instead.
   * 
   * @param parent is the parent to set.
   */
  void setParent(PARENT parent);

}
