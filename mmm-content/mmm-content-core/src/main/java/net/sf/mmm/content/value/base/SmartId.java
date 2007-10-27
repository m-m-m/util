/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import net.sf.mmm.content.value.api.ContentId;

/**
 * This is a more specific interface for the {@link ContentId} of a
 * {@link net.sf.mmm.content.api.ContentObject ContentObject}.<br>
 * Since the revision and
 * {@link net.sf.mmm.content.api.ContentObject#getContentClass() content-class}
 * of a {@link net.sf.mmm.content.api.ContentObject content-object} does not
 * change, their primary keys are stored in this ID implementation. This allows
 * to determine the content-class and revision of the resource without any cost
 * (e.g. DB lookup). Especially a content-object-instance can be created from
 * the ID using lazy loading.<br>
 * This {@link SmartId} is build out of four parts:
 * <ul>
 * <li>{@link #getObjectId() object-id} - unique object/resource counter but
 * <code>0</code> for the ID of a
 * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} and
 * <code>1</code> for the ID of a
 * {@link net.sf.mmm.content.model.api.ContentField ContentField}.</li>
 * <li>{@link #getStoreId() store-id} - if multiple backends are used to store
 * objects this identifies the actual store. In that case two totally different
 * objects may share the same object-id. Unique identification is only possible
 * in combination with this store-id.</li>
 * <li>{@link #getRevision() revision} - a resource can have multiple revisions
 * (in the version history). All revisions of a resource (in the same branch)
 * share the same {@link #getObjectId() object-id}. An {@link ContentId}
 * uniquely identifies the specific resource-revision.</li>
 * <li>{@link #getClassId() class-id} - is the id of the content-class that
 * reflects the {@link net.sf.mmm.content.api.ContentObject content-object}
 * identified by this {@link ContentId}. See also {@link #getContentClassId()}.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SmartId extends ContentId {

  /** This radix is used to convert numbers to/from strings. */
  public int RADIX = 32;

  /** the {@link #getObjectId() object-ID} of a class. */
  long OID_CLASS = 0;

  /** the {@link #getObjectId() object-ID} of a field. */
  long OID_FIELD = 1;

  /**
   * the first {@link #getObjectId() object-ID} that can be used for a resource.
   * All object-IDs lower than this are reserved for objects that use the
   * {@link #getClassId() class-ID} as primary key.
   */
  long OID_MINIMUM_RESOURCE = 16;

  /**
   * the first {@link #getObjectId() object-id} that can be used for custom
   * resources. All object-IDs lower than this are reserved for system resources
   * (e.g. root-folder, etc.).
   */
  long OID_MINIMUM_CUSTOM = 4096;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-principal.
   */
  int CLASS_ID_PRINCIPAL = 7;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-user.
   */
  int CLASS_ID_USER = 8;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  int CLASS_ID_GROUP = 9;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  int CLASS_ID_ACTION = 10;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  int CLASS_ID_PERMISSION = 11;

  /**
   * the first {@link #getClassId() class-id} that can be used for custom
   * classes. All class-IDs lower than this are reserved for system classes.
   */
  int CLASS_ID_MINIMUM_CUSTOM = 4096;

  /**
   * the first {@link #getObjectId() object-id} that can be used for custom
   * fields. All field-IDs lower than this are reserved for system fields.
   */
  int FIELD_ID_MINIMUM_CUSTOM = 4096;

  /** the id of the root-folder. */
  long FOLDER_ID_ROOT = 2;

  /** the id of the reflection-folder. */
  long FOLDER_ID_REFLECTION = 3;

  /** the id of the classes-folder. */
  long FOLDER_ID_CLASSES = 4;

  /** the id of the fields-folder. */
  long FOLDER_ID_FIELDS = 5;

  /** the id of the principals-folder. */
  long FOLDER_ID_PRINCIPALS = 6;

  /** the id of the users-folder. */
  long FOLDER_ID_USERS = 7;

  /** the id of the groups-folder. */
  long FOLDER_ID_GROUPS = 8;

  /** the id of the IDs-folder. */
  long FOLDER_ID_IDS = 9;

  /** the id of the resources-folder. */
  long FOLDER_ID_RESOURCES = 10;

  /** the delimiter used in the string representations of an id */
  char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representations of an id */
  String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

  /**
   * This method gets the object-id, which is a unique identifier (of the
   * revision history) of a
   * {@link net.sf.mmm.content.api.ContentObject ContentObject} with two
   * excuses:
   * <ul>
   * <li><code>0</code> is the object-id of all
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass}es and</li>
   * <li><code>1</code> is the object-id of all
   * {@link net.sf.mmm.content.model.api.ContentField ContentField}s.</li>
   * </ul>
   * 
   * @return the object-id.
   */
  long getObjectId();

  /**
   * This method gets the class-id. If the object identified by this ID is a
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} or
   * {@link net.sf.mmm.content.model.api.ContentField content-field} (the
   * {@link #getObjectId() object-id} is <code>0</code> or <code>1</code>),
   * this class-id is the main identifier rather than the
   * {@link #getObjectId() object-id}. In all other cases the class-id is the
   * identifier of the {@link net.sf.mmm.content.api.ContentObject objects}
   * {@link net.sf.mmm.content.api.ContentObject#getContentClass() content-class}.
   * 
   * @return the classId.
   */
  int getClassId();

  /**
   * This method gets the revision number of the specific version associated
   * with this id. If the associated object is NOT version controlled (e.g. a
   * class or field), the the revision will always be <code>0</code>.<br>
   * Further a revision of <code>0</code> always points to the latest revision
   * of an object.
   * 
   * @return the revision.
   */
  int getRevision();

  /**
   * This method gets the id of the store where the identified content object is
   * persisted. The default store has the id <code>0</code>.
   * 
   * @return the storeId.
   */
  int getStoreId();

  /**
   * This method gets the {@link ContentId ID} of the associated
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   * 
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   * 
   * @return the ID of the associated content-class.
   */
  SmartId getContentClassId();

  /**
   * {@inheritDoc}
   * 
   * The string representation of the smart ID needs to be in the following
   * form:
   * 
   * <pre>
   * &lt;{@link #getObjectId() objectId}&gt;.&lt;{@link #getClassId() classId}&gt;[.&lt;{@link #getRevision() revision}&gt;][:&lt;{@link #getStoreId() storeId}&gt;]
   * </pre>
   * 
   * The revision and/or storeId can be omitted if zero. All number values are
   * {@link Long#toString(long, int) encoded} using the {@link #RADIX} constant.
   */
  String toString();

}
