/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.RemoteInvocationCall;
import net.sf.mmm.service.api.RemoteInvocationCallFailedException;
import net.sf.mmm.service.base.GenericRemoteInvocationRequest;
import net.sf.mmm.service.base.GenericRemoteInvocationResponse;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.date.api.TimeMeasure;
import net.sf.mmm.util.exception.api.ExceptionUtil;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsThrowable;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the abstract base class for the common implementation of
 * {@link net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService} and
 * {@link net.sf.mmm.service.base.command.GenericRemoteInvocationCommandService}.<br/>
 * As extension you could e.g. add
 * {@link #doSecurityCheck(net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService.RequestContext)
 * custom security checks} or override the {@link #handleCallFailure(RemoteInvocationCall, Throwable) error
 * handling}.
 *
 * @param <CALL> is the generic type of the {@link RemoteInvocationCall}.
 * @param <REQUEST> is the generic type of the {@link GenericRemoteInvocationRequest}.
 * @param <RESPONSE> is the generic type of the {@link GenericRemoteInvocationResponse}.
 * @param <HANDLER> is the generic type of the {@link GenericRemoteInvocationCallHandler}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractGenericRemoteInvocationService<CALL extends RemoteInvocationCall, //
REQUEST extends GenericRemoteInvocationRequest<CALL, ? extends GenericRemoteInvocationTransactionalCalls<CALL>>, //
RESPONSE extends GenericRemoteInvocationResponse, HANDLER extends GenericRemoteInvocationCallHandler<CALL>> extends
    AbstractLoggableComponent {

  /** @see #processCall(RemoteInvocationCall, RequestContext) */
  private final Map<String, HANDLER> callHandlerMap;

  /** @see #getXsrfTokenManager() */
  private CsrfTokenManager xsrfTokenManager;

  /** @see #getSecurityDetector() */
  private RemoteInvocationSecurityDetector securityDetector;

  /** @see #getExceptionUtil() */
  private ExceptionUtil exceptionUtil;

  /** @see #getMaximumTransactionsPerRequest() */
  private int maximumTransactionsPerRequest;

  /** @see #getMaximumCallsPerTransaction() */
  private int maximumCallsPerTransaction;

  /** @see #getMaximumCallsPerRequest() */
  private int maximumCallsPerRequest;

  /** @see #getValidator() */
  private Validator validator;

  /**
   * The constructor.
   */
  public AbstractGenericRemoteInvocationService() {

    super();
    this.callHandlerMap = new HashMap<>();
    this.maximumTransactionsPerRequest = 3;
    this.maximumCallsPerTransaction = 10;
    this.maximumCallsPerRequest = 16;
  }

  /**
   * @return the {@link RemoteInvocationSecurityDetector} to use.
   */
  public RemoteInvocationSecurityDetector getSecurityDetector() {

    return this.securityDetector;
  }

  /**
   * @param securityDetector is the {@link RemoteInvocationSecurityDetector} to {@link Inject}.
   */
  @Inject
  public void setSecurityDetector(RemoteInvocationSecurityDetector securityDetector) {

    this.securityDetector = securityDetector;
  }

  /**
   * @return the xsrfTokenManager
   */
  public CsrfTokenManager getXsrfTokenManager() {

    return this.xsrfTokenManager;
  }

  /**
   * @param xsrfTokenManager is the {@link CsrfTokenManager} to {@link Inject}.
   */
  @Inject
  public void setXsrfTokenManager(CsrfTokenManager xsrfTokenManager) {

    getInitializationState().requireNotInitilized();
    this.xsrfTokenManager = xsrfTokenManager;
  }

  /**
   * @return the {@link ExceptionUtil} to use.
   */
  public ExceptionUtil getExceptionUtil() {

    return this.exceptionUtil;
  }

  /**
   * @param exceptionUtil is the {@link ExceptionUtil} to {@link Inject}.
   */
  @Inject
  public void setExceptionUtil(ExceptionUtil exceptionUtil) {

    getInitializationState().requireNotInitilized();
    this.exceptionUtil = exceptionUtil;
  }

  /**
   * @return the {@link Validator} instance to use.
   */
  public Validator getValidator() {

    return this.validator;
  }

  /**
   * @param validator is the {@link Validator} to {@link Inject}.
   */
  @Inject
  public void setValidator(Validator validator) {

    getInitializationState().requireNotInitilized();
    this.validator = validator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.validator == null) {
      this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
  }

  /**
   * @return the maximum number of {@link GenericRemoteInvocationTransactionalCalls transactions} allowed per
   *         {@link GenericRemoteInvocationRequest request} to prevent Denial of Service (DoS) attacks.
   */
  public int getMaximumTransactionsPerRequest() {

    return this.maximumTransactionsPerRequest;
  }

  /**
   * @param maximumTransactionsPerRequest is new value of {@link #getMaximumTransactionsPerRequest()}.
   */
  public void setMaximumTransactionsPerRequest(int maximumTransactionsPerRequest) {

    getInitializationState().requireNotInitilized();
    this.maximumTransactionsPerRequest = maximumTransactionsPerRequest;
  }

  /**
   * @return the maximum number of {@link RemoteInvocationCall}s allowed per
   *         {@link GenericRemoteInvocationTransactionalCalls transactions} to prevent Denial of Service (DoS)
   *         attacks.
   */
  public int getMaximumCallsPerTransaction() {

    return this.maximumCallsPerTransaction;
  }

  /**
   * @param maximumCallsPerTransaction is new value of {@link #getMaximumCallsPerTransaction()}.
   */
  public void setMaximumCallsPerTransaction(int maximumCallsPerTransaction) {

    getInitializationState().requireNotInitilized();
    this.maximumCallsPerTransaction = maximumCallsPerTransaction;
  }

  /**
   * @return the maximum number of {@link RemoteInvocationCall}s allowed per
   *         {@link GenericRemoteInvocationRequest request} to prevent Denial of Service (DoS) attacks.
   */
  public int getMaximumCallsPerRequest() {

    return this.maximumCallsPerRequest;
  }

  /**
   * @param maximumCallsPerRequest is new value of {@link #getMaximumCallsPerRequest()}.
   */
  public void setMaximumCallsPerRequest(int maximumCallsPerRequest) {

    getInitializationState().requireNotInitilized();
    this.maximumCallsPerRequest = maximumCallsPerRequest;
  }

  /**
   * This method performs a security check for the given request.
   *
   * @param context is the {@link RequestContext} to check.
   */
  protected void doSecurityCheck(RequestContext context) {

    REQUEST request = context.getRequest();
    CsrfToken xsrfToken = request.getXsrfToken();
    if (xsrfToken != null) {
      getXsrfTokenManager().validateToken(xsrfToken);
    }
    GenericRemoteInvocationTransactionalCalls<CALL>[] transactionalCalls = request.getTransactionalCalls();
    checkDosLimit(transactionalCalls.length, this.maximumTransactionsPerRequest);
    int totalCallCount = 0;
    int txCallIndex = 0;
    for (GenericRemoteInvocationTransactionalCalls<CALL> txCalls : transactionalCalls) {
      CALL[] calls = txCalls.getCalls();
      checkDosLimit(calls.length, this.maximumCallsPerTransaction);
      totalCallCount = totalCallCount + calls.length;
      for (CALL call : calls) {
        HANDLER handler = getHandler(call);
        if (handler.isSecured() && !handler.isLogin()) {
          // we prevent calls to secured methods without an XSRF token, login has to be invoked before
          if (xsrfToken == null) {
            throw new SecurityException("Invalid token!");
          }
        }
        try {
          handler.validate(call);
        } catch (Exception e) {
          context.transactionalResults[txCallIndex] = handleTxCallsFailure(txCalls, e);
          break;
        }
      }
      txCallIndex++;
    }
    checkDosLimit(totalCallCount, this.maximumCallsPerTransaction);
  }

  /**
   * Verifies that the given <code>actualCount</code> is less or equal to the given
   * <code>maximumAllowedCount</code>. Otherwise an exception is thrown.
   *
   * @param actualCount the actual count.
   * @param maximumAllowedCount the maximum allowed count.
   */
  private void checkDosLimit(int actualCount, int maximumAllowedCount) {

    if (actualCount > maximumAllowedCount) {
      throw new SecurityException("Denial of Service!");
    }
  }

  /**
   * Processes the given {@link GenericRemoteInvocationRequest} and creates the corresponding
   * {@link GenericRemoteInvocationResponse}.
   *
   * @param request is the {@link GenericRemoteInvocationRequest} to process.
   * @return the {@link GenericRemoteInvocationResponse} to send back to the client.
   */
  protected RESPONSE processRequest(REQUEST request) {

    NlsNullPointerException.checkNotNull(GenericRemoteInvocationRequest.class, request);
    TimeMeasure measure = new TimeMeasure();
    getLogger().debug("start processing request {}.", request);
    try {
      GenericRemoteInvocationTransactionalCalls<CALL>[] transactionalCalls = request.getTransactionalCalls();
      GenericRemoteInvocationTransactionalResults[] transactionalResults = new GenericRemoteInvocationTransactionalResults[transactionalCalls.length];

      RequestContext context = new RequestContext(request, transactionalResults);

      doSecurityCheck(context);
      int txCallIndex = 0;
      for (GenericRemoteInvocationTransactionalCalls<CALL> txCall : transactionalCalls) {
        // if not null then pre-validation has already failed...
        if (transactionalResults[txCallIndex] == null) {
          GenericRemoteInvocationTransactionalResults txResult;
          try {
            txResult = processCallsInTransaction(txCall, context);
          } catch (Throwable e) {
            txResult = handleTxCallsFailure(txCall, e);
          }
          transactionalResults[txCallIndex] = txResult;
        }
        txCallIndex++;
      }

      if (context.requestToken != null) {
        CsrfToken updateToken = this.xsrfTokenManager.generateUpdateToken(context.requestToken);
        if (updateToken != context.requestToken) {
          context.responseToken = updateToken;
        }
      }
      RESPONSE response = createResponse(request.getRequestId(), context.responseToken, transactionalResults);
      getLogger().debug("end processing request {}.", request);
      measure.succeed();
      return response;
    } catch (Throwable t) {
      getLogger().error("Request failed", t);
      throw t;
    } finally {
      measure.log(getLogger(), "processing request " + request.getRequestId());
    }
  }

  /**
   * Creates a new instance of {@literal <RESPONSE>}.
   *
   * @param requestId is the {@link GenericRemoteInvocationResponse#getRequestId() requestId}.
   * @param xsrfToken is the {@link GenericRemoteInvocationResponse#getXsrfToken() xsrfToken}.
   * @param transactionalResults are the {@link GenericRemoteInvocationResponse#getTransactionalResults()
   *        transactional results}.
   * @return the new {@link GenericRemoteInvocationRequest}.
   */
  protected abstract RESPONSE createResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults[] transactionalResults);

  /**
   * This method calls
   * {@link #processTransactionalCalls(GenericRemoteInvocationTransactionalCalls, RequestContext)} in a new
   * transaction.<br/>
   * <b>ATTENTION:</b><br/>
   * If you want to implement this using spring-aop and <code>@Transactional</code> you need to be aware that
   * you have to keep the annotated method out of this class as spring-aop uses dynamic proxies by default
   * that only support transactions when a method is called from outside the class and not for method calls
   * within a class. This is a typical pitfall and one of the reasons why we are using
   * <code>mmm-transaction</code> by default.
   *
   * @param transactionalCalls is the {@link GenericRemoteInvocationTransactionalCalls}.
   * @param context is the {@link RequestContext} to pass.
   * @return the {@link GenericRemoteInvocationTransactionalResults}.
   * @throws Exception if anything goes wrong.
   */
  protected abstract GenericRemoteInvocationTransactionalResults processCallsInTransaction(
      GenericRemoteInvocationTransactionalCalls<CALL> transactionalCalls, RequestContext context) throws Exception;

  /**
   * This method processes the given {@link GenericRemoteInvocationTransactionalCalls}.
   *
   * @param transactionalCalls is the {@link GenericRemoteInvocationTransactionalCalls}.
   * @param context is the {@link RequestContext} to pass.
   * @return the {@link GenericRemoteInvocationTransactionalResults}.
   */
  protected GenericRemoteInvocationTransactionalResults processTransactionalCalls(
      GenericRemoteInvocationTransactionalCalls<CALL> transactionalCalls, RequestContext context) {

    NlsNullPointerException.checkNotNull(GenericRemoteInvocationTransactionalCalls.class, transactionalCalls);
    CALL[] calls = transactionalCalls.getCalls();
    Serializable[] results = new Serializable[calls.length];

    int i = 0;
    for (CALL call : calls) {
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("start processing call {}.", call);
      }
      // method found, invoke it and add result to response...
      try {
        Serializable result = processCall(call, context);
        results[i++] = result;
      } catch (Throwable e) {
        RemoteInvocationCallFailedException callFailedException = handleCallFailure(call, e);
        throw callFailedException;
      }
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("end processing call {}.", call);
      }
    }
    return new GenericRemoteInvocationTransactionalResults(results);
  }

  /**
   * Processes the given {@link RemoteInvocationCall}. This method does not need to handle any
   * {@link Exception}s as this is already done by the caller of this method.
   *
   * @param call is the {@link RemoteInvocationCall} to invoke.
   * @param context is the {@link RequestContext}.
   * @return the actual result of the call. May be <code>null</code>.
   * @throws Throwable in case of any kind of error.
   */
  protected Serializable processCall(CALL call, RequestContext context) throws Throwable {

    TimeMeasure measure = new TimeMeasure();
    try {
      HANDLER handler = getHandler(call);
      Object result = handler.invoke(call);
      if (handler.isLogin()) {
        context.callLoginCount++;
        handleLoginOperation(context);
      }
      measure.succeed();
      if ((result != null) && !(result instanceof Serializable)) {
        throw new ObjectMismatchException(result.getClass(), Serializable.class, handler.getId(), "return-type");
      }
      return (Serializable) result;
    } finally {
      measure.log(getLogger(), "processing call " + call);
    }
  }

  /**
   * Post-processing for the
   * {@link RemoteInvocationSecurityDetector#isLogin(java.lang.reflect.AnnotatedElement) login operation}.
   * Will {@link CsrfTokenManager#generateInitialToken() generate the initial} {@link CsrfToken}.
   *
   * @param context is the {@link RequestContext}.
   */
  protected void handleLoginOperation(RequestContext context) {

    if (context.requestToken == null) {
      if (context.responseToken == null) {
        context.responseToken = this.xsrfTokenManager.generateInitialToken();
        assert (context.responseToken != null);
      } else {
        // for flexibility we do not throw an exception
        getLogger().warn("Multiple login operations in single request!");
      }
    }
  }

  /**
   * @param call is the {@link RemoteInvocationCall}.
   * @return the corresponding {@link GenericRemoteInvocationCallHandler}.
   * @throws ObjectNotFoundException if no handler exists for the given <code>call</code>.
   */
  protected HANDLER getHandler(CALL call) throws ObjectNotFoundException {

    String id = getHandlerId(call);
    HANDLER handler = this.callHandlerMap.get(id);
    if (handler == null) {
      throw new ObjectNotFoundException(GenericRemoteInvocationCallHandler.class, id);
    }
    return handler;
  }

  /**
   * Registers the given <code>handler</code>.
   *
   * @param handler is the {@link GenericRemoteInvocationCallHandler} to register.
   */
  protected void registerHandler(HANDLER handler) {

    HANDLER old = this.callHandlerMap.put(handler.getId(), handler);
    if (old != null) {
      getLogger().warn("Replaced handler " + old + " with " + handler);
    }
  }

  /**
   * @return the number of {@link #registerHandler(GenericRemoteInvocationCallHandler) registered}
   *         {@link GenericRemoteInvocationCallHandler}s.
   */
  protected int getHandlerCount() {

    return this.callHandlerMap.size();
  }

  /**
   * @param call is the {@link RemoteInvocationCall}.
   * @return the corresponding {@link GenericRemoteInvocationCallHandler#getId() ID}.
   */
  protected abstract String getHandlerId(CALL call);

  /**
   * This method contains the failure handling if a {@link Throwable} occurred while processing a
   * {@link RemoteInvocationCall}.
   *
   * @param call is the {@link RemoteInvocationCall}.
   * @param error is the {@link Throwable} that occurred.
   * @return the {@link RemoteInvocationCallFailedException} to throw.
   */
  protected RemoteInvocationCallFailedException handleCallFailure(CALL call, Throwable error) {

    return new RemoteInvocationCallFailedException(error, call);
  }

  /**
   * This method contains the failure handling if a {@link Throwable} occurred while processing
   * {@link GenericRemoteInvocationTransactionalCalls}.
   *
   * @param txCall is the {@link GenericRemoteInvocationTransactionalCalls}.
   * @param error is the {@link Throwable} that occurred.
   * @return the {@link RemoteInvocationCallFailedException} to throw.
   */
  protected GenericRemoteInvocationTransactionalResults handleTxCallsFailure(
      GenericRemoteInvocationTransactionalCalls<CALL> txCall, Throwable error) {

    Throwable userException = this.exceptionUtil.convertForUser(error);
    logFailure(txCall, userException);
    Throwable clientException = this.exceptionUtil.convertForClient(userException);
    return new GenericRemoteInvocationTransactionalResults(clientException);
  }

  /**
   * Logs a given <code>error</code> that occurred while processing the given <code>source</code>.
   *
   * @param source is the source object. We do not use {@link Object#toString()} here as this is for debug
   *        output and might contain confidential information such as passwords or whatever that we do not
   *        want to log.
   * @param error is the {@link Throwable} to log.
   */
  protected void logFailure(Object source, Throwable error) {

    if ((error instanceof NlsThrowable) && (!((NlsThrowable) error).isTechnical())) {
      // user failure
      if (getLogger().isInfoEnabled()) {
        getLogger().info("error while processing {}: {}", source, error.getMessage());
      }
    } else {
      // technical error
      getLogger().error("error while processing {}.", source, error);
    }
  }

  /**
   * A context object for stateful information related to the current {@link GenericRemoteInvocationRequest}.
   */
  public class RequestContext {

    /** @see #getRequest() */
    private final REQUEST request;

    /** @see #getTransactionalResults() */
    private final GenericRemoteInvocationTransactionalResults[] transactionalResults;

    /** @see #getRequestToken() */
    private final CsrfToken requestToken;

    /** @see #getResponseToken() */
    private CsrfToken responseToken;

    /** @see #getCallTotalCount() */
    private int callTotalCount;

    /** @see #getCallLoginCount() */
    private int callLoginCount;

    /**
     * The constructor.
     *
     * @param request is the request.
     * @param transactionalResults is the prepared result array for the response.
     */
    public RequestContext(REQUEST request, GenericRemoteInvocationTransactionalResults[] transactionalResults) {

      super();
      this.request = request;
      this.requestToken = this.request.getXsrfToken();
      this.transactionalResults = transactionalResults;
    }

    /**
     * @return the request
     */
    public REQUEST getRequest() {

      return this.request;
    }

    /**
     * @return the transactionalResults is the prepared result array for the response.
     */
    public GenericRemoteInvocationTransactionalResults[] getTransactionalResults() {

      return this.transactionalResults;
    }

    /**
     * @return the current number of total {@link RemoteInvocationCall}s processed.
     */
    public int getCallTotalCount() {

      return this.callTotalCount;
    }

    /**
     * @return the current number of the {@link RemoteInvocationCall}s processed that represent a
     *         {@link RemoteInvocationSecurityDetector#isLogin(java.lang.reflect.AnnotatedElement) login
     *         operation}. Should normally be <code>0</code> or <code>1</code>.
     */
    public int getCallLoginCount() {

      return this.callLoginCount;
    }

    /**
     * @return the requestToken
     */
    public CsrfToken getRequestToken() {

      return this.requestToken;
    }

    /**
     * @return the responseToken
     */
    public CsrfToken getResponseToken() {

      return this.responseToken;
    }

    /**
     * @param responseToken is the responseToken to set
     */
    public void setResponseToken(CsrfToken responseToken) {

      this.responseToken = responseToken;
    }

  }

}
