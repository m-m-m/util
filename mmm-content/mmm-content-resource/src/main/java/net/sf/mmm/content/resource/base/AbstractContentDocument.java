/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import net.sf.mmm.content.resource.api.ContentDocument;

/**
 * This is the implementation of the {@link ContentDocument} interface. You can
 * extend this class to define a new "top-level" entity. For specific mapping
 * settings parts of <code>javax.persistence</code> annotations can be used.
 * Please note that this does NOT mean that the full specification of these
 * annotations is supported.
 * 
 * The following rules are used to map the class of a custom entity to an
 * according {@link net.sf.mmm.content.model.api.ContentClass ContentClass}:
 * <ul>
 * <li>The class modifiers <code>abstract</code> and <code>final</code> are
 * mapped directly.</li>
 * <li>The name of the
 * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} can be defined
 * by declaring a {@link String} constant (static and final field) named
 * <code>CLASS_NAME</code>. If NOT declared by the entity-class, the
 * {@link Class#getSimpleName() simple name} of the class will be used.</li>
 * <li>The {@link net.sf.mmm.content.value.base.SmartId#getClassId() class-id}
 * of the {@link net.sf.mmm.content.model.api.ContentClass ContentClass} has to
 * be defined by declaring an <code>int</code> constant (static and final
 * field) named <code>CLASS_ID</code>.</li>
 * <li>Public getters indicate a
 * {@link net.sf.mmm.content.model.api.ContentField field}.</li>
 * <li>The modifiers <code>final</code> and <code>static</code> from the
 * getter will be mapped directly.</li>
 * <li>If no according setter is found for a field, it will be
 * {@link net.sf.mmm.content.model.api.FieldModifiers#isReadOnly() read-only}
 * and
 * {@link net.sf.mmm.content.model.api.FieldModifiers#isTransient() transient}.</li>
 * </ul>
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentDocument extends AbstractContentResource implements
    ContentDocument {

  /**
   * The constructor.
   */
  public AbstractContentDocument() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of the entity.
   */
  public AbstractContentDocument(String name) {

    super(name);
  }

}
