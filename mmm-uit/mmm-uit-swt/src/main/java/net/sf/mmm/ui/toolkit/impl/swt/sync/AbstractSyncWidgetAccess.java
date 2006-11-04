package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This is the abstract base class used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSyncWidgetAccess extends AbstractSyncObjectAccess {

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Widget#addListener(int, org.eclipse.swt.widgets.Listener) add}
   * a listener to the widget.
   */
  private static final String OPERATION_ADD_LISTENER = "addListener";

  /** the event type for the listener to add */
  private int eventType;

  /** the listener to add */
  private Listener listener;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the widget.
   */
  public AbstractSyncWidgetAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.eventType = 0;
    this.listener = null;
  }

  /**
   * This method gets the widget to access synchron.
   * 
   * @return the widget.
   */
  @Override
  public abstract Widget getSwtObject();

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#isDisposedSynchron()
   * 
   */
  @Override
  protected boolean isDisposedSynchron() {

    return getSwtObject().isDisposed();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#disposeSynchron()
   * 
   */
  @Override
  protected void disposeSynchron() {

    getSwtObject().dispose();
    super.disposeSynchron();
  }

  /**
   * This method is called from {@link #run()}. It does the actual job for
   * the given operation.
   * 
   * @param operation
   *        is the actual operation to perform.
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_ADD_LISTENER) {
      getSwtObject().addListener(this.eventType, this.listener);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * This method is called for the create operation.
   */
  @Override
  protected void createSynchron() {

    if (this.listener != null) {
      getSwtObject().addListener(this.eventType, this.listener);
    }
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Widget#addListener(int, org.eclipse.swt.widgets.Listener) adds}
   * a listener to the widget.<br>
   * ATTENTION: This implementation expects that this method is NOT called
   * more than once before {@link #create() creation} is performed.
   * 
   * @param type
   *        is the event type to listen to.
   * @param handler
   *        is the handler that will receive the events.
   */
  public void addListener(int type, Listener handler) {

    assert (checkReady());
    this.eventType = type;
    this.listener = handler;
    invoke(OPERATION_ADD_LISTENER);
  }

}
