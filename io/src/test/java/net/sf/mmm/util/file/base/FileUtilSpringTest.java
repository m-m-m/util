/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.impl.spring.UtilFileSpringConfig;

/**
 * This is the test-case for {@link FileUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilFileSpringConfig.class })
public class FileUtilSpringTest extends FileUtilTest {

  @Inject
  private FileUtil fileUtil;

  @Override
  protected FileUtil getFileUtil() {

    return this.fileUtil;
  }

}
