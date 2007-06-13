package net.sf.mmm.ui.toolkit.impl.swt.model;

import org.eclipse.swt.widgets.List;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncListAccess;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This class adapts from {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} to
 * a {@link org.eclipse.swt.widgets.List}. It is the controler of the MVC
 * pattern.
 * 
 * @param <E> is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ListModelAdapter<E> implements UIListModelListener, Runnable {

  /** the sync access to the SWT list */
  private final SyncListAccess syncAccess;

  /** the list model */
  private UIListModel<E> model;

  /** the curent event to handle */
  private UIListModelEvent event;

  /**
   * The constructor.
   * 
   * @param listAccess is the synchron access to the SWT list.
   * @param listModel is the model to adapt.
   */
  public ListModelAdapter(SyncListAccess listAccess, UIListModel<E> listModel) {

    this.syncAccess = listAccess;
    this.model = listModel;
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
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * This method sets the list-model. If a model was already set, all items of
   * that model will be removed. The items of the new model will be added to the
   * widget.
   * 
   * @param newModel is the new list-model.
   */
  public void setModel(UIListModel<E> newModel) {

    this.model.removeListener(this);
    this.syncAccess.removeAll();
    this.model = newModel;
    initialize();
  }

  /**
   * This method initializes the list after it has been creation or the model
   * changed.
   */
  public void initialize() {

    if (this.model != null) {
      synchronized (this.model) {
        int count = this.model.getElementCount();
        for (int i = 0; i < count; i++) {
          this.syncAccess.addItem(this.model.getElementAsString(i));
        }
      }
      this.model.addListener(this);
    }
  }

  /**
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {

    List list = this.syncAccess.getSwtObject();
    int start = this.event.getStartIndex();
    int end = this.event.getEndIndex();
    // TODO synchonization!!!
    if (this.event.getType() == Type.ADD) {
      for (int i = start; i <= end; i++) {
        // TODO maybe define method that performs the string conversion
        list.add(this.model.getElementAsString(i), i);
      }
    } else if (this.event.getType() == Type.REMOVE) {
      // for (int i = start; i <= end; i++) { list.remove(i); }
      list.remove(start, end);
    } else if (this.event.getType() == Type.UPDATE) {
      for (int i = start; i <= end; i++) {
        // TODO maybe define method that performs the string conversion
        list.setItem(i, this.model.getElementAsString(i));
      }
    }
  }

}
