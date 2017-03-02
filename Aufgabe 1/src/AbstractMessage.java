import java.io.Serializable;

public abstract class AbstractMessage implements Serializable
{
    // Wird verwendet um die Eingabe des Clients an den Server zu senden

    int zahl1, zahl2;
    String operation;

    public String getOperation() { return this.operation; }

    public void setOperation(String operation_new) { this.operation = operation_new; }

    public int getZahl2() { return this.zahl2; }

    public void setZahl2(int zahl2_new) { this.zahl2 = zahl2_new; }

    public int getZahl1() { return this.zahl1; }

    public void setZahl1(int zahl1_new) { this.zahl1 = zahl1_new; }
}