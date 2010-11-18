<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="net.sf.mmm.search.view.api.SearchViewConfiguration"%>
<%@ page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@ page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchViewLogic logic = searchContext.getLogic();
  logic.refresh();
  // TODO: does not work
  response.sendRedirect(((SearchViewConfiguration) logic).getSearchPath());
%>