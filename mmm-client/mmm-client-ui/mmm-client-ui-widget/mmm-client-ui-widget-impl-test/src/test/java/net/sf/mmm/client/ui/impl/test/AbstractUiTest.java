/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import java.util.Arrays;
import java.util.Collection;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This is the abstract base class for Tests of the {@link UiWidget}-framework based on the test implementation. It
 * provides the {@link #getContext() context} to start your tests with.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class AbstractUiTest extends Assert {

  /** @see #parameters() */
  public static final String SPRING_CONFIG = "/net/sf/mmm/client/ui/impl/test/beans-client-ui-widget-test.xml";

  /** @see #getContext() */
  private final String springConfig;

  /**
   * The constructor.
   * 
   * @param springConfig is the Spring XML config location or <code>null</code> to test without spring.
   */
  public AbstractUiTest(String springConfig) {

    super();
    this.springConfig = springConfig;
  }

  /**
   * @return the different {@link Parameters} to run this test with.
   */
  @Parameters
  public static Collection<Object[]> parameters() {

    return Arrays.asList(new Object[] { null }, new Object[] { SPRING_CONFIG });
  }

  /**
   * @return the {@link UiContext}.
   */
  protected UiContext getContext() {

    if (this.springConfig == null) {
      UiContextTest context = new UiContextTest();
      UiWidgetFactoryDatatypeTest impl = new UiWidgetFactoryDatatypeTest();
      impl.setContext(context);
      impl.initialize();
      context.setWidgetFactoryDatatype(impl);
      context.setDatatypeDetector(new DatatypeDetectorTest());
      context.initialize();
      return context;
    } else {
      return SpringContainerPool.getInstance(this.springConfig).get(UiContext.class);
    }
  }

  /**
   * This method enforces that the {@link UiWidgetAdapter} for the given <code>widget</code> is created.
   * 
   * @param widget is the {@link UiWidget}.
   */
  protected void forceWidgetAdapter(UiWidget widget) {

    AbstractUiWidget<?> w = (AbstractUiWidget<?>) widget;
    AbstractUiWidget.getWidgetAdapter(w);
  }

  /**
   * @param widget is the {@link AbstractUiWidget}.
   * @return the result of {@link AbstractUiWidget#hasWidgetAdapter()} (while a recursive verification for consistency
   *         is performed).
   */
  protected boolean hasWidgetAdapter(UiWidget widget) {

    AbstractUiWidget<?> w = (AbstractUiWidget<?>) widget;
    boolean hasWidgetAdapter = w.hasWidgetAdapter();
    verifyHasWidgetAdapterRecursive(w, hasWidgetAdapter);
    return hasWidgetAdapter;
  }

  /**
   * @param widget is the {@link AbstractUiWidget}.
   * @param hasWidgetAdapter - the status of {@link AbstractUiWidget#hasWidgetAdapter()} to check recursively for
   *        consistency.
   */
  protected void verifyHasWidgetAdapterRecursive(AbstractUiWidget<?> widget, boolean hasWidgetAdapter) {

    if (widget.hasWidgetAdapter() != hasWidgetAdapter) {
      if ((!hasWidgetAdapter) && (widget instanceof UiWidgetField)) {
        // ignore this as fields may require to create their adapter for building the fieldLabelWidget.
      } else {
        throw new ObjectMismatchException(Boolean.valueOf(widget.hasWidgetAdapter()),
            Boolean.valueOf(hasWidgetAdapter), widget);
      }
    }
    if (widget instanceof UiWidgetComposite) {
      UiWidgetComposite<?> composite = (UiWidgetComposite<?>) widget;
      for (int i = 0; i < composite.getChildCount(); i++) {
        UiWidget child = composite.getChild(i);
        verifyHasWidgetAdapterRecursive((AbstractUiWidget<?>) child, hasWidgetAdapter);
      }
    }
  }

  /**
   * @param widget is the widget where to clear the validation failure count recursively.
   */
  protected void clearValidationFailureSetCount(UiWidget widget) {

    UiWidgetAdapterTest adapter = (UiWidgetAdapterTest) AbstractUiWidget.getWidgetAdapter(widget);
    adapter.clearValidationFailureSetCount();
    if (widget instanceof UiWidgetComposite) {
      UiWidgetComposite<?> composite = (UiWidgetComposite<?>) widget;
      for (int i = 0; i < composite.getChildCount(); i++) {
        UiWidget child = composite.getChild(i);
        clearValidationFailureSetCount(child);
      }
    }
  }

  /**
   * @param widget is the widget to verify.
   * @param expectedValidationCount is the expected {@link UiWidgetAdapterTest#getValidationFailureSetCount()} value.
   */
  protected void assertValidationFailureSetCount(UiWidget widget, int expectedValidationCount) {

    if (widget instanceof AttributeWriteValidationFailure) {
      UiWidgetAdapterTest adapter = (UiWidgetAdapterTest) AbstractUiWidget.getWidgetAdapter(widget);
      assertEquals(widget.toString(), 1, adapter.getValidationFailureSetCount());
    }
    if (widget instanceof UiWidgetComposite) {
      UiWidgetComposite<?> composite = (UiWidgetComposite<?>) widget;
      for (int i = 0; i < composite.getChildCount(); i++) {
        UiWidget child = composite.getChild(i);
        assertValidationFailureSetCount(child, expectedValidationCount);
      }
    }
  }

  /**
   * This class is a {@link UiHandlerEventValueChange} implementation that counts the change-events.
   * 
   * @param <VALUE> is the generic type of the changed
   *        {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value}.
   */
  public static class ValueChangeHandler<VALUE> extends UiHandlerEventValueChange<VALUE> {

    /** The widget that sends the events to test. */
    private final UiWidgetWithValue<VALUE> widget;

    /** @see #getEventCount() */
    private int eventCount;

    /**
     * The constructor.
     * 
     * @param widget is the {@link UiWidgetWithValue} where to register this handler.
     */
    public ValueChangeHandler(UiWidgetWithValue<VALUE> widget) {

      super();
      this.widget = widget;
      this.widget.addChangeHandler(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValueChange(UiEventValueChange<VALUE> event) {

      assertSame("Event source does NOT match", this.widget, event.getSource());
      assertEquals("Events in test can only be programmatic!", Boolean.TRUE,
          Boolean.valueOf(event.isProgrammatic()));
      this.eventCount++;
    }

    /**
     * @return the eventCount
     */
    public int getEventCount() {

      return this.eventCount;
    }

    /**
     * Asserts that {@link #getEventCount() exactly one event} occurred.
     */
    public void assertOneEvent() {

      assertEquals("Expected exactly one value-change event!", 1, this.eventCount);
    }

    /**
     * Resets the internal state such as {@link #getEventCount()}.
     */
    public void clear() {

      this.eventCount = 0;
    }

  }
}
