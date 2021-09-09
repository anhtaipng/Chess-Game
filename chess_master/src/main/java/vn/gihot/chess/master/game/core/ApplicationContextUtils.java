package vn.gihot.chess.master.game.core;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        ctx = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}

/*
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

      private static ApplicationContext ctx;

      @Override
      public void setApplicationContext(ApplicationContext appContext) {
        ctx = appContext;
      }

      public static ApplicationContext getApplicationContext() {
        return ctx;
      }
}
 */