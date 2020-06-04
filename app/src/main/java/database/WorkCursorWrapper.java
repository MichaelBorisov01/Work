package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import ru.rsue.borisov.work.Work;

public class WorkCursorWrapper extends CursorWrapper {
    public WorkCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Work getWork() {
        String uuidString = getString(getColumnIndex(WorkDbSchema.WorkTable.Cols.UUID));
        String fio = getString(getColumnIndex(WorkDbSchema.WorkTable.Cols.FIO));
        String number = getString(getColumnIndex(WorkDbSchema.WorkTable.Cols.NUMBER));
        String hour = getString(getColumnIndex(WorkDbSchema.WorkTable.Cols.HOUR));
        String rate = getString(getColumnIndex(WorkDbSchema.WorkTable.Cols.RATE));


        Work work = new Work(UUID.fromString(uuidString));
        work.setFio(fio);
        work.setNumber(number);
        work.setHour(hour);
        work.setRate(rate);
        return work;
    }
}
