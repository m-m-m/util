/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for transaction support.
 * <a name="documentation"></a><h2>Transaction API</h2> 
 * This package contains the API for transaction support. For scenarios where
 * AOP is not an option (for what ever reason) <code>mmm-transaction</code>
 * supports simple and effective transaction management and prevents you from
 * doing typical mistakes when dealing with transactions. <br>
 * The main component offered by this API is 
 * {@link net.sf.mmm.transaction.api.TransactionExecutor} that allows to perform
 * arbitrary code within a transaction. Use this at the entry points to your
 * application (web-services, servlets, etc.). For advanced use-cases like 
 * batches that have to process millions of entities you can use the
 * {@link net.sf.mmm.transaction.api.TransactionCallable} callback-interface
 * to delegate to your custom code to execute transactional. Then you will 
 * receive a {@link net.sf.mmm.transaction.api.TransactionAdapter} that allows
 * to do {@link net.sf.mmm.transaction.api.TransactionAdapter#interCommit() 
 * intermediate commits} without bundling your business logic with the technical
 * details of transaction handling. Additionally, 
 * {@link net.sf.mmm.transaction.api.TransactionExecutor} allows to register
 * an {@link net.sf.mmm.transaction.api.TransactionEventListener} to add custom
 * logic to the transaction management. <br>
 * This API abstracts from any underlying transaction management framework (JTA,
 * spring-transaction) and allows to choose it by adding the right 
 * implementation of this API to your classpath.
 */
package net.sf.mmm.transaction.api;

