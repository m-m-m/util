/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.datatype.AbstractDataId;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the implementation of the {@link DataId} interface for the ID of a
 * {@link net.sf.mmm.data.api.reflection.DataField ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataFieldId extends AbstractDataId {

  /** UID for serialization. */
  private static final long serialVersionUID = 2291237026928351257L;

  /** @see #POOL */
  private static final int POOL_SIZE = 256;

  /** @see #valueOf(long) */
  private static final DataFieldId[] POOL = new DataFieldId[POOL_SIZE];

  /** The minimum {@link #getObjectId() ID} for {@link DataFieldId}. */
  private static final Long ID_MINIMUM_VALUE = Long.valueOf(0);

  /** The maximum {@link #getObjectId() ID} for {@link DataFieldId}. */
  private static final Long ID_MAXIMUM_VALUE = Long.valueOf(Integer.MAX_VALUE);

  /** The source in case of an exception. */
  private static final String VALUE_SOURCE = "value ID";

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.DataIdManager} to create instances.
   * 
   * @param objectId is the {@link #getObjectId() object-ID} identifying the
   *        {@link DataField}.
   */
  public DataFieldId(long objectId) {

    super(objectId);
  }

  /**
   * {@inheritDoc}
   */
  public long getClassId() {

    return DataField.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public DataId getDataClassId() {

    return DataClassId.FIELD;
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
   * This method gets the {@link DataFieldId} instance for the given
   * <code>fieldUid</code>. A pool is used to store the ID instances for the
   * first N <code>field-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param fieldUid is the {@link #getObjectId() object-ID} of the requested
   *        {@link DataFieldId} instance.
   * @return the requested {@link DataClassId} instance.
   */
  public static DataFieldId valueOf(long fieldUid) {

    DataFieldId id;
    ValueOutOfRangeException.checkRange(Long.valueOf(fieldUid), ID_MINIMUM_VALUE, ID_MAXIMUM_VALUE,
        VALUE_SOURCE);
    // actually the uid should always be int...
    if (fieldUid < POOL_SIZE) {
      int uidInt = (int) fieldUid;
      id = POOL[uidInt];
      if (id == null) {
        id = new DataFieldId(fieldUid);
        POOL[uidInt] = id;
      }
    } else {
      id = new DataFieldId(fieldUid);
    }
    return id;
  }

}
