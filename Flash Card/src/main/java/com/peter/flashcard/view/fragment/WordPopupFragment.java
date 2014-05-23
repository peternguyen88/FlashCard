package com.peter.flashcard.view.fragment;

import android.app.DialogFragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.peter.flashcard.R;
import com.peter.flashcard.model.Word;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Peter on 5/19/2014.
 */
@EFragment(R.layout.word_definition_popup_fragment)
public class WordPopupFragment extends DialogFragment {

    public static final String VOCABULARY_URL = "http://www.vocabulary.com/dictionary/";

    @ViewById(R.id.definitionWebView)
    WebView definitionWebView;

    Word word;

    public static WordPopupFragment instance(Word word) {
        WordPopupFragment popup = new WordPopupFragment_();
        popup.word = word;
        return popup;
    }

    @AfterViews
    public void openDefinition() {
//        definitionWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }
//        });
//        definitionWebView.getSettings().setJavaScriptEnabled(true);
//        definitionWebView.loadUrl(VOCABULARY_URL+word.getWord()+"/");


        crawlWebContent();
    }

    @Background
    public void crawlWebContent(){
        try {
            Document document = Jsoup.connect(VOCABULARY_URL + word.getWord()).get();
            document.select(".instances").remove();

            Elements styleElements = document.head().select("link[type=text/css]");
            Element definition = document.getElementById("definition");

            // Replace External CSS with inline CSS
            for(Element style : styleElements){
                Document css = Jsoup.connect("http://" + style.attr("href").replaceAll("//", "")).get();

                String styleRules = css.body().text().trim();
                String delims = "{}";
                StringTokenizer st = new StringTokenizer(styleRules, delims);

                while (st.countTokens()>1){
                    try {
                        String selector = st.nextToken(), properties = st.nextToken();
                        Elements toReplaceStyle = definition.select(selector);

                        for (Element replace : toReplaceStyle) {
                            String oldProperties = replace.attr("style");
                            replace.attr("style", oldProperties.length() > 0 ?
                                    concatenateProperties(oldProperties, properties) : properties);
                        }
                    }
                    catch (Exception e){
                        // Do Nothing
                    }
                }
                style.remove();
            }

            String html = definition.html();

            displayResult(html);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void displayResult(String html){
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        TagNode tagNode = new HtmlCleaner(props).clean(html);
        SimpleHtmlSerializer htmlSerializer =  new SimpleHtmlSerializer(props);
        definitionWebView.loadDataWithBaseURL(null, htmlSerializer.getAsString(tagNode),
                "text/html", "charset=UTF-8", null);
    }

    private static String concatenateProperties(String oldProp, String newProp) {
        oldProp = oldProp.trim();
        if (!newProp.endsWith(";"))
            newProp += ";";
        return newProp + oldProp; // The existing (old) properties should take precedence.
    }
}
