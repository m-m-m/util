/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.text.api.JustificationBuilder;

/**
 * This is the test-case for {@link JustificationBuilder} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public class JustificationBuilderSpringTest extends JustificationBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected JustificationBuilder getJustificationBuilder() {

    return SpringContainerPool.getInstance().get(JustificationBuilder.class);
  }

}
