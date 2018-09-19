package com.example.oglescnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eglnn.NnNetwork;
import com.example.eglnn.layer.ConvGEMM2;
import com.example.eglnn.layer.Layer.PaddingType;
import com.example.eglnn.layer.Input;
import com.example.eglnn.layer.Layer;
import com.example.eglnn.layer.Pooling;
import com.example.eglnn.utils.DataUtils;
import com.example.eglnn.utils.TestDataCreator;
import com.example.eglnn.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private NnNetwork mNnNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildNet();

    }

    int width = 227;
    int height = width;
    int channel = 8;

    private void buildNet() {
        mNnNetwork = new NnNetwork(this);

        Layer in = new Input(this, width, height, channel);
        mNnNetwork.addLayer(in);

        Layer conv1 = new ConvGEMM2(this, in, 512, 4, 4, PaddingType.SAME, 2, 2, 0, "");
        mNnNetwork.addLayer(conv1);

//        Pooling pooling = new Pooling(this, in, 3, 3, PaddingType.SAME, 2, 2);
//        mNnNetwork.addLayer(pooling);

        mNnNetwork.initialize();

    }

    public void runNn(View view) {
        float[][][] input = TestDataCreator.createInputBuffer(new int[]{width, height, channel});
        float[][] localInput = new float[Utils.alignBy4(channel) / 4][width * height * 4];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                for (int c = 0; c < channel; c++) {
                    localInput[c / 4][(h * width + w) * 4 + c % 4] = input[c][h][w];
                }
            }
        }

        mNnNetwork.predict(localInput);
    }
}
