package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VCardGetter {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showHomePage() {
		return new ModelAndView("Company", "Company", new Company());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/h")
	public String hydraulik(@RequestParam("p") String profesja) throws IOException {
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
//					System.out.println(element.data());
					Company company = new Company();
					company.name = getFieldFromJSONString(element.data(), "name");
					company.telephone = getFieldFromJSONString(element.data(), "telephone");
					company.email = getFieldFromJSONString(element.data(), "email");
					company.postalCode = getFieldFromJSONString(element.data(), "postalCode");
					company.country = getFieldFromJSONString(element.data(), "addressCountry");
					company.street = getFieldFromJSONString(element.data(), "streetAddress");
					company.locality = getFieldFromJSONString(element.data(), "addressLocality");
					companies.add(company);
//					getFieldFromJSONString(element.data(), "name");
//					str.append(element.data());
					str.append(company.toString());
					System.out.println(company.toString());
				}
			}
		}

		return str.toString();
	}

	private String getFieldFromJSONString(String json, String field) {
		String str = "";
		int indexOf = json.indexOf(field);
		str = json.substring(indexOf + field.length() + 3, json.indexOf("\"",indexOf + field.length() + 3));
//		System.out.println(str);
		return str;
	}


}