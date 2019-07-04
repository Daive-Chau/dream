package com.demo.em;

public enum PayType {
   BASIC(1,"基础工资"),
    ACHIEVE(2,"绩效工资"),
    BONUS(3,"奖金"),
    TOTAL(0,"总额");
    private Integer type;
    private String name;

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

    PayType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static PayType getPayTypeByType(Integer type){
        for(PayType payType :PayType.values()){
            if(payType.getType() == type){
                return  payType;
            }
        }
        return BASIC;
    }

    public static PayType getPayTypeByName(String name){
        for(PayType payType :PayType.values()){
            if(payType.getName().equals(name)){
                return  payType;
            }
        }
        return BASIC;
    }
}
