package com.github.tuchinoko.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 何も返さないItaratorの実装クラスです．
 *
 * @author Haruaki Tamada
 */
public final class NullIterator<T> implements Iterator<T>{
    /**
     * 常にfalseを返します．
     */
    @Override
    public boolean hasNext(){
        return false;
    }

    /**
     * 常にNoSuchElementExceptionが投げられます．
     */
    @Override
    public T next(){
        throw new NoSuchElementException();
    }

    /**
     * 何も行いません．
     */
    @Override
    public void remove(){
    }
}
