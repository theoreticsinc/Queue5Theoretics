package com.android.print.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.print.demo.bean.CONSTANTS;
import com.android.print.demo.bean.COUNT;
import com.android.print.demo.bluetooth.BluetoothDeviceList;
import com.android.print.demo.bluetooth.BluetoothOperation;
import com.android.print.demo.databinding.ActivityStarterBinding;
import com.android.print.demo.permission.EasyPermission;
import com.android.print.demo.usb.UsbOperation;
import com.android.print.demo.util.PrintUtils;
import com.android.print.demo.util.UriGetPath;
import com.android.print.demo.wifi.WifiOperation;
import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.wifi.WifiAdmin;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StarterActivity extends AppCompatActivity implements EasyPermission.PermissionCallback{

    private Context context;
    private AppBarConfiguration appBarConfiguration;
    private ActivityStarterBinding binding;

    private static boolean isConnected;                 //是否已经建立了连接
    protected static IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;
    private ProgressDialog dialog;

    public static final int CONNECT_DEVICE = 1;             //选择设备
    public static final int ENABLE_BT = 2;                  //启动蓝牙
    public static final int REQUEST_SELECT_FILE = 3;        //选择文件
    public static final int REQUEST_PERMISSION = 4;         //读写权限

    public String type = "";
    public String gender;
    public String purpose;
    public String service;
    public String summaryStr;

    public int regularCount = 0;
    public int priorityCount = 0;
    public java.util.Timer cocTimer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = ActivityStarterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_starter);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        initDialog();
        // Auto Connect Default Printer
        myOpertion = new BluetoothOperation(context, mHandler);
        myOpertion.btAutoConn(context,  mHandler);
        isConnected = true;
        mPrinter = myOpertion.getPrinter();
        java.util.Timer timer = new Timer();
        myTask = new StarterActivity.MyTask();
        timer.schedule(myTask, 0, 2000);
        //Toast.makeText(context, R.string.yesconn, Toast.LENGTH_SHORT).show();

        HttpHandler hh = new HttpHandler();
        hh.RetrieveCurrentCount("http://"+ CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/retrieveCount.php?deskID=0");
        cocTask = new CountOnlineCheck();

        cocTimer.schedule(cocTask, 5000, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myOpertion.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_starter);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void printBigTicket(){
        if(!isConnected && mPrinter == null) {
            return;
        }

        dialog.setTitle("Theoretics Queueing System");
        dialog.setMessage("Now Printing your Ticket...");
        dialog.show();


        new Thread(){
            @Override
            public void run() {
                if (type.startsWith("PRIORITY") == true) {
                    if (priorityCount < 10) {
                        PrintUtils.printTicket(mPrinter, "P0" + priorityCount, type, gender, purpose, service);
                    } else
                        PrintUtils.printTicket(mPrinter, "P" + priorityCount, type, gender, purpose, service);
                } else {
                    if (regularCount < 10)
                        PrintUtils.printTicket(mPrinter, "R0" + regularCount, type, gender, purpose, service);
                    else
                        PrintUtils.printTicket(mPrinter, "R" + regularCount, type, gender, purpose, service);
                }
                //PrintUtils.printTicket(mPrinter, "", type, gender, purpose, service);
                //PrintUtils.printText2(context.getResources(), mPrinter);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        initDialog();
                    }
                });
            }
        }.start();


    }

    private void initDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Connecting");
        dialog.setMessage("Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    //用于接受连接状态消息的 Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    mPrinter = myOpertion.getPrinter();
                    java.util.Timer timer = new Timer();
                    myTask = new StarterActivity.MyTask();
                    timer.schedule(myTask, 0, 2000);
                    Toast.makeText(context, R.string.yesconn, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.FAILED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(context, R.string.conn_failed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.CLOSED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(context, R.string.conn_closed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    Toast.makeText(context, R.string.conn_no, Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            //updateButtonState();

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    };


    private String bt_mac;
    private String bt_name;
    private String wifi_mac;
    private String wifi_name;




    private void connClick(){
        if(isConnected){        //如果已经连接了, 则断开
            myOpertion.close();
            myOpertion = null;
            mPrinter = null;
        }else{
            //如果没有连接, 则提示
            new AlertDialog.Builder(context)
                    .setTitle(R.string.str_message)
                    .setMessage(R.string.str_connlast)
                    .setPositiveButton(R.string.yesconn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            openConn();
                        }
                    })
                    .setNegativeButton(R.string.str_resel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reselConn();
                        }
                    })
                    .show();
        }
    }


    /**
     * 打开连接
     */
    private void openConn(){
        switch (binding.tabLayout.getSelectedTabPosition()){
            case 0:{        //蓝牙
                myOpertion = new BluetoothOperation(context, mHandler);
                myOpertion.btAutoConn(context,  mHandler);
                break;
            }
            case 1:{        //WIFI
                WifiManager mWifi =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (!mWifi.isWifiEnabled()) {
                    mWifi.setWifiEnabled(true);
                    return;     //wifi没有启用
                }
                WifiInfo wifiInfo = mWifi.getConnectionInfo();
                wifi_name = wifiInfo.getSSID();
                if (wifi_name != null && !wifi_name.equals("")) {
                    DhcpInfo dhcpinfo = mWifi.getDhcpInfo();
                    WifiAdmin mWifiAdmin = new WifiAdmin(StarterActivity.this);
                    wifi_mac = mWifiAdmin.intToIp(dhcpinfo.serverAddress);

                    myOpertion = new WifiOperation(StarterActivity.this, mHandler);
                    Intent intent=new Intent();
                    intent.putExtra("ip_address", wifi_mac);
                    myOpertion.open(intent);
                }
                break;
            }
            case 2:{            //USB
                myOpertion = new UsbOperation(StarterActivity.this, mHandler);
                UsbManager manager = (UsbManager)getSystemService(Context.USB_SERVICE);
                myOpertion.usbAutoConn(manager);
                break;
            }
        }
    }


    /**
     * 重新连接
     */
    private void reselConn(){
        switch (binding.tabLayout.getSelectedTabPosition()){
            case 0:{
                myOpertion = new BluetoothOperation(context, mHandler);
                myOpertion.chooseDevice();
                break;
            }
            case 1:{
                myOpertion = new WifiOperation(context, mHandler);
                myOpertion.chooseDevice();
                break;
            }
            case 2:{
                myOpertion = new UsbOperation(context, mHandler);
                myOpertion.chooseDevice();
                break;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //选择连接设备返回处理
        if(requestCode == CONNECT_DEVICE && resultCode == RESULT_OK){
            switch (binding.tabLayout.getSelectedTabPosition()){
                case 0:{        //蓝牙
                    bt_mac = data.getExtras().getString(BluetoothDeviceList.EXTRA_DEVICE_ADDRESS);
                    bt_name = data.getExtras().getString(BluetoothDeviceList.EXTRA_DEVICE_NAME);
                    dialog.show();
                    new Thread(new Runnable() {
                        public void run() {
                            myOpertion.open(data);
                        }
                    }).start();
                    break;
                }


                case 1:{        //WIFI
                    wifi_mac = data.getStringExtra("ip_address");
                    wifi_name = data.getExtras().getString("device_name");
                    if (!wifi_mac.equals("") && wifi_mac != null) {
                        myOpertion.open(data);
                        dialog.show();
                    } else {
                        mHandler.obtainMessage(PrinterConstants.Connect.FAILED).sendToTarget();
                    }

                    break;
                }

                case 2:{        //USB
                    myOpertion.open(data);
                    break;
                }
            }


            //请求打开蓝牙返回
        }else if(requestCode == ENABLE_BT){
            if (resultCode == Activity.RESULT_OK) {
                myOpertion.chooseDevice();
            } else {
                Toast.makeText(this, R.string.bt_not_enabled, Toast.LENGTH_SHORT).show();
            }


            //选择升级文件返回
        }else if(requestCode == REQUEST_SELECT_FILE && resultCode == Activity.RESULT_OK){
            String filePath = new UriGetPath().getUriToPath(context, data.getData());
            if (filePath == null) {
                Toast.makeText(this, "获取升级文件失败", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!filePath.endsWith("bin")){
                filePath = null;
                Toast.makeText(this, "升级文件错误", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private StarterActivity.MyTask myTask;
    public StarterActivity.CountOnlineCheck cocTask;

    public void createNewTaskTimer() {
        cocTimer = new Timer();
    }

    public StarterActivity.CountOnlineCheck createNewCOCTask() {
        cocTask = new CountOnlineCheck();
        return cocTask;
    }

    /**
     * wifi机器需要定时读取打印机数据, 如下代码
     * 当连接断开时, 读取数据 read() 会触发断开连接的消息
     *
     * USB 蓝牙 可忽略
     */
    private class MyTask extends java.util.TimerTask{
        @Override
        public void run() {
            if(isConnected && mPrinter != null) {
                byte[] by = mPrinter.read();
                if (by != null) {
                    System.out.println(mPrinter.isConnected() + " read byte " + Arrays.toString(by));
                }
            }
        }
    }

    public class CountOnlineCheck extends java.util.TimerTask {
        @Override
        public void run() {
            System.out.println("ANGELO  :   Online Count Synchronizing...");
            HttpHandler hh = new HttpHandler();
            hh.RetrieveCurrentCount("http://" + CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/retrieveCount.php?deskID=0");

            if (COUNT.getInstance().getRegularCount() <= regularCount) {
                COUNT.getInstance().setRegularCount(regularCount);
            } else {
                regularCount = COUNT.getInstance().getRegularCount();
                COUNT.getInstance().setRegularCount(regularCount);
            }
            hh.UpdateDisplayConnection("http://" + CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/updateDisplay.php?"
                    + "regularCount=" + regularCount);

            if (COUNT.getInstance().getPriorityCount() <= priorityCount) {
                COUNT.getInstance().setPriorityCount(priorityCount);
            } else {
                priorityCount = COUNT.getInstance().getPriorityCount();
                COUNT.getInstance().setPriorityCount(priorityCount);
            }
            hh.UpdateDisplayConnection("http://" + CONSTANTS.getInstance().getSERVERADDR() + "/undpqueue/updateDisplay.php?"
                    + "priorityCount=" + priorityCount);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    //有权限
    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        //startSelectFile();
    }


    //没有权限
    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {

        // 是否用户拒绝,不在提示
        boolean isAskAgain = EasyPermission.checkDeniedPermissionsNeverAskAgain(
                this,
                "选择文件需要开启权限，请在应用设置开启权限。",
                R.string.gotoSettings, R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }, perms);
    }
}