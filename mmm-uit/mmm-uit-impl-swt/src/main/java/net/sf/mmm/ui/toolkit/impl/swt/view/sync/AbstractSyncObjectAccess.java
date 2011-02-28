/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.UiDisplay;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This is the abstract base class used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSyncObjectAccess implements Runnable {

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Widget#Widget(org.eclipse.swt.widgets.Widget, int) create}
   * the object. This operation will be implemented in the according sub-class.
   */
  private static final String OPERATION_CREATE = "create";

  /**
   * operation to {@link org.eclipse.swt.widgets.Widget#dispose() dispose} to
   * widget.
   */
  private static final String OPERATION_DISPOSE = "dispose";

  /**
   * operation to determine if the widget is
   * {@link org.eclipse.swt.widgets.Widget#isDisposed() disposed}.
   */
  private static final String OPERATION_IS_DISPOSED = "isDisposed";

  /** the ui display */
  private final UiDisplay display;

  /** the operation to perform in {@link #run()} */
  private String op;

  /** the {@link org.eclipse.swt.SWT} style of this widget */
  private int style;

  /** <code>true</code> if the widget is disposed */
  private boolean disposed;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        widget.
   */
  public AbstractSyncObjectAccess(UiFactorySwt uiFactory, int swtStyle) {

    super();
    this.display = uiFactory.getDisplay();
    this.op = null;
    this.disposed = false;
    this.style = swtStyle;
  }

  /**
   * This method gets the widget to access synchronous.
   * 
   * @return the widget.
   */
  public abstract Object getSwtObject();

  /**
   * {@inheritDoc}
   */
  public final void run() {

    Object widget = getSwtObject();
    if (widget == null) {
      if (this.op == OPERATION_CREATE) {
        doCreateSynchron();
      }
    } else {
      if (isDisposedSynchron()) {
        handleDisposed();
      } else {
        performSynchron(this.op);
      }
    }
    this.op = null;
  }

  /**
   * This method is called from {@link #run()} if the widget has been disposed.
   */
  protected void handleDisposed() {

    this.disposed = true;
  }

  /**
   * This method is called from {@link #run()}. It does the actual job for the
   * given operation.
   * 
   * @param operation is the actual operation to perform.
   */
  protected void performSynchron(String operation) {

    if (operation == OPERATION_DISPOSE) {
      disposeSynchron();
    } else if (operation == OPERATION_IS_DISPOSED) {
      // this.disposed = isDisposedSynchron();
    } else if (operation == OPERATION_CREATE) {
      throw new UnsupportedOperationException("Duplicate operation: " + operation);
    } else {
      throw new UnsupportedOperationException("Unknown operation: " + operation);
    }
  }

  /**
   * This method is called for the create operation.
   */
  protected void doCreateSynchron() {

    createSynchron();
  }

  /**
   * This method is called for {@link #doCreateSynchron()}.
   */
  protected abstract void createSynchron();

  /**
   * This method is called for the isDisposed operation.
   * 
   * @return <code>true</code> if the widget is disposed false otherwise.
   */
  protected boolean isDisposedSynchron() {

    return this.disposed;
  }

  /**
   * This method is called for the dispose operation.
   */
  protected void disposeSynchron() {

    this.disposed = true;
  }

  /**
   * This method creates the actual widget. The {@link #getSwtObject() widget}
   * should not yet exist and the parent must be available.
   */
  protected void create() {

    assert (checkReady());
    assert (getSwtObject() == null);
    invoke(OPERATION_CREATE);
    assert (getSwtObject() != null);
  }

  /**
   * This method {@link org.eclipse.swt.widgets.Widget#dispose() disposes} the
   * widget.
   */
  public void dispose() {

    assert (checkReady());
    invoke(OPERATION_DISPOSE);
    this.disposed = true;
  }

  /**
   * This method determines if the widget is
   * {@link org.eclipse.swt.widgets.Widget#isDisposed() disposed}.
   * 
   * @return <code>true</code> if the widget is disposed.
   */
  public boolean isDisposed() {

    if (!this.disposed) {
      assert (checkReady());
      invoke(OPERATION_IS_DISPOSED);
    }
    return this.disposed;
  }

  /**
   * This method checks if previous operations are completed. It is used only
   * for assertions in development.
   * 
   * @return <code>true</code> if all previous operations are completed.
   */
  protected boolean checkReady() {

    String operation = this.op;
    if (operation == null) {
      return true;
    } else {
      System.out.println("Synchronization bug: the operation '" + operation
          + "' was NOT completed!");
      return false;
    }
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Widget#getStyle() style} of this widget.
   * 
   * @return the style of the widget.
   */
  public int getStyle() {

    return this.style;
  }

  /**
   * This method checks if the given <code>styleMask</code> is contained in
   * the {@link #getStyle() style} of the widget.<br>
   * E.g. use the following to check if the HORIZONTAL flag is set.
   * 
   * <pre>
   * {@link #hasStyle(int) hasStyle}({@link org.eclipse.swt.SWT#HORIZONTAL})
   * </pre>
   * 
   * @param styleMask is the style mask to check.
   * @return <code>true</code> if all bits in the given styleMask are set in
   *         the {@link #getStyle() style}, <code>false</code> otherwise.
   */
  public boolean hasStyle(int styleMask) {

    return ((this.style & styleMask) == styleMask);
  }

  /**
   * This method sets the flag(s) given in <code>styleMask</code> in the
   * {@link #getStyle() style} of the widget.
   * 
   * @param styleMask is the style mask to set.
   */
  protected void setFlag(int styleMask) {

    this.style = (this.style | styleMask);
  }

  /**
   * This method clears the flag(s) given by <code>styleMask</code> in the
   * {@link #getStyle() style} of the widget.
   * 
   * @param styleMask is the style mask to unset.
   */
  protected void unsetFlag(int styleMask) {

    this.style = (this.style & ~styleMask);
  }

  /**
   * This method inverts (XORs) the flag(s) given by <code>styleMask</code> in
   * the {@link #getStyle() style} of the widget.
   * 
   * @param styleMask is the style mask to invert.
   */
  protected void invertFlag(int styleMask) {

    this.style = (this.style ^ styleMask);
  }

  /**
   * This method invokes the given operation synchronous.
   * 
   * @param operation is the operation to perform.
   */
  protected void invoke(String operation) {

    assert (checkReady());
    this.op = operation;
    this.display.invokeSynchron(this);
  }

  /**
   * This method gets the display.
   * 
   * @return the display.
   */
  public UiDisplay getDisplay() {

    return this.display;
  }

}
