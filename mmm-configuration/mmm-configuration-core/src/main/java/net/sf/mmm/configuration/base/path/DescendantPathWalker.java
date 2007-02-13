/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import java.util.Iterator;

import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This is a utility class that provides functionality to evaluate descendant
 * paths.
 * 
 * @see net.sf.mmm.configuration.api.Configuration#getDescendant(String, String)
 * @see net.sf.mmm.configuration.api.Configuration#getDescendants(String,
 *      String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DescendantPathWalker {

  /**
   * The constructor
   */
  private DescendantPathWalker() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(String,
   *      String)
   * 
   * @param node
   *        is the current to work on.
   * @param namespaceUri
   *        is the namespace URI to use.
   * @param pathSegments
   *        are the segemnts of the descendant path.
   * @return the requested descendant or <code>null</code> if it does NOT
   *         exist and needs to be created.
   */
  public static AbstractConfiguration getDescendant(AbstractConfiguration node,
      String namespaceUri, PathSegment[] pathSegments) {

    // TODO: optimized implementation for pathSegments.length == 1?
    BestPathMatch match = new BestPathMatch(node);
    AbstractConfiguration descendant = getDescendant(node, namespaceUri, pathSegments, 0, match);
    if (descendant == null) {
      descendant = match.node;
      int segmentIndex = match.index;
      for (int i = segmentIndex; i < pathSegments.length; i++) {
        PathSegment segment = pathSegments[i];
        // TODO: what if best match is element and segment is attribute
        descendant = descendant.createChild(segment.getString(), namespaceUri);
        Condition condition = segment.getCondition();
        if (condition != Condition.EMPTY_CONDITION) {
          ConditionImpl cimpl = (ConditionImpl) condition;
          SimplePathSegment[] conditionSegments = cimpl.getSegments();
          AbstractConfiguration child = descendant;
          for (int j = 0; j < conditionSegments.length; j++) {
            SimplePathSegment conditionSegment = conditionSegments[j];
            child = child.createChild(conditionSegment.getString(), namespaceUri);            
          }
          child.getValue().setString(cimpl.getValue());
          assert (condition.accept(descendant));
        }
      }
    }
    return descendant;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(String,
   *      String)
   * 
   * @param node
   *        is the current to work on.
   * @param namespaceUri
   *        is the namespace URI to use.
   * @param pathSegments
   *        are the segemnts of the descendant path.
   * @param segmentIndex
   *        is the current index in the segment path.
   * @param match
   *        is a container used to collect the current best match.
   * @return the requested descendant or <code>null</code> if it does NOT
   *         exist and needs to be created.
   */
  public static AbstractConfiguration getDescendant(AbstractConfiguration node,
      String namespaceUri, PathSegment[] pathSegments, int segmentIndex, BestPathMatch match) {

    PathSegment segment = pathSegments[segmentIndex];
    int nextIndex = segmentIndex + 1;
    Iterator<AbstractConfiguration> childIterator = node.getChildren(segment.getString(),
        namespaceUri);
    while (childIterator.hasNext()) {
      AbstractConfiguration child = childIterator.next();
      if (segment.getCondition().accept(child)) {
        match.checkMatch(child, nextIndex);
        if (nextIndex < pathSegments.length) {
          AbstractConfiguration descendant = getDescendant(child, namespaceUri, pathSegments,
              nextIndex, match);
          if (descendant != null) {
            return descendant;
          }
        } else {
          // reached end of segment-list and found valid descendant...
          return child;
        }
      }
    }
    return null;
  }

  /**
   * This inner class is a simple container used to store the best match.
   */
  private static class BestPathMatch {

    /** the node that is currently the best (deepest) match */
    private AbstractConfiguration node;

    /** the depth of this best match */
    private int index;

    /**
     * The constructor.
     * 
     * @param start
     *        is the configuration node to start with.
     */
    public BestPathMatch(AbstractConfiguration start) {

      super();
      this.node = start;
      this.index = 0;
    }

    /**
     * This method checks if the given <code>match</code> is bettern than the
     * {@link #node current} according to the given <code>segmentIndex</code>.
     * If it is better, the <code>match</code> is stored as best match.
     * 
     * @param match
     *        is the match to check.
     * @param segmentIndex
     *        is the current segment index of the <code>match</code>.
     * @return <code>true</code> if the given <code>match</code> is
     *         currently the best and has been stored, <code>false</code>
     *         otherwise.
     */
    public boolean checkMatch(AbstractConfiguration match, int segmentIndex) {

      if (segmentIndex > this.index) {
        this.index = segmentIndex;
        this.node = match;
        return true;
      }
      return false;
    }

  }

}
