/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.base;

import net.sf.mmm.util.context.api.GenericContext;

/**
 * This is an implementation of the {@link net.sf.mmm.util.context.api.GenericContext} interface that is an
 * immutable view that delegates to another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ImmutableGenericContext extends AbstractGenericContextProxy {

  /** the {@link #getContext() "delegate instance"} this proxy points to. */
  private final GenericContext delegate;

  /**
   * The constructor.
   * 
   * @param environment is the {@link #getContext() "delegate instance"} this proxy points to.
   */
  public ImmutableGenericContext(GenericContext environment) {

    super();
    this.delegate = environment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericContext getContext() {

    return this.delegate;
  }

}
