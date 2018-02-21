/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
window.onscroll = function () {
    scrollFunction();
};
function scrollFunction() {
    if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
        document.getElementById("topBtn").style.display = "block";
        document.getElementById("top-zone").style.position = "fixed";

    } else {
        document.getElementById("topBtn").style.display = "none";
        document.getElementById("top-zone").style.position = "relative";

    }
}

function scrollToTop(scrollDuration) {
    var scrollStep = -window.scrollY / (scrollDuration / 15);
    var scrollInterval = setInterval(function () {
        if (window.scrollY !== 0) {
            window.scrollBy(0, scrollStep);
        } else
            clearInterval(scrollInterval);
    }, 15);
}



