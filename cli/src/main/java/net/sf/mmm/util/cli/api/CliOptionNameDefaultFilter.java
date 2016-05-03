/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the default {@link Filter} used to decide if the {@link CliOption#name() name} or
 * {@link CliOption#aliases() alias} of a {@link CliOption} is {@link #accept(String) acceptable}.
 * 
 * @see CliStyle#optionNameFilter()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliOptionNameDefaultFilter implements Filter<String> {

  /**
   * This method determines if a character the name (excluding the initial hyphens) is acceptable. You may
   * extend this class and override this method to declare a less strict {@link CliStyle#optionNameFilter()
   * naming style}.
   * 
   * @param c is the character to check.
   * @return {@code true} if the given character is acceptable, {@code false} otherwise.
   */
  protected boolean accept(char c) {

    if ((c >= 'a') && (c <= 'z')) {
      return true;
    } else if (c == '-') {
      return true;
    }
    return false;
  }

  @Override
  public boolean accept(String name) {

    if ((name == null) || (name.length() == 0)) {
      return false;
    }
    // the index of the first character that is no hyphen
    int startIndex = 0;
    while (name.charAt(startIndex) == '-') {
      startIndex++;
    }
    if (startIndex == name.length()) {
      // name only contains hyphens
      return false;
    }
    if ((startIndex > 2) || name.endsWith("-")) {
      return false;
    }
    if (startIndex == 0) {
      return false;
    }
    if ((name.length() > 2) && (startIndex < 2)) {
      return false;
    }
    // check the other characters of name
    for (int i = startIndex; i < name.length(); i++) {
      char c = name.charAt(i);
      if (!accept(c)) {
        return false;
      }
    }
    return true;
  }

}
