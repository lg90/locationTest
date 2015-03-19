package uk.co.leifgehrmann.locationtest;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationStats extends ActionBarActivity {

    SimpleDateFormat df; // Formatting dates
    WebView mapDisplay; // Displays the user position

    /** DECLARE VARIABLES HERE **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_stats);

        // Initialises the web view and loads a website which will display a circle, representing
        // the location reported by the GPS and NETWORK provider.
        mapDisplay = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mapDisplay.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mapDisplay.addJavascriptInterface(new JSInterface(), "AndroidErrorReporter");
        mapDisplay.loadUrl("http://gps.leifgehrmann.co.uk/map/");

        // Formats the date on the display page
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        /** INSERT CODE HERE **/
    }

    // Updates the position data in the app.
    public void updateUserInterface(String name, Location location){
        if(location!=null){
            String time = df.format(new Date(location.getTime()));
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Double altitude = location.getAltitude();
            Float speed = location.getSpeed();
            Float bearing = location.getBearing();
            Float accuracy = location.getAccuracy();

            TextView time_value   = (TextView)findViewById(getResId(name + "_time_value"));
            TextView latitude_value   = (TextView)findViewById(getResId(name + "_latitude_value"));
            TextView longitude_value   = (TextView)findViewById(getResId(name + "_longitude_value"));
            TextView altitude_value   = (TextView)findViewById(getResId(name + "_altitude_value"));
            TextView speed_value   = (TextView)findViewById(getResId(name + "_speed_value"));
            TextView bearing_value   = (TextView)findViewById(getResId(name + "_bearing_value"));
            TextView accuracy_value   = (TextView)findViewById(getResId(name + "_accuracy_value"));

            time_value.setText(time);
            latitude_value.setText(latitude.toString());
            longitude_value.setText(longitude.toString());
            altitude_value.setText(altitude.toString());
            speed_value.setText(speed.toString());
            bearing_value.setText(bearing.toString());
            accuracy_value.setText(accuracy.toString());

            String color = "#000";
            if(name.equals("GPS_PROVIDER")){
                color = "#F00";
            } else if(name.equals("NETWORK_PROVIDER")){
                color = "#0F0";
            }

            if(!name.equals("PASSIVE_PROVIDER")){
                callJavaScript("addCircle",name,longitude,latitude,accuracy,color);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Location found for "+name+" :(", Toast.LENGTH_LONG).show();
        }
    }

    // Appends lines to a text field
    public void updateUserInterfaceGPSNMEA(String message){
        int max = 1000;
        TextView NMEA_value   = (TextView)findViewById(R.id.GPS_PROVIDER_NMEA_value);
        String text = NMEA_value.getText().toString();
        text+="\n"+message;
        text = text.substring(Math.max(0,text.length()-max), text.length());
        NMEA_value.setText(text);
    }

    private void callJavaScript(String methodName, Object...params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        boolean start = true;
        for (Object param : params) {
            if(!start)
                stringBuilder.append(",");
            if(param instanceof String){
                stringBuilder.append("'");
                stringBuilder.append(param);
                stringBuilder.append("'");
            } else if(param instanceof Integer || param instanceof Float || param instanceof Double) {
                stringBuilder.append(param);
            }
            start = false;
        }
        stringBuilder.append(")}catch(error){Android.onError(error.message);}");
        Log.d("data",stringBuilder.toString());
        mapDisplay.loadUrl(stringBuilder.toString());
    }

    public int getResId(String resName) {
        return this.getResources().getIdentifier(resName, "id", this.getPackageName());
    }
}
