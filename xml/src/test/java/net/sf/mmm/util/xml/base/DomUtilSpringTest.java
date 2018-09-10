/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.impl.spring.UtilXmlSpringConfigBase;

/**
 * This is the test-case for {@link DomUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilXmlSpringConfigBase.class })
public class DomUtilSpringTest extends DomUtilTest {

  @Inject
  private DomUtil domUtil;

  @Override
  public DomUtil getDomUtil() {

    return this.domUtil;
  }

}
