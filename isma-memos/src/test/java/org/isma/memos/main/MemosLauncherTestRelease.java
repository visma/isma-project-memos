package org.isma.memos.main;
import org.isma.memos.db.HSQLConfigurationTestRelease;
import org.isma.memos.db.HSQLHandler;
import org.isma.memos.main.dao.InitSQLDAO;
import org.isma.memos.results.dao.MemoSQLDAO;
import org.isma.memos.tag.dao.TagSQLDAO;
public class MemosLauncherTestRelease extends MemosLauncher {

    protected MemosLauncherTestRelease() throws Exception {
    }


    @Override
    protected boolean mustDisplaySplashScreen() {
        return false;
    }


    @Override
    protected void registerDAOs() {
        picoContainer.registerComponentImplementation(HSQLHandler.class);
        picoContainer.registerComponentImplementation(HSQLConfigurationTestRelease.class);

        picoContainer.registerComponentImplementation(TagSQLDAO.class);
        picoContainer.registerComponentImplementation(MemoSQLDAO.class);
        picoContainer.registerComponentImplementation(InitSQLDAO.class);
    }

//    @Override
//    protected void registerDAOs() {
//        picoContainer.registerComponentImplementation(MockTagDAO.class);
//        picoContainer.registerComponentImplementation(MockMemoDAO.class);
//        picoContainer.registerComponentImplementation(MockInitDAO.class);
//    }


    public static void main(String[] args) throws Exception {
        new MemosLauncherTestRelease().launch();
    }
}
