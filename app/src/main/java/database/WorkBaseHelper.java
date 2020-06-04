package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "workBase.db";

    public WorkBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + WorkDbSchema.WorkTable.NAME_BASE + "(" +
                "_id integer primary key autoincrement, " +
                WorkDbSchema.WorkTable.Cols.UUID + "," +
                WorkDbSchema.WorkTable.Cols.FIO + "," +
                WorkDbSchema.WorkTable.Cols.NUMBER + "," +
                WorkDbSchema.WorkTable.Cols.HOUR + "," +
                WorkDbSchema.WorkTable.Cols.RATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
