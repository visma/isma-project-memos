package org.isma.memos;

import org.isma.core.Configuration;

import java.io.File;
public class MemoManagerConfiguration implements Configuration {

    public File getAttachmentTmpDirectory(){
        return new File(System.getProperty("java.io.tmpdir") + "/memomanager/");
    }
}
