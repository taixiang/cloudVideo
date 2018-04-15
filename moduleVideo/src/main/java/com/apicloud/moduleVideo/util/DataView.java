package com.apicloud.moduleVideo.util;

/**
 * Created by Steven on 2015/9/10 0010.
 */
public interface DataView
{
    public void  onGetDataFailured(String msg, String requestTag);
    public void  onGetDataSuccess(String result, String requestTag);
}
