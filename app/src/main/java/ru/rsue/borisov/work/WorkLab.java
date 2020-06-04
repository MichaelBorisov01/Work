package ru.rsue.borisov.work;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.WorkBaseHelper;
import database.WorkCursorWrapper;
import database.WorkDbSchema;

class WorkLab {
    private static WorkLab sWorkLab;
    private SQLiteDatabase mDatabase;

    static WorkLab get(Context context) {
        if (sWorkLab == null) sWorkLab = new WorkLab(context);
        return sWorkLab;
    }

    private WorkLab(Context context) {
        Context context1 = context.getApplicationContext();
        mDatabase = new WorkBaseHelper(context1).getWritableDatabase();

    }

    void addWorker(Work work) {
        ContentValues values = getContentValues(work);
        mDatabase.insert(WorkDbSchema.WorkTable.NAME_BASE, null, values);
    }

    void deleteWorker(Work work) {
        String uuidString = work.getId().toString();
        mDatabase.delete(WorkDbSchema.WorkTable.NAME_BASE, WorkDbSchema.WorkTable.Cols.UUID + "= ?", new String[]{uuidString});
    }

    void updateWork(Work work) {
        String uuidString = work.getId().toString();
        ContentValues values = getContentValues(work);
        mDatabase.update(WorkDbSchema.WorkTable.NAME_BASE, values, WorkDbSchema.WorkTable.Cols.UUID + "= ?", new String[]{
                uuidString
        });
    }

    private WorkCursorWrapper queryWorks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                WorkDbSchema.WorkTable.NAME_BASE,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new WorkCursorWrapper(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    List<Work> getWorks() {
        List<Work> works = new ArrayList<>();
        try (WorkCursorWrapper cursor = queryWorks(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                works.add(cursor.getWork());
                cursor.moveToNext();
            }
        }
        return works;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    Work getWork(UUID id) {
        try (WorkCursorWrapper cursor = queryWorks(
                WorkDbSchema.WorkTable.Cols.UUID + "= ?",
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWork();
        }
    }

    private static ContentValues getContentValues(Work work) {
        ContentValues values = new ContentValues();
        values.put(WorkDbSchema.WorkTable.Cols.UUID, work.getId().toString());
        values.put(WorkDbSchema.WorkTable.Cols.FIO, work.getFio());
        values.put(WorkDbSchema.WorkTable.Cols.NUMBER, work.getNumber());
        values.put(WorkDbSchema.WorkTable.Cols.HOUR, work.getHour());
        values.put(WorkDbSchema.WorkTable.Cols.RATE, work.getRate());
        return values;
    }
}
