package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Search extends Activity {
    
    Program p1 = new Program();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            TextView text = (TextView) findViewById(R.id.textView);
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage");
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                InputStream w = entity.getContent();

                try {
                    //URLConnection con = url.openConnection();
                    // InputStream is =con.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(w, "1900"), 8);

                    text.setText("Carl");
                    w.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    /*

        Button button = (Button) findViewById(R.id.button);
        final TextView text = (TextView) findViewById(R.id.textView);
        final String line = p1.grabHTMLInfo();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(line);
            }
        });
    }
}*/
class ProgramTest {
    private String lines;

    public String getLine() {
        return lines;
    }

    public void setLine(String line)
    {
        this.lines = line;
    }
}

    class Program {
        String line;

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }
        public String grabHTMLInfo() {
            //Program pi = new Program();
            String l = null;
            try {
                URL url = new URL("http://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage");

                URLConnection con = url.openConnection();
                InputStream is =con.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((l = reader.readLine()) != null) {
                    l = "Testing";
                }
               // reader.close();

            } catch (MalformedURLException e) {
                System.out.println("error");
            } catch (IOException e) {
                System.out.println("error2");
            }
            return l;
        }
    }
         /*

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.search, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
*/