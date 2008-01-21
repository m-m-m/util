/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import java.io.Serializable;
import java.util.List;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.api.Lock;
import net.sf.mmm.content.value.api.MetaDataSet;
import net.sf.mmm.content.value.api.RevisionHistory;

/**
 * This is the abstract interface for any content-object. An instance of
 * {@link ContentObject this} interface represents the
 * {@link #getRevision() revision} of an <em>entity</em>. An entity is a
 * persistent object stored in a repository. An entity always has a current
 * state that is called
 * <em>{@link #isRevisionClosed() latest} {@link #getRevision() revision}</em>
 * and may have <em>{@link #isRevisionClosed() closed revisions}</em> that
 * reflect the state of the entity at a time in its history.<br>
 * The core Java OO-world is rewritten here as meta-model inside Java. The
 * following table shows the mmm types with its corresponding Java constructs:<br>
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
 * <tr>
 * <td>{@link ClassLoader}</td>
 * <td>{@link net.sf.mmm.content.model.base.ContentClassLoader ContentClassLoader}</td>
 * </tr>
 * </table> <br>
 * The tree spanned by the hierarchy of the {@link ContentClass}es is called
 * <em>content-model</em>.<br>
 * A sub-type of this interface has to follow specific rules in order to be an
 * <em>entity-type</em> that will have an according {@link ContentClass}. For
 * more details see
 * {@link net.sf.mmm.content.base.AbstractContentObject AbstractContentObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentObject extends Serializable {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "ContentObject";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 0;

  /**
   * the variable-name of the current object in the context for calculation.
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
   * the separator used for the {@link #getPath() path}
   */
  String PATH_SEPARATOR = "/";

  /**
   * This method gets the unique identifier of this content-object.
   * 
   * @return the unique ID or <code>null</code> if the entity has been
   *         instantiated directly and NOT yet been persisted in the
   *         content-repository.
   */
  ContentId getId();

  /**
   * This method gets the name of this content-object. The name must be unique
   * for all classes, for all fields, or resources with the same parent folder.
   * <br>
   * The root-folder has the empty string as name while any other content-object
   * must have a name with a length greater zero.
   * 
   * @return the name of the resource.
   */
  String getName();

  /**
   * This method gets the content-class used to reflect this content-object. The
   * content-class represents the exact type of this content-object. The
   * content-class may also be called <em>entity-type</em>.
   * 
   * @return the content-class of this resource.
   */
  ContentClass getContentClass();

  /**
   * This method determines if this content-object is marked as deleted. The
   * deleted status is like {@link Deprecated deprecation} in java.<br>
   * A deleted object can NOT be modified. No instances or sub-classes can be
   * created of a deleted class. Deleted fields are NOT visible in the editor.
   * If an object is deleted it can either be undeleted or destroyed (if a
   * {@link ContentClass} is destroyed then all instances will be removed from
   * the persistence store).<br>
   * Like deprecation a deletion is inherited from the
   * {@link #getParent() parent}.
   * 
   * @see #getDeletedFlag()
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  boolean isDeleted();

  /**
   * The deleted-flag is inherited so {@link ContentObject#isDeleted()} will
   * return <code>true</code> if a {@link #getParent() parent object} is
   * marked as deleted.<br>
   * This method gets the deleted flag of this object. The method does not
   * inherit the flag.
   * 
   * @see ContentObject#isDeleted()
   * 
   * @return the deleted flag.
   */
  boolean getDeletedFlag();

  /**
   * This method gets the metadata-set of this object. The metadata can store
   * arbitrary informations that is NOT specified via the content-model.
   * 
   * @return the metadata-set.
   */
  MetaDataSet getMetaDataSet();

  /**
   * This method gets the lock of this object. It does NOT change the state of
   * the object but just gets the object that gives access to lock and unlock
   * this object.
   * 
   * @return the lock of this object.
   */
  Lock getLock();

  /**
   * This method gets the path to this content-object in the repository.<br>
   * The path already contains the {@link #getName() name} of this resource.
   * 
   * @return the path of the resource.
   */
  String getPath();

  /**
   * This method gets the revision number of this resource.<br>
   * The revision is a sequential counter that is incremented for each
   * {@link #isRevisionClosed() closed} revision. The initial revision of a new
   * created object is <code>0</code>.
   * 
   * @return the revision number of this resource where <code>0</code> means
   *         the latest revision.
   */
  int getRevision();

  /**
   * This method gets the history of this specific resource revision (version).
   * 
   * @return the history of this resource revision.
   */
  RevisionHistory getRevisionHistory();

  /**
   * This method determines if this object represents a closed
   * {@link #getRevision() revision} of the entities history. Such revision is
   * immutable and can NOT be modified anymore.<br>
   * In contrast each entity has a latest {@link #getRevision() revision} that
   * is open for modifications.
   * 
   * @return <code>true</code> if this object is a closed revision of the
   *         according entity and <code>false</code> if this is the latest
   *         revision.
   */
  boolean isRevisionClosed();

  /**
   * This method gets the parent of this object. This will typically be the
   * {@link #isFolder() folder} containing the object.
   * 
   * @return the parent or <code>null</code> if this is the root-{@link net.sf.mmm.content.resource.api.ContentFolder folder}
   *         or the root-{@link ContentClass class}.
   */
  ContentObject getParent();

  /**
   * This method gets the list containing all direct children of this object. If
   * this object is NOT a {@link #isFolder() folder} the result will always be
   * an empty list.<br>
   * Typically the direct children are the {@link ContentObject}s that have
   * this object as {@link #getParent() parent}.<br>
   * <b>ATTENTION:</b><br>
   * For some specific entities this method may return children that do NOT have
   * this object as {@link #getParent()}. E.g. a <code>SearchFolder</code>
   * may be a virtual folder that performs a dynamic search returning arbitrary
   * objects from this method that themselves reside in different, physical
   * folders.
   * 
   * @return the child resources.
   */
  List<? extends ContentObject> getChildren();

  /**
   * This method gets the {@link #getChildren() child} with the given
   * <code>childName</code>.<br>
   * 
   * @param childName is the {@link #getName() name} of the requested child.
   * @return the child with the given <code>childName</code> or
   *         <code>null</code> if no such child exists.
   */
  ContentObject getChild(String childName);

  /**
   * This method determines if this entity is a <em>folder</em> or a
   * <em>leaf</em>. A leaf will never have {@link #getChildren() children}
   * and therefore always return an empty list for {@link #getChildren()}. A
   * folder potentially has {@link #getChildren() children}. Anyways a specific
   * instance may or may NOT have {@link #getChildren() children}.<br>
   * If an entity is a leaf an instance can never be the
   * {@link #getParent() parent} of another object.
   * 
   * @return <code>true</code> if this entity is a folder, <code>false</code>
   *         if it is a leaf.
   */
  boolean isFolder();

  /**
   * This method gets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the generic
   * getter for all fields of this object. <br>
   * E.g. <code>getFieldValue("id")</code> will produce the same result as
   * {@link ContentObject#getId()}. If called on a sub-type, all fields
   * available fields of that entity are accessible.
   * 
   * @see #getValue(String, Class)
   * 
   * @param fieldName is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to get.
   * @return the value of the specified field or <code>null</code> if not set.
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the object does not have a
   *         {@link net.sf.mmm.content.model.api.ContentField field} with the
   *         given
   *         <code>{@link net.sf.mmm.content.model.api.ContentField#getName() fieldName}</code>.
   *         See
   *         {@link net.sf.mmm.content.model.api.FieldNotExistsException FieldNotExistsException}</li>
   *         <li>If {@link net.sf.mmm.content.security.api.ContentUser you} do
   *         NOT have
   *         {@link net.sf.mmm.content.security.api.ContentRule permission} to
   *         do so. See
   *         {@link net.sf.mmm.content.security.api.PermissionDeniedException PermissionDeniedException}.</li>
   *         </ul>
   */
  Object getValue(String fieldName) throws ContentException;

  /**
   * This method gets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the generic
   * getter for all fields of this object. <br>
   * E.g. <code>getFieldValue("id")</code> will produce the same result as
   * {@link ContentObject#getId()}. Additionally all fields that are defined in
   * sub-types are accessible.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param fieldName is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to get.
   * @param type is the type of the requested field value. It has to be
   *        {@link Class#isAssignableFrom(Class) compatible} with the
   *        {@link net.sf.mmm.content.model.api.ContentField#getFieldClass() fields class}.
   * @return the value of the specified field or <code>null</code> if not set.
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the object does not have a
   *         {@link net.sf.mmm.content.model.api.ContentField field} with the
   *         given
   *         <code>{@link net.sf.mmm.content.model.api.ContentField#getName() fieldName}</code>.
   *         See
   *         {@link net.sf.mmm.content.model.api.FieldNotExistsException FieldNotExistsException}</li>
   *         <li>If {@link net.sf.mmm.content.security.api.ContentUser you} do
   *         NOT have
   *         {@link net.sf.mmm.content.security.api.ContentRule permission} to
   *         do so. See
   *         {@link net.sf.mmm.content.security.api.PermissionDeniedException PermissionDeniedException}.</li>
   *         </ul>
   */
  <V> V getValue(String fieldName, Class<V> type) throws ContentException;

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
   * @throws ContentException if the operation failed. This can have one of the
   *         following reasons:
   *         <ul>
   *         <li>the object does not have a
   *         {@link net.sf.mmm.content.model.api.ContentField field} with the
   *         given
   *         <code>{@link net.sf.mmm.content.model.api.ContentField#getName() fieldName}</code>.
   *         See
   *         {@link net.sf.mmm.content.model.api.FieldNotExistsException FieldNotExistsException}</li>
   *         <li>If {@link net.sf.mmm.content.security.api.ContentUser you} do
   *         NOT have
   *         {@link net.sf.mmm.content.security.api.ContentRule permission} to
   *         do so. See
   *         {@link net.sf.mmm.content.security.api.PermissionDeniedException PermissionDeniedException}.</li>
   *         </ul>
   */
  void setValue(String fieldName, Object value) throws ContentException;

}
