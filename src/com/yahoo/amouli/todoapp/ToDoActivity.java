package com.yahoo.amouli.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends Activity {
	private ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	EditText etNewItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do); 
        
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        
        items = new ArrayList<String>();
        // Read the array into the "items" arrayList.
        readItems();
        
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        
        registerLVListener();
        
    }
    
    private void readItems() {
    	File fileDir = this.getFilesDir();
    	File todoFile = new File(fileDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}
    	catch (IOException e) {
    		items = new ArrayList<String>();
    		
    	}
    	
    }
    
    private void writeItems() {
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, "todo.txt");
    	try {
    		// System.out.println("writeItems called");
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    
    private void registerLVListener() {
    	// Registers a handler for Long Click on a item.

    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long unused) {
    			items.remove(pos);
    			itemsAdapter.notifyDataSetChanged();
    			writeItems();
    			return true;
    		}
    		
    	});
    	
    }

    public void addItem(View v) {
    	// Add button press results in a call to this method.
    	
    	String itemVal = etNewItem.getText().toString();
    	itemsAdapter.add(itemVal);
    	etNewItem.setText("");
    	writeItems();
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
