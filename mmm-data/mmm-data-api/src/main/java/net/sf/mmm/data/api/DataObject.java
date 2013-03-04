/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.io.Serializable;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.entity.api.MutableRevisionedEntity;
import net.sf.mmm.util.lang.api.BooleanEnum;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the interface for an object of the {@link net.sf.mmm.data.api.repository.DataRepository}. <br/>
 * The core Java OO-world is rewritten here as meta-model inside Java. The following table shows the mmm types
 * with its corresponding Java constructs:<br>
 * <table border="1">
 * <tr>
 * <th>Java</th>
 * <th>mmm</th>
 * </tr>
 * <tr>
 * <td>{@link Object}</td>
 * <td>{@link DataObject}</td>
 * </tr>
 * <tr>
 * <td>{@link Class}</td>
 * <td>{@link net.sf.mmm.data.api.reflection.DataClass}</td>
 * </tr>
 * <tr>
 * <td>{@link java.lang.reflect.Field Field}/{@link java.lang.reflect.Method Method}</td>
 * <td>{@link net.sf.mmm.data.api.reflection.DataField}</td>
 * </tr>
 * <tr>
 * <td>{@link ClassLoader}</td>
 * <td>{@link net.sf.mmm.data.api.reflection.DataClassLoader}</td>
 * </tr>
 * </table>
 * <br>
 * The tree spanned by the hierarchy of the {@link net.sf.mmm.data.api.reflection.DataClass}es is called
 * <em>data model</em>.<br>
 * A sub-type of this interface has to follow specific rules in order to be an <em>entity-type</em> that will
 * have an according {@link net.sf.mmm.data.api.reflection.DataClass}.
 * 
 * @see net.sf.mmm.data.api.datatype.DataId
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataObject.CLASS_ID, title = DataObject.CLASS_TITLE, isExtendable = BooleanEnum.FALSE)
public interface DataObject extends MutableRevisionedEntity<Long>, AttributeReadTitle<String>, Serializable {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_OBJECT;

  /**
   * The {@link DataObject#getTitle() title} of the {@link net.sf.mmm.data.api.reflection.DataClass}
   * reflecting this type.
   */
  String CLASS_TITLE = "DataObject";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field} {@link #getId() ID}.
   */
  String FIELD_NAME_ID = "id";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field} {@link #getTitle() name} for
   * generic access.
   */
  String FIELD_NAME_TITLE = "title";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field} {@link #getDeletedFlag()
   * deletedFlag} for generic access.
   */
  String FIELD_NAME_DELETEDFLAG = "deletedFlag";

  /**
   * {@inheritDoc}
   */
  @Override
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_ID, isFinal = BooleanEnum.TRUE)
  Long getId();

  /**
   * This method gets the title (name) of this object. For objects of particular
   * {@link net.sf.mmm.data.api.reflection.DataClass types} the title has to be unique either globally (e.g.
   * for {@link net.sf.mmm.data.api.reflection.DataClass}) or within a set of siblings (e.g.
   * {@link net.sf.mmm.data.api.entity.resource.DataEntityResource}).<br>
   * 
   * @return the title of this object.
   */
  @Override
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_TITLE, title = FIELD_NAME_TITLE, isFinal = BooleanEnum.TRUE)
  String getTitle();

  /**
   * The deleted-flag of a {@link DataNode} is inherited so {@link #isDeleted()} will return <code>true</code>
   * if a {@link DataNode#getParent() parent object} is marked as deleted.<br>
   * This method gets the deleted flag of this object. The method does not inherit the flag.
   * 
   * @see #isDeleted()
   * 
   * @return the deleted flag.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_DELETED_FLAG, title = FIELD_NAME_DELETEDFLAG, isFinal = BooleanEnum.TRUE)
  boolean getDeletedFlag();

  /**
   * This method determines if this content-object is marked as deleted. The deleted status is similar to
   * {@link Deprecated deprecation} in java.<br>
   * Further, a deleted object can NOT be modified. No instances or sub-classes can be created of a deleted
   * class. Deleted fields are hidden by default in the UI. If an object is deleted it can either be undeleted
   * or destroyed (if a {@link net.sf.mmm.data.api.reflection.DataClass} is destroyed then all instances will
   * be removed from the persistence store).<br>
   * Like deprecation a deletion is inherited from the {@link DataNode#getParent() parent}.
   * 
   * @see #getDeletedFlag()
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_DELETED, isTransient = BooleanEnum.TRUE)
  boolean isDeleted();

  /**
   * This method gets the {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class ID} identifying the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this object.
   * 
   * @return the {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class ID}.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_DATA_CLASS_ID, isStatic = BooleanEnum.TRUE, isFinal = BooleanEnum.TRUE, isReadOnly = BooleanEnum.TRUE)
  long getDataClassId();

  /**
   * This method sets the {@link #getDeletedFlag() deleted flag}.
   * 
   * @param deleted is the deleted flag to set.
   */
  void setDeletedFlag(boolean deleted);

  /**
   * This method sets the {@link #getTitle() title} or in other words it renames the object.<br/>
   * <b>ATTENTION:</b><br/>
   * The title of some {@link #getDataClassId() object types} has to be unique.
   * 
   * @param title is the new title.
   */
  void setTitle(String title);

}
