/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.text.api.JustificationBuilder;
import net.sf.mmm.util.text.impl.spring.UtilTextSpringConfig;

/**
 * This is the test-case for {@link JustificationBuilder} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilTextSpringConfig.class })
public class JustificationBuilderSpringTest extends JustificationBuilderTest {

  @Inject
  private JustificationBuilder justificationBuilder;

  @Override
  protected JustificationBuilder getJustificationBuilder() {

    return this.justificationBuilder;
  }

}
