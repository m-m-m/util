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
 * public class MyCompositeWidget extends {@link net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel}<MyBean> {
 *
 *   public MyCompositeWidget({@link net.sf.mmm.client.ui.api.UiContext} context) {
 *     super(context, MyBean.class);
 *     {@link UiWidgetWithValue}&lt;FooType&gT; fooField = getDataBinding().{@link #createAndBind(TypedProperty) createAndBind}(MyBean.PROPERTY_FOO);
 *     {@link UiWidgetWithValue}&lt;BarType&gT; barField = getDataBinding().{@link #createAndBind(TypedProperty) createAndBind}(MyBean.PROPERTY_BAR);
 *     getDelegate().addChildren(fooField, barField);
 *     ...
 *   }
 * }
 * </pre>
 * 
 * This will result in a form that shows input fields for the properties "foo" and "bar" with validators
 * according to potential annotations on these properties in <code>MyBean</code>. Setting the value (to some
 * <code>MyBean</code>) will automatically set the values of the properties "foo" and "bar" to the according
 * fields. Getting the value will automatically set the properties for the values entered in the according
 * fields.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDataBinding<VALUE> extends UiFeatureValueAndValidation<VALUE>, AttributeWriteModified {

  /**
   * This method binds the given widget to the given property. This will have to following effects:
   * <ul>
   * <li>{@link #getValueDirect(Object, net.sf.mmm.util.validation.api.ValidationState)} and therefore all
   * other <code>getValue*</code> methods will automatically
   * {@link #getValueDirect(Object, net.sf.mmm.util.validation.api.ValidationState) read the value} of the
   * given widget and set it to the given property of the &lt;VALUE&gt; to return.</li>
   * <li>{@link #setValue(Object, boolean)} and therefore all other <code>setValue*</code> methods will
   * automatically get the property-value of the given property from the &lt;VALUE&gt;-object and
   * {@link #setValue(Object, boolean) set that property-value} to the given widget.</li>
   * </ul>
   * 
   * @see #createAndBind(TypedProperty)
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property
   *        of &lt;VALUE&gt;.
   * @param widget is the {@link UiWidgetWithValue value based widget} to bind to the given property.
   */
  <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> widget);

  /**
   * This method creates a {@link UiWidgetWithValue value based widget} that is to be added as
   * {@link net.sf.mmm.client.ui.api.widget.UiWidgetComposite#getChild(int) child} of the
   * {@link UiWidgetWithValue widget} owning this {@link UiDataBinding data-binding}. This will have to
   * following effects:
   * <ul>
   * <li>A new widget is created according to the {@link TypedProperty#getPropertyType() property type}.</li>
   * <li>The label is automatically derived from the property including localization. Therefore you need to
   * define resource bundles (net/sf/mmm/client/ui/NlsBundleProperties.properties) mapping the
   * {@link TypedProperty#getTitle() title} of the property to the localized display label.</li>
   * <li>A {@link net.sf.mmm.util.validation.api.ValueValidator} is
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getValidator() automatically created
   * for the property} and will be
   * {@link UiWidgetWithValue#addValidator(net.sf.mmm.util.validation.api.ValueValidator) added} to the new
   * widget.</li>
   * <li>The new widget is automatically {@link #bind(TypedProperty, UiWidgetWithValue) bound} to the given
   * <code>property</code>.</li>
   * </ul>
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property
   *        of &lt;VALUE&gt; and contain its {@link TypedProperty#getPropertyType() property type}.
   * @return a new widget to view and edit the given property. It will already been
   *         {@link #bind(TypedProperty, UiWidgetWithValue) bound}.
   */
  <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property);

  /**
   * This method is like {@link #createAndBind(TypedProperty)} but using the given <code>label</code> instead
   * of deriving it automatically.
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() type of the property value}.
   * @param property is the {@link TypedProperty} representing the property to bind. It has to be a property
   *        of &lt;VALUE&gt; and contain its {@link TypedProperty#getPropertyType() property type}.
   * @param label is the label to use for the widget. See
   *        {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField#getFieldLabel()}.
   * @return a new widget to view and edit the given property. It will already been
   *         {@link #bind(TypedProperty, UiWidgetWithValue) bound}.
   */
  <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property, String label);

  /**
   * @return {@link Boolean#TRUE} if validation was successful, {@link Boolean#FALSE} if validation failed,
   *         <code>null</code> if NOT validated (since {@link #clearMessages()} has been called).
   */
  Boolean getValid();

  /**
   * This method sets the value of {@link #getValid()}.<br/>
   * <b>ATTENTION:</b><br/>
   * This is an internal method and should NOT be used by external users. Usage has to be compliant with
   * {@link #getValid()}.
   * 
   * @param valid is the new value for {@link #getValid()}. May be <code>null</code>.
   */
  void setValid(Boolean valid);

  /**
   * This method performs the actual validation using the
   * {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) registered} validators. It is called
   * from {@link net.sf.mmm.client.ui.base.widget.AbstractUiWidget#doValidate(ValidationState, Object)} that
   * itself is called from {@link #validate(ValidationState)}.
   * 
   * @param state is the {@link ValidationState}. Must NOT be <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   * @return <code>true</code> if the validation was successful, <code>false</code> otherwise (if there are
   *         validation failures).
   */
  boolean doValidate(ValidationState state, VALUE value);

}
