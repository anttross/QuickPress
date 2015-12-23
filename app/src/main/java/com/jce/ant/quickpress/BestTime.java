/*
 * *
 *  * Created by assafbt on <Date>
 *
 */

package com.jce.ant.quickpress;

import android.provider.BaseColumns;

/**
 * Created by assafbt on 22/12/2015.
 */
public class BestTime {

    public BestTime(){}

    public static  abstract  class TimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "bestTimeDB";
        public static final String LVL_CMPX = "lvl_cmpx";
        public static final String RECORD = "record";

    }
}
