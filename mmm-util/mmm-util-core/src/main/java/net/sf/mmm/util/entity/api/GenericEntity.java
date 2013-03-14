/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.api;

import java.io.Serializable;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a (persistent) entity, which is an object that is potentially stored in a
 * persistent store (typically a database and via JPA). Every non-abstract implementation of this interface is
 * simply called an <em>entity</em> or <em>business-object</em>. It is supposed to be a simple java-bean or in
 * words a {@link net.sf.mmm.util.pojo.api.Pojo}.<br>
 * This interface makes the following assumptions:
 * <ul>
 * <li>A {@link GenericEntity} is identified by a {@link #getId() primary key}.</li>
 * <li>A {@link GenericEntity} has a {@link #getModificationCounter() modification counter} for optimistic
 * locking (even though not strictly required - you could statically return 0).</li>
 * </ul>
 * <b>ATTENTION:</b><br>
 * An instance of this class can be one of the following:
 * <ul>
 * <li>a <b>{@link PersistenceEntity}</b><br/>
 * <li>a <b>{@link net.sf.mmm.util.transferobject.api.EntityTo transfer-object}</b><br/>
 * </ul>
 * In order to distinguish the above cases an application has an architecture that organizes the code in
 * technical layers (see classical 3-tier architecture) and business oriented slices (also called domains).
 * Therefore within the persistence layer instances should always be {@link PersistenceEntity persistence
 * entities} (independent from their actual state such as {@link PersistenceEntity#STATE_MANAGED managed}). On
 * the other hand in the usage layer (service or presentation layer) instances always need to be transfer
 * objects. Within the logic layer this may depend on the actual design. Our recommendation is to
 * {@link net.sf.mmm.util.transferobject.api.TransferObjectUtil#convertFromEntity(PersistenceEntity, Class)
 * convert} persistent entities into {@link net.sf.mmm.util.transferobject.api.EntityTo transfer-objects} when
 * exposing functionality to be used by other slices and allow to pass persistent entities within the same
 * slice to make use of lazy loading and other features. However, it is totally legal to use the same
 * implementation of {@link PersistenceEntity} also as logical transfer-objects by
 * {@link PersistenceEntity#STATE_DETACHED detaching} them (safest way is to create a clone/copy).<br/>
 * Further please note that transfer-objects for external services need to be separated from the actual
 * entities so they keep stable in case of internal changes.
 * 
 * @see javax.persistence.Entity
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface GenericEntity<ID> extends AttributeReadId<ID>, Serializable {

  /**
   * This method gets the unique identifier (primary key) of this entity. While this method is initially
   * defined in a generic way, it is strongly recommended to use {@link Long} as datatype for IDs.<br/>
   * Even if you want to have a {@link String}-based business-oriented identifier it is best practice to use a
   * {@link Long} as primary key and add the business identifier as additional field (with a unique
   * constraint). This way references to the entity will be a lot more compact and improve your performance in
   * JOINs or the like. However, there may be reasons to use other {@link net.sf.mmm.util.lang.api.Datatype
   * datatypes} for the ID. In any case the unique ID should be an immutable java-object that can be rebuild
   * from its {@link Object#toString() string-representation}.<br/>
   * Please note that if your ID has a specific syntax, semantic, formatting rules, etc. you should create a
   * custom {@link net.sf.mmm.util.lang.api.Datatype} for it. If it can easily be mapped to a {@link Long}
   * value you can still use {@link Long} here and provide a transient getter method that returns the your
   * custom {@link net.sf.mmm.util.lang.api.Datatype} from the {@link Long}.
   * 
   * @return the ID of this entity. It may be <code>null</code> if the object is
   *         {@link PersistenceEntity#STATE_NEW new} and no ID has manually been assigned.
   */
  @Override
  ID getId();

  /**
   * This method gets the current modification-counter of this entity. Whenever the object gets modified and
   * {@link javax.persistence.EntityManager#persist(Object) saved}, this counter will be increased. The
   * initial value after construction is <code>0</code>.<br>
   * If this feature is NOT supported for some reason, this method should always return <code>0</code>.
   * 
   * @see javax.persistence.Version
   * 
   * @return the current modification-counter.
   */
  int getModificationCounter();

}
