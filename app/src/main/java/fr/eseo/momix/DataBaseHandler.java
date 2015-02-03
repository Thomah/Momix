package fr.eseo.momix;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by etudiant on 02/02/2015.
 */

class DatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;

        private static final String DATABASE_NAME = "dictionaryManager";

        private static final String TABLE_DICTIONARY = "dictionary";

        private static final String KEY_ID = "id";
        private static final String KEY_TEXT = "text";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DICTIONARY + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT)";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY);
            onCreate(db);
        }

    public void addWord(String word) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, word);
        db.insert(TABLE_DICTIONARY, null, values);
        db.close();
    }

    public List<Word> getDictionary() {
        List<Word> dictionary = new ArrayList<Word>();
        String selectQuery = "SELECT  * FROM " + TABLE_DICTIONARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Word w = new Word();
                w.setId(Integer.parseInt(cursor.getString(0)));
                w.setText(cursor.getString(1));
                dictionary.add(w);
            } while (cursor.moveToNext());
        }
        return dictionary;
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DICTIONARY, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
        db.close();
    }
}
