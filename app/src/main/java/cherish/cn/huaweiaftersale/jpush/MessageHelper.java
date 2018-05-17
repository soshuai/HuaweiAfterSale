package cherish.cn.huaweiaftersale.jpush;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;

import cherish.cn.huaweiaftersale.base.AppContext;
import cherish.cn.huaweiaftersale.util.SecurityHelper;

public abstract class MessageHelper {

    private static final String TAG = MessageHelper.class.getSimpleName();

//    private static DatabaseHelper mDbHelper;
//    private static long lastNotifyTimestamp;
//    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//
//    public static void receiveMessage(final String json) {
//        // TODO Auto-generated method stub
//        // 写入数据库
//        // 启动线程处理
//        JSONObject jsonObject = JSON.parseObject(json);
//        int type = jsonObject.getIntValue("type");
//        int msgId = jsonObject.getIntValue("msgId");
//        int customerId = jsonObject.getIntValue("customerId");
//        String customerName = jsonObject.getString("customerName");
//        String customerAvatar = jsonObject.getString("customerAvatar");
//        String msgType = jsonObject.getString("msgType");
//        String content = jsonObject.getString("content");
//        String url = jsonObject.getString("url");
//        String sendTime = jsonObject.getString("sendTime");
//        Log.e(TAG, "receiveMessage: " + sendTime);
//        ChatBean bean = new ChatBean();
//        bean.setType(type);
//
//
//        if(type==4||type==5){
//           bean.setContent(content);
//        }else{
//            if ("text".equals(msgType)) {
//                bean.setContent(content);
//            } else {
//                bean.setContent(url);
//                if("voice".equalsIgnoreCase(msgType)){
//                    bean.setLength(1000);
//                }
//            }
//        }
//        bean.setTargetId(customerId + "");
//        bean.setMsgOwner(SecurityHelper.findUserData().getUser().getId() + "");
//        long t = System.currentTimeMillis();
//        if(TextUtils.isEmpty(sendTime)){
//            Log.e(TAG, "receiveMessage:System.time=== " + t);
//            bean.setTime(t);
//        }else {
//            bean.setTime(Long.parseLong(sendTime));
//        }
//        bean.setIsRead(0);
////        bean.setIsSelf(0);
//        bean.setMessageType(ChatBean.MSG_TYPE_CHAT);
//        bean.setMsgContentType(getContentType(msgType,type));
//        bean.setCustomerName(customerName);
//        bean.setCustomerAvatar(customerAvatar);
//        getDbHelper().getChatDao().save(bean);
//        //判断本地是否存在该会话
//        SessionBean sessionbenaLocal = getDbHelper().getSessionDao().getSessionBean(customerId + "");
//        if (sessionbenaLocal!=null) {
//            Log.e(TAG, "receiveMessage:更新 ");
//            //更新
//            if ("text".equals(msgType)||type==4||type==5) {
//                sessionbenaLocal.setLastMessage(content);
//            } else {
//                sessionbenaLocal.setLastMessage(url);
//            }
//            sessionbenaLocal.setMsgtype(msgType);
//            sessionbenaLocal.setIsRead(0);
//            sessionbenaLocal.setTime(System.currentTimeMillis());
//            getDbHelper().getSessionDao().save(sessionbenaLocal);
//        } else {
//            //创建
//            Log.e(TAG, "receiveMessage：创建");
//            SessionBean sessionBean = new SessionBean();
//            sessionBean.setTime(System.currentTimeMillis());
//            if ("text".equals(msgType)||type==4||type==5) {
//                sessionBean.setLastMessage(content);
//            } else {
//                sessionBean.setLastMessage(url);
//            }
//            sessionBean.setMsgtype(msgType);
//            sessionBean.setTakerAvatar(customerAvatar);
//            sessionBean.setTalkerNike(customerName);
//            sessionBean.setTalkId(customerId + "");
//            sessionBean.setTalker(customerName);
//            sessionBean.setUserName(SecurityHelper.getInstance().findUserData().getUser().getId() + "");
//            sessionBean.setIsRead(0);
//            getDbHelper().getSessionDao().save(sessionBean);
//
//        }
//
//        EventBus.getDefault().post(new ChatUpdateEvent(bean));
//        // 更新界面
//        EventBus.getDefault().post(new MessageComeEvent());
//    }
//
//    public static int getContentType(String msgType) {
//        if ("text".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_TEXT;
//        } else if ("image".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_IMG;
//        } else if ("voice".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_VOICE;
//        } else if ("manuals".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_MANUALS;
//        }
//        return ChatBean.MSG_CONTENT_TYPE_TEXT;
//    }
//public static int getContentType(String msgType, int type) {
//    if(type==4||type==5){
//         return  ChatBean.MSG_CONTENT_TYPE_BEHAVIOR;
//    }else{
//        if ("text".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_TEXT;
//        } else if ("image".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_IMG;
//        } else if ("voice".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_VOICE;
//        } else if ("manuals".equalsIgnoreCase(msgType)) {
//            return ChatBean.MSG_CONTENT_TYPE_MANUALS;
//        }
//    }
//
//
//    return ChatBean.MSG_CONTENT_TYPE_TEXT;
//}
//
//
//    public synchronized static DatabaseHelper getDbHelper() {
//        if (null == mDbHelper || !mDbHelper.isOpen())
//            mDbHelper = OpenHelperManager.getHelper(AppContext.getInstance(), DatabaseHelper.class);
//        return mDbHelper;
//    }

}
