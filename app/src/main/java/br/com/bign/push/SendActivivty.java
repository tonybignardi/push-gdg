package br.com.bign.push;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by tonybignardi on 13/02/2016.
 */
public class SendActivivty extends AppCompatActivity {

    public static String API_KEY="AIzaSyCKeFt1oZpaaCSDD_8FeZwshp754NqxGNk";
    HttpURLConnection conn;
    OutputStream outputStream;
    JSONObject jGcmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


        Button btsend= (Button) findViewById(R.id.sendmessage);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ettitle = (EditText) findViewById(R.id.title);
                EditText etmessage = (EditText) findViewById(R.id.message);
                try {
                    // Prepare JSON containing the GCM message content. What to send and where to send.
                    jGcmData = new JSONObject();
                    JSONObject jData = new JSONObject();
                    jData.put("message", etmessage.getEditableText().toString());
                    jData.put("title",ettitle.getEditableText().toString());
                    // Where to send GCM message.

                    //TOPIC OR EXPECIFIC TOKEN

                        jGcmData.put("to", "/topics/global");

                    // What to send in GCM message.
                    jGcmData.put("data", jData);


                                    URL url = null;

                                        url = new URL("https://android.googleapis.com/gcm/send");

                                        conn = (HttpURLConnection) url.openConnection();

                                        conn.setRequestProperty("Authorization", "key=" + API_KEY);
                                        conn.setRequestProperty("Content-Type", "application/json");

                                        conn.setRequestMethod("POST");

                                        conn.setDoOutput(true);

                                        // Send GCM message content.


                                         outputStream = null;


                                    Thread thread = new Thread(new Runnable(){
                                        @Override
                                        public void run() {
                                            try {
                                                //Your code goes here
                                                outputStream = conn.getOutputStream();
                                                outputStream.write(jGcmData.toString().getBytes());

                                                InputStream inputStream = null;
                                                inputStream = conn.getInputStream();

                                                String resp = null;
                                                resp = IOUtils.toString(inputStream);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();




                                        // Read GCM response.


                                        // System.out.println(resp);
                                        //System.out.println("Check your device/emulator for notification or logcat for " + "confirmation of the receipt of the GCM message.");
                                        finish();





                    // Create connection to send GCM Message request.



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
