package sr.com.sr.sabbir.webnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class weather extends AppCompatActivity {
    //String url="http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=2de143494c0b295cca9337e1e96b00e0";
    String maxTemp;
    String minTemp;
    String mxTmp;
    String mnTmp;
    String currentTemp;
    String cTemp;
    String humidity;
    String sunri;
    String sunse;
    String sunRise;
    String sunSet;
    String cityN;
    String description;
    String img="windy";
    String pressure;
    TextView sunRiseTV;
    TextView sunSetTV;
    TextView max;
    TextView min;
    TextView temp;
    TextView humidityTV;
    ImageView windicon;
    TextView cityName;
    TextView descriptionTV;
    ImageView cityImage;
    TextView pressureTV;
    String cityImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        max= (TextView) findViewById(R.id.MaxperTV);
        min= (TextView) findViewById(R.id.MinperTV);
        temp= (TextView) findViewById(R.id.degreeTV);
        cityName= (TextView) findViewById(R.id.cityName);
        humidityTV= (TextView) findViewById(R.id.humidtyperTV);
        sunRiseTV= (TextView) findViewById(R.id.SunriseTimeTV);
        sunSetTV= (TextView) findViewById(R.id.SunsetTimeTV);
        windicon= (ImageView) findViewById(R.id.windicon);
        descriptionTV= (TextView) findViewById(R.id.windtextTV);
        cityImage= (ImageView) findViewById(R.id.cityImage);
        pressureTV= (TextView) findViewById(R.id.pressureTV);


        //Toast.makeText(getApplicationContext(), loc+"\n"+url, Toast.LENGTH_LONG).show();

        showService();
    }
    private void showService(){
        String loc = getIntent().getExtras().getString("Loc");
        String url="http://api.openweathermap.org/data/2.5/weather?q="+loc+"&appid=44db6a862fba0b067b1930da0d769e98";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main=response.getJSONObject("main");
                    JSONObject sys=response.getJSONObject("sys");
                    JSONArray weather=response.getJSONArray("weather");
                    JSONObject weth=weather.getJSONObject(0);


                    maxTemp=main.getString("temp_max");
                    minTemp=main.getString("temp_min");
                    currentTemp=main.getString("temp");
                    humidity=main.getString("humidity");
                    sunRise=sys.getString("sunrise");
                    sunSet=sys.getString("sunset");
                    img=weth.getString("icon");
                    cityN=response.getString("name");
                    cityImg=response.getString("id");
                    description=weth.getString("description");
                    pressure=main.getString("pressure");


                    double mTemp=Double.parseDouble(maxTemp);
                    int tempMax= (int) mTemp;
                    int rsultMaxTemp=tempMax-273;
                    mxTmp=new Integer(rsultMaxTemp).toString();
                    double mnTemp=Double.parseDouble(minTemp);
                    int tempMin= (int) mnTemp;
                    int resultMinTmp=tempMin - 273;
                    mnTmp=new Integer(resultMinTmp).toString();
                    double ccTemp=Double.parseDouble(currentTemp);
                    int cccMin= (int) ccTemp;
                    int resultCurrentTmp=cccMin - 273;
                    cTemp=new Integer(resultCurrentTmp).toString();



                    int sunr=Integer.parseInt(sunRise);
                    long unixTimestamp = sunr;
                    long javaTimestamp = unixTimestamp * 1000L;
                    Date date = new Date(javaTimestamp);
                    sunri = new SimpleDateFormat("hh:mm").format(date);


                    int suns=Integer.parseInt(sunSet);
                    long unixTimestamp1=suns;
                    long javaTimestamp1=unixTimestamp1 * 1000L;
                    Date date1=new Date(javaTimestamp1);
                    sunse=new SimpleDateFormat("hh:mm").format(date1);



                    min.setText(mnTmp+" \u2103");
                    max.setText(mxTmp+" \u2103");
                    temp.setText(cTemp+" \u2103");
                    humidityTV.setText(humidity);
                    sunRiseTV.setText(sunri+" AM");
                    sunSetTV.setText(sunse +"PM");
                    cityName.setText(cityN);
                    descriptionTV.setText(description);
                    pressureTV.setText(pressure);
                    //Toast.makeText(getBaseContext(),img,Toast.LENGTH_LONG).show();
                    windicon.setImageResource(getResources().getIdentifier("w"+img, "drawable", getPackageName()));
                    cityImage.setImageResource(getResources().getIdentifier("a"+cityImg, "drawable", getPackageName()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


}
