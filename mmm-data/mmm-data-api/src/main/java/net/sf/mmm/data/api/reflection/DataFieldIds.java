/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

/**
 * This type is a simple collection of constants for the
 * {@link net.sf.mmm.data.api.reflection.DataField#getId() field IDs}. The
 * centralization of these IDs helps to establish their uniqueness. If you want
 * to extend the data model of mmm, you should define your own type (class or
 * interface) for this purpose that may extend this interface. Please be aware
 * that custom IDs for for end users have to be greater or equal to
 * {@link net.sf.mmm.data.api.datatype.DataId#OBJECT_ID_MINIMUM_CUSTOM}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldIds {

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObjectView#getId()}.
   */
  long ID_OBJECT_ID = 0;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObjectView#getDataClassId()}.
   */
  long ID_OBJECT_DATA_CLASS_ID = 1;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObjectView#getTitle()}.
   */
  long ID_OBJECT_TITLE = 6;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObjectView#isDeleted()}.
   */
  long ID_OBJECT_DELETED = 7;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObjectView#getDeletedFlag()}.
   */
  long ID_OBJECT_DELETED_FLAG = 8;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataNodeView#getParent()}.
   */
  long ID_NODE_PARENT = 20;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataSelectionGenericTreeView#isSelectable()}.
   */
  long ID_SELECTION_SELECTABLE = 42;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataSelectionGenericTreeView#getChildren()}.
   */
  long ID_SELECTIONTREE_CHILDREN = 41;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#getJavaClass()}
   * .
   */
  long ID_REFLECTIONOBJECT_JAVACLASS = 102;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#getModifiers()}
   * .
   */
  long ID_REFLECTIONOBJECT_MODIFIERS = 103;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#isDataClass()} .
   */
  long ID_REFLECTIONOBJECT_DATACLASS = 104;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getSuperClass()}.
   */
  long ID_CLASS_SUPERCLASS = 120;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getDeclaredFields()}.
   */
  long ID_CLASS_DECLAREDFIELDS = 121;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getFields()}.
   */
  long ID_CLASS_FIELDS = 122;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getSubClasses()}.
   */
  long ID_CLASS_SUBCLASSES = 123;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()}.
   */
  long ID_CLASS_MODIFIERS = 126;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass#getGroupVersion()}.
   */
  long ID_CLASS_GROUPVERSION = 127;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.entity.resource.DataEntityResourceView#getPath()}
   * .
   */
  long ID_RESOURCE_PATH = 200;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * <code>DataContact.getContactInfos()</code>.
   */
  long ID_CONTACT_CONTACT_INFOS = 400;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * <code>DataContact.getRelatedContacts()</code>.
   */
  long ID_CONTACT_RELATED_CONTACTS = 401;

}
