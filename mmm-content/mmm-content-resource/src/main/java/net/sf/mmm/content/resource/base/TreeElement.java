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
 * This annotation is used to mark an entity that represents a list of elements.
 * Therefore the getter of a
 * {@link net.sf.mmm.content.model.api.ContentField field} that represents the
 * parent link of a tree. The return type of the annotated getter should
 * therfore be the type of the entity declaring the getter (or an entity that
 * inherits from it). An entity may only have one field with this attribute.<br>
 * If a custom entity derived from
 * {@link net.sf.mmm.content.resource.base.AbstractContentDocument AbstractContentDocument}
 * has exactly one getter with this annotation, then it is assumed, that the
 * entity represents a tree. This has the following effects:
 * <ul>
 * <li>If such entity has to be selected, the GUI can show a tree-widget (e.g.
 * if another entity has a reference on it).</li>
 * <li>Other fields can be inherited from the parent (TODO: inherit
 * annotation?).</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface TreeElement {

  /**
   * This method determines if the tree should be cached in memory. Therefore
   * all instances of this entity will be kept in a tree-model directly
   * available without repository (DB) access.
   * 
   * @return <code>true</code> if the tree should be cached completely in
   *         main-memory.
   */
  boolean cacheable() default false;

}
