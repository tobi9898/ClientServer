import java.io.Serializable;

public abstract class AbstactMessage implements Serializable
{
    String URL, name;

    public String getURL() { return URL; }

    public void setURL(String new_URL) { this.URL = new_URL; }

    public void setName(String new_name) { this.name = new_name; }

    public String getName() { return this.name; }
}