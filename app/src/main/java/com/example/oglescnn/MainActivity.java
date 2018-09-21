package com.example.oglescnn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eglnn.NnNetwork;
import com.example.eglnn.layer.Concat2;
import com.example.eglnn.layer.ConvGEMM2;
import com.example.eglnn.layer.Expand;
import com.example.eglnn.layer.Layer.PaddingType;
import com.example.eglnn.layer.Input;
import com.example.eglnn.layer.Layer;
import com.example.eglnn.layer.Pooling;
import com.example.eglnn.utils.TestDataCreator;
import com.example.eglnn.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private NnNetwork mNnNetwork;

    int width = 227;
    int height = width;
    int channel = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildSqueezeNet();
//        buildSqueezeNet2();


//        buildTestNet();
    }

    private void buildSqueezeNet2() {
        mNnNetwork = new NnNetwork(this);

        Layer in = new Input(this, width, height, channel);
        mNnNetwork.addLayer(in);

        Layer conv1 = new ConvGEMM2(this, in, 64, 3, 3, PaddingType.VALID, 2, 2, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv1);

        Pooling pooling1 = new Pooling(this, conv1, 3, 3, PaddingType.VALID, 2, 2);
        mNnNetwork.addLayer(pooling1);

        // fire2
        Layer conv2_squeeze = new ConvGEMM2(this, pooling1, 16, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv2_squeeze);

        Layer expand2 = new Expand(this, conv2_squeeze, 64, Layer.ActiveType.RELU, "", "");
        mNnNetwork.addLayer(expand2);

        // fire3
        Layer conv3_squeeze = new ConvGEMM2(this, expand2, 16, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv3_squeeze);

        Layer expand3 = new Expand(this, conv3_squeeze, 64, Layer.ActiveType.RELU, "", "");
        mNnNetwork.addLayer(expand3);

        Pooling pooling3 = new Pooling(this, expand3, 3, 3, PaddingType.VALID, 2, 2);
        mNnNetwork.addLayer(pooling3);


        // fire4
//        Layer conv4_squeeze = new ConvGEMM2(this, pooling3, 32, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv4_squeeze);
//
//        Layer expand4 = new Expand(this, conv4_squeeze, 128, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand4);

        // fire5
//        Layer conv5_squeeze = new ConvGEMM2(this, expand4, 32, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv5_squeeze);
//
//        Layer expand5 = new Expand(this, conv5_squeeze, 128, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand5);
//
//        Pooling pooling5 = new Pooling(this, expand5, 3, 3, PaddingType.VALID, 2, 2);
//        mNnNetwork.addLayer(pooling5);

        // fire6
//        Layer conv6_squeeze = new ConvGEMM2(this, pooling5, 48, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv6_squeeze);
//
//        Layer expand6 = new Expand(this, conv6_squeeze, 192, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand6);

        // fire7
//        Layer conv7_squeeze = new ConvGEMM2(this, expand6, 48, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv7_squeeze);
//
//        Layer expand7 = new Expand(this, conv7_squeeze, 192, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand7);

        // fire8
//        Layer conv8_squeeze = new ConvGEMM2(this, expand7, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv8_squeeze);
//
//        Layer expand8 = new Expand(this, conv8_squeeze, 256, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand8);

        // fire9
//        Layer conv9_squeeze = new ConvGEMM2(this, expand8, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
//        mNnNetwork.addLayer(conv9_squeeze);
//
//        Layer expand9 = new Expand(this, conv9_squeeze, 256, Layer.ActiveType.RELU, "", "");
//        mNnNetwork.addLayer(expand9);

        mNnNetwork.initialize();

    }

    private void buildTestNet() {
        mNnNetwork = new NnNetwork(this);

        Layer in = new Input(this, width, height, channel);
        mNnNetwork.addLayer(in);


        Layer conv1 = new ConvGEMM2(this, in, 64, 3, 3, PaddingType.VALID, 2, 2, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv1);

        mNnNetwork.initialize();
    }

    private void buildSqueezeNet() {
        mNnNetwork = new NnNetwork(this);

        Layer in = new Input(this, width, height, channel);
        mNnNetwork.addLayer(in);


        Layer conv1 = new ConvGEMM2(this, in, 64, 3, 3, PaddingType.VALID, 2, 2, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv1);

        Pooling pooling1 = new Pooling(this, conv1, 3, 3, PaddingType.VALID, 2, 2);
        mNnNetwork.addLayer(pooling1);

        // fire2
        Layer conv2_squeeze = new ConvGEMM2(this, pooling1, 16, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv2_squeeze);

        Layer conv2_1 = new ConvGEMM2(this, conv2_squeeze, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv2_1);

        Layer conv2_2 = new ConvGEMM2(this, conv2_squeeze, 64, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv2_2);

        Concat2 concat2 = new Concat2(this, new Layer[]{conv2_1, conv2_2}, 2);
        mNnNetwork.addLayer(concat2);

        // fire3
        Layer conv3_squeeze = new ConvGEMM2(this, concat2, 16, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv3_squeeze);

        Layer conv3_1 = new ConvGEMM2(this, conv3_squeeze, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv3_1);

        Layer conv3_2 = new ConvGEMM2(this, conv3_squeeze, 64, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv3_2);

        Concat2 concat3 = new Concat2(this, new Layer[]{conv3_1, conv3_2}, 2);
        mNnNetwork.addLayer(concat3);

        Pooling pooling3 = new Pooling(this, concat3, 3, 3, PaddingType.VALID, 2, 2);
        mNnNetwork.addLayer(pooling3);

        // fire4
        Layer conv4_squeeze = new ConvGEMM2(this, pooling3, 32, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv4_squeeze);

        Layer conv4_1 = new ConvGEMM2(this, conv4_squeeze, 128, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv4_1);

        Layer conv4_2 = new ConvGEMM2(this, conv4_squeeze, 128, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv4_2);

        Concat2 concat4 = new Concat2(this, new Layer[]{conv4_1, conv4_2}, 2);
        mNnNetwork.addLayer(concat4);

        // fire5
        Layer conv5_squeeze = new ConvGEMM2(this, concat4, 32, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv5_squeeze);

        Layer conv5_1 = new ConvGEMM2(this, conv5_squeeze, 128, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv5_1);

        Layer conv5_2 = new ConvGEMM2(this, conv5_squeeze, 128, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv5_2);

        Concat2 concat5 = new Concat2(this, new Layer[]{conv5_1, conv5_2}, 2);
        mNnNetwork.addLayer(concat5);

        Pooling pooling5 = new Pooling(this, concat5, 3, 3, PaddingType.VALID, 2, 2);
        mNnNetwork.addLayer(pooling5);

        // fire6
        Layer conv6_squeeze = new ConvGEMM2(this, pooling5, 48, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv6_squeeze);

        Layer conv6_1 = new ConvGEMM2(this, conv6_squeeze, 192, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv6_1);

        Layer conv6_2 = new ConvGEMM2(this, conv6_squeeze, 192, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv6_2);

        Concat2 concat6 = new Concat2(this, new Layer[]{conv6_1, conv6_2}, 2);
        mNnNetwork.addLayer(concat6);

        // fire7
        Layer conv7_squeeze = new ConvGEMM2(this, concat6, 48, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv7_squeeze);

        Layer conv7_1 = new ConvGEMM2(this, conv7_squeeze, 192, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv7_1);

        Layer conv7_2 = new ConvGEMM2(this, conv7_squeeze, 192, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv7_2);

        Concat2 concat7 = new Concat2(this, new Layer[]{conv7_1, conv7_2}, 2);
        mNnNetwork.addLayer(concat7);

        // fire8
        Layer conv8_squeeze = new ConvGEMM2(this, concat7, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv8_squeeze);

        Layer conv8_1 = new ConvGEMM2(this, conv8_squeeze, 256, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv8_1);

        Layer conv8_2 = new ConvGEMM2(this, conv8_squeeze, 256, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv8_2);

        Concat2 concat8 = new Concat2(this, new Layer[]{conv8_1, conv8_2}, 2);
        mNnNetwork.addLayer(concat8);

        // fire9
        Layer conv9_squeeze = new ConvGEMM2(this, concat8, 64, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv9_squeeze);

        Layer conv9_1 = new ConvGEMM2(this, conv9_squeeze, 256, 1, 1, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv9_1);

        Layer conv9_2 = new ConvGEMM2(this, conv9_squeeze, 256, 3, 3, PaddingType.SAME, 1, 1, Layer.ActiveType.RELU, "");
        mNnNetwork.addLayer(conv9_2);

        Concat2 concat9 = new Concat2(this, new Layer[]{conv9_1, conv9_2}, 2);
        mNnNetwork.addLayer(concat9);

        mNnNetwork.initialize();
    }

    public void runNn(View view) {
        float[][][] input = TestDataCreator.createInputBuffer(new int[]{width, height, channel}, 0);
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
