/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.path.condition.Condition;

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
   * This method adds all descendants that match the given <code>path</code>
   * to the given <code>set</code>.
   * 
   * @param node
   *        is the current node to work one.
   * @param namespaceUri
   *        is the namespace-URI to use or <code>null</code> if namespaces
   *        should be ignored.
   * @param path
   *        is the parsed descendant path (simple path part).
   * @param segmentIndex
   *        is the current index in the path.
   * @param set
   *        is the set where to add the matching descendants.
   */
  public static void addDescendants(AbstractConfiguration node, String namespaceUri,
      List<PathSegment> path, int segmentIndex, Set<AbstractConfiguration> set) {

    Iterator<AbstractConfiguration> childIt;
    PathSegment segment = path.get(segmentIndex);
    if (segment.isPattern()) {
      childIt = node.getChildren(segment.getPattern(), namespaceUri);
    } else {
      childIt = node.getChildren(segment.getString(), namespaceUri);
    }
    int nextIndex = segmentIndex + 1;
    boolean lastSegment;
    if (nextIndex == path.size()) {
      lastSegment = true;
    } else {
      lastSegment = false;
    }
    while (childIt.hasNext()) {
      AbstractConfiguration child = childIt.next();
      if (segment.getCondition().accept(child, namespaceUri)) {
        if (lastSegment) {
          set.add(child);
        } else {
          addDescendants(child, namespaceUri, path, nextIndex, set);
        }
      }
    }
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
   *        are the segments of the descendant path.
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
        descendant = condition.establish(descendant, namespaceUri);
        assert (condition.accept(descendant, namespaceUri));
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
   *        are the segments of the descendant path.
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
    Condition condition = segment.getCondition();
    while (childIterator.hasNext()) {
      AbstractConfiguration child = childIterator.next();
      // if we would know that our condition is an index-condition this could be
      // solved a lot faster...
      if (condition.accept(child, namespaceUri)) {
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
