package net.creativelift.sqlitefun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FunOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fun.db";
	private static final int DATABASE_VERSION = 3;
	
	private static final String DATABASE_CREATE = ""
		+ "CREATE TABLE fun_table ( _id integer primary key autoincrement, fun_level TEXT, creation_date DATE, modification_date DATE );"
		+ "CREATE TABLE trigg_table ( _id integer primary key autoincrement, fun_level TEXT, creation_date DATE, modification_date DATE );"
		+ "CREATE TRIGGER insert_trigg_table_date_created AFTER INSERT ON trigg_table"
		+ "  BEGIN"
		+ "		trigg_table SET creation_date = DATETIME('NOW') WHERE rowid = new.rowid;"
		+ "  END;"
		+ "CREATE TRIGGER update_trigg_table_date_updated AFTER UPDATE ON trigg_table"
		+ "  BEGIN"
		+ "		trigg_table SET modified_date = DATETIME('NOW') WHERE rowid = new.rowid;"
		+ "  END;";
		
	public FunOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS fun_table; DROP TABLE IF EXISTS trigg_table;");
		onCreate(db);
	}

}

