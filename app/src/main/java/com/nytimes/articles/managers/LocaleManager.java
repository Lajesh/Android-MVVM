package com.nytimes.articles.managers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.nytimes.articles.common.Constants;
import com.nytimes.articles.utils.SharedPrefUtil;

import java.util.Locale;

import static com.nytimes.articles.common.Constants.LANGUAGE_ENGLISH;

/**
 * Localization manager class which will update the resources when there is a localization change
 * Author: Lajesh
 * Email: lajeshds2007@gmail.com
 * Created: 8/25/18
 * Modified: 8/25/18
 */
public class LocaleManager {
    public static Context setLocale(Context context) {
        return updateResources(context, getLanguage(context));
    }

    /**
     * This method is used to update the device resources incase of language change
     * @param context - Context
     * @param language - User selected language
     * @return updated context
     */
    public static Context setNewLocale(Context context, String language) {
        persistLanguage(context, language);
        return updateResources(context, language);
    }

    /**
     * This method return the current configured language
     * @param context - context
     * @return the current language code
     */
    public static String getLanguage(Context context) {
        return SharedPrefUtil.getStringPreference(context,Constants.LANGUAGE_KEY, LANGUAGE_ENGLISH);
    }

    /**
     * Method sets the current language in SharedPreference
     * @param context - Context
     * @param language - User selected language
     */
    private static void persistLanguage(Context context, String language) {
        SharedPrefUtil.setStringPreference(context, Constants.LANGUAGE_KEY, language);
    }

    /**
     * This method will update the android resources on localization change
     * @param context - Application context
     * @param language  - Selected Language
     * @return the updated context
     */
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        return context;
    }


    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}
