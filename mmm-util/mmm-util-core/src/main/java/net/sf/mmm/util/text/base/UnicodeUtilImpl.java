/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.text.api.UnicodeUtil;

/**
 * This is the implementation of the {@link UnicodeUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@SuppressWarnings("boxing")
public class UnicodeUtilImpl implements UnicodeUtil {

  /** @see #getInstance() */
  private static UnicodeUtil instance;

  /** @see #normalize2Ascii(char) */
  private static final Map<Character, String> CHARACTER_TO_ASCII_MAP;

  static {
    CHARACTER_TO_ASCII_MAP = new HashMap<Character, String>();
    CHARACTER_TO_ASCII_MAP.put(NON_BREAKING_SPACE, " ");
    CHARACTER_TO_ASCII_MAP.put(SOFT_HYPHEN, "-");
    CHARACTER_TO_ASCII_MAP.put(MINUS_SIGN, "-");
    CHARACTER_TO_ASCII_MAP.put('ä', "ae");
    CHARACTER_TO_ASCII_MAP.put('ö', "oe");
    CHARACTER_TO_ASCII_MAP.put('ü', "ue");

  }

  /**
   * The constructor.
   */
  public UnicodeUtilImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String normalize2Ascii(char character) {

    return CHARACTER_TO_ASCII_MAP.get(Character.valueOf(character));
  }

  /**
   * This method gets the singleton instance of this {@link UnicodeUtilImpl}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static UnicodeUtil getInstance() {

    if (instance == null) {
      synchronized (UnicodeUtilImpl.class) {
        if (instance == null) {
          instance = new UnicodeUtilImpl();
        }
      }
    }
    return instance;
  }

}
