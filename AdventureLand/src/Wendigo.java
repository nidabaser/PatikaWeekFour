/**
 * @author Nida Başer
 * March 2024
 */
import java.util.Random;
public class Wendigo extends Enemy{
    private static Random rand = new Random();
    public Wendigo() {
        // YENİ BÖLGE DÜŞMANININ HASARI RASTGELE AYARLANMASI
        super(4, "Wendigo", rand.nextInt(3,7), 12, 0);
    }
}
