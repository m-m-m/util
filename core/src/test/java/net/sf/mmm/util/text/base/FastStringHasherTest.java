/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.text.api.StringHasher;

/**
 * This is the test-case for {@link FastStringHasher}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FastStringHasherTest extends AbstractStringHasherTest {

  @Override
  protected StringHasher getStringHasher() {

    return new FastStringHasher();
  }

}
