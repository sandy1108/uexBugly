package org.zywx.wbpalmstar.plugin.uexbugly;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

import org.zywx.wbpalmstar.base.BDebug;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by zyp on 2021/03/17.
 */
public class EUExBugly extends EUExBase {

    private static final String TAG = "EUExBugly";

    public EUExBugly(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }

    public static void onApplicationCreate(final Context context) {
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppReportDelay(100);
        CrashReport.setUserId("uexBugly");
        // 初始化Bugly
        CrashReport.initCrashReport(context, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 初始化
     *
     * @param param
     */
    public void init(String[] param) {
        if(param.length < 1) {
            return;
        }
        BDebug.i(TAG, "init");
        new Thread(() -> {
            String test = null;
            test.concat("123");
        }).start();
    }

    /**
     * 用callbackId进行匿名JS方法回调
     *
     * @param callbackIdStr
     * @param jsonObj
     */
    private void callbackWithJsFuncIdStr(String callbackIdStr, Object jsonObj){
        callbackWithJsFuncIdStr(callbackIdStr, false, jsonObj);
    }

    /**
     * 用callbackId进行匿名JS方法回调
     *
     * @param callbackIdStr
     * @param jsonObj
     */
    private void callbackWithJsFuncIdStr(String callbackIdStr, boolean hasNext, Object jsonObj){
        if (!TextUtils.isEmpty(callbackIdStr)){
            try {
                int callbackId = Integer.parseInt(callbackIdStr);
                callbackToJs(callbackId, hasNext, jsonObj);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * JS方法名回调
     *
     * @param methodName
     * @param jsonData
     */
    private void callBackWithJsName(String methodName, String jsonData) {
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        onCallback(js);
    }

}
