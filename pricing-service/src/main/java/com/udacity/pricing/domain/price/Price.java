package com.udacity.pricing.domain.price;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity(name = "Prices")
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "currency")
    private String currency;
	
	@Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "vehicleid")
    private Long vehicleId;

    public Price() {
    }


	public Price(String currency, BigDecimal price, Long vehicleId) {
        this.currency = currency;
        this.price = price;
        this.vehicleId = vehicleId;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

	@Override
	public String toString() {
		return "Price [Id=" + id + ", currency=" + currency + ", price=" + price + ", vehicleId=" + vehicleId + "]";
	}
    
}
