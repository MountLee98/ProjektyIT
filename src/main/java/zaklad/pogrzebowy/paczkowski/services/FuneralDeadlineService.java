package zaklad.pogrzebowy.paczkowski.services;

import java.time.LocalDateTime;
import java.util.List;

import zaklad.pogrzebowy.paczkowski.domain.FuneralDeadline;

public interface FuneralDeadlineService {
	void add(FuneralDeadline f);
	void del(FuneralDeadline f);

	List<FuneralDeadline> showAll();

	List<LocalDateTime> deadlines();
}
