package com.scanner.demo.scrapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scanner.demo.entities.Laptop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FnacScraper {
	
	public static void main(String[] args) throws InterruptedException, IllegalAccessException {

		/*ArrayList<Laptop> laptopsInfos = getAllLaptopsInfo();
		LaptopPersistence lp=new LaptopPersistence();
		lp.bulkInsertLaptop(laptopsInfos);
		*/
		/*for (Laptop laptop : laptopsInfos) {
			System.out.println("here");
			log.info(laptop.toString());
			System.out.println(laptop.toString());
			
		}*/
		//getLinksOfLaptops("https://www.cdiscount.com/informatique/ordinateurs-pc-portables/pc-portables/l-1070992.html#_his_");
		getAllLaptopsInfo();
		
	//	System.out.println(getInfolaptop("https://www.fnac.com/PC-Ultra-Portable-Asus-Zenbook-ZENBOOK-13-OLED-EVO-3-UX325EA-KG356R-13-3-Intel-Core-i7-32-Go-RAM-1-To-SSD-Gris/a16343987/w-4#int=S:Nos%20offres%20du%20moment|Ordinateurs%20portables|230080|16343987|BL1|L1"));
	}

	public static String getRandomUserAgent() {
		Random randomGenerator = new Random();
		ArrayList<String> userAgents = new ArrayList<>();
		userAgents.add("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:81.0) Gecko/20100101 Firefox/81.0");
		/*userAgents.add("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
		userAgents.add(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
		userAgents.add("Mozilla/5.0 (Android 7.1.1; Mobile; rv:93.0) Gecko/93.0 Firefox/93.0");
		userAgents.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0; MDDCJS)");
		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0");
		userAgents.add(
				"Mozilla/5.0 (Linux; Android 7.1.1; E6653) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.74 Mobile Safari/537.36");
		userAgents.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		userAgents.add(
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");*/
		int index = randomGenerator.nextInt(userAgents.size());
		String userAgent = userAgents.get(index);
		return userAgent;

	}

	public static String getRandomWebSite() {
		Random randomGenerator = new Random();
		ArrayList<String> webSite = new ArrayList<>();
		webSite.add("https://www.youtube.com");
		webSite.add("https://www.google.fr");
		webSite.add("https://www.google.co.ma");
		webSite.add("https://www.messenger.com");
		webSite.add("https://www.facebook.com");
		webSite.add("https://www.google.de");
		webSite.add("https://www.google.es");
		webSite.add("https://www.google.ca");
		webSite.add("https://www.google.uk");
		int index = randomGenerator.nextInt(webSite.size());
		String userAgent = webSite.get(index);
		return userAgent;

	}

	public static Document getLaptopHtml(String link) {
		Document doc = null;
		String agent = getRandomUserAgent();
		String referrer = getRandomWebSite();
		try {

			doc = Jsoup.connect(link).userAgent(agent).referrer(referrer).ignoreHttpErrors(true).ignoreContentType(true).timeout(10000).maxBodySize(0)
					.get();
			//System.out.println("document : "+doc);
		} catch (Exception e) {
//			log.error("problem to get html page problem in getlaptophtml function");
			e.printStackTrace();
		}

		return doc;

	}

	public static int getRandom(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;

	}
	
	public static ArrayList<String> getLinksOfLaptops(String lienTouspc) throws InterruptedException {

		ArrayList<String> links = new ArrayList<>();
		String laptopLink = "https://www.fnac.com";
		for (int i = 0; i < 50; ++i) {
			int rand1 = getRandom(2000, 7000);
			//Thread.sleep(rand1);
			String realUrl = lienTouspc + i;
			Document doc = getLaptopHtml(realUrl);
			if (doc.getAllElements().toString().contains("Votre sélection ne correspond à aucun résultat")){
	//			log.warn("i cant get this page its the last page : " + realUrl);
				break;
			}
			try {
				
				Elements elements = doc.getElementsByAttributeValue("class", "thumbnail-titleLink js-Recos-track js-Search-hashLink");
				int count = 0;
				//System.out.println(elements);
				for (Element element : elements) {
					String link =  element.attr("href").trim();
					count++;
					System.out.println(link);
					if (!links.contains(link)) {
						links.add(link);
					}
				}
				//System.out.println("count"+count);
			} catch (Exception e) {
				//Log.error("i cant find link of laptops elements");
				System.out.println("i cant find link of laptops elements");
			}
		}
		return links;
	}

public static Laptop getInfolaptop(String link) throws InterruptedException {
		int rand1 = getRandom(2000, 7000);
		//Thread.sleep(rand1);
		Document doc = getLaptopHtml(link);
		Laptop laptop = new Laptop();
		String title = "";
		String source = "";
		String uri = link;
		String name = "";
		String referance = "";
		String imageUri = "";
		String screenSize = "";
		String screenresolution = "";
		String cpu = "";
		String gpu = "";
		String ram = "";
		String storage = "";
		String operatingSystem = "";
		String weight = "";
		String price = "";
		String typestockage="";
		try {
			imageUri = doc.getElementsByAttributeValue("class", "f-productVisuals__mainMedia js-ProductVisuals-imagePreview").attr("src");
			System.out.println("image :"+imageUri);
			
			title=doc.getElementsByClass("f-productHeader-Title").text();
			System.out.println("titre : "+title); 
		
			price	= doc.getElementsByClass("f-priceBox-price f-priceBox-price--reco checked").text();
			System.out.println("price : "+price); 
			
            Elements elemts = doc.getElementsByAttributeValue("class", "f-productProperties__item");
			
			for (Element element : elemts) {
				if (element.text().matches(".*Capacité de stockage.*")) {
					storage = element.text().replaceFirst("Capacité de stockage", "").toString();
				}
				if (element.text().matches(".*RAM installée.*")) {
					ram = element.text().replaceFirst("RAM installée", "").toString();
				}
				if (element.text().matches(".*Processeur.*")) {
					cpu = element.text().replaceFirst("Processeur", "").toString();
				}
				
				if (element.text().matches(".*Carte graphique.*")) {
					gpu = element.text().replaceFirst("Carte graphique", "").toString();
				}

				if (element.text().matches(".*Résolution maximale de la carte graphique.*")) {
					screenresolution = element.text().replaceFirst("Résolution maximale de la carte graphique", "").toString();
				}
				
				if (element.text().matches(".*Type de disque dur.*")) {
					typestockage = element.text().replaceFirst("Type de disque dur", "").toString();
				}
				
				if (element.text().matches(".*Système d'exploitation.*")) {
					operatingSystem = element.text().replaceFirst("Système d'exploitation", "").toString();
				}
				
				if (element.text().matches(".*Poids du produit.*")) {
					weight = element.text().replaceFirst("Poids du produit", "").toString();
				}
				
				if (element.text().matches(".*Taille de l'écran.*")) {
					screenSize = element.text().replaceFirst("Taille de l'écran", "").toString();
				}

				
			}
			System.out.println("storage : "+storage); 
			System.out.println("ram : "+ram);
			System.out.println("Processeur : "+cpu);
			System.out.println("gpu : "+gpu);
			System.out.println("screenresolution : "+screenresolution);
			System.out.println("typestockage : "+typestockage);
			System.out.println("operatingSystem : "+operatingSystem);
			System.out.println("weight : "+weight);
			System.out.println("screenSize : "+screenSize);
			
			

			laptop.setTitle(title);
			laptop.setSource(source);
			laptop.setUri(uri);
			laptop.setName(title);
			laptop.setReference(referance);
			laptop.setImageUri(imageUri);
			laptop.setScreenSize(screenSize);
			laptop.setScreenResolution(screenresolution);
			laptop.setCpu(cpu);
			laptop.setGpu(gpu);
			laptop.setRam(ram);
			laptop.setStorage(storage);
			laptop.setOperatingSystem(operatingSystem);
			laptop.setWeight(weight);
			laptop.setPrice(price);
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		return laptop;
	}
public static ArrayList<Laptop> getAllLaptopsInfo() throws InterruptedException, IllegalAccessException {
	ArrayList<Laptop> laptopsinfos = new ArrayList<>();
	ArrayList<String> lesLiens = getLinksOfLaptops("https://www.fnac.com/Ordinateurs-portables/shi48967/w-4");
	for (String link : lesLiens) {
		Laptop laptop = getInfolaptop(link);	
		if( laptop.checkNotNull(laptop) == true) {
		laptopsinfos.add(laptop);
		System.out.println(laptop.toString());
		}
//		log.warn(String.valueOf(laptopsinfos.size()));
	}
	return laptopsinfos;
}
	

	
	
}
