package zaklad.pogrzebowy.paczkowski.ui;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import zaklad.pogrzebowy.paczkowski.domain.CauseOfDeath;
import zaklad.pogrzebowy.paczkowski.domain.FuneralDeadline;
import zaklad.pogrzebowy.paczkowski.domain.Team;
import zaklad.pogrzebowy.paczkowski.services.CemeteryService;
import zaklad.pogrzebowy.paczkowski.services.ClientService;
import zaklad.pogrzebowy.paczkowski.services.FuneralDeadlineService;
import zaklad.pogrzebowy.paczkowski.services.TeamService;
import zaklad.pogrzebowy.paczkowski.ui.NewFuneralDeadline.addNewClientView;

@Route("grafikTeamu")
public class TeamPlan extends VerticalLayout{
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	CemeteryService cemeteryService;
	
	@Autowired
	FuneralDeadlineService funeralDeadlineService;
	
	Div interior;
	
	@PostConstruct
	void init() {
		interior = new Div();
		interior.setWidthFull();
		interior.setHeightFull();		
		add(interior);
		
		interior.add(new viewTeamPlan());
	}
	
	class viewTeamPlan extends VerticalLayout{
		
		viewTeamPlan() {
			
			ComboBox<Team> teams = new ComboBox<>();
			teams.setItems(teamService.showAll());
			teams.setItemLabelGenerator(Team::getNameOfTheTeam);
			teams.setLabel("Wybierz Team");
			
			VerticalLayout v = new VerticalLayout();
			
			teams.addValueChangeListener(event -> {
				this.setHeightFull();
				
				Grid<FuneralDeadline> tab = new Grid<>();
				
				//tab.setHeightFull();
				
				
				List<FuneralDeadline> f = funeralDeadlineService.showAll().stream()
						.filter(x -> x.getNameOfTeam().equals(teams.getValue().getNameOfTheTeam()))
						.collect(Collectors.toList());
				
				tab.removeAllColumns();
				tab.setSelectionMode(SelectionMode.SINGLE);
				tab.setItems(f);
				tab.addColumn(FuneralDeadline::getId).setHeader("Id");
				tab.addColumn(FuneralDeadline::getNameOfDeceased).setHeader("Imię zmarłego");
				tab.addColumn(FuneralDeadline::getCauseOfDeath).setHeader("Przyczyna zgonu");
				tab.addColumn(FuneralDeadline::getCemeteryName).setHeader("Gdzie pogrzeb");
				tab.addColumn(FuneralDeadline::getDeadline).setHeader("Termin");
				
				v.removeAll();              
				v.add(tab);
			});
			
			add(teams, v);
		}
	}
}
