/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.ArticleDAO;
import dao.CourseDAO;
import dao.JobDAO;
import entity.CompactArticle;
import entity.CompactCourse;
import entity.CompactJob;
import entity.ListArticle;
import entity.ListCourse;
import entity.ListJob;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.Page;
import utilities.Utilities;

/**
 *
 * @author yncdb
 */
public class homePageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = Page.homePage;
        try {
            System.out.println("homePageServlet-ing");
            ListArticle listArticle = ArticleDAO.getNFilRecordArt(3);
            String parseStringListArticle = Utilities.marshallerToString(listArticle);
//            ListCourse  listCourse = CourseDAO.get1kFilRecordCour();
//            String parseStringListCourse  = Utilities.marshallerToString(listCourse);
//            ListJob listJob = JobDAO.get1kFilRecordJob();
//            String parseStringListJob = Utilities.marshallerToString(listJob);
            request.setAttribute("ARTICLES", parseStringListArticle);
            //
            ListArticle listFirst = new ListArticle();
            List<CompactArticle> listFirstCom = new ArrayList<>();
            listFirstCom.add(listArticle.getArticle().get(0));
            listFirst.setArticle(listFirstCom);
            String parseStrFirstArt = Utilities.marshallerToString(listFirst);
            request.setAttribute("NEWEST_ARTICLE_ONE", parseStrFirstArt);
            //
            ListArticle listSecond = new ListArticle();
            List<CompactArticle> listSecondCom = new ArrayList<>();
            listSecondCom.add(listArticle.getArticle().get(1));
            listSecond.setArticle(listSecondCom);
            String parseStrSecondArt = Utilities.marshallerToString(listSecond);
            request.setAttribute("NEWEST_ARTICLE_TWO", parseStrSecondArt);
            //
            ListArticle listThird = new ListArticle();
            List<CompactArticle> listThirdCom = new ArrayList<>();
            listThirdCom.add(listArticle.getArticle().get(2));
            listThird.setArticle(listThirdCom);
            String parseStrThirdArt = Utilities.marshallerToString(listThird);
            request.setAttribute("NEWEST_ARTICLE_THREE", parseStrThirdArt);
            //Test for slide show
            ListCourse listCouseTest = new ListCourse();
            CompactCourse cou1 = new CompactCourse();
            cou1.setThumbnail("http://csc.edu.vn/data/images/slider/lap-trinh/slide-lap-trinh-web-238.png");
            CompactCourse cou2 = new CompactCourse();
            cou2.setThumbnail("http://csc.edu.vn/data/images/slider/lap-trinh/lap-trinh-r.jpg");
            CompactCourse cou3 = new CompactCourse();
            cou3.setThumbnail("http://csc.edu.vn/data/images/slider/lap-trinh/tong-khai-giang-lap-trinh-csdl-238.png");
            CompactCourse cou4 = new CompactCourse();
            cou4.setThumbnail("http://csc.edu.vn/data/images/slider/lap-trinh/slide-kotlin3-238.png");
            CompactCourse cou5 = new CompactCourse();
            cou5.setThumbnail("http://csc.edu.vn/data/images/slider/lap-trinh/slide-python-dj-235(1).png");
            List<CompactCourse> listCourseCom = new ArrayList<>();
            listCourseCom.add(cou1);
            listCourseCom.add(cou2);
            listCourseCom.add(cou3);
            listCourseCom.add(cou4);
            listCourseCom.add(cou5);
            listCouseTest.setCourseList(listCourseCom);
            String parseStringListCourseTest = Utilities.marshallerToString(listCouseTest);
            request.setAttribute("COURSES_SLIDE_SHOW", parseStringListCourseTest);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
