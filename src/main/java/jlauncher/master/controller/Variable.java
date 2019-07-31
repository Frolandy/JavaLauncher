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

    String getVariableName(){
        return variableName;
    }

    String getValue(){
        return value;
    }

    String getId(){
        return id;
    }
}
