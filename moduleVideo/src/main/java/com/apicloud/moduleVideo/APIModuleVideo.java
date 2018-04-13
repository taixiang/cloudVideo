package com.apicloud.moduleVideo;

import android.content.Intent;

import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

/**
 * @author tx
 * @date 2018/4/13
 */
public class APIModuleVideo extends UZModule {
    public APIModuleVideo(UZWebView webView) {
        super(webView);
    }

    public void jsmethod_startActivity(UZModuleContext moduleContext){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

}
