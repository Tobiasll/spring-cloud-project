package com.tobias.common;

public enum Application {
    /**
     * 公用模块
     */
    COMMON(new String[]{"com.hq.common","school-tcommon"}),
    /**
     * admin模块
     */
    ADMIN(new String[]{"com.hq.admin","school-tadmin"}),
    /**
     * 教师端
     */
    TEACHING(new String[]{"com.hq.teaching","school-teaching"});


    private String[] path;

    private Application(String[] path){
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

}
