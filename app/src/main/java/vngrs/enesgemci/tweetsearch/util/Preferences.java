package vngrs.enesgemci.tweetsearch.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Set;

import vngrs.enesgemci.tweetsearch.R;

import static android.content.SharedPreferences.*;

/**
 * Secure / Easy to use helper class for shared preferences
 *
 * @author enesgemci
 */
public class Preferences {

    private static SharedPreferences preferences = null;

    /**
     * Helper to write to editor if key specified is null. Writes a String property with the given
     * integer
     */
    public static void setIfUnset(SharedPreferences prefs, Editor editor, Resources r,
                                  int keyResource, int value) {
        String key = r.getString(keyResource);
        if (!prefs.contains(key)) {
            editor.putString(key, Integer.toString(value));
        }
    }

    /**
     * Helper to write to editor if key specified is null
     */
    public static void setIfUnset(SharedPreferences prefs, Editor editor, Resources r,
                                  int keyResource, boolean value) {
        String key = r.getString(keyResource);
        if (!prefs.contains(key) || !(prefs.getAll().get(key) instanceof Boolean)) {
            editor.putBoolean(key, value);
        }
    }

	/*
     * ======================================================================
	 * ======================================================= helper methods
	 * ======================================================================
	 */

    /**
     * Helper to write to editor if key specified is null
     */
    public static void setIfUnset(SharedPreferences prefs, Editor editor, Resources r, int keyResource, String value) {
        String key = r.getString(keyResource);

        if (!prefs.contains(key) || !(prefs.getAll().get(key) instanceof String)) {
            editor.putString(key, value);
        }
    }

    /**
     * Get preferences object from the context
     */
    public static SharedPreferences getPrefs() {
        if (preferences != null) {
            return preferences;
        }
        if (MInjector.getApplicationContext() == null) {
            L.e("[SharedPreferences.getPrefs] MInjector.getApplicationContext() returns null"
                    + " you should extend TApplication (not activity) inorder to use this preferences");
            return null;
        }
        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(MInjector.getApplicationContext());

            // try writing
            preferences.edit().commit();
        } catch (Exception e) {
            L.e(e);
            String alternate = "preferences" + android.os.Process.myUid();
            preferences = MInjector.getApplicationContext().getSharedPreferences(alternate, Context.MODE_PRIVATE);
        }

        return preferences;
    }

    /**
     * @return true if given preference is set
     */
    public static boolean isSet(String key) {
        return getPrefs().contains(EncryptionUtilities.getSHA256(key));
    }

    // --- preference fetching (string)

    /**
     * Gets an string value from a string preference. Returns null if the value is not set
     *
     * @return integer value, or null on error
     */
    public static String getString(String key) {
        return getString(key, "");
    }

    /**
     * Gets an string value from a string preference. Returns null if the value is not set
     *
     * @return integer value, or null on error
     */
    public static String getString(String key, String defValue) {
        return getStringEncrypted(key, defValue);
    }

    private static String getStringEncrypted(String key, String defValue) {
        String encrypted = getPrefs().getString(EncryptionUtilities.getSHA256(key), "");
        String decrypted = "";

        if (!TextUtils.isEmpty(encrypted)) {
            try {
                decrypted = EncryptionUtilities.aesDecrypt(encrypted, MInjector.getApplicationContext().getString(R.string.pref_pass));
            } catch (Exception ex) {
//                decrypted = getString(key);
                L.e("ENCRYPTION ERROR!!!");
                L.e(ex);
            }

            return decrypted;
        } else {
            return defValue;
        }
    }

    public static Set<String> getStringSet(String key) {
        return getPrefs().getStringSet(EncryptionUtilities.getSHA256(key), null);
    }

    public static void setStringSet(String key, Set<String> values) {
        Editor editor = getPrefs().edit();
        editor.putStringSet(key, values);
        editor.commit();
    }

    /**
     * Gets an integer value from a string preference. Returns null if the value is not set or not
     * an integer.
     *
     * @param keyResource resource from string.xml
     * @return integer value, or null on error
     */
    public static int getIntegerFromString(int keyResource, int defaultValue) {
        Resources r = MInjector.getApplicationContext().getResources();
        String value = getPrefs().getString(r.getString(keyResource), "");
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            L.e(e);
            return defaultValue;
        }
    }

    /**
     * Gets an float value from a string preference. Returns null if the value is not set or not an
     * flat.
     *
     * @param keyResource resource from string.xml
     */
    public static Float getFloatFromString(int keyResource) {
        Resources r = MInjector.getApplicationContext().getResources();
        String value = getPrefs().getString(r.getString(keyResource), "");

        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            L.e(e);
            return null;
        }
    }

    /**
     * Sets string preference
     */
    public static void setString(int keyResource, String newValue) {
        setStringEncrypted(MInjector.getApplicationContext().getString(keyResource), newValue);
    }

    /**
     * Sets string preference
     */
    public static void setString(String key, String newValue) {
        setStringEncrypted(key, newValue);
    }

    /**
     * Sets string preference
     */
    public static void setStringEncrypted(String key, String newValue) {
        String encrypted = "";
        try {
            if (!TextUtils.isEmpty(newValue)) {
                encrypted = EncryptionUtilities.aesEncrypt(newValue, MInjector.getApplicationContext().getString(R.string.pref_pass));
            } else {
                encrypted = newValue;
            }
        } catch (Exception ex) {
            L.e("ENCRYPTION ERROR!!!");
            L.e(ex);
        }

        Editor editor = getPrefs().edit();
        editor.putString(EncryptionUtilities.getSHA256(key), encrypted);
        editor.commit();
    }

    // --- preference fetching (boolean)

    /**
     * Gets a boolean preference (e.g. a CheckBoxPreference setting)
     *
     * @return default if value is unset otherwise the value
     */
    public static boolean getBoolean(String key, boolean defValue) {
        try {
            return getPrefs().getBoolean(EncryptionUtilities.getSHA256(key), defValue);
        } catch (Exception e) {
            L.e(e);
            return defValue;
        }
    }

    /**
     * Gets a boolean preference (e.g. a CheckBoxPreference setting)
     *
     * @return default if value is unset otherwise the value
     */
    public static boolean getBoolean(int keyResources, boolean defValue) {
        return getBoolean(MInjector.getApplicationContext().getString(keyResources), defValue);
    }

    /**
     * Sets boolean preference
     */
    public static void setBoolean(int keyResource, boolean value) {
        setBoolean(MInjector.getApplicationContext().getString(keyResource), value);
    }

    /**
     * Sets boolean preference
     */
    public static void setBoolean(String key, boolean value) {
        Editor editor = getPrefs().edit();
        editor.putBoolean(EncryptionUtilities.getSHA256(key), value);
        editor.commit();
    }

    public static void setSerialObject(String key, Object value) {
        Editor editor = getPrefs().edit();
        editor.putString(EncryptionUtilities.getSHA256(key), new Gson().toJson(value));
        editor.commit();
    }

    public static Object getSerialObject(String key, Class tClass) {
        return new Gson().fromJson(getPrefs().getString(EncryptionUtilities.getSHA256(key), ""), tClass);
    }

    // --- preference fetching (int)

    /**
     * Gets a int preference
     *
     * @return default if value is unset otherwise the value
     */
    public static int getInt(String key, int defValue) {
        return getPrefs().getInt(EncryptionUtilities.getSHA256(key), defValue);
    }

    /**
     * Sets int preference
     */
    public static void setInt(String key, int value) {
        Editor editor = getPrefs().edit();
        editor.putInt(EncryptionUtilities.getSHA256(key), value);
        editor.commit();
    }

    // --- preference fetching (long)

    /**
     * Gets a long preference
     *
     * @return default if value is unset otherwise the value
     */
    public static long getLong(String key, long defValue) {
        return getPrefs().getLong(EncryptionUtilities.getSHA256(key), defValue);
    }

    /**
     * Sets long preference
     */
    public static void setLong(String key, long value) {
        Editor editor = getPrefs().edit();
        editor.putLong(EncryptionUtilities.getSHA256(key), value);
        editor.commit();
    }

    /**
     * Clears a preference
     */
    public static void clear(String key) {
        Editor editor = getPrefs().edit();
        editor.remove(EncryptionUtilities.getSHA256(key));
        editor.remove(key);
        editor.commit();
    }
}