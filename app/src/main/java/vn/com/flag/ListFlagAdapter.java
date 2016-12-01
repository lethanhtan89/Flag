package vn.com.flag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import vn.com.flag.util.ImageLoader;

/**
 * Created by Administrator on 02/11/2016.
 */
public class ListFlagAdapter extends ArrayAdapter<JSONObject> implements SpinnerAdapter {
    private final static String TAG = ListFlagAdapter.class.getSimpleName();
    int vg;

    private ArrayList<JSONObject> list;
    private ArrayList<Countries> countriesArrayList;
    private Context context;
    private ImageView imgSelected, imgFlag;
    ProgressDialog pg_dialog;

    public ListFlagAdapter(Context context, int vg,  ArrayList<JSONObject> list, ArrayList<Countries> countriesArrayList){
        super(context,vg,list);
        this.context=context;
        this.vg=vg;
        this.list=list;
        this.countriesArrayList = countriesArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(vg, parent, false);

        TextView txtCtry=(TextView)itemView.findViewById(R.id.txt_ctry_cd);

        TextView txtCurrName=(TextView)itemView.findViewById(R.id.txt_curr_name);
        imgSelected = (ImageView) itemView.findViewById(R.id.img_selected);
        imgFlag = (ImageView) itemView.findViewById(R.id.img_flag);

        Countries countries = countriesArrayList.get(position);

        try {
            txtCtry.setText(countries.getNameConutry());
            txtCurrName.setText(countries.getMoneyCountry());

            //loadBitmap(1, imgFlag);
            imgSelected.setVisibility(View.INVISIBLE);

           // new BitmapWorkerTask(imgFlag).execute();

            String[] images =context.getAssets().list("flag");
            ArrayList<String> listImages = new ArrayList<String>(Arrays.asList(images));

            InputStream inputstream=context.getAssets().open("flag/" + listImages.get(position));
            //Drawable drawable = Drawable.createFromStream(inputstream, null);
            //imgFlag.setImageDrawable(drawable);

            Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
            imgFlag.setImageBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemView;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txtDrop = new TextView(getContext());
        txtDrop.setPadding(16, 16, 16, 16);
        txtDrop.setTextSize(18);
        txtDrop.setGravity(Gravity.CENTER_VERTICAL);

        try {
            txtDrop.setText(list.get(position).getString("ctry_cd"));
            imgSelected.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtDrop.setTextColor(Color.parseColor("#000000"));
        return  txtDrop;
    }

    @Override
    public int getCount() {
        return countriesArrayList.size();
    }
}
