package app.trivial.asier.model.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import app.trivial.asier.model.app.AppParameters
import java.io.IOException
import java.security.GeneralSecurityException

object SharedPreferencesUtil {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            try {
                val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                EncryptedSharedPreferences.create(
                    AppParameters.SP_ENCRYPTED_FILE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
                PreferenceManager.getDefaultSharedPreferences(context)
            } catch (e: IOException) {
                e.printStackTrace()
                PreferenceManager.getDefaultSharedPreferences(context)
            }

        } else {
            PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun put(context: Context, key: String?, `object`: Any) {
        val editor = getSharedPreferences(context).edit()
        when (`object`) {
            is String -> {
                editor.putString(key, `object`)
            }

            is Int -> {
                editor.putInt(key, `object`)
            }

            is Boolean -> {
                editor.putBoolean(key, `object`)
            }

            is Float -> {
                editor.putFloat(key, `object`)
            }

            is Long -> {
                editor.putLong(key, `object`)
            }

            else -> {
                editor.putString(key, `object`.toString())
            }
        }
        editor.apply()
    }

    operator fun get(context: Context, key: String?, defaultObject: Any?): Any? {
        val sp = getSharedPreferences(context)
        if (contains(context, key)) {
            when (defaultObject) {
                is String -> {
                    return sp.getString(key, defaultObject as String?)
                }

                is Int -> {
                    return sp.getInt(key, (defaultObject as Int?)!!)
                }

                is Boolean -> {
                    return sp.getBoolean(key, (defaultObject as Boolean?)!!)
                }

                is Float -> {
                    return sp.getFloat(key, (defaultObject as Float?)!!)
                }

                is Long -> {
                    return sp.getLong(key, (defaultObject as Long?)!!)
                }
            }
        }
        return defaultObject
    }

    fun contains(context: Context, key: String?): Boolean {
        return getSharedPreferences(context).contains(key)
    }

    fun remove(context: Context, key: String?) {
        if (contains(context, key)) {
            val editor = getSharedPreferences(context).edit()
            editor.remove(key)
            editor.apply()
        }
    }
}