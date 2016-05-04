/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the marker interface for all "resource-bundles" for this native language support. Instead of a
 * {@link java.util.ResourceBundle} you create a custom interface extending this one in order to define the
 * {@link NlsMessage#getInternationalizedMessage() messages}. This is done by defining methods with {@link NlsMessage}
 * as return type and the {@link NlsMessage#getArgument(String) NLS arguments} as parameter. <br>
 * <b>NOTE:</b><br>
 * You should NOT create an implementation of that interface as this is created for you automatically via
 * {@link NlsBundleFactory#createBundle(Class)}. This is the recommended approach for defining and binding
 * {@link NlsMessage}s. In order to make your {@link NlsBundle} work even in limited environments such as GWT (google
 * web toolkit) clients, we had to define the following convention:<br>
 * <ul>
 * <li>For your bundle you define a {@link Package} and a base name (e.g. {@code my.package.NlsBundleExample}). This
 * namespace must not be occupied by an existing type.</li>
 * <li>The interface you create and extend from {@link NlsBundle} has to be named using the base name followed by the
 * suffix {@link #INTERFACE_NAME_SUFFIX Root} (e.g. {@code my.package.NlsBundleExampleRoot}). This interface defines the
 * {@link NlsMessage}es for the {@link java.util.Locale#ROOT root locale} what is the reason for the suffix.</li>
 * <li>Your localized {@code *.properties} files have to be named using the base name followed by the according locale
 * suffix as for a regular {@link java.util.ResourceBundle} (e.g. {@code my/package/NlsBundleExample_de.properties} or
 * {@code my/package/NlsBundleExample_zh_HK.properties}). To override the message texts for the root locale (e.g. as
 * customization of a {@link NlsBundle} provided by a third-party library) you could also create a properties file
 * without the locale suffix (e.g. {@code my/package/NlsBundleExample.properties})</li>
 * </ul>
 * Here is an example:
 *
 * <pre>
 * public interface NlsBundleMynameRoot extends {@link NlsBundle} {
 *   {@literal @}{@link NlsBundleMessage}("The value {value} has to be in the range from {min} to {max}!")
 *   {@link NlsMessage} errorValueOutOfRange({@literal @}{@link javax.inject.Named}("value") int value,
 *            {@literal @}{@link javax.inject.Named}("min") int min, {@literal @}{@link javax.inject.Named}("max") int max);
 * }
 * </pre>
 *
 * For localization you create {@code *.properties} files (see
 * {@code net.sf.mmm.util.nls.base.ResourceBundleSynchronizer}) in the same package for each supported
 * {@link java.util.Locale}. In the example above e.g. {@code NlsBundleMyname_de.properties} with this content:
 *
 * <pre>
 * errorValueOutOfRange = Der Wert {value} muss innerhalb des Wertebereichs von {min} bis {max} liegen!
 * </pre>
 *
 * For a real example see {@link net.sf.mmm.util.NlsBundleUtilCoreRoot}. <br>
 * <b>IMPORTANT:</b><br>
 * Unless annotated with {@link NlsBundleKey} the {@link java.util.ResourceBundle#getString(String) key} is derived from
 * the name of the method. You have to ensure that the keys are unique as otherwise the localization is NOT possible.
 * Please strictly avoid having methods with the same name. <br>
 * <br>
 * For maintenance and better support of localizers it is strongly recommended to use
 * {@link NlsMessage#getArgument(String) named arguments} instead of {@link NlsMessage#getArgument(int) indexed
 * arguments}. Unfortunately Java does not include names of parameters in abstract methods (including methods of
 * interfaces). Java8 introduced this feature that we might support in the future. Currently the only way to work around
 * this is to add an annotation to each parameter. The {@link NlsBundleFactory} therefore supports the
 * {@link javax.inject.Named} annotation for this purpose. As it is nasty to add these annotations manually here is a
 * script for automation. This is just an example - please add more types as needed.
 *
 * <pre>
 * sed -i -r 's/([(]|, )((Object|Type|int|Number|String|boolean)([.]{3})?) ([a-z][a-zA-Z0-9]*)/\1@Named("\5") \2 \5/g' NlsBundleExampleRoot.java
 * </pre>
 *
 * <b>ATTENTION:</b><br>
 * It is strongly recommended NOT to use inheritance for {@link NlsBundle} interfaces. Instead create an interface that
 * directly extends {@link NlsBundle} (and nothing else). However, for purpose of abstraction feel free to add an empty
 * custom extension of {@link NlsBundle} as intermediate interface for your project to decouple dependencies.
 *
 * @see NlsBundleMessage
 * @see NlsBundleKey
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public abstract interface NlsBundle {

  /** The suffix for the interfaces derived from {@link NlsBundle}. This suffix will not change in the future. */
  String INTERFACE_NAME_SUFFIX = "Root";

}
