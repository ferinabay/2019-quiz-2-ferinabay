package id.ac.polinema.todoretrofit;

import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;

    public Session(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean isLogin() {
        String token = preferences.getString(Constant.KEY_TOKEN, null);
        return (token != null);
    }

    public void setSession(String token) {
        preferences.edit()
                .putString(Constant.KEY_TOKEN, token)
                .apply();
    }

    public void removeSession() {
        preferences.edit()
                .remove(Constant.KEY_TOKEN)
                .apply();
    }

    public String getToken() {
        String token = preferences.getString(Constant.KEY_TOKEN, null);
        return token;
    }
}
