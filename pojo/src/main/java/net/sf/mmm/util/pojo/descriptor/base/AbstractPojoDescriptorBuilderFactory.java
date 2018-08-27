/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the abstract base implementation of the {@link PojoDescriptorBuilderFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoDescriptorBuilderFactory extends AbstractLoggableComponent implements PojoDescriptorBuilderFactory {

  private static AbstractPojoDescriptorBuilderFactory instance;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilderFactory() {

    super();
  }

  /**
   * @param instance is the instance to set
   */
  protected static void setInstance(AbstractPojoDescriptorBuilderFactory instance) {

    if (AbstractPojoDescriptorBuilderFactory.instance != null) {
      instance.getLogger().warn("Duplicate instantiation of PojoDescriptorBuilderFactory.");
    }
    AbstractPojoDescriptorBuilderFactory.instance = instance;
  }

  /**
   * This method gets the singleton instance of {@link PojoDescriptorBuilderFactory}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the static instance of this class. May be {@code null} if not initialized.
   */
  public static PojoDescriptorBuilderFactory getInstance() {

    return instance;
  }

  @Override
  public PojoDescriptorBuilder createPrivateFieldDescriptorBuilder() {

    return createDescriptorBuilder(null, VisibilityModifier.PRIVATE);
  }

  @Override
  public PojoDescriptorBuilder createPublicMethodDescriptorBuilder() {

    return createDescriptorBuilder(VisibilityModifier.PUBLIC, null);
  }

}
