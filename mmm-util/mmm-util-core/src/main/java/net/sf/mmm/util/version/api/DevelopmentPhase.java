/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.text.api.UnicodeUtil;

/**
 * A {@link DevelopmentPhase} represents the state of development of an artifact.
 * 
 * @see VersionIdentifier#getPhase()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public enum DevelopmentPhase implements Datatype<String> {

  /**
   * This is the earliest {@link DevelopmentPhase} in the development cycle. In this {@link DevelopmentPhase}
   * the software is typically very unstable, experimental, and the focus is on creating new functionality.<br/>
   * This {@link DevelopmentPhase} is sometimes also just called <em>development</em> but this is quite
   * ambiguous as development happens in all {@link DevelopmentPhase}s.
   */
  PRE_ALPHA("pre-alpha", "pre"),

  /**
   * This {@link DevelopmentPhase} aims to given an early preview of the product and to allow first testings
   * (by "friendly users"). Therefore this {@link DevelopmentPhase} has focus on gathering feedback.<br/>
   * This {@link DevelopmentPhase} is sometimes also called <em>developer preview (DP)</em> .
   */
  ALPHA("alpha", "a", Character.toString(UnicodeUtil.GREEK_SMALL_LETTER_ALPHA)),

  /**
   * This {@link DevelopmentPhase} already gives a good impression of the final {@link #RELEASE release} but
   * is still containing bugs and may not be fully complete while all major features should be included.
   * Therefore this {@link DevelopmentPhase} has focus on stabilization of the product.<br/>
   * This {@link DevelopmentPhase} is sometimes also called <em>early access (EA)</em>.
   */
  BETA("beta", "b", Character.toString(UnicodeUtil.GREEK_SMALL_LETTER_BETA)),

  /**
   * This {@link DevelopmentPhase} is feature complete but may contain some last bugs while known bugs should
   * already be fixed. If all acceptance tests are passed, the {@link #RELEASE} is reached. Therefore this
   * {@link DevelopmentPhase} has focus on finalization of the product.<br/>
   * This {@link DevelopmentPhase} is sometimes also just called <em>pre-release</em> or <em>gamma</em> (while
   * gamma may be somewhere in between of {@link #BETA} and {@link #RELEASE_CANDIDATE}).
   */
  RELEASE_CANDIDATE("release-candidate", "RC", "pre-release"),

  /**
   * This {@link DevelopmentPhase} is feature complete and has passed acceptance tests. For productive usage
   * only product releases in this {@link DevelopmentPhase phase} or {@link #isAfter(DevelopmentPhase) after}
   * are officially intended. Please note that also a release can obviously still have bugs. <br/>
   * This {@link DevelopmentPhase} is sometimes also just called <em>final</em>, <em>stable</em>,
   * <em>gold</em>, <em>release to manufacturing (RTM)</em> or <em>general availability (GA)</em>.
   */
  RELEASE("release", "GA", "gold", "stable", "final", "RTM", "general-availability", "globally-available",
      "global-available"),

  /**
   * This {@link DevelopmentPhase} is like {@link #RELEASE} but may be used for bugfixes or tiny improvements
   * of the {@link #RELEASE released} product version.<br/>
   * This is sometimes called <em>service release (SR)</em> or <code>service pack (SP)</code>.
   */
  UPDATE("update", "u", "SR", "service-release", "SP", "service-pack");

  /** @see #getTitle() */
  private final String title;

  /** @see #getTitle() */
  private final String value;

  /** @see #getAlternatives() */
  private final String[] alternatives;

  /**
   * The constructor.
   * 
   * @param title - see {@link #getTitle()}.
   * @param value - see {@link #getValue()}.
   * @param alternatives - see {@link #getAlternatives()}.
   */
  private DevelopmentPhase(String title, String value, String... alternatives) {

    this.title = title;
    this.value = value;
    this.alternatives = alternatives;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * This method gets the alternatives. On each call of this method a copy of the internal array is created.
   * 
   * @return an array with string representations alternative to {@link #getTitle() title} and
   *         {@link #getValue() value}. May be empty.
   */
  public String[] getAlternatives() {

    String[] result = new String[this.alternatives.length];
    System.arraycopy(this.alternatives, 0, result, 0, this.alternatives.length);
    return result;
  }

  /**
   * This method determines if this {@link DevelopmentPhase} is before the given <code>phase</code>. Here
   * <em>before</em> means that it is earlier and typically less stable.
   * 
   * @param phase is the {@link DevelopmentPhase} to compare with.
   * @return <code>true</code> if this phase is before the given <code>phase</code>, <code>false</code>
   *         otherwise (also if <code>phase</code> is <code>null</code>).
   */
  public boolean isBefore(DevelopmentPhase phase) {

    if (phase == null) {
      return false;
    }
    return (ordinal() < phase.ordinal());
  }

  /**
   * This method determines if this {@link DevelopmentPhase} is after the given <code>phase</code>. Here
   * <em>after</em> means that it is later and typically more stable.
   * 
   * @param phase is the {@link DevelopmentPhase} to compare with.
   * @return <code>true</code> if this phase is after the given <code>phase</code>, <code>false</code>
   *         otherwise (also if <code>phase</code> is <code>null</code>).
   */
  public boolean isAfter(DevelopmentPhase phase) {

    if (phase == null) {
      return false;
    }
    return (ordinal() > phase.ordinal());
  }

  /**
   * This method gets the {@link DevelopmentPhase} with the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link DevelopmentPhase}.
   * @return the requested {@link DevelopmentPhase} or <code>null</code> if it does not exists.
   */
  public static DevelopmentPhase fromValue(String value) {

    for (DevelopmentPhase phase : values()) {
      if (phase.getValue().equals(value)) {
        return phase;
      }
    }
    return null;
  }

  /**
   * This method gets the {@link #getTitle() title} of this {@link DevelopmentPhase}.
   * 
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
