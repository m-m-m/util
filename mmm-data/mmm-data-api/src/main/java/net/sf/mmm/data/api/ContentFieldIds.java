/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This type is a simple collection of constants for the
 * {@link net.sf.mmm.data.reflection.api.ContentField#getId() field IDs}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface ContentFieldIds {

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentObject#getId()}.
   */
  int ID_OBJECT_ID = 0;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentObject#getTitle()}.
   */
  int ID_OBJECT_TITLE = 6;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentNode#getParent()}.
   */
  int ID_NODE_PARENT = 20;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentSelection#isCacheable()}.
   */
  int ID_SELECTION_CACHEABLE = 30;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentSelectionTree#getChildren()}.
   */
  int ID_SELECTIONTREE_CHILDREN = 41;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link ContentSelectionTree#isAbstract()}.
   */
  int ID_SELECTIONTREE_ABSTRACT = 42;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject#getDeletedFlag()}
   * .
   */
  int ID_REFLECTIONOBJECT_DELETEDFLAG = 100;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject#isDeleted()}
   * .
   */
  int ID_REFLECTIONOBJECT_DELETED = 101;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject#getJavaClass()}
   * .
   */
  int ID_REFLECTIONOBJECT_JAVACLASS = 102;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject#getContentModifiers()}
   * .
   */
  int ID_REFLECTIONOBJECT_CONTENTMODIFIERS = 103;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject#isContentClass()}
   * .
   */
  int ID_REFLECTIONOBJECT_CONTENTCLASS = 104;

  /**
   * {@link net.sf.mmm.data.reflection.api.ContentField#getId() Field ID} for
   * {@link net.sf.mmm.data.entity.api.ContentResource#getPath()}.
   */
  int ID_RESOURCE_PATH = 0;

}
