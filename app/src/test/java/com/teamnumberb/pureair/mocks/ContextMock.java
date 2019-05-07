package com.teamnumberb.pureair.mocks;

import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;
import android.view.Display;

import java.io.*;

import static org.junit.Assert.*;

public class ContextMock extends Context {
    public AssetManager getAssets() {
        throw new RuntimeException("NotImplemented");
    }

    public Resources getResources() {
        throw new RuntimeException("NotImplemented");
    }

    public PackageManager getPackageManager() {
        throw new RuntimeException("NotImplemented");
    }

    public ContentResolver getContentResolver() {
        throw new RuntimeException("NotImplemented");
    }

    public Looper getMainLooper() {
        throw new RuntimeException("NotImplemented");
    }

    public Context getApplicationContext() {
        throw new RuntimeException("NotImplemented");
    }

    public void setTheme(int i) {
        throw new RuntimeException("NotImplemented");

    }

    public Resources.Theme getTheme() {
        throw new RuntimeException("NotImplemented");
    }

    public ClassLoader getClassLoader() {
        throw new RuntimeException("NotImplemented");
    }

    public String getPackageName() {
        throw new RuntimeException("NotImplemented");
    }

    public ApplicationInfo getApplicationInfo() {
        throw new RuntimeException("NotImplemented");
    }

    public String getPackageResourcePath() {
        throw new RuntimeException("NotImplemented");
    }

    public String getPackageCodePath() {
        throw new RuntimeException("NotImplemented");
    }

    public SharedPreferences getSharedPreferences(String s, int i) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean moveSharedPreferencesFrom(Context context, String s) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean deleteSharedPreferences(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public String expectedInputFilename = "";
    public boolean openFileInputShouldThrow = false;
    public FileInputStream inputStreamToReturn = null;

    public FileInputStream openFileInput(String s) throws FileNotFoundException {
        assertEquals(expectedInputFilename, s);
        if(openFileInputShouldThrow) {
            throw new FileNotFoundException("Test exception");
        }
        return inputStreamToReturn;
    }

    public String expectedOutputFilename = "";
    public int expectedOutputFileMode = -1;
    public boolean openFileOutputShouldThrow = false;
    public FileOutputStream outputStreamToReturn = null;
    public FileOutputStream openFileOutput(String s, int i) throws FileNotFoundException {
        assertEquals(expectedOutputFilename, s);
        assertEquals(expectedOutputFileMode, i);
        if(openFileOutputShouldThrow) {
            throw new FileNotFoundException("Test exception");
        }
        return outputStreamToReturn;
    }

    public boolean deleteFile(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public File getFileStreamPath(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public File getDataDir() {
        throw new RuntimeException("NotImplemented");
    }

    public File getFilesDir() {
        throw new RuntimeException("NotImplemented");
    }

    public File getNoBackupFilesDir() {
        throw new RuntimeException("NotImplemented");
    }


    public File getExternalFilesDir(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public File[] getExternalFilesDirs(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public File getObbDir() {
        throw new RuntimeException("NotImplemented");
    }

    public File[] getObbDirs() {
        throw new RuntimeException("NotImplemented");
    }

    public File getCacheDir() {
        throw new RuntimeException("NotImplemented");
    }

    public File getCodeCacheDir() {
        throw new RuntimeException("NotImplemented");
    }


    public File getExternalCacheDir() {
        throw new RuntimeException("NotImplemented");
    }

    public File[] getExternalCacheDirs() {
        throw new RuntimeException("NotImplemented");
    }

    public File[] getExternalMediaDirs() {
        throw new RuntimeException("NotImplemented");
    }

    public String[] fileList() {
        throw new RuntimeException("NotImplemented");
    }

    public File getDir(String s, int i) {
        throw new RuntimeException("NotImplemented");
    }

    public SQLiteDatabase openOrCreateDatabase(String s,
                                               int i,
                                               SQLiteDatabase.CursorFactory cursorFactory) {
        throw new RuntimeException("NotImplemented");
    }

    public SQLiteDatabase openOrCreateDatabase(String s,
                                               int i,
                                               SQLiteDatabase.CursorFactory cursorFactory,
                                               DatabaseErrorHandler databaseErrorHandler) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean moveDatabaseFrom(Context context, String s) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean deleteDatabase(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public File getDatabasePath(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public String[] databaseList() {
        throw new RuntimeException("NotImplemented");
    }

    public Drawable getWallpaper() {
        throw new RuntimeException("NotImplemented");
    }

    public Drawable peekWallpaper() {
        throw new RuntimeException("NotImplemented");
    }

    public int getWallpaperDesiredMinimumWidth() {
        throw new RuntimeException("NotImplemented");
    }

    public int getWallpaperDesiredMinimumHeight() {
        throw new RuntimeException("NotImplemented");
    }

    public void setWallpaper(Bitmap bitmap) throws IOException {
        throw new RuntimeException("NotImplemented");

    }

    public void setWallpaper(InputStream inputStream) throws IOException {
        throw new RuntimeException("NotImplemented");

    }

    public void clearWallpaper() throws IOException {
        throw new RuntimeException("NotImplemented");

    }

    public void startActivity(Intent intent) {
        throw new RuntimeException("NotImplemented");

    }

    public void startActivity(Intent intent, Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void startActivities(Intent[] intents) {
        throw new RuntimeException("NotImplemented");

    }

    public void startActivities(Intent[] intents, Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void startIntentSender(IntentSender intentSender,
                                  Intent intent,
                                  int i,
                                  int i1,
                                  int i2) throws IntentSender.SendIntentException {
        throw new RuntimeException("NotImplemented");

    }

    public void startIntentSender(IntentSender intentSender,
                                  Intent intent,
                                  int i,
                                  int i1,
                                  int i2,
                                  Bundle bundle)
            throws IntentSender.SendIntentException {
        throw new RuntimeException("NotImplemented");

    }

    public void sendBroadcast(Intent intent) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendBroadcast(Intent intent, String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendOrderedBroadcast(Intent intent, String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendOrderedBroadcast(Intent intent,
                                     String s,
                                     BroadcastReceiver broadcastReceiver,
                                     Handler handler,
                                     int i,
                                     String s1,
                                     Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendBroadcastAsUser(Intent intent,
                                    UserHandle userHandle,
                                    String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendOrderedBroadcastAsUser(Intent intent,
                                           UserHandle userHandle,
                                           String s,
                                           BroadcastReceiver broadcastReceiver,
                                           Handler handler,
                                           int i,
                                           String s1,
                                           Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendStickyBroadcast(Intent intent) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendStickyOrderedBroadcast(Intent intent,
                                           BroadcastReceiver broadcastReceiver,
                                           Handler handler,
                                           int i,
                                           String s,
                                           Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void removeStickyBroadcast(Intent intent) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        throw new RuntimeException("NotImplemented");

    }

    public void sendStickyOrderedBroadcastAsUser(Intent intent,
                                                 UserHandle userHandle,
                                                 BroadcastReceiver broadcastReceiver,
                                                 Handler handler,
                                                 int i,
                                                 String s,
                                                 Bundle bundle) {
        throw new RuntimeException("NotImplemented");

    }

    public void removeStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        throw new RuntimeException("NotImplemented");

    }


    public Intent registerReceiver(BroadcastReceiver broadcastReceiver,
                                   IntentFilter intentFilter) {
        throw new RuntimeException("NotImplemented");
    }


    public Intent registerReceiver(BroadcastReceiver broadcastReceiver,
                                   IntentFilter intentFilter,
                                   int i) {
        throw new RuntimeException("NotImplemented");
    }


    public Intent registerReceiver(BroadcastReceiver broadcastReceiver,
                                   IntentFilter intentFilter,
                                   String s,
                                   Handler handler) {
        throw new RuntimeException("NotImplemented");
    }


    public Intent registerReceiver(BroadcastReceiver broadcastReceiver,
                                   IntentFilter intentFilter,
                                   String s,
                                   Handler handler,
                                   int i) {
        throw new RuntimeException("NotImplemented");
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        throw new RuntimeException("NotImplemented");

    }


    public ComponentName startService(Intent intent) {
        throw new RuntimeException("NotImplemented");
    }


    public ComponentName startForegroundService(Intent intent) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean stopService(Intent intent) {
        throw new RuntimeException("NotImplemented");
    }

    public boolean bindService(Intent intent,
                               ServiceConnection serviceConnection,
                               int i) {
        throw new RuntimeException("NotImplemented");
    }

    public void unbindService(ServiceConnection serviceConnection) {
        throw new RuntimeException("NotImplemented");

    }

    public boolean startInstrumentation(ComponentName componentName,
                                        String s,
                                        Bundle bundle) {
        throw new RuntimeException("NotImplemented");
    }

    public Object getSystemService(String s) {
        throw new RuntimeException("NotImplemented");
    }


    public String getSystemServiceName(Class<?> aClass) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkPermission(String s, int i, int i1) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkCallingPermission(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkCallingOrSelfPermission(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkSelfPermission(String s) {
        throw new RuntimeException("NotImplemented");
    }

    public void enforcePermission(String s,
                                  int i,
                                  int i1,
                                  String s1) {
        throw new RuntimeException("NotImplemented");

    }

    public void enforceCallingPermission(String s,
                                         String s1) {
        throw new RuntimeException("NotImplemented");

    }

    public void enforceCallingOrSelfPermission(String s,
                                               String s1) {
        throw new RuntimeException("NotImplemented");

    }

    public void grantUriPermission(String s, Uri uri, int i) {
        throw new RuntimeException("NotImplemented");

    }

    public void revokeUriPermission(Uri uri, int i) {
        throw new RuntimeException("NotImplemented");

    }

    public void revokeUriPermission(String s, Uri uri, int i) {
        throw new RuntimeException("NotImplemented");

    }

    public int checkUriPermission(Uri uri, int i, int i1, int i2) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkCallingUriPermission(Uri uri, int i) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkCallingOrSelfUriPermission(Uri uri, int i) {
        throw new RuntimeException("NotImplemented");
    }

    public int checkUriPermission(Uri uri,
                                  String s,
                                  String s1,
                                  int i,
                                  int i1,
                                  int i2) {
        throw new RuntimeException("NotImplemented");
    }

    public void enforceUriPermission(Uri uri, int i, int i1, int i2, String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void enforceCallingUriPermission(Uri uri, int i, String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void enforceCallingOrSelfUriPermission(Uri uri, int i, String s) {
        throw new RuntimeException("NotImplemented");

    }

    public void enforceUriPermission(Uri uri,
                                     String s,
                                     String s1,
                                     int i,
                                     int i1,
                                     int i2,
                                     String s2) {
        throw new RuntimeException("NotImplemented");

    }

    public Context createPackageContext(String s, int i)
            throws PackageManager.NameNotFoundException {
        throw new RuntimeException("NotImplemented");
    }

    public Context createContextForSplit(String s) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("NotImplemented");
    }

    public Context createConfigurationContext(Configuration configuration) {
        throw new RuntimeException("NotImplemented");
    }

    public Context createDisplayContext(Display display) {
        throw new RuntimeException("NotImplemented");
    }

    public Context createDeviceProtectedStorageContext() {
        throw new RuntimeException("NotImplemented");
    }

    public boolean isDeviceProtectedStorage() {
        throw new RuntimeException("NotImplemented");
    }
}
