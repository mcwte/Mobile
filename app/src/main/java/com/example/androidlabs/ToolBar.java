package com.example.androidlabs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;

public class ToolBar extends AppCompatActivity {
    String message = "This is the initial message";
    Toolbar tBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_activity);
        tBar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.toast_main_setting:

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.edit_main_setting:
                editMessage();
                break;
            case R.id.snack_main_setting:
                Snackbar sb = Snackbar.make(tBar, "Clicking go back will exit", Snackbar.LENGTH_LONG)
                        .setAction("Go Back?", e -> this.finish());
                sb.show();
                break;
            case R.id.overflow_setting:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void editMessage()
    {
        View middle = getLayoutInflater().inflate(R.layout.newmessage_layout, null);
//        Button btn = (Button)middle.findViewById(R.id.view_button);
        EditText et = middle.findViewById(R.id.new_message);
//        btn.setOnClickListener( clk -> et.setText("You clicked my button!"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Change message")
                .setPositiveButton("Positive", (dialog, id) -> {
                    // What to do on Accept
                    message = et.getText().toString();
                }).setNegativeButton("Negative", (dialog, id) -> {
            // What to do on Accept --> NOTHING!
        }).setView(middle);

        builder.create().show();
    }
}

