package com.rockink.curcon.urlRequests;

/**
 *
*/

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(){
        super("Data wasn't found.. It means something is wrong with data.. Presentation " +
                "sides are good.");
    }
}
