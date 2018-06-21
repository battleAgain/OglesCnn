package com.example.cnnlib;

import android.util.Log;

import com.example.cnnlib.eglenv.GLES31BackEnv;
import com.example.cnnlib.layer.Layer;
import com.example.cnnlib.utils.DataUtils;

import java.util.ArrayList;

import static com.example.cnnlib.utils.Constants.S_TEXTURE_SIZE;

/**
 * 网络最多只能保存7层数据
 */
public class CnnNetwork {

    private static final String TAG = "CnnNetwork";

    private ArrayList<Layer> mLayers;

    private boolean mIsInit;
    private GLES31BackEnv mGLes31BackEnv;

    public CnnNetwork() {
        init();
        this.mLayers = new ArrayList<>();
    }

    private void init() {
        mGLes31BackEnv = new GLES31BackEnv(S_TEXTURE_SIZE, S_TEXTURE_SIZE);
    }

    public void addLayer(Layer layer) {
        mLayers.add(layer);
    }

    private void initialize() {
        if (!mIsInit) {
            mIsInit = true;
            for (Layer layer : mLayers) {
                layer.initialize();
            }
        }
    }

    public void removeLayer(Layer layer) {
        mLayers.remove(layer);
    }

    public void run() {
        mGLes31BackEnv.post(new Runnable() {
            @Override
            public void run() {
                actualRun();
            }
        });
    }

    private void actualRun() {
        initialize();
        StringBuilder sb = new StringBuilder();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < mLayers.size(); i++) {
            long begin1 = System.currentTimeMillis();
            mLayers.get(i).forwardProc();
            sb.append(i).append(":").append(System.currentTimeMillis() - begin1).append("; ");
        }
        Log.d(TAG, sb.toString());
        Log.w(TAG, "----- total spent:" + (System.currentTimeMillis() - begin));
        actualReadOutput();
    }

    public void readOutput() {
        mGLes31BackEnv.post(new Runnable() {
            @Override
            public void run() {
                actualReadOutput();
            }
        });
    }

    private void actualReadOutput() {
        Layer layer = mLayers.get(mLayers.size() - 1);
        DataUtils.readOutput(layer);
    }


}
