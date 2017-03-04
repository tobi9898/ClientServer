public class Message extends AbstractMessage
{
    float random;
    long date;

    public long getDate() { return this.date; }

    public void setDate(long new_date)  { this.date = new_date; }

    public float getRandom() { return this.random; }

    public void setRandom(float new_random)  { this.random = new_random; }
}