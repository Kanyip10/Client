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

        import com.html5killer.R;

        import java.util.Arrays;

public class TutorialFragment extends Fragment {

    private ExpandableListView eListview;

    private ListView listView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_tutorial,container,false);

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(Arrays.asList(tutorials));





     listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.html5killer.fragments.TutorialFragment.this.openPDF(com.html5killer.fragments.TutorialFragment.this.urls[position]);

            }
        });
        return view;
    }


}
