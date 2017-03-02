import java.io.Serializable;

public abstract class AbstractMessage implements Serializable
{
    int zahl1, zahl2, ergebnis;
    String operation = " ", absender;

    public String getOperation() { return this.operation; }

    public void setOperation(String operation_new) { this.operation = operation_new; }

    public int getZahl2() { return this.zahl2; }

    public void setZahl2(int zahl2_new) { this.zahl2 = zahl2_new; }

    public int getZahl1() { return this.zahl1; }

    public void setZahl1(int zahl1_new) { this.zahl1 = zahl1_new; }

    public int getErgebnis() { return this.ergebnis; }

    public void setAbsender(String new_absender) { this.absender = new_absender; }

    public String getAbsender() { return this.absender; }

    public void setErgebnis(int zahl1_new, String operation_new, int zahl2_new)
    {
        if (operation_new.equals("+")) { this.ergebnis = zahl1_new + zahl2_new; }
        if (operation_new.equals("-")) { this.ergebnis = zahl1_new - zahl2_new; }
        if (operation_new.equals("*")) { this.ergebnis = zahl1_new * zahl2_new; }
        if (operation_new.equals("/")) { this.ergebnis = zahl1_new / zahl2_new; }
    }
}