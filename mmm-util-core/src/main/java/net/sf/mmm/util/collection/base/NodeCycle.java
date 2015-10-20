/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.value.api.SimpleValueConverter;

/**
 * This class is used to detect and represent a cyclic dependency of nodes.
 *
 * @param <V> is the generic type of the nodes.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NodeCycle<V> {

  /** @see #getInverseCycle() */
  private List<V> inverseCycle;

  /** @see #getStartNode() */
  private V startNode;

  /** @see #toString() */
  private final SimpleValueConverter<V, String> formatter;

  /**
   * The constructor.
   *
   * @param startNode is the {@link #getStartNode() start-node}.
   */
  public NodeCycle(V startNode) {

    this(startNode, null);
  }

  /**
   * The constructor.
   *
   * @param startNode is the {@link #getStartNode() start-node}.
   * @param formatter is the {@link SimpleValueConverter} used to get a custom string-representation of the nodes. If
   *        <code>null</code> {@link Object#toString()} is used as fallback.
   */
  public NodeCycle(V startNode, SimpleValueConverter<V, String> formatter) {

    super();
    this.startNode = startNode;
    this.formatter = formatter;
    this.inverseCycle = new ArrayList<>();
    this.inverseCycle.add(this.startNode);
  }

  /**
   * This method gets the {@link List} of nodes that build a cycle. It is stored in reverse order so the last node is
   * the start of the cycle from top-level. The first node will be the same node as the last one.
   *
   * @return the inverse cycle of nodes.
   */
  public List<V> getInverseCycle() {

    return this.inverseCycle;
  }

  /**
   * This method gets the start node where the detected cycle begins.
   *
   * @return the start node.
   */
  public V getStartNode() {

    return this.startNode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    for (int i = this.inverseCycle.size() - 1; i >= 0; i--) {
      V node = this.inverseCycle.get(i);
      if (this.formatter == null) {
        sb.append(node);
      } else {
        sb.append(this.formatter.convert(node, NodeCycle.class, String.class));
      }
      if (i > 0) {
        sb.append("-->");
      }
    }
    return sb.toString();
  }

}
