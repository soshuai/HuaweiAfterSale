package cherish.cn.huaweiaftersale.okhttp.utils;

public class NullCancellationSignal implements DownloadCancellationSignal {

    @Override
    public boolean isCancelled() {
        // TODO Auto-generated method stub
        return false;
    }

}
