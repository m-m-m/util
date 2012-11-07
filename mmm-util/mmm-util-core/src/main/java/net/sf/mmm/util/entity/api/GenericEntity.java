/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a (persistent) entity, which is an object that is potentially stored in a
 * persistent store (typically a database and via JPA). Every non-abstract implementation of this interface is
 * simply called an <em>entity</em>. It is supposed to be a simple java-bean. Typically it will have
 * {@link javax.persistence.Entity JPA annotations} but it may also be a regular transfer object.<br>
 * This interface makes the following assumptions:
 * <ul>
 * <li>A {@link GenericEntity} is identified by a {@link #getId() primary key}.</li>
 * <li>A {@link GenericEntity} has a {@link #getModificationCounter() modification counter} for optimistic
 * locking (even though not strictly required - you could statically return 0).</li>
 * </ul>
 * <b>ATTENTION:</b><br>
 * An instance of this class can be one of the following:
 * <ul>
 * <li><b>persistence entity</b><br/>
 * In this case the instance of this interface is {@link #STATE_MANAGED managed} by an
 * {@link javax.persistence.EntityManager} and can NOT be considered as a regular java-object. It is better to
 * think of a persistence entity as a direct view into the persistent store. Therefore you should never make
 * modifications (e.g. invoke setters or sort list properties) that are NOT intended to be persisted.<br>
 * Further the underlying persistence-code may create dynamic proxies for your entity. Therefore you should
 * NOT declare methods (getters and setters) as <code>final</code> except you exactly know what you are doing.
 * Additionally {@link #getClass()} may return a subclass of your entity that you never created (see
 * <code>PersistenceManager</code> in <code>mmm-persistence</code> for additional information and support).</li>
 * <li><b>transfer object</b><br/>
 * Here the instance is either a plain java-bean with no relation to JPA or it is a persistent entity in
 * {@link #STATE_NEW transient} state. In this case there are no restrictions and you can treat the instance
 * as a regular java bean.</li>
 * </ul>
 * In order to distinguish the above cases an application has an architecture that organizes the code in
 * technical layers (see classical 3-tier architecture) and business oriented slices (also called domains).
 * Therefore within the persistence layer instances should always be considered as persistence entities
 * (independent from their actual state such as {@link #STATE_MANAGED managed}). On the other hand in the
 * usage layer (service or presentation layer) instances always need to be transfer objects. Within the logic
 * layer this may depend on the actual design. Our recommendation is to convert persistent entities into
 * transfer objects when exposing functionality to be used by other slices and allow to pass persistent
 * entities within the same slice to make use of lazy loading and other features.<br/>
 * Further please note that transfer objects for external services need to be separated from the actual
 * entities so they keep stable in case of internal changes.
 * 
 * @see javax.persistence.Entity
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface GenericEntity<ID> extends AttributeReadId<ID> {

  /**
   * A {@link GenericEntity} is in the <em>new</em> (or transient) state when it is manually created via its
   * constructor (using <code>new</code>). In this state it can be considered as a regular java-object.
   * Whenever {@link javax.persistence.EntityManager#persist(Object) persist} or even
   * {@link javax.persistence.EntityManager#merge(Object) merge} is called, the state of the entity changes to
   * {@link #STATE_MANAGED managed}.
   */
  String STATE_NEW = "new";

  /**
   * A {@link GenericEntity} becomes <em>managed</em> when it is
   * {@link javax.persistence.EntityManager#persist(Object) persisted} or in case it is directly
   * {@link javax.persistence.EntityManager#find(Class, Object) retrieved} from the persistent store
   * (database). It will remain in the managed state until a state change is explicitly triggered via
   * {@link javax.persistence.EntityManager#detach(Object) detach} /
   * {@link javax.persistence.EntityManager#remove(Object) remove} or in case the transaction is committed. <br/>
   * <b>ATTENTION:</b><br/>
   * If managed entity is modified and the transaction gets committed, all modifications are written to the
   * persistent store. Be careful that you never make modifications that are NOT intended to be persisted.
   */
  String STATE_MANAGED = "managed";

  /**
   * If {@link javax.persistence.EntityManager#remove(Object) remove} is called on a {@link #STATE_MANAGED
   * managed} entity, its state changes to <code>removed</code> and the entity will be deleted from the
   * persistent store whenever the transaction is committed.
   */
  String STATE_REMOVED = "removed";

  /**
   * If {@link javax.persistence.EntityManager#detach(Object) detach} is called on a {@link #STATE_MANAGED
   * managed} entity, its state changes to <code>detached</code>. The same applies of the transaction is
   * committed or on manual invocations of {@link javax.persistence.EntityManager#clear() clear} or
   * {@link javax.persistence.EntityManager#close() close}.<br/>
   * If a relational property is {@link javax.persistence.FetchType#LAZY lazy} and has not been fetched
   * previously, calls to get this property will cause an exception. A detached entity can change its state
   * back to {@link #STATE_MANAGED managed} via {@link javax.persistence.EntityManager#merge(Object) merge}.
   */
  String STATE_DETACHED = "detached";

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
   * @return the ID of this entity. It may be <code>null</code> if the object is {@link #STATE_NEW new} and no
   *         ID has manually been assigned.
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
