package abhishek.checkinternet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import abhishek.checkinternet.Helper.CheckNetwork;


public class MainActivity extends Activity {

    /*
     * Defining the UI Components
     */
    boolean isInternetPresent = false;
    public ImageView networkConnectionImageView;
    public ImageView noNetworkConnectionImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization the UI Components
        networkConnectionImageView = (ImageView) findViewById(R.id.networkconnection);
        noNetworkConnectionImageView = (ImageView) findViewById(R.id.nonetworkconnection);
    }

    @Override
    protected void onResume() {

        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onPause() {

        super.onPause();
        unregisterReceiver(networkReceiver);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Method for Receiving the Network State
     */
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            String status = CheckNetwork.getConnectivityStatusString(context);
            if(status.equals("WIFI") || status.equals("MOBILE")) {
                isInternetPresent = true;
            } else if(status.equals("No Connection")) {
                isInternetPresent = false;
            }

            showNetworkState();
        }
    };

    /*
     * Method for display the respective Image depending on the Network State
     */
    public void showNetworkState(){
        if(isInternetPresent) {
            networkConnectionImageView.setVisibility(View.VISIBLE);
        } else {
            noNetworkConnectionImageView.setVisibility(View.VISIBLE);
        }
    }
}
