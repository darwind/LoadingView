package com.example.loadingview;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> pages = new ArrayList<>();
        pages.add("https://google.com");
        pages.add("https://stackoverflow.com");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(pages));
    }

    static class CustomPagerAdapter extends PagerAdapter {
        private final List<String> pages;

        CustomPagerAdapter(List<String> pages) {
            this.pages = pages;
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final View viewItem = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_item, container, false);
            final WebView webView = (WebView) viewItem.findViewById(R.id.webView);
            webView.setVisibility(View.INVISIBLE);
            final ProgressBar progressBar = (ProgressBar) viewItem.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            });
            // You can load a URL like this
            // webView.loadUrl(pages.get(position));

            // Or load data as you do it already:
            webView.loadDataWithBaseURL("file:///android_asset/", createHtmlData(), "text/html; charset=utf-8", "utf-8", null);
            container.addView(viewItem);
            return viewItem;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private String createHtmlData() {
            String head = "<head><title>Hello there!</title></head>";
            String htmlData = "<html>" + head + "<body><p>Hello there again again</p></body></html>";
            return htmlData;
        }
    }
}