/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import page.Page;

/**
 *
 * @author yncdb
 */
public class BGListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        final ServletContext context = sce.getServletContext();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("Deploy.......");
//                String realPath = sce.getServletContext().getRealPath("/");
//                String filePathOne = realPath + Page.filePathOne;     
//                String filePathTwo = realPath + Page.filePathTwo;
//                String filePathThree = realPath + Page.filePathThree;
//                String filePathFour = realPath + Page.filePathFour;
//                CrawlData cd = new CrawlData(context);
//                cd.crawlHTMLJob(filePathFour, "http://iviettech.vn/category/viec-lam", 1);
//                cd.crawlHTMLForIviettech(filePathOne, "http://iviettech.vn/category/chuong-trinh-dao-tao");
//                cd.crawlHTMLForKyna(filePathTwo, "https://kyna.vn/danh-sach-khoa-hoc/it-va-lap-trinh");
//                cd.crawlArticleForCsc(filePathThree, "http://csc.edu.vn/lap-trinh-va-csdl/tin-tuc/tin-hoat-dong-lap-trinh-48", 1);            
                System.out.println("Done");
            }
        };

        scheduler.scheduleAtFixedRate(runnable, 0, 7, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

}
