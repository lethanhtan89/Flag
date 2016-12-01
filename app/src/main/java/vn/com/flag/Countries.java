package vn.com.flag;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 03/11/2016.
 */
public class Countries implements Serializable{
    String NameConutry;
    String MoneyCountry;
    String Flag;

    public Countries(){}

    public Countries(String NameCountry, String MoneyCountry, String Flag){
        this.NameConutry = NameCountry;
        this.MoneyCountry = MoneyCountry;
        this.Flag = Flag;
    }

    public void save(Context p_context) {
        SharedPreferences w_sp = p_context.getSharedPreferences("Countries", Activity.MODE_PRIVATE);
        SharedPreferences.Editor w_editor = w_sp.edit();

        w_editor.putString("ctry_cd", NameConutry);
        w_editor.putString("curr_name", NameConutry);

        w_editor.commit();
    }

    public static Countries load(Context p_context) {
        Countries countries = null;

        SharedPreferences w_sp = p_context.getSharedPreferences("Countries", Activity.MODE_PRIVATE);
        if (w_sp != null) {
            countries = new Countries();
            countries.NameConutry = w_sp.getString("ctry_cd", "");
            countries.MoneyCountry = w_sp.getString("curr_name", "");
        }

        return countries;
    }

    public JSONObject toJSONObject() {
        JSONObject w_json = new JSONObject();

        try {
            w_json.put("ctry_cd", NameConutry);
            w_json.put("curr_name", MoneyCountry);

        } catch (JSONException e) {
            return null;
        }

        return w_json;
    }

    public static Countries fromJSONObject(JSONObject p_json) {
        Countries countries = null;

        try {
            String w_NameCountry = p_json.getString("ctry_cd");
            String w_MoneyCountry = p_json.getString("curr_name");
            String w_Flag = p_json.getString("curr_cd");


            countries = new Countries(w_NameCountry, w_MoneyCountry, w_Flag);
        } catch (JSONException e) {
            return null;
        }

        return countries;
    }

    public String getNameConutry() {
        return NameConutry;
    }

    public void setNameConutry(String nameConutry) {
        NameConutry = nameConutry;
    }

    public String getMoneyCountry() {
        return MoneyCountry;
    }

    public void setMoneyCountry(String moneyCountry) {
        MoneyCountry = moneyCountry;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
}
