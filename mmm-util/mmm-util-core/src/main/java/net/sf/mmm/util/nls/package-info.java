/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides advanced native language support (NLS).
 * <h2>Native Language Support</h2>
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
 * There is almost no support if an application needs NLS support that is
 * handling multiple users with different locales concurrently (e.g. a
 * web-application).<br>
 * You will typically store your messages in a
 * {@link java.util.ResourceBundle}. Now if you store the
 * technical key of the bundle in a message or exception the receiver needs the
 * proper <code>{@link java.util.ResourceBundle}</code> to decode it or he ends 
 * up with a cryptical message he can NOT understand (e.g. as illustrated by the
 * screenshot).<br>
 * On the other hand you need to know the locale of the receiver to do the l10n
 * when creating the message or exception with the proper text. This is
 * impossible to solve in may situations or leading to sick design.
 * </p>
 * <h3>The Solution</h3>
 * The solution is quite simple because again Java brings parts of the solution
 * with <code>{@link java.text.MessageFormat}</code>:<br>
 * We simply bundle the message in default language together with the separated
 * locale independent arguments in one container object that is called
 * <code>{@link net.sf.mmm.util.nls.api.NlsMessage}</code>. 
 * Here is an example to clarify the idea: The i18n
 * message is "Hi {0}! How are you?" and the language independent argument is
 * the users name e.g. "Lilli". Now if we store these informations together we
 * have all we need. To get the localized message we simply translate the i18n
 * message to the proper language and then fill in the arguments. If we can NOT
 * translate we always have the message in default language which is "Hi Lilli!
 * How are you?".<br>
 * But how do we translate the i18n message without artificial intelligence? The
 * answer is very easy: We simply use the bundle in default language and do a
 * reverse lookup to get the technical key. Then we can lookup the proper
 * message in the according bundle to translate it to the according locale.<br>
 * All you have to do is creating a subclass of
 * <code>{@link net.sf.mmm.util.nls.AbstractResourceBundle}</code> that declares 
 * public string constants.
 * 
 * <pre>
 * public class MyResourceBundle extends {@link net.sf.mmm.util.nls.AbstractResourceBundle} {
 *   public static final String MSG_SAY_HI = "Hi {0}! How are you?";
 *   public static final String ERR_LOGIN_IN_USE = "Sorry. The login \"{0}\" is " +
 *     "already in use. Please choose a different login.";
 * }
 * </pre>
 * 
 * From your code you only need to create the
 * {@link net.sf.mmm.util.nls.api.NlsMessage NlsMessage}</code> using this
 * constants:
 * 
 * <p>
 * <code>&nbsp;&nbsp;String usersName = "Lilli";</code><br>
 * <code>&nbsp;&nbsp;{@link net.sf.mmm.util.nls.api.NlsMessage} msg = 
 * {@link net.sf.mmm.util.nls.NlsAccess#getFactory()}.<!--
 * -->{@link net.sf.mmm.util.nls.api.NlsMessageFactory#create(String, Object...) create}<!--
 * -->(MyResourceBundle.MSG_SAY_HI, usersName);</code>
 * </p>
 * 
 * For exceptions there is additional support via <code>{@link net.sf.mmm.util.nls.NlsException}</code>
 * and <code>{@link net.sf.mmm.util.nls.NlsRuntimeException}</code>. Simply
 * derive your declared exceptions from one of these classes:
 * 
 * <pre>
 * public class IllegalLoginException extends {@link net.sf.mmm.util.nls.NlsException} {
 *   public IllegalLoginException(String usedLogin) {
 *     super(MyResourceBundle.ERR_LOGIN_IN_USE, usedLogin);
 *   }
 * }
 * </pre>
 * 
 * Now if you throw a <code>IllegalLoginException</code>, it will behave as a
 * "regular" exception with an English message. Additionally it has methods
 * <code>getLocalizedMessage</code> and <code>printStackTrace</code> that
 * take a {@link java.util.Locale} and a 
 * <code>{@link net.sf.mmm.util.nls.api.NlsTemplateResolver}</code> 
 * as argument. You can use {@link net.sf.mmm.util.nls.impl.NlsTemplateResolverImpl}
 * as implementation. All you need to do is register all of your 
 * {@link net.sf.mmm.util.nls.AbstractResourceBundle}s (such as 
 * <code>MyResourceBundle</code>) and provide them at construction. 
 * 
 * <h3>Conclusion</h3>
 * As we have seen the NLS provided here makes it very easy for developers to
 * write and maintain internationalized code. While messages are spread 
 * throughout the code there are only very view places where these messages are 
 * actually displayed to the end-user. At these places you need to figure out
 * the users locale and provide a proper 
 * <code>{@link net.sf.mmm.util.nls.api.NlsTemplateResolver}</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
package net.sf.mmm.util.nls;

