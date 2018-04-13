package com.apicloud.moduleVideo.service;


import com.apicloud.moduleVideo.bean.BaseBean;
import com.apicloud.moduleVideo.bean.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author tx
 * @date 2018/4/12
 */
public interface AppUrlApi {
    @GET("api/TvChannel/ItemList")
    Observable<BaseBean<List<Data>>> getData();
}
