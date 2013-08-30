/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import net.sf.mmm.util.gwt.base.rebind.AbstractIncrementalGenerator;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;

/**
 * This is the {@link AbstractIncrementalGenerator incremental GWT generator} to generate the implementation
 * of {@link PojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractPojoDescriptorGenerator extends AbstractIncrementalGenerator {

  /** @see #getConfiguration() */
  private PojoDescriptorGeneratorConfiguration configuration;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorGenerator() {

    super();
  }

  /**
   * @return the {@link PojoDescriptorGeneratorConfiguration} that is {@link #createConfiguration() created
   *         lazily}.
   */
  protected final PojoDescriptorGeneratorConfiguration getConfiguration() {

    if (this.configuration == null) {
      this.configuration = createConfiguration();
    }
    return this.configuration;
  }

  /**
   * This method creates the {@link PojoDescriptorGeneratorConfiguration}. Override here if you want to define
   * your own marker-interface.
   * 
   * @return the new instance of {@link PojoDescriptorGeneratorConfiguration}.
   */
  protected PojoDescriptorGeneratorConfiguration createConfiguration() {

    return new PojoDescriptorGeneratorConfigurationImpl();
  }

}
