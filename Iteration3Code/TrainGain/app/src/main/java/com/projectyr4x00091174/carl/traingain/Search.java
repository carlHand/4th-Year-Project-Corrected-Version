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
import java.io.Writer;
/*
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
*/
public class Search extends Activity {
    TextView textMsg;
    String line = null;
    // Program p1 = new Program();
    String lines = "";

    final String textSource = "http://sites.google.com/site/androidersite/text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // text = (TextView) findViewById(R.id.textView);
        textMsg = (TextView) findViewById(R.id.textView);
    }
}
    /*
        Button button = (Button) findViewById(R.id.button);
        //final TextView text = (TextView) findViewById(R.id.textView);
        lines = p1.grabInfo();
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                text.setText(lines);
            }
        });


        URL textUrl;
        try {
            textUrl = new URL(textSource);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;
            String stringText = "";
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
            textMsg.setText(stringText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            textMsg.setText(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            textMsg.setText(e.toString());
        }
    }
}

        /*
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
/*
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

        public String grabInfo() {
            String l = "";
            try {
                // Make a URL to the web page
                URL url = new URL("http://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage");

                // Get the input stream through URL Connection
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();

                // Once you have the Input Stream, it's just plain old Java IO stuff.

                // For this case, since you are interested in getting plain-text web page
                // I'll use a reader and output the text content to System.out.

                // For binary content, it's better to directly read the bytes from stream and write
                // to the target file.


                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                //String line = null;
                // final String line2 = "Lee";
                // read each line and write to System.out
                while ((l = br.readLine()) != null) {
                    //System.out.println(line);
                    l = "CARL";

                    //text.setText("CARL it works!!!");
                }


            } catch (Exception e) {
                e.printStackTrace();
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