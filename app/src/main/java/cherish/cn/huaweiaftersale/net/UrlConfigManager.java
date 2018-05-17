package cherish.cn.huaweiaftersale.net;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.SparseArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

import cherish.cn.huaweiaftersale.R;
import cherish.cn.huaweiaftersale.util.LogUtils;

public final class UrlConfigManager {
    // 便于查找
    private SparseArray<UrlData> mUrlMapping;

    private Object mLock = new Object();

    private static UrlConfigManager sInstance;

    private String mDefaultVersion;
    private String mTokenName;
    private String mSignName;

    private boolean mDebug;
    private String mDebugBaseHttpUrl;
    private String mReleaseBaseHttpUrl;
    private String mDebugBaseHttpsUrl;
    private String mReleaseBaseHttpsUrl;


    private UrlConfigManager() {
        super();

        mUrlMapping = new SparseArray<UrlData>();
    }

    public static synchronized UrlConfigManager getInstance() {
        if (sInstance == null) {
            sInstance = new UrlConfigManager();
        }
        return sInstance;
    }

    /**
     * 读取配置文件，并生成所有的url配置
     * @param context
     */
    private void fetchUrlDataFromXml(final Context context) {
        synchronized (mLock) {
            final XmlResourceParser xmlParser = context.getResources().getXml(R.xml.url);

            int eventCode;
            try {
                eventCode = xmlParser.getEventType();
                while (eventCode != XmlPullParser.END_DOCUMENT) {
                    switch (eventCode) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("UrlConfig".equals(xmlParser.getName())) {
                                mDebug = xmlParser.getAttributeBooleanValue(null, "debug", true);
                                mDebugBaseHttpUrl = xmlParser.getAttributeValue(null, "debugBaseHttp");
                                mReleaseBaseHttpUrl = xmlParser.getAttributeValue(null, "releaseBaseHttp");
                                mDebugBaseHttpsUrl = xmlParser.getAttributeValue(null, "debugBaseHttps");
                                mReleaseBaseHttpsUrl = xmlParser.getAttributeValue(null, "releaseBaseHttps");
                                mDefaultVersion = xmlParser.getAttributeValue(null, "defaultVersion");
                                mTokenName = xmlParser.getAttributeValue(null, "tokenName");
                                mSignName = xmlParser.getAttributeValue(null, "signName");
                            } else if ("Node".equals(xmlParser.getName())) {
                                String s=xmlParser.getAttributeValue(null,"url");
                                String idAttr = xmlParser.getAttributeValue(null, "ids");
                                if (TextUtils.isEmpty(idAttr)) {
                                    throw new IllegalArgumentException();
                                }
                                int id = 0;
//                                if (idAttr.startsWith("@+id/")) {
                                    String apiName = idAttr;
                                    checkApkName(apiName);
                                    id = context.getResources().getIdentifier(
                                            idAttr, "id",
                                            context.getPackageName());
//                                } else if (idAttr.startsWith("@id/")) {
//                                    String apiName = idAttr.subSequence(4, idAttr.length()).toString();
//                                    checkApkName(apiName);
//                                    id = context.getResources().getIdentifier(
//                                            idAttr.subSequence(4, idAttr.length()).toString(), "id",
//                                            context.getPackageName());
//                                } else {
//                                    id = xmlParser.getAttributeIntValue(null, "id", 0);
//                                }
//                                if (id == 0) {
//                                    throw new IllegalArgumentException("id must NOT be zero");
//                                }
                                String url = xmlParser.getAttributeValue(null, "url");
                                if (TextUtils.isEmpty(url)) {
                                    throw new IllegalArgumentException("url must NOT be null");
                                }

                                boolean fullUrlFlag = true;
                                if (!(url.startsWith("http://") || url.startsWith("https://"))) {
                                    url = ("" + url);
                                    fullUrlFlag = false;
                                    if (url.contains("?") || url.contains("&")) {
                                        throw new IllegalArgumentException("url can NOT has parameters");
                                    }
                                }

                                String method = xmlParser.getAttributeValue(null, "method");
                                if (TextUtils.isEmpty(method)) {
                                    method = "get";
                                }

                                String apiVersion = xmlParser.getAttributeValue(null, "version");
                                if (TextUtils.isEmpty(apiVersion)) {
                                    apiVersion = sInstance.mDefaultVersion;
                                }

                                String encrypt = xmlParser.getAttributeValue(null, "encrypt");
                                if (mTokenName.equals(encrypt) || mSignName.equals(encrypt)) {
                                    encrypt = null;
                                }

                                final UrlData urlData = new UrlData(id, url.trim(), method.toLowerCase(),
                                        xmlParser.getAttributeValue(null, "class"),
                                        apiVersion.trim(),
                                        xmlParser.getAttributeBooleanValue(null, "needToken", true),
                                        xmlParser.getAttributeBooleanValue(null, "https", false), fullUrlFlag, encrypt, xmlParser.getAttributeIntValue(null, "count", 1));
                                LogUtils.d("apiManager", urlData.toString());
                                mUrlMapping.put(id, urlData);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        default:
                            break;
                    }
                    eventCode = xmlParser.next();
                }
            } catch (final XmlPullParserException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                xmlParser.close();
            }
        }
    }

    private void checkApkName(String apiName) {
        if (!apiName.startsWith("api_")) {
            throw new IllegalArgumentException("illegal api name");
        }
    }

    public UrlData findURL(final Context context, final int findKey) {
        // 如果urlList还没有数据（第一次），或者被回收了，那么（重新）加载xml
        synchronized (mLock) {
            if (mUrlMapping == null || mUrlMapping.size() == 0) {
                fetchUrlDataFromXml(context);
            }
        }
        return mUrlMapping.get(findKey);
    }

    public String getSignName() {
        return mSignName;
    }

    public String getBaseUrl(boolean isHttps, String version) {
        String tmpVersion = version;
        if (TextUtils.isEmpty(tmpVersion)) {
            tmpVersion = mDefaultVersion;
        }
        if (mDebug) {
            if (isHttps) {
                return mDebugBaseHttpsUrl + tmpVersion;
            } else {
                return mDebugBaseHttpUrl + tmpVersion;
            }
        } else {
            if (isHttps) {
                return mReleaseBaseHttpsUrl + tmpVersion;
            } else {
                return mReleaseBaseHttpUrl + tmpVersion;
            }
        }
    }

    public String getTokenName() {
        return mTokenName;
    }
}