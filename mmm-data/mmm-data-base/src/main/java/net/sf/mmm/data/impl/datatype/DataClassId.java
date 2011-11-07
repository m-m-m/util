/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.base.datatype.AbstractDataId;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the implementation of the {@link DataId} interface for the ID of a
 * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataClassId extends AbstractDataId {

  /** UID for serialization. */
  private static final long serialVersionUID = -7638229489656133262L;

  /** @see #POOL */
  private static final int POOL_SIZE = 256;

  /** @see #valueOf(long) */
  private static final DataClassId[] POOL = new DataClassId[POOL_SIZE];

  /** The minimum {@link #getObjectId() ID} for {@link DataClassId}. */
  private static final Long ID_MINIMUM_VALUE = Long.valueOf(0);

  /** The maximum {@link #getObjectId() ID} for {@link DataClassId}. */
  private static final Long ID_MAXIMUM_VALUE = Long.valueOf(Integer.MAX_VALUE);

  /** The source in case of an exception. */
  private static final String VALUE_SOURCE = "class ID";

  /**
   * the id of the root {@link net.sf.mmm.data.api.reflection.DataClass
   * ContentClass} (the class that all other classes are derived from).
   */
  public static final DataClassId ROOT = valueOf(DataObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject
   * content-reflection-object}.
   */
  public static final DataClassId REFELCTION = valueOf(DataReflectionObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects itself (Like {@link Class} in java).
   */
  public static final DataClassId CLASS = valueOf(DataClass.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.api.reflection.DataField content-field}.
   */
  public static final DataClassId FIELD = valueOf(DataField.CLASS_ID);

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.DataIdManager} to create instances.
   * 
   * @param objectId is the {@link #getObjectId() object-ID} identifying the
   *        {@link DataClass}.
   */
  public DataClassId(long objectId) {

    super(objectId);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getDataClassId() {

    return CLASS;
  }

  /**
   * {@inheritDoc}
   */
  public long getClassId() {

    return DataClass.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getStoreId() {

    return 0;
  }

  /**
   * This method gets the {@link DataClassId} instance for the given
   * <code>classUid</code>. A pool is used to store the ID instances for the
   * first N <code>class-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param classUid is the {@link #getObjectId() object-ID} of the requested
   *        {@link DataClassId} instance.
   * @return the requested {@link DataClassId} instance.
   */
  public static DataClassId valueOf(long classUid) {

    DataClassId id;
    ValueOutOfRangeException.checkRange(Long.valueOf(classUid), ID_MINIMUM_VALUE, ID_MAXIMUM_VALUE,
        VALUE_SOURCE);
    if (classUid < POOL_SIZE) {
      int uidInt = (int) classUid;
      id = POOL[uidInt];
      if (id == null) {
        id = new DataClassId(classUid);
        POOL[uidInt] = id;
      }
    } else {
      id = new DataClassId(classUid);
    }
    return id;
  }

}
