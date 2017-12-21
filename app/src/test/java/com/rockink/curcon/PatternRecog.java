package com.rockink.curcon;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
*/

public class PatternRecog {

    @Test
    public void recognizeCube(){
        char[] s= "<Cube time=\"2017-12-12\"><Cube currency=\"USD\" rate=\"1.1766\"/>".toCharArray();


        int i = 0;
        while (true){
            char ch = s[i];
            if(ch == 'C') {
                if(s[i + 1] == 'u'){
                    //Cu...
                    //loop until we get attribute
                    while (s[i++] != ' ');

                    String keyAttribute, valueAttrib;
                    String keyAttrib = findAttribs(s, i);

                }

            }

        }


    }

    /**
     * Starting from index i, find attributes
     * @param s
     * @param i
     * @return
     */

    private String findAttribs(char[] s, int i) {

        //from index i, it is time=123 />

        StringBuilder keySb = new StringBuilder();
        StringBuilder valSb = new StringBuilder();

        while (true){
            char ch = s[i];

            //example time="12-45-67"
            //find key
            while (ch != '='){
                ch = s[i];
                keySb.append(ch);
                i++;
            }

            //key found,
            i++; //by pass =

            //find value
            int count = 0;
            while (count < 2){
                ch = s[i];

                //two of the " ends the value
                if (ch == '\"'){
                    count++;
                    i++;
                    continue;
                }



                i++;
            }

        }

    }
}
