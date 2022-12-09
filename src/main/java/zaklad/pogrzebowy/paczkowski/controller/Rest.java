package zaklad.pogrzebowy.paczkowski.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;
import zaklad.pogrzebowy.paczkowski.domain.CauseOfDeath;
import zaklad.pogrzebowy.paczkowski.domain.Cemetery;
import zaklad.pogrzebowy.paczkowski.domain.Client;
import zaklad.pogrzebowy.paczkowski.domain.FuneralDeadline;
import zaklad.pogrzebowy.paczkowski.domain.Team;
import zaklad.pogrzebowy.paczkowski.services.CemeteryService;
import zaklad.pogrzebowy.paczkowski.services.ClientService;
import zaklad.pogrzebowy.paczkowski.services.FuneralDeadlineService;
import zaklad.pogrzebowy.paczkowski.services.TeamService;

@RestController
public class Rest {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	CemeteryService cemeteryService;
	
	@Autowired
	FuneralDeadlineService funeralDeadlineService;
	
	
	
	@GetMapping("statyZgonow")
	HashMap<CauseOfDeath, Integer> death() {
		
		HashMap<CauseOfDeath, Integer> stats = new HashMap<CauseOfDeath,Integer>();
		
		List<CauseOfDeath> list = new ArrayList<CauseOfDeath>(Arrays.asList(CauseOfDeath.values()));

		list.stream().forEach(x -> stats.put(x, 0));
		
		funeralDeadlineService.showAll().stream()
			.map(x -> x.getCauseOfDeath()).forEach(x -> {
				stats.put(x, stats.get(x)+1);
			});
		
		return stats;
		
	}
	
	@GetMapping("terminy")
	List<LocalDateTime> deadlines() {
		
		
		LocalDate nowDate = LocalDate.now();		
		
		List<LocalDateTime> dateTimeList = new ArrayList<>();
		
		List<LocalDate> dateList = new ArrayList<>();
		
		Stream.iterate(nowDate, d -> d.plusDays(1))
		  .limit(14)
		  .forEach( d -> {
			  IntStream.range(9, 12).forEach( t -> {
				  LocalTime l = LocalTime.of(t, 0);
				  dateTimeList.add(LocalDateTime.of(d, l));
			  });
		  });
		
		List<LocalDateTime> notDeadline = dateTimeList.stream()
				.filter( x -> x.getDayOfWeek() != DayOfWeek.SUNDAY)
				.filter( x -> x.getDayOfWeek() != DayOfWeek.SATURDAY)
				.filter( x -> funeralDeadlineService.showAll().stream()
						.filter(y -> y.getDeadline().isEqual(x))
						.findFirst().isPresent() == false)
				.collect(Collectors.toList());
		
		return notDeadline;
	}
	
	@GetMapping("makeBase")
	String makeBase()
	{
		
		Team t1 = new Team();
		t1.setNameOfTheTeam("Pogrzebowcy");
		teamService.add(t1);
		
		Team t2 = new Team();
		t2.setNameOfTheTeam("Chowance");
		teamService.add(t2);
		
		Cemetery cem1 = new Cemetery();
		cem1.setCemeteryName("Cmentarz Ludwik");
		cemeteryService.add(cem1);
		
		Cemetery cem2 = new Cemetery();
		cem2.setCemeteryName("Cmentarz Blonie");
		cemeteryService.add(cem2);
		
		Cemetery cem3 = new Cemetery();
		cem3.setCemeteryName("Cmentarz Przedmiescie");
		cemeteryService.add(cem3);
		
		Cemetery cem4 = new Cemetery();
		cem4.setCemeteryName("Cmentarz Jakis");
		cemeteryService.add(cem4);
		
		
		
		return "done";
	}
}
