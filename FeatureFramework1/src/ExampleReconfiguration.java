
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;

import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.Feature;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureModelImpl;
import com.emiliano.fmframework.core.FeatureState;
import com.emiliano.fmframework.core.constraints.treeConstraints.OptionalFeature;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.fmframework.optimization.ConfigurationSelectionAlgorithm;
import com.emiliano.fmframework.optimization.ConfigurationSelectionInstance;
import com.emiliano.fmframework.optimization.csa.BacktrackingCSA;
import com.emiliano.fmframework.optimization.csa.strategies.SearchStrategy;
import com.emiliano.fmframework.optimization.objectiveFunctions.AdditionObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.LinearWeightedObjective;
import com.emiliano.fmframework.optimization.objectiveFunctions.ObjectiveFunction;

public class ExampleReconfiguration {
	public static void main(String[] args) {
		
		// Build the model
		FeatureModel model = new FeatureModelImpl();
		model.addFeature(new Feature("root"));
		model.addFeature(new Feature("componentA"));
		model.addFeature(new Feature("componentB"));
		model.addFeature(new Feature("componentC"));
		model.addTreeConstraint(new OptionalFeature("root", "componentA"));
		model.addTreeConstraint(new OptionalFeature("root", "componentB"));
		model.addTreeConstraint(new OptionalFeature("root", "componentC"));

		// Build the real system
		Map<String, Component> components = new HashMap<String, Component>();
		components.put("componentA", new Component(1.1, 1.1));
		components.put("componentB", new Component(1.2, 1.0));
		components.put("componentC", new Component(1.0, 1.3));

		// Associate elements of the real system to the model
		model.getFeature("componentA").setSoftElement(components.get("componentA"));
		model.getFeature("componentB").setSoftElement(components.get("componentB"));
		model.getFeature("componentC").setSoftElement(components.get("componentC"));

		
		
		
		// Current full configuration
		Configuration current = new Configuration(model);
		current.setFeatureStateWithConstraintPropagation("root", true);
		current.setFeatureStateWithConstraintPropagation("componentA", true);
		current.setFeatureStateWithConstraintPropagation("componentB", false);
		current.setFeatureStateWithConstraintPropagation("componentC", true);

		// New partial configuration from which we select a new full
		// configuration
		Configuration partialConfiguration = new Configuration(model);
		partialConfiguration.setFeatureStateWithConstraintPropagation("root", true);
		partialConfiguration.setFeatureStateWithConstraintPropagation("componentA", false);

		// Add attributes to model
		addReconfigurationTimeValues(model, current);

		// Select the new configuration that minimizes reconfiguration time from
		// the partial configuration
		ObjectiveFunction function = new AdditionObjective("SelectionTime", "DeselectionTime");
		
		ConfigurationSelectionInstance instance = new ConfigurationSelectionInstance(partialConfiguration, function);
		BacktrackingCSA algorithm = new BacktrackingCSA(SearchStrategy.BestFirstSearchStar);
		Configuration newConfig = algorithm.selectConfiguration(instance);
		System.out.println(newConfig + " " + function.evaluate(newConfig));

		// Show that the chosen configuration was the one that minimizes
		// reconfiguration time
		List<Configuration> allSolutions = new LinkedList<Configuration>();
		algorithm.searchAllSolutions(partialConfiguration, allSolutions);
		for (Configuration conf : allSolutions) {
			System.out.println(conf+ " "+function.evaluate(conf));
			System.out.println();
		}

	}

	private static void addReconfigurationTimeValues(FeatureModel model, Configuration current) {
		for (Feature feature : model.getFeatures()) {
			if (feature.getSoftElement() != null && feature.getSoftElement() instanceof Component) {
				Component component = (Component) feature.getSoftElement();
				if (current.getFeatureState(feature) == FeatureState.SELECTED) {
					feature.addAttribute("DeselectionTime", component.getShutDownTime());
					feature.addAttribute("SelectionTime", 0.0);
				} else {
					feature.addAttribute("DeselectionTime", 0.0);
					feature.addAttribute("SelectionTime", component.getStartUpTime());
				}
			} else {
				feature.addAttribute("DeselectionTime", 0.0);
				feature.addAttribute("SelectionTime", 0.0);
			}

		}
	}
}
