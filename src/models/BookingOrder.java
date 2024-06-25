package models;

import java.util.Date;
import java.util.List;
import interfaces.IBengkelPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingOrder implements IBengkelPayment{
	private String bookingId;
	private Customer customer;
	private List<ItemService> services;
	private String paymentMethod;
	private double totalServicePrice;
	private double totalPayment;
	
	@Override
	public void calculatePayment() {
		double discount = 0;
		if (paymentMethod.equalsIgnoreCase("Saldo Coin")) {
			discount = getTotalServicePrice() * RATES_DISCOUNT_SALDO_COIN;
		}else {
			discount = getTotalServicePrice() * RATES_DISCOUNT_CASH;
		}
		
		setTotalPayment(getTotalServicePrice() - discount);
	}

	
}
