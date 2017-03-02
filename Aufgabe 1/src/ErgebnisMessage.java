public class ErgebnisMessage extends AbstractMessage
{
    // Wird fuer die Rueckgabe des Ergebnisses verwendet

    int ergebnis;

    public int getErgebnis() { return this.ergebnis; }

    public void setErgebnis(int zahl1_new, String operation_new, int zahl2_new)
    {
        if (operation_new.equals("+")) { this.ergebnis = zahl1_new + zahl2_new; }
        if (operation_new.equals("-")) { this.ergebnis = zahl1_new - zahl2_new; }
        if (operation_new.equals("*")) { this.ergebnis = zahl1_new * zahl2_new; }
        if (operation_new.equals("/")) { this.ergebnis = zahl1_new / zahl2_new; }
    }
}