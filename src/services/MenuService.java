package services;

import java.util.List;
import java.util.Scanner;

import models.*;
import repositories.*;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static Scanner input = new Scanner(System.in);
	private static Customer customer;

	public static boolean run() {
		boolean isLooping = true;
		do {
			login();
			mainMenu();
		} while (isLooping);
		return true;
		
	}
	
	public static void login() {
		String id;
		String pass;
		int countloopid = 0;
		int countlooppw = 0;
		boolean loopid = true;
		boolean looppw = true;
		System.out.println("+---------------------------------+");
		System.out.println("Login");
		System.out.println("+---------------------------------+");
		
		while(loopid) {
			
			id = Validation.validasiInput("Masukan Customer ID       : ", "ID tidak sesuai!", "Cust-\\d+");
			Customer cus = BengkelService.id(id, listAllCustomers);
			
			if(cus != null) {
				while(looppw) {					
					pass = Validation.validasiInput("Masukan Customer Password : ", "Password tidak dikenali!", "cust\\d+");
					Customer pw = BengkelService.pass(cus, pass, listAllCustomers);
					
					if(pw != null) {
						customer = pw;
						looppw = false;
						loopid = false;
					} else {
						System.out.println("Password yang anda Masukan Salah!");
						countlooppw += 1;
						System.out.printf("Percobaan memasukan password ke-"+countlooppw+". Percobaan ke-3 akan exit otomatis\n");
						if(countlooppw == 3) {
							System.out.println("Aplikasi diberhentikan");
							System.exit(0);
							break;
						}
					}
				}
			} else {
				System.out.println("Customer Id Tidak Ditemukan atau Salah");
				countloopid += 1;
				System.out.printf("Percobaan memasukan id ke-"+countloopid+". Percobaan ke-3 akan exit otomatis\n");
			}
			
			if(countloopid == 3) {
				System.out.println("Aplikasi diberhentikan");
				System.exit(0);
				break;
			}
		}
		
	}
	
	public static void mainMenu() {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			
			switch (menuChoice) {
			case 1:
				PrintService.printInfoCustomer(customer);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				BengkelService.booking(input, customer, listAllItemService);
				break;
			case 3:
				BengkelService.topup(input, customer);
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				PrintService.printBooking(BengkelService.bo);
				break;
			default:
				BengkelService.logout();
				isLooping = false;
				break;
			}
		} while (isLooping);
		
		
	}
	
}
