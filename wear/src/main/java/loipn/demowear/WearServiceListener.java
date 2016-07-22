package loipn.demowear;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by loipn on 7/19/2016.
 */
public class WearServiceListener extends WearableListenerService {

    public static final String ACTION_BACKGROUND_CHANGED = "ACTION_BACKGROUND_CHANGED";

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);

        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                String path = event.getDataItem().getUri().getPath();
                if (path.equals("/watch_face_config")) {
                    String message = dataMap.getString("message");
                    int num = dataMap.getInt("num", 3);
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    sendBroadcast(num);
                    Log.d("message", message);
                }
            }
        }
    }

    private void sendBroadcast(int num) {
        Intent intent = new Intent();
        intent.setAction(ACTION_BACKGROUND_CHANGED);
        intent.putExtra("num", num);
        sendBroadcast(intent);
    }
}
