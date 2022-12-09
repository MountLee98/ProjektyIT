package zaklad.pogrzebowy.paczkowski.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zaklad.pogrzebowy.paczkowski.domain.Cemetery;
import zaklad.pogrzebowy.paczkowski.domain.FuneralDeadline;
import zaklad.pogrzebowy.paczkowski.repo.FuneralDeadlineRepo;

@Service
public class FuneralDeadlineServiceImpl implements FuneralDeadlineService{
	
	@Autowired
	FuneralDeadlineRepo funeralDeadlineRepo;
	
	@Override
	public void add(FuneralDeadline f) {
		funeralDeadlineRepo.save(f);
		
	}

	@Override
	public void del(FuneralDeadline f) {
		funeralDeadlineRepo.delete(f);
		
	}

	@Override
	public List<FuneralDeadline> showAll() {
		List<FuneralDeadline> res = funeralDeadlineRepo.findAll();
		if(res == null)
			res = new ArrayList<>();
		return res;
	}

	@Override
	public List<LocalDateTime> deadlines() {
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
				.filter( x -> showAll().stream()
						.filter(y -> y.getDeadline().isEqual(x))
						.findFirst().isPresent() == false)
				.collect(Collectors.toList());
		
		return notDeadline;
	}
	
	

}
