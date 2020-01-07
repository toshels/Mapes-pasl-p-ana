



package dirhiderjava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Scanner;

public class dirhider {

	// Funkcija, kura pārbaude vai parole failā "parole.txt" sakrīt ar lietotāja
	// ievadīto
	static boolean parolesparbaude(String f) {
		File fails = new File(f);
		Scanner scparole = new Scanner(System.in);
		Scanner sc = null;

		try {
			sc = new Scanner(fails);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Ievadi paroli.");
		String lietotajaparole = scparole.nextLine();
		String parole = sc.nextLine();

		// Todo šeit nāks hash

		if (lietotajaparole.equals(parole)) {
			sc.close();
			System.out.println("Parole pareiza!");
			return true;
		} else {
			System.out.println("Nepareiza parole!");
			sc.close();
			return false;
		}


	}



	// Funkcija, kura atgriež "true" ja lietotājs ievada "y" vai "Y", ja "n", "N"
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
		File f = null, parole = null;
		;


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
				parole = new File(f, "parole.txt");

				if (!f.exists()) {
					System.out.println("Mape ar tādu nosaukumu nepastāv, vai vēlies izveidot jaunu ? y/n");

					if (jautajums()) {
						f.mkdir();
						try {
							parole.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("Mape ir izveidota.");
						String parole1, parole2;


						// Izveido text failu, kurš tiek uzreiz paslēpts, tiek pievienota kriptēta
						// parole un hash.
						// TODO
						// pievienot paroles hashošanu, pievienot paroles pārbaudi pirms aizslēgt,
						// parādīt mapi
						while (true) {
							System.out.println("Ievadi paroli.");
							parole1 = sc.nextLine();
							System.out.println("Atrārto paroli");
							parole2 = sc.nextLine();


							if (parole1.equals(parole2)) {
								System.out.println("Paroles sakrīt.");


								try {
									PrintWriter wr = new PrintWriter(parole, "UTF-8");
									wr.println(parole1);
									wr.close();
									Path pa = Paths.get(parole.toString());


									try {
										DosFileAttributes dos = Files.readAttributes(pa, DosFileAttributes.class);
										Files.setAttribute(pa, "dos:hidden", true);
										break;
										
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								} catch (FileNotFoundException | UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							} else {
								System.out.println("Paroles nesakrīt, mēģini velreiz");
							}
							
						}
					}
				} else {
					break;
				}
				if(f.exists()) {
					break;
				}
			}


			// Pārbauda un savāc informāciju par mapes pašreizējo stāvokli (aizslēgta vai nē)
			Path p = Paths.get(path + vards);
			DosFileAttributes dos = null;


			try {
				dos = Files.readAttributes(p, DosFileAttributes.class);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			System.out.println("Pašreiz tava mape ir " + (dos.isHidden() ? "aizslēgta" : "neaizslēgta"));


			// Jautā lietotājam, vai vēlas aizslēgt mapi, vai nē
			System.out.println("Vai vēlies to " + (dos.isHidden() ? "atslēgt" : "aizslēgt") + " ? y/n");

			if (jautajums()) {


				if (parolesparbaude((path + "\\" + vards + "\\" + "parole.txt"))) {

					try {
						Files.setAttribute(p, "dos:hidden", (dos.isHidden() ? false : true));
						System.out.println("Darbība izdarīta");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			System.out.println("Vai vēlies turpināt darbu? y/n");

			if (jautajums()) {

			}
		}
	}
}
