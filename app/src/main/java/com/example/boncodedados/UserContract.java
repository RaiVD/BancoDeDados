package com.example.boncodedados;

import android.provider.BaseColumns;

public class UserContract {
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_PASSWORD = "username";
        public static final String COLUMN_NAME_EMAIL = "email";
    }
}

