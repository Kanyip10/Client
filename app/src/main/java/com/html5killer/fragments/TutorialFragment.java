package com.html5killer.fragments;

        import android.support.v4.app.Fragment;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ExpandableListView;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.html5killer.ExpandableListAdapter;
        import com.html5killer.R;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.List;

public class TutorialFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ImageView owl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tutorial,container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        owl = (ImageView) view.findViewById(R.id.imageView2);
      /*  arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(Arrays.asList(tutorials));

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TutorialActivity.this.openPDF(TutorialActivity.this.urls[position]);

            }
        });*/


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(
                        getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        return  view;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Tutorial 1");
        listDataHeader.add("Tutorial 2");
        listDataHeader.add("Tutorial 3");

        // Adding child data
        List<String> Tutorial1 = new ArrayList<String>();
        Tutorial1.add("PDF");
        Tutorial1.add("Youtube");
        Tutorial1.add("Test");


        List<String> Tutorial2 = new ArrayList<String>();
        Tutorial2.add("PDF");
        Tutorial2.add("Youtube");
        Tutorial2.add("Test");

        List<String> Tutorial3 = new ArrayList<String>();
        Tutorial3.add("PDF");
        Tutorial3.add("Youtube");
        Tutorial3.add("Test");

        listDataChild.put(listDataHeader.get(0), Tutorial1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Tutorial2);
        listDataChild.put(listDataHeader.get(2), Tutorial3);
    }
}

  /*  private ListView listView;
    private String[] tutorials = {
            "Tutorial 1 - PDF",
            "Tutorial 1 - Youtube",
            "Tutorial 2 - PDF",
            "Tutorial 2 - Youtube",
            "Tutorial 3 - PDF",
            "Tutorial 3 - Youtube",
            "Tutorial 4 - PDF",
            "Tutorial 4 - Youtube",
            "Tutorial 5 - PDF",
            "Tutorial 5 - Youtube"
    };
    private String[] urls = {
            "https://drive.google.com/file/d/0B1o8pNj_jIRjMWFDTnZ3OVFZZjg/view",
            "https://www.youtube.com/watch?v=9gTw2EDkaDQ",
            "https://drive.google.com/file/d/0B1o8pNj_jIRjMWFDTnZ3OVFZZjg/view",
            "https://www.youtube.com/watch?v=YcApt9RgiT0",
            "https://drive.google.com/file/d/0B1o8pNj_jIRjMWFDTnZ3OVFZZjg/view",
            "https://www.youtube.com/watch?v=CGSdK7FI9MY",
            "https://drive.google.com/file/d/0B1o8pNj_jIRjMWFDTnZ3OVFZZjg/view",
            "https://www.youtube.com/watch?v=4I1WgJz_lmA",
            "https://drive.google.com/file/d/0B1o8pNj_jIRjMWFDTnZ3OVFZZjg/view",
            "https://www.youtube.com/watch?v=dDn9uw7N9Xg"
    };
    private ArrayAdapter arrayAdapter;

    protected void openPDF(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }




}*/
