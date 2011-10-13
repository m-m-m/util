/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;


/**
 * This type is a simple collection of constants for the
 * {@link net.sf.mmm.data.api.reflection.DataField#getId() field IDs}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataFieldIds {

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObject#getId()}.
   */
  int ID_OBJECT_ID = 0;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataObject#getTitle()}.
   */
  int ID_OBJECT_TITLE = 6;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataNode#getParent()}.
   */
  int ID_NODE_PARENT = 20;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataSelection#isCacheable()}.
   */
  int ID_SELECTION_CACHEABLE = 30;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataSelectionTree#getChildren()}.
   */
  int ID_SELECTIONTREE_CHILDREN = 41;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.DataSelectionTree#isAbstract()}.
   */
  int ID_SELECTIONTREE_ABSTRACT = 42;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#getDeletedFlag()}
   * .
   */
  int ID_REFLECTIONOBJECT_DELETEDFLAG = 100;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#isDeleted()} .
   */
  int ID_REFLECTIONOBJECT_DELETED = 101;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#getJavaClass()}
   * .
   */
  int ID_REFLECTIONOBJECT_JAVACLASS = 102;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#getContentModifiers()}
   * .
   */
  int ID_REFLECTIONOBJECT_CONTENTMODIFIERS = 103;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject#isContentClass()}
   * .
   */
  int ID_REFLECTIONOBJECT_CONTENTCLASS = 104;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() Field ID} for
   * {@link net.sf.mmm.data.api.entity.resource.DataResource#getPath()}.
   */
  int ID_RESOURCE_PATH = 0;

}
