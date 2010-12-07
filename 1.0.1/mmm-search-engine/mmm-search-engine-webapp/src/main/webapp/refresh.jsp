<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="java.util.Locale"
%><%@page import="java.util.Map"
%><%@page import="java.util.HashMap"
%><%@page import="net.sf.mmm.util.nls.api.NlsLocalizer"
%><%@page import="net.sf.mmm.util.nls.api.NlsObject"
%><%@page import="net.sf.mmm.search.NlsBundleSearchApi"
%><%@page import="net.sf.mmm.search.view.api.SearchViewConfiguration"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchViewLogic logic = searchContext.getLogic();
  boolean refreshed = logic.refresh();
  Locale locale = request.getLocale();
  NlsLocalizer localizer = logic.getNlsLocalizer();
  Map<String, Object> nlsArguments = new HashMap<String, Object>();
  String searchPath = searchContext.getViewConfiguration().getSearchPath();
  if (searchPath.startsWith("/")) {
    searchPath = searchPath.substring(1);
  }
  nlsArguments.put(NlsObject.KEY_URI, searchPath);
%><html>
<head>
  <title>Search</title>
  <meta name="description" content="Search result page"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
</head>
<body>
<%@include file="jinc/header.jinc" %>
<div id="searchhead">
  <%= localizer.localize(locale, NlsBundleSearchApi.MSG_BACK_TO_SEARCH, nlsArguments)%>
</div>
<div id="hitlisttop">
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_REFRESH)%>
</div>
<div id="hit"><%
  if (refreshed) {
%>
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_REFRESH_SUCCESS)%>
<%
  } else {
%>
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_REFRESH_NO_CHANGE)%>
<%    
  }
%>
</div>
<div id="hitlistbottom">
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_REFRESH)%>
</div>
</body>  