package ai.naturali.sdksample.fan;

/**
 * 姓名：McoWu
 * 时间:2017/12/22 16:35.
 * 本类作用:
 */

public class Info {
    private String id;
    private String name;
    private String Bpic;
    private String Apic;

    public Info() {
    }

    public Info(String id, String name, String bpic, String apic) {
        this.id = id;
        this.name = name;
        Bpic = bpic;
        Apic = apic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBpic() {
        return Bpic;
    }

    public void setBpic(String bpic) {
        Bpic = bpic;
    }

    public String getApic() {
        return Apic;
    }

    public void setApic(String apic) {
        Apic = apic;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Bpic='" + Bpic + '\'' +
                ", Apic='" + Apic + '\'' +
                '}';
    }
}
