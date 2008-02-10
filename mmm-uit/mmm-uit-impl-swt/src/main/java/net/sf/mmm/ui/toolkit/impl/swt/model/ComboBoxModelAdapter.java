package net.sf.mmm.ui.toolkit.impl.swt.model;

import org.eclipse.swt.widgets.Combo;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncComboAccess;
import net.sf.mmm.util.event.ChangeEventType;

/**
 * This class adapts from {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} to
 * a {@link org.eclipse.swt.widgets.Combo}. It is the controller of the MVC
 * pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComboBoxModelAdapter implements UIListModelListener, Runnable {

  /** sync access to the combo-box */
  private final SyncComboAccess syncAccess;

  /** the list model */
  private UIListModel<?> model;

  /** the event to handle */
  private UIListModelEvent event;

  /**
   * The constructor.
   * 
   * @param comboAccess is the access to the SWT combo-box widget.
   * @param listModel is the model defining the elements to select.
   */
  public ComboBoxModelAdapter(SyncComboAccess comboAccess, UIListModel<?> listModel) {

    this.syncAccess = comboAccess;
    this.model = listModel;
    // this.model.addListener(this);
  }

  /**
   * {@inheritDoc}
   */
  public synchronized void listModelChanged(UIListModelEvent changeEvent) {

    this.event = changeEvent;
    this.syncAccess.getDisplay().invokeSynchron(this);
  }

  /**
   * This method gets the list-model that is adapted.
   * 
   * @return the actual list-model.
   */
  public UIListModel<?> getModel() {

    return this.model;
  }

  /**
   * This method sets the list-model. If a model was already set, all items of
   * that model will be removed. The items of the new model will be added to the
   * widget.
   * 
   * @param newModel is the new list-model.
   */
  public synchronized void setModel(UIListModel<?> newModel) {

    this.model.removeListener(this);
    this.syncAccess.removeAll();
    this.model = newModel;
    initialize();
  }

  /**
   * This method initializes the combo after it has been creation or the model
   * changed.
   */
  public void initialize() {

    if (this.syncAccess.getSwtObject() != null) {
      this.event = null;
      this.syncAccess.getDisplay().invokeSynchron(this);
      this.model.addListener(this);
    }
  }

  /**
   * This method is called synchronous to handle an model update event.
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {

    if (this.event == null) {
      // initialize
      Combo combo = this.syncAccess.getSwtObject();
      synchronized (this.model) {
        int count = this.model.getElementCount();
        for (int i = 0; i < count; i++) {
          combo.add(this.model.getElementAsString(i));
        }
      }
    } else {
      int start = this.event.getStartIndex();
      int end = this.event.getEndIndex();
      Combo combo = this.syncAccess.getSwtObject();
      if (this.event.getType() == ChangeEventType.ADD) {
        for (int i = start; i <= end; i++) {
          combo.add(this.model.getElementAsString(i), i);
        }
      } else if (this.event.getType() == ChangeEventType.REMOVE) {
        // for (int i = start; i <= end; i++) { combo.remove(i); }
        combo.remove(start, end);
      } else if (this.event.getType() == ChangeEventType.UPDATE) {
        for (int i = start; i <= end; i++) {
          combo.setItem(i, this.model.getElementAsString(i));
        }
      }
    }
  }

}
