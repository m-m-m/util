/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

/**
 * This enum contains the available selection types for an
 * {@link net.sf.mmm.content.api.ContentObject entity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum EntitySelection {

  /**
   * By default the user needs to select an instance of an entity via its
   * {@link net.sf.mmm.content.api.ContentObject#getId() ID} or by navigating
   * its {@link net.sf.mmm.content.api.ContentObject#getPath() path}.
   */
  DEFAULT,

  /**
   * This selection type is used for an entity that represents a list of items
   * or with other words some sort of {@link Enum}. Then all instances of the
   * entity are cached in a list-model. If the user needs to select such an
   * entity, the GUI can show a list using the cached list-model. For example an
   * entity called <code>Color</code> may be declared as cached list and could
   * have instances like
   * {@link net.sf.mmm.content.api.ContentObject#getName() named}
   * <code>green</code>, <code>red</code> and <code>orange</code>.<br>
   * <b>ATTENTION:</b><br>
   * do NOT use this for entities that may have thousands of instances. <br>
   * <b>INFORMATION:</b><br>
   * Please note that you also have the ability to use a value rather than an
   * entity in this case. You can declare a java {@link Enum} and use it as type
   * of a field of an entity. If the instances (items) are fixed, this may be
   * the better choice.
   */
  LIST,

  /**
   * This selection type is used for an entity that represents a tree of nodes.
   * This implies that the
   * {@link net.sf.mmm.content.api.ContentObject#getParent() parent} field
   * points to the entity type itself. Additionally there has to be only one
   * instance where the
   * {@link net.sf.mmm.content.api.ContentObject#getParent() parent} is
   * <code>null</code>. This instance is called the root-node of the entity.<br>
   * If the user needs to select such an entity, the GUI can show a tree
   * starting with the root-node.
   */
  TREE

}
