package cherish.cn.huaweiaftersale.net.callback;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class DownloadCallback implements Callback {

    private String filePath;
    public DownloadCallback(String filePath){
        this.filePath = filePath;
    }
    @Override
    public final void onFailure(Request request, IOException e) {
        onError(e.getLocalizedMessage());
    }

    @Override
    public final void onResponse(Response response) throws IOException {
        long total = response.body().contentLength();
        if(total > 0) {
            InputStream is = response.body().byteStream();

            byte[] buf = new byte[1024];
            int len = -1;
            int sum = 0;

            File file = new File(filePath);
            if (file.createNewFile()) {
                onStart();
                FileOutputStream fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    sum += len;
                    fos.write(buf, 0, len);

                    onDownloading(total, sum);
                }
                fos.flush();
                fos.close();

                onSuccess(file);
            } else {
                onError("下载文件创建失败");
            }
        }else{
            onError("空文件");
        }
    }

    protected abstract void onStart();

    protected abstract void onSuccess(File file);

    protected abstract void onDownloading(long total, int downloaded);

    protected abstract void onError(String msg);
}
