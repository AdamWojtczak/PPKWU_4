package com.example.demo;

import com.example.demo.Company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
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
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
public class VCardGetter {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showHomePage() {
		return new ModelAndView("Company", "Company", new Company());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/h")
	public String hydraulik(@RequestParam("p") String profesja) throws IOException {
//		return new ModelAndView("Company", "Company", new Company());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://panoramafirm.pl/szukaj?k=");
		stringBuilder.append(profesja);
		stringBuilder.append("&l=");
		Document doc = Jsoup.connect(stringBuilder.toString()).get();

		StringBuilder str = new StringBuilder();
		Elements elements = doc.select("script");
		for (Element element : elements) {
			if (element.attr("type").equals("application/ld+json")) {
				if (element.data().contains("LocalBusiness")) {
					System.out.println(element.data());
					str.append(element.data());
				}
			}
		}
//		Elements scriptElements = doc.getElementsByTag("script");
//		for (Element element :scriptElements ){
//			element.attr()
//			for (DataNode node : element.dataNodes()) {
//				System.out.println(node.getWholeData());
//				str.append(node.getWholeData());
//			}
//			System.out.println("-------------------");
//		}


		return str.toString();
	}

	private String getFromFukenJSONString(String json, String field) {
		String str = "";
		int indexOf = json.indexOf(field);

		return str;
	}


}