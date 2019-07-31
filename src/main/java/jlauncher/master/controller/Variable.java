package jlauncher.master.controller;

public class Variable {
    private String variableName;
    private String value;
    private String id;

    public Variable(String variableName, String value, String id){
        this.variableName = variableName;
        this.value = value;
        this.id = id;
    }

    public String getVariableName(){
        return variableName;
    }

    public String getValue(){
        return value;
    }

    public String getId(){
        return id;
    }
}
