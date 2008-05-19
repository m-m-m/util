/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;
import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTreeAccess;
import net.sf.mmm.util.event.api.ChangeEventType;

/**
 * This class adapts from {@link net.sf.mmm.ui.toolkit.api.model.UITreeModel} to
 * an {@link org.eclipse.swt.widgets.Tree swt-tree}. It is the controler of the
 * MVC pattern.
 * 
 * @param <N> is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TreeModelAdapter<N> implements UITreeModelListener<N>, Listener, Runnable {

  /** synchron access to the SWT tree */
  private final SyncTreeAccess syncAccess;

  /** the tree model */
  private UITreeModel<N> model;

  /** the node map */
  private final Map<N, TreeItem> node2itemMap;

  /** the set of items that have been expanded */
  private final Set<TreeItem> expandedItems;

  /** the event to handel synchron */
  private UITreeModelEvent<N> event;

  /**
   * The constructor.
   * 
   * @param treeAccess is the sync access to the SWT tree.
   */
  public TreeModelAdapter(SyncTreeAccess treeAccess) {

    super();
    this.syncAccess = treeAccess;
    this.syncAccess.addListener(SWT.Expand, this);
    this.model = null;
    this.node2itemMap = new HashMap<N, TreeItem>();
    this.expandedItems = new HashSet<TreeItem>();
  }

  /**
   * This method gets the model that is adapted.
   * 
   * @return the actual model.
   */
  public UITreeModel<N> getModel() {

    return this.model;
  }

  /**
   * This method sets the model. If a model was already set, all items of that
   * model will be removed. The items of the new model will be added to the
   * widget.
   * 
   * @param newModel is the new model.
   */
  public void setModel(UITreeModel<N> newModel) {

    if (this.model != null) {
      this.model.removeListener(this);
      this.syncAccess.removeAll();
      this.node2itemMap.clear();
    }
    this.model = newModel;
    initialize();
  }

  /**
   * This method initializes the tree after it has been created or the model
   * changed.
   */
  public synchronized void initialize() {

    if (this.syncAccess.getSwtObject() != null) {
      this.event = null;
      this.syncAccess.getDisplay().invokeSynchron(this);
    }
  }

  /**
   * This method creates an SWT tree item for the given node.
   * 
   * @param userNode is the node of the users tree model.
   * @param parentItem is the parent-item of the item to create.
   * @return the according tree item.
   */
  private TreeItem createNode(N userNode, TreeItem parentItem) {

    TreeItem item = this.node2itemMap.get(userNode);
    if (item == null) {
      if (parentItem == null) {
        item = new TreeItem(this.syncAccess.getSwtObject(), SWT.NONE);
      } else {
        item = new TreeItem(parentItem, SWT.NONE);
      }
      item.setData(userNode);
      item.setText(this.model.toString(userNode));
      this.node2itemMap.put(userNode, item);
    }
    return item;
  }

  /**
   * 
   * @param item
   */
  @SuppressWarnings("unchecked")
  private void expandNode(TreeItem item) {

    N userNode = (N) item.getData();
    int childCount = this.model.getChildCount(userNode);
    for (int i = 0; i < childCount; i++) {
      N childNode = this.model.getChildNode(userNode, i);
      // TreeItem childItem =
      createNode(childNode, item);
    }
  }

  /**
   * This method removes a node recursive from the node2itemMap.
   * 
   * @param node is the node to remove with all its children.
   */
  private void removeNodes(N node) {

    int childCount = this.model.getChildCount(node);
    for (int i = 0; i < childCount; i++) {
      N childNode = this.model.getChildNode(node, i);
      TreeItem childItem = this.node2itemMap.remove(childNode);
      if (childItem != null) {
        removeNodes(childNode);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void treeModelChanged(UITreeModelEvent<N> changeEvent) {

    assert (changeEvent != null);
    this.event = changeEvent;
    this.syncAccess.getDisplay().invokeSynchron(this);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void handleEvent(Event swtEvent) {

    TreeItem item = (TreeItem) swtEvent.item;
    if (!this.expandedItems.contains(item)) {
      N userNode = (N) item.getData();
      int childCount = this.model.getChildCount(userNode);
      for (int i = 0; i < childCount; i++) {
        N childNode = this.model.getChildNode(userNode, i);
        TreeItem childItem = createNode(childNode, item);
        expandNode(childItem);
      }
      this.expandedItems.add(item);
    }
  }

  /**
   * This method is called synchron to handle an model update event.
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {

    if (this.event == null) {
      // initialization
      TreeItem rootItem = createNode(this.model.getRootNode(), null);
      expandNode(rootItem);
      this.model.addListener(this);
    } else {
      N node = this.event.getTreeNode();
      if (this.event.getType() == ChangeEventType.ADD) {
        if (!this.node2itemMap.containsKey(node)) {
          N parentNode = this.model.getParent(node);
          TreeItem parentItem = this.node2itemMap.get(parentNode);
          if (parentItem != null) {
            TreeItem item = createNode(node, parentItem);
            if (parentItem.getExpanded()) {
              expandNode(item);
            }
          }
        }
      } else if (this.event.getType() == ChangeEventType.REMOVE) {
        TreeItem item = this.node2itemMap.remove(node);
        if (item != null) {
          item.dispose();
          removeNodes(node);
        }
      } else if (this.event.getType() == ChangeEventType.UPDATE) {
        TreeItem item = this.node2itemMap.get(node);
        if (item != null) {
          item.setText(this.model.toString(node));
        }
      }
    }
  }

}
