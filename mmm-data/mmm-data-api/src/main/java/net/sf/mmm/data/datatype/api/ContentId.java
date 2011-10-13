/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.datatype.api;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for an ID that uniquely identifies a
 * {@link net.sf.mmm.data.api.ContentObject} in a content-repository.<br/>
 * It is build out of four parts:
 * <ul>
 * <li>{@link #getObjectId() object-id} - unique ID of a
 * {@link net.sf.mmm.data.api.ContentObject} of a particular
 * {@link net.sf.mmm.data.reflection.api.ContentClass} (type).</li>
 * <li>{@link #getClassId() class-id} - is the id of the
 * {@link net.sf.mmm.data.reflection.api.ContentClass} that reflects the
 * {@link net.sf.mmm.data.api.ContentObject} identified by this
 * {@link ContentId}.</li>
 * <li>{@link #getRevision() revision} - a
 * {@link net.sf.mmm.data.api.ContentObject}
 * {@link net.sf.mmm.data.reflection.api.ContentClass#isRevisionControlled() can}
 * have multiple {@link net.sf.mmm.data.reflection.api.ContentClass#getRevision()
 * revisions} (as history). An {@link ContentId} uniquely identifies the
 * specific {@link #getRevision() revision}.</li>
 * <li>{@link #getStoreId() store-id} - if multiple back-ends are used to store
 * objects this identifies the actual store. In that case two totally different
 * objects may share the same {@link #getObjectId() object-id}. Unique
 * identification is only possible in combination with this
 * {@link #getStoreId() store-id}. However in smaller systems only one store is
 * used.</li>
 * </ul>
 * If the identified object is a
 * {@link net.sf.mmm.data.resource.api.ContentResource} this ID points to the
 * exact revision in the history of that resource.<br>
 * Since the {@link #getRevision() revision} and {@link #getClassId() class} of
 * a {@link net.sf.mmm.data.api.ContentObject content-object} does not
 * change, their primary keys are stored in this ID implementation. This allows
 * to determine the content-class and revision of the resource without any cost
 * (e.g. DB lookup). Especially a {@link net.sf.mmm.data.api.ContentObject}
 * -instance may be created as dynamic proxy from the ID using lazy loading.<br>
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentId extends Datatype<ContentId> {

  /** The name of this value type. */
  String VALUE_NAME = "Id";

  /** This radix is used to convert numbers to/from strings. */
  int RADIX = 32;

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
   * the id number of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} of a content-principal.
   */
  int CLASS_ID_PRINCIPAL = 7;

  /**
   * the id number of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} of a content-user.
   */
  int CLASS_ID_USER = 8;

  /**
   * the id number of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} of a content-group.
   */
  int CLASS_ID_GROUP = 9;

  /**
   * the id number of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} of a content-group.
   */
  int CLASS_ID_ACTION = 10;

  /**
   * the id number of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} of a content-group.
   */
  int CLASS_ID_PERMISSION = 11;

  /**
   * the first {@link #getObjectId() object-id} that can be used for custom
   * fields. All field-IDs lower than this are reserved for system fields.
   */
  int FIELD_ID_MINIMUM_CUSTOM = 4096;

  /** The id of the root-folder. */
  long FOLDER_ID_ROOT = 2;

  /** the id of the reflection-folder. */
  long FOLDER_ID_REFLECTION = 3;

  /** The id of the classes-folder. */
  long FOLDER_ID_CLASSES = 4;

  /** The id of the fields-folder. */
  long FOLDER_ID_FIELDS = 5;

  /** The id of the principals-folder. */
  long FOLDER_ID_PRINCIPALS = 6;

  /** The id of the users-folder. */
  long FOLDER_ID_USERS = 7;

  /** The id of the groups-folder. */
  long FOLDER_ID_GROUPS = 8;

  /** The id of the IDs-folder. */
  long FOLDER_ID_IDS = 9;

  /** The id of the resources-folder. */
  long FOLDER_ID_RESOURCES = 10;

  /** The delimiter used in the string representations of an id. */
  char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representations of an id */
  String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

  /**
   * This method gets the object-id, which is the
   * {@link net.sf.mmm.data.api.ContentObject#getId() unique identifier} of a
   * {@link net.sf.mmm.data.api.ContentObject} for a particular
   * {@link net.sf.mmm.data.reflection.api.ContentClass}.
   * 
   * @see net.sf.mmm.data.api.ContentObject#getId()
   * 
   * @return the object-id.
   */
  long getObjectId();

  /**
   * This method gets the identifier of the
   * {@link net.sf.mmm.data.reflection.api.ContentClass} reflecting the identified
   * object.
   * 
   * @see net.sf.mmm.data.api.ContentObject#getContentClassId()
   * @see #getContentClassId()
   * 
   * @see net.sf.mmm.data.reflection.api.ContentClass#CLASS_ID
   * @see net.sf.mmm.data.reflection.api.ContentField#CLASS_ID
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
   * @see net.sf.mmm.data.api.ContentObject#getRevision()
   * @see net.sf.mmm.data.api.ContentObject#LATEST_REVISION
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
   * This method gets the {@link ContentId} of the associated
   * {@link net.sf.mmm.data.reflection.api.ContentClass}. It is build by using the
   * {@link #getClassId() class-ID} as {@link #getObjectId() object-ID} and
   * {@link net.sf.mmm.data.reflection.api.ContentClass#CLASS_ID} as
   * {@link #getClassId() class-ID}.
   * 
   * @return the ID of the associated content-class.
   */
  ContentId getContentClassId();

  /**
   * The string representation of the smart ID needs to be in the following
   * form:
   * 
   * <pre>
   * &lt;{@link #getObjectId() objectId}&gt;.&lt;{@link #getClassId() 
   * classId}&gt;[.&lt;{@link #getRevision() revision}&gt;][:&lt;{@link #getStoreId() storeId}&gt;]
   * </pre>
   * 
   * The revision and/or storeId can be omitted if zero. All number values are
   * {@link Long#toString(long, int) encoded} using the {@link #RADIX} constant. <br/>
   * <br/>
   * 
   * {@inheritDoc}
   */
  String toString();

}
