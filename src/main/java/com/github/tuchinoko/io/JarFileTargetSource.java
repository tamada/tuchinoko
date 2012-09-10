package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage(), e);
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

    private InputStream getInputStream(JarEntry entry) throws IOException{
        return file.getInputStream(entry);
    }

    /**
     * このオブジェクトに含まれるProcessTargetの列挙を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator(){
        return new JarEntryIterator(this, file.entries());
    }

    private static class JarEntryIterator implements Iterator<ProcessTarget>{
        private Enumeration<JarEntry> entries;
        private JarFileTargetSource source;
        private JarEntry next;

        public JarEntryIterator(JarFileTargetSource source, Enumeration<JarEntry> entries){
            this.source = source;
            this.entries = entries;
            next = findNext();
        }

        @Override
        public boolean hasNext(){
            return next != null;
        }

        @Override
        public ProcessTarget next(){
            ProcessTarget target = null;
            try{
                String name = next.getName();
                target = new PlainProcessTarget(source, name, source.getInputStream(next));
                next = findNext();
            } catch(IOException e){
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.WARNING, e.getMessage(), e);
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
    }
}
