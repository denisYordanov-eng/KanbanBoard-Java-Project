package teistermask.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    // TODO: Implement me...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("Open") || status.equals("In Progress") ||
                status.equals("Finished")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid Status " + status);
        }
    }
}
