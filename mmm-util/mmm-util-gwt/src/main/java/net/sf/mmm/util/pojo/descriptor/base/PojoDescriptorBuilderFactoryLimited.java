/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory} for
 * a limited environment (GWT).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class PojoDescriptorBuilderFactoryLimited extends AbstractPojoDescriptorBuilderFactory {

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderFactoryLimited() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoDescriptorBuilder createDescriptorBuilder(VisibilityModifier methodVisibility,
      VisibilityModifier fieldVisibility) {

    if (fieldVisibility != null) {
      throw new NlsUnsupportedOperationException("createDescriptorBuilder@fieldVisibility:" + fieldVisibility);
    }
    if (methodVisibility != VisibilityModifier.PUBLIC) {
      throw new NlsUnsupportedOperationException("createDescriptorBuilder@methodVisibility:" + methodVisibility);
    }
    return AbstractPojoDescriptorBuilderLimited.getInstance();
  }

}
