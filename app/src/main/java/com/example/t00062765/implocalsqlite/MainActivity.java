package com.example.t00062765.implocalsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }

    public void onClick_AddRecord(View v) {

        long newId = myDb.insertRow("Jenny", 5559, "Green");

        // Query for the record we just added to confirm.
        // Use the ID:
        Cursor cursor = myDb.getRow(newId);
        displayRecordSet(cursor);
        // [TO_DO_B2]
        // Confirm that data is written correctly by reading it back and comparing it with the original data then display a toast message to confirm the transaction.
        // [TO_DO_B3]
        // Update the list view
    }

    public void onClick_ClearAll(View v) {
        myDb.deleteAll();
        // [TO_DO_B4]
        // Confirm that all rows are deleted. Think about what would be the best way to do that?
        // [TO_DO_B5]
        // Update the list view
    }

    public void onClick_DisplayRecords(View v) {
        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);


    }

    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                int studentNumber = cursor.getInt(DBAdapter.COL_STUDENTNUM);
                String favColour = cursor.getString(DBAdapter.COL_FAVCOLOUR);

                // Append data to the message:
                message += "id=" + id
                        +", name=" + name
                        +", #=" + studentNumber
                        +", Colour=" + favColour
                        +"\n";

                // [TO_DO_B6]
                // Create arraylist(s)? and use it(them) in the list view
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();


        // [TO_DO_B7]
        // Update the list view

        // [TO_DO_B8]
        // Display a Toast message
    }
}
