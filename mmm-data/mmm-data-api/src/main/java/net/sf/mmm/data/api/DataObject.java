/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.io.Serializable;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.util.lang.api.BooleanEnum;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the abstract interface for an object of the content-repository. An
 * instance of {@link DataObject this} interface represents the
 * {@link #getRevision() revision} of an <em>entity</em> stored in the content
 * repository. There are two cases to distinguish:
 * <ul>
 * <li>The {@link #LATEST_REVISION latest revision}.<br/>
 * A {@link DataObject} pointing to {@link #LATEST_REVISION} represents the
 * latest state of the entity and can be modified.</li>
 * <li>A previous {@link #getRevision() revision}.<br/>
 * If the entity is {@link #getRevision() revision controlled}, it has a history
 * of modifications. A {@link DataObject} can represent a historic
 * {@link #getRevision() revision} out of this history. It therefore is
 * immutable so operations to modify the {@link DataObject} will fail.</li>
 * </ul>
 * The core Java OO-world is rewritten here as meta-model inside Java. The
 * following table shows the mmm types with its corresponding Java constructs:<br>
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
 * <td>{@link java.lang.reflect.Field Field}/{@link java.lang.reflect.Method
 * Method}</td>
 * <td>{@link net.sf.mmm.data.api.reflection.DataField}</td>
 * </tr>
 * <tr>
 * <td>{@link ClassLoader}</td>
 * <td>{@link net.sf.mmm.data.api.reflection.DataClassLoader}</td>
 * </tr>
 * </table>
 * <br>
 * The tree spanned by the hierarchy of the
 * {@link net.sf.mmm.data.api.reflection.DataClass}es is called
 * <em>data model</em>.<br>
 * A sub-type of this interface has to follow specific rules in order to be an
 * <em>entity-type</em> that will have an according
 * {@link net.sf.mmm.data.api.reflection.DataClass}.
 * 
 * @see net.sf.mmm.data.api.datatype.DataId
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataObject.CLASS_ID, title = DataObject.CLASS_TITLE, isExtendable = BooleanEnum.FALSE)
public abstract interface DataObject extends RevisionedPersistenceEntity<Long>,
    AttributeReadTitle<String>, Serializable {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 0;

  /**
   * The {@link DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataObject";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getId() ID}.
   */
  String FIELD_NAME_ID = "id";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getTitle() name} for generic access.
   */
  String FIELD_NAME_TITLE = "title";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getDeletedFlag() deletedFlag} for generic access.
   */
  String FIELD_NAME_DELETEDFLAG = "deletedFlag";

  /**
   * {@inheritDoc}
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_ID, isFinal = true)
  Long getId();

  /**
   * This method gets the title (name) of this content-object. For objects of
   * particular {@link net.sf.mmm.data.api.reflection.DataClass types} (e.g.
   * {@link net.sf.mmm.data.api.reflection.DataClass} or
   * {@link net.sf.mmm.data.api.reflection.DataField}) the title has to be
   * unique. <br>
   * 
   * @return the title of this object.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_OBJECT_TITLE, title = FIELD_NAME_TITLE, isFinal = true)
  String getTitle();

  /**
   * The deleted-flag of a {@link DataNode} is inherited so {@link #isDeleted()}
   * will return <code>true</code> if a {@link DataNode#getParent() parent
   * object} is marked as deleted.<br>
   * This method gets the deleted flag of this object. The method does not
   * inherit the flag.
   * 
   * @see #isDeleted()
   * 
   * @return the deleted flag.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_DELETEDFLAG, title = FIELD_NAME_DELETEDFLAG, isFinal = true)
  boolean getDeletedFlag();

  /**
   * This method determines if this content-object is marked as deleted. The
   * deleted status is similar to {@link Deprecated deprecation} in java.<br>
   * Further, a deleted object can NOT be modified. No instances or sub-classes
   * can be created of a deleted class. Deleted fields are hidden by default in
   * the UI. If an object is deleted it can either be undeleted or destroyed (if
   * a {@link net.sf.mmm.data.api.reflection.DataClass} is destroyed then all
   * instances will be removed from the persistence store).<br>
   * Like deprecation a deletion is inherited from the
   * {@link DataNode#getParent() parent}.
   * 
   * @see #getDeletedFlag()
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_DELETED, isTransient = true)
  boolean isDeleted();

}
