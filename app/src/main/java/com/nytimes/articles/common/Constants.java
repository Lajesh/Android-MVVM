package com.nytimes.articles.common;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {
    public static final String BUNDLE_KEY_ARTICLE_URL = "articleUrl";
    public static final String BUNDLE_KEY_ARTICLE_PUBLISHED_DATE = "publishDate";
    public static final String LANGUAGE_KEY = "language_key";

    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_ARABIC = "ar";
    public static final String LANGUAGE_HINDI = "hi";
    public static final String LANGUAGE_RUSSIAN = "ru";

    public static final String LOCALE_EN = "en-US";


    @StringDef({LANGUAGE_ENGLISH, LANGUAGE_ARABIC,LANGUAGE_HINDI,LANGUAGE_RUSSIAN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LanguageCodes {}
    private Constants(){
        // Private constructor to hide the implicit one
    }

}
