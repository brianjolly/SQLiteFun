package net.creativelift.sqlitefun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FunOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fun.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "CREATE TABLE fun_table ("
		+ "_id integer primary key autoincrement, "
		+ "fun_level TEXT );";
		
	public FunOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS fun_table");
		onCreate(db);
	}

}
