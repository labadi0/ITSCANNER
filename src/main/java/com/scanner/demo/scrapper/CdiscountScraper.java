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
public class CdiscountScraper {
	
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
		System.out.println(getInfolaptop("https://www.cdiscount.com/informatique/ordinateurs-pc-portables/ordinateur-portable-chromebook-acer-cb314-2h-k9db/f-1070992-nxawfef001.html#mpos=0|cd"));
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

			doc = Jsoup.connect(link).userAgent(agent).referrer(referrer).ignoreHttpErrors(true).ignoreContentType(true).timeout(10000).maxBodySize(0)
					.get();
			//System.out.println("document : "+doc);
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
		String laptopLink = "https://www.cdiscount.com";
		for (int i = 0; i < 54; ++i) {
			int rand1 = getRandom(2000, 7000);
			//Thread.sleep(rand1);
			String realUrl = lienTouspc + i;
			Document doc = getLaptopHtml(realUrl);
			if (doc.getAllElements().toString().contains("Votre sélection ne correspond à aucun résultat")){
				log.warn("i cant get this page its the last page : " + realUrl);
				break;
			}
			try {
				
				Elements elements = doc.select("ul.prdtBPCar.jsPrdtBILLink");
				int count = 0;
				//System.out.println(elements);
				for (Element element : elements) {
					String link =  element.attr("data-href").trim();
					count++;
					System.out.println(link);
					if (!links.contains(link)) {
						links.add(link);
					}
				}
				System.out.println("count"+count);
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
		try {
			imageUri = doc.getElementsByAttributeValue("property", "og:image").attr("content");
			title=doc.select("div.fpDesCol > h1").text();
			System.out.println("titre : "+title);
			System.out.println("image :"+imageUri);
			
			Elements elemts = doc.getElementsByAttributeValue("class", "fpDescTb fpDescTbPub").first().getElementsByTag("tr");
		//	System.out.println(doc.getElementsByAttributeValue("class", "fpDescTb fpDescTbPub").first().getElementsByTag("tr"));
			
			for (Element element : elemts) {
				String section= element.getElementsByTag("td").first().toString();
				
				
				System.out.println("element   "+element);
				
				String valeur= element.getElementsByTag("td").get(1).toString();
				System.out.println("section : "+section);
				System.out.println("valeur : "+valeur);
				
				if (section.matches(".*Référence.*")) {
					referance = valeur.toString().trim();
				}
				
			}
			System.out.println("reference : "+referance);
			//System.out.println("cpu : "+cpu);
			
			/*title = doc.getElementsByAttributeValue("itemprop", "name").attr("content");
			referance=doc.getElementsByAttributeValue("itemprop","productID").text();
			 //= doc.getElementsByClass("product-title__ref").text().split(":")[1].trim();
			//imageUri = doc.getElementsByClass("product-img__visual").attr("src");
			imageUri = doc.getElementsByAttributeValue("itemprop", "image").attr("src");
			System.out.println("Titre :"+title);
			System.out.println("Reference :"+referance);
			System.out.println("image :"+imageUri);
			price = doc.getElementsByAttributeValue("class", "dyn_prod_price").text()+"€";
			System.out.println("price :"+price);
			screenSize = doc.select("ul.tech-list li.clr p.value").get(2).text().replaceAll("pouces", "");	
			System.out.println("screenSize : "+screenSize);
			//screenresolution=doc.select("div.caracteristique-box div.group-box div.group-items div.group-item div.value span")..text();
		
				screenresolution = doc.text().matches("Résolution de l'écran")
						+ doc.getElementsByClass("product-description-box-img-limit").text().toString().split(":")[1].trim().toString();
			
			System.out.println("screenresolution :"+screenresolution);*/
			/*screenSize = doc.getElementsByClass("tech-list").attr("li");
			System.out.println("screenSize :"+screenSize);
			*/
			/*
			Elements elemts = doc.getElementsByClass("feature-item__content");
			for (Element element : elemts) {
				if (element.text().matches(".*Taille standard d'écran d'ordinateur.*")) {
					screenSize = element.text().toString().trim().toString();
				}
				if (element.text().matches(".*Résolution.*")) {
					screenresolution = screenresolution + " "
							+ element.text().toString().split(":")[1].trim().toString();
				}
				if (element.text().matches(".*Type de résolution.*")) {
					screenresolution = screenresolution + " "
							+ element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Référence du processeur.*")) {
					cpu = element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Carte graphique.*")) {
					gpu = gpu + " " + element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Mémoire vive (RAM).*")) {
					ram = ram + " " + element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Type et capacité totale de stockage.*")) {
					storage = storage + " " + element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Système d'exploitation.*")) {
					operatingSystem = operatingSystem + " " + element.text().toString().split(":")[1].trim().toString();
				}

				if (element.text().matches(".*Poids.*")) {
					weight = weight + " " + element.text().toString().split(":")[1].trim().toString();
				}

			}
*/
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
	ArrayList<String> lesLiens = getLinksOfLaptops("  ");
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
