/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api;

import java.util.Collection;
import java.util.List;

import net.sf.mmm.data.api.ContentClassAnnotation;
import net.sf.mmm.data.api.ContentFieldAnnotation;
import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.api.ContentSelectionTree;

/**
 * This is the interface for the type of an entity. It reflects the structure of
 * the {@link net.sf.mmm.data.api.ContentObject content-object} types in an
 * object-oriented way. <br>
 * A content-class is the analogy to a {@link java.lang.Class} that reflects a
 * {@link java.lang.Object}. <br>
 * A content-class may be used to render a generic UI editor, synchronize the
 * schema of the persistence store (e.g. a DB), etc. <br>
 * 
 * @see net.sf.mmm.data.api.ContentObject
 * @see ContentReflectionService#getContentClass(net.sf.mmm.data.api.ContentObject)
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentClass.CLASS_ID, title = ContentClass.CLASS_NAME, isFinal = true)
public interface ContentClass<CLASS extends ContentObject> extends ContentReflectionObject<CLASS>,
    ContentSelectionTree<ContentClass<? extends ContentObject>> {

  /**
   * The {@link net.sf.mmm.data.api.ContentObject#getTitle() name} of the
   * {@link ContentClass} reflecting this type.
   */
  String CLASS_NAME = "ContentClass";

  /**
   * The {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link ContentClass} reflecting this type.
   */
  short CLASS_ID = 2;

  /**
   * The first {@link net.sf.mmm.data.datatype.api.ContentId#getClassId()
   * class-ID} that can be used for custom classes. All
   * {@link net.sf.mmm.data.datatype.api.ContentId#getClassId() class-ID}
   * lower than this are reserved for {@link ContentClassModifiers#isSystem()
   * system} classes.
   */
  int CLASS_ID_MINIMUM_CUSTOM = 4096;

  /** the xml tag for a {@link ContentClass}. */
  String XML_TAG_CLASS = "Class";

  /**
   * The name of the {@link net.sf.mmm.data.reflection.api.ContentField
   * field} {@link #getSuperClass() superClass} for generic access.
   */
  String FIELD_NAME_SUPER_CLASS = "superClass";

  /**
   * The name of the {@link net.sf.mmm.data.reflection.api.ContentField
   * field} {@link #getSubClasses() subClasses} for generic access.
   */
  String FIELD_NAME_SUB_CLASSES = "subClasses";

  /**
   * The name of the {@link net.sf.mmm.data.reflection.api.ContentField
   * field} {@link #getFields() fields} for generic access.
   */
  String FIELD_NAME_FIELDS = "fields";

  /**
   * The name of the {@link net.sf.mmm.data.reflection.api.ContentField
   * field} {@link #getDeclaredFields() declaredFields} for generic access.
   */
  String FIELD_NAME_DECLARED_FIELDS = "declaredFields";

  /**
   * the attribute for the {@link #getTitle() name}.
   */
  String XML_TAG_CONTENT_MODEL = "content-model";

  // /**
  // * {@inheritDoc}
  // *
  // * @return the {@link #getSuperClass() super-class} or the folder
  // * {@link ContentObject#getChildren() containing} the root-class.
  // */
  // ContentClass<? extends ContentClass> getParent();
  //
  // /**
  // * {@inheritDoc}
  // *
  // * @return the {@link #getSubClasses() sub-classes}.
  // */
  // List<ContentClass<? extends ContentClass>> getChildren();

  /**
   * This method gets an iterator of all fields declared by this class. This
   * does NOT include fields inherited from the {@link #getSuperClass()
   * super-class} except they are overridden by this class. An inherited field
   * can be overridden (if supported by the {@link ContentReflectionService
   * content-model}) in order to declare it more specific. Then the type of the
   * field is a subtype of the field that is overridden or the validator is more
   * restrictive.<br>
   * 
   * @return a (read-only) collection of all declared fields.
   */
  Collection<? extends ContentField<CLASS, ?>> getDeclaredFields();

  /**
   * This method gets the declared field with the given
   * {@link ContentField#getTitle() name}. Declared means that the field is
   * {@link ContentField#getInitiallyDefiningClass() initially defined} or
   * overridden in this class.<br>
   * An inherited field can be overridden (if supported by the
   * {@link ContentReflectionService content-model}) in order to declare it more
   * specific (typically the {@link ContentField#getFieldType() field-type} is
   * specialized). Such field can be identified via
   * {@link ContentField#getInitiallyDefiningClass()}.
   * 
   * @see #getField(String)
   * 
   * @param name is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such field
   *         is declared by this class.
   */
  ContentField<CLASS, ?> getDeclaredField(String name);

  /**
   * This method gets the field with the given {@link ContentField#getTitle()
   * name}. A field is either {@link #getDeclaredFields() declared} in this
   * class or inherited from a {@link #getSuperClass() super-class}.
   * 
   * @param name is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such field
   *         exists for this class.
   */
  ContentField<? super CLASS, ?> getField(String name);

  /**
   * This method gets all fields defined in this class or inherited by the
   * super-class(es). An inherited field can be identified via
   * {@link ContentField#getDeclaringClass()}.<br>
   * <b>ATTENTION:</b><br>
   * The {@link Collection#size()} method of the returned instance may be very
   * expensive. Please avoid unnecessary or repetitive calls.
   * 
   * @return a (read-only) collection of fields of this class.
   */
  Collection<? extends ContentField<? super CLASS, ?>> getFields();

  /**
   * This method gets the super-class of this class. Like in java this class
   * inherits from its super-classes.<br>
   * This method exists only for expressiveness - it does the same as
   * {@link #getParent()}.
   * 
   * @return the super-class that is extended by this class or <code>null</code>
   *         if this is the root-class ({@link ContentClass} reflecting
   *         {@link net.sf.mmm.data.api.ContentObject}).
   */
  @ContentFieldAnnotation(id = 25, isReadOnly = true)
  ContentClass<? super CLASS> getSuperClass();

  /**
   * This method gets the list of all sub-classes.
   * 
   * @return an un-modifiable list of all sub-class.
   */
  List<? extends ContentClass<? extends CLASS>> getSubClasses();

  /**
   * {@inheritDoc}
   */
  ContentClassModifiers getContentModifiers();

  /**
   * This method determines is this class is a super class of the given class. <br>
   * 
   * @param contentClass is the class to compare with.
   * @return <code>true</code> if this class is a super-class of the given
   *         class.
   */
  boolean isSuperClassOf(ContentClass<?> contentClass);

  /**
   * This is the opposite of the method
   * {@link ContentClass#isSuperClassOf(ContentClass)}. This means that
   * <code>class1.isSubClassOf(class2)</code> is equal to
   * <code>class2.isSuperClassOf(class1)</code>.
   * 
   * @param contentClass is the class to compare with.
   * @return <code>true</code> if this class is a sub-class of the given class.
   */
  boolean isSubClassOf(ContentClass<?> contentClass);

  // /**
  // * This method determines if the {@link ContentObject entity} represented by
  // * this {@link ContentClass} is a {@link ContentObject#isFolder() folder}.
  // *
  // * @see ContentObject#isFolder()
  // *
  // * @return <code>true</code> if the represented entity is a folder,
  // * <code>false</code> if it is a leaf.
  // */
  // boolean isFolderClass();

  /**
   * This method determines if the {@link net.sf.mmm.data.api.ContentObject}s
   * of this {@link ContentClass} are revision-controlled. Fur such entity
   * historical {@link net.sf.mmm.data.api.ContentObject#getRevision()
   * revisions} can be stored in the history. <br>
   * If an entity that is NOT under revision-control is modified, every except
   * the current state is lost.
   * 
   * @see net.sf.mmm.data.api.ContentClassAnnotation#revisionControl()
   * 
   * @return <code>true</code> if this type is revisioned, <code>false</code>
   *         otherwise.
   */
  boolean isRevisionControlled();

}
