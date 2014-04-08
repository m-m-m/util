/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.entity.api.Entity;

/**
 * This is a limited subset of {@link TransferObjectUtil} that is GWT compatible.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@ComponentSpecification
public interface TransferObjectUtilLimited {

  /**
   * This method creates a {@link Object#clone() clone} of the {@link AbstractTransferObject} given as
   * <code>template</code>.
   *
   * @param <TO> is the generic type of the {@link AbstractTransferObject}.
   * @param template is the {@link AbstractTransferObject} to clone. Must NOT be <code>null</code>.
   * @return a new instance with the same {@link #getClass() type} as the given <code>template</code>.
   */
  <TO extends AbstractTransferObject> TO clone(TO template);

  /**
   * This method creates a deep-copy of the {@link AbstractTransferObject} given as <code>template</code>.
   * This may do the same as {@link #clone(AbstractTransferObject)} but it will ensure to create a deep-copy
   * that does NOT contain references to the original <code>template</code> objects. Therefore modifications
   * of the returned object will NOT have any side-effects on the original <code>template</code> objects.
   *
   * @param <TO> is the generic type of the {@link AbstractTransferObject}.
   * @param template is the {@link AbstractTransferObject} to clone. Must NOT be <code>null</code>.
   * @return a new instance with the same {@link #getClass() type} as the given <code>template</code>.
   */
  <TO extends AbstractTransferObject> TO copy(TO template);

  /**
   * This method creates a new instance of the {@link AbstractTransferObject} given as <code>template</code>.
   * A simple implementation my just return
   * <code>template.{@link #getClass()}.{@link Class#newInstance() newInstance()}</code>. However, an
   * implementation may also be generated to be GWT compatible.
   *
   * @param <TO> is the generic type of the {@link AbstractTransferObject}.
   * @param template is the {@link AbstractTransferObject} to create a new instance of. Must NOT be
   *        <code>null</code>.
   * @return a new instance with the same {@link #getClass() type} as the given <code>template</code>.
   */
  <TO extends AbstractTransferObject> TO newInstance(TO template);

  /**
   * This method copies the attributes from a new instance of the {@link Entity} given as
   * <code>template</code>. If a property is copied whose value is a mutable object (not a
   * {@link net.sf.mmm.util.lang.api.Datatype} or the like), that object also has to be copied/cloned.<br/>
   * <b>ATTENTION:</b><br/>
   * This is a generic method that should be used with care. Due to limitations in Java's generic type-system,
   * the signature of this method cannot be declared in a type-safe way. You have to ensure that
   * <code>source</code> is an instance of the {@link Entity}-interface of &lt;TO&gt;.
   *
   * @param <ENTITY> is the generic type of the source {@link Entity}.
   * @param <TO> is the generic type of the target {@link TransferObject}.
   * @param source is the {@link Entity} to copy the attributes from. Will NOT be modified in any way. Must
   *        NOT be <code>null</code>.
   * @param target is the {@link AbstractTransferObject} to copy the attributes to. Must NOT be
   *        <code>null</code>.
   */
  <ENTITY extends Entity, TO extends AbstractTransferObject> void copyProperties(ENTITY source, TO target);

}
