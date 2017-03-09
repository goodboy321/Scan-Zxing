
# Scan-Zxing使用方法
可以生成二维码，扫描二维码和相册中的二维码


1. Add it in your root build.gradle at the end of repositories:
在project的build.gradle添加如下代码：	

		allprojects {
			repositories {
				maven { url 'https://jitpack.io' }
			}
		}

2. Add the dependency
在build.gradle添加依赖：	

		dependencies {
			compile 'com.github.goodboy321:Scan-Zxing:1.0'
		}



布局：

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <EditText
        android:id="@+id/et"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="输入内容，生成二维码"
        android:text="http://www.baidu.com" />

    <Button
        android:background="@color/colorAccent"
        android:id="@+id/btn2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="生成二维码" />

    <Button
        android:layout_marginTop="10dp"
        android:background="@color/colorPrimaryDark"
        android:id="@+id/btn1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="扫码（识别相册中二维码）" />

    <ImageView
        android:id="@+id/image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal" />


    <TextView
        android:id="@+id/tv_result"
        android:text="识别的二维码"
        android:gravity="center"
        android:textSize="18sp"
        android:textColor="#FF0000"
        android:layout_marginTop="30dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <ImageView
        android:id="@+id/image_callback"
        android:layout_marginTop="10dp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center_horizontal" />
    
</LinearLayout>

主方法：

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


具体需求可修改源码
