package cherish.cn.huaweiaftersale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cherish.cn.huaweiaftersale.base.ApiHelper;
import cherish.cn.huaweiaftersale.base.BaseActivity;
import cherish.cn.huaweiaftersale.bean.WorkBean;
import cherish.cn.huaweiaftersale.callback.DataCallback;
import cherish.cn.huaweiaftersale.entity.GetRecordInfo;
import cherish.cn.huaweiaftersale.entity.OrderGetrecordDataEntity;
import cherish.cn.huaweiaftersale.util.AppException;
import cherish.cn.huaweiaftersale.util.ImageUtils;
import cherish.cn.huaweiaftersale.util.StringUtils;

public class ManageActivity extends BaseActivity implements View.OnClickListener,DataCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.top_custom_title)
    TextView title;
    @BindView(R.id.tv_time)
    TextView time;
    @BindView(R.id.tv_location)
    TextView location;
    @BindView(R.id.tv_name)
    TextView  name;
    @BindView(R.id.tv_phone)
    TextView phone;
    @BindView(R.id.manage)
    TextView manage;
    @BindView(R.id.tv_states)
    TextView state;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    private final String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/jkls/Portrait/";
    private String mProtraitPath;
    private Uri mCropUri;
    private File mProtraitFile;
    private Uri mOrgUri;
    private final static int CROP = 200;
    private String id;
    List<String> imgList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        manage.setOnClickListener(this);
        back.setOnClickListener(this);
        photo.setOnClickListener(this);
        title.setText("处理工单");
        String recordId=getIntent().getStringExtra("recordId");
        Bundle bundle = new Bundle();
        bundle.putString("recordId", recordId);
        ApiHelper.load(mContext, R.id.api_order_getRecord, bundle, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.photo:
                startActionCamera();
                break;
            case R.id.manage:
                doSubmit();
                break;
            default:
                break;
        }
    }

    private void doSubmit() {
        Map<String,String> map=new HashMap<>();
        Map<String,File> files=new HashMap<>();
        map.put("","");
        ApiHelper.submit(mContext,R.id.api_order_ajaxSaveFile,map,this,files);
    }

    @Override
    public void onFailure(int funcKey, Bundle bundle, AppException appe) {}

    @Override
    public void onSuccess(int funcKey, Bundle bundle, Object data) {
        if (funcKey==R.id.api_order_getRecord){
            List<OrderGetrecordDataEntity> list= (List<OrderGetrecordDataEntity>) data;
            OrderGetrecordDataEntity entity=list.get(0);
            GetRecordInfo info=entity.getInfo();
            if (info!=null){
               name.setText(info.getUserName());
               phone.setText(info.getUserMobile());
               time.setText(getTime(info.getMinute()));
               location.setText(info.getMeetingName());
               state.setText(info.getStatus());
               id=info.getRecordId();
            }
        }
    }

    private String getTime(int time) {
        //计算天 1天 = 24*60 1小时=60
        int day = time/(24*60);
        int hour = (time%(24*60))/60;
        int minute = (time%(24*60))%60;
        if (day>0){
            return day+"天"+hour+"小时前";//+minute+"分钟前";
        }else if (hour>0){
            return hour+"小时前";//+minute+"分钟前";
        }else{
            return minute+"分钟前";
        }
    }

    /**
     * 上传新照片
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        Log.e("recode", resultCode + "结果");
        if (resultCode != -1)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(mOrgUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                //照片上传到服务器
                Log.i("AAAA", "照片上传到服务器" + mCropUri);
                imgList.add(mProtraitPath);
                notifyUI();
                break;
        }
    }

    private void notifyUI() {
        if (imgList.size() > 0) {
            setImg(imgList.get(0), img1);
            if (imgList.size() > 1) {
                setImg(imgList.get(1), img2);
                if (imgList.size() >2) {
                    setImg(imgList.get(2), img3);
                    if (imgList.size()>3)
                        setImg(imgList.get(3), img4);
                }
            }
        }
    }
    private void setImg(String path, ImageView img) {
        File file = new File(path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);
        }
    }

    /**
     * 相机拍照
     */
    private void startActionCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    /**
     * 拍照保存的绝对路径
     *
     * @return
     */
    private Uri getCameraTempFile() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            Toast.makeText(mContext, "无法保存上传的图片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 照片命名
        String cropFileName = "autogreen_camera_" + timeStamp + ".jpg";
        // 裁剪图片的绝对路径
        mProtraitPath = FILE_SAVEPATH + cropFileName;
        mProtraitFile = new File(mProtraitPath);

        /////

        mCropUri = Uri.fromFile(mProtraitFile);
        this.mOrgUri = this.mCropUri;
        return this.mCropUri;
    }

    /**
     * 裁剪
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");// 开启剪裁
        intent.putExtra("aspectX", 1.1);// 裁剪框比例
        intent.putExtra("aspectY", 1.1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    /**
     * 裁剪头像的绝对路径
     *
     * @param uri
     * @return
     */
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            Toast.makeText(mContext, "无法保存上传的头像，可能是存储空间已满。", Toast.LENGTH_SHORT).show();
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (TextUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(mContext, uri);
        }
        String ext = getFileFormat(thePath);
        ext = TextUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        Log.i("AAAA", "id==" + id);
        String cropFileName = id + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        mProtraitPath = FILE_SAVEPATH + cropFileName;
        mProtraitFile = new File(mProtraitPath);

        mCropUri = Uri.fromFile(mProtraitFile);
        return this.mCropUri;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    private String getFileFormat(String fileName) {
        if (StringUtils.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

}
