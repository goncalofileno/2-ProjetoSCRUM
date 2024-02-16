package aor.paj.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.format.DateTimeParseException;

import java.time.LocalDate;

@XmlRootElement
public class TaskDto {

    private int id;
    private String title;
    private String description;

    private LocalDate initialDate;
    private LocalDate finalDate;
    private Integer status;
    private Integer priority;

    public TaskDto() {
        System.out.println("Construtor chamado sem parâmetros");
    }

    public TaskDto(String title, String description, LocalDate initialDate, LocalDate finalDate, int priority) {
        System.out.println("Construtor chamado com parâmetros");
        this.title = title;
        this.description = description;
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
            // If the string is not in the correct format, set initialDate to null
            this.initialDate = null;
        }
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    @XmlElement
    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        try {
            this.finalDate = LocalDate.parse(finalDate);
        } catch (DateTimeParseException e) {
            // If the string is not in the correct format, set initialDate to null
            this.finalDate = null;
        }
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    @XmlElement
    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {

        if (status == null) {
            this.status = 0; // or any default value
        } else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        try {
            this.status = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            this.status = 0; // or any default value
        }
    }

    @XmlElement
    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        System.out.println("Setter chamado com parâmetros Integer");
        if (priority == null) {
            this.priority = 0; // or any default value
        } else {
            this.priority = priority;
        }
    }

    public void setPriority(String priority) {
        System.out.println("Setter chamado com parâmetros String");
        try {
            this.priority = Integer.parseInt(priority);
        } catch (NumberFormatException e) {
            this.priority = 0; // or any default value
        }
    }
    // other fields and methods...


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

