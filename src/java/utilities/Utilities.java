/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.InputStream;
import java.io.Serializable;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author yncdb
 */
public class Utilities implements Serializable {

    public static XMLStreamReader parseFileToStAXCursor(InputStream is)
            throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        return reader;
    }

    public static String getTextContext(String elementName, XMLStreamReader reader) throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals(elementName)) {
                        reader.next();
                        String result = reader.getText();
                        reader.nextTag();
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public static String getNodeValue(String elementName,
            String attName, String namespaceURI, XMLStreamReader reader) throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals(elementName)) {
                        String result = reader.getAttributeValue(namespaceURI, attName);
                        return result;
                    }
                }
            }
        }
        return null;
    }
    
}
