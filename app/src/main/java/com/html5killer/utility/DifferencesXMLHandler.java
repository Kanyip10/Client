package com.html5killer.utility;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class DifferencesXMLHandler extends DefaultHandler {
    boolean currentElement;
    String currentValue;
    DifferencesInfo diffInfo;
    ArrayList<DifferencesInfo> diffList;
    DifferencePoint diffPoint;

    public DifferencesXMLHandler() {
        this.currentElement = false;
        this.currentValue = "";
    }

    public ArrayList<DifferencesInfo> getDiffList() {
        return this.diffList;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.currentElement = true;
        if (qName.equals("stages")) {
            this.diffList = new ArrayList();
        } else if (qName.equals("stage")) {
            this.diffInfo = new DifferencesInfo();
        } else if (qName.equals("difference")) {
            this.diffPoint = new DifferencePoint();
            this.diffPoint.setPosX(attributes.getValue("x"));
            this.diffPoint.setPosY(attributes.getValue("y"));
            this.diffPoint.setRadius(attributes.getValue("radius"));
            this.diffInfo.addPoint(this.diffPoint);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentElement = false;
        if (qName.equalsIgnoreCase("image1")) {
            this.diffInfo.setImageLocation1(this.currentValue.trim());
        } else if (qName.equalsIgnoreCase("image2")) {
            this.diffInfo.setImageLocation2(this.currentValue.trim());
        } else if (qName.equalsIgnoreCase("stage")) {
            this.diffList.add(this.diffInfo);
        }
        this.currentValue = "";
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.currentElement) {
            this.currentValue += new String(ch, start, length);
        }
    }
}
