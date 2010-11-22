/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

/**
 * A {@link HyphenationPatternPosition} represents a {@link #ranking ranked}
 * {@link #index position} of a potential hyphenation of a
 * {@link HyphenationPattern}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenationPatternPosition {

  // CHECKSTYLE:OFF (no Getters for Performance)

  /**
   * the index of the hyphenation-position in the pattern (without numbers). A
   * value of <code>0</code> indicates a hyphenation before the first character.
   */
  public final int index;

  /**
   * The ranking a hyphenation at the represented {@link #index}. A higher value
   * over-rules a lower value of a previous pattern. Odd values indicate a
   * hyphenation while even values indicate
   */
  public final int ranking;

  // CHECKSTYLE:ON

  /**
   * The constructor.
   * 
   * @param index is the {@link #index}.
   * @param rankink is the {@link #ranking}.
   */
  public HyphenationPatternPosition(int index, int rankink) {

    super();
    this.index = index;
    this.ranking = rankink;
  }

}
