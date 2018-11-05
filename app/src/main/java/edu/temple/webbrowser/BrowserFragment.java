package edu.temple.webbrowser;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowserFragment extends Fragment {

    private static final String URL_KEY = "URL";
    private static final String CLIENT_KEY = "WEB";

    String currenturl;
    WebViewClient client = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            text.setText(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            currenturl = url;
            text.setText(url);
            int current = ((BrowserInt) parent).getCurrent();
            ((BrowserInt) parent).getUrls().set(current, url);
        }
    };

    Button go;
    EditText text;


    Context parent;
    WebView web;
    Button next;
    Button back;

    public BrowserFragment() {
        // Required empty public constructor
    }

    public static BrowserFragment newInstance(String url) {
        BrowserFragment bf = new BrowserFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        bf.setArguments(args);
        return bf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currenturl = getArguments().getString(URL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_browser, container, false);

        web = (WebView) v.findViewById(R.id.web);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(client);
        web.loadUrl(currenturl);

        text = (EditText) v.findViewById(R.id.editText);

        go = (Button) v.findViewById(R.id.button);

        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url = text.getText().toString();
                web.loadUrl(url);

            }
        });

        back = (Button) v.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(web.canGoBack()){
                    web.goBack();
                }
            }
        });

        next = (Button) v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(web.canGoForward()){
                    web.goForward();
                }
            }
        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = context;
    }

    interface BrowserInt{
        ArrayList<String> getUrls();
        int getCurrent();
    }
}
