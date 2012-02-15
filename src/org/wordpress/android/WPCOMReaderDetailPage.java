package org.wordpress.android;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WPCOMReaderDetailPage extends WPCOMReaderBase {
	
	private String cachedPage = null;
	private String requestedURL = null;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.reader);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			cachedPage = extras.getString("cachedPage");
			requestedURL = extras.getString("requestedURL");
		}
		
		final WebView wv = (WebView) findViewById(R.id.webView);
		this.setDefaultWebViewSettings(wv);
		wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); //override the default setting of NO_CACHE
		wv.addJavascriptInterface( new JavaScriptInterface(this), "Android" );
		wv.setWebViewClient(new WordPressWebViewClient());
		
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					//String methodCall = "Reader2.show_article_details()";
					//wv.loadUrl("javascript:"+methodCall);
				}
			}
		});
		
		//wv.loadData(Uri.encode(this.cachedPage), "text/html", HTTP.UTF_8);
		wv.loadUrl(requestedURL);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// ignore orientation change
		super.onConfigurationChanged(newConfig);
	}
}