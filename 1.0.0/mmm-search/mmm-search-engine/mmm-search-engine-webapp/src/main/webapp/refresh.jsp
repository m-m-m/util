<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@ page import="java.util.Iterator"
%><%@ page import="net.sf.mmm.util.StringUtil"
%><%@ page import="net.sf.mmm.search.api.SearchEntry"
%><%@ page import="net.sf.mmm.search.view.SearchViewConfiguration"
%><%@ page import="net.sf.mmm.search.view.SearchViewContext"%><%
  // get parameters as attributes (already prepared by the servlet)
  SearchViewContext searchContext = SearchViewContext.get(request);
  SearchViewConfiguration conf = searchContext.getConfiguration();
  conf.getSearchEngine().refresh();
  response.sendRedirect("/search");
%>