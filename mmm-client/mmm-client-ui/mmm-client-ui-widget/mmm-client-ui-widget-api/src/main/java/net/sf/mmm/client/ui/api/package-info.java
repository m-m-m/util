/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API to build user-interfaces for an application client.
 * <a name="documentation"/><h2>Client UI API</h2>
 * This package and its sub-packages contain the API to build user-interfaces for an application client.
 * It abstracts from underlying native UI toolkits and allows to build a portable cross-technology
 * client application.<br/>
 * The major part of this API is provided by {@link net.sf.mmm.client.ui.api.widget}.<br/>
 * <h3>Why?</h3> There are various features and advantages of this layer:
 * <ul>
 * <li><b>Designed for testability</b><br/>
 * This framework is an abstraction layer to actual UI frameworks. Your client code will be written against
 * this API. The common part of the implementation is java bean like and works even without an underlying
 * native widget. By providing a test implementation of the API, we offer the ability for you to test your
 * dialogs and client logic easily with unit-tests.</li>
 * <li><b>Cross-plattform and mutli-channel</b><br/>
 * Same client-code can run with all supported toolkits.
 * <ul>
 * <li>Web (via GWT) supporting HTML, Touch and Mobile</li>
 * <li>Native client (Swing and SWT)</li>
 * <li>Native android app</li>
 * <li>Apps for all mobile devices via web-client using <a
 * href="http://incubator.apache.org/cordova/">cordova</a></li>
 * </ul>
 * </li>
 * You do not believe us that this is possible with good results? Our framework introduces a new philosophy
 * and programming model for higher level UI design. So far most UIs are build by actually hard-coding the
 * decisions of what is on top or beside each other and what shows up sequentially in wizards or the like.
 * This is leading on the wrong track. Instead, we define high-level UI patterns such as a list of business
 * objects in combination with a details section to view and edit these objects. This way the framework can
 * decide according to the device, orientation and screen-resolution if the details-section is below/beside
 * the list or if the UI switches between these two views showing only one at a time.
 * <li><b>Faster development</b><br/>
 * Many client frameworks deal with different artifacts and programming models. The worst case is the classic
 * web development with HTML, CSS, JavaScript, various JavaScript frameworks and a totally different
 * tool-stack on the server like Java, or the like. Also JSP, JSF, and many other approaches exist in this
 * area. However, these technology gaps are causing really big pain because you have to learn all these
 * languages, most of these dialects have really bad tool support and the connections between these worlds are
 * very fragile and easily break on refactoring. Even worse it is hard to trace and debug through all these
 * connections (e.g. how to figure out where is the server-side implementation for a service-call triggered in
 * the client code). There are approaches like <a href="http://nodejs.org/">node.js</a> to make JavaScript the
 * universal programming language. But JavaScript has a rather strange design and is extremely error prone. As
 * an untyped language it can never get excellent tool support including refactoring, code-completion and
 * reference resolution. Approaches to establish better client programming technologies into browsers via
 * plugins have all failed so far so also doing Java in the browser is not an option. Still we believe that
 * Java is the proper language for developing and maintaining large-scale enterprise applications. Not,
 * because the Java language has the best concepts, but because it is good, easy to learn, extremely
 * established, open, has a great eco-system and awesome tool-support. Besides native client technologies like
 * Swing or SWT, there is also <a href="https://developers.google.com/web-toolkit/">GWT (Google Web
 * Toolkit)</a> that allows to compile Java code to JavaScript. This way we can provide a web-variant of this
 * framework. GWT itself is quite low-level so this framework speeds you up with all the infrastructure you
 * need. Besides GWT being a great technology it has a major drawback: Development cycles are quite slow for
 * large and complex clients as starting and refreshing takes very long. This layer allows to test parts of
 * your dialogs e.g. in Swing in order to improve the layout and find initial bugs. Later you can start
 * testing with GWT in order to find web related problems. This approach can save a lot of time.</li>
 * <li><b>Good Performance</b><br/>
 * You think that an abstraction layer for UI toolkits that wraps all widgets is causing a large performance
 * overhead? Nope! Of course you can create widgets a lot faster if you directly write JavaScript but the
 * overall performance of an application is NOT dominated by the speed of creating widgets. Instead you should
 * consider:
 * <ol>
 * <li>This framework has a smart concept of late binding. This way widgets are created as late as possible.
 * E. g. hidden parts of a dialog are not created unless they get visible. For complex dialogs this can even
 * boost your performance.</li>
 * <li>The user interacts directly with the native widgets and NOT with the wrapper of this framework.</li>
 * <li>Within the last years clients (especially browsers and mobile devices) have become extraordinary fast.</li>
 * <li>The main performance bottleneck is typically on the server-side. The architecture of this framework
 * produces a rich client that does all the rendering and keeps the state while the server can be entirely
 * stateless and only provides business-oriented services (SOA) used by the client (and maybe other systems).</li>
 * </ol>
 * To be honest a web client build with this framework based on GWT is still heavy load for a mobile device.
 * But on powerful smartphones the performance is fine.
 * <li><b>Clean API</b><br/>
 * Simple, clear, well documented, and easy to use API. Native UI toolkits typically use java classes as API.
 * Due to lack of multi-inheritance and due to implementation specific decisions the API often sucks. There
 * are many inherited API methods in particular widgets that do NOT make sense there and will confuse the
 * user. This API is designed using interfaces. Every individual attribute or feature is defined by a central
 * interface. This approach allows that each method is defined only in one place and documentation gets
 * consistent and maintainable. Finally, we have analyzed various native UI toolkits with their API and
 * internals. From this perspective and with the experience of building various large enterprise application
 * clients this API has been designed. Please have a look.</li>
 * <li><b>Consistent</b><br/>
 * Hide strange and touchy behaviors of existing toolkits.</li>
 * <li><b>Extensibility</b><br/>
 * Even though we define widgets by interfaces and have support for multiple native UI toolkits, you can
 * extend this code. Either with UI toolkit independent widgets by composing existing ones (see
 * <code>UiWidgetCustom</code> in <code>uit-widget-base</code> and
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class)}) or by replacing or adding widgets for native toolkit
 * support. We hope that the latter case will never be necessary or that you join this project in this case.</li>
 * <li><b>Higher Level</b><br/>
 * While the adoption of native UI toolkits takes place on a quite low level, there are many high level
 * features like
 * <ul>
 * <li>{@link net.sf.mmm.client.ui.api.feature.UiFeatureValidation validation}</li>
 * <li>{@link net.sf.mmm.client.ui.api.attribute.AttributeReadModified#isModified() dirty handling}</li>
 * <li>
 * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.client.ui.api.common.UiMode)
 * switching} between {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view} and
 * {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit} {@link net.sf.mmm.client.ui.api.common.UiMode
 * mode}</li>
 * <li>Higher level widgets such as {@link net.sf.mmm.client.ui.api.widget.grid grid widgets},
 * {@link net.sf.mmm.client.ui.api.widget.panel panels}, {@link net.sf.mmm.client.ui.api.widget.window windows}, etc.</li>
 * <li>High level widgets to support {@link net.sf.mmm.client.ui.api.widget.pattern UI patterns}</li>
 * </ul>
 * But you are still free to pick and choose: If you only want to use the lower level abstraction you can still ignore
 * the higher level stuff.</li>
 * <li><b>Dialogs not pages</b><br/>
 * Most client frameworks have a programming model that is page-oriented (e.g. you produce a web-page). However, for
 * a rich internet application you need to think in dialogs and not in pages. So if you open a particular (main-)dialog
 * the result is a page that contains that dialog typically together with other things like header, footer, navigation,
 * etc. In this API all of these parts are dialogs that are composed and embedded to build a page. However, each of these
 * dialogs have a state and lifecycle rather than the page itself.</li>
 * <li><b>Usability</b><br/>
 * By providing established UI patterns this framework supports you in creating a UI with high usability.
 * <li><b>Accessibility</b><br/>
 * {@link net.sf.mmm.client.ui.api.common.Accessibility} is an important but also complex topic. This layer
 * support standards such as {@link net.sf.mmm.client.ui.api.common.Accessibility#WAI_ARIA WAI-ARIA} as well
 * as keyboard control and other features to increase accessibility.</li>
 * <li><b>Internationalization</b><br/>
 * Of course everything is integrated with our great native language support provided by
 * {@link net.sf.mmm.util.nls.api.NlsMessage}. This offers a single, flexible and powerful mechanism for i18n and l10n
 * abstracting from the very different concepts of the underlying native toolkits that are supported. Further we expose
 * features like bidirectional-text (BiDi) provided by the underlying technology.</li>
 * </ul>
 * <h3>Limitations</h3>
 * Please also note the following limitations before you make your choice:
 * <ul>
 * <li><b>No gimmicks</b><br/>
 * This approach is focusing on functionality and is limited to a common sense of features that are offered by
 * all supported native UI toolkits. You will have a less flexibility if you are using this layer. If you want
 * to offer a very fancy or extraordinary UI you should consider using a single UI technology directly and
 * stick to it. On the other hand you should also consider this as a feature as it helps you to build client
 * applications using common UI patterns that have good usability and accessibility.</li>
 * <li><b>No magic</b><br/>
 * You should NOT expect that switching from a web-application to Swing or SWT/EclipseRCP can be done by a
 * single line of code and the UI behaves and looks the same. However, this abstraction layer allows to switch
 * from one toolkit to another or to support multiple native toolkits with a single codebase with reasonable
 * effort.</li>
 * <li><b>Not small</b><br/>
 * This framework is designed for medium to large sized client applications. If you want a small client with
 * few screens, the overhead of setting up and learning this technology might be too high. However, we have a
 * strong focus on making your live easy. So also many things are much faster to implement.</li>
 * </ul>
 */
package net.sf.mmm.client.ui.api;

