/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.api.DataNode;
import net.sf.mmm.data.api.DataObject;

/**
 * This is the interface for an object reflecting the content model. It can be
 * either a {@link DataClass} or a {@link DataField}.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataReflectionObject.CLASS_ID, title = DataReflectionObject.CLASS_NAME)
public abstract interface DataReflectionObject<CLASS> extends
    DataNode<DataClass<? extends DataObject>> {

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() name} of the
   * {@link DataClass} reflecting this type.
   */
  String CLASS_NAME = "ContentReflectionObject";

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link DataClass} reflecting this type.
   */
  short CLASS_ID = 10;

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getModifiers() modifiers} for generic access.
   */
  String FIELD_NAME_MODIFIERS = "contentModifiers";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field}
   * {@link #getDeletedFlag() deletedFlag} for generic access.
   */
  String FIELD_NAME_DELETEDFLAG = "deletedFlag";

  /**
   * The deleted-flag is inherited so {@link #isDeleted()} will return
   * <code>true</code> if a {@link #getParent() parent object} is marked as
   * deleted.<br>
   * This method gets the deleted flag of this object. The method does not
   * inherit the flag.
   * 
   * @see #isDeleted()
   * 
   * @return the deleted flag.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_DELETEDFLAG, title = FIELD_NAME_DELETEDFLAG, isFinal = true)
  boolean getDeletedFlag();

  /**
   * This method determines if this content-object is marked as deleted. The
   * deleted status is like {@link Deprecated deprecation} in java.<br>
   * A deleted object can NOT be modified. No instances or sub-classes can be
   * created of a deleted class. Deleted fields are NOT visible in the editor.
   * If an object is deleted it can either be undeleted or destroyed (if a
   * {@link net.sf.mmm.data.api.reflection.DataClass} is destroyed then all
   * instances will be removed from the persistence store).<br>
   * Like deprecation a deletion is inherited from the {@link #getParent()
   * parent}.
   * 
   * @see #getDeletedFlag()
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_DELETED, isTransient = true)
  boolean isDeleted();

  /**
   * This method gets the java-class of the reflected object. The &lt;CLASS&gt;
   * will typically be a subclass of {@link net.sf.mmm.data.api.DataObject} or
   * at least {@link net.sf.mmm.persistence.api.PersistenceEntity}. However for
   * ultimate flexibility and freedom the type is not bound in its generic
   * declaration. This allows to use this API even with third-party entities
   * that do not implement such interfaces.<br/>
   * <b>ATTENTION:</b><br>
   * This API allows to reflect both a statically typed as well as a dynamic
   * content-model or even mixed forms. While in statically typed models every
   * {@link DataClass} represents a dedicated java-class, in dynamic models,
   * multiple, most or even all {@link DataClass} are bound to the same
   * (generic) java-class. In the latter case the java-class is a more generic
   * object (e.g. based on a {@link java.util.Map}) while static types are
   * typically java beans.
   * 
   * @return the java-class.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_JAVACLASS, isFinal = true, isReadOnly = true)
  Class<CLASS> getJavaClass();

  /**
   * This method gets the modifiers of this object. The modifiers are a set of
   * flags.
   * 
   * @see DataClass#getModifiers()
   * @see DataField#getModifiers()
   * 
   * @return the objects modifiers.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_MODIFIERS, isFinal = true, isReadOnly = true)
  DataModifiers getModifiers();

  /**
   * This method determines if this is a {@link DataClass content-class} or a
   * {@link DataField content-field}. It is allowed to cast this object
   * according to the result of this method.
   * 
   * @return <code>true</code> if this is a {@link DataClass content-class},
   *         <code>false</code> if this is a {@link DataField content-field}.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_CONTENTCLASS, isStatic = true, isFinal = true, isReadOnly = true)
  boolean isContentClass();

}
