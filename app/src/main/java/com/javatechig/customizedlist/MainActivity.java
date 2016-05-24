package com.javatechig.customizedlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.property.UserProperty;
import com.example.customizedlist.R;
import android.os.Vibrator;

public class MainActivity extends Activity {

    //static ArrayList<String> names = new ArrayList<String>();

    static BackendlessCollection<Person> myVar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appVersion = "v1";
        Backendless.initApp(this, "A0819152-C875-C222-FF18-0516AB9ACC00", "94E2E030-C1B8-0F27-FFEE-CD829BAE3400", appVersion);
        Log.i("info", "backendless success");

        String clause = "";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(clause);

        Backendless.Data.of( Users.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<Users>>() {
            @Override
            public void handleResponse(BackendlessCollection<Users> nycPeople) {


                Iterator<Users> iterator = nycPeople.getCurrentPage().iterator();


                while (iterator.hasNext()) {

                    Users user = iterator.next();

                    Log.i("info", "unique" + user.getName());


                }


            }


            @Override
            public void handleFault(BackendlessFault backendlessFault) {

                Log.i("info", "failed");

            }
        });




        Backendless.Data.of( Person.class ).find(dataQuery, new AsyncCallback<BackendlessCollection<Person>>() {
            @Override
            public void handleResponse(BackendlessCollection<Person> nycPeople) {


                myVar = nycPeople;


                /*Iterator<Person> iterator = nycPeople.getCurrentPage().iterator();





                while (iterator.hasNext()) {

                    Person post = iterator.next();


                    names.add(post.getName());


                }*/

                ArrayList<ListItem> listData = getListData();

                final ListView listView = (ListView) findViewById(R.id.custom_list);
                listView.setAdapter(new CustomListAdapter(MainActivity.this, listData));
                listView.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        ListItem newsData = (ListItem) listView.getItemAtPosition(position);
                        Toast.makeText(MainActivity.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();

                        Vibrator vib = (Vibrator) MainActivity.this.getSystemService(MainActivity.this.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        vib.vibrate(500);


                    }
                });


            }


            @Override
            public void handleFault(BackendlessFault backendlessFault) {

                Log.i("info", "failed");

            }
        });


    }



    ArrayList<ListItem> getListData() {

        /*for (String s: names) {

            Log.i("info", s);

        }*/


        final ArrayList<ListItem> listMockData = new ArrayList<ListItem>();
        String[] images = getResources().getStringArray(R.array.images_array);
        String[] headlines = getResources().getStringArray(R.array.headline_array);

        /*for (int i = 0; i < images.length; i++) {
            ListItem newsData = new ListItem();
            newsData.setUrl("http://vignette4.wikia.nocookie.net/mrmen/images/5/52/Small.gif/revision/latest?cb=20100731114437");
            newsData.setHeadline(headlines[i]);
            newsData.setReporterName("Pankaj Gupta");
            newsData.setDate("May 26, 2013, 13:35");
            listMockData.add(newsData);
        }*/



        Iterator<Person> iterator = myVar.getCurrentPage().iterator();



                while (iterator.hasNext()) {

                    Person post = iterator.next();

                    Log.i("info", "working: " + post.getName());

                    ListItem newsData = new ListItem();
                    newsData.setUrl("https://api.backendless.com/A0819152-C875-C222-FF18-0516AB9ACC00/v1/files/media/" + post.getName() + ".png");
                    newsData.setHeadline(post.getName());
                    newsData.setReporterName(post.getName());
                    newsData.setDate("May 26, 2013, 13:35");
                    listMockData.add(newsData);





                }
        return listMockData;

    }




}
