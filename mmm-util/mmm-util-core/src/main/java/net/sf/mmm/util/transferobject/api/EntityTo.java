/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.entity.api.MutableRevisionedEntity;
import net.sf.mmm.util.entity.api.PersistenceEntity;

/**
 * This is the abstract base class for an {@link DataTo DTO} corresponding to a
 * {@link net.sf.mmm.util.entity.api.PersistenceEntity}. Classes derived from this class should carry the
 * suffix <code>Eto</code>. <br>
 * For additional details and an example consult the {@link net.sf.mmm.util.transferobject.api package
 * JavaDoc}.
 *
 * @param <ID> is the type of the {@link #getId() primary key}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class EntityTo<ID> extends DataTo implements MutableRevisionedEntity<ID> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3039958170310580721L;

  /** @see #getId() */
  private ID id;

  /** @see #getModificationCounter() */
  private int modificationCounter;

  /** @see #getRevision() */
  private Number revision;

  /**
   * @see #getModificationCounter()
   */
  private transient GenericEntity<ID> persistentEntity;

  /**
   * The constructor.
   */
  public EntityTo() {

    super();
    this.revision = LATEST_REVISION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ID getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(ID id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   *
   * <br>
   * <b>NOTE:</b><br>
   * {@link EntityTo} can carry a hidden reference to a {@link net.sf.mmm.util.entity.api.PersistenceEntity}.
   * This is set by
   * {@link TransferObjectUtil#convertFromEntity(net.sf.mmm.util.entity.api.PersistenceEntity, Class)} and
   * related functions. In such case the modification counter is updated from that reference by this method as
   * an intended side effect. This is done because JPA implementations will typically update the modification
   * counter only after the transaction has been committed and the conversion of an entity to {@link EntityTo
   * ETO} typically happens before. This approach is better and more robust than
   * {@link javax.persistence.EntityManager#flush() flushing}.
   */
  @Override
  public int getModificationCounter() {

    if (this.persistentEntity != null) {
      // JPA implementations will update modification counter only after the transaction has been committed.
      // Conversion will typically happen before and would result in the wrong (old) modification counter.
      // Therefore we update the modification counter here (that has to be called before serialization takes
      // place).
      this.modificationCounter = this.persistentEntity.getModificationCounter();
    }
    return this.modificationCounter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModificationCounter(int version) {

    this.modificationCounter = version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Number getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRevision(Number revision) {

    this.revision = revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + this.modificationCounter;
    result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    EntityTo<?> other = (EntityTo<?>) obj;
    if (this.id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!this.id.equals(other.id)) {
      return false;
    }
    if (this.modificationCounter != other.modificationCounter) {
      return false;
    }
    if (this.revision == null) {
      if (other.revision != null) {
        return false;
      }
    } else if (!this.revision.equals(other.revision)) {
      return false;
    }
    return true;
  }

  /**
   * Method to extend {@link #toString()} logic.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the
   *        string representation.
   */
  @Override
  protected void toString(StringBuilder buffer) {

    super.toString(buffer);
    if (this.id != null) {
      buffer.append("[id=");
      buffer.append(this.id);
      buffer.append("]");
    }
    if (this.revision != null) {
      buffer.append("[rev=");
      buffer.append(this.revision);
      buffer.append("]");
    }
  }

  /**
   * Force update of modification counter for serialization.
   *
   * @param o is the {@link ObjectOutputStream} where to write the serialized object data to.
   * @throws IOException in case of an I/O error.
   */
  @SuppressWarnings("unused")
  private void writeobject(ObjectOutputStream o) throws IOException {

    // force updating of modification counter before serialization...
    getModificationCounter();
    o.defaultWriteObject();
  }

  /**
   * Inner class to grant access to internal {@link PersistenceEntity} reference of an {@link EntityTo}. Shall
   * only be used internally and never be external users.
   */
  public static class PersistentEntityAccess {

    /**
     * Sets the internal {@link PersistenceEntity} reference of the given {@link EntityTo}.
     *
     * @param <ID> is the generic type of the {@link GenericEntity#getId() ID}.
     * @param eto is the {@link EntityTo}.
     * @param persistentEntity is the {@link PersistenceEntity}.
     */
    protected <ID> void setPersistentEntity(EntityTo<ID> eto, PersistenceEntity<ID> persistentEntity) {

      assert ((eto.persistentEntity == null) || (persistentEntity == null));
      eto.persistentEntity = persistentEntity;
    }
  }
}
