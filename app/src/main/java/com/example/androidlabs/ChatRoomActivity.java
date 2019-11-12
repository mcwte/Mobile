package com.example.androidlabs;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ChatRoomActivity extends AppCompatActivity {

    public static EditText editText;
    public static ChatAdapter chatListAdapter;
    protected SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ListView listView = findViewById(R.id.listview);
        //get the database:
        MyDatabase opener = new MyDatabase(this);
        db =  opener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabase.COL_ID, MyDatabase.COL_MESSAGE, MyDatabase.COL_IS_SENDER};
        Cursor results = db.query(false, MyDatabase.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results);

        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyDatabase.COL_MESSAGE);
        int isSenderColIndex = results.getColumnIndex(MyDatabase.COL_IS_SENDER);
        //int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        ArrayList<Message> messageList = new ArrayList<>();
        while(results.moveToNext())
        {
            Log.d("isSender",Integer.toString(results.getInt(isSenderColIndex)));
            String message = results.getString(messageColumnIndex);
            Boolean isSender = results.getInt(isSenderColIndex) == 1;
            //long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            Message newMessage = new Message(isSender, message);
            messageList.add(newMessage);
        }

        chatListAdapter = new ChatAdapter(this, messageList);
        listView.setAdapter(chatListAdapter);

    }

    public void printCursor( Cursor c){
        Log.d("DATABASE_VERSION",Integer.toString(db.getVersion()));
        Log.d("COLUMNS",Integer.toString(c.getColumnCount()));
        for(String columnName : c.getColumnNames()){
            Log.d("COLUMN_NAMES",columnName);
        }

        Log.d("NUM_RESULTS",Integer.toString(c.getCount()));

        //find the column indices:
        int messageColumnIndex = c.getColumnIndex(MyDatabase.COL_MESSAGE);
        int isSenderColIndex = c.getColumnIndex(MyDatabase.COL_IS_SENDER);

        while(c.moveToNext())
        {
            //long id = results.getLong(idColIndex);
            Log.d("message",c.getString(messageColumnIndex));
            Log.d("isSender",Integer.toString(c.getInt(isSenderColIndex)));
        }
        c.moveToFirst();
    }
    public void receiveMessage(View view){
        editText = findViewById(R.id.message);
        String message = editText.getText().toString();

        //add to the database and get the new ID
        ContentValues newRowValues = new ContentValues();
        //put is sender bool in the IS_SENDER column:
        newRowValues.put(MyDatabase.COL_IS_SENDER, 0);
        //put string email in the EMAIL column:
        newRowValues.put(MyDatabase.COL_MESSAGE, message);
        //insert in the database:
        db.insert(MyDatabase.TABLE_NAME, null, newRowValues);

        chatListAdapter.getArray().add(new Message(false, message));
        chatListAdapter.notifyDataSetChanged();
        editText.setText(new String(""));

    }

    public void sendMessage(View view){
        editText = findViewById(R.id.message);
        String message = editText.getText().toString();

        //add to the database and get the new ID
        ContentValues newRowValues = new ContentValues();
        //put is sender bool in the IS_SENDER column:
        newRowValues.put(MyDatabase.COL_IS_SENDER, 1);
        //put string email in the EMAIL column:
        newRowValues.put(MyDatabase.COL_MESSAGE, message);
        //insert in the database:
        db.insert(MyDatabase.TABLE_NAME, null, newRowValues);

        chatListAdapter.getArray().add(new Message(true, message));
        chatListAdapter.notifyDataSetChanged();
        editText.setText(new String(""));

    }


}