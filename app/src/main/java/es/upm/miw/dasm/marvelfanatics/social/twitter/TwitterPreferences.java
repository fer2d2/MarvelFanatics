package es.upm.miw.dasm.marvelfanatics.social.twitter;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface TwitterPreferences {

    @DefaultBoolean(false)
    boolean isUserLoggedIn();

    @DefaultString("")
    String userName();

    @DefaultString("")
    String userPicture();

    @DefaultString("")
    String oauthToken();

    @DefaultString("")
    String oauthSecret();
}
