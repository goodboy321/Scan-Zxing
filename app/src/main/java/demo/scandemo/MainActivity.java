package demo.scandemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.btn2)
    Button btn2;
    @InjectView(R.id.btn1)
    Button btn1;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.tv_result)
    TextView tvResult;
    @InjectView(R.id.image_callback)
    ImageView imageCallback;
    private Context mContext;
    private final static int REQ_CODE = 1028;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mContext = this;
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.image, R.id.tv_result, R.id.image_callback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.btn2:
                image.setVisibility(View.VISIBLE);
                //隐藏扫码结果view
                imageCallback.setVisibility(View.GONE);
                tvResult.setVisibility(View.GONE);

                String content = et.getText().toString().trim();
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtils.create2DCode(content);//根据内容生成二维码
                    tvResult.setVisibility(View.GONE);
                    image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image:
                break;
            case R.id.tv_result:
                break;
            case R.id.image_callback:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            image.setVisibility(View.GONE);
            tvResult.setVisibility(View.VISIBLE);
            imageCallback.setVisibility(View.VISIBLE);

            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);

            tvResult.setText("扫码结果："+result);
            showToast("扫码结果：" + result);
            if(bitmap != null){
                imageCallback.setImageBitmap(bitmap);//现实扫码图片
            }
        }


    }

    private void showToast(String msg) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
}
