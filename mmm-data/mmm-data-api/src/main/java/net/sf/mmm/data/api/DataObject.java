/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import java.io.Serializable;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
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
@DataClassAnnotation(id = DataObject.CLASS_ID, title = DataObject.CLASS_NAME)
public abstract interface DataObject extends RevisionedPersistenceEntity<Long>,
    AttributeReadTitle<String>, Serializable {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 0;

  /**
   * The {@link DataObject#getTitle() name} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_NAME = "ContentObject";

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

}
