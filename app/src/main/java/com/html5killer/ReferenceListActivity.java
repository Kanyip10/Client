package com.html5killer;


import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.html5killer.model.ReferenceList;
import com.html5killer.model.Response;
import com.html5killer.network.NetworkUtil;
import com.html5killer.utils.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;




/**
 * Created by Ashley Wong on 30/1/2017.
 */

public class ReferenceListActivity extends AppCompatActivity {
    public static final String TAG = ReferenceListActivity.class.getSimpleName();



    private ListView listView ;
    private ListView listView1 ;
    private ListView listView2 ;
    private ListView listView3 ;
    private ListView listView4 ;
    private TextView header;
    private TextView header1;
    private TextView header2;
    private TextView header3;
    private TextView header4;
    private Spinner spinner;
    private TextView mTvMessage;


   private CompositeSubscription mSubscriptions;
    private String mToken;
    private String mEmail;

    private String[] list1 = {"<!--...-->", "<!DOCTYPE>", "<a>", "<abbr>", "<acronym>", "<address>", "<applet>", "<area>", "<article>", "<aside>", "<audio>", "<b>", "<base>", "<basefont>", "<bdi>", "<bdo>", "<big>", "<blockquote>", "<body>", "<br>", "<button>", "<canvas>", "<caption>", "<center>", "<cite>", "<code>", "<col>", "<colgroup>", "<datalist>", "<dd>", "<del>", "<details>", "<dfn>", "<dialog>", "<dir>", "<div>", "<dl>", "<dt>", "<em>", "<embed>", "<fieldset>", "<figcaption>", "<figure>", "<font>", "<footer>", "<form>", "<frame>", "<frameset>", "<h1> - <h6>", "<head>", "<header>", "<hr>", "<html>", "<i>", "<iframe>", "<img>", "<input>", "<ins>", "<kbd>", "<keygen>", "<label>", "<legend>", "<li>", "<link>", "<main>", "<map>", "<mark>", "<menu>", "<menuitem>", "<meta>", "<meter>", "<nav>", "<noframes>", "<noscript>", "<object>", "<ol>", "<optgroup>", "<option>", "<output>", "<p>", "<param>", "<picture>", "<pre>", "<progress>", "<q>", "<rp>", "<rt>", "<ruby>", "<s>", "<samp>", "<script>", "<section>", "<select>", "<small>", "<source>", "<span>", "<strike>", "<strong>", "<style>", "<sub>", "<summary>", "<sup>", "<table>", "<tbody>", "<td>", "<textarea>", "<tfoot>", "<th>", "<thead>", "<time>", "<title>", "<tr>", "<track>", "<u>", "<ul>", "<var>", "<video>", "<wbr>"};


    private static final String[] list2 = {"<!--...-->","<!DOCTYPE>","<body>","<br>","<h1> - <h6>","<head>","<header>","<hr>"
            ,"<html>","<p>"};

    private static final String[] list3 = {"<table>","<caption>","<th>","<tr>","<td>","<thead>"
    ,"<tbody>","<tfoot>","<col>","<colgroup>"};

    private static final String[] list4 = {"<ul>","<ol>","<li>","<dl>","<dt>","<dd>"};

    private static final String[] list5 = {"<html>","<body>","<p>","<h1> - <h6>","<br>"};

    private static final String[] emptyList = {};

    private static final Map<String, String> fallbackLinkMap = new HashMap<String, String>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referencelist);
        mSubscriptions = new CompositeSubscription();
        initViews();
        initSharedPreferences();
        initFallbackLinkMap();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                switch (position){
                case 0:
                //sorting(spinner.getSelectedItem().toString());
                    header.setText("");
                    header1.setText("");
                    header2.setText("");
                    header3.setText("");
                    header4.setText("");




                    listView.setAdapter(selectedList(list1));
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    listView1.setAdapter(selectedList(emptyList));
                    listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    listView2.setAdapter(selectedList(emptyList));
                    listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    ListUtils.setDynamicHeight(listView);
                    ListUtils.setDynamicHeight(listView1);
                    ListUtils.setDynamicHeight(listView2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                                            Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString(Constants.EMAIL, mEmail);
                                            bundle.putString(Constants.TOKEN, mToken);
                                            bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                            bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView.getItemAtPosition(position)));
                                            intent.putExtras(bundle);
                                            startActivity(intent);


                        }
                    });

                    break;
                case 1:
               // sorting(spinner.getSelectedItem().toString());
                    header.setText("basic");
                    listView.setAdapter(selectedList(list2));
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    header1.setText("table");
                    listView1.setAdapter(selectedList(list3));
                    listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    header2.setText("list");
                    listView2.setAdapter(selectedList(list4));
                    listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    ListUtils.setDynamicHeight(listView);
                    ListUtils.setDynamicHeight(listView1);
                    ListUtils.setDynamicHeight(listView2);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {



                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView.getItemAtPosition(position)));
                                intent.putExtras(bundle);
                                startActivity(intent);





                        }
                    });
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView1.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView1.getItemAtPosition(position)));
                                intent.putExtras(bundle);



                        }
                    });
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView2.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView2.getItemAtPosition(position)));
                                intent.putExtras(bundle);
                                startActivity(intent);




                        }
                    });


                    break;
                case 2:
               // sorting(spinner.getSelectedItem().toString());
                    header.setText("Tutorial1");
                    listView.setAdapter(selectedList(list5));
                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    header1.setText("Tutorial2");
                    listView1.setAdapter(selectedList(list3));
                    listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    header2.setText("Tutorial3");
                    listView2.setAdapter(selectedList(list4));
                    listView2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                    ListUtils.setDynamicHeight(listView);
                    ListUtils.setDynamicHeight(listView1);
                    ListUtils.setDynamicHeight(listView2);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView.getItemAtPosition(position)));
                                intent.putExtras(bundle);
                                startActivity(intent);


                        }
                    });

                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView1.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView1.getItemAtPosition(position)));
                                intent.putExtras(bundle);
                                startActivity(intent);





                        }
                    });
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                                Intent intent = new Intent(ReferenceListActivity.this, listContent.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.EMAIL, mEmail);
                                bundle.putString(Constants.TOKEN, mToken);
                                bundle.putString(Constants.LIST_NAME, (String) listView2.getItemAtPosition(position));
                                bundle.putString(Constants.FALLBACK_LINK, fallbackLinkMap.get((String) listView2.getItemAtPosition(position)));
                                intent.putExtras(bundle);
                                startActivity(intent);





                        }
                    });


                    break;


                }


            }
            public void onNothingSelected(AdapterView arg0) {

            }
        });
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }


    private void initSharedPreferences() {



            Bundle bundle = getIntent().getExtras();

            mToken = bundle.getString(Constants.TOKEN);
            mEmail = bundle.getString(Constants.EMAIL);

    }


    private void initViews() {
        spinner = (Spinner) findViewById(R.id.list_spinner);
        spinner.setAdapter(spinnerView());
        spinner.setSelection(0);
        listView = (ListView) findViewById(R.id.list1);
        listView1 = (ListView) findViewById(R.id.list2);
        listView2 = (ListView) findViewById(R.id.list3);
        listView3 = (ListView) findViewById(R.id.list4);
        listView4 = (ListView) findViewById(R.id.list5);
        header = (TextView) findViewById(R.id.separator1);
        header1 = (TextView) findViewById(R.id.separator2);
        header2 = (TextView) findViewById(R.id.separator3);
        header3 = (TextView) findViewById(R.id.separator4);
        header4 = (TextView) findViewById(R.id.separator5);


    }

    private void initFallbackLinkMap() {
        fallbackLinkMap.put("<!--...-->", "https://www.w3schools.com/tags/tag_comment.asp");
        fallbackLinkMap.put("<!DOCTYPE>", "https://www.w3schools.com/tags/tag_doctype.asp");
        fallbackLinkMap.put("<a>", "https://www.w3schools.com/tags/tag_a.asp");
        fallbackLinkMap.put("<abbr>", "https://www.w3schools.com/tags/tag_abbr.asp");
        fallbackLinkMap.put("<acronym>", "https://www.w3schools.com/tags/tag_acronym.asp");
        fallbackLinkMap.put("<address>", "https://www.w3schools.com/tags/tag_address.asp");
        fallbackLinkMap.put("<applet>", "https://www.w3schools.com/tags/tag_applet.asp");
        fallbackLinkMap.put("<area>", "https://www.w3schools.com/tags/tag_area.asp");
        fallbackLinkMap.put("<article>", "https://www.w3schools.com/tags/tag_article.asp");
        fallbackLinkMap.put("<aside>", "https://www.w3schools.com/tags/tag_aside.asp");
        fallbackLinkMap.put("<audio>", "https://www.w3schools.com/tags/tag_audio.asp");
        fallbackLinkMap.put("<b>", "https://www.w3schools.com/tags/tag_b.asp");
        fallbackLinkMap.put("<base>", "https://www.w3schools.com/tags/tag_base.asp");
        fallbackLinkMap.put("<basefont>", "https://www.w3schools.com/tags/tag_basefont.asp");
        fallbackLinkMap.put("<bdi>", "https://www.w3schools.com/tags/tag_bdi.asp");
        fallbackLinkMap.put("<bdo>", "https://www.w3schools.com/tags/tag_bdo.asp");
        fallbackLinkMap.put("<big>", "https://www.w3schools.com/tags/tag_big.asp");
        fallbackLinkMap.put("<blockquote>", "https://www.w3schools.com/tags/tag_blockquote.asp");
        fallbackLinkMap.put("<body>", "https://www.w3schools.com/tags/tag_body.asp");
        fallbackLinkMap.put("<br>", "https://www.w3schools.com/tags/tag_br.asp");
        fallbackLinkMap.put("<button>", "https://www.w3schools.com/tags/tag_button.asp");
        fallbackLinkMap.put("<canvas>", "https://www.w3schools.com/tags/tag_canvas.asp");
        fallbackLinkMap.put("<caption>", "https://www.w3schools.com/tags/tag_caption.asp");
        fallbackLinkMap.put("<center>", "https://www.w3schools.com/tags/tag_center.asp");
        fallbackLinkMap.put("<cite>", "https://www.w3schools.com/tags/tag_cite.asp");
        fallbackLinkMap.put("<code>", "https://www.w3schools.com/tags/tag_code.asp");
        fallbackLinkMap.put("<col>", "https://www.w3schools.com/tags/tag_col.asp");
        fallbackLinkMap.put("<colgroup>", "https://www.w3schools.com/tags/tag_colgroup.asp");
        fallbackLinkMap.put("<datalist>", "https://www.w3schools.com/tags/tag_datalist.asp");
        fallbackLinkMap.put("<dd>", "https://www.w3schools.com/tags/tag_dd.asp");
        fallbackLinkMap.put("<del>", "https://www.w3schools.com/tags/tag_del.asp");
        fallbackLinkMap.put("<details>", "https://www.w3schools.com/tags/tag_details.asp");
        fallbackLinkMap.put("<dfn>", "https://www.w3schools.com/tags/tag_dfn.asp");
        fallbackLinkMap.put("<dialog>", "https://www.w3schools.com/tags/tag_dialog.asp");
        fallbackLinkMap.put("<dir>", "https://www.w3schools.com/tags/tag_dir.asp");
        fallbackLinkMap.put("<div>", "https://www.w3schools.com/tags/tag_div.asp");
        fallbackLinkMap.put("<dl>", "https://www.w3schools.com/tags/tag_dl.asp");
        fallbackLinkMap.put("<dt>", "https://www.w3schools.com/tags/tag_dt.asp");
        fallbackLinkMap.put("<em>", "https://www.w3schools.com/tags/tag_em.asp");
        fallbackLinkMap.put("<embed>", "https://www.w3schools.com/tags/tag_embed.asp");
        fallbackLinkMap.put("<fieldset>", "https://www.w3schools.com/tags/tag_fieldset.asp");
        fallbackLinkMap.put("<figcaption>", "https://www.w3schools.com/tags/tag_figcaption.asp");
        fallbackLinkMap.put("<figure>", "https://www.w3schools.com/tags/tag_figure.asp");
        fallbackLinkMap.put("<font>", "https://www.w3schools.com/tags/tag_font.asp");
        fallbackLinkMap.put("<footer>", "https://www.w3schools.com/tags/tag_footer.asp");
        fallbackLinkMap.put("<form>", "https://www.w3schools.com/tags/tag_form.asp");
        fallbackLinkMap.put("<frame>", "https://www.w3schools.com/tags/tag_frame.asp");
        fallbackLinkMap.put("<frameset>", "https://www.w3schools.com/tags/tag_frameset.asp");
        fallbackLinkMap.put("<h1> - <h6>", "https://www.w3schools.com/tags/tag_hn.asp");
        fallbackLinkMap.put("<head>", "https://www.w3schools.com/tags/tag_head.asp");
        fallbackLinkMap.put("<header>", "https://www.w3schools.com/tags/tag_header.asp");
        fallbackLinkMap.put("<hr>", "https://www.w3schools.com/tags/tag_hr.asp");
        fallbackLinkMap.put("<html>", "https://www.w3schools.com/tags/tag_html.asp");
        fallbackLinkMap.put("<i>", "https://www.w3schools.com/tags/tag_i.asp");
        fallbackLinkMap.put("<iframe>", "https://www.w3schools.com/tags/tag_iframe.asp");
        fallbackLinkMap.put("<img>", "https://www.w3schools.com/tags/tag_img.asp");
        fallbackLinkMap.put("<input>", "https://www.w3schools.com/tags/tag_input.asp");
        fallbackLinkMap.put("<ins>", "https://www.w3schools.com/tags/tag_ins.asp");
        fallbackLinkMap.put("<kbd>", "https://www.w3schools.com/tags/tag_kbd.asp");
        fallbackLinkMap.put("<keygen>", "https://www.w3schools.com/tags/tag_keygen.asp");
        fallbackLinkMap.put("<label>", "https://www.w3schools.com/tags/tag_label.asp");
        fallbackLinkMap.put("<legend>", "https://www.w3schools.com/tags/tag_legend.asp");
        fallbackLinkMap.put("<li>", "https://www.w3schools.com/tags/tag_li.asp");
        fallbackLinkMap.put("<link>", "https://www.w3schools.com/tags/tag_link.asp");
        fallbackLinkMap.put("<main>", "https://www.w3schools.com/tags/tag_main.asp");
        fallbackLinkMap.put("<map>", "https://www.w3schools.com/tags/tag_map.asp");
        fallbackLinkMap.put("<mark>", "https://www.w3schools.com/tags/tag_mark.asp");
        fallbackLinkMap.put("<menu>", "https://www.w3schools.com/tags/tag_menu.asp");
        fallbackLinkMap.put("<menuitem>", "https://www.w3schools.com/tags/tag_menuitem.asp");
        fallbackLinkMap.put("<meta>", "https://www.w3schools.com/tags/tag_meta.asp");
        fallbackLinkMap.put("<meter>", "https://www.w3schools.com/tags/tag_meter.asp");
        fallbackLinkMap.put("<nav>", "https://www.w3schools.com/tags/tag_nav.asp");
        fallbackLinkMap.put("<noframes>", "https://www.w3schools.com/tags/tag_noframes.asp");
        fallbackLinkMap.put("<noscript>", "https://www.w3schools.com/tags/tag_noscript.asp");
        fallbackLinkMap.put("<object>", "https://www.w3schools.com/tags/tag_object.asp");
        fallbackLinkMap.put("<ol>", "https://www.w3schools.com/tags/tag_ol.asp");
        fallbackLinkMap.put("<optgroup>", "https://www.w3schools.com/tags/tag_optgroup.asp");
        fallbackLinkMap.put("<option>", "https://www.w3schools.com/tags/tag_option.asp");
        fallbackLinkMap.put("<output>", "https://www.w3schools.com/tags/tag_output.asp");
        fallbackLinkMap.put("<p>", "https://www.w3schools.com/tags/tag_p.asp");
        fallbackLinkMap.put("<param>", "https://www.w3schools.com/tags/tag_param.asp");
        fallbackLinkMap.put("<picture>", "https://www.w3schools.com/tags/tag_picture.asp");
        fallbackLinkMap.put("<pre>", "https://www.w3schools.com/tags/tag_pre.asp");
        fallbackLinkMap.put("<progress>", "https://www.w3schools.com/tags/tag_progress.asp");
        fallbackLinkMap.put("<q>", "https://www.w3schools.com/tags/tag_q.asp");
        fallbackLinkMap.put("<rp>", "https://www.w3schools.com/tags/tag_rp.asp");
        fallbackLinkMap.put("<rt>", "https://www.w3schools.com/tags/tag_rt.asp");
        fallbackLinkMap.put("<ruby>", "https://www.w3schools.com/tags/tag_ruby.asp");
        fallbackLinkMap.put("<s>", "https://www.w3schools.com/tags/tag_s.asp");
        fallbackLinkMap.put("<samp>", "https://www.w3schools.com/tags/tag_samp.asp");
        fallbackLinkMap.put("<script>", "https://www.w3schools.com/tags/tag_script.asp");
        fallbackLinkMap.put("<section>", "https://www.w3schools.com/tags/tag_section.asp");
        fallbackLinkMap.put("<select>", "https://www.w3schools.com/tags/tag_select.asp");
        fallbackLinkMap.put("<small>", "https://www.w3schools.com/tags/tag_small.asp");
        fallbackLinkMap.put("<source>", "https://www.w3schools.com/tags/tag_source.asp");
        fallbackLinkMap.put("<span>", "https://www.w3schools.com/tags/tag_span.asp");
        fallbackLinkMap.put("<strike>", "https://www.w3schools.com/tags/tag_strike.asp");
        fallbackLinkMap.put("<strong>", "https://www.w3schools.com/tags/tag_strong.asp");
        fallbackLinkMap.put("<style>", "https://www.w3schools.com/tags/tag_style.asp");
        fallbackLinkMap.put("<sub>", "https://www.w3schools.com/tags/tag_sub.asp");
        fallbackLinkMap.put("<summary>", "https://www.w3schools.com/tags/tag_summary.asp");
        fallbackLinkMap.put("<sup>", "https://www.w3schools.com/tags/tag_sup.asp");
        fallbackLinkMap.put("<table>", "https://www.w3schools.com/tags/tag_table.asp");
        fallbackLinkMap.put("<tbody>", "https://www.w3schools.com/tags/tag_tbody.asp");
        fallbackLinkMap.put("<td>", "https://www.w3schools.com/tags/tag_td.asp");
        fallbackLinkMap.put("<textarea>", "https://www.w3schools.com/tags/tag_textarea.asp");
        fallbackLinkMap.put("<tfoot>", "https://www.w3schools.com/tags/tag_tfoot.asp");
        fallbackLinkMap.put("<th>", "https://www.w3schools.com/tags/tag_th.asp");
        fallbackLinkMap.put("<thead>", "https://www.w3schools.com/tags/tag_thead.asp");
        fallbackLinkMap.put("<time>", "https://www.w3schools.com/tags/tag_time.asp");
        fallbackLinkMap.put("<title>", "https://www.w3schools.com/tags/tag_title.asp");
        fallbackLinkMap.put("<tr>", "https://www.w3schools.com/tags/tag_tr.asp");
        fallbackLinkMap.put("<track>", "https://www.w3schools.com/tags/tag_track.asp");
        fallbackLinkMap.put("<u>", "https://www.w3schools.com/tags/tag_u.asp");
        fallbackLinkMap.put("<ul>", "https://www.w3schools.com/tags/tag_ul.asp");
        fallbackLinkMap.put("<var>", "https://www.w3schools.com/tags/tag_var.asp");
        fallbackLinkMap.put("<video>", "https://www.w3schools.com/tags/tag_video.asp");
        fallbackLinkMap.put("<wbr>", "https://www.w3schools.com/tags/tag_wbr.asp");
    }

    private ArrayAdapter<CharSequence> spinnerView(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.list_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    return adapter;
    }



    private ArrayAdapter<String> selectedList(String[] strings){
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, strings);
        return adapter;
    }


  /*  private void sorting(String division) {

        mSubscriptions.add(NetworkUtil.getRetrofit(mToken).sort(mEmail,division)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));


    }

    private void handleResponse(ReferenceList list ) {


      //  for(int i = 0; i < sorted.length; i++){
        // sorted[0] = list.getName();
        //}




    }

    private void handleError(Throwable error) {



        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void showMessage(String message) {

        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);

    }
    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.referencelist),message,Snackbar.LENGTH_SHORT).show();

    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        }
    }

