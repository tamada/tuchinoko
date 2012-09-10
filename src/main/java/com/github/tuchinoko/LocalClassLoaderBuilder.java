package com.github.tuchinoko;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * デフォルトの{@link ClassLoaderBuilder}です．
 * 以下の場所を検索するクラスローダを構築します．
 * </p>
 * <ul>
 *   <li>このクラスのロード元のjarファイルと同じ階層にある全てのjarファイルとzipファイル．</li>
 *   <li>$HOME/.tuchinoko/lib 以下にある全てのjarファイルとzipファイル．</li>
 *   <li>システムプロパティ tuchinoko.lib に設定されているjarファイルとzipファイル，もしくはディレクトリ．</li>
 * </ul>
 * <p>
 * tuchinoko.lib はシステムのパス区切り文字(Windowsでは「;」，UNIXでは「:」で表される文字)
 * で複数のディレクトリを指定できます．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class LocalClassLoaderBuilder implements ClassLoaderBuilder{
    public String getName(){
        return "local";
    }

    public ClassLoader createLoader() throws MalformedURLException{
        final URL[] urls = collectUrls();
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){
            public ClassLoader run(){
                return new URLClassLoader(urls);
            }
        });
    }

    URL[] collectUrls() throws MalformedURLException{
        final List<URL> list = new ArrayList<URL>();
        list.add(getJarUrlInThisClass());
        for(URL url: getHomeLibUrls()){
            if(url != null){
                list.add(url);
            }
        }
        for(URL url: getSystemProperties()){
            if(url != null){
                list.add(url);
            }
        }
        return list.toArray(new URL[list.size()]);
    }

    private void findJarsInDirectory(File dir, List<URL> list) throws MalformedURLException{
        if(dir.isDirectory()){
            for(File file: dir.listFiles()){
                String name = file.getName();
                if(file.isDirectory()){
                    findJarsInDirectory(file, list);
                }
                else if(name.endsWith(".jar")){
                    list.add(file.toURI().toURL());
                }
            }
        }
    }

    private URL[] getHomeLibUrls() throws MalformedURLException{
        File homeLibPath = new File(System.getProperty("user.home") + "/.tuchinoko/lib");
        
        List<URL> list = new ArrayList<URL>();
        if(homeLibPath.exists() && homeLibPath.isDirectory()){
            findJarsInDirectory(homeLibPath, list);
        }
        return list.toArray(new URL[list.size()]);
    }

    private URL getJarUrlInThisClass() throws MalformedURLException{
        URL url = getClass().getResource("/com/github/tuchinoko/LocalClassLoaderBuilder.class");
        String path = url.toString();
        if(path.startsWith("jar:")){
            path = path.substring("jar:".length(), path.indexOf('!'));
        }
        if(path.startsWith("file:")){
            if(path.endsWith(".class")){
                path = path.substring(0, path.indexOf("com/github/tuchinoko/LocalClassLoaderBuilder.class"));
            }
            url = new URL(path);
        }
        return url;
    }

    private URL[] getSystemProperties() throws MalformedURLException{
        String envPathList = System.getProperty("tuchinoko.lib");
        String[] envPathArray = new String[0];
        if(envPathList != null){
            envPathArray = envPathList.split(File.pathSeparator);
        }
        URL[] urls = new URL[envPathArray.length];
        for(int i = 0; i < envPathArray.length; i++){
            File file = new File(envPathArray[i]);
            if(file.exists()){
                urls[i] = file.toURI().toURL();
            }
            else{
                urls[i] = null;
            }
        }
        return urls;

    }
}
