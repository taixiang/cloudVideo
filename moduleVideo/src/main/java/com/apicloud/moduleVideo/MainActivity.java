package com.apicloud.moduleVideo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apicloud.moduleVideo.bean.DataBean;
import com.apicloud.moduleVideo.util.CommonUtil;
import com.apicloud.moduleVideo.util.DataView;
import com.apicloud.moduleVideo.util.GsonUtils;
import com.apicloud.moduleVideo.util.LogUtil;
import com.apicloud.moduleVideo.util.RequestUtils;
import com.apicloud.sdk.moduleVideo.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.uzmap.pkg.uzcore.UZResourcesIDFinder;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {


    private CustomVideoView player;
    private ImageView iv;
    private Timer timer;
    private TimerTask task;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            play();
        }
    };

    private List<DataBean.Data> list = new ArrayList<>();
    private int nextType;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(UZResourcesIDFinder.getResLayoutID("activity_main"));

        player = (CustomVideoView) findViewById(UZResourcesIDFinder.getResIdID("player"));
        iv = (ImageView) findViewById(UZResourcesIDFinder.getResIdID("iv"));

        GSYVideoType.enableMediaCodec();
        GSYVideoType.enableMediaCodecTexture();

        timer = new Timer();
        initData();

        player.setStandardVideoAllCallBack(new VideoViewListener() {

            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                int videoWidth = GSYVideoManager.instance().getCurrentVideoWidth();
                int videoHeight = GSYVideoManager.instance().getCurrentVideoHeight();

                int width = CommonUtil.getWidthAndHeight(MainActivity.this)[0];
                int height = width * videoHeight / videoWidth;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) player.getLayoutParams();
                params.height = height;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                play();
            }
        });
    }

    /**
     * 网络请求
     */
    private void initData() {

        RequestUtils.getDataFromUrl(this, "http://tv.ibsci.cn/api/TvChannel/ItemList", new DataView() {
            @Override
            public void onGetDataFailured(String msg, String requestTag) {
                LogUtil.i("》》》》onGetDataFailured "+msg);
            }

            @Override
            public void onGetDataSuccess(String result, String requestTag) {

                LogUtil.i("》》》》"+result);
                DataBean dataBean = GsonUtils.jsonToClass(result, DataBean.class);
                if(dataBean.getCode() == 200){
                    list.addAll(dataBean.getData());
                    play();
                }

            }
        }, "success", false, false);

    }

    /**
     * 播放0 视频&1 图片
     */
    private void play() {
        nextType = list.get(index).getType();
        if (nextType == 0) {
            player.setUp(list.get(index).getUrl(), true, "");
            player.startPlayLogic();
            player.setVisibility(View.VISIBLE);
            iv.setVisibility(View.GONE);

        } else {
            ImageLoader.loadImage(MainActivity.this, list.get(index).getUrl(), iv);
            iv.setVisibility(View.VISIBLE);
            startNewTask();
            player.setVisibility(View.GONE);
        }
        index++;
        if (index >= list.size()) {
            index = 0;
        }
    }

    /**
     * 图片播放延迟5s
     */
    private void startNewTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(task, 5000);
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getCurPlay().release();
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (task != null) {
            task.cancel();
        }
    }

    private GSYVideoPlayer getCurPlay() {
        if (player.getFullWindowPlayer() != null) {
            return player.getFullWindowPlayer();
        }
        return player;
    }

}
