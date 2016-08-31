package sr.com.sr.sabbir.webnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner countrySP;
    private Spinner locationSP;
private  DB  db;
    String Country[], Locations[];
    String selectCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DB(this);
        db.createTables();
        addItemsOnCountrySpinner();

    }

    public void addItemsOnCountrySpinner() {

        countrySP = (Spinner) findViewById(R.id.country_spinner);
        List<String> country_list = new ArrayList<String>();

        //country_list=dataSource.getAllLabels();
        Country = db.GetCountry();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Country);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySP.setAdapter(dataAdapter);


        countrySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        locationSP = (Spinner) findViewById(R.id.location_spinner);
                        if(position == 0)
                        {
                            locationSP.setVisibility(View.GONE);
                        }
                        else
                        {

                            addItemsOnLocationSpinner(Country[position]);
                            locationSP.setVisibility(View.VISIBLE);
                        }

                        locationSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String country_name = countrySP.getSelectedItem().toString();
                                        String location_name = locationSP.getSelectedItem().toString();

                                        Intent intent = new Intent(getBaseContext(),weather.class);
                                        intent.putExtra("Loc",location_name+","+country_name);
                                        startActivity(intent);
                                        //Toast.makeText(getApplicationContext(), country_name + location_name, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void addItemsOnLocationSpinner(String country) {

        locationSP = (Spinner) findViewById(R.id.location_spinner);
        List<String> list1 = new ArrayList<String>();

        Locations = db.GetLocation(country);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSP.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
