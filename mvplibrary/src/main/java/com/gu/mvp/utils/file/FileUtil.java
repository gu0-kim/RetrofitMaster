package com.gu.mvp.utils.file;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** Created by devel on 2018/4/19. */
public class FileUtil {
  private static final String TAG = FileUtil.class.getSimpleName();

  public FileUtil() {}

  public interface NotifyProgressCallback {
    void callback(int progress);
  }

  public File save2CacheFile(Context context, InputStream inputStream) {
    return save2CacheFile(context, System.currentTimeMillis() + ".jpg", inputStream);
  }

  public File save2CacheFile(Context context, String fileName, InputStream inputStream) {
    return save2CacheFile(context, null, fileName, inputStream);
  }

  public File save2CacheFile(
      Context context, String dirPath, String fileName, InputStream sourceInputStream) {
    File file = getCacheFileByName(context, dirPath, fileName);
    if (file == null) {
      try {
        throw new Exception("---------CREATE FILE ERROR,PLEASE CHECK IT!-----------");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    FileOutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(file);
      byte[] buf = new byte[512];
      int read;
      while ((read = sourceInputStream.read(buf)) != -1) {
        outputStream.write(buf, 0, read);
      }
      outputStream.flush();
      return file;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
          Log.e(TAG, "saveDownLoad: close outputStream");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public String getNameFromPath(String path) {
    return path.substring(path.lastIndexOf(File.separator) + 1, path.length());
  }

  public long getFileSize(String path) {
    File file = new File(path);
    return file.length();
  }

  public String cacheFileExist(Context context, String fileName) {
    return cacheFileExist(context, null, fileName);
  }

  public String cacheFileExist(Context context, String dirPath, String fileName) {
    File file = getCacheFileByName(context, dirPath, fileName);
    if (file != null) return file.exists() ? file.getPath() : null;
    return null;
  }

  private File getCacheFileByName(Context context, String fileName) {
    return getCacheFileByName(context, null, fileName);
  }

  private File getCacheFileByName(Context context, String dirPath, String fileName) {
    if (dirPath != null) {
      if (createCacheFileDir(context, dirPath))
        return new File(context.getCacheDir(), dirPath + File.separator + fileName);
      else return null;
    }
    return new File(context.getCacheDir(), fileName);
  }

  private boolean createCacheFileDir(Context context, String dirPath) {
    File dir = new File(context.getCacheDir(), dirPath);
    return dir.exists() || dir.mkdirs();
  }
}
