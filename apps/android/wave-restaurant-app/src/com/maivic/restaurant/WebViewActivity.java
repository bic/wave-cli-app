package com.maivic.restaurant;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.restaurantapp.R;

public class WebViewActivity extends Activity {

	private WebView webView;
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
 
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
			          //to prevent opening the default browser
			          return (false);
			           }});
		String postData = "data=" + URLEncoder.encode("zj6LuGswGi2Icp33j5nxnaeS75NIN2h7y67kUppBx2rVxWvvn7AtcWMx6WRGxCgmCBoV0sKpDpTXCWIl4AguTmBuhryTkvVyN3jgcSUsghluVKVYua5+SXZXuoCx2MpY6V3pNLTwUS5ydt88i/WzEvs2lHUhLeM1Ont81pTIhirAR6X/9yQlCU6Y2BG6QvjIaBMY7NGFke6UwmWBGRDLhHS8kwjmBc3QZEKSLoyDWbpMCg4l/TVbIPv1swT1XrQxGENVFrMlknnw7OKQ7s7IGBuTBQPglCZKT7Qm4+iqN3i+CiM+gl7YBm1B+M4xUAmCuxPiRdW2leSnVX1ftruqArHsQcPF9wuHQcpU6sDvevXGB8hHMu/cLRqwqvXBTHk7N7Tmn9I8qpokcc3erjey1FHOtss9K0JZPqTJ3FK2q9A9w2+/kUiFrQlXwINKpIf7Dc3V9pBpli9Nn/OuD2XOJ/+F5BTu6vsfAojT/UTrBg083BpzRgRhT7/S0V/5LvXTJJC9sVjOveCcbPG7S/6C7tnni/NDs1nrliPXVSO/Xfqc456Rt80zWCn4A3H9h+MstTQGhEjMUVau8/+p3dr6F2NlSFKv01VksDLLYp2jCnwCP5y+2B1KDbnJkK9Ls8EKu+MlUT2USZoMiZdFipfsbksTS7CGh9a6s5X9TcTl+j2Ue01jcHlfFmkS50JTtXaKd0QcZ1J+AcVE0Wtlv4ENLLfAWQPBc6S94izIpgOsonumLZnZobQtDZ/4N2oQ+Y4pRrSdFq9yWST4Gk8wuD0AhzgadTWZFrt2TAGY0zGBQzFsYOK8bmDpTSZAcEVqHqCAUCfZ58Zyjb3EdoX8CMuntKaw")
			           + "&env_key=" + URLEncoder.encode("X8nAlBLeT6OlIpUdTqK5AHYMNyjaIXL2JyX2zMYTwGNnbIOksBhVDaXY/L/cvTxS/jKrVQ9rMTIg3yTUKIG8n2VHdnPBrN1cvxjogFs57MIMO+qHkOJsbj/ke62E54vlEpsfBkF7k3cA4pDLPpO3fS8cawiWvfyBPVJ02ZblJXk="); 
		//webView.postUrl("https://sandboxsecure.mobilpay.ro", postData.getBytes());
		//webView.postUrl("http://192.168.1.122:21300", postData.getBytes());
		webView.loadUrl("https://www.google.com");
		
		
 
	}
	
    /*@Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
       
            UrlFetchResult fetchRes = api.fetchUrl(url);
            String charset = "utf-8";
            String mime = fetchRes.getMimetype();
            WebResourceResponse res = new WebResourceResponse(mime, charset, new ByteArrayInputStream(fetchRes.getResult()));
            return res;
        
        
    }*/

}
