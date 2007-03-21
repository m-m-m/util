/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface that
 * allows to insert an existing {@link #delegate configuration} at another place
 * in the configuration tree.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EntryPointConfiguration extends ConfigurationProxy {

  /** the parent configuration */
  private final AbstractConfiguration parent;

  /** the entry point */
  private final AbstractConfiguration delegate;

  /**
   * The constructor.
   * 
   * @param parentConfiguration
   *        is the parent of this configuration.
   * @param entryPoint
   *        is the configuration that should appear as child of the
   *        <code>parentConfiguration</code>.
   */
  public EntryPointConfiguration(AbstractConfiguration parentConfiguration,
      AbstractConfiguration entryPoint) {

    super();
    this.parent = parentConfiguration;
    this.delegate = entryPoint;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfiguration getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getParent() {

    return this.parent;
  }

}
