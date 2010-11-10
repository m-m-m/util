<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@ page import="net.sf.mmm.search.engine.api.config.SearchEngineConfiguration"
%><%@ page import="net.sf.mmm.search.view.SearchViewContext"%><%
  // get parameters as attributes (already prepared by the servlet)
  SearchViewContext searchContext = SearchViewContext.get(request);
  SearchEngineConfiguration conf = searchContext.getConfiguration();
  searchContext.getSearchEngine().refresh();
  // TODO: get from servlet parameter
  response.sendRedirect("/search");
%>