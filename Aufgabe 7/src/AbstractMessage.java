import java.io.Serializable;

public abstract class AbstractMessage implements Serializable
{
    String befehl;

    public String getBefehl() { return this.befehl; }

    public void setBefehl(String new_befehl) { this.befehl = new_befehl; }
}