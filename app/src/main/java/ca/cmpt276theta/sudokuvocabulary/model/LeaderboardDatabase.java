package ca.cmpt276theta.sudokuvocabulary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class LeaderboardDatabase {
    private static SQLiteDatabase db = null;
    public static void init(Context context)
    {
        db = context.openOrCreateDatabase("LeaderboardDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Leaderboard(Name TEXT, Time LONG)");
    }
    public static void clear()
    {
        if(db != null)
            db.execSQL("DELETE FROM Leaderboard");
    }
    public static void insert(String name, Long time)
    {
        if(db != null) {
            String query = "INSERT INTO Leaderboard VALUES(?,?)";
            ContentValues values = new ContentValues();
            values.put("Name", name);
            values.put("Time", time);
            db.insert("Leaderboard", null, values);
            //db.rawQuery(query, new String[]{name, String.valueOf(time)}).close();
            System.out.println("Added into database: " + name + "   " + time);
        }
    }
    public static ArrayList<Object[]> getLeaderboard()
    {
        if(db != null) {
            ArrayList<Object[]> objList = new ArrayList<Object[]>();
            Cursor cursor = db.rawQuery("SELECT * FROM Leaderboard ORDER BY Time", new String[]{});
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String name = cursor.getString(0);
                Long i = cursor.getLong(1);
                objList.add(new Object[]{name, i});
                cursor.moveToNext();
            }
            cursor.close();
            return objList;
        }
        return null;
    }
}
