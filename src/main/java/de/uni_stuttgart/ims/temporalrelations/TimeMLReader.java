package de.uni_stuttgart.ims.temporalrelations;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by julia on 21.12.15.
 */
public class TimeMLReader extends DefaultHandler {

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.err.println("start qname: "+qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.err.println("end qname: "+qName);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        System.err.println("chars: "+new String(ch, start, length));
    }

}
