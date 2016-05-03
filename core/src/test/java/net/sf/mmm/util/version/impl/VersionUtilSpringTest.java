/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.version.api.VersionUtil;

/**
 * This is the test-case for {@link VersionUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class VersionUtilSpringTest extends VersionUtilTest {

  @Override
  public VersionUtil getVersionUtil() {

    return SpringContainerPool.getInstance().get(VersionUtil.class);
  }

}
