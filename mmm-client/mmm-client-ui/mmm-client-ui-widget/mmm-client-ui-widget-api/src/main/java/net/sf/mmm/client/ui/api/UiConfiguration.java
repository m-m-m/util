/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;

import net.sf.mmm.util.nls.api.NlsMessageLookup;

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

  /**
   * This method gets the path for the current theme. This is attached to all relative image URLs. You can use this to
   * switch to a different theme in order to get a different appearance of the UI. Typically you decide for a theme at
   * build-time. However, this allows to switch themes at runtime.
   * 
   * @return the URL path for the current theme.
   */
  String getTheme();

  /**
   * This method gets the instance of {@link NlsMessageLookup} used to
   * {@link NlsMessageLookup#getMessage(String, java.util.Map) lookup}
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField#getLabel() labels} automatically by
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle() property titles} via
   * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding}. So you can highly customize your labels for your custom
   * defined properties and have full I18N/l10n support with little effort. For rapid development you can also ignore
   * all this and just live with the {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle() property title} that
   * is used as fallback if lookup fails.<br/>
   * For full support you create an interface e.g. NlsBundleMyLabelsRoot that extends from
   * {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup}. For further details look at
   * {@link net.sf.mmm.util.nls.api.NlsBundle}. For each {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle()
   * property title} such as <code>lastName</code> you define an according method in your bundle
   * (NlsBundleMyLabelsRoot):<br/>
   * 
   * <pre>
   * {@literal @}{@link net.sf.mmm.util.nls.api.NlsBundleLocation}(bundleName = "NlsBundleMyLabels")
   * public interface NlsBundleMyLabelsRoot extends {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup} {
   *   {@literal @}{@link net.sf.mmm.util.nls.api.NlsBundleMessage}("Last name")
   *   {@link net.sf.mmm.util.nls.api.NlsMessage} lastName();
   *   ...
   * }
   * </pre>
   * 
   * E.g. for German localization you define <code>NlsBundleMyLabels_de.properties</code> in the same package with this
   * content:
   * 
   * <pre>
   * lastName = Nachname
   * ...
   * </pre>
   * 
   * Now we assume you have a business object like this:
   * 
   * <pre>
   * public interface Partner {
   * 
   *   {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <String>} PROPERTY_LAST_NAME = new {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <>}("lastName", String.class);
   * 
   * }
   * </pre>
   * 
   * you can use
   * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding#createAndBind(net.sf.mmm.util.pojo.path.api.TypedProperty)
   * createAndBind}(Partner.PROPERTY_LAST_NAME) to create an input field widget and the label will be set automatically
   * to your localized label text ("Last Name", "Vorname", or whatever).<br/>
   * Now if you refactor your code so the property <code>lastName</code> gets renamed to <code>surename</code> and you
   * want to keep your NLS/I18N properties stable, you can change your declaration to
   * 
   * <pre>
   * public interface Partner {
   * 
   *   {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <String>} PROPERTY_LAST_NAME = new {@link net.sf.mmm.util.pojo.path.api.TypedProperty
   * }{@literal <>}("surename", String.class, "lastName");
   * 
   * }
   * </pre>
   * 
   * And maybe also rename it to <code>PROPERTY_SURENAME</code>.<br/>
   * You could also use "Partner_surename" as {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle() property
   * title} if you want to have a different label for surename in a different business object e.g. <code>Address</code>.
   * You only need to ensure that the {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getTitle() property title}
   * matches the method name and therefore key in your {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup}. You can also
   * use "Partner.surename" with the following trick:
   * 
   * <pre>
   *   {@literal @}{@link net.sf.mmm.util.nls.api.NlsBundleKey}("Partner.surename")
   *   {@literal @}{@link net.sf.mmm.util.nls.api.NlsBundleMessage}("Last name")
   *   {@link net.sf.mmm.util.nls.api.NlsMessage} PartnerSurename();
   * </pre>
   * 
   * <br/>
   * <b>NOTE:</b><br/>
   * It is recommended to specify the {@link net.sf.mmm.util.pojo.path.api.TypedProperty#getPropertyType() property
   * type} in your {@link net.sf.mmm.util.pojo.path.api.TypedProperty} constant. However, this is technically not
   * required and you may also omit it. This might cause a little performance overhead as the property type has to be
   * reflected then. Also you need to be careful not to pick the wrong constructor for this purpose such as
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#TypedProperty(String, String)}.<br/>
   * 
   * @see net.sf.mmm.client.ui.base.binding.UiDataBinding
   * 
   * @return the {@link NlsMessageLookup} instance for automatic label creation. You should typically use
   *         {@link net.sf.mmm.util.nls.base.NlsMessageLookupProxy} in your custom implementation of this method.
   */
  NlsMessageLookup getLabelLookup();

  // focus on set edit mode: first editable field, off, ...
  // focus on validation failure: first invalid field, off, ...
  // validation failure handling: popup, message panel, popup & message panel, ...
  // tab control: tab passive fields, only active fields (accessibility setting?)

}
