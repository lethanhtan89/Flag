package vn.com.flag;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    // JSON Node names
    private static final String TAG_CTRY_CD = "ctry_cd";
    private static final String TAG_CTRY_KR = "ctry_kr";
    private static final String TAG_CTRY_EN = "ctry_en";
    private static final String TAG_CURR_CD = "curr_cd";
    private static final String TAG_CURR_NAME = "curr_name";
    private static final String TAG_AP_ICAN = "ap_ican";
    private static final String TAG_AP_CT_KR = "ap_ct_kr";
    private static final String TAG_AP_KR = "ap_kr";


    private ListView lv;
    ArrayList<HashMap<String,String>> countriesList= new ArrayList<HashMap<String,String>>();
    private ProgressDialog progressDialog;

    private AssetManager assetManager;

    private SearchView searchView;

    private TextView txtSearch;
    ListFlagAdapter adapter;

    private Spinner spinner;

    JSONArray jsonArray = null;
    JSONArray jsonDropArray = null;
    ArrayList<JSONObject> listItems;
    ArrayList<Countries> arrayCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar ab = getSupportActionBar();
        ab.hide();

        searchView = (SearchView) findViewById(R.id.searchView);

        spinner = (Spinner) findViewById(R.id.spList);

        txtSearch = (TextView) findViewById(R.id.txtSearch);


        init();




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init(){
        new AsyncTask<String, String, String>(){
                JSONObject json_data;

                @Override
                protected String doInBackground(String... strings) {
                    jsonArray = getJSonData("countries.json");

                    return String.valueOf(jsonArray);
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);

                    arrayCountry = new ArrayList<>();

                    try{
                        jsonArray = new JSONArray(result);

                        for(int i = 0;i < jsonArray.length();i++){
                            json_data = jsonArray.getJSONObject(i);
                            Countries countries = new Countries();
                            countries.NameConutry = json_data.getString(TAG_CTRY_CD);
                            countries.MoneyCountry= json_data.getString(TAG_CURR_NAME);

                            arrayCountry.add(countries);
                        }



                        adapter = new ListFlagAdapter(MainActivity.this, R.layout.item_flag, listItems , arrayCountry);
                        lv = (ListView) findViewById(R.id.listview);

                        lv.setAdapter(adapter);

                        //spinner.setAdapter(adapter);


                    }catch (Exception e){
                        Toast.makeText(MainActivity.this,"Can not load information", Toast.LENGTH_LONG).show();
                    }
                }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //txtSearch.setVisibility(View.GONE);
                if (TextUtils.isEmpty(newText)) {
                    adapter.getFilter().filter("");
                    lv.clearTextFilter();
                } else {
                    adapter.getFilter().filter(newText.toString());
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        init();
    }




    private JSONArray getJSonData(String fileName){

        JSONArray jsonArray=null;

        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray=new JSONArray(json);
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException je){
            je.printStackTrace();
        }
        return jsonArray;
    }

    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList<JSONObject> aList=new ArrayList<JSONObject>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        }catch (JSONException je){je.printStackTrace();}
        return  aList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
            lv.clearTextFilter();
        } else {
            adapter.getFilter().filter(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Ban da chon", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
