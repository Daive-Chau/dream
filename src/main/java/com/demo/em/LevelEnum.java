package com.demo.em;

public enum LevelEnum {
    PRIMARY(1,"初级工程师"),
    MIDDLE(2,"中级工程师"),
    HIGH(3,"高级工程师");
    private Integer type;
    private String name;

    LevelEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static LevelEnum getLevelByType(Integer type){
        for(LevelEnum levelEnum :LevelEnum.values()){
            if(levelEnum.getType() == type){
                return levelEnum;
            }
        }
        return PRIMARY;
    }

    public static LevelEnum getLevelByName(String name){
        for(LevelEnum levelEnum :LevelEnum.values()){
            if(levelEnum.getName().equals(name)){
                return levelEnum;
            }
        }
        return PRIMARY;
    }
}
