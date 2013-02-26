/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.api.DataNode;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.DataSelection;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for an object reflecting the content model. It can be either a {@link DataClass} or a
 * {@link DataField}.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass() class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataReflectionObject.CLASS_ID, title = DataReflectionObject.CLASS_TITLE, isExtendable = BooleanEnum.FALSE)
public abstract interface DataReflectionObject<CLASS> extends DataNode<DataClass<? extends DataObject>>, DataSelection {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the {@link DataClass} reflecting
   * this type.
   */
  long CLASS_ID = DataClassIds.ID_REFLECTIONOBJECT;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the {@link DataClass} reflecting this
   * type.
   */
  String CLASS_TITLE = "DataReflectionObject";

  /**
   * The name of the {@link net.sf.mmm.data.api.reflection.DataField field} {@link #getModifiers() modifiers}
   * for generic access.
   */
  String FIELD_NAME_MODIFIERS = "contentModifiers";

  /**
   * This method gets the java-class of the reflected object. The &lt;CLASS&gt; will typically be a subclass
   * of {@link net.sf.mmm.data.api.DataObjectView} or at least
   * {@link net.sf.mmm.util.entity.api.GenericEntity}. However for ultimate flexibility and freedom the type
   * is not bound in its generic declaration. This allows to use this API even with third-party entities that
   * do not implement such interfaces.<br/>
   * <b>ATTENTION:</b><br>
   * This API allows to reflect both a statically typed as well as a dynamic content-model or even mixed
   * forms. While in statically typed models every {@link DataClass} represents a dedicated java-class, in
   * dynamic models, multiple, most or even all {@link DataClass} are bound to the same (generic) java-class.
   * In the latter case the java-class is a more generic object (e.g. based on a {@link java.util.Map}) while
   * static types are typically java beans.
   * 
   * @return the java-class.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_JAVACLASS, isFinal = BooleanEnum.TRUE, isReadOnly = BooleanEnum.TRUE)
  Class<CLASS> getJavaClass();

  /**
   * This method gets the modifiers of this object. The modifiers are a set of flags.
   * 
   * @see DataClass#getModifiers()
   * @see DataField#getModifiers()
   * 
   * @return the objects modifiers.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_MODIFIERS, isReadOnly = BooleanEnum.TRUE)
  DataModifiers getModifiers();

  /**
   * This method determines if this is a {@link DataClass content-class} or a {@link DataField content-field}.
   * It is allowed to cast this object according to the result of this method.
   * 
   * @return <code>true</code> if this is a {@link DataClass content-class}, <code>false</code> if this is a
   *         {@link DataField content-field}.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_REFLECTIONOBJECT_DATACLASS, isStatic = BooleanEnum.TRUE, isFinal = BooleanEnum.TRUE, isReadOnly = BooleanEnum.TRUE)
  boolean isDataClass();

}
