/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataObject} that acts as selection. Such
 * selection will allow to choose out of all instances of this
 * {@link net.sf.mmm.data.api.reflection.DataClass type}. This is used to
 * determine how end users choose an instance to link from another
 * {@link DataObject}.
 * 
 * TODO: remove this interface and move isCacheable() to DataClass and
 * DataClassAnnotation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionView.CLASS_ID, title = DataSelectionView.CLASS_TITLE)
public abstract interface DataSelectionView extends DataObjectView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 3;

  /**
   * The {@link DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataSelection";

  /**
   * This method determines if the entire selection options of the concrete
   * implementing type should be cached by the system in main memory. This
   * option has to be be static for a concrete type.
   * 
   * @return <code>true</code> if the selections shall be cached,
   *         <code>false</code> otherwise.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_SELECTION_CACHEABLE, isStatic = BooleanEnum.TRUE, isFinal = BooleanEnum.TRUE)
  boolean isCacheable();

}
