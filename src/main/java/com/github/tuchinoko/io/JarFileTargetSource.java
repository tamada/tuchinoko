package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

/**
 * <p>
 * Jarファイル(Zipファイル)に格納された{@link ProcessTarget <code>ProcessTarget</code>}
 * を管理する{@link TargetSource <code>TargetSource</code>}です．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class JarFileTargetSource implements TargetSource{
    private JarFile file;
    private Manifest manifest;

    /**
     * 指定されたfileが表すjarファイルに含まれるファイルを
     * {@link ProcessTarget <code>ProcessTarget</code>}
     * とするTargetSourceオブジェクトを構築します．
     */
    public JarFileTargetSource(File file) throws IOException{
        this(new JarFile(file));
    }

    /**
     * 指定されたJarファイルに含まれるファイルを
     * {@link ProcessTarget <code>ProcessTarget</code>}
     * とするTargetSourceオブジェクトを構築します．
     */
    public JarFileTargetSource(JarFile file){
        this.file = file;
    }

    /**
     * <p>
     * このオブジェクトが表すjarファイルに含まれる
     * MANIFESTファイルを返します．
     * </p><p>
     * マニフェストファイルが含まれない場合，nullを返します．
     * </p>
     */
    public Manifest getManifest(){
        if(manifest == null){
            try{
                manifest = file.getManifest();
            } catch(IOException e){
            }
        }
        return manifest;
    }

    /**
     * このProcessTargetを閉じます．
     * このメソッド呼び出し以降，他のメソッド呼び出しは正常に終了しなくなります．
     */
    public void close() throws IOException{
        file.close();
    }

    /**
     * このTargetSourceの名前を返します．
     */
    @Override
    public String getName(){
        return file.getName();
    }

    /**
     * このTargetSourceが指定されたファイルを保持していればtrueを返します．
     */
    @Override
    public boolean contains(String target){
        JarEntry entry = file.getJarEntry(target);
        return entry != null;
    }

    /**
     * このオブジェクトに含まれるProcessTargetの列挙を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator(){
        return new Iterator<ProcessTarget>(){
            Enumeration<JarEntry> entries = file.entries();
            JarEntry next = findNext();

            @Override
            public boolean hasNext(){
                return next != null;
            }

            @Override
            public ProcessTarget next(){
                ProcessTarget target = null;
                try{
                    String name = next.getName();
                    target = new PlainProcessTarget(JarFileTargetSource.this, name, file.getInputStream(next));
                    next = findNext();
                } catch(IOException e){
                    e.printStackTrace();
                }
                return target;
            }

            @Override
            public void remove(){
            }

            private JarEntry findNext(){
                JarEntry entry = null;
                do{
                    if(entries.hasMoreElements()){
                        entry = entries.nextElement();
                    }
                    else{
                        break;
                    }
                } while(entry.isDirectory());
                return entry;
            }
        };
    }
}
