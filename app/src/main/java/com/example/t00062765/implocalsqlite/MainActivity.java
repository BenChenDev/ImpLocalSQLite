package com.example.t00062765.implocalsqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    EditText firstName, lastName, marks;
    Button create_btn, readAll_btn, update_btn, delete_btn;
    String fn,ln,mk;
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();

        firstName = (EditText)findViewById(R.id.editFirstName);
        lastName = (EditText)findViewById(R.id.editLastName);
        marks = (EditText)findViewById(R.id.editMarks);

        create_btn = (Button)findViewById(R.id.createBtn);
        create_btn.setOnClickListener(this);
        readAll_btn = (Button)findViewById(R.id.readAllBtn);
        readAll_btn.setOnClickListener(this);
        update_btn = (Button)findViewById(R.id.updateBtn);
        delete_btn = (Button)findViewById(R.id.deleteBtn);
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


        long newId = myDb.insertRow("Jenny", "Jenny", 100);

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
                String firstname = cursor.getString(DBAdapter.COL_FIRSTNAME);
                String lastname = cursor.getString(DBAdapter.COL_LASTNAME);
                int marks = cursor.getInt(DBAdapter.COL_MARKS);

                // Append data to the message:
                message += "id=" + id
                        +", first name=" + firstname
                        +", last name=" + lastname
                        +", marks=" + marks
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
        Log.d("Test", message);
    }

    //onclick()
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.createBtn:
                fn = firstName.getText().toString();
                ln = lastName.getText().toString();
                mk = marks.getText().toString();

                if(fn.isEmpty() || ln.isEmpty() || mk.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please make sure all fields are filled!",Toast.LENGTH_LONG).show();
                } else {
                    Long id = myDb.insertRow(fn, ln, Integer.valueOf(mk));
                    Toast.makeText(getApplicationContext(),"Command Sent! .. ID = " + id,Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
