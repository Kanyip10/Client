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
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.html5killer.ExpandableListAdapter;
        import com.html5killer.HomeActivity;
        import com.html5killer.PlayActivity;
        import com.html5killer.R;
        import com.html5killer.TestActivity;
        import com.html5killer.utility.Prefs;
        import com.html5killer.utils.Constants;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.List;

        import android.content.SharedPreferences;

public class TutorialFragment extends Fragment {

    public static final String TAG = TutorialFragment.class.getSimpleName();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ImageView owl;
    private String mToken;
    private String mEmail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tutorial,container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        owl = (ImageView) view.findViewById(R.id.imageView2);
        getData();
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);



        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


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
        setListViewHeight(expListView,5);

   /*     expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

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
        });*/

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
             switch (groupPosition){
                 case 0:
                     switch (childPosition){
                         case 0:
                             openPDF("https://drive.google.com/file/d/0B1o8pNj_jIRjbmtkQ3hZQlNrWUk/view?usp=sharing");
                             break;
                         case 1:
                             openPDF("https://www.youtube.com/watch?v=9gTw2EDkaDQ");
                             break;
                         case 2:
                             Intent intent = new Intent(getActivity(),TestActivity.class);
                             Bundle bundle = new Bundle();
                             bundle.putString(Constants.EMAIL, mEmail);
                             bundle.putString(Constants.TOKEN,mToken);
                             bundle.putString("tutorialNum", "1");
                             intent.putExtras(bundle);
                             startActivity(intent);
                             break;
                         case 3:
                             Intent intent1 = new Intent(getActivity(), HomeActivity.class);
                             Bundle bundle1 = new Bundle();
                             bundle1.putString(Constants.EMAIL, mEmail);
                             bundle1.putString(Constants.TOKEN,mToken);
                             bundle1.putString("tutorialNum", "1");
                             intent1.putExtras(bundle1);
                             startActivity(intent1);
                             break;

                     }
                     break;
                 case 1:
                     switch (childPosition){
                         case 0:
                             openPDF("https://drive.google.com/file/d/0B1o8pNj_jIRjbmtkQ3hZQlNrWUk/view?usp=sharing");
                             break;
                         case 1:
                             openPDF("https://www.youtube.com/watch?v=YcApt9RgiT0");
                             break;
                         case 2:
                             Intent intent = new Intent(getActivity(),TestActivity.class);
                             Bundle bundle = new Bundle();
                             bundle.putString(Constants.EMAIL, mEmail);
                             bundle.putString(Constants.TOKEN,mToken);
                             bundle.putString("tutorialNum", "2");
                             intent.putExtras(bundle);
                             startActivity(intent);
                             break;
                         case 3:
                             Intent intent1 = new Intent(getActivity(), HomeActivity.class);
                             Bundle bundle1 = new Bundle();
                             bundle1.putString(Constants.EMAIL, mEmail);
                             bundle1.putString(Constants.TOKEN,mToken);
                             bundle1.putString("tutorialNum", "2");
                             intent1.putExtras(bundle1);
                             startActivity(intent1);
                             break;

                     }
                     break;
                 case 2:
                     switch (childPosition){
                         case 0:
                             openPDF("https://drive.google.com/file/d/0B1o8pNj_jIRjbmtkQ3hZQlNrWUk/view?usp=sharing");
                             break;
                         case 1:
                             openPDF("https://www.youtube.com/watch?v=CGSdK7FI9MY");
                             break;
                         case 2:
                             Intent intent = new Intent(getActivity(),TestActivity.class);
                             Bundle bundle = new Bundle();
                             bundle.putString(Constants.EMAIL, mEmail);
                             bundle.putString(Constants.TOKEN,mToken);
                             bundle.putString("tutorialNum", "3");
                             intent.putExtras(bundle);
                             startActivity(intent);
                             break;
                         case 3:
                             Intent intent1 = new Intent(getActivity(), HomeActivity.class);
                             Bundle bundle1 = new Bundle();
                             bundle1.putString(Constants.EMAIL, mEmail);
                             bundle1.putString(Constants.TOKEN,mToken);
                             bundle1.putString("tutorialNum", "3");
                             intent1.putExtras(bundle1);
                             startActivity(intent1);
                             break;

                     }
                     break;
                 /*
                 case 3:
                     switch (childPosition){
                         case 0:
                             openPDF("https://drive.google.com/file/d/0B1o8pNj_jIRjbmtkQ3hZQlNrWUk/view?usp=sharing");
                             break;
                         case 1:
                             openPDF("https://www.youtube.com/watch?v=9gTw2EDkaDQ");
                             break;
                         case 2:
                             Intent intent = new Intent(getActivity(),TestActivity.class);
                             Bundle bundle = new Bundle();
                             bundle.putString(Constants.EMAIL, mEmail);
                             bundle.putString(Constants.TOKEN,mToken);
                             intent.putExtras(bundle);
                             startActivity(intent);
                             break;
                         case 3:
                             Intent intent1 = new Intent(getActivity(), HomeActivity.class);
                             Bundle bundle1 = new Bundle();
                             bundle1.putString(Constants.EMAIL, mEmail);
                             bundle1.putString(Constants.TOKEN,mToken);
                             intent1.putExtras(bundle1);
                             startActivity(intent1);
                             break;

                     }
                 case 4:
                     switch (childPosition){
                         case 0:
                             openPDF("https://drive.google.com/file/d/0B1o8pNj_jIRjbmtkQ3hZQlNrWUk/view?usp=sharing");
                             break;
                         case 1:
                             openPDF("https://www.youtube.com/watch?v=9gTw2EDkaDQ");
                             break;
                         case 2:
                             Intent intent = new Intent(getActivity(),TestActivity.class);
                             Bundle bundle = new Bundle();
                             bundle.putString(Constants.EMAIL, mEmail);
                             bundle.putString(Constants.TOKEN,mToken);
                             intent.putExtras(bundle);
                             startActivity(intent);
                             break;
                         case 3:
                             Intent intent1 = new Intent(getActivity(), HomeActivity.class);
                             Bundle bundle1 = new Bundle();
                             bundle1.putString(Constants.EMAIL, mEmail);
                             bundle1.putString(Constants.TOKEN,mToken);
                             intent1.putExtras(bundle1);
                             startActivity(intent1);
                             break;

                     }

                */




             }


            return false;}
        });
        return  view;
    }
    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
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
        //listDataHeader.add("Tutorial 4");
        //listDataHeader.add("Tutorial 5");


        // Adding child data
        List<String> Tutorial1 = new ArrayList<String>();
        Tutorial1.add("PDF");
        Tutorial1.add("Youtube");
        Tutorial1.add("Test");
        Tutorial1.add("Game             Highest score: " + String.valueOf(getResources().getString(R.string.highestscore) )+ "/" + String.valueOf(getResources().getString(R.string.highestscore)));


        List<String> Tutorial2 = new ArrayList<String>();
        Tutorial2.add("PDF");
        Tutorial2.add("Youtube");
        Tutorial2.add("Test");
        Tutorial2.add("Game             Highest score: " + String.valueOf(getResources().getString(R.string.highestscore)) + "/" + String.valueOf(getResources().getString(R.string.highestscore)));

        List<String> Tutorial3 = new ArrayList<String>();
        Tutorial3.add("PDF");
        Tutorial3.add("Youtube");
        Tutorial3.add("Test");
        Tutorial3.add("Game             Highest score: " + getActivity().getSharedPreferences("FIND_DIFF", 0).getInt("HIGHSCORE", 0) + "/" + String.valueOf(getResources().getString(R.string.highestscore)));

        /*
        List<String> Tutorial4 = new ArrayList<String>();
        Tutorial4.add("PDF");
        Tutorial4.add("Youtube");
        Tutorial4.add("Test");
        Tutorial4.add("Game             Highest score: " + "0" + "/" + String.valueOf(getResources().getString(R.string.highestscore)));
        List<String> Tutorial5 = new ArrayList<String>();
        Tutorial5.add("PDF");
        Tutorial5.add("Youtube");
        Tutorial5.add("Test");
        Tutorial5.add("Game             Highest score: " + "0" + "/" + String.valueOf(getResources().getString(R.string.highestscore)));
*/


        listDataChild.put(listDataHeader.get(0), Tutorial1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Tutorial2);
        listDataChild.put(listDataHeader.get(2), Tutorial3);
        //listDataChild.put(listDataHeader.get(3), Tutorial4);
        //listDataChild.put(listDataHeader.get(4), Tutorial5);

    }
    protected void openPDF(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
    private void getData() {

        Bundle bundle = getArguments();

        mToken = bundle.getString(Constants.TOKEN);
        mEmail = bundle.getString(Constants.EMAIL);
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
