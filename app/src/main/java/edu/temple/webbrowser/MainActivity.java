package edu.temple.webbrowser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.*;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BrowserFragment.BrowserInt{

    ViewPager vp;
    TabAdapter tabs;
    FragmentManager fm = getSupportFragmentManager();

    ArrayList<BrowserFragment> fragments = new ArrayList<>();
    public ArrayList<String> urls = new ArrayList<>();

    int current;

    final String firstURL = "https://google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.pager);
        tabs = new TabAdapter(fm, urls);
        vp.setAdapter(tabs);

        BrowserFragment bf;
        if(getIntent().getData() != null){
            bf = BrowserFragment.newInstance(getIntent().getData().toString());
            fragments.add(bf);
            urls.add(getIntent().getData().toString());
            tabs.notifyDataSetChanged();
        }
        else{
            bf = BrowserFragment.newInstance(firstURL);
            fragments.add(bf);
            urls.add(firstURL);
            tabs.notifyDataSetChanged();
        }





        fm
            .beginTransaction()
            .replace(R.id.container, bf)
            .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.nextTab:

                if(vp.getCurrentItem() + 1 < urls.size()){
                    vp.setCurrentItem(vp.getCurrentItem() + 1);
                }

                return true;

            case R.id.backTab:


                if(vp.getCurrentItem() - 1 >= 0){
                    vp.setCurrentItem(vp.getCurrentItem() - 1);
                }

                return true;

            case R.id.newTab:

                BrowserFragment bf = BrowserFragment.newInstance(firstURL);
                fm
                        .beginTransaction()
                        .replace(R.id.container, bf)
                        .commit();

                fragments.add(bf);
                urls.add(firstURL);
                tabs.notifyDataSetChanged();
                vp.setCurrentItem(vp.getCurrentItem() + 1);


                for(int i = 0; i < fragments.size(); i++){
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<String> getUrls() {
        return urls;
    }

    @Override
    public int getCurrent() {
        return vp.getCurrentItem();
    }
}
