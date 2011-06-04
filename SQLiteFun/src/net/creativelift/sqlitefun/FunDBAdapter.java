package net.creativelift.sqlitefun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FunDBAdapter {
	
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_FUN_LEVEL = "fun_level";
	
	private static final String DATABASE_TABLE = "fun_table";
	
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
		ContentValues initialValues = createContentValues(fun_level);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public boolean updateFunRecord(long rowId, String fun_level) {
		ContentValues updateValues = createContentValues(fun_level);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID+"="+rowId, null) > 0;
	}
	
	public boolean deleteFunRecord(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID+"="+rowId, null) > 0;
	}
	
	public Cursor fetchAllFunRecords() {
		return database.query(DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_FUN_LEVEL
		}, null, null, null, null, null);
	}
	
	public Cursor fetchFunRecord(long rowId) throws SQLException {
		return database.query(DATABASE_TABLE, new String[] {
				KEY_ROWID,
				KEY_FUN_LEVEL
		}, KEY_ROWID+"="+rowId, null, null, null, null);
	}
	
	private ContentValues createContentValues(String fun_level) {
		ContentValues values = new ContentValues();
		values.put(KEY_FUN_LEVEL, fun_level);
		return values;
	}
	
}
