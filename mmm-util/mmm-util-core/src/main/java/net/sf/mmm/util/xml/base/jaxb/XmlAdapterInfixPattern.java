/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.base.RegexInfixPatternCompiler;

/**
 * This class extends {@link XmlAdapterPattern} using {@link RegexInfixPatternCompiler}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlAdapterInfixPattern extends XmlAdapterPattern {

  /**
   * The constructor.
   */
  public XmlAdapterInfixPattern() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Pattern unmarshal(String pattern) throws Exception {

    return RegexInfixPatternCompiler.INSTANCE.compile(pattern);
  }

}
