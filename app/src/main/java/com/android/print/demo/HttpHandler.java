package com.android.print.demo;

import android.os.AsyncTask;
import android.util.Log;

import com.android.print.demo.bean.CONSTANTS;
import com.android.print.demo.bean.COUNT;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class HttpHandler {

        private final String TAG = HttpHandler.class.getSimpleName();
        private StarterActivity activity;
        public String retStr;
        //private DBHelper dbh;
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        public HttpHandler() {
            //dbh = db;
        }

        public String updateData2Server(final String urlWebService, final String sql, final String ldm) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class SendSQL extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some timecm n'\
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : UPDATING TO SERVER " + urlWebService + sql);
                        //creating a URL
                        String u = urlWebService + sql;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        if (null == con)
                            return null;
                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json = "";
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //insertNewVIP2DB(dbh, json);
                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string
//                    if (json.compareToIgnoreCase("ok") == 0) {
                        //force
//                        dbh.updateLDM(EXIT_TABLE_NAME, ldm);
//                    }
                        //dbh.updateLDM(EXIT_TABLE_NAME, ldm);

                        //retJSON[0] = readJSON(json);
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(sql);

                        return "true";
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            SendSQL sendSQL = new SendSQL();
            sendSQL.execute();

            return retStr;
        }


        public String saveRawText2Server(final String urlWebService, final String rawText) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class SendSQL extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some timecm n'\
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : UPDATING TO SERVER " + urlWebService + rawText);
                        //creating a URL
                        String u = urlWebService + rawText;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json = "";
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //insertNewVIP2DB(dbh, json);
                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string

                        //retJSON[0] = readJSON(json);
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(sql);

                        return "true";
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            SendSQL sendSQL = new SendSQL();
            sendSQL.execute();

            return retStr;
        }

        public String sendGRAND2Server(final String urlWebService, final String rawText) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class SendSQL extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some timecm n'\
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : UPDATING TO SERVER " + urlWebService + rawText);
                        //creating a URL
                        String u = urlWebService + rawText;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json = "";
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //insertNewVIP2DB(dbh, json);
                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string

                        //retJSON[0] = readJSON(json);
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(sql);

                        return "true";
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            SendSQL sendSQL = new SendSQL();
            sendSQL.execute();

            return retStr;
        }

        public String makeAmbulatory2Server(final String urlWebService, final String sql) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class SendSQL extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some timecm n'\
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : UPDATING TO SERVER " + urlWebService + sql);
                        //creating a URL
                        String u = urlWebService + sql;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json = "";
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //insertNewVIP2DB(dbh, json);
                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string

                        //retJSON[0] = readJSON(json);
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(sql);

                        return "true";
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            SendSQL sendSQL = new SendSQL();
            sendSQL.execute();

            return retStr;
        }

        public void getNewVIPFromServer(final String urlWebService, final String ldc) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class GetJSON extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some timecm n'\
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : RUNNING NEW VIPS " + urlWebService + ldc);
                        //creating a URL
                        String u = urlWebService + ldc;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json = "";
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //insertNewVIP2DB(dbh, json);
                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string

                        //retJSON[0] = readJSON(json);
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(ldc);

                        return "true";
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            GetJSON getJSON = new GetJSON();
            getJSON.execute();

        }

        //this method is actually fetching the json string
        public void getModifiedVIPFromServer(final String urlWebService, final String ldm) {
            //final String[] retStr = new String[1];
            /*
             * As fetching the json string is a network operation
             * And we cannot perform a network operation in main thread
             * so we need an AsyncTask
             * The constrains defined here are
             * Void -> We are not passing anything
             * Void -> Nothing at progress update as well
             * String -> After completion it should return a string and it will be the json string
             * */
            class GetJSON extends AsyncTask<Void, Void, String> {

                //this method will be called before execution
                //you can display a progress bar or something
                //so that user can understand that he should wait
                //as network operation may take some time
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                //this method will be called after execution
                //so here we are displaying a toast with the json string
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    retStr = s;

                }

                //in this method we are fetching the json string
                @Override
                protected String doInBackground(Void... voids) {

                    try {
                        System.out.println("ANGELO : " + urlWebService + ldm);
                        //creating a URL
                        String u = urlWebService + ldm;
                        URL url = new URL(u);

                        //Opening the URL using HttpURLConnection
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        //StringBuilder object to read the string from the service
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json;
                        int i = 0;
                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {
                            i++;
                            //appending it to string builder
                            sb.append(json + "\n");
                            System.out.println("ANGELO : [" + i + "]" + json + "\n");
                            //updateModifiedVIP2DB(dbh, json);
                            //dbh.updateLDM(ldm);

                        }
                        System.out.println("ANGELO : [" + i + "]" + sb.toString());
                        //finally returning the read string
                        //Date now = new Date();
                        //String n = sdf.format(now);
                        //System.out.println(n);
                        //dbh.updateLDC(ldm);
                        //retJSON[0] = readJSON(json);

                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }

                }
            }

            //creating asynctask object and executing it
            GetJSON getJSON = new GetJSON();
            getJSON.execute();

        }


        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }
/*
        private void insertNewVIP2DB(DBHelper dbh, String msg) {
            //String url = "http://api.androidhive.info/contacts/";
            //url = "http://192.168.1.116/timecheck.php";
            //HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            //String jsonStr = sh.makeServiceCall(url);

            try {
                JSONObject c = new JSONObject(msg);

                // tmp hash map for single contact
                HashMap<String, String> contact = new HashMap<>();

                // adding each child node to HashMap key => value
                //contact.put(VIP_COLUMN_CARDID, c.getString(VIP_COLUMN_CARDID));


                // adding contact to contact
                //sendmessage(c.getString("id") + " : " + c.getString("name") + " : " + c.getString("email") + " : " + c.getString("mobile"));
                //System.out.println("ANGELO: "+c.getString(VIP_COLUMN_CARDID) + " : " + c.getString(VIP_COLUMN_CARDCODE) + " : " + c.getString(VIP_COLUMN_PARKERTYPE) + " : " + c.getString(VIP_COLUMN_PLATENUMBER));

                //String cardID, String parkerType, String plateNumber, String name, String cardNumber, int maxUse, int status, String ldc, String ldm)
                //boolean inserted = dbh.insertContact(c.getString(VIP_COLUMN_CARDID), c.getString(VIP_COLUMN_PARKERTYPE), c.getString(VIP_COLUMN_PLATENUMBER),
                //        c.getString(VIP_COLUMN_NAME), c.getString(VIP_COLUMN_CARDCODE), c.getString(VIP_COLUMN_MAXUSE),
                //        c.getString(VIP_COLUMN_STATUS), c.getString(VIP_COLUMN_DATECREATED),c.getString(VIP_COLUMN_DATEMODIFIED));
                //if (inserted) dbh.updateLDC("vips", c.getString(VIP_COLUMN_DATECREATED));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
*/
/*
        private void updateModifiedVIP2DB(DBHelper dbh, String msg) {
            //String url = "http://api.androidhive.info/contacts/";
            //url = "http://192.168.1.116/timecheck.php";
            //HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            //String jsonStr = sh.makeServiceCall(url);

            try {
                JSONObject c = new JSONObject(msg);

                // tmp hash map for single contact
                HashMap<String, String> contact = new HashMap<>();

                // adding each child node to HashMap key => value
                contact.put(VIP_COLUMN_CARDID, c.getString(VIP_COLUMN_CARDID));
                contact.put(VIP_COLUMN_CARDCODE, c.getString(VIP_COLUMN_CARDCODE));
                contact.put(VIP_COLUMN_PARKERTYPE, c.getString(VIP_COLUMN_PARKERTYPE));
                contact.put(VIP_COLUMN_PLATENUMBER, c.getString(VIP_COLUMN_PLATENUMBER));
                contact.put(VIP_COLUMN_NAME, c.getString(VIP_COLUMN_NAME));
                contact.put(VIP_COLUMN_MAXUSE, c.getString(VIP_COLUMN_MAXUSE));
                contact.put(VIP_COLUMN_STATUS, c.getString(VIP_COLUMN_STATUS));
                contact.put(VIP_COLUMN_DATECREATED, c.getString(VIP_COLUMN_DATECREATED));
                contact.put(VIP_COLUMN_DATEMODIFIED, c.getString(VIP_COLUMN_DATEMODIFIED));

                // adding contact to contact
                //sendmessage(c.getString("id") + " : " + c.getString("name") + " : " + c.getString("email") + " : " + c.getString("mobile"));
                System.out.println("ANGELO: "+c.getString(VIP_COLUMN_CARDID) + " : " + c.getString(VIP_COLUMN_CARDCODE) + " : " + c.getString(VIP_COLUMN_PARKERTYPE) + " : " + c.getString(VIP_COLUMN_PLATENUMBER));

                //String cardID, String parkerType, String plateNumber, String name, String cardNumber, int maxUse, int status, String ldc, String ldm)
                boolean inserted = dbh.updateContact(c.getString(VIP_COLUMN_CARDID), c.getString(VIP_COLUMN_PARKERTYPE), c.getString(VIP_COLUMN_PLATENUMBER),
                        c.getString(VIP_COLUMN_NAME), c.getString(VIP_COLUMN_CARDCODE), c.getString(VIP_COLUMN_MAXUSE),
                        c.getString(VIP_COLUMN_STATUS), c.getString(VIP_COLUMN_DATECREATED),c.getString(VIP_COLUMN_DATEMODIFIED));
                if (inserted) dbh.updateLDM("vips", c.getString(VIP_COLUMN_DATEMODIFIED));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
*/

    public void TestConnection2() {
        System.out.println("ANGELO:  Connection Testing... ");
        String url = "http://api.androidhive.info/contacts/";
        url = "http://"+ CONSTANTS.getInstance().getSERVERADDR() + "/timecheck.php";
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);
        System.out.println("ANGELO: " + jsonStr);
        try {
            JSONObject c = new JSONObject(jsonStr);

            // tmp hash map for single contact
            HashMap<String, String> contact = new HashMap<>();
            System.out.println("ANGELO: "+c.getString("hr") + " : " + c.getString("min") + " : "
                    + c.getString("sec") + " : " + c.getString("year"));

        } catch (Exception ex) {

        }
    }

    public void TestConnection(final String urlWebService) {
        //final String[] retStr = new String[1];
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        System.out.println("ANGELO:  Connection Testing... ");
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                retStr = s;

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    System.out.println("ANGELO : " + urlWebService);
                    //creating a URL
                    String u = urlWebService;
                    URL url = new URL(u);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;
                    int i = 0;
                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {
                        i++;
                        //appending it to string builder
                        sb.append(json + "\n");
                        System.out.println("ANGELO : [" + i + "]" + json + "\n");
                        //updateModifiedVIP2DB(dbh, json);
                        //dbh.updateLDM(ldm);

                    }
                    //readJSON(json);
                    System.out.println("ANGELO : [" + i + "]" + sb.toString());
                    //finally returning the read string
                    //Date now = new Date();
                    //String n = sdf.format(now);
                    //System.out.println(n);
                    //dbh.updateLDC(ldm);
                    //retJSON[0] = readJSON(json);

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    public void RetrieveCurrentCount(final String urlWebService) {
        //final String[] retStr = new String[1];
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        //System.out.println("ANGELO:  RetrieveCurrentCount... ");
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                retStr = s;

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    System.out.println("ANGELO : " + urlWebService);
                    //creating a URL
                    String u = urlWebService;
                    URL url = new URL(u);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;
                    int i = 0;
                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {
                        i++;
                        //appending it to string builder
                        sb.append(json + "\n");
                        //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                        //updateModifiedVIP2DB(dbh, json);
                        //dbh.updateLDM(ldm);

                    }
                    readCount(sb.toString());
                    //System.out.println("ANGELO : [" + i + "]" + sb.toString());
                    //finally returning the read string
                    //Date now = new Date();
                    //String n = sdf.format(now);
                    //System.out.println(n);
                    //dbh.updateLDC(ldm);
                    //retJSON[0] = readJSON(json);

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    public void UpdateDisplayConnection(final String urlWebService) {
        //final String[] retStr = new String[1];
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        //System.out.println("ANGELO:  UpdateDisplayConnection... ");
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                retStr = s;

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    System.out.println("ANGELO : " + urlWebService);
                    //creating a URL
                    String u = urlWebService;
                    URL url = new URL(u);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;
                    int i = 0;
                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {
                        i++;
                        //appending it to string builder
                        sb.append(json + "\n");
                        //System.out.println("ANGELO : [" + i + "]" + json + "\n");
                        //updateModifiedVIP2DB(dbh, json);
                        //dbh.updateLDM(ldm);

                    }
                    //readRFID(sb.toString());
                    //System.out.println("ANGELO : [" + i + "]" + sb.toString());
                    //finally returning the read string
                    //Date now = new Date();
                    //String n = sdf.format(now);
                    //System.out.println(n);
                    //dbh.updateLDC(ldm);
                    //retJSON[0] = readJSON(json);

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    private void readJSON(String msg) {
        //String url = "http://api.androidhive.info/contacts/";
        //url = "http://192.168.1.116/timecheck.php";
        //HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        //String jsonStr = sh.makeServiceCall(url);

        try {
            JSONObject c = new JSONObject(msg);
            String sec = c.getString("sec");
            String min = c.getString("min");
            String hr = c.getString("hr");
            String date = c.getString("date");
            String mon = c.getString("mon");

            // tmp hash map for single contact
            HashMap<String, String> contact = new HashMap<>();

            // adding each child node to HashMap key => value
            contact.put("id", sec);
            contact.put("name", min);
            contact.put("email", hr);
            contact.put("mobile", date);

            // adding contact to contact
            System.out.println("ANGELO : " + contact.get("id") + " : " + contact.get("name") + " : " + contact.get("email") + " : " + contact.get("mobile"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");


                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }
        */
    }

    private void readRFID(String msg) {
        //String url = "http://api.androidhive.info/contacts/";
        //url = "http://192.168.1.116/timecheck.php";
        //HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        //String jsonStr = sh.makeServiceCall(url);

        try {
            JSONObject c = new JSONObject(msg);
            String gender = c.getString("gender");
            String type = c.getString("type");
            String service = c.getString("service");
            String purpose = c.getString("purpose");

            // tmp hash map for single contact
            HashMap<String, String> contact = new HashMap<>();

            // adding each child node to HashMap key => value
            contact.put("gender", gender);
            contact.put("type", type);
            contact.put("service", service);
            contact.put("purpose", purpose);

            // adding contact to contact
            System.out.println("ANGELO : gender: "+ contact.get("gender") + " type: " + contact.get("type") + " service: " + contact.get("service") + " purpose: " + contact.get("purpose"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");


                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }
        */
    }

    private void readCount(String msg) {
        //String url = "http://api.androidhive.info/contacts/";
        //url = "http://192.168.1.116/timecheck.php";
        //HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        //String jsonStr = sh.makeServiceCall(url);

        try {
            JSONObject c = new JSONObject(msg);
            String regular = c.getString("regular");
            String priority = c.getString("priority");

            // tmp hash map for single contact
            HashMap<String, String> contact = new HashMap<>();

            // adding each child node to HashMap key => value
            contact.put("regular", regular);
            contact.put("priority", priority);
            COUNT.getInstance().setRegularCount(Integer.parseInt(regular));
            COUNT.getInstance().setPriorityCount(Integer.parseInt(priority));
            // adding contact to contact
            System.out.println("ANGELO : regular: "+ contact.get("regular") + " priority: " + contact.get("priority"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");


                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }
        */
    }

    private void parseResult(JSONObject json) {

        String pageId;
        String pageNumber = null;
        String snippet;
        try {
            pageId = json.getString("page_id");
            pageNumber = json.optString("page_number");
            snippet = json.optString("snippet_text");
        } catch (JSONException e) {
            Log.w(TAG, e);
            // Never seen in the wild, just being complete.

        }

        if (pageNumber == null || pageNumber.isEmpty()) {
            // This can happen for text on the jacket, and possibly other
            // reasons.
            pageNumber = "";
        } else {
            pageNumber =  ' '
                    + pageNumber;
        }

    }


    private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }
