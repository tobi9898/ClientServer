public class AuthentifizierungMessage extends AbstractMessage
{
    boolean authentifiziert = true;

    public boolean getAuthentifiziert() { return this.authentifiziert; }

    public void setAuthentifiziert(boolean new_authentifiziert) { this.authentifiziert = new_authentifiziert; }
}