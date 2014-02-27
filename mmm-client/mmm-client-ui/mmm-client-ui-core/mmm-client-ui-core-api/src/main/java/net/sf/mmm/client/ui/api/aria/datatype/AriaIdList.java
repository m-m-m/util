/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatypeBase;

/**
 * This class is a simple representation for a {@link List} of IDs. Each ID is pointing to a DOM element and
 * has to be compliant with the syntax for ID-Attribute in DOM/XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AriaIdList extends AbstractSimpleDatatypeBase<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = -690230679053352591L;

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   * 
   * @param idList is a single ID or a whitespace separated list of IDs.
   */
  public AriaIdList(String idList) {

    super();
    this.value = idList;
  }

  /**
   * The constructor.
   * 
   * @param ids are the IDs.
   */
  public AriaIdList(String... ids) {

    super();
    StringBuilder buffer = new StringBuilder();
    for (String id : ids) {
      if (buffer.length() > 0) {
        buffer.append(' ');
      }
      buffer.append(id);
    }
    this.value = buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * @return the {@link #getValue() value} as {@link List} of IDs.
   */
  public List<String> getIdList() {

    List<String> result = new ArrayList<String>();
    int start = 0;
    int end = 0;
    while (end < this.value.length()) {
      if (this.value.charAt(end) == ' ') {
        if (end > start) {
          String token = this.value.substring(start, end);
          result.add(token);
          start = end + 1;
        }
      }
      end++;
    }
    return result;
  }

}
