package ai.naturali.sdksample.fan;

/**
 * 姓名：McoWu
 * 时间:2017/12/27 09:48.
 * 本类作用:
 */

public class SubsidyEntity {
    private int id;
    private String name;
    private String Bpic;
    private String Apic;

    public SubsidyEntity(int id, String name, String bpic, String apic) {
        this.id = id;
        this.name = name;
        Bpic = bpic;
        Apic = apic;
    }

    public SubsidyEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "SubsidyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Bpic='" + Bpic + '\'' +
                ", Apic='" + Apic + '\'' +
                '}';
    }
}
