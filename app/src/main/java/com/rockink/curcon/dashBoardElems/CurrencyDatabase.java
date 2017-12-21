package com.rockink.curcon.dashBoardElems;

import com.rockink.curcon.entities.BulkData;

/**
 *
*/

public class CurrencyDatabase {

    BulkData bulkData;

    static CurrencyDatabase currencyDatabase;


    public CurrencyDatabase getInstance(){
        if(currencyDatabase == null)
            currencyDatabase = new CurrencyDatabase();

        return currencyDatabase;
    }

    //gotta send out the data of the currency
    public static void getDataOfCurrency(String currency){

    }

    public void parseCurrency(){
//        BulkDataLoader bulkDataLoader = new BulkDataLoader() {
//            @Override
//            public void onBulkDataLoaded(ArrayList<BulkData> latestData) {
//                bulkData = latestData;
//            }
//        };

//        BulkDataRequest bulkDataRequest = new BulkDataRequest(
//                "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml",
//                bulkDataLoader
//        );

    }

}
