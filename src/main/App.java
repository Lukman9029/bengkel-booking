package main;
import services.*;

public class App 
{
    public static void main( String[] args )
    {
		String[] listLogin = {"Login", "Exit"};
		int login = 0;
		boolean loop = true;
		while(loop) {
			PrintService.printMenu(listLogin, "Aplikasi Booking Bengkel");
			login = Validation.validasiNumberWithRange("Pilihan Menu:", "ID tidak ditemukan!", "^[0-9]+$", listLogin.length-1, 0);
			switch (login) {
			case 1:
				if(MenuService.run() == false) {
					System.out.println("Aplikasi diberhentikan");
					loop = false;
				}
				break;
			default:
				System.out.println("Exit");
				loop = false;
				break;
			}
		}
    	
    }
}
