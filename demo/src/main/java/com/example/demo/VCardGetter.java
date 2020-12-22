package com.example.demo;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VCardGetter {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showHomePage() {
//		ModelAndView mnv = new ModelAndView("company", "company", new Company());
//		ModelAndView mnv = new ModelAndView("company");
//		mnv.addObject("company", new Company());
//		System.out.println(mnv.getModel());
//		System.out.println(mnv.getView());
		ModelAndView mnv = new ModelAndView("index", "company", new Company());
		return mnv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
	public String searchEmployee( @ModelAttribute("company") Company company, BindingResult result, ModelMap model) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://panoramafirm.pl/szukaj?k=");
		stringBuilder.append("hydraulik");
		stringBuilder.append("&l=");
		Document doc = Jsoup.connect(stringBuilder.toString()).get();

		StringBuilder str = new StringBuilder();
		List<Company> companies = new ArrayList<Company>();

		System.out.println(company);
		Elements elements = doc.select("script");
		for (Element element : elements) {
			if (element.attr("type").equals("application/ld+json")) {
				if (element.data().contains("LocalBusiness")) {
//					Company company = new Company();
					company.name = getFieldFromJSONString(element.data(), "name");
					company.telephone = getFieldFromJSONString(element.data(), "telephone");
					company.email = getFieldFromJSONString(element.data(), "email");
					company.postalCode = getFieldFromJSONString(element.data(), "postalCode");
					company.country = getFieldFromJSONString(element.data(), "addressCountry");
					company.street = getFieldFromJSONString(element.data(), "streetAddress");
					company.locality = getFieldFromJSONString(element.data(), "addressLocality");
					companies.add(company);
					str.append(company.toString());
					System.out.println(company.toString());
				}
			}
		}

		model.addAttribute("companies", companies);

		return "results";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/h")
	public String hydraulik(@RequestParam("name") String profesja) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://panoramafirm.pl/szukaj?k=");
		stringBuilder.append(profesja);
		stringBuilder.append("&l=");
		Document doc = Jsoup.connect(stringBuilder.toString()).get();

		StringBuilder str = new StringBuilder();
		List<Company> companies = new ArrayList<Company>();

		Elements elements = doc.select("script");
		for (Element element : elements) {
			if (element.attr("type").equals("application/ld+json")) {
				if (element.data().contains("LocalBusiness")) {
					Company company = new Company();
					company.name = getFieldFromJSONString(element.data(), "name");
					company.telephone = getFieldFromJSONString(element.data(), "telephone");
					company.email = getFieldFromJSONString(element.data(), "email");
					company.postalCode = getFieldFromJSONString(element.data(), "postalCode");
					company.country = getFieldFromJSONString(element.data(), "addressCountry");
					company.street = getFieldFromJSONString(element.data(), "streetAddress");
					company.locality = getFieldFromJSONString(element.data(), "addressLocality");
					companies.add(company);
					str.append(company.toString());
					System.out.println(company.toString());
				}
			}
		}



		return str.toString();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getvcard")
	public String getVCard(@RequestParam("name") String profesja, @RequestParam("index") String index, HttpServletResponse response) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://panoramafirm.pl/szukaj?k=");
		stringBuilder.append(profesja);
		stringBuilder.append("&l=");
		Document doc = Jsoup.connect(stringBuilder.toString()).get();

		StringBuilder str = new StringBuilder();
		List<Company> companies = new ArrayList<Company>();

		Elements elements = doc.select("script");
		for (Element element : elements) {
			if (element.attr("type").equals("application/ld+json")) {
				if (element.data().contains("LocalBusiness")) {
					Company company = new Company();
					company.name = getFieldFromJSONString(element.data(), "name");
					company.telephone = getFieldFromJSONString(element.data(), "telephone");
					company.email = getFieldFromJSONString(element.data(), "email");
					company.postalCode = getFieldFromJSONString(element.data(), "postalCode");
					company.country = getFieldFromJSONString(element.data(), "addressCountry");
					company.street = getFieldFromJSONString(element.data(), "streetAddress");
					company.locality = getFieldFromJSONString(element.data(), "addressLocality");
					company.url = getFieldFromJSONString(element.data(), "url");
					companies.add(company);
					str.append(company.toString());
					System.out.println(company.toString());
				}
			}
		}


		Company indexedCompany = companies.get(Integer.parseInt(index));

		if(indexedCompany == null) {
			return "Podano zły indeks";
		}

		File file = new File("vcard.vcf");
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		bufferedWriter.write("BEGIN:VCARD\r\n");
		bufferedWriter.write("VERSION:4.0\r\n");
		bufferedWriter.write("ORG:" + indexedCompany.getName() + "\r\n");
		bufferedWriter.write("TEL:" + indexedCompany.getTelephone() + "\r\n");
		bufferedWriter.write("ADR:" + indexedCompany.getStreet() + " " + indexedCompany.getPostalCode() + "\r\n");
		bufferedWriter.write("EMAIL:" + indexedCompany.getEmail() + "\r\n");
		bufferedWriter.write("URL:" + indexedCompany.getUrl() + "\r\n");
		bufferedWriter.write("END:VCARD");
		bufferedWriter.close();

		try {
			InputStream inputStream = new FileInputStream(file);
			response.setContentType("text/x-vcard;charset=utf-8");
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}


		return str.toString();
	}

	private String getFieldFromJSONString(String json, String field) {
		String str = "";
		int indexOf = json.indexOf(field);
		str = json.substring(indexOf + field.length() + 3, json.indexOf("\"",indexOf + field.length() + 3));
		return str;
	}


}