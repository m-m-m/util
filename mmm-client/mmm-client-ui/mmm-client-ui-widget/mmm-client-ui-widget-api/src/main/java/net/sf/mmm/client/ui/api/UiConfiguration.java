/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;

import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;

/**
 * This is the interface for the configuration of the UI (user interface).<br/>
 * <b>ATTENTION:</b><br/>
 * This is an {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extendable interface}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiConfiguration {

  /** The default value for {@link #getTheme() theme}. */
  String DEFAULT_THEME = "standard";

  /** The default for {@link #getLabelResourceBundleName()}. */
  String DEFAULT_LABEL_RESOURCE_BUNDLE_NAME = "net.sf.mmm.client.ui.PropertyLabels";

  /**
   * This method gets the path for the current theme. This is attached to all relative image URLs. You can use
   * this to switch to a different theme in order to get a different appearance of the UI. Typically you
   * decide for a theme at build-time. However, this allows to switch themes at runtime.
   * 
   * @return the URL path for the current theme.
   */
  String getTheme();

  /**
   * This method builds the label from the given parameters. It may append a static suffix like colon (":").
   * Further it may append a static suffix like asterisk ("*") if the given {@link UiWidgetWithValue widget}
   * is {@link UiWidgetWithValue#isMandatory() mandatory}. Further its decision may change if the
   * {@link UiWidgetWithValue widget} is in {@link net.sf.mmm.client.ui.api.common.UiMode#isEditable()
   * editable} {@link UiWidgetWithValue#getMode() mode}.
   * 
   * @param label is the original label of the widget as
   *        {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel#setLabel(String) set} by the user.
   * @param widget is the widget owning the label, typically a
   *        {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField field}.
   * @return the final label to display in the UI.
   */
  String buildLabel(String label, UiWidgetWithValue<?> widget);

  /**
   * This method gets the {@link java.util.ResourceBundle#getBundle(String) base name} of the default
   * {@link java.util.ResourceBundle} holding the localized labels for the
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle() property titles}.<br/>
   * For the default value you need to define <code>PropertyLabels.properties</code> and according localized
   * variants (e.g. <code>PropertyLabels_de.properties</code> for German labels) located in the classpath of
   * your application at <code>net/sf/mmm/client/ui/</code>. If you have a business object defining e.g.
   * 
   * <pre>public static final {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <FooType>} PROPERTY_FOO = new {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <>}("foo", FooType.class);</pre>
   * 
   * you can map the label by adding <code>foo=Label of Foo</code> to the properties file.<br/>
   * Now if you refactor your code so the property <code>foo</code> gets renamed to <code>bar</code> and you
   * want to keep your NLS/I18N properties stable, you can change your declaration to
   * 
   * <pre>public static final {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <FooType>} PROPERTY_FOO = new {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <>}("foo", FooType.class, "bar");</pre>
   * 
   * And maybe also rename it to <code>PROPERTY_BAR</code> instead.<br/>
   * <b>NOTE:</b><br/>
   * You may also omit the {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getPropertyType() property type}
   * (<code>FooType.class</code>) but be careful not to pick the wrong constructor for this purpose such as
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#TypedProperty(String, String)}.<br/>
   * <b>Customization:</b><br/>
   * Change the {@value #DEFAULT_LABEL_RESOURCE_BUNDLE_NAME default} of this method to define your own package
   * and name. You may also
   * {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom#getLabelResourceBundleName() override} the
   * global default in your {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom custom widgets}.<br/>
   * <b>ATTENTION:</b><br/>
   * For GWT environments you need to use {@link net.sf.mmm.util.nls.api.NlsResourceBundleRequestor} for lazy
   * loading (code-splitting) and for bundles required at startup you have to add a script line to your
   * <code>*.gwt.xml</code> file such as:
   * 
   * <pre>
   * &lt;script src="js/mmm-nls-bundle.js?name=net.sf.mmm.client.ui.PropertyLabels"/>
   * </pre>
   * 
   * @see net.sf.mmm.client.ui.base.binding.UiDataBinding#createAndBind(net.sf.mmm.util.pojo.path.api.TypedProperty)
   * @see net.sf.mmm.util.nls.api.NlsMessageFactory#createDirect(String, String, java.util.Map)
   * 
   * @return the {@link java.util.ResourceBundle#getBundle(String) base name} of the default
   *         {@link java.util.ResourceBundle} for property labels.
   */
  String getLabelResourceBundleName();

  // focus on set edit mode: first editable field, off, ...
  // focus on validation failure: first invalid field, off, ...
  // validation failure handling: popup, message panel, popup & message panel, ...
  // tab control: tab passive fields, only active fields (accessibility setting?)

}
