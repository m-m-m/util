/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFlagAdvanced;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiWidgetTreeNode;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetNative;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;
import net.sf.mmm.client.ui.gwt.widgets.HtmlConstants;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.validation.api.ValidationState;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * This is the implementation of {@link UiWidgetAdapterTree} using GWT based on {@link Tree}.
 * 
 * @param <NODE> is the generic type of the tree-nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTree<NODE> extends UiWidgetAdapterGwtWidgetActive<FlowPanel> implements
    UiWidgetAdapterTree<NODE> {

  /** @see #setRootNode(Object) */
  private NODE rootNode;

  /** @see #setTreeModel(UiTreeModel) */
  private UiTreeModel<NODE> treeModel;

  /** @see #setTreeNodeRenderer(UiTreeNodeRenderer) */
  private UiTreeNodeRenderer<NODE, ?> treeNodeRenderer;

  /** Maps from {@literal <NODE>} to {@link TreeNodeAdapter}. */
  private Map<NODE, TreeNodeAdapter> nodeMap;

  /** @see #setTitle(String) */
  private InlineLabel titleHeader;

  /** @see #getGwtTree() */
  private Tree tree;

  /** @see #getScrollPanel() */
  private ScrollPanel scrollPanel;

  /** @see #setSelectionMode(SelectionMode) */
  private SelectionMode selectionMode;

  /** The {@link TreeNodeAdapter} for {@link #rootNode}. */
  private TreeNodeAdapter rootNodeAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTree() {

    super();
    this.nodeMap = new HashMap<NODE, TreeNodeAdapter>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeModel(UiTreeModel<NODE> treeModel) {

    this.treeModel = treeModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer) {

    this.treeNodeRenderer = renderer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    if (this.selectionMode == selectionMode) {
      return;
    }
    this.selectionMode = selectionMode;
    if (this.rootNodeAdapter != null) {
      this.rootNodeAdapter.updateSelectionMode();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValue(NODE selectedValue) {

    TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(selectedValue);
    if (treeNodeAdapter != null) {
      if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
        getGwtTree().setSelectedItem(treeNodeAdapter);
      } else {
        clearMultiSelection();
        treeNodeAdapter.multiSelectionCheckbox.setValue(Boolean.TRUE);
      }
    }
  }

  /**
   * Clear {@link SimpleCheckBox}es of {@link SelectionMode#MULTIPLE_SELECTION}.
   */
  private void clearMultiSelection() {

    for (TreeNodeAdapter adapter : this.nodeMap.values()) {
      adapter.multiSelectionCheckbox.setValue(Boolean.FALSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValues(List<NODE> selectedValues) {

    if (this.selectionMode == SelectionMode.MULTIPLE_SELECTION) {
      clearMultiSelection();
      for (NODE node : selectedValues) {
        TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(node);
        if (treeNodeAdapter != null) {
          treeNodeAdapter.multiSelectionCheckbox.setValue(Boolean.TRUE);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    return (getSelectedValue() != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NODE getSelectedValue() {

    if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
      TreeNodeAdapter selectedItem = (TreeNodeAdapter) getGwtTree().getSelectedItem();
      if (selectedItem != null) {
        return selectedItem.node;
      }
    } else if (this.selectionMode == SelectionMode.MULTIPLE_SELECTION) {
      for (TreeNodeAdapter adapter : this.nodeMap.values()) {
        if (adapter.multiSelectionCheckbox.getValue().booleanValue()) {
          return adapter.node;
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<NODE> getSelectedValues() {

    List<NODE> selectedValues = new LinkedList<NODE>();
    if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
      TreeNodeAdapter selectedItem = (TreeNodeAdapter) getGwtTree().getSelectedItem();
      if (selectedItem != null) {
        selectedValues.add(selectedItem.node);
      }
    } else if (this.selectionMode == SelectionMode.MULTIPLE_SELECTION) {
      for (TreeNodeAdapter adapter : this.nodeMap.values()) {
        if (adapter.multiSelectionCheckbox.getValue().booleanValue()) {
          selectedValues.add(adapter.node);
        }
      }
    }
    return selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTreeNode<NODE> getTreeNodeWidget(NODE node) {

    TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(node);
    return treeNodeAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void collapseAllNodes() {

    for (TreeNodeAdapter adapter : this.nodeMap.values()) {
      adapter.setState(false);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void expandNodes() {

    for (TreeNodeAdapter adapter : this.nodeMap.values()) {
      adapter.setState(true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootNode(NODE node) {

    if (this.rootNode != null) {
      TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(this.rootNode);
      if (treeNodeAdapter != null) {
        treeNodeAdapter.remove();
      }
      this.nodeMap.clear();
    }
    this.rootNode = node;
    this.rootNodeAdapter = createTreeNodeAdapter(this.rootNode);
    this.nodeMap.put(this.rootNode, this.rootNodeAdapter);
    this.rootNodeAdapter.loadChildren();
    getGwtTree().addItem(this.rootNodeAdapter);
  }

  /**
   * @param node is the {@literal <NODE>} to wrap.
   * @return the {@link TreeNodeAdapter} for the given <code>node</code>.
   */
  private TreeNodeAdapter createTreeNodeAdapter(NODE node) {

    UiWidgetRegular widget = this.treeNodeRenderer.create(getUiWidget().getContext());
    TreeNodeAdapter treeNodeAdapter = new TreeNodeAdapter(node, widget);
    return treeNodeAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EventAdapterGwt createEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    EventAdapterGwtTree adapter = new EventAdapterGwtTree(source, sender);
    return adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForSelection(EventAdapterGwt adapter) {

    HandlerRegistration registration = getGwtTree().addSelectionHandler((EventAdapterGwtTree) adapter);
    addHandlerRegistration(registration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getTitleHeader().setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    if (enabled) {
      getGwtTree().removeStyleName("Disabled");
    } else {
      getGwtTree().addStyleName("Disabled");
    }
  }

  /**
   * @param event is the {@link OpenEvent}.
   */
  private void onOpenEvent(OpenEvent<TreeItem> event) {

    TreeNodeAdapter treeNodeAdapter = (TreeNodeAdapter) event.getTarget();
    treeNodeAdapter.loadChildren();
  }

  /**
   * @return the raw GWT {@link Tree}.
   */
  public Tree getGwtTree() {

    if (this.tree == null) {
      this.tree = new Tree();
      this.tree.setTabIndex(HtmlConstants.TAB_INDEX_DISABLE);
      OpenHandler<TreeItem> openHandler = new OpenHandler<TreeItem>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onOpen(OpenEvent<TreeItem> event) {

          onOpenEvent(event);
        }
      };
      this.tree.addOpenHandler(openHandler);
    }
    return this.tree;
  }

  /**
   * @return the {@link ScrollPanel} containing the {@link #getGwtTree() tree}.
   */
  public ScrollPanel getScrollPanel() {

    if (this.scrollPanel == null) {
      this.scrollPanel = new ScrollPanel(getGwtTree());
    }
    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Element getSizeElement() {

    return getScrollPanel().getElement();
  }

  /**
   * @return the titleHeader
   */
  public InlineLabel getTitleHeader() {

    if (this.titleHeader == null) {
      this.titleHeader = new InlineLabel();
      this.titleHeader.setStylePrimaryName("Header");
    }
    return this.titleHeader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createToplevelWidget() {

    FlowPanel toplevelWidget = new FlowPanel();
    toplevelWidget.add(getTitleHeader());
    toplevelWidget.add(getScrollPanel());
    toplevelWidget.setStylePrimaryName(UiWidgetAbstractTree.STYLE_TREE);
    return toplevelWidget;
  }

  /**
   * This inner class adapts from {@literal <NODE>} to {@link TreeItem}.
   */
  // ATTENTION: This is actually a little dangerous if GWT TreeItem changes on GWT update as it might clash
  // with our own methods. But adding a full-fledged widget and move this into another adapter abstraction per
  // node would be too much overhead and cause bad performance...
  protected class TreeNodeAdapter extends TreeItem implements Consumer<List<NODE>>, ValueChangeHandler<Boolean>,
      ClickHandler, UiWidgetTreeNode<NODE> {

    /** @see #getNode() */
    private final NODE node;

    /** @see #getWidget() */
    private final UiWidgetRegular nodeWidget;

    /** @see #loadChildren() */
    private boolean loaded;

    /** @see #getWidgetPanel() */
    private FlowPanel widgetPanel;

    /** The {@link SimpleCheckBox} for {@link SelectionMode#MULTIPLE_SELECTION}. */
    private SimpleCheckBox multiSelectionCheckbox;

    /**
     * The dummy constructor.
     */
    private TreeNodeAdapter() {

      super();
      this.loaded = true;
      this.node = null;
      this.nodeWidget = null;
      this.widgetPanel = null;
    }

    /**
     * The constructor.
     * 
     * @param node is the unwrapped node (business object).
     * @param widget is the {@link UiWidgetRegular} rendering this node.
     */
    public TreeNodeAdapter(NODE node, UiWidgetRegular widget) {

      super();
      this.nodeWidget = widget;
      this.node = node;
      switch (UiWidgetAdapterGwtTree.this.selectionMode) {
        case SINGLE_SELECTION:
          setWidget(getToplevelWidget(widget));
          break;
        case MULTIPLE_SELECTION:
          initializeMultiSelection();
          break;
        default :
          throw new IllegalCaseException(SelectionMode.class, UiWidgetAdapterGwtTree.this.selectionMode);
      }
      updateNode();
      this.loaded = false;
      // add a dummy child so the node can be expanded for lazy loading...
      addItem(new TreeNodeAdapter());
    }

    /**
     * @return the node
     */
    public NODE getNode() {

      return this.node;
    }

    /**
     * @return the widgetPanel
     */
    public FlowPanel getWidgetPanel() {

      return this.widgetPanel;
    }

    /**
     * Updates the UI of this node.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void updateNode() {

      ((UiTreeNodeRenderer) UiWidgetAdapterGwtTree.this.treeNodeRenderer)
          .assignNodeToWidget(this.node, this.nodeWidget);
    }

    /**
     * Loads the children of this node asynchronously.
     */
    protected void loadChildren() {

      if (this.loaded) {
        return;
      }
      UiWidgetAdapterGwtTree.this.treeModel.getChildrenAsync(this.node, this);
      this.loaded = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(List<NODE> value) {

      clearChildren();
      for (NODE childNode : value) {
        TreeNodeAdapter childNodeAdapter = createTreeNodeAdapter(childNode);
        UiWidgetAdapterGwtTree.this.nodeMap.put(childNode, childNodeAdapter);
        addItem(childNodeAdapter);
      }
    }

    /**
     * Clears all existing children.
     */
    private void clearChildren() {

      if (this.loaded) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
          TreeNodeAdapter child = (TreeNodeAdapter) getChild(i);
          TreeNodeAdapter oldNode = UiWidgetAdapterGwtTree.this.nodeMap.remove(child.node);
          assert (oldNode == child);
        }
      }
      removeItems();
    }

    /**
     * Recursively updates the {@link SelectionMode}.
     */
    private void updateSelectionMode() {

      switch (UiWidgetAdapterGwtTree.this.selectionMode) {
        case SINGLE_SELECTION:
          if (this.widgetPanel != null) {
            this.multiSelectionCheckbox.setVisible(false);
            // TODO: deselect
          }
          break;
        case MULTIPLE_SELECTION:
          if (this.widgetPanel != null) {
            initializeMultiSelection();
          }
          break;
        default :
          throw new IllegalCaseException(SelectionMode.class, UiWidgetAdapterGwtTree.this.selectionMode);
      }

    }

    /**
     * Initializes checkboxes for {@link SelectionMode#MULTIPLE_SELECTION multi-selection}.
     */
    private void initializeMultiSelection() {

      this.widgetPanel = new FlowPanel();
      this.widgetPanel.setStylePrimaryName("TreeItemContainer");
      this.multiSelectionCheckbox = new SimpleCheckBox();
      this.multiSelectionCheckbox.addClickHandler(this);
      this.widgetPanel.add(this.multiSelectionCheckbox);
      this.widgetPanel.add(getToplevelWidget(this.nodeWidget));
      setWidget(this.widgetPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(ClickEvent event) {

      setNodeSelected(this.multiSelectionCheckbox.getValue().booleanValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValueChange(ValueChangeEvent<Boolean> event) {

      boolean selected = event.getValue().booleanValue();
      setNodeSelected(selected);
    }

    /**
     * Sets the selection state.
     * 
     * @param selected - <code>true</code> for selected, <code>false</code> for not selected.
     */
    private void setNodeSelected(boolean selected) {

      String style = "Selected";
      if (selected) {
        this.widgetPanel.addStyleName(style);
      } else {
        this.widgetPanel.removeStyleName(style);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRegular getNodeWidget() {

      return this.nodeWidget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollapsed() {

      return !getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCollapsed(boolean collapsed) {

      setState(!collapsed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTooltip(String tooltip) {

      super.setTitle(tooltip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTooltip() {

      return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(String id) {

      getElement().setId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {

      return getElement().getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiMode getMode() {

      return this.nodeWidget.getMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMode(UiMode mode) {

      this.nodeWidget.setMode(mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModeFixed(UiMode mode) {

      this.nodeWidget.setModeFixed(mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiMode getModeFixed() {

      return this.nodeWidget.getModeFixed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetComposite<?> getParent() {

      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NODE getValue() {

      return this.node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(boolean enabled) {

      // TODO hohwille
      this.nodeWidget.setEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(boolean visible, boolean programmatic) {

      setVisible(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {

      return this.nodeWidget.isEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {

      this.nodeWidget.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisposed() {

      return this.nodeWidget.isDisposed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisibleRecursive() {

      if (!getUiWidget().isVisibleRecursive()) {
        return false;
      }
      return isVisible();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiContext getContext() {

      return getUiWidget().getContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(ValidationState state) {

      return this.nodeWidget.validate(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEventHandler(UiHandlerEvent handler) {

      this.nodeWidget.addEventHandler(handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEventHandler(UiHandlerEvent handler) {

      return this.nodeWidget.removeEventHandler(handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrimaryStyle(String primaryStyle) {

      setStylePrimaryName(primaryStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStyles(String styles) {

      super.setStyleName(styles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStyles() {

      return getStyleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addStyle(String style) {

      super.addStyleName(style);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeStyle(String style) {

      removeStyleName(style);
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryStyle() {

      return getStylePrimaryName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasStyle(String style) {

      return (AbstractUiWidgetNative.getIndexOfStyle(getStyles(), style) >= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLength(LengthProperty property, Length length) {

      DOM.setStyleAttribute(getElement(), property.getMemberName(), length.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Length getLength(LengthProperty property) {

      String value = DOM.getStyleAttribute(getSizeElement(), property.getMemberName());
      return new Length(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getAriaRole() {

      return this.nodeWidget.getAriaRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWidthInPixel() {

      return getOffsetWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeightInPixel() {

      return getOffsetHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Length getWidth() {

      return Length.valueOfPixel(getWidthInPixel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Length getHeight() {

      return Length.valueOfPixel(getHeightInPixel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isModified() {

      return this.nodeWidget.isModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidth(Length width) {

      setWidth(width.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeight(Length height) {

      setHeight(height.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(Length width, Length height) {

      setWidth(width);
      setHeight(height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeWriteFlagAdvanced getVisibleFlag() {

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearMessages() {

      this.nodeWidget.clearMessages();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeightInPercent(double heightInPercent) {

      setHeight(Length.valueOfPercent(heightInPercent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeightInPixel(double heightInPixel) {

      setHeight(Length.valueOfPixel(heightInPixel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidthInPercent(double widthInPercent) {

      setWidth(Length.valueOfPercent(widthInPercent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidthInPixel(double widthInPixel) {

      setWidth(Length.valueOfPixel(widthInPixel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(double width, double height, LengthUnit unit) {

      setSize(new Length(width, unit), new Length(height, unit));
    }

  }

  /**
   * @see UiWidgetAdapterGwtTree#createEventAdapter(UiFeatureEvent, UiHandlerEvent)
   */
  private static class EventAdapterGwtTree extends EventAdapterGwt implements SelectionHandler<TreeItem> {

    /**
     * The constructor.
     * 
     * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
     * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
     */
    public EventAdapterGwtTree(UiFeatureEvent source, UiHandlerEvent sender) {

      super(source, sender);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSelection(SelectionEvent<TreeItem> event) {

      fireEvent(EventType.SELECTION_CHANGE);
    }

  }
}
