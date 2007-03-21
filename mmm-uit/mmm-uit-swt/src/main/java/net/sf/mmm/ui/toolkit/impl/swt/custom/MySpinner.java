/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.state.UIWriteEditable;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex;

/**
 * This class is a custom implementation of an SWT spinner (spin-box).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MySpinner extends Composite implements UIWriteSelectionIndex, UIListModelListener,
    UIWriteEditable {

  /** the width of the inc/dec buttons */
  private static final int FIXED_WIDTH = 15;

  /** the textfield */
  private final Text textField;

  /** the spinner */
  private final Spinner spinner;

  /** the current selection index */
  private int selection;

  /** the model of the elements for this spinner */
  private UIListModel<?> model;

  /**
   * The constructor.
   * 
   * @param parent
   *        is the parent composite.
   * @param style
   *        is the style of this widget.
   * @param listModel
   *        is the model for the elements of this spinner.
   */
  public MySpinner(Composite parent, int style, UIListModel<?> listModel) {

    super(parent, style);
    this.textField = new Text(this, style | SWT.SINGLE | SWT.BORDER);
    this.textField.setEditable(false);
    this.model = listModel;
    this.spinner = new Spinner(this, SWT.DEFAULT);
    this.spinner.setMinimum(0);
    this.spinner.setMaximum(this.model.getElementCount() - 1);
    this.model.addListener(this);

    this.spinner.addListener(SWT.Selection, new Listener() {

      public void handleEvent(Event event) {

        updateSelection();
      };
    });

    this.textField.addListener(SWT.Traverse, new Listener() {

      public void handleEvent(Event e) {

        traverse(e);
      }
    });

    addListener(SWT.Resize, new Listener() {

      public void handleEvent(Event e) {

        resize();
      }
    });

    addListener(SWT.FocusIn, new Listener() {

      public void handleEvent(Event e) {

        MySpinner.this.textField.setFocus();
      }
    });

    this.textField.setFont(getFont());

    setSelectedIndex(0);
    resize();
  }

  /**
   * 
   * @param newModel
   */
  public void setModel(UIListModel<?> newModel) {

    this.model.removeListener(this);
    this.model = newModel;
    this.model.addListener(this);
    update();
  }

  /**
   * This method is invoked if a key is pressed in the text-field.
   * 
   * @param event
   *        is the key-press event.
   */
  private void traverse(Event event) {

    switch (event.detail) {
      case SWT.TRAVERSE_ARROW_PREVIOUS:
        if (event.keyCode == SWT.ARROW_UP) {
          event.doit = true;
          event.detail = SWT.NULL;
          increment();
        }
        break;
      case SWT.TRAVERSE_ARROW_NEXT:
        if (event.keyCode == SWT.ARROW_DOWN) {
          event.doit = true;
          event.detail = SWT.NULL;
          decrement();
        }
        break;
      case SWT.TRAVERSE_RETURN:
        if (event.keyCode == 13) {
          event.doit = true;
          event.detail = SWT.NULL;
          int newIndex = this.model.getIndexOfString(this.textField.getText());
          if (newIndex == -1) {
            setSelection(this.selection);
          } else {
            setSelectedIndex(newIndex);
          }
        }
    }
  }

  /**
   * This method is invoked if the increment button was pressed.
   */
  private void increment() {

    int max = this.model.getElementCount() - 1;
    if (this.selection < max) {
      int nextIndex = this.selection + 1;
      setSelectedIndex(nextIndex);
    }
  }

  /**
   * This method is invoked if the decrement button was pressed.
   */
  private void decrement() {

    int prevIndex = this.selection;
    if (this.selection > 0) {
      prevIndex--;
      setSelectedIndex(prevIndex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFont(Font font) {

    super.setFont(font);
    this.textField.setFont(font);
  }

  /**
   * This method resizes this widget.
   */
  private void resize() {

    Point p = getSize();
    p = computeSize(p.x, p.y);
    int textFieldWidth = p.x - FIXED_WIDTH;
    this.textField.setBounds(0, 0, textFieldWidth, p.y);
    this.spinner.setBounds(textFieldWidth, 0, FIXED_WIDTH, p.y);
  }

  /**
   * This method updates the selection made via the spinner.
   */
  private void updateSelection() {

    int newSelection = this.spinner.getSelection();
    if (((newSelection == 0) && (this.selection == this.spinner.getMaximum()))
        || (this.selection == 0) && (newSelection == this.spinner.getMaximum())) {
      this.spinner.setSelection(this.selection);
    } else {
      setSelection(newSelection);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Point computeSize(int wHint, int hHint, boolean changed) {

    int height = hHint;
    int width = wHint;
    if ((height == SWT.DEFAULT) || (width == SWT.DEFAULT)) {
      Point p = this.textField.computeSize(wHint, hHint, changed);
      if (height == SWT.DEFAULT) {
        height = p.y;
      }
      if (width == SWT.DEFAULT) {
        width = p.x + FIXED_WIDTH;
      }
    }
    return new Point(width, height);

  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.selection;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.spinner.setSelection(newIndex);
    setSelection(newIndex);
  }

  /**
   * This method sets the selection index without updating the spinner.
   * 
   * @param newIndex
   *        is the new index to set.
   */
  private void setSelection(int newIndex) {

    String value = this.model.getElementAsString(newIndex);
    this.selection = newIndex;
    this.textField.setText(value);
    this.textField.setFocus();
  }

  /**
   * {@inheritDoc}
   */
  public void listModelChanged(UIListModelEvent event) {

    int max = this.model.getElementCount() - 1;
    this.spinner.setMaximum(max);
    if (this.selection > max) {
      this.selection = max;
    }
    setSelectedIndex(this.selection);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.textField.getEditable();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    this.textField.setEditable(editableFlag);
  }

}
