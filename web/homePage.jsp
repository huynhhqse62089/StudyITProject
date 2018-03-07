<%-- 
    Document   : homePage
    Created on : Feb 4, 2018, 5:27:30 PM
    Author     : yncdb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>

<c:set var="newestArticleOne" value="${requestScope.NEWEST_ARTICLE_ONE}" />
<c:set var="newestArticleTwo" value="${requestScope.NEWEST_ARTICLE_TWO}" />
<c:set var="newestArticleThree" value="${requestScope.NEWEST_ARTICLE_THREE}" />
<c:set var="articles" value="${requestScope.ARTICLES}" />
<c:set var="coursesSlide" value="${requestScope.COURSES_SLIDE_SHOW}" />
<c:import var="xsldocNewestLeft" url="content/xslt/newestArticleLeft.xsl" />
<c:import var="xsldocNewestRight" url="content/xslt/newestArticleRight.xsl" />
<c:import var="xsldocSlideShow" url="content/xslt/courseSlideShow.xsl"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="content/img/favicon.ico" />
        <title>StudyIT - Trung tâm tin học Đại học FPT</title>
        <link href="content/css/homePage.css" rel="stylesheet" type="text/css">
        <link href="content/css/slideShow.css" rel="stylesheet" type="text/css">
    </head>
    <body onload="getThumbnail();
            swap()">
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
                            <x:transform doc="${coursesSlide}" xslt="${xsldocSlideShow}" />
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
                        <div class="main-left-div khoang-cach-5">                           
                            <x:transform doc="${newestArticleOne}" xslt="${xsldocNewestLeft}" />
                        </div>
                        <div class="main-right-div khoang-cach-5">
                            <div class="main-right-up-div">
                                <x:transform doc="${newestArticleTwo}" xslt="${xsldocNewestRight}" />
                            </div>
                            <div class="main-right-down-div">
                                <x:transform doc="${newestArticleThree}" xslt="${xsldocNewestRight}" />
                            </div>
                        </div>
                    </div>
                    <div id="" class="startnews">
                        <button class="btn btnMore" onclick="">Xem thêm</button>
                    </div>
                </div>
                <c:import url="footer.jsp" charEncoding="UTF-8" />
            </div>
        </div>
    </body>
    <script type="text/javascript" src="content/js/GeneralJs.js"></script>
    <script>
        var counter = 0;
        var time;
        var arrayPic = [];
        function getThumbnail() {
            var courseObj, parser, xmlDoc, pic, i, length;
            courseObj = '${requestScope.COURSES_SLIDE_SHOW}';
            parser = new DOMParser();
            xmlDoc = parser.parseFromString(courseObj, "text/xml");
            length = xmlDoc.getElementsByTagName("thumbnail").length;
            for (i = 0; i < length; i++) {
                pic = xmlDoc.getElementsByTagName("thumbnail")[i].childNodes[0].nodeValue;
                arrayPic.push(pic);
            }
        }

        function swap() {
            var tag = document.getElementById("pic");
            var label1 = document.getElementById("carousel-1");
            var label2 = document.getElementById("carousel-2");
            var label3 = document.getElementById("carousel-3");
            var label4 = document.getElementById("carousel-4");
            var label5 = document.getElementById("carousel-5");
            if (counter === arrayPic.length) {
                counter = 0;
            }
            tag.src = arrayPic[counter];
            if(counter == 0){
                label1.checked = true;
                label2.checked = false;
                label3.checked = false;
                label4.checked = false;
                label5.checked = false;
            }else if(counter == 1){
                label1.checked = false;              
                label2.checked = true;
                label3.checked = false;
                label4.checked = false;
                label5.checked = false;
            }else if(counter == 2){
                label1.checked = false;
                label2.checked = false;
                label3.checked = true;
                label4.checked = false;
                label5.checked = false;
            }else if(counter == 3){
                label1.checked = false;
                label2.checked = false;
                label3.checked = false;
                label4.checked = true;
                label5.checked = false;
            }else if(counter == 4){
                label1.checked = false;
                label2.checked = false;
                label3.checked = false;
                label4.checked = false;
                label5.checked = true;
            }               
            counter++;
            time = setTimeout("swap()", 2000);
        }
    </script>
</html>
