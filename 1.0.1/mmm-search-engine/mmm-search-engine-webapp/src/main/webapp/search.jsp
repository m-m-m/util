<%--
 $Id$
 Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 http://www.apache.org/licenses/LICENSE-2.0
 --%><%@page import="net.sf.mmm.search.view.api.SearchEntryTypeView"%>
<%@page import="net.sf.mmm.util.nls.api.NlsObject"%>
<%@page import="java.util.Locale"
%><%@page import="java.util.Map"
%><%@page import="java.util.HashMap"
%><%@page import="net.sf.mmm.util.nls.api.NlsThrowable"
%><%@page import="net.sf.mmm.util.xml.api.XmlUtil"
%><%@page import="net.sf.mmm.util.nls.api.NlsLocalizer"
%><%@page import="net.sf.mmm.util.nls.api.NlsRuntimeException"
%><%@page import="net.sf.mmm.search.NlsBundleSearchApi"
%><%@page import="net.sf.mmm.search.api.config.SearchSource"
%><%@page import="net.sf.mmm.search.engine.api.config.SearchEntryType"
%><%@page import="net.sf.mmm.search.engine.api.SearchResultPage"
%><%@page import="net.sf.mmm.search.engine.api.SearchHit"
%><%@page import="net.sf.mmm.search.engine.api.config.SearchEngineConfiguration"
%><%@page import="net.sf.mmm.search.view.api.SearchSourceView"
%><%@page import="net.sf.mmm.search.view.api.SearchViewLogic"
%><%@page import="net.sf.mmm.search.view.api.SearchViewRequestParameters"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContextAccess"
%><%@page import="net.sf.mmm.search.view.api.SearchViewContext"%><%
  // get parameters as attributes (already validated and prepared by the servlet)
  SearchViewContext searchContext = SearchViewContextAccess.getContext(request);
  SearchResultPage searchPage = searchContext.getResultPage();
  SearchViewRequestParameters parameters = searchContext.getRequestParameters();
  SearchViewLogic logic = searchContext.getLogic();
  SearchEngineConfiguration configuration = logic.getConfiguration();
  XmlUtil xmlUtil = logic.getXmlUtil();
  Locale locale = request.getLocale();
  NlsLocalizer localizer = logic.getNlsLocalizer();
%><html>
<head>
  <title>Search</title>
  <meta name="description" content="Search result page"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="css/site.css" type="text/css"/>
  <script type="text/javascript">
function getById(id) {
  var element;
  if (document.getElementById) {
    // offical javascript
    element = document.getElementById(id);
  } else if (document.all) {
    // IE 6-
    element = document.all[id];
  } else if (document.layers) {
    // old netscape
    element = document.layers[id];
  }
  return element;
}
function toggleDiv(divId) {
  var divElement = getById(divId);
  if (divElement) {
    divElement.style.display = divElement.style.display ? "" : "block";
  }
}
function setDivVisible(divId, isVisible) {
  var divElement = getById(divId);
  if (divElement) {
    // dont ask me why "block" sets visible and "" makes invisble ???
    if (isVisible) {
      divElement.style.display = "block";
    } else {
      divElement.style.display = "";
    }
  }
}
function updateExpert() {
  setDivVisible('searchadvanced', document.search.expert.checked);
}
function showPage(pageNum) {
  document.search.<%= SearchViewRequestParameters.PARAMETER_PAGE %>.value = pageNum;
  document.search.submit();
}
  </script>
</head><%
  String paramExpert = request.getParameter("expert");
  if (paramExpert == null) {
    paramExpert = "";
  }
  String expertSelected = "";
  if (paramExpert.length() > 0) {
    expertSelected = " checked=\"true\"";
  }
  String htmlQuery = xmlUtil.escapeXml(parameters.getQuery(), true);
  Map<String, Object> nlsArguments = new HashMap<String, Object>();
  nlsArguments.put(NlsObject.KEY_QUERY, htmlQuery);
%>
<body onload="updateExpert()">
<%@include file="jinc/header.jinc" %>
<div id="searchhead">
  <form method="get" action="search" name="search" target="_self">
    <%= localizer.localize(locale, NlsBundleSearchApi.INF_QUERY) %>: 
    <input type="text" size="60" maxlength="2048" name="<%= SearchViewRequestParameters.PARAMETER_QUERY %>" value="<%= htmlQuery %>"/>
    <input type="hidden" name="<%= SearchViewRequestParameters.PARAMETER_PAGE %>" value="<%= parameters.getPageNumber() %>"/>  
    <input type="hidden" name="<%= SearchViewRequestParameters.PARAMETER_TOTAL_HIT_COUNT %>" value="<%= parameters.getTotalHitCount() %>"/>  
    <input type="submit" value="<%= localizer.localize(locale, NlsBundleSearchApi.INF_SEARCH) %>" onclick="showPage(0)"/>
    <input type="checkbox" name="expert" <%= expertSelected %> onclick="updateExpert()"/><%= localizer.localize(locale, NlsBundleSearchApi.INF_ADVANCED) %>
    <div id="searchadvanced">
      <%= localizer.localize(locale, NlsBundleSearchApi.INF_SOURCE) %>: 
      <select name="<%= SearchViewRequestParameters.PARAMETER_SOURCE %>" size="1"><%
  for (SearchSourceView source: logic.getSourceViews()) {
    String sourceKey = source.getId();
    String sourceSelected = "";
    if (sourceKey.equals(parameters.getSource())) {
      sourceSelected = "selected=\"true\"";
    }
        %>
        <option value="<%= sourceKey%>"<%= sourceSelected %>><%=localizer.localize(locale, 
            source.getTitle()) + " (" + source.getEntryCount() + ")" %></option><%
  }
%>
      </select>
      <%= localizer.localize(locale, NlsBundleSearchApi.INF_FILETYPE) %>:
      <select name="<%= SearchViewRequestParameters.PARAMETER_TYPE %>" size="1"><%
  for (SearchEntryTypeView type: logic.getEntryTypeViews()) {
    String typeKey = type.getId();
    String typeSelected = "";
    if (typeKey.equals(parameters.getType())) {
      typeSelected = " selected=\"true\"";
    }
%>
        <option value="<%= typeKey%>"<%= typeSelected %>><%=localizer.localize(locale, 
            type.getTitle()) + " (" + type.getEntryCount() + ")"%></option><%
  }
%>
      </select>
      <%= localizer.localize(locale, NlsBundleSearchApi.INF_CREATOR) %>: 
      <input type="text" maxlength="512" name="<%= SearchViewRequestParameters.PARAMETER_CREATOR %>" value="<%= xmlUtil.escapeXml(parameters.getCreator(), true) %>"/><br/>
      <%= localizer.localize(locale, NlsBundleSearchApi.INF_LAST_REFRESH) %>: <%= logic.getLastRefreshDate() %>
    </div>
  </form>
</div>
<% 
  if (searchPage == null) {
    NlsRuntimeException error = searchContext.getException();
    if (error != null) {
      %>
<div id="hitlisttop"> 
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_ERROR) %>
</div>
<div id="hit">
<br/>
<pre><% 
  error.printStackTrace(locale, new java.io.PrintWriter(out));
%>
</pre>
</div>
<div id="hitlistbottom">
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_ERROR) %>
</div>
      <%    
    }
  } else {
    if (searchPage.getTotalHitCount() == 0) {
      String noHitsMessage = localizer.localize(locale, NlsBundleSearchApi.MSG_NO_HITS, nlsArguments);
      %>
<div id="hitlisttop"> 
<%= noHitsMessage %>
</div>
<div id="hit">
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_NO_HITS_HINT, nlsArguments) %>
</div>
<div id="hitlistbottom">
<%= noHitsMessage %>
</div>
      <%
    } else {
      int hitStartIndex = searchPage.getPageIndex() * searchPage.getHitsPerPage();
      int hitStart = hitStartIndex + 1;
      int hitEnd = hitStartIndex + searchPage.getPageHitCount();
      int maxPage = searchPage.getPageCount();
      int currentPage = searchPage.getPageIndex() + 1;
      int pagingStart = searchPage.getPagingStartIndex(2);
      int pagingEnd = searchPage.getPagingEndIndex(2);
      nlsArguments.put(NlsObject.KEY_SIZE, Integer.valueOf(searchPage.getTotalHitCount()));
      nlsArguments.put(NlsObject.KEY_MIN, Integer.valueOf(hitStart));
      nlsArguments.put(NlsObject.KEY_MAX, Integer.valueOf(hitEnd));
      String top = localizer.localize(locale, NlsBundleSearchApi.INF_TOP);
      String bottom = localizer.localize(locale, NlsBundleSearchApi.INF_BOTTOM);
      String first = localizer.localize(locale, NlsBundleSearchApi.INF_FIRST);
      String last = localizer.localize(locale, NlsBundleSearchApi.INF_LAST);
      String next = localizer.localize(locale, NlsBundleSearchApi.INF_NEXT);
      String previous = localizer.localize(locale, NlsBundleSearchApi.INF_PREVIOUS);
      %>
<div id="hitlisttop"> 
<%= localizer.localize(locale, NlsBundleSearchApi.MSG_RESULT_RAGE, nlsArguments) %>
<br/>
<a href="#bottom"><img src="images/arrows/arrow-bottom-on.gif" alt="<%= bottom %>" title="<%= bottom %>"/></a>
<%@include file="jinc/search-paging.jinc" %>
</div>
      <%
      // show the hits
      int hitCount = searchPage.getPageHitCount();
      for (int hitIndex = 0; hitIndex < hitCount; hitIndex++) {
        SearchHit hit = searchPage.getPageHit(hitIndex);
        SearchSource source = configuration.getSource(hit.getSource());
        String baseUrl = "";
        if (source != null) {
          baseUrl = source.getUrlPrefix();
        }
        String url = baseUrl + hit.getUri();
        String title = logic.getDisplayTitle(hit);
        String styleClass;
        if ((hitIndex % 2) == 0) {
          styleClass = "even";
        } else {
          styleClass = "odd";
        }
        %>
<div id="hit" class="<%= styleClass %>">
<a href="details?id=<%= hit.getEntryId() %>"><img src="images/ranking/stars<%= hit.getScore(5) %>.gif"/></a> 
<a href="<%= url %>"><img src="images/filetypes/<%= logic.getEntryType(hit.getType()).getIcon() %>"/> <%= title %></a><br/>
<%= hit.getHighlightedText() %><br/>
<a href="<%= url %>"><span class="url"><%= url %></span></a>
</div>
        <%
      }
      %>
<div id="hitlistbottom">
<a name="bottom"></a><a href="#top"><img src="images/arrows/arrow-top-on.gif" alt="<%= top%>" title="<%= top%>"/></a> <%@include file="jinc/search-paging.jinc" %>
</div>
      <%
    }
    %>
    <%
  }
%>
<%@include file="jinc/footer.jinc" %>
</body>
</html>