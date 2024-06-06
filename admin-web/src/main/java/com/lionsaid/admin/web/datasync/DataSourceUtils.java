package com.lionsaid.admin.web.datasync;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.business.model.po.DataSyncJob;
import com.lionsaid.admin.web.business.model.po.DataSyncJobFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class DataSourceUtils {
    private DataSyncFunction sourceDataSyncFunction;
    private DataSyncFunction targetDataSyncFunction;

    public DataSourceUtils(DataSyncDataSource source, DataSyncDataSource target, DataSyncJob dataSyncJob, Map<String, List<DataSyncJobFilter>> filter, JSONArray failInfo) {
        if (source.getDriverClassName().contains("jdbc")) {
            sourceDataSyncFunction = new JDBCDataSyncFunction(filter, source, dataSyncJob, failInfo);
        }
        if (target.getDriverClassName().contains("jdbc")) {
            targetDataSyncFunction = new JDBCDataSyncFunction(filter, target, dataSyncJob, failInfo);
        }
    }


    public DataSyncResult queryForStream(DataSyncResult dataSyncResult) {
        return sourceDataSyncFunction.queryForStream(dataSyncResult);
    }

    public Boolean exist(JSONObject result) {
        return targetDataSyncFunction.exist(result);
    }

    public int update(JSONObject result) {
        return targetDataSyncFunction.update(result);
    }

    public int insert(JSONObject result) {
        return targetDataSyncFunction.insert(result);
    }

    public void close() {
        sourceDataSyncFunction.close();
        targetDataSyncFunction.close();
    }


}
