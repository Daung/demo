package com.wzy.basemodule;

public class ServiceFactory {

    private static volatile ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    private ServiceFactory() {

    }

    private ILoginService loginService;
    private IMyService myService;

    public ILoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public IMyService getMyService() {
        return myService;
    }

    public void setMyService(IMyService myService) {
        this.myService = myService;
    }
}
