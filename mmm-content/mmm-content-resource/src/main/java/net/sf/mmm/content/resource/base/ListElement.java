/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark an entity that represents a list of elements.<br>
 * If a custom entity is derived from
 * {@link net.sf.mmm.content.resource.base.AbstractContentDocument AbstractContentDocument}
 * that carries this annotation, then all instances of the entity are cached in
 * a list-model. If another entity has a reference on such entity the GUI can
 * show a list using the cached list-model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface ListElement {

  /**
   * This method determines if the list should be cached in memory. Therefore
   * all instances of this entity will be kept in a list-model directly
   * available without repository (DB) access.
   * 
   * @return <code>true</code> if the tree should be cached completely in
   *         main-memory.
   */
  boolean cacheable() default false;

}
