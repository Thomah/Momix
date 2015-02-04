package fr.eseo.momix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static boolean dicoPerso = true;

    private static final String TABLE_DICTIONNAIRE_PERSO = "dictionnairePerso";
    private static final String TABLE_DICTIONNAIRE_PREDEFINI = "dictionnairePredefini";

    private String TABLE_DICTIONNAIRE_EN_COURS = "dictionnairePerso";
    private static final String DATABASE_NAME = "dictionaryManager";

    private static final String TABLE_DICTIONARY = "dictionary";

    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_DICTIONNAIRE_PERSO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT)";
        db.execSQL(CREATE_WORDS_TABLE);

        String CREATE_WORDS_TABLE2 = "CREATE TABLE " + TABLE_DICTIONNAIRE_PREDEFINI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEXT + " TEXT)";
        db.execSQL(CREATE_WORDS_TABLE2);
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(0,'Sauter à pieds joins')");
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(1,'Jouer des maracasses')");
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(2,'Chanter à capella')");
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(3,'Avaler de travers')");
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(4,'Mettre son clignotant')");
        db.execSQL("INSERT INTO " + TABLE_DICTIONNAIRE_PREDEFINI + " ("+KEY_ID + "," + KEY_TEXT +") values(5,'Allumer la lumière')");

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
        db.insert(TABLE_DICTIONNAIRE_PERSO, null, values);
        db.close();
    }

    public List<Word> getDictionary() {
        selectDictionary();
        List<Word> dictionary = new ArrayList<Word>();
        String selectQuery = "SELECT  * FROM " + TABLE_DICTIONNAIRE_EN_COURS + " ORDER BY " + KEY_TEXT;
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

    public Word getWord(int id) {
        selectDictionary();
        List<Word> dictionary = new ArrayList<Word>();
        String selectQuery = "SELECT  * FROM " + TABLE_DICTIONNAIRE_EN_COURS + " WHERE " + KEY_ID + "=" + id + " ORDER BY " + KEY_TEXT;
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
        return dictionary.get(0);
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DICTIONNAIRE_PERSO, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
        db.close();
    }

    public void selectDictionary(){
        if(dicoPerso){
            TABLE_DICTIONNAIRE_EN_COURS = TABLE_DICTIONNAIRE_PERSO;
        }else{
            TABLE_DICTIONNAIRE_EN_COURS = TABLE_DICTIONNAIRE_PREDEFINI;
        }
    }


    public void setDicoPerso(boolean dicoPerso) {
        this.dicoPerso = dicoPerso;
    }

}
