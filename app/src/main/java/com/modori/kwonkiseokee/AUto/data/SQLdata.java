package com.modori.kwonkiseokee.AUto.data;

import android.provider.BaseColumns;

public final class SQLdata {
    public static final class createDB implements BaseColumns {

//        - DatabaseHelper(): 생성자입니다.
//        - onCreate(): 데이터베이스의 테이블을 생성합니다. 테이블 구성 시 다른 테이블 명칭을 추가하여 작성하면 하나의 데이터베이스에서 여러 테이블도 생성 가능합니다.
//                - onUpgrade(): 버전 업그레이드 시 사용합니다. 이전 버전을 지우고 새 버전을 생성합니다.
//                - open(): 해당 데이터베이스를 열어서 사용할 수 있도록 해줍니다. getWritableDatabase()는 데이터베이스를 읽고 쓸 수 있도록 해줍니다.
// - close(): 해당 데이터베이스를 닫습니다. 사용 중에는 매번 열고 닫지 않아도 되지만 모두 사용한 후에는 가급적이면 닫아주는 것이 좋습니다.
        public static final String photoID = "id";
        public static final String photoURL = "url";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + photoID + " text not null , "
                + photoURL + " text not null ); ";


    }
}
