package net.sf.mmm.ui.toolkit.impl.swing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UITreeModel} to a
 * Swing {@link javax.swing.tree.TreeModel}.
 * 
 * @param <N> is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TreeModelAdapter<N> implements TreeModel, UITreeModelListener<N> {

  /** the original tree model */
  private final UITreeModel model;

  /**
   * the list of all registered tree model listeners (
   * {@link TreeModelListener TreeModelListener}objects)
   */
  private final List<TreeModelListener> listeners;

  /**
   * The constructor.
   * 
   * @param treeModel is the tree model to adapt.
   */
  @SuppressWarnings("unchecked")
  public TreeModelAdapter(UITreeModel<N> treeModel) {

    super();
    this.model = treeModel;
    this.listeners = new Vector<TreeModelListener>();
    this.model.addListener(this);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Object getRoot() {

    return this.model.getRootNode();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Object getChild(Object parent, int index) {

    return this.model.getChildNode(parent, index);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public int getChildCount(Object parent) {

    return this.model.getChildCount(parent);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public boolean isLeaf(Object node) {

    return (this.model.getChildCount(node) == 0);
  }

  /**
   * {@inheritDoc}
   */
  public void valueForPathChanged(TreePath path, Object newValue) {

  // we do not support editors, yet
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public int getIndexOfChild(Object parent, Object child) {

    for (int i = 0; i < this.model.getChildCount(parent); i++) {
      if (child == this.model.getChildNode(parent, i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  public void addTreeModelListener(TreeModelListener l) {

    this.listeners.add(l);
  }

  /**
   * {@inheritDoc}
   */
  public void removeTreeModelListener(TreeModelListener l) {

    this.listeners.remove(l);
  }

  /**
   * This method fires an update event to all registered listeners.
   * 
   * @param event is the event to fire.
   */
  private void fireUpdateEvent(UITreeModelEvent<N> event) {

    TreePath treePath = getTreePath(event.getTreeNode());
    TreeModelEvent swingEvent = new TreeModelEvent(this, treePath);
    for (int i = 0; i < this.listeners.size(); i++) {
      TreeModelListener listener = this.listeners.get(i);
      // //treeNodesChanged is not handled by swing!
      // listener.treeNodesChanged(swingEvent);
      listener.treeStructureChanged(swingEvent);
    }
  }

  /**
   * This method fires an add event to all registered listeners.
   * 
   * @param event is the event to fire.
   */
  @SuppressWarnings("unchecked")
  private void fireAddEvent(UITreeModelEvent<N> event) {

    Object parentNode = this.model.getParent(event.getTreeNode());
    TreePath treePath = getTreePath(parentNode);
    TreeModelEvent swingEvent = new TreeModelEvent(this, treePath);
    for (int i = 0; i < this.listeners.size(); i++) {
      TreeModelListener listener = this.listeners.get(i);
      // //treeNodesInserted is not handled by swing!
      // listener.treeNodesInserted(event);
      listener.treeStructureChanged(swingEvent);
    }
  }

  /**
   * This method fires a remove event to all registered listeners.
   * 
   * @param event is the event to fire.
   */
  @SuppressWarnings("unchecked")
  private void fireRemoveEvent(UITreeModelEvent<N> event) {

    Object parentNode = this.model.getParent(event.getTreeNode());
    TreePath treePath = getTreePath(parentNode);
    TreeModelEvent swingEvent = new TreeModelEvent(this, treePath);
    for (int i = 0; i < this.listeners.size(); i++) {
      TreeModelListener listener = this.listeners.get(i);
      // //treeNodesRemoved does NOT help after the node is removed!
      // listener.treeNodesRemoved(swingEvent);
      listener.treeStructureChanged(swingEvent);
    }
  }

  /**
   * This method gets the tree-path of a given node.
   * 
   * @param node is the tree-node.
   * @return the tree-path from the root-node to the according node.
   */
  @SuppressWarnings("unchecked")
  protected TreePath getTreePath(Object node) {

    final List path = new ArrayList();
    // Object node = event.getTreeNode();
    while (node != null) {
      path.add(node);
      node = this.model.getParent(node);
    }
    int len = path.size();
    int last = len - 1;
    Object[] treePath = new Object[len];
    for (int i = 0; i < len; i++) {
      treePath[i] = path.get(last - i);
    }
    return new TreePath(treePath);
  }

  /**
   * {@inheritDoc}
   */
  public void treeModelChanged(UITreeModelEvent<N> event) {

    // TreePath treePath = getTreePath(event);
    // only treeStructureChanged and treeNodeRemoved are handled by swing!
    if (event.getType() == Type.ADD) {
      fireAddEvent(event);
    } else if (event.getType() == Type.REMOVE) {
      fireRemoveEvent(event);
    } else if (event.getType() == Type.UPDATE) {
      fireUpdateEvent(event);
    }
  }

  /**
   * This method gets the adapted tree model.
   * 
   * @return the model.
   */
  @SuppressWarnings("unchecked")
  public UITreeModel<N> getModel() {

    return this.model;
  }

  /**
   * This method disposes this model adapter. It is called when the adapter is
   * NOT used anymore and can be eaten by the garbarge-collector. The
   * implementation of this method can tidy-up (e.g. remove registered
   * listeners).
   */
  @SuppressWarnings("unchecked")
  public void dispose() {

    this.model.removeListener(this);
  }

}
