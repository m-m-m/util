/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

/**
 * This is the interface for a set of XML nodes that is selected by combining a
 * {@link #getSelector(int) list of selectors} with a specific
 * {@link #getConjunction() conjunction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSet extends XmlSelector {

  /**
   * This method gets the conjunction used to combine the
   * {@link #getSelector(int) child-selectors} of this {@link XmlSet}.
   * 
   * @return the conjunction.
   */
  Conjunction getConjunction();

  /**
   * This method gets the child-selector at the given {@code index}.
   * 
   * @param index is the index of the requested selector. It has to be in the
   *        range from {@code 0} to
   *        <code>{@link #getSelectorCount()}-1</code>.
   * @return the selector at the given {@code index}.
   */
  XmlSelector getSelector(int index);

  /**
   * This method gets the number of child-selectors combined by this set.
   * 
   * @see #getSelector(int)
   * 
   * @return the number of child-selectors.
   */
  int getSelectorCount();

  /**
   * This enum contains the available conjunctions for an {@link XmlSet}. Such
   * conjunction is an operation that describes how a the results of multiple
   * {@link XmlSelector}s are combined.
   */
  public static enum Conjunction {

    /**
     * This is the conjunction used to combine the results of multiple
     * {@link XmlSelector}s with a logical {@code OR}. Each result that
     * is accepted by any of the {@link XmlSelector}s is added to the result of
     * the according {@link XmlSet}. <br>
     * E.g. if the {@link XmlSelector} {@code A} accepts the XML nodes
     * {@code n1} and {@code n2} with the {@link XmlSelector}
     * {@code B} accepts {@code n2} and {@code n3} then the
     * {@link #UNION} of {@code A} and {@code B} will accept
     * {@code n1}, {@code n2} and {@code n3}.
     */
    UNION,

    /**
     * This is the conjunction used to combine the results of multiple
     * {@link XmlSelector}s with a logical {@code AND}. Only results
     * that are accepted by all of the {@link XmlSelector}s are added to the
     * result of the according {@link XmlSet}. <br>
     * E.g. if the {@link XmlSelector} {@code A} accepts the XML nodes
     * {@code n1} and {@code n2} with the {@link XmlSelector}
     * {@code B} accepts {@code n2} and {@code n3} then the
     * {@link #INTERSECTION} of {@code A} and {@code B} will only
     * accept {@code n2}.
     */
    INTERSECTION;

  }

}
