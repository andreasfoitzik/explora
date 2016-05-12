package de.explora;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    private static final String TAG = "ListViewFragment";
    static List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    static SimpleAdapter listViewAdapter;
    int positionElement;
    private static int MENU_ADD = 1;
    private static int MENU_DETAILS = 2;
    private static View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_list, container, false);
        } catch (InflateException e) {
        }

        return rootView;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] from = { "flag","name","detail" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.name,R.id.detail};

        aList.clear();
        HashMap<String, String> hm = new HashMap<String,String>();
        hm.put("name","Text");
        hm.put("detail","Subtext");
        hm.put("detailtext","Detailtext");
        hm.put("flag", Integer.toString(R.drawable.ic_action_bluetooth_connected) );
        aList.add(hm);
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        listViewAdapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) getActivity().findViewById(R.id.listview);


        //registerContextMenu
        registerForContextMenu(listView);


        // Setting the adapter to the listView
        listView.setAdapter(listViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                v.setLongClickable(false);
                getActivity().openContextMenu(v);

            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, MENU_ADD, 0, "Add Element to Container");
        menu.add(0, MENU_DETAILS, 1, "Show details");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;


        if (item.getItemId() == MENU_ADD) {

            positionElement = -1;

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Items");
            builder.setSingleChoiceItems(R.array.details, -1, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    positionElement = arg1;
                }
            });

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Resources res = getResources();
                    String[] networks = res.getStringArray(R.array.details);
                    if (positionElement >= 0) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Element " + networks[positionElement] + " added to Container", Toast.LENGTH_LONG)
                                .show();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "No Element selected", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.show();



        } else if (item.getItemId() == MENU_DETAILS) {

            ListView lv = (ListView) getActivity().findViewById(R.id.listview);
            HashMap<String,String> details =(HashMap<String, String>) lv.getItemAtPosition(index);


            Log.d(TAG, "Get details from Element"+details);


            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Details");
            builder.setIcon(R.drawable.ic_action_about);
            builder.setPositiveButton(android.R.string.ok,null);
            builder.setMessage(details.get("detailtext"));
            builder.show();
        }
        return super.onContextItemSelected(item);
    }


}
