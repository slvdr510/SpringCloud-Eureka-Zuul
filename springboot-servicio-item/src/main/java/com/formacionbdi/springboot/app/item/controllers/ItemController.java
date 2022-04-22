package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	private Environment env;

	@Value("${server.port}")
	private Integer port;

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	@GetMapping("/listar")
	public List<Item> listar() {
		return itemService.findAll().stream().map(item -> {
			item.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			// item.setPort(port);
			return item;
		}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		Item item = itemService.findById(id, cantidad);
		// item.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		item.setPort(port);
		return item;
	}

}
