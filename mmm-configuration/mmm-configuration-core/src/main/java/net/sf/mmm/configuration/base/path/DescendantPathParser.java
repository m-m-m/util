/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.IllegalDescendantPathException;
import net.sf.mmm.configuration.base.path.comparator.Comparator;
import net.sf.mmm.configuration.base.path.comparator.ComparatorManager;
import net.sf.mmm.configuration.base.path.comparator.EqualsComparator;
import net.sf.mmm.configuration.base.path.condition.CompareCondition;
import net.sf.mmm.configuration.base.path.condition.Condition;
import net.sf.mmm.configuration.base.path.condition.IndexCondition;
import net.sf.mmm.configuration.base.path.condition.PathCondition;
import net.sf.mmm.util.ListCharFilter;
import net.sf.mmm.util.StringParser;

/**
 * This is a parser for descendant-paths.
 * 
 * @see Configuration#getDescendant(String, String)
 * @see Configuration#getDescendants(String, String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DescendantPathParser {

  /** filter that excepts all chars except those reserved for descendent path */
  private static final ListCharFilter SEGMENT_FILTER = new ListCharFilter(false,
      Configuration.PATH_SEPARATOR, Configuration.PATH_CONDITION_START,
      Configuration.PATH_CONDITION_END, Configuration.PATH_UNION, Configuration.PATH_INTERSECTION,
      '=', '<', '>', '!');

  /** filter that accepts only chars allowed for condition comparators */
  private static final ListCharFilter OPERATION_FILTER = new ListCharFilter(true, '=', '<', '>');

  /**
   * The constructor
   */
  private DescendantPathParser() {

    super();
  }

  /**
   * This method parses a single condition from the descendant path.<br>
   * After the successfully call of this method the given <code>parser</code>
   * will point to the next character after
   * {@link Configuration#PATH_CONDITION_START}.
   * 
   * @param parser
   *        is the string to parse at the current state. It points to the next
   *        character after {@link Configuration#PATH_CONDITION_START}.
   * @param segmentList
   *        is a list used to collect path segments.
   * @param strict -
   *        if <code>true</code> the path is parsed
   *        {@link Configuration#getDescendant(String, String) strict}, if
   *        <code>false</code> wildcards and advanced conditions are
   *        permitted.
   * @return the parsed condition.
   */
  public static Condition parseCondition(StringParser parser, List<SimplePathSegment> segmentList,
      boolean strict) {

    segmentList.clear();
    // parse all segments...
    char c = Configuration.PATH_SEPARATOR;
    while (c == Configuration.PATH_SEPARATOR) {
      String name = parser.readWhile(SEGMENT_FILTER);
      if (name.length() == 0) {
        throw new IllegalDescendantPathException(parser.getOriginalString());
      }
      SimplePathSegment segment = new SimplePathSegment(name);
      segmentList.add(segment);
      c = parser.forcePeek();
      if (c == Configuration.PATH_SEPARATOR) {
        parser.next();
      }
    }
    // check for index condition...
    if (segmentList.size() == 1) {
      SimplePathSegment segment = segmentList.get(0);
      if (!segment.isPattern()) {
        String indexString = segment.getString();
        try {
          int index = Integer.parseInt(indexString);
          if (index < 1) {
            throw new IllegalDescendantPathException(parser.getOriginalString());
          }
          c = parser.forceNext();
          if (c != Configuration.PATH_CONDITION_END) {
            throw new IllegalDescendantPathException(parser.getOriginalString());            
          }
          return new IndexCondition(index - 1);
        } catch (NumberFormatException e) {
          // ignore...
        }
      }
    }
    SimplePathSegment[] segments = segmentList.toArray(new SimplePathSegment[segmentList.size()]);
    String operator = parser.readWhile(OPERATION_FILTER);
    Condition condition;
    if (operator.length() > 0) {
      Comparator comparator = ComparatorManager.getInstance().getComparator(operator);
      if (strict && !EqualsComparator.SYMBOL.equals(comparator.getSymbol())) {
        throw new IllegalDescendantPathException(parser.getOriginalString());
      }
      if (parser.expect(Configuration.PATH_VALUE_START)) {
        String value = parser.readUntil(Configuration.PATH_VALUE_END, false);
        if (value == null) {
          throw new IllegalDescendantPathException(parser.getOriginalString());
        }
        if (!parser.expect(Configuration.PATH_CONDITION_END)) {
          throw new IllegalDescendantPathException(parser.getOriginalString());          
        }
        CompareCondition cond = new CompareCondition(segments, value, comparator);
        if (strict && cond.isValuePattern()) {
          throw new IllegalDescendantPathException(parser.getOriginalString());
        }
        condition = cond;        
      } else {
        throw new IllegalDescendantPathException(parser.getOriginalString());        
      }
    } else {
      condition = new PathCondition(segments);
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
   * @param strict -
   *        if <code>true</code> the path is parsed
   *        {@link Configuration#getDescendant(String, String) strict}, if
   *        <code>false</code> wildcards and advanced conditions are
   *        permitted.
   * @return the parsed segment.
   */
  public static PathSegment parseSegment(StringParser parser, List<SimplePathSegment> segmentList,
      boolean strict) {

    String segment = parser.readWhile(SEGMENT_FILTER);
    Condition condition = Condition.EMPTY_CONDITION;
    if (parser.hasNext()) {
      char c = parser.peek();
      if (c == Configuration.PATH_CONDITION_START) {
        parser.next();
        condition = parseCondition(parser, segmentList, strict);
      }
    }
    PathSegment result = new PathSegment(segment, condition);
    if (strict && result.isPattern()) {
      throw new IllegalDescendantPathException(parser.getOriginalString());
    }
    return result;
  }

  /**
   * This method parses all segments of a simple descendant path. Such path is
   * build by one or multiple segments separated by
   * {@link Configuration#PATH_SEPARATOR}. A full descendant path may contain
   * multiple simple paths separated by {@link Configuration#PATH_UNION} or
   * {@link Configuration#PATH_INTERSECTION}.
   * 
   * @see Configuration#getDescendant(String, String)
   * @see Configuration#getDescendants(String, String)
   * 
   * @param parser
   *        is the string-parser pointing to the start of the path to parse.
   * @param strict -
   *        if <code>true</code> the path is parsed
   *        {@link Configuration#getDescendant(String, String) strict}, if
   *        <code>false</code> wildcards and advanced conditions are
   *        permitted.
   * @return the parsed segments of the given <code>path</code>.
   */
  public static PathSegment[] parsePath(StringParser parser, boolean strict) {

    List<PathSegment> segments = new ArrayList<PathSegment>();
    parsePath(parser, segments, strict, new ArrayList<SimplePathSegment>());
    return segments.toArray(new PathSegment[segments.size()]);
  }

  /**
   * This method parses all segments of a simple descendant path. Such path is
   * build by one or multiple segments separated by
   * {@link Configuration#PATH_SEPARATOR}. A full descendant path may contain
   * multiple simple paths separated by {@link Configuration#PATH_UNION} or
   * {@link Configuration#PATH_INTERSECTION}.
   * 
   * @see Configuration#getDescendant(String, String)
   * @see Configuration#getDescendants(String, String)
   * 
   * @param parser
   *        is the string-parser pointing to the start of the path to parse.
   * @param segments
   *        is the list where to add the parsed segments.
   * @param strict -
   *        if <code>true</code> the path is parsed
   *        {@link Configuration#getDescendant(String, String) strict}, if
   *        <code>false</code> wildcards and advanced conditions are
   *        permitted.
   * @param conditionSegments
   *        is a list used to collect path segments.
   */
  public static void parsePath(StringParser parser, List<PathSegment> segments, boolean strict,
      List<SimplePathSegment> conditionSegments) {

    boolean todo = true;
    while (todo) {
      segments.add(parseSegment(parser, conditionSegments, strict));
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
