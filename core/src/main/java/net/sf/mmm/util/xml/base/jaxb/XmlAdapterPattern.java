/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This is an implementation of {@link XmlAdapter} for mapping {@link Pattern}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlAdapterPattern extends XmlAdapter<String, Pattern> {

  /**
   * The constructor.
   */
  public XmlAdapterPattern() {

    super();
  }

  @Override
  public String marshal(Pattern pattern) throws Exception {

    if (pattern == null) {
      return null;
    } else {
      return pattern.pattern();
    }
  }

  @Override
  public Pattern unmarshal(String pattern) throws Exception {

    if (pattern == null) {
      return null;
    } else {
      return Pattern.compile(pattern);
    }
  }

}
