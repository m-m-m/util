/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.file.api.FileUtil;

/**
 * This is the test-case for {@link FileUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileUtilSpringTest extends FileUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected FileUtil getFileUtil() {

    return SpringContainerPool.getInstance().getComponent(FileUtil.class);
  }

}
