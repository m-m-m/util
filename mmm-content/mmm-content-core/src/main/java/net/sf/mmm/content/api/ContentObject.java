/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import java.io.Serializable;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.FieldNotExistsException;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.api.MetaData;

/**
 * This is the abstract interface for any content object. Such object is a
 * persistent entity stored in a repository.<br>
 * The core Java OO-world is rewritten here as meta-model inside Java. The
 * following table shows the mmm types to corresponding Java constructs:<br>
 * <table border="1">
 * <tr>
 * <th>Java</th>
 * <th>mmm</th>
 * </tr>
 * <tr>
 * <td>{@link Object}</td>
 * <td>{@link ContentObject}</td>
 * </tr>
 * <tr>
 * <td>{@link Class}</td>
 * <td>{@link ContentClass}</td>
 * </tr>
 * <tr>
 * <td>{@link java.lang.reflect.Field Field}/{@link java.lang.reflect.Method Method}</td>
 * <td>{@link net.sf.mmm.content.model.api.ContentField ContentField}</td>
 * </tr>
 * </table> <br>
 * TODO: add generic methods "ContentObject getParent()", "String getPath()"
 * "Collection&lt;ContentObject&gt; getChildren()" instead of content-folder?<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentObject extends Serializable {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "Object";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 0;

  /** the namespace used for project internal metadata. */
  String METADATA_NAMESPACE_MMM = "mmm";

  /** the namespace used to reflect the fields as metadata. */
  String METADATA_NAMESPACE_NONE = "";

  /**
   * the variable-name of the current object in the
   * {@link net.sf.mmm.context.api.Context context} for link
   * net.sf.mmm.content.model.api.ContentField#calculate(ContentObject)
   * calculation.
   */
  String ENV_VARIABLE_THIS = "this";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getId() ID} for generic access via {@link #getValue(String)}.
   */
  String FIELD_NAME_ID = "id";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getName() name} for generic access via {@link #getValue(String)}.
   */
  String FIELD_NAME_NAME = "name";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #isDeleted() deleted} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_DELETED = "deleted";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getContentClass() contentClass} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_CONTENT_CLASS = "contentClass";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getMetaData(String) metadata} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_METADATA = "metadata";

  /**
   * This method gets the unique identifier of this content-object.
   * 
   * @return the unique ID.
   */
  ContentId getId();

  /**
   * This method gets the name of this content-object. The name must be unique
   * for all content-objects in the same folder (that have the same parent
   * folder). <br>
   * The root-folder has the empty string as name while any other content-object
   * must have a name with a length greater zero.
   * 
   * @return the name of the resource.
   */
  String getName();

  /**
   * This method gets the content-class used to reflect this content-object. The
   * content-class represents the exact type of this content-object.
   * 
   * @return the content-class of this resource.
   */
  ContentClass getContentClass();

  /**
   * This method determines if this content-object is marked as deleted. <br>
   * A deleted class or field can NOT be modified. No instances or sub-classes
   * can be created of a deleted class. Deleted fields are NOT visible in the
   * editor. If a class or field is deleted it can either be undeleted or
   * destroyed (then all instances will be removed from the persistence store).
   * <br>
   * If the deleted flag is set, the class or field can be seen as deprecated.
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  boolean isDeleted();

  /**
   * This method gets all {@link #getMetaData(String)} meta-data of this object.
   * The keys are in the form <code>&lt;namespace&gt;:&lt;local-key&gt;</code>
   * (e.g. <code>mmm:proxy</code>). This method is rather expensive. Use
   * {@link #getMetaData(String)} whenever possible.
   * 
   * @return the meta-data.
   */
  // Map<String, String> getMetaData();
  /**
   * This method gets the meta-data of this object for a given
   * <code>namespace</code>. The metadata can store arbitrary informations as
   * string that is NOT specified via the content-model.
   * 
   * @param namespace is the namespace (prefix) for which the metadata is
   *        requested. The value needs to consist only of the characters
   *        <code>'a-Z', '0-9', '.'</code> and <code>'-'</code>. Especially
   *        it must NOT contain the character <code>':'</code>. Please note
   *        that {@link #METADATA_NAMESPACE_MMM mmm} and
   * @return the meta-data.
   */
  MetaData getMetaData(String namespace);

  /**
   * This method gets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the generic
   * getter for all fields of this object. <br>
   * E.g. <code>getFieldValue("id")</code> will produce the same result as
   * {@link ContentObject#getId()}. Additionally all fields that are defined in
   * sub-types are accessible.
   * 
   * @param fieldName is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to get.
   * @return the value of the specified field or <code>null</code> if not set.
   * @throws FieldNotExistsException if the objects
   *         {@link #getContentClass() content-class} does not have a
   *         {@link net.sf.mmm.content.model.api.ContentField field} with the
   *         given {@link ContentObject#getName() name}.
   * @throws PermissionDeniedException if you (the current user) does not have
   *         permission to perform the operation.
   * @throws ContentException TODO: if the specified field is
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isTransient() transient}
   *         but an error occurred calculating its value.
   */
  Object getValue(String fieldName) throws FieldNotExistsException, PermissionDeniedException,
      ContentException;

  /**
   * This method sets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the generic
   * setter for all fields of this resource. The field must NOT be
   * {@link net.sf.mmm.content.model.api.FieldModifiers#isReadOnly() read-only}.
   * A {@link net.sf.mmm.content.model.api.FieldModifiers#isStatic() static}
   * field can only be set on a {@link ContentClass content-class}. Other
   * fields only on a resource.
   * 
   * @see #getValue(String)
   * 
   * @param fieldName is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to set. The
   *        field must be defined in the object's
   *        {@link #getContentClass() content-class}.
   * @param value is the new value for the field. It must be an instance of the
   *        {@link net.sf.mmm.content.model.api.ContentField#getFieldType() type}
   *        declared by the
   *        {@link net.sf.mmm.content.model.api.ContentField field}.
   * @throws FieldNotExistsException if the objects
   *         {@link #getContentClass() content-class} does not have a
   *         {@link net.sf.mmm.content.model.api.ContentField field} with the
   *         given {@link ContentObject#getName() name}.
   * @throws PermissionDeniedException if you (the current user) does not have
   *         permission to perform the operation.
   * @throws ContentException TODO: if the field has an incompatible type for
   *         the given value.
   */
  void setValue(String fieldName, Object value) throws FieldNotExistsException,
      PermissionDeniedException, ContentException;

  /**
   * This method test if the given action is allowed for the current user.
   * 
   * @param action is the action to be checked.
   * @return <code>true</code> if the current user is allowed to perform the
   *         specified action of this object.
   */
  // boolean checkPermission(ContentActionIF action);
  /**
   * This method validates the given object. This is done by
   * {@link net.sf.mmm.content.api.model.ContentFieldIF#validate(Object) validating}
   * the {@link #getFieldValue(String) values} of all
   * {@link net.sf.mmm.content.api.model.ContentFieldIF fields} as defined by
   * the objects {@link #getContentClass() content-class}.
   * 
   * @return the result of the validation.
   * @throws PermissionDeniedException if you (the current user) does not have
   *         permission to perform the operation.
   */
  // ValidationResultIF validate() throws PermissionDeniedException;
}
