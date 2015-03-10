package uk.co.leifgehrmann.locationtest;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.GpsStatus;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class LocationStats extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "uk.co.leifgehrmann.locationtest.MESSAGE";
    /*private GPSTracker gps;*/

    // Acquire a reference to the system Location Manager
    LocationManager locationManagerNetwork, locationManagerGPS, locationManagerPassive;

    // Define a listener that responds to location updates
    LocationListener locationListenerNetwork, locationListenerGPS, locationListenerPassive;
    GpsStatus.NmeaListener locationListenerGPSNMEA;

    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_stats);

        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        locationManagerNetwork = (LocationManager) this.getSystemService(LocationStats.LOCATION_SERVICE);
        locationManagerGPS = (LocationManager) this.getSystemService(LocationStats.LOCATION_SERVICE);
        locationManagerPassive = (LocationManager) this.getSystemService(LocationStats.LOCATION_SERVICE);
        locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location location) { updateUserInterfaceNetwork(location);}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        locationListenerGPS = new LocationListener() {
            public void onLocationChanged(Location location) { updateUserInterfaceGPS(location);}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        locationListenerPassive = new LocationListener() {
            public void onLocationChanged(Location location) { updateUserInterfacePassive(location);}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        locationManagerNetwork.addNmeaListener(new GpsStatus.NmeaListener() {
            @Override
            public void onNmeaReceived(long timestamp, String message) {
                updateUserInterfaceGPSNMEA(message);
            }
        });

        // Register the listener with the Location Manager to receive location updates
        locationManagerNetwork.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        locationManagerGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        locationManagerPassive.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListenerPassive);

        updateUserInterfaceNetwork(locationManagerNetwork.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        updateUserInterfaceNetwork(locationManagerGPS.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        updateUserInterfaceNetwork(locationManagerPassive.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_stats, menu);
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

    public void start_gps(View view) {
       /*if(gps==null) {
            gps = new GPSTracker(LocationStats.this);
        }

        // check if GPS enabled
       if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            List<Address> addresses = gps.getAddress();
            String addr = "none :(";
            if(addresses!=null) {
                Address address = addresses.get(0);
                addr = address.toString();
            }

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude + "\n which was identified as the address: \n"+addr, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
       }*/
    }

    public void stop_gps(View view) {
        /*gps.stopUsingGPS();*/
    }

    public void updateUserInterfaceGPS(Location location){
        if(location!=null){

            String time = df.format(new Date(location.getTime()));
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Double altitude = location.getAltitude();
            Float speed = location.getSpeed();
            Float bearing = location.getBearing();
            Float accuracy = location.getAccuracy();

            TextView time_value   = (TextView)findViewById(R.id.GPS_PROVIDER_time_value);
            TextView latitude_value   = (TextView)findViewById(R.id.GPS_PROVIDER_latitude_value);
            TextView longitude_value   = (TextView)findViewById(R.id.GPS_PROVIDER_longitude_value);
            TextView altitude_value   = (TextView)findViewById(R.id.GPS_PROVIDER_altitude_value);
            TextView speed_value   = (TextView)findViewById(R.id.GPS_PROVIDER_speed_value);
            TextView bearing_value   = (TextView)findViewById(R.id.GPS_PROVIDER_bearing_value);
            TextView accuracy_value   = (TextView)findViewById(R.id.GPS_PROVIDER_accuracy_value);

            time_value.setText(time.toString());
            latitude_value.setText(latitude.toString());
            longitude_value.setText(longitude.toString());
            altitude_value.setText(altitude.toString());
            speed_value.setText(speed.toString());
            bearing_value.setText(bearing.toString());
            accuracy_value.setText(accuracy.toString());

        } else {
            Toast.makeText(getApplicationContext(), "No GPS Location not found :(", Toast.LENGTH_LONG).show();
        }
    }
    public void updateUserInterfaceNetwork(Location location){
        if(location!=null){

            String time = df.format(new Date(location.getTime()));
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Double altitude = location.getAltitude();
            Float speed = location.getSpeed();
            Float bearing = location.getBearing();
            Float accuracy = location.getAccuracy();

            TextView time_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_time_value);
            TextView latitude_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_latitude_value);
            TextView longitude_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_longitude_value);
            TextView altitude_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_altitude_value);
            TextView speed_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_speed_value);
            TextView bearing_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_bearing_value);
            TextView accuracy_value   = (TextView)findViewById(R.id.NETWORK_PROVIDER_accuracy_value);

            time_value.setText(time.toString());
            latitude_value.setText(latitude.toString());
            longitude_value.setText(longitude.toString());
            altitude_value.setText(altitude.toString());
            speed_value.setText(speed.toString());
            bearing_value.setText(bearing.toString());
            accuracy_value.setText(accuracy.toString());

        } else {
            Toast.makeText(getApplicationContext(), "No Network Location not found :(", Toast.LENGTH_LONG).show();
        }
    }
    public void updateUserInterfacePassive(Location location){
        if(location!=null){

            String time = df.format(new Date(location.getTime()));
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            Double altitude = location.getAltitude();
            Float speed = location.getSpeed();
            Float bearing = location.getBearing();
            Float accuracy = location.getAccuracy();

            TextView time_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_time_value);
            TextView latitude_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_latitude_value);
            TextView longitude_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_longitude_value);
            TextView altitude_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_altitude_value);
            TextView speed_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_speed_value);
            TextView bearing_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_bearing_value);
            TextView accuracy_value   = (TextView)findViewById(R.id.PASSIVE_PROVIDER_accuracy_value);

            time_value.setText(time.toString());
            latitude_value.setText(latitude.toString());
            longitude_value.setText(longitude.toString());
            altitude_value.setText(altitude.toString());
            speed_value.setText(speed.toString());
            bearing_value.setText(bearing.toString());
            accuracy_value.setText(accuracy.toString());

        } else {
            Toast.makeText(getApplicationContext(), "No Passive Location not found :(", Toast.LENGTH_LONG).show();
        }
    }

    public void updateUserInterfaceGPSNMEA(String message){
            TextView NMEA_value   = (TextView)findViewById(R.id.GPS_PROVIDER_NMEA_value);
            NMEA_value.setText(message);
    }
}
