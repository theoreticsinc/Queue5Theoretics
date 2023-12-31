package com.android.print.demo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.usb.UsbManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.print.demo.bluetooth.BluetoothDeviceList;
import com.android.print.demo.bluetooth.BluetoothOperation;
import com.android.print.demo.databinding.ActivityMainBinding;
import com.android.print.demo.permission.EasyPermission;
import com.android.print.demo.usb.UsbOperation;
import com.android.print.demo.util.PrintUtils;
import com.android.print.demo.util.UriGetPath;
import com.android.print.demo.wifi.WifiOperation;
import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.bluetooth.BluetoothPort;
import com.android.print.sdk.wifi.WifiAdmin;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;


/**
 * Android 打印机SDK 开发示例 v3.0
 * 技术支持 小谢
 * 打印APP定制开发 QQ 2227421573
 *
 */
public class MainActivity extends AppCompatActivity implements EasyPermission.PermissionCallback{
    private Context context;
    private ActivityMainBinding binding;

    private static boolean isConnected;                 //是否已经建立了连接
    protected static IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;
    private ProgressDialog dialog;


    public static final int CONNECT_DEVICE = 1;             //选择设备
    public static final int ENABLE_BT = 2;                  //启动蓝牙
    public static final int REQUEST_SELECT_FILE = 3;        //选择文件
    public static final int REQUEST_PERMISSION = 4;         //读写权限


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        context = this;

        initView();
    }


    /**
     * 界面初始化
     */
    private void initView(){
        binding.connectLayout.setOnClickListener(onClickListener);
        binding.printText.setOnClickListener(onClickListener);
        binding.printImage.setOnClickListener(onClickListener);
        binding.update.setOnClickListener(onClickListener);
        binding.printQr.setOnClickListener(onClickListener);
        binding.bigData.setOnClickListener(onClickListener);


        initDialog();
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



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == binding.connectLayout){        //点击连接
                connClick();

            }else if(v == binding.printText){       //打印文字
                printText();

            }else if(v == binding.printImage){      //打印图片
                printImage();

            }else if(v == binding.printQr){         //打印条码
                printBarcode();

            }else if(v == binding.bigData){         //大数据打印测试
                printBigData();

            }else if(v == binding.update){         //更新打印机程序
                startSelectFile();
            }
        }
    };



    //SD卡读写权限
    String[] permisions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private boolean hasSDcardPermissions() {
        //判断是否有权限
        if (EasyPermission.hasPermissions(context, permisions)) {
            return true;
        } else {
            EasyPermission.with(this)
                    .rationale("选择文件需要SDCard读写权限")
                    .addRequestCode(REQUEST_PERMISSION)
                    .permissions(permisions)
                    .request();
        }
        return false;
    }


    private void startSelectFile(){
        if(!isConnected && mPrinter == null) {
            return;
        }

        if(hasSDcardPermissions()){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_SELECT_FILE);
        }
    }


    private void tipUpdate(final String filePath){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(filePath + "\n请确认打印机版本是否支持")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printUpdate(filePath);
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 打印文字
     * WIIF打印机 API涉及网络通讯, 建议异步调用
     *
     */
    private void printText(){
        if(!isConnected && mPrinter == null) {
            return;
        }
        new Thread(){
            @Override
            public void run() {
                PrintUtils.printText(context.getResources(), mPrinter);
            }
        }.start();
    }

    /**
     * 打印图片
     * WIIF打印机 API涉及网络通讯, 建议异步调用
     *
     */
    private void printImage(){
        if(!isConnected && mPrinter == null) {
            return;
        }
        new Thread(){
            @Override
            public void run() {
                PrintUtils.printImage(context.getResources(), mPrinter);
            }
        }.start();
    }


    private void printBarcode(){
        if(!isConnected && mPrinter == null) {
            return;
        }
        new Thread(){
            @Override
            public void run() {
                PrintUtils.printBarcode(context.getResources(), mPrinter);
            }
        }.start();
    }


    private void printBigData(){
        if(!isConnected && mPrinter == null) {
            return;
        }

        dialog.setTitle(null);
        dialog.setMessage("正在打印, 请稍后...");
        dialog.show();


        new Thread(){
            @Override
            public void run() {
                PrintUtils.printBigData(context.getResources(), mPrinter);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        initDialog();
                    }
                });
            }
        }.start();
    }


    /**
     * 打印机升级是耗时操作, 请开发者在异步线程中操作
     */
    private void printUpdate(final String filePath){
        if(!isConnected && mPrinter == null) {
            return;
        }
        dialog.setTitle(null);
        dialog.setMessage("正在升级, 请稍后...");
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                PrintUtils.printUpdate(context.getResources(), mPrinter, filePath);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "数据已发送, 请等待打开机升级完成", Toast.LENGTH_LONG).show();
                        initDialog();
                    }
                });
            }
        }.start();

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
                    myTask = new MyTask();
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

            updateButtonState();

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    };


    private String bt_mac;
    private String bt_name;
    private String wifi_mac;
    private String wifi_name;


    /**
     * 更新界面状态
     */
    private void updateButtonState() {
        if(!isConnected){
            binding.connectAddress.setText(R.string.no_conn_address);
            binding.connectState.setText(R.string.connect);
            binding.connectName.setText(R.string.no_conn_name);
        }else{
            switch (binding.tabLayout.getSelectedTabPosition()){
                case 0:{
                    if( bt_mac!=null && !bt_mac.equals("")){
                        binding.connectAddress.setText(getString(R.string.str_address)+ bt_mac);
                        binding.connectState.setText(R.string.disconnect);
                        binding.connectName.setText(getString(R.string.str_name)+bt_name);
                    }else if(bt_mac==null ) {
                        bt_mac= BluetoothPort.getmDeviceAddress();
                        bt_name=BluetoothPort.getmDeviceName();
                        binding.connectAddress.setText(getString(R.string.str_address)+bt_mac);
                        binding.connectState.setText(R.string.disconnect);
                        binding.connectName.setText(getString(R.string.str_name)+bt_name);
                    }
                    break;
                }
                case 1:{
                    binding.connectAddress.setText(getString(R.string.str_address)+wifi_mac);
                    binding.connectState.setText(R.string.disconnect);
                    binding.connectName.setText(getString(R.string.str_name)+wifi_name);
                    break;
                }
                case 2:{
                    binding.connectAddress.setText(getString(R.string.disconnect));
                    binding.connectState.setText(R.string.disconnect);
                    binding.connectName.setText(getString(R.string.disconnect));
                    break;
                }
            }
        }
    }




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
                    WifiAdmin mWifiAdmin = new WifiAdmin(MainActivity.this);
                    wifi_mac = mWifiAdmin.intToIp(dhcpinfo.serverAddress);

                    myOpertion = new WifiOperation(MainActivity.this, mHandler);
                    Intent intent=new Intent();
                    intent.putExtra("ip_address", wifi_mac);
                    myOpertion.open(intent);
                }
                break;
            }
            case 2:{            //USB
                myOpertion = new UsbOperation(MainActivity.this, mHandler);
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

            tipUpdate(filePath);
        }

    }

    private MyTask myTask;

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




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    //有权限
    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        startSelectFile();
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
