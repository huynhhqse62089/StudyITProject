/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import Course.CourseItem;
import Course.ListCourse;
import dao.CourseDAO;
import entity.Course;
import entity.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
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
import java.util.ArrayList;
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
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import page.Page;

/**
 *
 * @author yncdb
 */
public class CrawlData {

    ServletContext srvlContext;

    List<Course> listCourse;

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
            boolean isFoundEndKeyWord = false;
            boolean isFoundLiEleRedundancy = false;
            writer.write("<root>");
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (inputLine.contains("</ol>")) {
                    isFoundLiEleRedundancy = false;
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
                        }
                    }
                    if (isFoundKeyWord == true && inputLine.contains("</li>")) {
                        isFoundEndKeyWord = true;
                    }

                    if (isFoundKeyWord) {
                        if (isFoundLiEleRedundancy == true) {
                            isFoundLiEleRedundancy = false;
                            continue;
                        }
                        writer.write(inputLine + "\n");
                    }

                    if (isFoundEndKeyWord) {
                        isFoundKeyWord = false;
                        isFoundEndKeyWord = false;
                    }
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
            boolean isFoundEndKeyWord = false;
            while ((inputLine = in.readLine()) != null) {
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<ul id=\"w0\" class=\"k-box-card-list\">")) {
                        isFoundKeyWord = true;
                    }
                }
                if (isFoundKeyWord == true && inputLine.contains("<nav id=\"pager-container\">")) {
                    isFoundEndKeyWord = true;
                }

                if (isFoundEndKeyWord) {
                    break;
                }

                if (isFoundKeyWord) {
                    writer.write(inputLine + "\n");
                }
            }
            in.close();
            writer.close();
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
                        listCourse.add(course);
                    }
                }
            }// end while
            for (Course cour : listCourse) {
                getCourseDetailForIviettech(cour, filePath);
            }
        } catch (Exception e) {
            log(CrawlData.class.getName() + " Exception: " + e.getMessage());
        }
    }

    private void getCourseDetailForIviettech(Course course, String filePath) {
//        Writer writer = null;
        String inputLine = "";
        String content = "";
        XMLStreamReader reader = null;
        BufferedReader in = null;
        InputStream is = null;
        List<Image> listImage = null;
        Image image = null;

        try {
            URL url = new URL(course.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            in = getGeneralBufferedReader(course.getLink());
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(filePath), "UTF-8"));
            boolean isFoundKeyWord = false;
            boolean isFoundEndKeyWord = false;
            boolean startTuition = false;
            boolean startRemoveTable = false;
            boolean startLinkImage = false;
            boolean specialCase = false;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();
                if (isFoundKeyWord == false) {
                    if (inputLine.contains("<div class=\"wd-detail-article\">")) {
                        isFoundKeyWord = true;
                    }
                }

                if (isFoundKeyWord == true && inputLine.contains("class=\"row-fluid vt-question\"")) {
                    isFoundEndKeyWord = true;
                }

                if (isFoundEndKeyWord == true) {
                    break;
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
            String removeImage = content.substring(content.indexOf("<imagelink href=\"http://iviettech.vn/dang-ky\">"),
                    content.indexOf("</imagelink>") + 12);
            content = content.replace(removeText, "");
            content = content.replace(removeImage, "");
            course.setDescription(content);
            //get Images,tuition and instructor name
            course.setInstructorName("Giảng viên là các chuyên gia trong ngành"
                    + " đang làm việc tại các công ty phần mềm lớn tại thành phố"
                    + " Đà Nẵng như Axon, FSOFT, Evizi, Enclave, Logigear…");
            course.setLocation("Địa chỉ : 92 Quang Trung, Q. Hải Châu, "
                    + "TP. Đà Nẵng - Điện thoại : 02363 888 279");
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
                }
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
                CourseDAO.addCourse(course);
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
        try {
            xmlEventReader = fact.createXMLEventReader(
                    new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();

                if (event.isStartElement()) {

                }//end if startElement
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("li")) {
                        course.setId(listCourse.size());
                        listCourse.add(course);
                    }
                }//end if endElement
            }// end while
        } catch (Exception ex) {
            log(CrawlData.class.getName() + " Exception: " + ex.getMessage());
        }
    }
}
