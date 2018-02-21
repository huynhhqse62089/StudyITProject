/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import Course.CourseItem;
import Course.ListCourse;
import entity.Course;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import javax.print.DocFlavor;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

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
            }
            writer.write("</root>");
            in.close();
            writer.close();
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
//        String coursename = "";
//        String timeStudy = "";
//        String openingDate = "";
//        String location = "";
//        String link = "";
//        String tuition = "";
//        String instructorName = "";
//        String lengthStudy = "";
//        String thumbnail = "";
//        String description = "";
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
                        listCourse.add(course);
                    }
                }
            }// end while

//            for (Course course1 : listCourse) {
//                System.out.println("Course Name" + course1.getCourseName());
//                System.out.println("Course Link" + course1.getLink());
//                System.out.println("Course TimeStudy" + course1.getTimeStudy());
//                System.out.println("Course LengthStudy" + course1.getLengthStudy());
//                System.out.println("Course Thumb" + course1.getThumbnail());
//                System.out.println("Course Opening" + course1.getOpeningDate());
//            }
        } catch (Exception e) {
            log(CrawlData.class.getName() + " Exception: " + e.getMessage());
        }
    }

    public Course getCourseDetailForIviettech(Course course) {
        Course resultCourse = new Course();
        String inputLine = "";
        String document = "<root>";
        
        try {
            URL url = new URL(course.getLink());
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Chrome");
            BufferedReader in = getGeneralBufferedReader(course.getLink());
            String content = "";
            while ((inputLine = in.readLine()) != null) {                
                
            }
            
        } catch (Exception e) {
        }
        return null;
    }
}
