package sample.principal;

public class SamplePrincipal implements java.security.Principal, java.io.Serializable {

    private String name;

    public SamplePrincipal(String name) {
        if (name == null)
            throw new NullPointerException("illegal null input");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return("SamplePrincipal:  " + name);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SamplePrincipal))
            return false;
        SamplePrincipal that = (SamplePrincipal)o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}

