package it.cosenonjaviste.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.parceler.ParcelClass;

import java.io.File;
import java.io.IOException;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.PostDetailBinding;
import it.cosenonjaviste.lib.mvp.MvpFragment;

@ParcelClass(PageModel.class)
public class PageFragment extends MvpFragment<PagePresenter> implements PageView {

    private PostDetailBinding binding;

    @Override protected PagePresenter createPresenter() {
        return CoseNonJavisteApp.getComponent(this).getPagePresenter();
    }

    @SuppressLint("SetJavaScriptEnabled") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PostDetailBinding.bind(inflater.inflate(R.layout.post_detail, container, false));
        binding.setPresenter(presenter);

        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        File externalFilesDir = getActivity().getExternalFilesDir(null);
        if (externalFilesDir != null) {
            settings.setAppCachePath(externalFilesDir.getAbsolutePath());
            settings.setAppCacheMaxSize(20 * 1024 * 1024);
            settings.setAppCacheEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override public WebResourceResponse shouldInterceptRequest(WebView view11, String url) {
                if (url.equalsIgnoreCase("http://www.cosenonjaviste.it/wp-content/themes/flexform/style.css")) {
                    return getCssWebResourceResponseFromAsset();
                }
                if (url.startsWith("https://pbs.twimg.com/")
                        || url.startsWith("https://cdn.syndication.twimg.com/")
                        || url.startsWith("https://syndication.twitter.com")
                        || url.contains("platform.twitter.com/")
                        || url.startsWith("http://www.facebook.com/plugins/like_box.php")
                        || url.startsWith("https://fbcdn-profile-")
                        || url.contains("sharethis.com/")
                        || url.equals("http://www.cosenonjaviste.it/wp-content/uploads/2013/06/favicon.ico")
                        ) {
                    return null;
                }
                return super.shouldInterceptRequest(view11, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return new WebResourceResponse("text/css", "UTF-8", getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                presenter.loading.set(false);
            }
        });
        return binding.getRoot();
    }

    @Override public void update(PageModel model) {
        binding.webView.loadUrl(presenter.getPostUrl());
    }
}
