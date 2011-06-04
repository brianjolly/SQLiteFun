package net.creativelift.sqlitefun;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FunDBAdapter {
	
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_FUN_LEVEL = "fun_level";
	public static final String KEY_CREATION_DATE = "creation_date";
	public static final String KEY_MODIFICATION_DATE = "modification_date";
	
	private static final String DATABASE_TABLE = "fun_table";
	private static final String TRIGGER_DATABASE_TABLE = "trigg_table";
	
	private Context context;
	private SQLiteDatabase database;
	private FunOpenHelper dbHelper;
	
	public FunDBAdapter(Context context) {
		this.context = context;
	}
	
	public FunDBAdapter open() throws SQLException {
		dbHelper = new FunOpenHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createFunRecord(String fun_level) {
		ContentValues initialValues = createContentValues(fun_level,true);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	public long createFunRecordWitTrigger(String fun_level) {
		ContentValues initialValues = createContentValues(fun_level,false);
		return database.insert(TRIGGER_DATABASE_TABLE, null, initialValues);
	}
	
	public boolean updateFunRecord(long rowId, String fun_level) {
		ContentValues updateValues = createContentValues(fun_level,true);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID+"="+rowId, null) > 0;
	}
	public boolean updateFunRecordWitTrigger(long rowId, String fun_level) {
		ContentValues updateValues = createContentValues(fun_level,false);
		return database.update(TRIGGER_DATABASE_TABLE, updateValues, KEY_ROWID+"="+rowId, null) > 0;
	}
	
	public boolean deleteFunRecord(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID+"="+rowId, null) > 0;
	}
	
	public Cursor fetchAllFunRecords() {
		return database.query(DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_FUN_LEVEL,
				KEY_CREATION_DATE,
				KEY_MODIFICATION_DATE
		}, null, null, null, null, null);
	}
	
	public Cursor fetchFunRecord(long rowId) throws SQLException {
		return database.query(DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_FUN_LEVEL,
				KEY_CREATION_DATE,
				KEY_MODIFICATION_DATE
		}, KEY_ROWID+"="+rowId, null, null, null, null);
	}
	
	public Cursor fetchTrigTableRecord(long rowId) throws SQLException {
		return database.query(TRIGGER_DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_FUN_LEVEL,
				KEY_CREATION_DATE,
				KEY_MODIFICATION_DATE
		}, KEY_ROWID+"="+rowId, null, null, null, null);
	}
	
	private ContentValues createContentValues(String fun_level, boolean use_java_date) {
		ContentValues values = new ContentValues();
		if (use_java_date) {
			long date = getDate();
			values.put(KEY_CREATION_DATE, date);
			values.put(KEY_MODIFICATION_DATE, date);
		} else {
			values.put(KEY_FUN_LEVEL, fun_level);
		}
		return values;
	}
	
	// TODO: check to see what sqlite's default DATE is from the trigger
	private long getDate() {
        Date date = new Date();
        return date.getTime();
	}
	
}

/*
 * sqlite> select datetime('now');
     2004-10-18 23:32:34

     sqlite> select datetime('now','localtime');
     2004-10-18 19:32:46
     
     CONVERTING TO LOCAL TIME:

     sqlite> select datetime(timeEnter,'localtime') from tablename;
     
     */
