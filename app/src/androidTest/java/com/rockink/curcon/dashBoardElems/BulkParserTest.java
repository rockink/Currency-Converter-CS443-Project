package com.rockink.curcon.dashBoardElems;

import android.support.test.runner.AndroidJUnit4;
import android.util.Xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
*/
@RunWith(AndroidJUnit4.class)
public class BulkParserTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new FileInputStream("/sdcard/ele.xml"), null);


        while (parser.next() != XmlPullParser.END_DOCUMENT){

            int event = parser.getEventType();
            //if it is a start, we need to find a tag...
            //and parse it
            if(event == XmlPullParser.START_TAG){
                String name = parser.getName();
                System.out.println(name);

                if(!name.equals("Cube")){
                    continue;
                }

                while (true) {
                    parser.next(); //to the time tag
                    readTimeCube(parser);
                    event = parser.next();
                    if(event == XmlPullParser.END_TAG) break;
                }

                break;

            }

        }

        System.out.println(dateData);

    }


    static class CurrencyHolder {
        String currency;
        String rate;

        public CurrencyHolder(String currency, String rate){
            this.currency = currency;
            this.rate = rate;
        }
    }

    HashMap<String, ArrayList<CurrencyHolder>> dateData = new HashMap<>();

    private void readTimeCube(XmlPullParser parser) throws XmlPullParserException, IOException {

        if(!parser.getName().equals("Cube")){
            throwError("Cube not defined");
        }

        ArrayList<CurrencyHolder> currencyHolders = new ArrayList<>();
        //we are starting at cube time="%s"
        parser.getAttributeCount();
        String time = parser.getAttributeName(0);
        String timeVal = parser.getAttributeValue(0);

        //now that we are done with time, lets start doing currency
        parser.next();
        currencyHolders = parseCurrenciesForDate(parser);

        dateData.put(timeVal, currencyHolders);


    }

    /**
     * Thea header right now is in
     * With the starting header of
     * @param parser
     */
    private ArrayList<CurrencyHolder> parseCurrenciesForDate(XmlPullParser parser) throws IOException, XmlPullParserException {

        ArrayList<CurrencyHolder> currencyHolders = new ArrayList<>();

        while (true){
            if(!isAttribute(parser, "time")) {
                //we read from startTag
                CurrencyHolder currencyHolder = getAtributeKeyValForCurrency(parser);
                currencyHolders.add(currencyHolder);
                int event = parser.getEventType();

                event = parser.next();

                event = parser.next();
                //if we again see the end tag, it means we are done here...
                if(event == XmlPullParser.END_TAG) break;
                else continue;
            }
            //this then breaks when we are done!!
            System.out.println("breaking ");
            break;
        }
        return currencyHolders;
    }

    private boolean isAttribute(XmlPullParser parser, String time) {
        if(parser.getAttributeCount() == -1 || parser.getAttributeCount() == 0) return false;
        if(parser.getAttributeName(0).equals(time)) return true;
        return false;
    }

    private CurrencyHolder getAtributeKeyValForCurrency(XmlPullParser parser) throws XmlPullParserException {
        //for each currency

        int event = parser.getEventType();
        if(event == XmlPullParser.START_TAG){

            String curKey = parser.getAttributeName(0);
            String curVal = parser.getAttributeValue(0);

            String rateKey = parser.getAttributeName(1);
            String rateVal = parser.getAttributeValue(1);

            return new CurrencyHolder(curVal, rateVal);


        }

        //this will never return null.. or is not support to return null
        return null;
    }

    private void throwError(String s) {
        throw new RuntimeException(s);
    }


}