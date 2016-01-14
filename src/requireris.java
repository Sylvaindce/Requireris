import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class requireris {
	private static int index;

	public static void main(String[] args) {
		System.out.print("Launch Requireris");
		index = 0;
		try {
			Window my_act = new Window();
			System.out.print(my_act);
		} catch (Exception exc) {
			System.out.print("Cannot create object Window my_act");
		}
	}

	public static void set_index(int i) {
		index = i;
	}

	public static int get_index() {
		return index;
	}

	public static long get_cTime() {
		long time = new Date().getTime() / TimeUnit.SECONDS.toMillis(30);
		return time;
	}

	public static int calculateCode(byte[] key, long tm) throws NoSuchAlgorithmException, InvalidKeyException {
		// Allocation d'un ByteBuffer pour time
		ByteBuffer time_tmp = ByteBuffer.allocate(8);
		
		// Convertion du temps(long -> byteBuffer)
		time_tmp.putLong(tm);
		
		// Convertion du temps vers un unsigned int Ref. RFC4226 
		byte[] time = time_tmp.array();
		
		// Creation de la clé spécifique pour l'algorithme HmacSHA1
		SecretKeySpec hmac_key = new SecretKeySpec(key, "HmacSHA1");

		// Initialisation de l'algorithme HMACSHA1 
		Mac mac = Mac.getInstance("HmacSHA1");

		// Initialisation de l'instance avec la clé d'algo
		mac.init(hmac_key);

		//Finalisation de l'instance avec la variable temps/ recuperation de la donnée chiffrée
		byte[] hash = mac.doFinal(time);

		//System.out.print(hash);
		//System.out.print(" :hash ");
		// Generation d'une chaine de 4octets (dynamic troncature) ref RFC4226
		int offset = hash[hash.length - 1] & 0xF;

		//System.out.print(offset);
		//System.out.print(" :offset ");
		// Utilisation d'un long en remplacement d'un unsigned int(pas de type unsigned en java)
		// et nous avons besoin de 32bits
		long result = 0;

		// Extraction des 4bits de poids faible
		for (int i = 0; i < 4; ++i) {
			result <<= 8;
			result |= (hash[offset + i] & 0xFF);
		}

		//System.out.print(result);
		//System.out.print(" :result ");
		// Creation du code à 6 chiffres
		int modulo = (int) Math.pow(10, 6);
		result %= modulo;
		return (int) result;
	}

	public static String readFile() throws IOException {
		//jar
		//String content = new String(Files.readAllBytes(Paths.get(requireris.class.getResource("save.txt").getFile())));
		//java
		String content = new String(Files.readAllBytes(Paths.get("./src/save.txt")));
		return content;
	}
}
