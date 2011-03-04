/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.ListModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiList} interface using Swing as
 * the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIListImpl<E> extends AbstractUiWidget implements UiList<E> {

  /** the swing scroll pane */
  private JScrollPane scrollPanel;

  /** the swing list */
  private JList list;

  /** the list model adapter */
  private ListModelAdapter<E> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UIListImpl(UIFactorySwing uiFactory, UiComposite<? extends UiElement> parentObject) {

    super(uiFactory);
    this.list = new JList();
    this.scrollPanel = new JScrollPane(this.list);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMultiSelection() {

    int mode = this.list.getSelectionMode();
    return (mode != ListSelectionModel.SINGLE_SELECTION);
  }

  /**
   * This method sets the selection mode of this list.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items, else ony one.
   */
  public void setMultiSelection(boolean multiSelection) {

    int mode = ListSelectionModel.SINGLE_SELECTION;
    if (multiSelection) {
      mode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
    }
    this.list.setSelectionMode(mode);
  }

  /**
   * {@inheritDoc}
   */
  public UiListMvcModel<E> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new ListModelAdapter<E>(newModel);
    this.list.setModel(this.modelAdapter);
    this.list.setCellRenderer(new Renderer());
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.list.getSelectedIndex();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.list.setSelectedIndex(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public int[] getSelectedIndices() {

    return this.list.getSelectedIndices();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JComponent getActiveSwingComponent() {

    return this.list;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int newIndex = this.modelAdapter.getModel().getIndexOf(newValue);
    if (newIndex != -1) {
      setSelectedIndex(newIndex);
    }
  }

  /**
   * {@inheritDoc}
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

    /** UID for serialization. */
    private static final long serialVersionUID = 7629826608171331045L;

    /**
     * The constructor.
     */
    public Renderer() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList swingList, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {

      super.getListCellRendererComponent(swingList, value, index, isSelected, cellHasFocus);
      String text;
      if (value == null) {
        text = UIListImpl.this.modelAdapter.getModel().getNullString();
      } else {
        text = UIListImpl.this.modelAdapter.getModel().toString((E) value);
      }
      setText(text);
      return this;
    }
  }

}