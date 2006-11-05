/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.List;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.IllegalPathException;
import net.sf.mmm.util.StringUtil;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PathParser {

  /** the descendant path to parse */
  private final String string;

  /** the {@link #string} as {@link String#toCharArray() char-array} */
  private final char[] chars;

  /**
   * the current index in the {@link #chars char-array}.
   */
  private int pos;

  /**
   * The constructor.
   * 
   * @param path
   *        is the
   *        {@link Configuration#getDescendants(String, String) descendant path}
   *        to parse.
   */
  public PathParser(String path) {

    super();
    this.string = path;
    this.chars = this.string.toCharArray();
    this.pos = 0;
  }

  /**
   * This method parses a single condition from the descendant path.
   * 
   * @param namePattern
   *        is the pattern the name has to match or <code>null</code>.
   * @return the parsed condition.
   */
  public ConditionIF parseCondition(Pattern namePattern) {

    char c;
    boolean isPattern = false;
    String childName = null;
    int start = this.pos;
    int comparatorLength = 0;
    ConditionIF condition = null;
    while (condition == null) {
      if (this.pos < this.chars.length) {
        c = this.chars[this.pos++];
      } else {
        c = 0;
        this.pos++;
      }
      switch (c) {
        case '=':
        case '!':
        case '<':
        case '>':
          if (childName == null) {
            int len = this.pos - start - 1;
            if (len <= 0) {
              throw new IllegalPathException(this.string);
            }
            if (isPattern) {
              // currently not supported!
              throw new IllegalPathException(this.string);
            }
            childName = new String(this.chars, start, len);
            start = this.pos;
          }
          comparatorLength++;
          break;
        case Configuration.PATH_CONDITION_END:
          if (comparatorLength == 0) {
            throw new IllegalPathException(this.string);
          } else {
            String cmp = new String(this.chars, start, comparatorLength);
            Comparator comparator = ComparatorManager.getInstance().getComparator(cmp);
            start = start + comparatorLength;
            int len = this.pos - start - 1;
            if (len <= 0) {
              throw new IllegalPathException(this.string);
            }
            String value = new String(this.chars, start, len);
            Pattern valuePattern;
            if (isPattern) {
              valuePattern = StringUtil.compileGlobPattern(value);
            } else {
              valuePattern = null;
            }
            condition = new ConditionImpl(namePattern, childName, value, valuePattern, comparator);
          }
          break;
        case '*':
        case '?':
          isPattern = true;
          break;
        case Configuration.PATH_SEPARATOR:
        case Configuration.PATH_UNION:
        case Configuration.PATH_INTERSECTION:
        case Configuration.PATH_CONDITION_START:
          throw new IllegalPathException(this.string);
      }
    }
    return condition;
  }

  /**
   * This method parses a segment of the descendant path. A segment is a
   * substring of the descendant path that represents a query for a child
   * configuration. It is build out of a name (-pattern) and an optional
   * condition.<br>
   * Examples are "childName", "*", "@attr*", "item[@name=='foo']"
   * 
   * @return the parsed segment.
   */
  public PathSegment parseSegment() {

    char c;
    int start = this.pos;
    boolean isPattern = false;
    PathSegment segment = null;
    while (segment == null) {
      if (this.pos < this.chars.length) {
        c = this.chars[this.pos++];
      } else {
        c = Configuration.PATH_SEPARATOR;
        this.pos++;
      }
      switch (c) {
        case Configuration.PATH_UNION:
        case Configuration.PATH_INTERSECTION:
        case Configuration.PATH_CONDITION_START:
        case Configuration.PATH_SEPARATOR:
          int len = this.pos - start - 1;
          if (len <= 0) {
            throw new IllegalPathException(this.string);
          }
          String name = new String(this.chars, start, len);
          Pattern namePattern;
          if (isPattern) {
            namePattern = StringUtil.compileGlobPattern(name);
          } else {
            namePattern = null;
          }
          ConditionIF condition = null;
          if (c == Configuration.PATH_CONDITION_START) {
            condition = parseCondition(namePattern);
            if (this.pos < this.chars.length) {
              c = this.chars[this.pos++];
              switch (c) {
                case Configuration.PATH_UNION:
                case Configuration.PATH_INTERSECTION:
                case Configuration.PATH_SEPARATOR:
                  break;
                default :
                  throw new IllegalPathException(this.string);
              }
            } else {
              this.pos++;
            }
          } else {
            condition = new ConditionImpl(namePattern);
          }
          segment = new PathSegment(name, isPattern, condition);
          break;
        case '*':
        case '?':
          isPattern = true;
          break;
        case '=':
        case '!':
        case '<':
        case '>':
        case Configuration.PATH_CONDITION_END:
          throw new IllegalPathException(this.string);
      }
    }
    return segment;
  }

  /**
   * This method reads all segments of a simple path. A simple path is build by
   * one or multiple segments separated by
   * {@link Configuration#PATH_SEPARATOR}. The descendant path may contain
   * multiple simple paths separated by {@link Configuration#PATH_UNION} or
   * {@link Configuration#PATH_INTERSECTION}.
   * 
   * @param segments
   */
  public void parsePath(List<PathSegment> segments) {

    boolean todo = true;
    while (todo) {
      segments.add(parseSegment());
      int lastPos = this.pos - 1;
      if (lastPos < this.chars.length) {
        char c = this.chars[lastPos];
        if (c != Configuration.PATH_SEPARATOR) {
          todo = false;
        }
      } else {
        todo = false;
      }
    }
  }

  /**
   * This method gets the current char of the parsing process or
   * <code>'\0'</code> if the parsing is completed.
   * 
   * @return the state.
   */
  public char getState() {

    int lastPos = this.pos - 1;
    if (lastPos < this.chars.length) {
      return this.chars[lastPos];
    } else {
      return 0;
    }
  }

}
