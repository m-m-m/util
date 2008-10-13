/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the abstract base implementation of the
 * {@link PojoDescriptorBuilderFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilderFactory extends AbstractLoggable implements
    PojoDescriptorBuilderFactory {

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilderFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoDescriptorBuilder createPrivateFieldDescriptorBuilder() {

    return createDescriptorBuilder(null, VisibilityModifier.PRIVATE);
  }

  /**
   * {@inheritDoc}
   */
  public PojoDescriptorBuilder createPublicMethodDescriptorBuilder() {

    return createDescriptorBuilder(VisibilityModifier.PUBLIC, null);
  }

}
