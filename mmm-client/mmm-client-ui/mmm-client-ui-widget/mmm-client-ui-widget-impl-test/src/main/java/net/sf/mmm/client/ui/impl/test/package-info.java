/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the test implementation of <code>client-ui</code> for widgets.
 * <a name="documentation"></a><h2>Client UI Impl Test</h2>
 * This package and its sub-packages contain the implementation of {@link net.sf.mmm.client.ui.api Client UI API} for
 * tests without a native toolkit. <br>
 * It allows to run (integrative) tests of the UI widgets and dialogs/screens for continuous integration and quick
 * feedback if a change breaks existing features. The test implementation fully supports all programmatic changes
 * including the caused effects such as events fired and related dynamic effects. However, such tests can NOT reveal any
 * problems specific to layout (broken CSS rendering, etc.) or toolkit specific problems (custom JavaScript failure or
 * browser quirks). For such purpose you may want to use manual integration testing or use
 * <a href="http://www.seleniumhq.org">selenium</a> for automation of real integration tests.
 */
package net.sf.mmm.client.ui.impl.test;

