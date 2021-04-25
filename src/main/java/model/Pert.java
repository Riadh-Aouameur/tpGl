package model;



public class Pert {


    int id;
    String name;
    String tasks;

    public Pert() {

    }

    public Pert(int id, String name, String tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    public Pert(String name, String tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Pert{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks='" + tasks + '\'' +
                '}';
    }
}
