package com.hustaty.android.alergia.util;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class LogUtil {
	
	private static final String logFileLocation = Environment.getExternalStorageDirectory() +"/alergia.log";
	
	private static final Uri logFileUri = Uri.fromFile(new File(logFileLocation));
	
	private static void checkFileExists() {
		File logFile = new File(logFileLocation);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				Log.e(LOG_TAG+":LogUtil:#checkFileExists()", e.getMessage());
			}
		} 
	}
	
	public static void appendLog(String text) {
		File logFile = new File(logFileLocation);
		
		checkFileExists();
		
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
			buf.append((new Date()) + " : " + text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			Log.e(LOG_TAG+":LogUtil:#appendLog()", e.getMessage());
		}
	}
	
	public static String readLog() {
		String result = "";
		File logFile = new File(logFileLocation);
		checkFileExists();
		try {
			BufferedReader buf = new BufferedReader(new FileReader(logFile));
			String line;
			while((line = buf.readLine()) != null) {
				result += line + "\n";
			}
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG+":LogUtil:#readLog()", e.getMessage());
		} catch (IOException e) {
			Log.e(LOG_TAG+":LogUtil:#readLog()", e.getMessage());
		} 
		return result;
	}

	public static void clearLog() {
		File logFile = new File(logFileLocation);
		logFile.delete();
		checkFileExists();
	}
	
	public static void checkFileTooOldOrLong() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		
		File logFile = new File(logFileLocation);

		Date lastModified = new Date(logFile.lastModified());
		
		if(lastModified.before(cal.getTime())) {
			clearLog();
		}
	}

	public static String getLogfilelocation() {
		return LogUtil.logFileLocation;
	}

	public static Uri getLogFileUri() {
		return LogUtil.logFileUri;
	}
	
	
}
