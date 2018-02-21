<%-- 
    Document   : homePage
    Created on : Feb 4, 2018, 5:27:30 PM
    Author     : yncdb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="content/img/favicon.ico" />
        <title>StudyIT - Trung tâm tin học Đại học FPT</title>
        <link href="content/css/homePage.css" rel="stylesheet" type="text/css">
        <link href="content/css/slideShow.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div class="background">
            <div class="container">
                <c:import url="header.jsp" charEncoding="UTF-8" />
                <div class="content">
                    <div class="carousel">
                        <input type="radio" id="carousel-1" name="carousel" checked>
                        <input type="radio" id="carousel-2" name="carousel">
                        <input type="radio" id="carousel-3" name="carousel">
                        <input type="radio" id="carousel-4" name="carousel">
                        <input type="radio" id="carousel-5" name="carousel">
                        <ul class="carousel__items">
                            <li class="carousel__item"><img src="content/img/slide1.jpg" alt="pic1"></li>
                            <li class="carousel__item"><img src="content/img/slide2.jpg" alt="pic2"></li>
                            <li class="carousel__item"><img src="content/img/slide3.jpg" alt="pic3"></li>
                            <li class="carousel__item"><img src="content/img/slide4.jpg" alt="pic4"></li>
                            <li class="carousel__item"><img src="content/img/slide5.jpg" alt="pic5"></li>
                        </ul>
                        <div class="carousel__prev">
                            <label for="carousel-1"></label>
                            <label for="carousel-2"></label>
                            <label for="carousel-3"></label>
                            <label for="carousel-4"></label>
                            <label for="carousel-5"></label>
                        </div>
                        <div class="carousel__next">
                            <label for="carousel-1"></label>
                            <label for="carousel-2"></label>
                            <label for="carousel-3"></label>
                            <label for="carousel-4"></label>
                            <label for="carousel-5"></label>
                        </div>
                        <div class="carousel__nav">
                            <label for="carousel-1"></label>
                            <label for="carousel-2"></label>
                            <label for="carousel-3"></label>
                            <label for="carousel-4"></label>
                            <label for="carousel-5"></label>
                        </div>
                    </div><!-- end slide show -->
                    <div id="gioi-thieu" class="container-fluid gioi-thieu" style="text-align:center; background-color:#f7fbfe">
                        <div class="tieu-de-thong-ke">
                            Cám ơn các bạn đã tin tưởng và đồng hành
                            cùng chúng tôi</div>
                        <div class="container group-thong-ke">
                            <div class="div1" style="margin-bottom:20px;">
                                <div class="bieu-tuong">
                                    <span>&gt;</span> 30</div>
                                <div class="noi-dung-thong-ke">
                                    năm</div>
                            </div>
                            <div class="div2" style="margin-bottom:20px;">
                                <div class="bieu-tuong">
                                    <span>&gt;</span> 1.000.000</div>
                                <div class="noi-dung-thong-ke">
                                    học viên</div>
                            </div>
                            <div class="div3" style="margin-bottom:20px;">
                                <div class="bieu-tuong">
                                    <span>&gt;</span> 20</div>
                                <div class="noi-dung-thong-ke">
                                    cơ sở khắp cả nước</div>
                            </div>
                            <div class="div4" style="margin-bottom:20px;">
                                <div class="bieu-tuong">
                                    <span>&gt;</span> 100</div>
                                <div class="noi-dung-thong-ke">
                                    chương trình đào tạo</div>
                            </div>
                        </div>
                        <p>
                            &nbsp;</p>
                    </div>
                    <div id="tin-tuc" style="text-align:center;">
                        <h1 class="tieu-de-muc">Tin tức sự kiện mới nhất</h1>
                    </div>
                    <div class="tin-tuc-moi-nhat">
                        
                    </div>
                    <div id="" class="startnews">
                        <ul id="otherArticleList">
                            <%--<x:transform xml="${otherArticles}" xslt="${xsldocOther}" />--%>
                        </ul>
                        <button class="btn btnMore" onclick="getMoreArticle(null)">Xem thêm</button>
                    </div>
                </div>
                <c:import url="footer.jsp" charEncoding="UTF-8" />
            </div>
        </div>
    </body>
    <script type="text/javascript" src="content/js/GeneralJs.js"></script>
</html>
