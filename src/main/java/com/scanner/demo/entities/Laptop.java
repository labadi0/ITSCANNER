package com.scanner.demo.entities;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Slf4j

public class Laptop {
	private String title;
	private String source;
	private String uri;
	private String name;
	private String reference;
	private String imageUri;
	private String screenSize;
	private String screenResolution;
	private String cpu;
	private String gpu;
	private String ram;
	private String storage;
	private String operatingSystem;
	private String weight;
	private String price;
	
	

	public Laptop() {
		
		// TODO Auto-generated constructor stub
	}

	public Laptop(String title, String source, String uri, String name, String reference, String imageUri,
			String screenSize, String screenResolution, String cpu, String gpu, String ram, String storage,
			String operatingSystem, String weight, String price) {
		super();
		this.title = title;
		this.source = source;
		this.uri = uri;
		this.name = name;
		this.reference = reference;
		this.imageUri = imageUri;
		this.screenSize = screenSize;
		this.screenResolution = screenResolution;
		this.cpu = cpu;
		this.gpu = gpu;
		this.ram = ram;
		this.storage = storage;
		this.operatingSystem = operatingSystem;
		this.weight = weight;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getGpu() {
		return gpu;
	}

	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public static org.slf4j.Logger getLog() {
		return log;
	}

	public boolean checkNotNull(Laptop laptop) throws IllegalAccessException {
		if ((laptop.getTitle() != null) || (laptop.getSource() != null) || (laptop.getUri() != null)
				|| (laptop.getName() != null) || (laptop.getReference() != null) || (laptop.getImageUri() != null)
				|| (laptop.getScreenSize() != null) || (laptop.getScreenResolution() != null)
				|| (laptop.getCpu() != null) || (laptop.getGpu() != null) || (laptop.getRam() != null)
				|| (laptop.getStorage() != null) || (laptop.getOperatingSystem() != null)
				|| (laptop.getWeight() != null) || (laptop.getPrice() != null)) {

			return true;

		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return source + ";" + uri + ";" + name + ";" + reference + ";" + imageUri + ";" + screenSize + ";"
				+ screenResolution + ";" + cpu + ";" + gpu + ";" + ram + ";" + storage + ";" + operatingSystem + ";"
				+ weight + ";" + price + ";" + title;

		// Laptop [source=Darty,
		// uri=https://www.darty.com/nav/achat/informatique/macbook_imac_ipad/macbook/apple_newmba13_m1_8_256_gs.html,
		// name=Apple MacBook Air 13'' 256 Go SSD 8 Go RAM Puce M1 Gris sid�ral Nouveau,
		// reference=4789075,
		// imageUri=https://image.darty.com/informatique/macbook_imac_ipad/macbook/apple_newmba13_m1_8_256_gs_s2011104789075A_221333728.jpg,
		// screenSize=13,3 ", screenResolution=2 560 x 1 600 pixels, cpu=Nombre de c�ur
		// : 8; Apple M1, gpu=Apple M1 7-core, ram=8 Go, storage= ssd 256 Go,
		// operatingSystem=MacOS, weight=1,29 kg, price=1 029,99 �]

	}

}
