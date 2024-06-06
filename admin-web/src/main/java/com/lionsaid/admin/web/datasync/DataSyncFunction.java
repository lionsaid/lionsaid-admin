package com.lionsaid.admin.web.datasync;

import com.alibaba.fastjson2.JSONObject;

public abstract class DataSyncFunction {

    public abstract DataSyncResult queryForStream(DataSyncResult dataSyncResult);

    public abstract Boolean exist(JSONObject result);

    public abstract int update(JSONObject result);

    public abstract int insert(JSONObject result);

    public abstract void close();
}
