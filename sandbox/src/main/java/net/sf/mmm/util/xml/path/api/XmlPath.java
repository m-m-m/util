/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

/**
 * This is the interface for a selector that represents a XML path selector.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlPath extends XmlSelector {

  /**
   * This method gets the segment at the given {@code index}.
   *
   * @param index is the index of the requested segment. It has to be in the range from {@code 0} to <code>
   *        {@link #getSegmentCount()}-1</code>.
   * @return the segment at the given {@code index}.
   */
  XmlPathSegment getSegment(int index);

  /**
   * This method gets the number of segments this path is build of.
   *
   * @see #getSegment(int)
   *
   * @return the segment count.
   */
  int getSegmentCount();

}
