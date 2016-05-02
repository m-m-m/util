/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.process.api.ProcessUtil;

/**
 * This is the test-case for {@link ProcessUtilImpl} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ProcessUtilSpringTest extends ProcessUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcessUtil getProcessUtil() {

    return SpringContainerPool.getInstance().get(ProcessUtil.class);
  }

}
