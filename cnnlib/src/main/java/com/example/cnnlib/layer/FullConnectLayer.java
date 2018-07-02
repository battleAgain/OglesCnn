package com.example.cnnlib.layer;

import android.content.Context;
import android.util.Log;

import com.example.cnnlib.render.ComputeRender;
import com.example.cnnlib.utils.DataUtils;
import com.example.cnnlib.utils.NetUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.example.cnnlib.render.ComputeRender.initFullConnPro;

/**
 * 全连接前接 flat 或 全连接层 kennel 个数不超过4096
 * 全连接层的 输出 和 kennel 都需要 4 对齐
 */
public class FullConnectLayer extends Layer {

    private static final String TAG = "FullConnectLayer";

    private List<float[]> mKennels;
    private int mShaderPro;
    private int mKennelAmount;                      // kennel 个数

    private int[] mParams;

    private NonLinearLayer.NonLinearType mType;
    private int[] mKennelBuffer = new int[1];

    public FullConnectLayer(Context context, Layer preLayer, int kennelAmount, NonLinearLayer.NonLinearType type) {
        super(context, kennelAmount, preLayer);
        this.mKennelAmount = kennelAmount;
        this.mType = type;
    }

    @Override
    public void initialize() {
        initFullConnect();
    }

    private void initFullConnect() {
        Log.w(TAG,"initFullConnect");
        int[] inputShape = mPreLayer.getOutputShape();
        int kennelSize = inputShape[0] * inputShape[1] * inputShape[2] + 1;
        int alignKennelSize =
                NetUtils.alignBy4(inputShape[0] * inputShape[1] * inputShape[2]) + 1;
        mShaderPro = initFullConnPro(mContext, "full_connect.comp", alignKennelSize, mKennelAmount, mOutputShape[0], 1, 1);
        mAttachID = Layer.getDataAttachID();
        mOutTex = ComputeRender.createTexture();


        int kennelBufSize = alignKennelSize * mKennelAmount;
        mKennels = createKennels(alignKennelSize, kennelSize);

        mKennelBuffer[0] = ComputeRender.initKennelBuffer(kennelBufSize);
        transferKennelToBuffer();

        mParams = new int[7];
        mParams[0] = inputShape[0];
        mParams[1] = inputShape[1];
        mParams[2] = inputShape[2];
        mParams[3] = mOutputShape[0];
        mParams[4] = mOutputShape[1];
        mParams[5] = mOutputShape[2];
        mParams[6] = mType.index;
    }

    private void transferKennelToBuffer() {
        for (int i = 0; i < mKennels.size(); i++) {
            float[] kennel = mKennels.get(i);
            int offset = i * kennel.length;
            ComputeRender.transferToBuffer(FloatBuffer.wrap(kennel), mKennelBuffer[0], offset);
        }
    }

    private List<float[]> createKennels(int alignSize, int kennelSize) {
        List<float[]> kennels = new ArrayList<>();
        for (int i = 0; i < mKennelAmount; i++) {
            float[] kennel = DataUtils.createFullConnKennel(alignSize, kennelSize, i);
            kennels.add(kennel);
        }
        return kennels;
    }

    @Override
    protected void bindTextureAndBuffer() {
        ComputeRender.bindTextureAndBuffer(mOutTex, mAttachID, mKennelBuffer[0]);
    }

    @Override
    protected void actualForwardProc(float[][][] input) {
        ComputeRender.performFullConnect(mShaderPro, mParams, mPreLayer.getOutTex(), mOutTex, mKennelBuffer[0]);
    }

}
