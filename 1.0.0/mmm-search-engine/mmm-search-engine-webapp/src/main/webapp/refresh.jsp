<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="net.sf.mmm.search.view.api.SearchViewConfiguration"%>
<%@page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchViewLogic logic = searchContext.getLogic();
  boolean refreshed = logic.refresh();
%><html>
<head>
  <title>Search</title>
  <meta name="description" content="Search result page"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
  </script>
</head>
<body>
<%@include file="jinc/header.jinc" %>
<div id="searchhead"><%
  if (refreshed) {
%>
The search engine has been refreshed successfully.
<%
  } else {
%>
No changes since the last refresh.
<%    
  }
%>
</div>
</body>  