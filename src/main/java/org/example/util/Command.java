package org.example.util;

public abstract class Command {
    public abstract boolean isRestricted();
    public abstract String exec(String args) throws Throwable;
    public String use() { return ""; }
    public String desc() { return ""; }
}
