/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the MVP base classes of the Client Dialog API using GWT.
 * <a name="documentation"/><h2>Client Dialog Base MVP (GWT)</h2> 
 * This package and its sub-package contains the base classes that extend GWTP to build a web client framework.<br/>
 * <b>INSTRUCTIONS:</b><br/>
 * For each module or component of your application you define a brief unique technical name <code><em>M</em></code> 
 * and follow these steps:
 * <ol>
 * <li>You create a class <code><em>M</em>GinModule</code> extending 
 * {@link com.gwtplatform.mvp.client.gin.AbstractPresenterModule}. 
 * <li>You create an interface <code><em>M</em>Ginjector</code> extending {@link com.google.gwt.inject.client.Ginjector}
 * (or other of your derived {@link com.google.gwt.inject.client.Ginjector} interfaces from dependent modules).
 * Next you annotate the interface with &#64;{@link com.google.gwt.inject.client.GinModules}<code>(<em>M</em>GinModule.class)</code>
 * (and potentially others such as <code>DispatchAsyncModule.class</code>)</li>
 * </ol>
 * Now for each {@link net.sf.mmm.client.api.dialog.Dialog} of your module, you define a brief unique technical name 
 * <code><em>X</em></code> and an according package path <code><em>x</em></code>. Next, you follow these steps to 
 * implement your own {@link net.sf.mmm.client.api.dialog.Dialog}:
 * <ul>
 * <li>Create an interface extending {@link net.sf.mmm.client.base.gwt.dialog.mvp.common.UiHandlersAbstractPresenter} named
 * <code>basepackage.<em>x</em>.common.UiHandlers<em>X</em></code>.</li>
 * <li>Create an interface extending {@link net.sf.mmm.client.base.gwt.dialog.mvp.common.AbstractView} named 
 * <code>basepackage.<em>x</em>.common.<em>X</em>View</code>.</li>
 * <li>Create a class extending {@link net.sf.mmm.client.base.gwt.dialog.mvp.view.AbstractViewImpl} and implementing 
 * <code><em>X</em>View</code> named <code>basepackage.<em>x</em>.view.<em>X</em>ViewImpl</code>.</li>
 * <li>Create a subclass of {@link net.sf.mmm.client.base.gwt.dialog.mvp.presenter.AbstractPresenter} named 
 * <code>basepackage.<em>x</em>.presenter.<em>X</em>Presenter</code>. This class has to have an inner interface named
 * <code>MyProxy</code> that extends {@link com.gwtplatform.mvp.client.proxy.ProxyPlace}&lt;<em>X</em>Presenter&gt; and is annotated with
 * {@link com.gwtplatform.mvp.client.annotations.NameToken} specifying a constant from your central class all
 * your {@link com.google.gwt.place.shared.Place}s and either
 * {@link com.gwtplatform.mvp.client.annotations.ProxyStandard} (or
 * {@link com.gwtplatform.mvp.client.annotations.ProxyCodeSplit} for asynchronous loading of the javascript logic).</li>
 * <li>In your class <code><em>M</em>GinModule</code> (see above) 
 * you add to the method 
 * {@link com.gwtplatform.mvp.client.gin.AbstractPresenterModule#configure(com.google.gwt.inject.client.binder.GinBinder)}
 * this magic line: 
 * <code>bindPresenter(XPresenter.class, XView.class, XViewImpl.class, XPresenter.MyProxy.class);</code></li>
 * <li>>In your interface <code><em>M</em>Ginjector</code> (see above) you add the following method:
 * <code>{@link com.google.inject.Provider}&lt;<em>X</em>Presenter&gt; get<em>X</em>Presenter()</code>.
 * In case you used {@link com.gwtplatform.mvp.client.annotations.ProxyCodeSplit}, you have to use {@link com.google.gwt.inject.client.AsyncProvider}
 * instead of {@link com.google.inject.Provider}.</li>
 * </ul>
 */
package net.sf.mmm.client.base.gwt.dialog.mvp;

