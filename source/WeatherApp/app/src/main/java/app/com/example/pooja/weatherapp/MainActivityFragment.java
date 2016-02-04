package app.com.example.pooja.weatherapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, true);
        BackgroundActivity bga=new BackgroundActivity();
        bga.execute();

        ArrayList<String> weather_data=new ArrayList<String>();
        weather_data.add("Today-Sunny-88/63");
        weather_data.add("Tomorrow-Cloudy-77/34");
        weather_data.add("Yesterday-Rainy-22/27");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast_textview,
                R.id.list_item_forecast1_textview,weather_data);
        ListView listView = (ListView) rootView.findViewById(R.id.list_item_forecast1_textview);
        listView.setAdapter(arrayAdapter);

        return rootView;


    }

    public class BackgroundActivity extends AsyncTask<Void,Void,Void> {

        private final String LOG_TAG=BackgroundActivity.class.getSimpleName();
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String jsonResponse = null;


            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=036c830d3d53bd37efcb6bab20dc3d54");
                System.out.println("Yahooo");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if (inputStream == null) {
                    //return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));//reads input stream in input streamreader object and converts into bytes in buffered reader
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return  null;

                }
                jsonResponse = stringBuffer.toString();
                //Thread.sleep(1000);


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error Closing Stream", e);
                }
            }


            return null;

        }

        protected void onPostExecute(String[] result) {

        }

    }
}







