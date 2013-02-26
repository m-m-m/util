/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataObject} that acts as selection. Such selection will allow to choose
 * out of all instances of this {@link net.sf.mmm.data.api.reflection.DataClass type}. This is used to
 * determine how end users choose an instance to link from another {@link DataObject}.
 * 
 * TODO: remove this interface and move isCacheable() to DataClass and DataClassAnnotation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelection.CLASS_ID, title = DataSelection.CLASS_TITLE)
public interface DataSelection extends DataObject {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 3;

  /**
   * The {@link DataObject#getTitle() title} of the {@link net.sf.mmm.data.api.reflection.DataClass}
   * reflecting this type.
   */
  String CLASS_TITLE = "DataSelection";

  /**
   * This method determines if this instance is <em>selectable</em>. Selectable simply means it is a valid
   * selection the user can choose. Otherwise this instances can not be chosen as valid choice in regular
   * selections. This is especially helpful for instances that represent a node of a
   * {@link DataSelectionGenericTree selection tree} that is not selectable (in this case called an abstract
   * node) and only one of its children can be selected. In case of {@link DataSelectionList selection lists}
   * instances that are NOT selectable, may be disabled or even entirely omitted in the UI when doing a
   * selection (e.g. as dropdown/combobox).
   * 
   * @return <code>true</code> if selectable, <code>false</code> otherwise.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_SELECTION_SELECTABLE, isStatic = BooleanEnum.TRUE, isFinal = BooleanEnum.TRUE)
  boolean isSelectable();

  /**
   * This method sets the {@link #isSelectable() selectable} flag.
   * 
   * @param selectable - <code>true</code> if this instance should be {@link #isSelectable() selectable},
   *        <code>false</code> otherwise.
   */
  void setSelectable(boolean selectable);

}
