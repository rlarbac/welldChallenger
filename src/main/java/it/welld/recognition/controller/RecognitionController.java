package it.welld.recognition.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.welld.recognition.model.Line;
import it.welld.recognition.model.Point;
import it.welld.recognition.service.PlaneService;

/**
 * The class has a responsibility to provide all REST services from this project.
 * @author rlarb
 *
 */
@RestController
public class RecognitionController {

	@Autowired
	private PlaneService service;	
	
	@PostMapping("/point") 
	public void addPoint(@RequestBody Point point) {
		service.addPoint(point);
	}
	
	@DeleteMapping("/space")
	public void deleteSpace() {
		service.clearAll();
	}
	
	@GetMapping("/space") 
	public Collection<Point> getPoints() {
		return service.getPoints();
	}

	@GetMapping("/lines/{n}") 
	public List<Set<Point>> getAllPointsFromEachLineByNumberOfPoints(@PathVariable(name = "n") int quantifyOfpoints) {
		return service.getAllPointsFromEachLineByNumberOfPoints(quantifyOfpoints);
	}
	
	@GetMapping("/linesWithMoreInformation/{n}") 
	public List<Line> getLinesWithMoreInformation(@PathVariable(name = "n") int quantifyOfpoints) {
		return service.getLinesByNumberOfPoints(quantifyOfpoints);
	}
	
	
}
