package org.craft.Model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable,Cloneable, Comparable<Task> {
    private Integer id;
    private String title;
    private Integer priority;
    private boolean isDone;
    private Date createdOn;

    public Task () {
        setId(0);
        setTitle("Dummy task");
        setPriority(1);
        setDone(false);
        setCreatedOn(new Date());
    }

    public Task(String title, Integer priority){
        setId(0);
        setTitle(title);
        setPriority(priority);
        setDone(false);
        setCreatedOn(new Date());
    }

    @Override
    public int hashCode() {
        return title.hashCode() + id.hashCode();
    }

    //equal if identical
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Task){
            return getTitle().equals(((Task) obj).getTitle()) &&
                    getCreatedOn().equals(((Task) obj).getCreatedOn()) &&
                    getId().equals(((Task) obj).getId()) &&
                    getPriority().equals(((Task) obj).getPriority()) &&
                    isDone() == ((Task) obj).isDone();
        }
        return false;
    }

    @Override
    public String toString() {
        return getId() + " " + getTitle() + " [created on : " +
                getCreatedOn().toString().substring(0,7) + " | status : " +
                ((isDone)? "done]" : "not done]");
    }

    //perform a deep clone
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object taskClone = super.clone();
        if(taskClone instanceof Task) {
            Task task = (Task) taskClone;
            task.setId(getId());
            task.setTitle(new String(getTitle()));
            task.setPriority(Integer.valueOf(getPriority()));
            task.setDone(isDone());
            task.setCreatedOn(new Date(getCreatedOn().getTime()));
            return  task;
        }
        return taskClone;
    }

    //compare based on creation date, if equal compare title
    @Override
    public int compareTo(Task o) {
        if(getPriority().compareTo(o.getPriority()) == 0)
            return getCreatedOn().compareTo(o.getCreatedOn());
        else
            return getPriority().compareTo(o.getPriority());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
