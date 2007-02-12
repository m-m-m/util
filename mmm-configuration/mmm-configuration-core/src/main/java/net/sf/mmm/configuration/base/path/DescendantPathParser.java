/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import java.util.List;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.IllegalDescendantPathException;
import net.sf.mmm.util.ListCharFilter;
import net.sf.mmm.util.StringParser;

/**
 * This is a parser for
 * {@link Configuration#getDescendants(String, String) descendant-paths}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DescendantPathParser {

  /** filter that excepts all chars except those reserved for descendent path */
  private static final ListCharFilter SEGMENT_FILTER = new ListCharFilter(false,
      Configuration.PATH_SEPARATOR, Configuration.PATH_CONDITION_START, Configuration.PATH_UNION,
      Configuration.PATH_INTERSECTION, '=', '<', '>', '!');

  /** filter that accepts only chars allowed for condition comparators */
  private static final ListCharFilter OPERATION_FILTER = new ListCharFilter(true, '=', '<', '>');

  /**
   * The constructor
   * 
   */
  public DescendantPathParser() {

    super();
  }

  /**
   * This method parses a single condition from the descendant path.<br>
   * After the successfuly call of this method the given <code>parser</code>
   * will point to the next character after
   * {@link Configuration#PATH_CONDITION_START}.
   * 
   * @param parser
   *        is the string to parse at the current state. It points to the next
   *        character after {@link Configuration#PATH_CONDITION_START}.
   * @param segmentList
   *        is a list used to collect path segments.
   * @return the parsed condition.
   */
  public static Condition parseCondition(StringParser parser, List<SimplePathSegment> segmentList) {

    segmentList.clear();
    char c = Configuration.PATH_SEPARATOR;
    while (c == Configuration.PATH_SEPARATOR) {
      String name = parser.readWhile(SEGMENT_FILTER);
      SimplePathSegment segment = new SimplePathSegment(name);
      segmentList.add(segment);
      c = parser.forcePeek();
    }
    SimplePathSegment[] segments = segmentList.toArray(new SimplePathSegment[segmentList.size()]);
    String operator = parser.readWhile(OPERATION_FILTER);
    Condition condition;
    if (operator.length() > 0) {
      String value = parser.readUntil(Configuration.PATH_CONDITION_END, false);
      if (value == null) {
        throw new IllegalDescendantPathException(parser.getOriginalString());
      }
      Comparator comparator = ComparatorManager.getInstance().getComparator(operator);
      condition = new ConditionImpl(segments, value, comparator);
    } else {
      condition = new ConditionImpl(segments);
      c = parser.forceNext();
      if (c != Configuration.PATH_CONDITION_END) {
        throw new IllegalDescendantPathException(parser.getOriginalString());
      }
    }
    return condition;
  }

  /**
   * This method parses a segment of the descendant path. A segment is a
   * substring of the descendant path that represents a query for a child
   * configuration. It is build out of a name(-pattern) and an optional
   * condition.<br>
   * Examples are "childName", "*", "@attr*", "item[@name=='foo']"
   * 
   * @param parser
   *        is the string to parse at the current state.
   * @param segmentList
   *        is a list used to collect path segments.
   * @return the parsed segment.
   */
  public static PathSegment parseSegment(StringParser parser, List<SimplePathSegment> segmentList) {

    String segment = parser.readWhile(SEGMENT_FILTER);
    Condition condition = null;
    if (parser.hasNext()) {
      char c = parser.peek();
      if (c == Configuration.PATH_CONDITION_START) {
        parser.next();
        condition = parseCondition(parser, segmentList);
      }
    }
    return new PathSegment(segment, condition);
  }

  /**
   * This method parses all segments of a simple path. A simple path is build by
   * one or multiple segments separated by {@link Configuration#PATH_SEPARATOR}.
   * The descendant path may contain multiple simple paths separated by
   * {@link Configuration#PATH_UNION} or {@link Configuration#PATH_INTERSECTION}.
   * 
   * @param parser
   *        is the string-parser pointing to the start of the path to parse.
   * @param segments
   *        is the list where to add the parsed segments.
   * @param conditionSegments
   *        is a list used to collect path segments.
   */
  public static void parsePath(StringParser parser, List<PathSegment> segments,
      List<SimplePathSegment> conditionSegments) {

    boolean todo = true;
    while (todo) {
      segments.add(parseSegment(parser, conditionSegments));
      todo = false;
      if (parser.hasNext()) {
        char c = parser.peek();
        if (c == Configuration.PATH_SEPARATOR) {
          parser.next();
          todo = true;
        }
      }
    }
  }

}
