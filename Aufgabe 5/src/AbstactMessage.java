import java.io.Serializable;

public abstract class AbstactMessage implements Serializable
{
    String Pfad;

    public String getPfad() { return Pfad; }

    public void setPfad(String new_Pfad) { Pfad = new_Pfad; }
}