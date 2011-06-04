package net.creativelift.sqlitefun.test;

import net.creativelift.sqlitefun.FunDBAdapter;
import android.content.Context;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.PerformanceTestCase;
import android.util.Log;

public class FunDBPerfTest extends AndroidTestCase implements PerformanceTestCase {
	
	private FunDBAdapter dbAdapter;
	private Context testContext;
	
	private static final int ITERATIONS = 1000;
	Intermediates i;

	@Override
	public boolean isPerformanceOnly() {
		return true;
	}

	@Override
	public int startPerformance(Intermediates intermediates) {
		intermediates.setInternalIterations(ITERATIONS * 10);
		i = intermediates;
		return 1;
		//return 0;
	}
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        testContext = this.getContext();
		dbAdapter = new FunDBAdapter(testContext);
		//dbAdapter.open();
	}
	
	public void startTiming(boolean realTime) {
		if (i != null ) {
			i.finishTiming(realTime);
		}
	}
	
	public void finishTiming(boolean realTime) {
		if (i != null) {
			i.finishTiming(realTime);
		}
	}
	
	public void addIntermediate(String name) {
		if (i != null) {
			i.addIntermediate(name);
		}
	}

	public void addIntermediate(String name, long timeInNS) {
		if (i != null) {
			i.addIntermediate(name, timeInNS);
		}
	}
	
	public void testInsertWithJavaDate() {
		startTiming(true);
		long start_time = System.currentTimeMillis();
		
		dbAdapter.open();
		long new_row_id = dbAdapter.createFunRecord("specific record");
		Cursor specific_record = dbAdapter.fetchFunRecord(new_row_id);
		specific_record.moveToFirst();
		String result = specific_record.getString(1);
		assertEquals("specific record", result);
		
		dbAdapter.close();
		
		long end_time = System.currentTimeMillis();
		long total_time = end_time-start_time;
		Log.w("SQLTESTS", "time spent (java date version): "+total_time);
		finishTiming(true);
	}
	
	public void testInsertWithTriggerDate() {
		startTiming(true);
		long start_time = System.currentTimeMillis();
		
		dbAdapter.open();
		long new_row_id = dbAdapter.createFunRecordWitTrigger("record in trigger table");
		Cursor specific_record = dbAdapter.fetchTrigTableRecord(new_row_id);
		specific_record.moveToFirst();
		String result = specific_record.getString(1);
		assertEquals("record in trigger table", result);
		
		dbAdapter.close();
		
		long end_time = System.currentTimeMillis();
		long total_time = end_time-start_time;
		Log.w("SQLTESTS", "time spent (trigger version): "+total_time);
		finishTiming(true);
	}
	
}
