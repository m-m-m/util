/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.text.api.Singularizer;
import net.sf.mmm.util.text.impl.spring.UtilTextSpringConfig;

/**
 * This is the test-case for {@link EnglishSingularizer} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilTextSpringConfig.class })
public class EnglishSingularizerSpringTest extends EnglishSingularizerTest {

  @Inject
  private Singularizer singularizer;

  @Override
  public Singularizer getEnglishSingularizer() {

    return this.singularizer;
  }

}
