package zaklad.pogrzebowy.paczkowski.ui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;

import zaklad.pogrzebowy.paczkowski.domain.CauseOfDeath;
import zaklad.pogrzebowy.paczkowski.domain.Cemetery;
import zaklad.pogrzebowy.paczkowski.domain.Client;
import zaklad.pogrzebowy.paczkowski.domain.FuneralDeadline;
import zaklad.pogrzebowy.paczkowski.domain.Team;
import zaklad.pogrzebowy.paczkowski.services.CemeteryService;
import zaklad.pogrzebowy.paczkowski.services.ClientService;
import zaklad.pogrzebowy.paczkowski.services.FuneralDeadlineService;
import zaklad.pogrzebowy.paczkowski.services.TeamService;

@Route("dodajTermin")
public class NewFuneralDeadline extends VerticalLayout{
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	CemeteryService cemeteryService;
	
	@Autowired
	FuneralDeadlineService funeralDeadlineService;
	
	Div interior;
	
	String name;
	
	CauseOfDeath cause;
	
	@PostConstruct
	void init() {
		interior = new Div();
		interior.setWidthFull();
		interior.setHeightFull();		
		add(interior);
		
		interior.add(new addNewClientView());
	}
	
	class addNewClientView extends VerticalLayout {

		addNewClientView()
		{
			HorizontalLayout h = new HorizontalLayout();
	
			
			TextField nameTF = new TextField("Imię");
			
			h.add(nameTF);
			
			ComboBox<CauseOfDeath> causeDeath = new ComboBox<>();
			causeDeath.setItems(CauseOfDeath.values());
			causeDeath.setLabel("Przyczyna śmierci");
			
			h.add(causeDeath);
			
			Button send = new Button("Dodaj klienta");
			
			send.addClickListener( x -> {
				if(causeDeath.getValue() != null) {
					Client c = new Client();
					c.setName(nameTF.getValue());
					name = nameTF.getValue();
					c.setCauseOfDeath(causeDeath.getValue());
					cause = causeDeath.getValue();
					
					clientService.add(c);
					
					interior.removeAll();
					interior.add(new addDeadlineView());
				}
				
			});
			
			add(h, send);
		}
	}
	
	class addDeadlineView extends VerticalLayout {
		
		addDeadlineView() {
//			Label checkDeadline = new Label();
//			checkDeadline.setText("Wybierz team, cmentarz, datę i godzinę");
//			checkDeadline.getStyle().set("fontWeight", "bold");
			
			HorizontalLayout h1 = new HorizontalLayout();
			
			ComboBox<Team> teams = new ComboBox<>();
			List<Team> t = teamService.showAll();
			teams.setItems(t);
			teams.setItemLabelGenerator(Team::getNameOfTheTeam);
			teams.setLabel("Wybierz Team");
			
			ComboBox<Cemetery> cemetery = new ComboBox<>();
			List<Cemetery> c = cemeteryService.showAll();
			cemetery.setItems(c);
			cemetery.setItemLabelGenerator(Cemetery::getCemeteryName);
			cemetery.setLabel("Wybierz cmentarz");
			
			h1.add(teams, cemetery);
			
			HorizontalLayout h2 = new HorizontalLayout();
			
//			List<LocalDateTime> notDeadline = funeralDeadlineService.deadlines();
			
			ComboBox<LocalDateTime> nDeadline = new ComboBox<>();		
			
			
//			DatePicker datePicker = new DatePicker();
//			datePicker.setLabel("Wybierz datę");
//			
//			TimePicker timePicker = new TimePicker();
//			timePicker.setLabel("Wybierz godzinę");
//
//			timePicker.setMin("09:00");
//			timePicker.setMax("11:00");
//			
//			
			teams.addValueChangeListener(x -> {
				List<LocalDateTime> notDeadline = funeralDeadlineService.deadlines();
				if (teams.getValue().getNameOfTheTeam().equals("Pogrzebowcy")) {
					List<LocalDateTime> pogDeadline = funeralDeadlineService.showAll().stream()
							.filter( y -> y.getNameOfTeam().equals(teams.getValue().getNameOfTheTeam()))
							.map( y -> y.getDeadline()).collect(Collectors.toList());
					
					pogDeadline.stream()
					.filter( y -> notDeadline.stream()
							.filter( z -> z.isEqual(y)).findFirst().isPresent())
					.forEach(y -> {
						notDeadline.remove(y);
					});
					
					nDeadline.setItems(notDeadline);

				}
				
				if (teams.getValue().getNameOfTheTeam().equals("Chowance")) {
					List<LocalDateTime> pogDeadline = funeralDeadlineService.showAll().stream()
							.filter( y -> y.getNameOfTeam().equals(teams.getValue().getNameOfTheTeam()))
							.map( y -> y.getDeadline()).collect(Collectors.toList());
					
					pogDeadline.stream()
					.filter( y -> notDeadline.stream()
							.filter( z -> z.isEqual(y)).findFirst().isPresent())
					.forEach(y -> {
						notDeadline.remove(y);
					});
					
					nDeadline.setItems(notDeadline);
					System.out.println(nDeadline.getValue());

				}
				
			});
//			
//			datePicker.addValueChangeListener(x -> {
//				String dateTime = datePicker.getValue()+" "+timePicker.getValue();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//				LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
//				
//				if(localDateTime.isBefore(LocalDateTime.now())) {
//					checkDeadline.setText("Nie można ustawić terminu przed obecną datą!");
//				} else if(localDateTime.isAfter(LocalDateTime.now().plusDays(14))) {
//					checkDeadline.setText("Nie przewidujemy terminów 2 tygodnie wprzód!");
//				} else if(localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {	
//					checkDeadline.setText("Nie przyjmujemy terminów w sobotę i niedzielę!");
//				} else {
//					
//					List<FuneralDeadline> f =  funeralDeadlineService.showAll().stream()
//							.filter(y -> y.getNameOfTeam().equals(teams.getValue().getNameOfTheTeam()))
//							.filter(y -> y.getDeadline().isEqual(localDateTime))
//							.collect(Collectors.toList());
//					if (f.size() == 0) {
//						checkDeadline.setText("Termin wolny.");
//					} else {
//						checkDeadline.setText("Taki termin już istnieje!");
//					}
//				}
//			});
//			
//			timePicker.addValueChangeListener(x -> {
//				String dateTime = datePicker.getValue()+" "+timePicker.getValue();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//				LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
//				
//				if(localDateTime.isBefore(LocalDateTime.now())) {
//					checkDeadline.setText("Nie można ustawić terminu przed obecną datą!");
//				} else if(localDateTime.isAfter(LocalDateTime.now().plusDays(14))) {
//					checkDeadline.setText("Nie przewidujemy terminów 2 tygodnie wprzód!");
//				} else if(localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {	
//					checkDeadline.setText("Nie przyjmujemy terminów w sobotę i niedzielę!");
//				} else {
//					
//					List<FuneralDeadline> f =  funeralDeadlineService.showAll().stream()
//							.filter(y -> y.getNameOfTeam().equals(teams.getValue().getNameOfTheTeam()))
//							.filter(y -> y.getDeadline().isEqual(localDateTime))
//							.collect(Collectors.toList());
//					if (f.size() == 0) {
//						checkDeadline.setText("Termin wolny.");
//					} else {
//						checkDeadline.setText("Taki termin już istnieje!");
//					}
//				}
//			});
			
			//h2.add(datePicker, timePicker);
			
			
			Button send = new Button("Dodaj termin");
			
			send.addClickListener( x -> {
				if(cemetery.getValue() != null) {
					FuneralDeadline deadline = new FuneralDeadline();
					deadline.setNameOfDeceased(name);
					deadline.setCauseOfDeath(cause);
					deadline.setNameOfTeam(teams.getValue().getNameOfTheTeam());
					deadline.setCemeteryName(cemetery.getValue().getCemeteryName());
					
//					String dateTime = datePicker.getValue()+" "+timePicker.getValue();
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//			        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
			        
					deadline.setDeadline(nDeadline.getValue());
					
					funeralDeadlineService.add(deadline);
					
					interior.removeAll();
					interior.add(new showAllDeadlines());
				}
			});
			
			add(h1, nDeadline, send);
		}	
	}
	
	class showAllDeadlines extends VerticalLayout {
		
		showAllDeadlines() {
			
			
			this.setHeightFull();
			
			Grid<FuneralDeadline> tab = new Grid<>();
			
//			tab.setHeightFull();
			
			tab.removeAllColumns();
			tab.setSelectionMode(SelectionMode.SINGLE);
			tab.setItems(funeralDeadlineService.showAll());
			tab.addColumn(FuneralDeadline::getId).setHeader("Id");
			tab.addColumn(FuneralDeadline::getNameOfDeceased).setHeader("Imię zmarłego");
			tab.addColumn(FuneralDeadline::getCauseOfDeath).setHeader("Przyczyna zgonu");
			tab.addColumn(FuneralDeadline::getNameOfTeam).setHeader("Nazwa Teamu");
			tab.addColumn(FuneralDeadline::getCemeteryName).setHeader("Gdzie pogrzeb");
			tab.addColumn(FuneralDeadline::getDeadline).setHeader("Termin");
			
			add(tab);
		}
	}
	
}
