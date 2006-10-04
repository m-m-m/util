/* $Id$ */
package net.sf.mmm.configuration.base.path;

/**
 * This class represents a single segment of a
 * {@link net.sf.mmm.configuration.api.ConfigurationIF#getDescendants(String, String) descendant-path}.
 * A segment is a substring of the descendant-path that represents a query for a
 * {@link net.sf.mmm.configuration.base.AbstractConfiguration#getChild(String) child}
 * configuration. It is build out of a name (-pattern) and an optional
 * condition.<br>
 * Examples are "childName", "*", "@a?tr*", "item[@name==foo*]"
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PathSegment {

  /**
   * the segment string ({@link net.sf.mmm.configuration.api.ConfigurationIF#getName() name})
   */
  private String string;

  /**
   * if <code>true</code> the {@link #string} is a
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}
   * that will be checked by the {@link #condition}.
   */
  private boolean isPattern;

  /**
   * the condition or <code>null</code> if any child is acceptable.
   */
  private ConditionIF condition;

  /**
   * The constructor.
   * 
   * @param name
   * @param isGlobPattern
   * @param filter
   */
  public PathSegment(String name, boolean isGlobPattern, ConditionIF filter) {

    super();
    this.string = name;
    this.isPattern = isGlobPattern;
    this.condition = filter;
  }

  /**
   * This method gets the condition.
   * 
   * @return the condition.
   */
  public ConditionIF getCondition() {

    return this.condition;
  }

  /**
   * This method determines if the {@link #getString() string} is a
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}
   * that will be checked by the {@link #getCondition() condition}.
   * 
   * @return <code>true</code> if the {@link #getString() string} is a pattern
   *         and <code>false</code> if it is a regular
   *         {@link net.sf.mmm.configuration.api.ConfigurationIF#getName() name}.
   */
  public boolean isPattern() {

    return this.isPattern;
  }

  /**
   * This method gets the string. It is eigther a regular
   * {@link net.sf.mmm.configuration.api.ConfigurationIF#getName() name} of the
   * according
   * {@link net.sf.mmm.configuration.api.ConfigurationIF configuration} or a
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}
   * the {@link net.sf.mmm.configuration.api.ConfigurationIF#getName() name}
   * must match.
   * 
   * @return the string.
   */
  public String getString() {

    return this.string;
  }

  /**
   * @see java.lang.Object#toString() 
   */
  @Override
  public String toString() {

    return this.string;
  }

}
