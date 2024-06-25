package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.*;
import main.*;

public class BengkelService {
	public static List<BookingOrder> bo = new ArrayList<>();
	
	//Login
	public static Customer id(String id, List<Customer> customer) {
		for(Customer c: customer) {
			if(c.getCustomerId().equalsIgnoreCase(id)) {
				return c;
			}
		}
		return null;
	}
	
	//Login
	public static Customer pass(Customer id, String pass, List<Customer> customer) {
		for(Customer c: customer) {
			if(c.equals(id)) {				
				if(c.getPassword().equalsIgnoreCase(pass)) {
					return c;
				}
			}
		}
		return null;
	}
		
	//Vehicle
	public static Vehicle vehicle(String id, List<Vehicle> vehicle) {
		for(Vehicle v: vehicle) {
			if(v.getVehiclesId().equalsIgnoreCase(id)) {
				return v;
			}
		}
		return null;
	}
	
	//Service
	public static ItemService itemservice(String id, List<ItemService> is) {
		for(ItemService c: is) {
			if(c.getServiceId().equalsIgnoreCase(id)) {
				return c;
			}
		}
		return null;
	}
	
	//Booking atau Reservation
	public static void booking(Scanner input, Customer customer, List<ItemService> is) {
		String ser = null;
		String pilihan = null;
		int looping = 0;
		boolean loop = true;
		double saldo = 0;
		double total = 0;
		ItemService items1;
		List<ItemService> items = new ArrayList<>();
		
		while(loop) {
			System.out.print("Masukan Vehicle ID anda : ");
			String id = input.nextLine();
			
			Vehicle vehicle = vehicle(id, customer.getVehicles());
			
			if(vehicle != null) {
				PrintService.printService(is, vehicle);
				
				while(true) {
					if(vehicle instanceof Car) {
						ser = Validation.validasiInput("Masukan Service ID       : ", "ID tidak sesuai!", "Svcc-\\d+");
					} else if(vehicle instanceof Motorcyle) {					
						ser = Validation.validasiInput("Masukan Service ID       : ", "ID tidak sesuai!", "Svcm-\\d+");
					} 
					
					items1 = itemservice(ser, is);
					
					if(items1 != null) {
						items.add(items1);
						looping += 1;
						
						if(customer instanceof MemberCustomer) {

							while(true) {
								System.out.println("Apakah anda ingin menambahkan Service Lainnya? (Y/T)");
								String pilih = input.nextLine();
								if(looping == 2) {
									if(pilih.equalsIgnoreCase("Y")) {
										System.out.println("Jumlah max service terpenuhi, silahkan pilih T untuk melanjutkan tahap pembayaran!");
									}
								} else {
									if(pilih.equalsIgnoreCase("Y")) {
										if(vehicle instanceof Car) {
											ser = Validation.validasiInput("Masukan Service ID       : ", "ID tidak sesuai!", "Svcc-\\d+");
										} else if(vehicle instanceof Motorcyle) {					
											ser = Validation.validasiInput("Masukan Service ID       : ", "ID tidak sesuai!", "Svcm-\\d+");
										} 
										items1 = itemservice(ser, is);
										
										if(items1 != null) {
											items.add(items1);
											looping += 1;
										} else {
											System.out.println("ID Service tidak ditemukan!");
										}
									}
								}
								if(pilih.equalsIgnoreCase("T")) {
									System.out.println("Silahkan Pilih Metode Pembayaran (Saldo Coin atau Cash)");
									pilihan = input.nextLine();
									break;
								} 
							}
							
							
							if(pilihan.equalsIgnoreCase("Saldo Coin")) {
								for(ItemService i: items) {
									saldo += i.getPrice();
								}
								total = saldo - (saldo * 0.1);
								
								double saldocoin = ((MemberCustomer) customer).getSaldoCoin();
								((MemberCustomer) customer).setSaldoCoin(saldocoin-total);
								
								System.out.println("Booking Berhasil!!");
								System.out.printf("Total Harga Service : " + saldo + "\n");
								System.out.println("Total Harga Payment : " + total + "\n");
								
								bo(customer, items, "Cash", saldo, total);
								
								loop = false;
								break;
							} else if(pilihan.equalsIgnoreCase("Cash")) {
								for(ItemService i: items) {
									saldo += i.getPrice();
								}
								
								System.out.println("Booking Berhasil!!");
								System.out.printf("Total Harga Service : " + saldo + "\n");
								System.out.println("Total Harga Payment : " + saldo + "\n");
								
								bo(customer, items, "Cash", saldo, saldo);
								
								loop = false;
								break;
							} else {
								System.out.println("Input salah, masukan input yang benar");
							}
								
						} else {
							bo(customer, items, "Cash", items1.getPrice(), items1.getPrice());
							System.out.println("Booking Berhasil!!");
							System.out.printf("Total Harga Service : " + items1.getPrice() + "\n");
							System.out.println("Total Harga Payment : " + items1.getPrice() + "\n");
							loop = false;
							break;
						}
						
						break;
					} else {
						System.out.println("ID service tidak ditemukan!");
					}
				}
				
			} else {
				System.out.println("ID kendaraan tidak ditemukan!");
			}
		}
	}
	
	public static void bo(Customer customer, List<ItemService> items, String method, double price, double total) {
		String id = null;;
		if(bo.size() <= 8) {
			id = "Book-Cust-00"+ (bo.size()+1) + "-00" + customer.getCustomerId().charAt(7);
		} else if(bo.size() < 10) {
			id = "Book-Cust-0"+ (bo.size()+1) + "-00" + customer.getCustomerId().charAt(7);
		} else if(bo.size() < 100) {
			id = "Book-Cust-"+ (bo.size()+1) + "-00" + customer.getCustomerId().charAt(7);
		}
				
		BookingOrder bos = BookingOrder.builder()
				.bookingId(id)
				.customer(customer)
				.services(items)
				.paymentMethod(method)
				.totalServicePrice(price)
				.totalPayment(total)
				.build();
		
		bo.add(bos);
	}
	
	//Top Up Saldo Coin Untuk Member Customer
	public static void topup(Scanner input, Customer customer) {
		int tu;
		double saldo;
		boolean loop = true;
		
		if(customer instanceof MemberCustomer) {
			String line = "+----------------------------------------------+%n";
			System.out.format(line);
			System.out.printf("| %-46s |%n", "Top Up Saldo Coin");
			System.out.format(line);
			
			while(loop) {
				tu = Validation.validasiNumberWithRange("Masukan besaran Top Up : ", "Input harus berupa angka !", "^[0-9]+$", 1000000000, 0);
				
				if(tu != 0) {				
					saldo = tu + ((MemberCustomer) customer).getSaldoCoin();
					((MemberCustomer) customer).setSaldoCoin(saldo);
					System.out.println("Berhasil Top Up!");
					loop = false;
				}
			}
		} else {
			System.out.println("Maaf fitur ini hanya untuk Member saja!");
		}
	}
	
	//Logout
	public static void logout() {
        System.out.println("Anda telah logout.");
        App.main(null);
    }
	
}
