package aor.paj.dto;

import jakarta.inject.Inject;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import aor.paj.bean.TaskBean;

import java.time.LocalDate;
import java.util.Date;

@XmlRootElement
public class Task {

    int id;
    String title;
    String description;

    LocalDate initialDate;
    LocalDate finalDate;
    int status;
    int priority;

    public Task() {
    }

    public Task(String title, String description, LocalDate initialDate, LocalDate finalDate, int priority) {
        this.title = title;
        this.description= description;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.priority = priority;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    @XmlElement
    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
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
}
