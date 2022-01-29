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
public class RueDuCommerceScraper {

	public static void main(String[] args) throws InterruptedException, IllegalAccessException {
		
		//Pour écrire les infos dans la bd
		LaptopPersistence lp=new LaptopPersistence();
		lp.bulkInsertLaptop(getAllLaptopsInfo());
		
		//getAllLaptopsInfo();
	}

	public static String getRandomUserAgent() {
		Random randomGenerator = new Random();
		ArrayList<String> userAgents = new ArrayList<>();
		userAgents.add("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:81.0) Gecko/20100101 Firefox/81.0");
		userAgents.add("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
		userAgents.add(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
		userAgents.add("Mozilla/5.0 (Android 7.1.1; Mobile; rv:93.0) Gecko/93.0 Firefox/93.0");
		userAgents.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0; MDDCJS)");
		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0");
		userAgents.add(
				"Mozilla/5.0 (Linux; Android 7.1.1; E6653) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.74 Mobile Safari/537.36");
		userAgents.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		userAgents.add(
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36");
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

			doc = Jsoup.connect(link).userAgent(agent).referrer(referrer).ignoreHttpErrors(true).ignoreContentType(true).timeout(10000)
					.get();
		} catch (Exception e) {
			log.error("problem to get html page problem in getlaptophtml function");
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
		String laptopLink = "https://www.rueducommerce.fr";
		for (int i = 1; i < 41; i++) {
			int rand1 = getRandom(2000, 7000);
			//Thread.sleep(rand1);
			String realUrl = lienTouspc + i;
			Document doc = getLaptopHtml(realUrl);
			if (doc.getAllElements().toString().contains("Votre sélection ne correspond à aucun résultat")){
				log.warn("i cant get this page its the last page : " + realUrl);
				break;
			}
			try {

				Elements elements = doc.select("a.item__image");
				int count = 0;
				for (Element element : elements) {
					String link = laptopLink + element.attr("href").trim();
					count++;
					System.out.println("la "+i+" : "+link);
					if (!links.contains(link)) {
						links.add(link);
					}
				}
				System.out.println(count);
			} catch (Exception e) {
				log.error("i cant find link of laptops elements");
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
		String source = "Rue Du Commerce";
		String uri = link;
		String name = "";
		String reference = "";
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
			title = doc.getElementsByAttributeValue("itemprop", "name").attr("content");
			reference=doc.getElementsByAttributeValue("itemprop","productID").text();
			imageUri = doc.getElementsByAttributeValue("itemprop", "image").attr("src");
			System.out.println("Titre :"+title);
			System.out.println("Reference :"+reference);
			System.out.println("image :"+imageUri);
			price = doc.getElementsByClass("dyn_prod_price").text()+"€";
			System.out.println("price :"+price);
			
			screenSize=doc.select("p.legende").text().split("-")[0].trim().toString();
			System.out.println("screenSize : "+screenSize);
			
			Elements elemts = doc.getElementsByClass("group-item");
			
			for (Element element : elemts) {
				if (element.text().matches(".*Résolution de l'écran Pixels.*") || element.text().matches(".*Résolution maximale.*") || element.text().matches(".*Résolution de l'écran.*") ) {
					screenresolution = element.text().toString().split(":")[1].trim().toString();
				}
				if (element.text().matches(".*Modèle du processeur portable.*") || element.text().matches(".*Processeur.*")) {
					cpu = element.text().toString().split(":")[1].trim().toString();
				}
				if (element.text().matches(".*Description carte graphique.*") || element.text().matches(".*Type de carte graphique.*")) {
					gpu = element.text().toString().split(":")[1].trim().toString();
				}
				if (element.text().matches(".*Mémoire.*") || element.text().matches(".*Mémoire vive installée (Go).*")) {
					ram = element.text().toString().split(":")[1].trim().toString()+" Go";
				}
				
				if (element.text().matches(".*Type de stockage.*")) {
					typestockage = element.text().toString().split(":")[1].trim().toString()+"";	
				}
				
				if (element.text().matches(".*Hard drives.*") || element.text().matches(".*Capacité SSD.*") || element.text().matches(".*Capacité totale de stockage .*")) {
					storage = element.text().toString().split(":")[1].trim().toString();
					storage = typestockage+" "+storage;
				}
				
				if (element.text().matches(".*Type de Système d'exploitation.*") || element.text().matches(".*Version du système d'exploitation.*") || element.text().matches(".*Version de l'OS.*")  ) {
					operatingSystem = element.text().toString().split(":")[1].trim().toString();
				}
				if (element.text().matches(".*Poids kg.*") ) {
					weight = element.text().toString().split(":")[1].trim().toString()+" kg";
				}
				 
			}
			
			System.out.println("screenresolution :"+screenresolution);
			System.out.println("cpu : "+cpu);
			System.out.println("gpu : "+gpu);
			System.out.println("ram : "+ram);
			System.out.println("storage : "+storage);
			System.out.println("operatingSystem : "+operatingSystem);
			System.out.println("weight : "+weight);
			
			laptop.setTitle(title);
			laptop.setSource(source);
			laptop.setUri(uri);
			laptop.setName(title);
			laptop.setReference(reference);
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
		ArrayList<String> lesLiens = getLinksOfLaptops("https://www.rueducommerce.fr/rayon/ordinateurs-64/pc-portable-5875?page=");
		for (String link : lesLiens) {
			Laptop laptop = getInfolaptop(link);	
			if( laptop.checkNotNull(laptop) == true) {
			laptopsinfos.add(laptop);
			System.out.println(laptop.toString());
			}
			log.warn(String.valueOf(laptopsinfos.size()));
		}
		return laptopsinfos;
	}
	
	

}
