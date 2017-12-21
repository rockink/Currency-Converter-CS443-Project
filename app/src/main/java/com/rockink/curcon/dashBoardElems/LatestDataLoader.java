package com.rockink.curcon.dashBoardElems;

import com.rockink.curcon.entities.LatestData;

/**
 * When data is loaded, this method is expected to be called. Attached to activities.
 *
 * */

public interface LatestDataLoader {

    public void onDataLoaded(LatestData latestData);

}

