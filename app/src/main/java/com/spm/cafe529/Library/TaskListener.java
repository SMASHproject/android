package com.spm.cafe529.Library;

import org.json.JSONObject;

/**
 * Created by park on 2015. 6. 15..
 */
public interface TaskListener  {

    public void onReceived(JSONObject json);

    public void onCancelled();
}
