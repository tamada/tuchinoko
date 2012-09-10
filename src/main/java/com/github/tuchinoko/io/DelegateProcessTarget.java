package com.github.tuchinoko.io;

import java.io.IOException;
import java.io.InputStream;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.TargetType;

class DelegateProcessTarget implements ProcessTarget{
    private ProcessTarget target;
    private TargetSource source;

    public DelegateProcessTarget(TargetSource source, ProcessTarget target){
        this.source = source;
        this.target = target;
    }

    @Override
    public String getName(){
        return target.getName();
    }

    @Override
    public String getClassName(){
        return target.getClassName();
    }

    @Override
    public InputStream getSource() throws IOException{
        return target.getSource();
    }

    @Override
    public TargetType getType(){
        return target.getType();
    }

    @Override
    public TargetSource getTargetSource(){
        return source;
    }

    public void setTargetSource(TargetSource source){
        if(source == null){
            throw new NullPointerException();
        }
        this.source = source;
    }
}
