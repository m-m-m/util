/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This class represents the property of a {@link net.sf.mmm.util.pojo.api.Pojo}
 * .<br>
 * It can be a <em>simple property</em> such as <code>fooBar</code>, an
 * <em>indexed property</em> such as <code>fooBar[42]</code> or a
 * <em>mapped property</em> such as <code>fooBar['my.key']</code>.<br>
 * This class contains the logic to parse such property and gives structured
 * access via the offered getters.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoProperty {

  /** @see #getName() */
  private final String name;

  /** @see #getIndex() */
  private final Integer index;

  /** @see #getKey() */
  private final String key;

  /**
   * The constructor.
   * 
   * @param propertyName is the raw property-name.
   */
  public PojoProperty(String propertyName) {

    super();
    int len = propertyName.length();
    int startIndex = propertyName.indexOf('[');
    if (startIndex == -1) {
      this.name = propertyName;
      this.index = null;
      this.key = null;
    } else {
      this.name = propertyName.substring(0, startIndex);
      if (propertyName.charAt(len - 1) != ']') {
        throw new NlsIllegalArgumentException(propertyName);
      }
      char c = propertyName.charAt(startIndex + 1);
      if (c == '\'') {
        if ((propertyName.charAt(len - 2) != '\'') || (startIndex >= (len - 3))) {
          throw new NlsIllegalArgumentException(propertyName);
        }
        this.index = null;
        this.key = propertyName.substring(startIndex + 2, len - 2);
      } else if ((c >= '0') && (c <= '9')) {
        String indexString = propertyName.substring(startIndex + 1, len - 1);
        this.index = Integer.valueOf(indexString);
        this.key = null;
      } else {
        throw new NlsIllegalArgumentException(propertyName);
      }
    }
    if (this.name.length() == 0) {
      throw new NlsIllegalArgumentException(propertyName);
    }
  }

  /**
   * This method gets the plain name of the property.<br>
   * Examples:
   * <table border="1">
   * <tr>
   * <th>propertyName</th>
   * <th>new {@link PojoProperty}(propertyName).{@link #getName()}</th>
   * </tr>
   * <tr>
   * <td>fooBar</td>
   * <td>fooBar</td>
   * </tr>
   * <tr>
   * <td>foo[42]</td>
   * <td>foo</td>
   * </tr>
   * <tr>
   * <td>bar['key']</td>
   * <td>bar</td>
   * </tr>
   * </table>
   * 
   * @return the plain name of the property.
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the optional index.
   * 
   * @return the index or <code>null</code> if this {@link PojoProperty} does
   *         NOT represent an indexed property.
   */
  public Integer getIndex() {

    return this.index;
  }

  /**
   * This method gets the optional key.
   * 
   * @return the key of <code>null</code> if this {@link PojoProperty} does NOT
   *         represent a mapped property.
   */
  public String getKey() {

    return this.key;
  }

}
