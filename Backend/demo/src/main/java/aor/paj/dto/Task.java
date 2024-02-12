package aor.paj.dto;

import jakarta.inject.Inject;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import aor.paj.bean.TaskBean;
import java.time.format.DateTimeParseException;

import java.time.LocalDate;
import java.util.Date;

@XmlRootElement
public class Task {

    private int id;
    private String title;
    private String description;

    private LocalDate initialDate;
    private LocalDate finalDate;
    private Integer status;
    private Integer priority;

    public Task() {

    }

    public Task(String title, String description, LocalDate initialDate, LocalDate finalDate, int priority) {
        System.out.println("Construtor chamado com parâmetros");
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

    public void setInitialDate(String initialDate) {
        try {
            this.initialDate = LocalDate.parse(initialDate);
        } catch (DateTimeParseException e) {
            // Handle the parsing exception
            // For example, set a default value or throw a custom exception
            // For demonstration, setting initialDate to null
            this.initialDate = null;
        }
    }

    @XmlElement
    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        try {
            this.finalDate = LocalDate.parse(finalDate);
        } catch (DateTimeParseException e) {
            this.finalDate = null;
        }
    }

    @XmlElement
    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        try {
            // Attempt to parse the priority string to an integer
            this.priority = Integer.parseInt(priority);
        } catch (Exception e) {
            // Handle the error appropriately
            // For example, you can log the error and set a default priority value
            System.err.println("Error parsing priority: " + e.getMessage());
            this.priority = 0; // Set a default priority value or handle the error in another way
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", status=" + status +
                ", priority=" + priority +
                '}';
    }
}
