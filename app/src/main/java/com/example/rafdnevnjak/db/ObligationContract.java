package com.example.rafdnevnjak.db;

import android.provider.BaseColumns;

public class ObligationContract {
    public static final class ObligationEntry  implements BaseColumns {
        public static final String TABLE_NAME = "obligations";
        public static final String COLUMN_STARTTIME = "start_time";
        public static final String COLUMN_ENDTIME = "end_time";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_USERID = "userId";
        public static final String COLUMN_PRIORITY = "priority";
    }
}
