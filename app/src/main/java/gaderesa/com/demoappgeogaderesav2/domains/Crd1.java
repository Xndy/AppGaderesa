package gaderesa.com.demoappgeogaderesav2.domains;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Xndy
*/
public class Crd1 implements Serializable {

	private static final long serialVersionUID = 1L;
	private String address;
	private String cardCode;
	private String street;
	private String city;
	private String county;
	private String country;
	private Character adresType;
	private String glblLocNum;
	private String uGadEmaelfact;
	private transient Date uGadDatecrea;
	private String uGadUpgpsandroid;
    
    public Crd1() {
    }


	public Crd1(String address, String cardCode, String street, String city, String county, String country, Character adresType, String glblLocNum, String uGadEmaelfact, Date uGadDatecrea) {
		this.address = address;
		this.cardCode = cardCode;
		this.street = street;
		this.city = city;
		this.county = county;
		this.country = country;
		this.adresType = adresType;
		this.glblLocNum = glblLocNum;
		this.uGadEmaelfact = uGadEmaelfact;
		this.uGadDatecrea = uGadDatecrea;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Character getAdresType() {
		return adresType;
	}

	public void setAdresType(Character adresType) {
		this.adresType = adresType;
	}

	public String getGlblLocNum() {
		return glblLocNum;
	}

	public void setGlblLocNum(String glblLocNum) {
		this.glblLocNum = glblLocNum;
	}

	public String getuGadEmaelfact() {
		return uGadEmaelfact;
	}

	public void setuGadEmaelfact(String uGadEmaelfact) {
		this.uGadEmaelfact = uGadEmaelfact;
	}

	public Date getuGadDatecrea() {
		return uGadDatecrea;
	}

	public void setuGadDatecrea(Date uGadDatecrea) {
		this.uGadDatecrea = uGadDatecrea;
	}

	public String getuGadUpgpsandroid() {
		return uGadUpgpsandroid;
	}

	public void setuGadUpgpsandroid(String uGadUpgpsandroid) {
		this.uGadUpgpsandroid = uGadUpgpsandroid;
	}

	@Override
	public String toString() {
		return "Crd1{" +
				"address='" + address + '\'' +
				", cardCode='" + cardCode + '\'' +
				", street='" + street + '\'' +
				", city='" + city + '\'' +
				", county='" + county + '\'' +
				", country='" + country + '\'' +
				", adresType=" + adresType +
				", glblLocNum='" + glblLocNum + '\'' +
				", uGadEmaelfact='" + uGadEmaelfact + '\'' +
				", uGadDatecrea=" + uGadDatecrea +
				'}';
	}
}
