/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.ListModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIList} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIListImpl<E> extends AbstractUIWidget implements UIList<E> {

  /** the swing scroll pane */
  private JScrollPane scrollPanel;

  /** the swing list */
  private JList list;

  /** the list model adapter */
  private ListModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UIListImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.list = new JList();
    this.scrollPanel = new JScrollPane(this.list);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection()
   * 
   */
  public boolean isMultiSelection() {

    int mode = this.list.getSelectionMode();
    return (mode != ListSelectionModel.SINGLE_SELECTION);
  }

  /**
   * This method sets the selection mode of this list.
   * 
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple items, else ony
   *        one.
   */
  public void setMultiSelection(boolean multiSelection) {

    int mode = ListSelectionModel.SINGLE_SELECTION;
    if (multiSelection) {
      mode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
    }
    this.list.setSelectionMode(mode);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#getModel()
   * 
   */
  public UIListModel<E> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   * 
   */
  public void setModel(UIListModel<E> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new ListModelAdapter<E>(newModel);
    this.list.setModel(this.modelAdapter);
    this.list.setCellRenderer(new Renderer());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   * 
   */
  public int getSelectedIndex() {

    return this.list.getSelectedIndex();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   * 
   */
  public void setSelectedIndex(int newIndex) {

    this.list.setSelectedIndex(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#getSelectedIndices()
   * 
   */
  public int[] getSelectedIndices() {

    return this.list.getSelectedIndices();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   * 
   */
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getActiveSwingComponent()
   * 
   */
  protected JComponent getActiveSwingComponent() {

    return this.list;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   * 
   */
  public void setSelectedValue(E newValue) {

    int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
    if (newIndex != -1) {
      setSelectedIndex(newIndex);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   * 
   */
  public E getSelectedValue() {

    int index = this.list.getSelectedIndex();
    if (index == -1) {
      return null;
    }
    return this.modelAdapter.getModel().getElement(index);
  }

  /**
   * TODO This inner class is the renderer used to layout the list items.
   */
  private class Renderer extends DefaultListCellRenderer {

    /** UID for serialization */
    private static final long serialVersionUID = 7629826608171331045L;

    /**
     * The constructor.
     */
    public Renderer() {

      super();
    }

    /**
     * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
     *      java.lang.Object, int, boolean, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList swingList, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

      super.getListCellRendererComponent(swingList, value, index, isSelected, cellHasFocus);
      setText(UIListImpl.this.modelAdapter.getModel().toString((E) value));
      return this;
    }
  }

}
