/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.datatype;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the interface for an ID that uniquely identifies a
 * {@link net.sf.mmm.data.api.DataObject} in a
 * {@link net.sf.mmm.data.api.repository.DataRepository}.<br/>
 * It is build out of four parts:
 * <ul>
 * <li>{@link #getObjectId() object-id} -
 * {@link net.sf.mmm.data.api.DataObject#getId() unique ID} of a
 * {@link net.sf.mmm.data.api.DataObject} of a particular
 * {@link net.sf.mmm.data.api.reflection.DataClass} (type).</li>
 * <li>{@link #getClassId() class-id} - is the
 * {@link net.sf.mmm.data.api.reflection.DataClass#getId() id} of the
 * {@link net.sf.mmm.data.api.reflection.DataClass} that reflects the
 * {@link net.sf.mmm.data.api.DataObject} identified by this {@link DataId}.</li>
 * <li>{@link #getRevision() revision} - a
 * {@link net.sf.mmm.data.api.DataObject} can have multiple
 * {@link net.sf.mmm.data.api.DataObject#getRevision() revisions} (as history of
 * changes). A {@link DataId} uniquely identifies the specific
 * {@link #getRevision() revision}.</li>
 * <li>{@link #getStoreId() store-id} - if multiple back-ends are used to store
 * objects this identifies the actual store. In that case two totally different
 * objects may share the same {@link #getObjectId() object-id}. Unique
 * identification is only possible in combination with this
 * {@link #getStoreId() store-id}. However, this may only be used in large and
 * complex systems. Typically only one store is used that has the store-id
 * <code>0</code>.</li>
 * </ul>
 * If the identified object is revision controlled this ID points to the exact
 * revision in the history of that resource.<br>
 * Since the {@link #getRevision() revision} and {@link #getClassId() class} of
 * a {@link net.sf.mmm.data.api.DataObject} does not change, their primary keys
 * are stored in this ID implementation. This allows to determine the data-class
 * and revision of the resource without any cost (e.g. DB lookup). Especially a
 * {@link net.sf.mmm.data.api.DataObject}-instance may be created as dynamic
 * proxy from the ID using lazy loading.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataId extends Datatype<DataId> {

  /** The name of this value type. */
  String VALUE_NAME = "Id";

  /** This radix is used to convert numbers to/from strings. */
  int RADIX = 32;

  /**
   * An illegal value for the {@link #getObjectId() object-id}. This is reserved
   * for undefined values.
   */
  long OBJECT_ID_ILLEGAL = -1;

  /**
   * The minimum {@link #getObjectId() object-id} that can be used for custom
   * {@link net.sf.mmm.data.api.entity.DataEntityView entities},
   * {@link net.sf.mmm.data.api.reflection.DataClass classes}, or
   * {@link net.sf.mmm.data.api.reflection.DataField fields}. All object-IDs
   * lower than this are reserved for system objects (e.g. build-in classes and
   * fields, root-folder, etc.).<br/>
   * <b>NOTE:</b><br/>
   * If you want to create a reusable and distributed extension for mmm please
   * get in contact with us and we can try to find a reserved ID space.
   */
  long OBJECT_ID_MINIMUM_CUSTOM = 1048576;

  /** The delimiter used in the string representations of an id. */
  char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representations of an id */
  String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

  /**
   * This method gets the object-id, which is the
   * {@link net.sf.mmm.data.api.DataObjectView#getId() unique identifier} of a
   * {@link net.sf.mmm.data.api.DataObject} for a particular
   * {@link net.sf.mmm.data.api.reflection.DataClass}.
   * 
   * @see net.sf.mmm.data.api.DataObjectView#getId()
   * 
   * @return the object-id.
   */
  long getObjectId();

  /**
   * This method gets the identifier of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting the identified
   * object.
   * 
   * @see net.sf.mmm.data.api.reflection.DataReflectionService#getDataId(net.sf.mmm.data.api.DataObjectView)
   * @see #getDataClassId()
   * 
   * @see net.sf.mmm.data.api.reflection.DataClass#CLASS_ID
   * @see net.sf.mmm.data.api.reflection.DataField#CLASS_ID
   * 
   * @return the classId.
   */
  long getClassId();

  /**
   * This method gets the revision number of the specific version associated
   * with this id. If the associated object is NOT version controlled (e.g. a
   * class or field), the the revision will always be <code>0</code>.<br>
   * Further a revision of <code>0</code> always points to the latest revision
   * of an object.
   * 
   * @see net.sf.mmm.data.api.DataObjectView#getRevision()
   * @see net.sf.mmm.data.api.DataObjectView#LATEST_REVISION
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
   * This method gets the {@link DataId} of the associated
   * {@link net.sf.mmm.data.api.reflection.DataClass}. It is build by using the
   * {@link #getClassId() class-ID} as {@link #getObjectId() object-ID} and
   * {@link net.sf.mmm.data.api.reflection.DataClass#CLASS_ID} as
   * {@link #getClassId() class-ID}.
   * 
   * @return the ID of the associated content-class.
   */
  DataId getDataClassId();

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
