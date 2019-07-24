<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Test for Data</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="An extended Bootstrap table with radio, checkbox, sort, pagination, and other added features.">
  <meta name="keywords" content="table, bootstrap, bootstrap plugin, bootstrap resources, bootstrap table, jQuery plugin">
  <meta name="author" content="Zhixin Wen, and Bootstrap table contributors">
  <!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
  <link rel="stylesheet" href="${ctxPath }/content/plugins/bootstrap-4.2.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="${ctxPath }/content/scripts/mapping/css/reset-min.css">
  <link rel="stylesheet" href="${ctxPath }/content/scripts/mapping/css/algolia-min.css">
  <link href="${ctxPath }/content/scripts/mapping/css/docs.min.css" rel="stylesheet">
  <link href="${ctxPath }/content/scripts/mapping/css/index.css?v=VERSION" rel="stylesheet">
  <!-- Favicons -->
  <link rel="apple-touch-icon" href="${ctxPath}/content/images/favicon.png">
  <link rel="icon" href="${ctxPath}/content/images/favicon.png">

  <script type="text/javascript">
    if (window !== top) // 判断当前的window对象是否是top对象
      top.location.href = window.location.href // 如果不是，将top对象的网址自动导向被嵌入网页的网址
  </script>
</head>

<body>
  <a class="skippy sr-only sr-only-focusable" href="#content">
    <span class="skippy-text">Skip to main content</span>
  </a>

  <header class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar">
    <a class="navbar-brand mr-0 mr-md-2" href="https://bootstrap-table.com/" aria-label="Bootstrap">
      <img src="${ctxPath}/content/images/favicon.png" width="36" height="36">
    </a>

    <div class="navbar-nav-scroll">
      <ul class="navbar-nav bd-navbar-nav flex-row">
        <li class="nav-item">
          <a class="nav-link" href="https://bootstrap-table.com/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="https://bootstrap-table.com/docs/getting-started/introduction/">
            Documentation
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="https://bootstrap-table.com/themes">
            CSS Frameworks
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="https://examples.bootstrap-table.com">
            Examples
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="https://bootstrap-table.com/donate">
            Donate
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="https://bootstrap-table.com/news">
            News
          </a>
        </li>
      </ul>
    </div>

    <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
      <li class="nav-item dropdown">
        <a class="nav-item nav-link dropdown-toggle mr-md-2" href="#" id="bd-theme" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <span id="theme-title"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="bd-theme">
          <a class="dropdown-item" data-theme="" href="index.html">Bootstrap v4</a>
          <a class="dropdown-item" data-theme="bootstrap3" href="index.html?bootstrap3">Bootstrap v3</a>
          <a class="dropdown-item" data-theme="semantic" href="index.html?semantic">Semantic UI</a>
          <a class="dropdown-item" data-theme="bulma" href="index.html?bulma">Bulma</a>
          <a class="dropdown-item" data-theme="materialize" href="index.html?materialize">Materialize</a>
          <a class="dropdown-item" data-theme="foundation" href="index.html?foundation">Foundation</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link p-2" href="https://github.com/wenzhixin/bootstrap-table" target="_blank" rel="noopener" aria-label="GitHub"><svg class="navbar-nav-svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 499.36" focusable="false" role="img"><title>GitHub</title><path d="M256 0C114.64 0 0 114.61 0 256c0 113.09 73.34 209 175.08 242.9 12.8 2.35 17.47-5.56 17.47-12.34 0-6.08-.22-22.18-.35-43.54-71.2 15.49-86.2-34.34-86.2-34.34-11.64-29.57-28.42-37.45-28.42-37.45-23.27-15.84 1.73-15.55 1.73-15.55 25.69 1.81 39.21 26.38 39.21 26.38 22.84 39.12 59.92 27.82 74.5 21.27 2.33-16.54 8.94-27.82 16.25-34.22-56.84-6.43-116.6-28.43-116.6-126.49 0-27.95 10-50.8 26.35-68.69-2.63-6.48-11.42-32.5 2.51-67.75 0 0 21.49-6.88 70.4 26.24a242.65 242.65 0 0 1 128.18 0c48.87-33.13 70.33-26.24 70.33-26.24 14 35.25 5.18 61.27 2.55 67.75 16.41 17.9 26.31 40.75 26.31 68.69 0 98.35-59.85 120-116.88 126.32 9.19 7.9 17.38 23.53 17.38 47.41 0 34.22-.31 61.83-.31 70.23 0 6.85 4.61 14.81 17.6 12.31C438.72 464.97 512 369.08 512 256.02 512 114.62 397.37 0 256 0z" fill="currentColor" fill-rule="evenodd"></path></svg>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link p-2" href="https://twitter.com/wenzhixin2010" target="_blank" rel="noopener" aria-label="Twitter"><svg class="navbar-nav-svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 416.32" focusable="false" role="img"><title>Twitter</title><path d="M160.83 416.32c193.2 0 298.92-160.22 298.92-298.92 0-4.51 0-9-.2-13.52A214 214 0 0 0 512 49.38a212.93 212.93 0 0 1-60.44 16.6 105.7 105.7 0 0 0 46.3-58.19 209 209 0 0 1-66.79 25.37 105.09 105.09 0 0 0-181.73 71.91 116.12 116.12 0 0 0 2.66 24c-87.28-4.3-164.73-46.3-216.56-109.82A105.48 105.48 0 0 0 68 159.6a106.27 106.27 0 0 1-47.53-13.11v1.43a105.28 105.28 0 0 0 84.21 103.06 105.67 105.67 0 0 1-47.33 1.84 105.06 105.06 0 0 0 98.14 72.94A210.72 210.72 0 0 1 25 370.84a202.17 202.17 0 0 1-25-1.43 298.85 298.85 0 0 0 160.83 46.92" fill="currentColor"></path></svg>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link p-2" href="https://opencollective.com/bootstrap-table" target="_blank" rel="noopener" aria-label="Open Collective"><svg class="navbar-nav-svg" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 40 41" focusable="false" role="img" fill="currentColor" fill-rule="evenodd"><title>Open Collective</title><path d="M32.8 21c0 2.4-.8 4.9-2 6.9l5.1 5.1c2.5-3.4 4.1-7.6 4.1-12 0-4.6-1.6-8.8-4-12.2L30.7 14c1.2 2 2 4.3 2 7z" fill-opacity=".4"></path><path d="M20 33.7a12.8 12.8 0 0 1 0-25.6c2.6 0 5 .7 7 2.1L32 5a20 20 0 1 0 .1 31.9l-5-5.2a13 13 0 0 1-7 2z"></path></svg>
        </a>
      </li>
    </ul>

    <a class="btn btn-bd-download d-none d-lg-inline-block mb-3 mb-md-0 ml-md-3" href="${ctxPath}/">东方足球网</a>
  </header>

  <div class="container-fluid">
    <div class="row flex-xl-nowrap">
      <div class="col-12 col-md-3 col-xl-2 bd-sidebar">
        <form class="bd-search d-flex align-items-center">
          <div id="searchbox"></div>
          <div id="hits">
            <div class="hits-body"></div>
            <div class="hits-footer">
              Search by <a class="algolia-docsearch-footer--logo" href="https://www.algolia.com/docsearch">Algolia</a>
            </div>
          </div>
          <button class="btn btn-link bd-search-docs-toggle d-md-none p-0 ml-3" type="button" data-toggle="collapse" data-target="#bd-docs-nav" aria-controls="bd-docs-nav" aria-expanded="false" aria-label="Toggle docs navigation"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 30 30" width="30" height="30" focusable="false" role="img"><title>Menu</title><path stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-miterlimit="10" d="M4 7h22M4 15h22M4 23h22"></path></svg>
          </button>
        </form>

        <nav class="collapse bd-links" id="bd-docs-nav">
          <div class="bd-toc-item">
            <a class="bd-toc-link" href="#welcome.html">澳客数据处理</a>
            <ul class="nav bd-sidenav">
                <li><a href="#league">联赛数据</a></li>
                <li><a href="#team">球队数据</a></li>
                <li><a href="#match">比赛数据</a></li>
                <li><a href="#welcomes/from-url.html">From URL</a></li>
                <li data-show=""><a href="#welcomes/modal-table.html">Modal Table</a></li>
                <li data-show="bootstrap3"><a href="#welcomes/modal-table-bootstrap3.html">Modal Table</a></li>
                <li><a href="#welcomes/group-columns.html">Group Columns</a></li>
                <li><a href="#welcomes/sub-table.html">Sub Table</a></li>
                <li><a href="#welcomes/multiple-table.html">Multiple Table</a></li>
                <li><a href="#welcomes/flat-json.html">Flat Json</a></li>
                <li><a href="#welcomes/large-data.html">Large Data</a></li>
                <li><a href="#welcomes/vue-component.html">Vue Component</a></li>
            </ul>
          </div>

          <div class="bd-toc-item">
            <a class="bd-toc-link" href="#options/table-height.html">Options</a>
            <ul class="nav bd-sidenav">
              <li><a href="#options/table-height.html">Table Height</a></li>
              <li data-show=""><a href="#options/table-classes.html">Table Classes</a></li>
              <li data-show=""><a href="#options/thead-classes.html">Thead Classes</a></li>
              <li><a href="#options/row-style.html">Row Style</a></li>
              <li data-show="bootstrap3,"><a href="#options/row-attributes.html">Row Attributes</a></li>
              <li><a href="#options/undefined-text.html">Undefined Text</a></li>
              <li><a href="#options/table-locale.html">Table Locale</a></li>
              <li><a href="#options/table-sortable.html">Table Sortable</a></li>
              <li><a href="#options/sort-class.html">Sort Class</a></li>
              <li><a href="#options/silent-sort.html">Silent Sort</a></li>
              <li><a href="#options/sort-name-order.html">Sort Name Order</a></li>
              <li><a href="#options/sort-stable.html">Sort Stable</a></li>
              <li><a href="#options/remember-order.html">Remember Order</a></li>
              <li><a href="#options/custom-sort.html">Custom Sort</a></li>
              <li><a href="#options/table-columns.html">Table Columns</a></li>
              <li><a href="#options/table-method.html">Table Method</a></li>
              <li><a href="#options/table-cache.html">Table Cache</a></li>
              <li><a href="#options/content-type.html">Content Type</a></li>
              <li><a href="#options/data-type.html">Data Type</a></li>
              <li><a href="#options/table-ajax.html">Table AJAX</a></li>
              <li><a href="#options/ajax-options.html">AJAX Options</a></li>
              <li><a href="#options/query-params.html">Query Params</a></li>
              <li><a href="#options/query-params-type.html">Query Params Type</a></li>
              <li><a href="#options/response-handler.html">Response Handler</a></li>
              <li><a href="#options/total-data-field.html">Total/Data Field</a></li>
              <li><a href="#options/table-pagination.html">Table Pagination</a></li>
              <li><a href="#options/only-info-pagination.html">Only Info Pagination</a></li>
              <li><a href="#options/show-extended-pagination.html">Show Extended Pagination</a></li>
              <li><a href="#options/total-not-filtered-field.html">Total Not Filtered Field</a></li>
              <li><a href="#options/pagination-loop.html">Pagination Loop</a></li>
              <li><a href="#options/client-side-pagination.html">Client Side Pagination</a></li>
              <li><a href="#options/server-side-pagination.html">Server Side Pagination</a></li>
              <li><a href="#options/show-button-icons.html">Show Button Icon</a></li>
              <li><a href="#options/show-button-text.html">Show Button Text</a></li>
              <li><a href="#options/page-number.html">Page Number</a></li>
              <li><a href="#options/page-size.html">Page Size</a></li>
              <li><a href="#options/page-list.html">Page List</a></li>
              <li><a href="#options/pagination-h-align.html">Pagination H Align</a></li>
              <li><a href="#options/pagination-v-align.html">Pagination V Align</a></li>
              <li><a href="#options/pagination-text.html">Pagination Text</a></li>
              <li><a href="#options/pagination-index-number.html">Pagination Index Number</a></li>
              <li><a href="#options/table-search.html">Table Search</a></li>
              <li><a href="#options/search-on-enter-key.html">Search On Enter Key</a></li>
              <li><a href="#options/strict-search.html">Strict Search</a></li>
              <li><a href="#options/visible-search.html">Visible Column Search</a></li>
              <li><a href="#options/show-search-button.html">Show Search Button</a></li>
              <li><a href="#options/show-search-clear-button.html">Show Search Clear Button</a></li>
              <li><a href="#options/trim-on-search.html">Trim On Search</a></li>
              <li><a href="#options/search-align.html">Search Align</a></li>
              <li><a href="#options/search-time-out.html">Search Time Out</a></li>
              <li><a href="#options/search-text.html">Search Text</a></li>
              <li><a href="#options/custom-search.html">Custom Search</a></li>
              <li><a href="#options/show-header.html">Show Header</a></li>
              <li><a href="#options/show-footer.html">Show Footer</a></li>
              <li><a href="#options/footer-style.html">Footer Style</a></li>
              <li><a href="#options/basic-columns.html">Basic Columns</a></li>
              <li><a href="#options/columns-toggle-all.html">Basic Columns Toggle All Checkbox</a></li>
              <li><a href="#options/large-columns.html">Large Columns</a></li>
              <li><a href="#options/minimum-count-columns.html">Minimum Count Columns</a></li>
              <li><a href="#options/show-pagination-switch.html">Show Pagination Switch</a></li>
              <li><a href="#options/show-refresh.html">Show Refresh</a></li>
              <li><a href="#options/show-toggle.html">Show Toggle</a></li>
              <li><a href="#options/show-fullscreen.html">Show Fullscreen</a></li>
              <li><a href="#options/smart-display.html">Smart Display</a></li>
              <li><a href="#options/table-escape.html">Table Escape</a></li>
              <li><a href="#options/filter-options.html">Filter Options</a></li>
              <li><a href="#options/id-field.html">Id Field</a></li>
              <li><a href="#options/click-to-select.html">Click To Select</a></li>
              <li><a href="#options/ignore-click-to-select-on.html">Ignore Click To Select On</a></li>
              <li><a href="#options/single-select.html">Single Select</a></li>
              <li><a href="#options/checkbox-header.html">Checkbox Header</a></li>
              <li><a href="#options/maintain-meta-data.html">Maintain Meta Data</a></li>
              <li><a href="#options/multiple-select-row.html">Multiple Select Row</a></li>
              <li><a href="#options/card-view.html">Card View</a></li>
              <li><a href="#options/detail-view.html">Detail View</a></li>
              <li><a href="#options/detail-view-icon.html">Detail View Icon</a></li>
              <li><a href="#options/detail-formatter.html">Detail Formatter</a></li>
              <li><a href="#options/detail-filter.html">Detail Filter</a></li>
              <li><a href="#options/custom-toolbar.html">Custom Toolbar</a></li>
              <li><a href="#options/toolbar-align.html">Toolbar Align</a></li>
              <li><a href="#options/buttons-toolbar.html">Buttons Toolbar</a></li>
              <li><a href="#options/buttons-align.html">Buttons Align</a></li>
              <li data-show="bootstrap3,"><a href="#options/buttons-prefix.html">Buttons Prefix</a></li>
              <li data-show="bootstrap3,"><a href="#options/buttons-class.html">Buttons Class</a></li>
              <li data-show="bootstrap3,"><a href="#options/table-icons.html">Table Icons</a></li>
              <li><a href="#options/icon-size.html">Icon Size</a></li>
              <li data-show="bootstrap3,"><a href="#options/icons-prefix.html">Icons Prefix</a></li>
            </ul>
          </div>

          <div class="bd-toc-item">
            <a class="bd-toc-link" href="#column-options/field.html">Column Options</a>
            <ul class="nav bd-sidenav">
              <li><a href="#column-options/field.html">Column Fields</a></li>
              <li><a href="#column-options/title.html">Column Title</a></li>
              <li><a href="#column-options/title-tooltip.html">Title Tooltip</a></li>
              <li><a href="#column-options/class.html">Column Class</a></li>
              <li><a href="#column-options/width.html">Column Width</a></li>
              <li><a href="#column-options/width-unit.html">Width Unit</a></li>
              <li><a href="#column-options/rowspan-colspan.html">Rowspan Colspan</a></li>
              <li><a href="#column-options/aligning-columns.html">Aligning Columns</a></li>
              <li><a href="#column-options/aligning-footer.html">Aligning Footer</a></li>
              <li><a href="#column-options/cell-style.html">Cell Style</a></li>
              <li><a href="#column-options/radio.html">Column Radio</a></li>
              <li><a href="#column-options/checkbox.html">Column Checkbox</a></li>
              <li><a href="#column-options/checkbox-enabled.html">Checkbox Enabled</a></li>
              <li><a href="#column-options/checkbox-disabled.html">Checkbox Disabled</a></li>
              <li><a href="#column-options/click-to-select.html">Click To Select</a></li>
              <li><a href="#column-options/show-select-title.html">Show Select Title</a></li>
              <li><a href="#column-options/sortable.html">Column Sortable</a></li>
              <li><a href="#column-options/sort-name-order.html">Sort Name Order</a></li>
              <li><a href="#column-options/sorter.html">Column Sorter</a></li>
              <li><a href="#column-options/visible.html">Column Visible</a></li>
              <li><a href="#column-options/switchable.html">Column Switchable</a></li>
              <li><a href="#column-options/card-visible.html">Card Visible</a></li>
              <li><a href="#column-options/searchable.html">Column Searchable</a></li>
              <li><a href="#column-options/formatter.html">Column Formatter</a></li>
              <li><a href="#column-options/footer-formatter.html">Footer Formatter</a></li>
              <li><a href="#column-options/detail-formatter.html">Detail Formatter</a></li>
              <li><a href="#column-options/search-formatter.html">Search Formatter</a></li>
              <li><a href="#column-options/escape.html">Column Escape</a></li>
              <li><a href="#column-options/events.html">Column Events</a></li>
            </ul>
          </div>

          <div class="bd-toc-item">
            <a class="bd-toc-link" href="https://github.com/wenzhixin/bootstrap-table-examples" target="_blank">
              Examples GitHub
            </a>
          </div>
        </nav>
      </div>

      <main class="col-12 col-md-9 col-xl-10 py-md-3 bd-content" role="main">
        <iframe width="100%" height="100%"></iframe>
      </main>
    </div>
  </div>

  <script src="${ctxPath }/content/plugins/jquery/jquery-3.3.1.min.js" ></script>
  <script src="${ctxPath }/content/plugins/popper/popper.min.js" ></script>
  <script src="${ctxPath }/content/plugins/bootstrap-4.2.1/js/bootstrap.min.js" ></script>
  <script>$.browser = {}</script>
  <script src="${ctxPath }/content/scripts/mapping/js/jquery.ba-hashchange.min.js"></script>
  <script src="${ctxPath }/content/scripts/mapping/js/algoliasearchLite.min.js"></script>
  <script src="${ctxPath }/content/scripts/mapping/js/instantsearch.production.min.js"></script>
  <script src="${ctxPath }/content/scripts/mapping/js/docs.min.js"></script>
  <script src="${ctxPath }/content/scripts/mapping/js/index.js?v=VERSION"></script>
</body>

</html>