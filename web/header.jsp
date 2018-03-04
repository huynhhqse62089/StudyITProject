<%-- 
    Document   : header
    Created on : Feb 3, 2018, 4:09:36 PM
    Author     : yncdb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<header>
    <div class="top-header" id="top-zone" style="position: relative">
        <div class="container-top">
            <div class="l-container-top" style="display: inline-block">
                <input type="text" class="searchInfo" placeholder="Nhập từ khóa"/>
                <span class="ltop">
                    <a href="DispatcherServlet?action=SEARCH"><img src="content/img/search.png" style="margin-top: 9px; margin-left: 3px"/></a>
                </span>
            </div>

            <span class="rtop">
                <a href="https://www.facebook.com/long.lp" ><img src="content/img/facebook.png"/></a>
                <a href="https://www.youtube.com/" ><img src="content/img/youtube.png"/></a>
                <a href="https://twitter.com/" ><img src="content/img/twitter.png"/></a>
                <a href="https://plus.google.com/?hl=vi" ><img src="content/img/googleplus.png"/></a>
            </span>
        </div>
    </div>
    <div class="header">
        <a href="DispatcherServlet"><img src="content/img/my_logo.png" alt="Logo" class="logo"/></a>
        <img src="content/img/slogan.png" class="slogan"/>
    </div>

    <div class="menu row">
        <ul>
            <li class="mlink zone-2"><a href="DispatcherServlet">Trang chủ</a></li>
            <li class="zone-1">|</li>
            <li class="mlink zone-3"><a href="DispatcherServlet?action=RANKING">Các khóa học lập trình</a></li>
            <li class="zone-1">|</li>
            <li class="mlink zone-2"><a href="DispatcherServlet?action=RANKING">Tin tức</a></li>
            <li class="zone-1">|</li>
            <li class="mlink zone-2"><a href="DispatcherServlet?action=RANKING">Việc làm</a></li>
            
        </ul>
    </div>
</header>
