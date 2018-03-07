/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dao.ArticleDAO;
import dao.CourseDAO;
import dao.JobDAO;
import entity.Article;
import entity.Course;
import entity.Image;
import entity.Job;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import static java.rmi.server.LogStream.log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.lang3.StringEscapeUtils;
import page.Page;

/**
 *
 * @author yncdb
 */
public class CrawlData {

    private int lastestNumberPage = -1;

    ServletContext srvlContext;

    List<Course> listCourse;

    List<Article> listArticle;

    List<Job> listJob;

    public List<Article> getListArticle() {
        return listArticle;
    }

    public List<Job> getListJob() {
        return listJob;
    }

    public List<Course> getListCourse() {
        return listCourse;
    }

    public void setListCourse(List<Course> listCourse) {
        this.listCourse = listCourse;
    }

    public CrawlData() {
    }

    public CrawlData(ServletContext srvlContext) {
        this.srvlContext = srvlContext;
    }

    private BufferedReader getGeneralBufferedReader(String uri) {
        BufferedReader in = null;
        try {
            URL url = new URL(uri);
            URLConnection yc = url.openConnection();
            yc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            /* show thông số cấu hình để hình thành message gửi về server để clone dữ liệu về*/
            InputStream is = yc.getInputStream();
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        } catch (MalformedURLException ex) {
            log(CrawlData.class.getName() + " MalformedURLException: " + ex.getMessage());
        } catch (IOException ex) {
            log(CrawlData.class.getName() + " IOException: " + ex.getMessage());
        }

        return in;
    }

    public void crawlHTMLForIviettech(String filePath, String uri) {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getGeneralBufferedReader(uri);
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            boolean isFoundLiEleRedundancy = false;
            boolean isFoundDiv = false;
            writer.write("<root>");
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (inputLine.contains("</ol>")) {
                    isFoundLiEleRedundancy = false;
                } else {
                    //do nothing
                }
                if (!inputLine.contains("<ol>")
                        && !inputLine.contains("</ol>")
                        && !inputLine.contains("<p class=\"vt-color-red cls-title\">")
                        && !inputLine.contains("strong")
                        && !inputLine.contains("<li class=\"cls-no-border-top\">")
                        && !inputLine.contains("<ul class=\"vt-list-category cls-listing\" id=\"\">")
                        && !inputLine.contains("</ul>")) {

                    if (isFoundKeyWord == false) {
                        if (inputLine.contains("<li class=\"cls-item cls-has-bottom-border\">")) {
                            isFoundKeyWord = true;
                        } else {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                    if (isFoundKeyWord == true && inputLine.contains("</li>")) {
                        isFoundKeyWord = false;
                    } else {
                        //do nothing
                    }

                    if (isFoundKeyWord) {
                        if (inputLine.contains("<div class=\"vt-folow cls-content-info\">")) {
                            isFoundDiv = true;
                        } else {
                            //do nothing
                        }
                        if (isFoundLiEleRedundancy == true) {
                            isFoundLiEleRedundancy = false;
                            continue;
                        } else {
                            //do nothing
                        }
                        writer.write(inputLine + "\n");
                        if (isFoundDiv && inputLine.contains("</div>")) {
                            writer.write("</li>");
                            isFoundDiv = false;
                        }
                    } else {
                        //do nothing
                    }
                }//end if ol
                else {
                    //do nothing
                }
            }// end while
            writer.write("</root>");
            in.close();
            writer.close();
            getListCourseFromIviettech(filePath);
        } catch (Exception ex) {
            log(CrawlData.class.getName() + " Exception: " + ex.getMessage());
        }
    }

    public void crawlHTMLForKyna(String filePath, String uri) {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getGeneralBufferedReader(uri);
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (!inputLine.contains("<div class=\"number-student\">")) {
                    if (isFoundKeyWord == false) {
                        if (inputLine.contains("<ul id=\"w0\" class=\"k-box-card-list\">")) {
                            isFoundKeyWord = true;
                        } else {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                    if (isFoundKeyWord == true && inputLine.contains("<nav id=\"pager-container\">")) {
                        break;
                    } else {
                        //do nothing
                    }

                    if (isFoundKeyWord) {
                        if (inputLine.contains("data-ajax")) {
                            inputLine = inputLine.replace("data-ajax", "");
                        } else {
                            //do nothing
                        }
                        if (inputLine.contains("<img")) {
                            inputLine = inputLine.replace("jpg\">", "jpg\" />");
                            inputLine = inputLine.substring(0, inputLine.indexOf(">") + 1) + "</img" + inputLine.substring(inputLine.indexOf(">"), inputLine.length());
                        } else {
                            //do nothing
                        }
                        writer.write(inputLine + "\n");
                    } else {
                        //do nothing
                    }
                }//end if 
            }//end while
            in.close();
            writer.close();
            getListCourseFromKyna(filePath);
        } catch (Exception ex) {
            log(CrawlData.class.getName() + " Exception: " + ex.getMessage());
        }
    }

    public void getListCourseFromIviettech(String filePath) {
        XMLInputFactory fact = XMLInputFactory.newInstance();
        fact.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        fact.setProperty(XMLInputFactory.IS_VALIDATING, false);
        //Declare variable
        if (listCourse == null) {
            listCourse = new ArrayList<>();
        }
        XMLEventReader xmlEventReader = null;
        Course course = null;
        boolean check = false;
        try {
            xmlEventReader = fact.createXMLEventReader(
                    new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("li")) {//if 1
                        System.out.println("Start Element : li");
                        course = new Course();
                    }//end if li
                    else if (qName.equalsIgnoreCase("a")) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        if (attrHref != null) {
                            course.setLink(attrHref.getValue().trim());
                        }
                    }//end if a
                    else if (qName.equalsIgnoreCase("img")) {
                        Attribute attrTittle = startElement.getAttributeByName(new QName("title"));
                        if (attrTittle != null) {
                            course.setCourseName(attrTittle.getValue().trim());
                        }
                        Attribute attrScr = startElement.getAttributeByName(new QName("src"));
                        if (attrScr != null) {
                            course.setThumbnail(attrScr.getValue().trim());
                        }
                    }// end if img
                    else if (qName.equalsIgnoreCase("p")) {
                        String testContent = xmlEventReader.peek().toString();
                        if (testContent.trim().matches("\\u0054\\u0068\\u1edd\\u0069"
                                + "\\u0020\\u0067\\u0069\\u0061\\u006e\\u003a")) {// thời gian học
                            xmlEventReader.next();
                            XMLEvent eve1 = xmlEventReader.nextTag();
                            if (eve1.isStartElement()) {
                                StartElement ele1 = (StartElement) eve1;
                                if (ele1.getName().toString().equals("b")) {
                                    course.setLengthStudy(xmlEventReader.peek().toString());
                                }
                            }
                        } else if (testContent.trim().matches("\\u0053\\u0075\\u1ea5"
                                + "\\u0074\\u0020\\u0068\\u1ecd\\u0063\\u003a")) {// suất học
                            xmlEventReader.next();
                            XMLEvent eve1 = xmlEventReader.nextTag();
                            if (eve1.isStartElement()) {
                                StartElement ele1 = (StartElement) eve1;
                                if (ele1.getName().toString().equals("b")) {
                                    course.setTimeStudy(xmlEventReader.peek().toString());
                                }
                            }
                        }
                    }//end if p
                    else if (qName.equalsIgnoreCase("i")) {
                        xmlEventReader.next();
                        xmlEventReader.nextTag();
                        String valueOpeningDate = xmlEventReader.peek().toString();
                        course.setOpeningDate(valueOpeningDate.substring(
                                valueOpeningDate.indexOf(":") + 1, valueOpeningDate.length()).trim());
                    }//end if i
                }// end if startElement
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("li")) {
                        course.setId(listCourse.size());
                        check = getCourseDetailForIviettech(course);
                        if (check) {
                            listCourse.add(course);
                        } else {
                            //do nothing
                        }
                    }
                }
            }// end while
            System.out.println("Xong cai course ItechViet");
        } catch (Exception e) {
            log(CrawlData.class.getName() + " Exception: " + e.getMessage());
        }
    }

    private boolean getCourseDetailForIviettech(Course course) {
//        Writer writer = null;
        boolean checkAddSucOrNot = false;
        int id = -1;
        String inputLine = "";
        String content = "";
        XMLStreamReader reader = null;
        BufferedReader in = null;
        InputStream is = null;
        Image image = null;

        try {
            URL url = new URL(course.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            in = getGeneralBufferedReader(course.getLink());
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            boolean startTuition = false;
            boolean startRemoveTable = false;
            boolean startLinkImage = false;
            boolean specialCase = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<div class=\"wd-detail-article\">")) {
                        isFoundKeyWord = true;
                    } else {
                        //do nothing
                    }
                } else {
                    //do nothing
                }

                if (isFoundKeyWord == true && inputLine.contains("class=\"row-fluid vt-question\"")) {
                    break;
                } else {
                    //do nothing
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("<a href=\"http://iviettech.vn/dang-ky\">")
                            && startRemoveTable == false) {//handling for image ->
                        inputLine = inputLine.replace("<a", "<imagelink");
                        startLinkImage = true;
                    }

                    if (startLinkImage && inputLine.contains("</a>") && startRemoveTable == false) {
                        inputLine = inputLine.replace("</a>", "</imagelink>");
                        startLinkImage = false;
                    } //handling for image <-

                    if (startRemoveTable) {// handling for tuition ->
                        if (inputLine.contains("<strong>")) {
                            inputLine = inputLine.replace("<strong>", "");
                        }
                        if (inputLine.contains("</strong>")) {
                            inputLine = inputLine.replace("</strong>", "");
                        }

                        if (inputLine.contains("<br />")) {
                            inputLine = inputLine.replace("<br />", "");
                        }

                        if (inputLine.contains("&#8211;")) {
                            inputLine = inputLine.replace("&#8211;", "");
                        }

                        if (inputLine.contains("<div align=\"left\">")) {
                            specialCase = true;
                        }

                    }// handling for tuition <-

                    if (startTuition && inputLine.contains("<td>") && specialCase == false) {//handling for tuition ->
                        inputLine = inputLine.replace("<td>", "<tuition>");
                    } else if (startTuition && inputLine.
                            contains("<td style=\"padding-left: 20px;\" valign=\"top\" width=\"350\">")
                            && specialCase == true) {
                        inputLine = inputLine.
                                replace("<td style=\"padding-left: 20px;\" valign=\"top\" width=\"350\">", "<tuition>");
                    }

                    if (startTuition && inputLine.contains("</td>")) {
                        inputLine = inputLine.replace("</td>", "</tuition>");
                        startTuition = false;
                    }

                    if (inputLine.contains("(1 lần)")) {
                        startTuition = true;
                    }//handling for tuition <-

                    content += inputLine;
//                    writer.write(inputLine + "\n");

                    if (inputLine.contains("<h3 class=\"wd-tlh3-4\">Học phí")) {//handling for tuition ->
                        content += "<removetable>";
//                        writer.write("<removetable>" + "\n");
                        startRemoveTable = true;
                    }
                    if (startRemoveTable && inputLine.contains("</table>") && specialCase == false) {
                        content += "</removetable>";
//                        writer.write("</removetable>" + "\n");
                        startRemoveTable = false;
                    } else if (startRemoveTable && inputLine.contains("</div>")
                            && specialCase == true) {
                        content += "</removetable>";
//                        writer.write("</removetable>" + "\n");
                        startRemoveTable = false;
                        specialCase = false;
                    }//handling for tuition <-
                }//end if foundKeyword
                else {
                    //do nothing
                }
            }//end while
//            writer.write("</div>");
//            writer.close();
            content += "</div>";
            content = content.replaceAll("&nbsp;", "&#160;");
            in.close();
            is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            String removeText = content.substring(content.indexOf("<removetable>"),
                    content.indexOf("</removetable>") + 1);
//            String removeImage = content.substring(content.indexOf("<imagelink href=\"http://iviettech.vn/dang-ky\">"),
//                    content.indexOf("</imagelink>") + 12);
            content = content.replace(removeText, "");
//            content = content.replace(removeImage, "");
            course.setDescription(content);
            //get Images,tuition and instructor name
            course.setInstructorName(Page.instructorNameIviettech);
            course.setLocation(Page.locationIviettech);
            reader = Utilities.parseFileToStAXCursor(is);
            while (reader.hasNext()) { //handling for tuition ->
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equalsIgnoreCase("imagelink")) {
                        image = new Image();
                        reader.next();
                        String nextTag = reader.getLocalName();
                        if (nextTag.equalsIgnoreCase("img")) {
                            String linkImage = reader.getAttributeValue(null, "src");
                            image.setLink(linkImage);
                            image.setType("illustration");
                            image.setId(course.getId());
                        }
                    } else if (tagName.equalsIgnoreCase("removetable")) {
                        String txtTuition = Utilities.getTextContext("tuition", reader);
                        course.setTuition(txtTuition.substring(0, txtTuition.indexOf(")") + 1));
                        break;
                    }
                }//end jf currentCursor
            }//handling for tuition <-
            String realPath = srvlContext.getRealPath("/");
            String coursePath = realPath + Page.filePathCourseSch;
            String imagePath = realPath + Page.filePathImageSch;
            boolean isValidCourse = Utilities.validateXMLBeforeSaveToDatabase(
                    Utilities.marshallerToString(course), coursePath);
            if (isValidCourse) {
                boolean isValidImage = Utilities.validateXMLBeforeSaveToDatabase(
                        Utilities.marshallerToString(image), imagePath);
                if (isValidImage) {
                    course.addImage(image);
                }
                id = CourseDAO.addCourse(course);
                checkAddSucOrNot = id > 0 ? true : false;
            }
        } catch (Exception ex) {
            log(CrawlData.class.getName() + " Exception: " + ex.getMessage());
        } finally {
            try {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (XMLStreamException ex) {
                        log(CrawlData.class.getName() + " XMLStreamException: " + ex.getMessage());
                    }
                }
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                log(CrawlData.class.getName() + " IOException: " + ex.getMessage());
            }
        }
        return checkAddSucOrNot;
    }

    public void getListCourseFromKyna(String filePath) {
        XMLInputFactory fact = XMLInputFactory.newInstance();
        fact.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        fact.setProperty(XMLInputFactory.IS_VALIDATING, false);
        //Declare variable
        if (listCourse == null) {
            listCourse = new ArrayList<>();
        }
        XMLEventReader xmlEventReader = null;
        Course course = null;
        boolean check = false;
        boolean isFoundLi = false;
        boolean isEnd = false;
        try {
            xmlEventReader = fact.createXMLEventReader(
                    new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("li") && isFoundLi == false) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue()
                                .equalsIgnoreCase("col-xl-4 col-lg-6 col-xs-12 k-box-card")) {
                            System.out.println("Start LI:");
                            isFoundLi = true;
                            course = new Course();
                        } else {
                            //do nothing
                        }
                    } else if (isFoundLi && qName.equalsIgnoreCase("img")) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue().equalsIgnoreCase("img-fluid")) {
                            System.out.println("Img");
                            Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                            if (attrSrc != null) {
                                course.setThumbnail(attrSrc.getValue().trim());
                            }
                        } else {
                            // do nothing
                        }
                    } else if (isFoundLi && qName.equalsIgnoreCase("span")) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue().equalsIgnoreCase("time pc")) {
                            String hoursStudy = xmlEventReader.getElementText().trim();
                            course.setLengthStudy(hoursStudy);
                        }
                        if (attrClass != null && attrClass.getValue().equalsIgnoreCase("author")) {
                            String authorName = xmlEventReader.getElementText().trim();
                            course.setInstructorName(authorName);
                        }
                    } else if (isFoundLi && qName.equalsIgnoreCase("h4")) {
                        String courName = xmlEventReader.getElementText();
                        course.setCourseName(courName);
                    } else if (isFoundLi && qName.equalsIgnoreCase("li")) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue().equalsIgnoreCase("price")) {
                            xmlEventReader.next();
                            String price = xmlEventReader.getElementText();
                            course.setTuition(price);
                        }
                    } else if (isFoundLi && qName.equalsIgnoreCase("a")) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null && attrClass.getValue().equalsIgnoreCase("link-wrap")) {
                            Attribute attrhref = startElement.getAttributeByName(new QName("href"));
                            course.setLink("https://kyna.vn" + attrhref.getValue().trim());
                            isEnd = true;
                        } else {
                            //do nothing
                        }
                    }

                }//end if startElement
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("li") && isEnd) {
                        course.setLocation(Page.locationKyna);
                        course.setOpeningDate(Page.expectedString);
                        course.setTimeStudy(Page.expectedString);
                        course.setId(listCourse.size());
                        check = getCourseDetailForKyna(course);
                        if (check) {
                            listCourse.add(course);
                        } else {
                            //do nothing
                        }
                        isFoundLi = false;
                        isEnd = false;
                    }
                }//end if endElement
            }// end while
            System.out.println("Xong cai kyna");
        } catch (Exception ex) {
            log(CrawlData.class.getName() + " Exception: " + ex.getMessage());
        }
    }

    private boolean getCourseDetailForKyna(Course course) {
//        Writer writer = null;
        boolean checkAddSucOrNot = false;
        int id = -1;
        String inputLine = "";
        String content = "";
        XMLStreamReader reader = null;
        BufferedReader in = null;
        InputStream is = null;
        Image image = null;
        try {
            URL url = new URL(course.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            in = getGeneralBufferedReader(course.getLink());
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<div class=\"k-course-details-main main-container\">")) {
                        isFoundKeyWord = true;
                    }
                } else {
                    //do nothing
                }
                if (isFoundKeyWord && inputLine.contains("<div id=\"k-course-details-author\"")) {
                    break;
                } else {
                    //do nothing
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("<div id=\"k-course-details-author\"")) {
                        inputLine = inputLine.replace("<section>", "<div id=\"wd-detail-article\"");
                    }
                    if (inputLine.contains("<section>")) {
                        inputLine = inputLine.replace("<section>", "");
                    }
                    if (inputLine.contains("</section>")) {
                        inputLine = inputLine.replace("</section>", "");
                    }

                    if (inputLine.contains("<i class=\"icon icon-arrow-circle-right-line\"></i>")) {
                        inputLine = inputLine.replace("<i class=\"icon icon-arrow-circle-right-line\"></i>", "");
                    }
                    if (inputLine.contains("k-course-details-main main-container")) {
                        inputLine = inputLine.replace("k-course-details-main main-container", "wd-detail-article");
                    }
//                    writer.write(inputLine + "\n");
                    content += inputLine;
                } else {
                    //do nothing
                }
            }//end while
            course.setDescription(content);
            //Create Image for Course
            image = new Image();
            image.setLink(Page.linkSampleImage);
            image.setType("illustration");
            image.setId(course.getId());
            //end Create Image for Course
            String realPath = srvlContext.getRealPath("/");
            String coursePath = realPath + Page.filePathCourseSch;
            String imagePath = realPath + Page.filePathImageSch;
            boolean isValidCourse = Utilities.validateXMLBeforeSaveToDatabase(
                    Utilities.marshallerToString(course), coursePath);
            if (isValidCourse) {
                boolean isValidImage = Utilities.validateXMLBeforeSaveToDatabase(
                        Utilities.marshallerToString(image), imagePath);
                if (isValidImage) {
                    course.addImage(image);
                    id = CourseDAO.addCourse(course);
                    checkAddSucOrNot = id > 0 ? true : false;
                } else {
                    //do nothing
                }
            } else {
                //do nothing
            }
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    try {
                        reader.close();

                    } catch (XMLStreamException ex) {
                        log(CrawlData.class
                                .getName() + " XMLStreamException: " + ex.getMessage());
                    }
                }
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();

                }
            } catch (IOException ex) {
                log(CrawlData.class
                        .getName() + " IOException: " + ex.getMessage());
            }
        }
        return checkAddSucOrNot;
    }

    public void crawlArticleForCsc(String filePath, String uri, int startIndex) {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getGeneralBufferedReader(uri);
            String inputLine;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("class=col-md-9 khoang-cach-5")) {
                        isFoundKeyWord = true;
                    }
                }
                if (isFoundKeyWord && inputLine.contains("<div class=\"col-md-3 hidden-sm hidden-xs\">")) {
                    break;
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("class=col-md-9 khoang-cach-5")) {
                        inputLine = inputLine.replace("class=col-md-9 khoang-cach-5", "<div class='col-md-9 khoang-cach-5'");
                    }
                    if (inputLine.contains("</li<li>")) {
                        inputLine = inputLine.replace("</li<li>", "</li><li>");
                    }

                    if (inputLine.contains("&laquo;")) {
                        inputLine = inputLine.replace("&laquo;", "");
                    }
                    if (inputLine.contains("&raquo;")) {
                        inputLine = inputLine.replace("&raquo;", "");
                    }
                    if (inputLine.contains("&nbsp;")) {
                        inputLine = inputLine.replace("&nbsp;", "");
                    }

                    if (inputLine.contains("&")) {
                        inputLine = inputLine.replace("&", "");
                    }
//                    if (inputLine.contains("<img")) {
//                        inputLine = inputLine.replace("jpg\">", "jpg\" />");
//                        inputLine = inputLine.substring(0, inputLine.indexOf(">") + 1) + "</img" + inputLine.substring(inputLine.indexOf(">"), inputLine.length());
//                    }
                    writer.write(inputLine + "\n");
                }

            }//end while
            in.close();
            writer.close();
            if (lastestNumberPage < 0) {
                lastestNumberPage = getLastestPage(filePath, "class", "link");
            }
            getListArticleFromCsc(filePath);
            String realPath = srvlContext.getRealPath("/");
            String filePathThree = realPath + Page.filePathThree;
            if (startIndex == lastestNumberPage) {
                System.out.println("Xong cai Csc");
                lastestNumberPage = -1;
                return;
            }
            startIndex++;
            crawlArticleForCsc(filePathThree, "http://csc.edu.vn/lap-trinh-va-csdl/tin-tuc/tin-hoat-dong-lap-trinh-48/trang-" + String.valueOf(startIndex), startIndex);

        } catch (Exception ex) {
            log(CrawlData.class
                    .getName() + " Exception: " + ex.getMessage());
        }
    }

    private int getLastestPage(String filePath, String classStr, String classStrValue) throws FileNotFoundException, XMLStreamException {
        InputStream is = null;
        XMLStreamReader reader = null;
        int number = -1;
        boolean isMeetUlPag = false;
        try {
            is = new FileInputStream(filePath);
            reader = Utilities.parseFileToStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName().trim();
                    if (!isMeetUlPag && tagName.equalsIgnoreCase("ul")) {
                        String classStrUl = reader.getAttributeValue(null, "class");
                        if (classStrUl != null) {
                            if (classStrUl.equalsIgnoreCase("pagination")) {
                                isMeetUlPag = true;
                            } else {
                                //do nothing
                            }
                        } else {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                    if (tagName.equalsIgnoreCase("a")
                            && classStr != null && isMeetUlPag) {
//                        int currentCursorTemp = reader.nextTag();
//                        if (currentCursorTemp == XMLStreamConstants.START_ELEMENT) {
//                            continue;
//                        }
                        String attrClass = reader.getAttributeValue(null, classStr);
                        if (attrClass != null && attrClass.equalsIgnoreCase(classStrValue)) {
                            try {
                                int tmp = Integer.parseInt(reader.getElementText());
                                if (number < tmp) {
                                    number = tmp;
                                }
                            } catch (NumberFormatException e) {
                                continue;
                            }
                        } else {
                            //do nothing
                        }
                    }//end if equal tag a
                    else if (tagName.equalsIgnoreCase("a")
                            && classStr == null && isMeetUlPag) {
                        String attrIndex = reader.getAttributeValue(null, "tabindex");
                        if (attrIndex != null) {
                            continue;
                        } else {
                            //do nothing
                        }
                        try {
                            int tmp = Integer.parseInt(reader.getElementText());
                            if (number < tmp) {
                                number = tmp;
                            }
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                } else {
                    //do nothing
                }
            }
        } catch (FileNotFoundException e) {
            log(CrawlData.class
                    .getName() + " FileNotFoundException: " + e.getMessage());
        } catch (XMLStreamException e) {
            return number;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                try {
                    is.close();

                } catch (IOException ex) {
                    log(CrawlData.class
                            .getName() + " IOException: " + ex.getMessage());
                }
            }
        }

        return number;
    }

    private void getListArticleFromCsc(String filePath) {
        XMLInputFactory fact = XMLInputFactory.newInstance();
        fact.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        fact.setProperty(XMLInputFactory.IS_VALIDATING, false);
        //Declare variable
        if (listArticle == null) {
            listArticle = new ArrayList<>();
        }
        XMLEventReader xmlEventReader = null;
        Article article = null;
        boolean isFoundMainDiv = false;
        boolean isEnd = false;
        boolean check = false;
        try {
            xmlEventReader = fact.createXMLEventReader(
                    new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();
                if (event.isStartElement()) {//start if start element
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("div") && isFoundMainDiv == false) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null) {
                            if (attrClass.getValue()
                                    .equalsIgnoreCase("col-xs-12 col-sm-8 khoang-cach-5")
                                    || attrClass.getValue().equalsIgnoreCase("col-xs-12 col-md-12 khoang-cach-5")
                                    || attrClass.getValue().equalsIgnoreCase("col-md-12 khoang-cach-5")) {
                                isFoundMainDiv = true;
                                article = new Article();
                            }
                        } else {
                            //do nothing
                        }
                    } else if (isFoundMainDiv && qName.equalsIgnoreCase("a")) {
                        Attribute attrTitle = startElement.getAttributeByName(new QName("title"));
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        XMLEvent eventTmp = xmlEventReader.nextEvent();
                        if (attrTitle != null && attrHref != null && eventTmp.isStartElement()) {
                            article.setTitle(attrTitle.getValue());
                            article.setLink("http://csc.edu.vn" + attrHref.getValue());
                            StartElement startElementTmp = eventTmp.asStartElement();
                            String qNameTmp = startElementTmp.getName().getLocalPart();
                            if (qNameTmp.equalsIgnoreCase("img")) {
                                Attribute attrSrc = startElementTmp.getAttributeByName(new QName("src"));
                                if (attrSrc != null) {
                                    article.setThumbnail("http://csc.edu.vn" + attrSrc.getValue());
                                }
                            }
                        } else if (attrTitle != null && attrHref != null && eventTmp.isCharacters()) {
                            Characters chars = (Characters) eventTmp;
                            article.setSummary(chars.getData());
                            isEnd = true;
                        }
                    }//end if tag a                  
                }//end if start element
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("div") && isEnd) {
                        article.setId(listArticle.size());
                        check = getArticleDetailForCsc(article);
                        if (check) {
                            listArticle.add(article);
                        } else {
                            //do nothing
                        }
                        isFoundMainDiv = false;
                        isEnd = false;

                    }
                }//end if endElement
            }//end while   
            System.out.println("a");
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        }
    }

    private boolean getArticleDetailForCsc(Article article) {
//        Writer writer = null;
        boolean checkAddSucOrNot = false;
        int id = -1;
        String inputLine = "";
        String content = "";
        XMLStreamReader reader = null;
        BufferedReader in = null;
        InputStream is = null;
        Image image = null;
        List<Image> listImage = new ArrayList<Image>();
        try {
            URL url = new URL(article.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            in = getGeneralBufferedReader(article.getLink());
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<div class='noi-dung-bai-viet-chi-tiet'>")) {
                        isFoundKeyWord = true;
                    }
                } else {
                    //do nothing
                }
                if (isFoundKeyWord && inputLine.contains("<div class=\"cung-chuyen-muc\">")) {
                    break;
                } else {
                    //do nothing
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("&")) {
                        inputLine = StringEscapeUtils.unescapeHtml4(inputLine).trim();
                        inputLine = inputLine.replace("&", "Và");
                    }

//                    writer.write(inputLine + "\n");
                    content += inputLine;
                } else {
                    //do nothing
                }
            }//end while
            String removeText = content.substring(0, content.indexOf("</h1>") + 5);
            content = content.replace(removeText, "");
            content = content.replaceAll("&lt;", "<");
            content = content.replaceAll("&gt;", ">");
            article.setDescription(content);
            //set image
            is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            reader = Utilities.parseFileToStAXCursor(is);
            while (reader.hasNext()) {
                int currentCursor = reader.next();
                if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("img".equalsIgnoreCase(tagName)) {
                        String linkImage = reader.getAttributeValue(null, "src");
                        image = new Image();
                        image.setId(article.getId());
                        image.setLink(linkImage);
                        image.setType("illustration");
                        image.setId(article.getId());
                        listImage.add(image);
                    }//end if img
                    else if ("span".equals(tagName)) {
                        String classStr = reader.getAttributeValue(null, "class");
                        if (classStr != null) {
                            if (classStr.equalsIgnoreCase("glyphicon glyphicon-time")) {
                                reader.nextTag();
                                reader.next();
                                String dateStr = reader.getText().trim();
                                String dateStrFil = dateStr.substring(dateStr.indexOf("y") + 1, dateStr.length()).trim();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = sdf.parse(dateStrFil);
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                                article.setPubDate("Ngày " + sdf2.format(date));
                                article.setAuthor(Page.authorCsc);
                            }
                        }//end if != null
                        else {
                            //do nothing
                        }
                    }
                }//end if startEle
                else {
                    //do nothing
                }
            }//and whhile
            boolean check = true;
            String realPath = srvlContext.getRealPath("/");
            String articlePath = realPath + Page.filePathArticleSch;
            String imagePath = realPath + Page.filePathImageSch;
            boolean isValidArticle = Utilities.validateXMLBeforeSaveToDatabase(
                    Utilities.marshallerToString(article), articlePath);
            if (isValidArticle) {
                for (Image ima : listImage) {
                    boolean isValidImage = Utilities.validateXMLBeforeSaveToDatabase(
                            Utilities.marshallerToString(ima), imagePath);
                    if (isValidImage) {
                        article.addImage(image);
                    } else {
                        check = false;
                    }
                }
                if (check) {
                    id = ArticleDAO.addArticle(article);
                    checkAddSucOrNot = id > 0 ? true : false;
                }
            } else {
                //do nothing
            }
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    try {
                        reader.close();

                    } catch (XMLStreamException ex) {
                        log(CrawlData.class
                                .getName() + " XMLStreamException: " + ex.getMessage());
                    }
                }
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();

                }
            } catch (IOException ex) {
                log(CrawlData.class
                        .getName() + " IOException: " + ex.getMessage());
            }
        }
        return checkAddSucOrNot;
    }

    public void crawlHTMLJob(String filePath, String uri, int startIndex) {
        Writer writer = null;
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent",
                    "Chrome");

            BufferedReader in = getGeneralBufferedReader(uri);
            String inputLine;
            boolean isFoundKeyWord = false;
            boolean meetUl = false;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));
            writer.write("<root>");
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<ul class=\"vt-list-category cls-listing\" >")) {
                        isFoundKeyWord = true;
                    } else {
                        //do nothing
                    }
                }//end if isFoundKeyWord
                else {
                    //do nothing
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("</ul>")) {
                        meetUl = true;
                    } else {
                        //do nothing
                    }
                    if (meetUl && inputLine.contains("</div>")) {
                        continue;
                    } else {
                        //do nothing
                    }
                    if (inputLine.contains("&")) {
                        inputLine = StringEscapeUtils.unescapeHtml4(inputLine).trim();
                        inputLine = inputLine.replace("&", "AND");
                    } else {
                        //do nothing
                    }

                    if (inputLine.contains("")) {
                        inputLine = inputLine.replace("", "");
                    } else {
                        //do nothing
                    }
                    if (inputLine.contains("<3")) {
                        inputLine = inputLine.replace("<3", "");
                    } else {
                        //do nothing
                    }
                    writer.write(inputLine + "\n");
                } else {
                    //do nothing
                }

                if (isFoundKeyWord && inputLine.contains("</nav>")) {
                    break;
                } else {
                    //do nothing
                }
            }//end while
            writer.write("</root>");
            in.close();
            writer.close();
            int lastestNumber = getLastestPage(filePath, null, null);
            // i have to get 1k record, so i choose 64 pages, real number is 122
            getListJobFromIviettech(filePath);
            String realPath = srvlContext.getRealPath("/");
            String filePathFour = realPath + Page.filePathFour;
            if (startIndex == 64) {
                System.out.println("Xong Job");
                return;
            }
            startIndex++;
            crawlHTMLJob(filePathFour, "http://iviettech.vn/category/viec-lam/page/" + String.valueOf(startIndex), startIndex);
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        }
    }

    private void getListJobFromIviettech(String filePath) {
        XMLInputFactory fact = XMLInputFactory.newInstance();
        fact.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        fact.setProperty(XMLInputFactory.IS_VALIDATING, false);
        //Declare variable
        if (listJob == null) {
            listJob = new ArrayList<>();
        }
        XMLEventReader xmlEventReader = null;
        Job job = null;
        boolean isFoundMainLi = false;
        boolean isEnd = false;
        boolean check = false;
        try {
            xmlEventReader = fact.createXMLEventReader(
                    new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("li") && !isFoundMainLi) {
                        Attribute attrClass = startElement.getAttributeByName(
                                new QName("class"));
                        if (attrClass != null) {
                            if (attrClass.getValue().trim().equalsIgnoreCase(
                                    "cls-item cls-has-bottom-border")) {
                                job = new Job();
                                isFoundMainLi = true;
                            } else {
                                //do nothing
                            }
                        } else {
                            //do nothing
                        }
                    } else if ("a".equalsIgnoreCase(qName) && isFoundMainLi) {
                        Attribute attrHref = startElement.getAttributeByName(
                                new QName("href"));
                        if (attrHref != null) {
                            job.setLink(attrHref.getValue().trim());
                        } else {
                            //do nothing
                        }
                    } else if ("span".equalsIgnoreCase(qName) && isFoundMainLi) {
                        xmlEventReader.nextEvent();
                        xmlEventReader.nextTag();
                        XMLEvent eventTemp = xmlEventReader.nextEvent();
                        if (eventTemp.isCharacters()) {
                            Characters cpnChars = eventTemp.asCharacters();
                            job.setCompanyName(cpnChars.getData().trim());
                        } else {
                            //do nothing
                        }
                    } else if ("strong".equalsIgnoreCase(qName) && isFoundMainLi) {
                        xmlEventReader.nextTag();
                        String jobTitle = xmlEventReader.getElementText().trim();
                        job.setTitle(jobTitle);
                    } else if ("i".equalsIgnoreCase(qName) && isFoundMainLi) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null) {
                            if (attrClass.getValue().trim().equalsIgnoreCase("iconmuch-date")) {
                                xmlEventReader.next();
                                xmlEventReader.nextTag();
                                XMLEvent eventTemp = xmlEventReader.nextEvent();
                                if (eventTemp.isCharacters()) {
                                    Characters datePub = eventTemp.asCharacters();
                                    String dateStr = datePub.getData().trim();
                                    String dateStrFil = dateStr.substring(dateStr.indexOf(",") + 1, dateStr.length()).trim();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = sdf.parse(dateStrFil);
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");                                    
                                    job.setPubDate("Ngày " + sdf2.format(date));
                                }
                            }
                        }
                    } else if ("img".equalsIgnoreCase(qName)) {
                        Attribute attrSrc = startElement.getAttributeByName(new QName("src"));
                        if (attrSrc != null) {
                            job.setThumbnail(attrSrc.getValue().trim());
                        }
                    } else if ("p".equalsIgnoreCase(qName)) {
                        Attribute attrClass = startElement.getAttributeByName(new QName("class"));
                        if (attrClass != null) {
                            if (attrClass.getValue().trim().equalsIgnoreCase("cls-description")) {
                                job.setSummary(xmlEventReader.getElementText());
                                isEnd = true;
                            } else {
                                //do nothing
                            }
                        } else {
                            //do nothing
                        }
                    }
                } //end if start element 
                else {
                    //do nothing
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("li") && isEnd) {
                        job.setId(listJob.size());
                        check = getJobDetailForIviettech(job);
                        if (check) {
                            listJob.add(job);
                        } else {
                            //do nothing
                        }
                        isFoundMainLi = false;
                        isEnd = false;
                    }
                }//end if endElement
                else {
                    //do nothing
                }
            }//end while            
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        }
    }

    private boolean getJobDetailForIviettech(Job job) {
//        Writer writer = null;
        boolean checkAddSucOrNot = false;
        int id = -1;
        String inputLine = "";
        String content = "";
        XMLStreamReader reader = null;
        BufferedReader in = null;
        InputStream is = null;
        try {
            URL url = new URL(job.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            in = getGeneralBufferedReader(job.getLink());
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<div class=\"wd-detail-article\">")) {
                        isFoundKeyWord = true;
                    } else {
                        //do nothing
                    }
                }

                if (isFoundKeyWord == true && inputLine.contains("<div id=\"fb-root\"")) {
                    break;
                } else {
                    //do nothing
                }

                if (isFoundKeyWord) {
                    if (inputLine.contains("&")) {
                        inputLine = StringEscapeUtils.unescapeHtml4(inputLine).trim();
                        inputLine = inputLine.replace("&", "");
                    } else {
                        //do nothing
                    }
                    if (inputLine.contains("<div class=\"fb-like\"")) {
                        continue;
                    }
//                    writer.write(inputLine + "\n");
                    content += inputLine;
                } else {
                    //do nothing
                }
            }//end while
            in.close();
//            writer.close();
            job.setDescription(content);
            //save to db
            String realPath = srvlContext.getRealPath("/");
            String jobPath = realPath + Page.filePathJobSch;
            boolean isValidJob = Utilities.validateXMLBeforeSaveToDatabase(
                    Utilities.marshallerToString(job), jobPath);
            if (isValidJob) {
                boolean fullInFo = checkFullInfo(job);
                if (fullInFo) {
                    id = JobDAO.addJob(job);
                    checkAddSucOrNot = id > 0 ? true : false;
                } else {
                    //do nothing
                }
            } else {
                //do nothing
            }
        } catch (Exception e) {
            log(CrawlData.class
                    .getName() + " Exception: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    try {
                        reader.close();

                    } catch (XMLStreamException ex) {
                        log(CrawlData.class
                                .getName() + " XMLStreamException: " + ex.getMessage());
                    }
                }
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();

                }
            } catch (IOException ex) {
                log(CrawlData.class
                        .getName() + " IOException: " + ex.getMessage());
            }
        }
        return checkAddSucOrNot;
    }

    private boolean checkFullInfo(Job job) {
        boolean check = true;
        if (job.getCompanyName().length() == 0) {
            return check = false;
        }
        if (job.getDescription().length() == 0) {
            return check = false;
        }
        if (job.getLink().length() == 0) {
            return check = false;
        }
        if (job.getPubDate().length() == 0) {
            return check = false;
        }
        if (job.getSummary().length() == 0) {
            return check = false;
        }
        if (job.getThumbnail().length() == 0) {
            return check = false;
        }
        if (job.getTitle().length() == 0) {
            return check = false;
        }
        return check;
    }
}
