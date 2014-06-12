/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

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
   * <code>template</code>. It should create a deep-copy that does NOT contain references to the original
   * <code>template</code> objects so that modifications of the returned object will NOT have side-effects on
   * the original <code>template</code> object(s).
   *
   *
   * @param <TO> is the generic type of the {@link AbstractTransferObject}.
   * @param template is the {@link AbstractTransferObject} to clone. Must NOT be <code>null</code>.
   * @return a new instance with the same {@link #getClass() type} as the given <code>template</code>.
   */
  <TO extends AbstractTransferObject> TO clone(TO template);

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

}
