/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for the native language support (NLS).
 * <a name="documentation"/><h2>NLS API</h2>
 * Applications that should be used by people all over the world need
 * <em>native language support</em> (NLS). The developers task is the
 * <em>internationalization</em> (i18n) where the application has to be
 * written in a way that the code is (mostly) independent from locale-specific
 * informations. This is a challenging task that affects many aspects like
 * GUI-dialogs as well as all text-messages displayed to the end-user.
 * The NLS provided here only addresses the internationalization of text-messages
 * in a way that allows <em>localization</em> (l10n) to the users locale.<br>
 * <h3>The Problem</h3>
 * <p>
 * <img src="{@docRoot}/doc-files/no-msg-dialog.png"/>
 * </p>
 * <p>
 * Java already comes with great i18n support. But IMHO there are some tiny
 * peaces missing to complete the great puzzle of NLS:<br>
 * There is almost no support if an application needs NLS that is
 * handling multiple users with different locales concurrently (e.g. a
 * web-application).<br>
 * You will typically store your messages in a
 * {@link java.util.ResourceBundle}. Now if you store the
 * technical key of the bundle in a message or exception the receiver needs the
 * proper <code>{@link java.util.ResourceBundle}</code> to decode it or he ends
 * up with a cryptic message he can NOT understand (e.g. as illustrated by the
 * screenshot).<br>
 * On the other hand you need to know the locale of the receiver to do the l10n when creating the message or exception
 * with the proper text. This may lead to sick design such as static hacks. Also if you have to translate the text at
 * the creation of the message every receiver has to live with this language. Especially for logging this is a big problem.
 * An operator will be lost in space if he gets such logfiles:
 * <pre style='font-family: "Courier New", Courier, monospace'>
 * [2000-01-31 23:59:00,000][ERROR][n.s.m.u.n.a.MasterService] The given value (256) has to be in the range from 0 to 100.
 * [2000-01-31 23:59:01,000][WARN ][n.s.m.u.n.a.MasterService] Der Benutzername oder das Passwort sind ungültig.
 * [2000-01-31 23:59:02,000][ERROR][n.s.m.u.n.a.MasterService] 文件不存在。
 * [2000-01-31 23:59:03,000][FATAL][n.s.m.u.n.a.MasterService] ข้อผิดพลาดที่ไม่คาดคิดได้เกิดขึ้น
 * </pre>
 * </p>
 * <h3>The Solution</h3>
 * The solution is quite simple:<br>
 * We simply bundle the message in default language together with the separated
 * dynamic arguments in one container object that is called
 * <code>{@link net.sf.mmm.util.nls.api.NlsMessage}</code>.
 * For exceptions there is additional support via <code>{@link net.sf.mmm.util.nls.api.NlsException}</code> and
 * <code>{@link net.sf.mmm.util.nls.api.NlsRuntimeException}</code>.
 * Here is an example to clarify the idea of {@link net.sf.mmm.util.nls.api.NlsMessage}:
 * The i18n message is "Hi {name}! How are you?" and the dynamic argument is the users name e.g. "Lilli".
 * Now if we store these informations together we have all we need. To get the localized message we simply translate the
 * i18n message to the proper language and then fill in the arguments. If we can NOT translate we always have the
 * message in default language which is "Hi Lilli! How are you?".<br>
 * But how do we translate the i18n message without artificial intelligence?
 * The answer is quite easy:
 * <h4>Recommended Approach: {@link net.sf.mmm.util.nls.api.NlsBundle}</h4>
 * The recommended approach is to create an interface derived from {@link net.sf.mmm.util.nls.api.NlsBundle}.
 * For each message you define a method that takes the arguments to fill in and returns an
 * {@link net.sf.mmm.util.nls.api.NlsMessage}. Via annotations you provide the default message for each method.
 * <pre>
 * package foo.bar;
 *
 * public interface NlsBundleFooBarRoot extends {@link net.sf.mmm.util.nls.api.NlsBundle} {
 *
 *   &#64;{@link net.sf.mmm.util.nls.api.NlsBundleMessage}("Hi {name}! How are you?")
 *   {@link net.sf.mmm.util.nls.api.NlsMessage} messageSayHi(&#64;{@link javax.inject.Named}("name") String name);
 *
 *   &#64;{@link net.sf.mmm.util.nls.api.NlsBundleMessage}("Sorry. The login \"{login}\" is already in use. Please choose a different login.")
 *   {@link net.sf.mmm.util.nls.api.NlsMessage} errorLoginInUse(&#64;{@link javax.inject.Named}("login") String login);
 * }
 * </pre>
 * From your code you now can do this:
 *
 * <pre>
 * String userName = "Lilli";
 * {@link net.sf.mmm.util.nls.api.NlsMessage} msg = {@link net.sf.mmm.util.nls.api.NlsAccess#getBundleFactory()}.{@link
 * net.sf.mmm.util.nls.api.NlsBundleFactory#createBundle(Class)
 * createBundle}(NlsBundleFooBarRoot.class).messageSayHi(userName);
 * String textDefault = msg.{@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage() getLocalizedMessage}());
 * String textDe = msg.{@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(java.util.Locale)
 * getLocalizedMessage}({@link java.util.Locale}.GERMANY));
 * </pre>
 * For the error message create an exception like this:
 * <pre>
 * public class LoginAlreadyInUseException extends {@link net.sf.mmm.util.nls.api.NlsRuntimeException} {
 *   public LoginAlreadyInUseException(String usedLogin) {
 *
 *     super({@link net.sf.mmm.util.nls.api.NlsRuntimeException#createBundle(Class)
 *     createBundle}(NlsBundleFooBarRoot.class).errorLoginInUse(usedLogin));
 *   }
 * }
 * </pre>
 * For further details see {@link net.sf.mmm.util.nls.api.NlsBundle}.<br/>
 * <br/>
 * For localization you can create property files with the translations of your NLS-bundle.
 * E.g. <code>foo/bar/NlsBundleFooBar_de.properties</code> with this content:
 * <pre>
 * messageSayHi = Hallo {name}! Wie geht es Dir?
 * errorLoginInUse = Es tut uns leid. Das Login "{login}" ist bereits vergeben. Bitte wählen Sie ein anderes Login.
 * </pre>
 * Unlike the Java defaults, here resource bundles are read in UTF-8 encoding and allow to use named parameters as
 * alternative to indexes what makes it easier for localizers. There are even more advanced features such as recursive
 * translation of arguments and choice format type. See {@link net.sf.mmm.util.nls.api.NlsMessage} for further details.
 * Also our solution supports specific environments such as GWT (google web toolkit) what makes it very interoperable.
 *
 * In order to support you with creating and maintaining the localized
 * properties, this solution also comes with the
 * <code>net.sf.mmm.util.nls.base.ResourceBundleSynchronizer</code>.
 * <br/>
 * The advantage is that also the bundle name and key are available in the {@link net.sf.mmm.util.nls.api.NlsMessage}
 * and that this approach is GWT compatible when using <code>mmm-util-gwt</code>. However, there is still our legacy
 * approach.
 * <h4>Alternate Approach (legacy): {@link net.sf.mmm.util.nls.base.AbstractResourceBundle}</h4>
 * Simply create a subclass of {@link net.sf.mmm.util.nls.base.AbstractResourceBundle} that declares public string
 * constants:
 * <pre>
 * package foo.bar;
 *
 * public class FooBarResourceBundle extends {@link net.sf.mmm.util.nls.base.AbstractResourceBundle} {
 *   public static final String MSG_SAY_HI = "Hi {name}! How are you?";
 *   public static final String ERR_LOGIN_IN_USE = "Sorry. The login \"{login}\" is " +
 *     "already in use. Please choose a different login.";
 * }
 * </pre>
 * From your code you only need to create the
 * {@link net.sf.mmm.util.nls.api.NlsMessage NlsMessage}</code> using this
 * constants:
 *
 * <pre>
 * String userName = "Lilli";
 * {@link net.sf.mmm.util.nls.api.NlsMessage} msg = {@link net.sf.mmm.util.nls.api.NlsAccess#getFactory()
 * }.{@link net.sf.mmm.util.nls.api.NlsMessageFactory#create(String, String, Object)
 * create}(FooBarResourceBundle.MSG_SAY_HI, "name", userName);</code>
 * String textDefault = msg.{@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage() getLocalizedMessage}());
 * String textDe = msg.{@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(java.util.Locale)
 * getLocalizedMessage}({@link java.util.Locale}.GERMANY));
 * </pre>
 * For the error message create an exception like this:
 * <pre>
 * public class LoginAlreadyInUseException extends {@link net.sf.mmm.util.nls.api.NlsRuntimeException} {
 *   public LoginAlreadyInUseException(String usedLogin) {
 *     super(MyResourceBundle.ERR_LOGIN_IN_USE, toMap(KEY_NAME, usedLogin));
 *   }
 * }
 * </pre>
 * For the automatic reverse-lookup create the file
 * <code>META-INF/net.sf.mmm/nls-bundles</code> with the fully qualified name
 * of your bundle-class (foo.bar.FooBarResourceBundle) as content.
 * <br/>
 * For localization you can create property files as described above in the recommended approach.
 *
 * In order to support you with creating and maintaining the localized
 * properties, this solution also comes with the
 * <code>net.sf.mmm.util.nls.base.ResourceBundleSynchronizer</code>.
 * <h3>Conclusion</h3>
 * As we have seen the NLS provided here makes it very easy for developers to write and maintain internationalized code.
 * While messages are created throughout the code they only need to be localized for the end-user in the client and at
 * service-endpoints. Only at these places you need to figure out the users locale (see
 * {@link net.sf.mmm.util.session.base.UserSessionProvider}).
 * <ul>
 *   <li>The {@link net.sf.mmm.util.nls.api.NlsMessage} allows to store an internationalized message together with
 *   actual arguments to fill in.</li>
 *   <li>The arguments can be arbitrary objects including {@link net.sf.mmm.util.nls.api.NlsMessage}s.</li>
 *   <li>There are powerful ways to format these arguments including variable expressions for optional arguments or
 *   plural forms. See {@link net.sf.mmm.util.nls.api.NlsMessage} for advanced examples.</li>
 *   <li>In addition to numbered arguments that have been deprecated we also support named arguments. This makes
 *   maintenance of the messages a lot easier. Your localizers will love you for choosing this solution.</li>
 *   <li>Resource bundle properties are read in UTF-8 encoding making it easier for localizers as they do not have to
 *   escape characters to unicode number sequences.</li>
 *   <li>The localization (translation to native language) is easily performed by
 *   {@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(java.util.Locale)}.</li>
 *   <li>For exceptions there is additional support via {@link net.sf.mmm.util.nls.api.NlsThrowable}.</li>
 * </ul>
 */
package net.sf.mmm.util.nls.api;

