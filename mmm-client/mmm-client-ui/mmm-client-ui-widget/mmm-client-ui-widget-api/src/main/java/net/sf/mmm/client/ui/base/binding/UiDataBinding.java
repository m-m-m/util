/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteModified;
import net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the interface for the data-binding for {@link #getValue() values} with
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget widgets}.<br/>
 * <b>Important:</b><br/>
 * Regular users should only use the data-binding for
 * {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite} and derived classes where it allows
 * comfortable {@link #createAndBind(TypedProperty) property-bindings}.<br/>
 * Have a look at the following example:
 * 
 * <pre>
 * public class MyBeanFrom extends {@link net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel}<MyBean> {
 * 
 *   public MyBeanForm({@link net.sf.mmm.client.ui.api.UiContext} context) {
 *     super(context, MyBean.class);
 *     {@link UiWidgetWithValue}&lt;Integer&gt; fooField = getDataBinding().{@link #createAndBind(TypedProperty) createAndBind}(MyBean.PROPERTY_FOO);
 *     getDelegate().addChildren(fooField);
 *     {@link UiWidgetWithValue}&lt;BarType&gt; barField = getDataBinding().{@link #createAndBind(TypedProperty) createAndBind}(MyBean.PROPERTY_BAR);
 *     getDelegate().addChildren(barField);
 *     ...
 *   }
 * }
 * </pre>
 * 
 * With <code>MyBean</code> like this:
 * 
 * <pre>
 * public class MyBean implements {@link net.sf.mmm.util.pojo.api.Pojo} {
 * 
 *   public static final {@link TypedProperty}&lt;Integer&gt; PROPERTY_FOO = new {@link TypedProperty}&lt;&gt;("foo");
 *   public static final {@link TypedProperty}&lt;BarType&gt; PROPERTY_BAR = new {@link TypedProperty}&lt;&gt;("bar");
 * 
 *   {@literal @}{@link javax.validation.constraints.Min}(-5)
 *   {@literal @}{@link javax.validation.constraints.Max}(5)
 *   private int foo;
 * 
 *   {@literal @}{@link net.sf.mmm.util.validation.base.Mandatory}
 *   private BarType bar;
 * 
 *   public int getFoo() {
 *     return this.foo;
 *   }
 * 
 *   public void setFoo(int foo) {
 *     this.foo = foo;
 *   }
 * 
 *   public BarType getBar() {
 *     return this.bar;
 *   }
 * 
 *   public void setBar(BarType bar) {
 *     this.bar = bar;
 *   }
 * 
 *   ...
 * 
 * }
 * </pre>
 * 
 * This will automatically do all the nasty work for you as described in {@link #createAndBind(TypedProperty)}.
 * Otherwise you would have to do something like this in your widget:
 * 
 * <pre>
 * public class MyBeanFrom extends {@link net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel}<MyBean> {
 * 
 *   private final {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField} fooField;
 * 
 *   private final MyBarCustomWidget barField;
 * 
 *   public MyBeanForm({@link net.sf.mmm.client.ui.api.UiContext} context) {
 *     super(context, MyBean.class);
 *     // create field for property "foo" by hand...
 *     this.fooField = getContext().getWidgetFactory().create({@link net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField}.class);
 *     Integer min = Integer.valueOf(-5);
 *     this.fooField.setMinimumValue(min);
 *     Integer max = Integer.valueOf(5);
 *     this.fooField.setMaximumValue(max);
 *     NlsBundleMyLabelsRoot labels = {@link net.sf.mmm.util.nls.api.NlsAccess#getBundleFactory()}.createBundle(NlsBundleMyLabelsRoot.class);
 *     this.fooField.setLabel(labels.foo());
 *     this.fooField.addValidator(new {@link net.sf.mmm.util.validation.base.ValidatorRange}&lt;Integer&gt;(min, max));
 *     this.fooField.addValidator({@link net.sf.mmm.util.validation.base.ValidatorMandatory#getInstance()});
 *     getDelegate().addChildren(this.fooField);
 *     // create field for property "bar" by hand...
 *     this.barField = new MyBarCustomWidget(context);
 *     this.barField.setLabel(labels.bar());
 *     this.barField.addValidator({@link net.sf.mmm.util.validation.base.ValidatorMandatory#getInstance()});
 *     getDelegate().addChildren(this.barField);
 *     ...
 *   }
 * 
 *   @Override
 *   protected MyBean doGetValue(MyBean template, ValidationState state) {
 *     MyBean result = super.doGetValue(template, state);
 *     Integer foo = this.fooField.getValueDirect(null, state);
 *     if (foo == null) {
 *       // TODO reimplement mandatory validatin violation manually here
 *     } else {
 *       result.setFoo(foo.intValue());
 *     }
 *     BarType bar = this.barField.getValueDirect(result.getBar(), state);
 *     result.setBar(bar);
 *     ...
 *     return result;
 *   }
 * 
 *   @Override
 *   protected void doSetValue(MyBean template, boolean forUser) {
 *     super.doSetValue(template, forUser);
 *     if (template == null) {
 *       this.fooField.setValue(Integer.valueOf(0));
 *       this.barField.setValue(null);
 *       ...
 *     } else {
 *       this.fooField.setValue(Integer.valueOf(template.getFoo());
 *       this.barField.setValue(template.getBar());
 *       ...
 *     }
 *   }
 * 
 * }
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDataBinding<VALUE> extends UiFeatureValueAndValidation<VALUE>, AttributeWriteModified {

  /**
   * This method binds the given widget to the given property. This will have to following effects:
   * <ul>
   * <li>{@link #getValueDirect(Object, net.sf.mmm.util.validation.api.ValidationState)} and therefore all other
   * <code>getValue*</code> methods will automatically
   * {@link #getValueDirect(Object, net.sf.mmm.util.validation.api.ValidationState) read the value} of the given widget
   * and set it to the given property of the &lt;VALUE&gt; to return.</li>
   * <li>{@link #setValue(Object, boolean)} and therefore all other <code>setValue*</code> methods will automatically
   * get the property-value of the given property from the &lt;VALUE&gt;-object and {@link #setValue(Object, boolean)
   * set that property-value} to the given widget.</li>
   * </ul>
   * 
   * @see #createAndBind(TypedProperty)
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property of
   *        &lt;VALUE&gt;.
   * @param widget is the {@link UiWidgetWithValue value based widget} to bind to the given property.
   */
  <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> widget);

  /**
   * This method creates a {@link UiWidgetWithValue value based widget} that is to be added as
   * {@link net.sf.mmm.client.ui.api.widget.UiWidgetComposite#getChild(int) child} of the {@link UiWidgetWithValue
   * widget} owning this {@link UiDataBinding data-binding}. This will have to following effects:
   * <ul>
   * <li>A new widget is created according to the {@link TypedProperty#getPropertyType() property type} via
   * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype} and can be highly customized.</li>
   * <li>The label is automatically derived from the property including localization. Therefore you need to define a
   * {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup} (see
   * {@link net.sf.mmm.client.ui.api.UiConfiguration#getLabelLookup()}) mapping the {@link TypedProperty#getTitle()
   * title} with the properties to the localized display labels.</li>
   * <li>A {@link net.sf.mmm.util.validation.api.ValueValidator} is automatically created for the property based on JSR
   * 303 and will be {@link UiWidgetWithValue#addValidator(net.sf.mmm.util.validation.api.ValueValidator) added} to the
   * new widget.</li>
   * <li>Mandatory fields are marked as such (appearance can be customized in CSS).</li>
   * <li>If the input field is a {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetRangeField} the minimum and
   * maximum value is set in case JSR 303 constraints such as {@link javax.validation.constraints.Min},
   * {@link javax.validation.constraints.DecimalMin}, {@link javax.validation.constraints.Max}, and
   * {@link javax.validation.constraints.DecimalMax} are present. In the example the property foo will be rendered as an
   * integer spin box for the value range from <code>-5</code> to <code>+5</code>.</li>
   * <li>Also, primitive types are also automatically considered as mandatory as they can not be <code>null</code>.</li>
   * <li>The new widget is automatically {@link #bind(TypedProperty, UiWidgetWithValue) bound} to the given
   * <code>property</code>.</li>
   * </ul>
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property of
   *        &lt;VALUE&gt; and contain its {@link TypedProperty#getPropertyType() property type}.
   * @return a new widget to view and edit the given property. It will already been
   *         {@link #bind(TypedProperty, UiWidgetWithValue) bound}.
   */
  <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property);

  /**
   * This method is like {@link #createAndBind(TypedProperty)} but using the given <code>label</code> instead of
   * deriving it automatically.
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property of
   *        &lt;VALUE&gt; and contain its {@link TypedProperty#getPropertyType() property type}.
   * @param label is the label to use for the widget. See
   *        {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField#getFieldLabel()}.
   * @return a new widget to view and edit the given property. It will already been
   *         {@link #bind(TypedProperty, UiWidgetWithValue) bound}.
   */
  <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property, String label);

  /**
   * @return {@link Boolean#TRUE} if validation was successful, {@link Boolean#FALSE} if validation failed,
   *         <code>null</code> if NOT validated (since {@link net.sf.mmm.client.ui.api.widget.UiWidget#clearMessages()}
   *         or something similar has been called).
   */
  Boolean getValidity();

  /**
   * This method sets the value of {@link #getValidity()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This is an internal method and should NOT be used by external users. Usage has to be compliant with
   * {@link #getValidity()}.
   * 
   * @param validity is the new value for {@link #getValidity()}. May be <code>null</code>.
   */
  void setValidity(Boolean validity);

  /**
   * This method performs the actual validation using the
   * {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) registered} validators. It is called from
   * {@link net.sf.mmm.client.ui.base.widget.AbstractUiWidget} (method <code>doValidate</code>) that itself is called
   * from {@link #validate(ValidationState)}.
   * 
   * @param state is the {@link ValidationState}. Must NOT be <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   * @return <code>true</code> if the validation was successful, <code>false</code> otherwise (if there are validation
   *         failures).
   */
  boolean doValidate(ValidationState state, VALUE value);

  /**
   * @return the last value that has been set via {@link #setValue(Object, boolean)}. This is the
   *         {@link #getOriginalValue() original value} or the {@link #setValueForUser(Object) value that has been set
   *         for the user} after the {@link #getOriginalValue() original value} was set for the last time.
   */
  VALUE getRecentValue();

}
