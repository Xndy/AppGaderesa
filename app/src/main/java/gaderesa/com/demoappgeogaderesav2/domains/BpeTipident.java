/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaderesa.com.demoappgeogaderesav2.domains;


import java.io.Serializable;

/**
 *
 * @author Xndy
 */

public class BpeTipident implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private String uSypCodcom;
    private String uSypCodvta;
    private String uSypCodtjc;
    private String uSypCodrfin;
    private String uSypCodffi;

    public BpeTipident() {
    }

    public BpeTipident(String code) {
        this.code = code;
    }

    public BpeTipident(String code, String name) {
        this.code = code;
        this.name = name;
    }

    



    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getuSypCodcom() {
		return uSypCodcom;
	}

	public void setuSypCodcom(String uSypCodcom) {
		this.uSypCodcom = uSypCodcom;
	}

	public String getuSypCodvta() {
		return uSypCodvta;
	}

	public void setuSypCodvta(String uSypCodvta) {
		this.uSypCodvta = uSypCodvta;
	}

	public String getuSypCodtjc() {
		return uSypCodtjc;
	}

	public void setuSypCodtjc(String uSypCodtjc) {
		this.uSypCodtjc = uSypCodtjc;
	}

	public String getuSypCodrfin() {
		return uSypCodrfin;
	}

	public void setuSypCodrfin(String uSypCodrfin) {
		this.uSypCodrfin = uSypCodrfin;
	}

	public String getuSypCodffi() {
		return uSypCodffi;
	}

	public void setuSypCodffi(String uSypCodffi) {
		this.uSypCodffi = uSypCodffi;
	}

	@Override
    public String toString() {
        return "demoappgadere.BpeTipident[ code=" + code + " ]";
    }
    
}
