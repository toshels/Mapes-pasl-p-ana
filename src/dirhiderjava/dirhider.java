package dirhiderjava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Scanner;

public class dirhider {

	// Funkcija kura atgriež "true" ja lietotājs ievada "y" vai "Y", ja "n", "N"
	// apstādina programmu
	// un pie nepareizas ievades prasa ievadi kamēr tā ir pareiza.
	static boolean jautajums() {
		Scanner sc = new Scanner(System.in);
		while (true) {

			String atbilde = sc.nextLine();

			if (atbilde.equals("y") || atbilde.equals("Y")) {
				return true;
			} else if (atbilde.equals("n") || atbilde.equals("N")) {

				System.out.println("...");
				System.exit(0);
			} else {
				System.out.println("Nepareiza ievade, mēģini velreiz.");
			}
		}
	}





	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		File f = null;


		// while loop, lai programma pēc beigām neapstātos.
		while (true) {
			System.out.println("Kā tevi sauc?");
			String vards = null;
			String path = "C:\\Users\\tomsl\\Desktop\\";



			// while loop, kurš pārliecinās, lai mape, ar tādu vārdu pastāv,
			// lietotājs netiek tālāk, ja mape nepastāv.
			while (true) {
				vards = sc.nextLine();
				f = new File(path + vards);


				if (!f.exists()) {
					System.out.println("Mape ar tādu nosaukumu nepastāv, vai vēlies izveidot jaunu ? y/n");

					if (jautajums()) {
						f.mkdir();
						System.out.println("Mape ir izveidota.");
						break;
					}
				} else {
					break;
				}
			}


			// Pārbauda un savāc informāciju par mapes pašreizējo stāvokli (paslēpta vai nē)
			Path p = Paths.get(path + vards);
			DosFileAttributes dos = null;
			try {
				dos = Files.readAttributes(p, DosFileAttributes.class);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Pašreiz tava mape ir " + (dos.isHidden() ? "paslēpta" : "nepaslēpta"));


			// Jautā lietotājam, vai vēlas paslēpt mapi, vai nē
			System.out.println("Vai vēlies to " + (dos.isHidden() ? "atslēpt" : "paslēpt") + " ? y/n");
			if (jautajums()) {
				try {
					Files.setAttribute(p, "dos:hidden", (dos.isHidden() ? false : true));
					System.out.println("Darbība izdarīta");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Vai vēlies turpināt darbu? y/n");
			if (jautajums()) {

			}
		}
	}
}
