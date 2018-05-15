package cherish.cn.huaweiaftersale.okhttp.utils;

import android.os.Environment;

import java.io.File;

public class CaseRecordUtils {

    public static final String BASE_DIR        = Environment.getExternalStorageDirectory().getAbsolutePath()
                                                       + "/vidyosample/";

    public static final String IMAGE_DIR       = BASE_DIR + "imgs/";
    public static final String AUDIO_DIR       = BASE_DIR + "audios/";
    public static final String VIDEO_DIR       = BASE_DIR + "videos/";
    public static final String UPLOAD_DIR      = BASE_DIR + "upRecorder/";
    public static final String DOWNLOAD_DIR    = BASE_DIR + "download/";
    public static final String AUTO_UPLOAD_DIR = BASE_DIR + "autoupload/";
    public static final String WATER_MARK_DIR  = BASE_DIR + "watermark/";

    public static final int    LOAD_SUCCESS    = 0;
    public static final int    LOAD_FAILED     = 1;
    public static final int    LOAD_CANCELLED  = 2;

    public static final int    timer_interval  = 10000;

    static {
        makeDirectory(IMAGE_DIR);
        makeDirectory(AUDIO_DIR);
        makeDirectory(VIDEO_DIR);
        makeDirectory(UPLOAD_DIR);
        makeDirectory(AUTO_UPLOAD_DIR);
        makeDirectory(DOWNLOAD_DIR);
        makeDirectory(WATER_MARK_DIR);
    }

    private static final void makeDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}
