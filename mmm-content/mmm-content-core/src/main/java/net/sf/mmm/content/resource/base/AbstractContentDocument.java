/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.base;

import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.resource.api.ContentDocument;
import net.sf.mmm.content.resource.api.ContentResource;

/**
 * This is the implementation of the {@link ContentDocument} interface. You can
 * extend this class to define a new custom entity.<br>
 * <b>ATTENTION:</b><br>
 * Please follow these rules when designing your custom entity (so it can be
 * mapped to an according
 * {@link net.sf.mmm.content.model.api.ContentClass ContentClass}:
 * <ul>
 * <li>Extend {@link AbstractContentDocument} directly or indirectly.</li>
 * <li>Declare a public non-arg constructor.</li>
 * <li>The class modifiers <code>abstract</code> and <code>final</code> are
 * mapped directly.</li>
 * <li>The {@link ContentClass} is marked as
 * {@link ContentClass#getDeletedFlag() deleted} if the according class is
 * annotated as {@link Deprecated} (as annotation and NOT via javadoc!).</li>
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
 * <li>If there is no public getter, the field will be
 * {@link net.sf.mmm.content.model.api.FieldModifiers#isReadOnly() read-only}.</li>
 * <li>If there is no getter at all, the field will be
 * {@link net.sf.mmm.content.model.api.FieldModifiers#isTransient() transient}.</li>
 * <li>The {@link ContentField} is marked as
 * {@link ContentClass#getDeletedFlag() deleted} if the according class is
 * annotated as {@link Deprecated} (as annotation and NOT via javadoc!).</li>
 * </ul>
 * For specific mapping settings parts of <code>javax.persistence</code>
 * annotations can be used (TODO: explain). Please note that this does NOT mean
 * that the full specification of these annotations is supported.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = 23, name = "ContentDocument")
public abstract class AbstractContentDocument extends AbstractContentResource implements
    ContentDocument {

  /** @see #getParent() */
  private ContentResource parent;

  /**
   * The constructor.
   */
  public AbstractContentDocument() {

    super();
  }

  /**
   * This method sets the {@link #getParent() parent} folder.<br>
   * <b>ATTENTION:</b><br>
   * It is an internal method and therefore protected. To move the resource
   * please use the repository API.
   * 
   * @param parent the parent to set.
   */
  protected void setParent(ContentResource parent) {

    this.parent = parent;
  }

}
