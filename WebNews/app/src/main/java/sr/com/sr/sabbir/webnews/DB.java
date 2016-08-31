package sr.com.sr.sabbir.webnews;

import java.util.ArrayList;


import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    Context context;
    Utility utility;
    SQLiteDatabase db;
    String dbName = "halumz";
    public boolean startApp = false;
    ArrayList<String> tblNames;
    public ArrayList<ArrayList<String>> columnNames, columnTypes;
    String ColumnVars[];
    String ID = "ID";
    public String dataL = "";
    public String dbchecker = "";

    public DB(Context context) {
        this.context = context;
        utility = new Utility(context);
        initialization();

    }

    public void initialization() {

        String ColumnVars[] = { "int", "varchar(1255)", "", "DATE" };
        this.ColumnVars = ColumnVars;
        tblNames = new ArrayList<String>();
        columnNames = new ArrayList<ArrayList<String>>();
        columnTypes = new ArrayList<ArrayList<String>>();

        ArrayList<String> columnName = new ArrayList<String>();
        ArrayList<String> columnType = new ArrayList<String>();

        tblNames.add("tblLocation");
        columnName = new ArrayList<String>();
        columnType = new ArrayList<String>();
        columnName.add(ID);
        columnType.add(ColumnVars[0]);
        columnName.add("Country");
        columnType.add(ColumnVars[1]);
        columnName.add("Location");
        columnType.add(ColumnVars[1]);
        columnNames.add(columnName);
        columnTypes.add(columnType);


    }

    public void open() {
        db = context.openOrCreateDatabase(dbName, 0, null);
    }

    public void close() {
        db.close();
    }

    public void createTables() {
        open();
        // db.execSQL("DROP TABLE IF EXISTS tblupdatedDate");
        for (int i = 0; i < tblNames.size(); i++)
            createTable(i);
        IntroDatas();
        close();
    }

    public void createTable(int position) {
        String tableName = tblNames.get(position);
        ArrayList<String> columnName = columnNames.get(position);
        ArrayList<String> columnType = columnTypes.get(position);

        String Query = "CREATE TABLE  IF NOT EXISTS " + tableName + " (" + ID
                + " integer primary key autoincrement not null ";
        for (int i = 1; i < columnName.size(); i++) {
            Query += " , " + columnName.get(i) + " " + columnType.get(i);
        }
        Query += " )";
        // data+=Query+"\n";
        db.execSQL(Query);
    }

    public void deleteTblALL(String tblName) {
        open();
        db.execSQL("delete from " + tblName);
        close();
    }

    public void deleteTblALL(int position) {
        deleteTblALL(tblNames.get(position));
    }

    public int getcount(String tblName) {
        return getcount(tblName, null);
    }

    public int getcount(int position) {
        return getcount(tblNames.get(position), null);
    }

    public int getcount(String tblName, String where) {

        open();
        if (where != null)
            where = "where " + where;
        else
            where = "";
        Cursor cursor = db.rawQuery("select count(*) from " + tblName + " "
                + where, null);
        cursor.moveToFirst();
        close();
        return cursor.getInt(0);
    }

    public int getcount(int position, String where) {
        return getcount(tblNames.get(position), where);
    }

    public String WhereClause(int tblposition, ArrayList<String> Items) {
        String Where = "";
        if (Items.size() == 0)
            return "";
        String TableName = tblNames.get(tblposition);
        ArrayList<String> columnName = columnNames.get(tblposition);
        ArrayList<String> columnType = columnTypes.get(tblposition);
        for (int i = 0; i < columnName.size(); i++) {
            if (!Items.get(i).equals("@")) {
                if (columnType.equals(0)) {
                    Where += columnName.get(i) + " = " + Items.get(i) + " and";
                } else {
                    Where += columnName.get(i) + " = '" + Items.get(i)
                            + "' and";
                }
            }
        }
        Where = Where.substring(0, Where.length() - 4);
        return Where;
    }

    public Cursor selectquery(int tblposition, ArrayList<String> columns,
                              String whereClause) {
        return selectquery(tblposition, columns, whereClause, null);
    }

    public Cursor selectquery(int tblposition, ArrayList<String> columns,
                              String whereClause, String OrderBy) {

        open();
        String TableName = tblNames.get(tblposition);
        if (whereClause == "")
            whereClause = null;
        if (columns == null)
            columns = columnNames.get(tblposition);
        try {
            Cursor cursor = db.query(false, TableName,
                    utility.Arraylist2Array(columns), whereClause, null, null,
                    null, OrderBy, null);
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }

    public void insertquery(int tblposition, ArrayList<String> insertItems) {

        open();
        String TableName = tblNames.get(tblposition);
        ArrayList<String> columnName = columnNames.get(tblposition);
        ContentValues values = new ContentValues();
        for (int i = 0; i < columnName.size(); i++) {
            if (!insertItems.get(i).equals("@"))
                values.put(columnName.get(i), insertItems.get(i));
        }
        db.insert(TableName, null, values);
        close();
    }

    public void updatequery(int tblposition, ArrayList<String> UpdateItems,
                            String whereClause) {

        open();
        String TableName = tblNames.get(tblposition);
        ArrayList<String> columnName = columnNames.get(tblposition);
        ContentValues values = new ContentValues();
        for (int i = 1; i < columnName.size(); i++) {
            if (!UpdateItems.get(i).equals("@"))
                values.put(columnName.get(i), UpdateItems.get(i));
        }
        if (whereClause.equals(""))
            whereClause = null;
        db.update(TableName, values, whereClause, null);
        close();
    }

    public void Deletequery(int tblposition, String whereClause) {

        open();
        String TableName = tblNames.get(tblposition);
        ArrayList<String> columnName = columnNames.get(tblposition);
        if (whereClause.equals(""))
            whereClause = null;
        db.delete(TableName, whereClause, null);
        close();
    }

    public String WhereLikeClause(String ColumnName, String Number) {
        String whereClause = ColumnName + " LIKE '%" + Number + "%' OR " + "'"
                + Number + "' LIKE  '%" + ColumnName + "%'";
        return whereClause;
    }

    public ArrayList<ArrayList<String>> getcursordata(Cursor cursor) {
        ArrayList<ArrayList<String>> cursordata = new ArrayList<ArrayList<String>>();
        int no_columns = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            ArrayList<String> elements = new ArrayList<String>();
            for (int i = 0; i < no_columns; i++) {
                elements.add(cursor.getString(i) + "");
            }
            cursordata.add(elements);
        }

        return cursordata;
    }

    public void IntroDatas() {
        int init = getcount(0);
        if (init == 0) {

            ArrayList<String> elements = new ArrayList<String>();
            elements.add("@");
            elements.add("Bangladesh");
            elements.add("Dhaka");
            insertquery(0, elements);

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("Bangladesh");
            elements.add("Chittagong");
            insertquery(0, elements);

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("Bangladesh");
            elements.add("Rajshahi");
            insertquery(0, elements);

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("India");
            elements.add("Kolkata");
            insertquery(0, elements);

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("India");
            elements.add("Mumbai");
            insertquery(0, elements);
            

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("Pakistan");
            elements.add("Islamabad");
            insertquery(0, elements);

            elements = new ArrayList<String>();
            elements.add("@");
            elements.add("GB");
            elements.add("London");
            insertquery(0, elements);

        }
    }

    public String[] GetCountry() {
        open();
        Cursor cursor = db.rawQuery("select  DISTINCT Country from "+tblNames.get(0), null);//DISTINCT
        String Countries[] = new String[cursor.getCount()+1];
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            Countries[i+1] = cursor.getString(0);
        }
        Countries[0]="   --select your Country--   ";
        close();
        return Countries;
    }

    public String[] GetLocation(String Country) {
        open();
        Cursor cursor = db.rawQuery("select Location from "+tblNames.get(0) +" where Country='"+Country+"'", null);//DISTINCT
        String Locations[] = new String[cursor.getCount()+1];
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            Locations[i+1] = cursor.getString(0);
        }
        Locations[0]="   --select your City--   ";
        close();
        return Locations;
    }

}
