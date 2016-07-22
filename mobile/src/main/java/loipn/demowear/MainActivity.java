package loipn.demowear;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private TextView btnSend;
    private TextView btnSend2;
    private TextView btnSend3;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        btnSend = (TextView) findViewById(R.id.btnSend);
        btnSend2 = (TextView) findViewById(R.id.btnSend2);
        btnSend3 = (TextView) findViewById(R.id.btnSend3);

        btnSend.setOnClickListener(this);
        btnSend2.setOnClickListener(this);
        btnSend3.setOnClickListener(this);
    }

    private void initData() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                sendData(1);
                break;
            case R.id.btnSend2:
                sendData(2);
                break;
            case R.id.btnSend3:
                sendData(3);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void sendData(int num) {
        i++;
        if (mGoogleApiClient == null)
            return;

        final PutDataMapRequest putRequest = PutDataMapRequest.create("/watch_face_config");
        final DataMap map = putRequest.getDataMap();
        map.putString("message", "MyWatchface" + i);
        map.putInt("num", num);
        Wearable.DataApi.putDataItem(mGoogleApiClient, putRequest.asPutDataRequest());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
