package aor.paj.dto;

import jakarta.inject.Inject;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import aor.paj.bean.TaskBean;

import java.util.Date;

@XmlRootElement
public class Task {

    @Inject
    TaskBean TaskBean;

    int id;
    String title;
    String description;
    Date initialDate;
    Date finalDate;
    int status;
    int priority;
    int userId;

    public Task() {
    }

    public Task(int i, String t, String d, Date id, Date fd, int s, int p, int u) {
        this.id = 1;
        this.title = t;
        this.description = d;
        this.initialDate = id;
        this.finalDate = fd;
        this.status = s;
        this.priority = p;
        this.userId = u;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    @XmlElement
    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    @XmlElement
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @XmlElement
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
