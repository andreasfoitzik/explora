package de.explora;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends Activity {

    GridView gridView;
    GridViewAdapter gridViewAdapter;

    private static int MENU_EDIT = 1;
    private static int MENU_DETAILS = 2;
    private static int MENU_DELETE = 3;
    private static String TAG ="DetailActivity";

    static final ArrayList<String> gridElements = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gridView = (GridView) findViewById(R.id.gridView);
        registerForContextMenu(gridView);

        //Example Data
        gridElements.clear();
        gridElements.add("Element1");
        gridElements.add("Element2");
        gridElements.add("Element3");

        gridViewAdapter = new GridViewAdapter(this, gridElements,"beacon");
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String nameOfElement =  ((TextView) v.findViewById(R.id.grid_item_label)).getText().toString();

                Toast.makeText(getApplicationContext(),
                        nameOfElement, Toast.LENGTH_LONG)
                        .show();

                v.setLongClickable(false);
                openContextMenu(v);
            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_EDIT, 0, "Edit");
        menu.add(0, MENU_DELETE, 1, "Delete");
        menu.add(0, MENU_DETAILS, 2, "Details");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;


        if (item.getItemId() == MENU_EDIT) {
            GridView gridview = (GridView) findViewById(R.id.gridView);
            String details =(gridview.getItemAtPosition(index).toString());

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Edit Element");
            builder.setMessage("Edit your elemen bla...");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            input.setText(details);
            builder.setView(input);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    gridElements.set(index, value);

                    Toast.makeText(getApplicationContext(),
                            "New Elementname " + value, Toast.LENGTH_LONG)
                            .show();
                    gridViewAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridViewAdapter);

                }
            });
            builder.setNegativeButton(android.R.string.cancel,null);
            builder.show();




        } else if (item.getItemId() == MENU_DELETE) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Element");
            builder.setMessage("Really delete Element?");
            builder.setIcon(R.drawable.ic_action_about);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    gridElements.remove(index);
                    Toast.makeText(getApplicationContext(),
                            "Element deleted!", Toast.LENGTH_LONG)
                            .show();
                    gridViewAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridViewAdapter);
                }
            });
            builder.setNegativeButton("cancel",null);
            builder.show();
        }
        else if (item.getItemId() == MENU_DETAILS) {

            GridView gridview = (GridView) this.findViewById(R.id.gridView);
            String details =(gridview.getItemAtPosition(index).toString());

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(details);
            builder.setIcon(R.drawable.ic_action_about);
            builder.setPositiveButton(android.R.string.ok,null);
            builder.setMessage("Details...");
            builder.show();
        }
        return super.onContextItemSelected(item);
    }
}
