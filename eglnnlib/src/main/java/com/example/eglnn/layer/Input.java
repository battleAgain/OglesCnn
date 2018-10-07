package com.example.eglnn.layer;

import android.content.Context;

import com.example.eglnn.Render;
import com.example.eglnn.utils.DataUtils;
import com.example.eglnn.utils.Utils;

import java.nio.FloatBuffer;

public class Input extends Layer {

    private FloatBuffer mOut;       // 每次只能读取一个深度上的数据
    private int mBindLayer;         // 纹理绑定深度编号

    public Input(Context context, String name, int w, int h, int c) {
        super(context, name, w, h, c);
        this.mOutShape = new int[]{w, h, c};
        this.mOut = FloatBuffer.allocate(w * h * 4);
        this.mBindLayer = 0;        // 默认绑定第一层
    }

    @Override
    public void initialize() {
        mAttachID = Layer.getDataAttachID();
        mOutTex = Render.createFloatTextureArray(mOutShape[0], mOutShape[1], Utils.alignBy4(mOutShape[2]) / 4);
    }

    @Override
    protected void bindTextureAndBuffer() {
        Render.bindTextureArray(mOutTex, mAttachID, mBindLayer);
    }

    @Override
    protected void actualForwardProc(float[][] input) {
        writeInput(input);
    }

    private void writeInput(float[][] input) {
        for (int i = 0; i < input.length; i++) {
            FloatBuffer data = FloatBuffer.wrap(input[i]);
            Render.transferToTextureArrayFloat(data, mOutTex, 0, 0, i, mOutShape[0], mOutShape[1], 1);
        }
    }

    @Override
    public float[][][] readResult() {
        float[][][] out = new float[mOutShape[2]][mOutShape[1]][mOutShape[0]];
        DataUtils.readOutput(this, mOut);
        DataUtils.transform(out, mOut.array(), mOutShape[0], mOutShape[1], mOutShape[2], mBindLayer);
        return out;
    }

}
