package com.emiliano;

import java.util.LinkedList;
import java.util.List;

import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.Feature;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureModelImpl;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Exclude;
import com.emiliano.fmframework.core.constraints.treeConstraints.AlternativeGroup;
import com.emiliano.fmframework.core.constraints.treeConstraints.MandatoryFeature;
import com.emiliano.fmframework.core.constraints.treeConstraints.OptionalFeature;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.fmframework.optimization.ConfigurationSelectionInstance;
import com.emiliano.fmframework.optimization.csa.BacktrackingCSA;
import com.emiliano.fmframework.optimization.objectiveFunctions.AdditionObjective;

public class Main {
	public static void main(String[] args) {
		
		FeatureModel model=new FeatureModelImpl();
		model.addFeature(new Feature("root"));
		//classes
		model.addFeature(new Feature("Diagnoser"));
		model.addFeature(new Feature("Region"));
		model.addFeature(new Feature("Vehicle"));
		model.addFeature(new Feature("CarRecord"));
		model.addFeature(new Feature("RoadCamera"));
		//attach classes
		model.addTreeConstraint(new MandatoryFeature("root","Diagnoser"));
		model.addTreeConstraint(new MandatoryFeature("root","Region"));
		model.addTreeConstraint(new MandatoryFeature("root","Vehicle"));
		model.addTreeConstraint(new MandatoryFeature("root","CarRecord"));
		model.addTreeConstraint(new OptionalFeature("root","RoadCamera"));
		//add functions
		model.addFeature(new Feature("DiagnoserRVehicle_LinkRegion"));
		model.addFeature(new Feature("diagPerms2_LinkRegion"));
		model.addFeature(new Feature("Diagnoser_LinkRegion"));
		model.addFeature(new Feature("vehicleAttachementIsModifiedSince"));
		model.addFeature(new Feature("Vehicle_LinkRegion"));
		model.addFeature(new Feature("DiagCarPerms_Modify"));
		model.addFeature(new Feature("CArRecordPerms_Read"));
		model.addFeature(new Feature("carRecordPerms_Create"));
		model.addFeature(new Feature("RegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("NotRegionWithChangedInfoCons"));
		model.addFeature(new Feature("NotRegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("VCPerms_read"));
		model.addFeature(new Feature("VCPerms_followMe"));
		model.getFeature("VCPerms_followMe").addAttribute("ValueSelected", 1);

		model.addTreeConstraint(new OptionalFeature("Diagnoser","DiagnoserRVehicle_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","diagPerms2_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","Diagnoser_LinkRegion"));
		
		model.addTreeConstraint(new OptionalFeature("Vehicle","vehicleAttachementIsModifiedSince"));
		model.addTreeConstraint(new OptionalFeature("Vehicle","Vehicle_LinkRegion"));
		
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_Modify"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","CArRecordPerms_Read"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","carRecordPerms_Create"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","RegionWithoutChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","NotRegionWithChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","NotRegionWithoutChangedInfoCons"));
		
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_read"));
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_followMe"));
		/*
		model.addTreeConstraint(new MandatoryFeature("",""));
		model.addTreeConstraint(new AlternativeGroup("H","D","F"));
		model.addTreeConstraint(new MandatoryFeature("root","B"));
		model.addTreeConstraint(new OptionalFeature("root","C"));
		model.addCrossTreeConstraint(new Exclude("C", "D"));
		model.addTreeConstraint(new OptionalFeature("root","G"));
		*/
		Configuration conf=new Configuration(model);
		ConfOperations.selectFeature(conf, "root");
		System.out.println(conf);
		
//		ConfOperations.selectFeature(conf, "C");
//		System.out.println(conf);
		
		BacktrackingCSA alg=new BacktrackingCSA();
		Configuration bestConfiguration=alg.selectConfiguration(new ConfigurationSelectionInstance(conf,new AdditionObjective("ValueSelected", "ValueDeselected")));
		
		
		System.out.println(bestConfiguration);
		
	}
}
