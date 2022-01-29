package com.scanner.demo.scrapper;

import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scanner.demo.entities.Laptop;

public class MaterielNetScraper {
public static void main(String[] args) throws InterruptedException, IllegalAccessException {
		
		//Pour écrire les infos dans la bd
		LaptopPersistence lp=new LaptopPersistence();
		lp.bulkInsertLaptop(getAllLaptopsInfo());
		
		//getAllLaptopsInfo();
		//getLinksOfLaptops("https://www.materiel.net/pc-portable/l409/page");
		//getInfolaptop("https://www.materiel.net/produit/202109220063.html");
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

			doc = Jsoup.connect(link).userAgent(agent).referrer(referrer).ignoreHttpErrors(true).ignoreContentType(true).timeout(100000).maxBodySize(0)
					.get();
		} catch (Exception e) {
			System.out.println("problem to get html page problem in getlaptophtml function");
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
		String laptopLink = "https://www.materiel.net";
		int count =0;
		for (int i = 0; i < 13; i++) {
			int rand1 = getRandom(2000, 7000);
			//Thread.sleep(rand1);
			String realUrl = lienTouspc + i;
			Document doc = getLaptopHtml(realUrl);
			if (doc.getAllElements().toString().contains("Votre sélection ne correspond à aucun résultat")){
				System.out.println("i cant get this page its the last page : " + realUrl);
				break;
			}

			try {

				Elements elements = doc.getElementsByClass("c-product__link o-link--reset");
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
		String source = "Materiel.net";
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
			System.out.println("source : "+source);
			System.out.println("uri : "+uri);
			title = doc.select("div.col-12.col-md-9 > h1").text();
			System.out.println("Titre :"+title);
		
			price = doc.getElementsByClass("row align-items-center justify-content-center justify-content-lg-start mb-5 c-product__cart-zone c-unsalable-product invisible d-none d-md-flex").attr("data-product-price-vat-on").replace(".", "€");
			System.out.println("price :"+price);
			
			imageUri = doc.getElementsByAttributeValue("property", "og:image").attr("content");
			System.out.println("image :"+imageUri);
			
			Elements elemts = doc.select("table.table.c-specs__table > tbody > tr.feature");
			//System.out.println("eleements 1 :"+elemts);
			
			for (Element element : elemts) {
				if (element.text().matches(".*Système d'exploitation.*")) {
					operatingSystem = element.select("td.value").text();
				}
				if (element.text().matches(".*Taille de la mémoire.*")) {
					ram = element.select("td.value").text();
				}
			}
			Elements elemts_list2 = doc.select("table.table.c-specs__table > tbody > tr");
			
			for (Element element : elemts_list2) {
				
				if (element.text().matches(".*Type de processeur.*")) {
					cpu = element.select("td.value").text();
				}
				if (element.text().matches(".*Résolution Max.*")) {
					screenresolution = element.select("td.value").text();
				}
				if (element.text().matches(".*Poids.*")) {
					weight = element.select("td.value").text();
				}
				if (element.text().matches(".*Taille de l'écran.*")) {
					screenSize = element.select("td.value").text();
				}
				if (element.text().matches(".*Disque dur.*")) {
					storage = element.select("td.value").text();
				}
				if (element.text().matches(".*Gamme.*")) {
					reference = element.select("td.value").text();
				}
				if (element.text().matches(".*Chipset graphique.*")) {
					gpu = element.select("td.value").text();
				}
			}
			
			System.out.println("screenresolution :"+screenresolution);
			System.out.println("screensize :"+screenSize);
			System.out.println("reference : "+reference);
			System.out.println("cpu : "+cpu);
			System.out.println("gpu : "+gpu);
			System.out.println("ram : "+ram);
			System.out.println("storage : "+storage);
			System.out.println("operatingSystem : "+operatingSystem);
			System.out.println("weight : "+weight);
			System.out.println("******************************************************");
			
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
		ArrayList<String> lesLiens = getLinksOfLaptops("https://www.materiel.net/pc-portable/l409/page");
		for (String link : lesLiens) {
			Laptop laptop = getInfolaptop(link);	
			if( laptop.checkNotNull(laptop) == true) {
			laptopsinfos.add(laptop);
			System.out.println(laptop.toString());
			}
			System.out.println(String.valueOf(laptopsinfos.size()));
		}
		return laptopsinfos;
	}
	
}
