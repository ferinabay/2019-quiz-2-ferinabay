package id.ac.polinema.todoretrofit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Application extends android.app.Application {

	private static SharedPreferences preferences;
	private static Session session;

	@Override
	public void onCreate() {
		super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        session = new Session(preferences);
	}

	public static SharedPreferences providePreferences() {
		return preferences;
	}

	public static Session provideSession() {
		return session;
	}
}
