package services;

import java.util.List;

import models.*;

public class PrintService {

	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}

	public static void printInfoCustomer(Customer customer) {
		String member;
		double saldo;
		if(customer instanceof MemberCustomer) {
			member = "Member";
			saldo = ((MemberCustomer) customer).getSaldoCoin();
		} else {
			member = "Non-Member";
			saldo = 0;
		}
		
		String line = "+-------------------------------------------------------------------------------------------------+%n";
		System.out.format(line);
		System.out.printf("| %-95s |%n", "Customer Profile");
		System.out.format(line);
		System.out.printf("| %-95s |%n", "Customer ID     : " + customer.getCustomerId());
		System.out.printf("| %-95s |%n", "Nama            : " + customer.getName());
		System.out.printf("| %-95s |%n", "Customer Status : " + member);
		System.out.printf("| %-95s |%n", "Alamat          : " + customer.getAddress());
		if(customer instanceof MemberCustomer) {
			System.out.printf("| %-95s |%n", "Saldo Koin      : " + saldo);
		}
		printVechicle(customer.getVehicles());
	}
	
	public static void printService(List<ItemService> service, Vehicle listVehicle) {
		String formatTable = "| %-2s | %-15s | %-15s | %-15s | %-10s |%n";
		String line = "+----+-----------------+-----------------+-----------------+------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (ItemService s : service) {
	    	if(listVehicle instanceof Car) {
	    		vehicleType = "Car";
	    	} else if(listVehicle instanceof Motorcyle) {
	    		vehicleType = "Motorcyle";
	    	}
	    	if(s.getVehicleType().equalsIgnoreCase(vehicleType)) {
	    		System.out.format(formatTable, number++, s.getServiceId(), s.getServiceName(), vehicleType, s.getPrice());
	    	}
//	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static String printServices(List<ItemService> serviceList){
        String result = "";
        for (ItemService service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }
	
	public static void printBooking(List<BookingOrder> booking) {
		String formatTable = "| %-2s | %-20s | %-15s | %-15s | %-15s | %-15s | %-30s |%n";
		String line = "+----+----------------------+-----------------+-----------------+-----------------+-----------------+--------------------------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "Payment Method", "Total Service", "Total Payment", "List Service");
	    System.out.format(line);
	    int number = 1;
	    for (BookingOrder b : booking) {
	    	System.out.format(formatTable, number, b.getBookingId(), b.getCustomer().getName(), b.getPaymentMethod(), b.getTotalServicePrice(), b.getTotalPayment(), printServices(b.getServices()));
	    	number++;
	    }
	    System.out.printf(line);
	}
	
}
